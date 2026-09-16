/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.SOprojection;

import java.time.LocalDateTime;
import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.communication.model.ProjectionStatus;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;

/**
 * Sistemska operacija za izmenu podataka o postojecoj projekciji.
 *
 * @author nkala
 * @version 1.0
 */
public class EditProjectionSO extends AbstractSO {

    /**
     * Proverava da li parametar ispunjava poslovne preduslove operacije.
     *
     * @param param podatak koji se obradjuje
     * @throws Exception ako je parametar neispravan ili uslovi nisu ispunjeni
     */
    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof Projection)) {
            throw new Exception("Invalid parameter type - expected Projection");
        }

        Projection projection = (Projection) param;

        if (projection.getId() == null) {
            throw new Exception("Projection ID is required for edit");
        }

        // Validacija obaveznih polja
        if (projection.getFilm() == null || projection.getFilm().getId() == null) {
            throw new Exception("Film is required");
        }

        if (projection.getHall() == null || projection.getHall().getId() == null) {
            throw new Exception("Hall is required");
        }

        if (projection.getDate() == null) {
            throw new Exception("Date is required");
        }

        if (projection.getTime() == null) {
            throw new Exception("Time is required");
        }

        if (projection.getPrice() == null || projection.getPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new Exception("Price must be positive");
        }

        // Provera da li je projekcija u budućnosti (ne možemo menjati prošle projekcije)
        LocalDateTime projectionDateTime = LocalDateTime.of(projection.getDate(), projection.getTime());
        if (projectionDateTime.isBefore(LocalDateTime.now())) {
            throw new Exception("Cannot edit past projections");
        }

        // Provera dostupnosti sale (isključujući trenutnu projekciju)
        if (!isHallAvailable(projection.getHall().getId(), projection.getDate(), projection.getTime(), projection.getId())) {
            throw new Exception("Hall is not available at the selected date and time");
        }

        // Provera jedinstvenosti (isključujući trenutnu projekciju)
        if (isDuplicateProjection(projection.getFilm().getId(), projection.getHall().getId(),
                projection.getDate(), projection.getTime(), projection.getId())) {
            throw new Exception("Projection with same film, hall, date and time already exists");
        }

        // Provera da li ima prodatih karata - ako ima, ne možemo smanjivati kapacitet
        Projection existingProjection = getExistingProjection(projection.getId());
        if (existingProjection != null && projection.getSoldTickets() < existingProjection.getSoldTickets()) {
            throw new Exception("Cannot decrease sold tickets count");
        }
    }

    /**
     * Izvrsava poslovnu logiku sistemske operacije nad validiranim parametrom.
     *
     * @param param validiran podatak koji se obradjuje
     * @throws Exception ako operacija ne moze da se izvrsi
     */
    @Override
    protected void executeOperation(Object param) throws Exception {
        Projection projection = (Projection) param;

        // Ažuriraj status ako je potrebno
        if (projection.getSoldTickets() >= projection.getHall().getCapacity()) {
            projection.setStatus(ProjectionStatus.SOLD_OUT);
        } else if (projection.getStatus() == ProjectionStatus.SOLD_OUT) {
            projection.setStatus(ProjectionStatus.ACTIVE);
        }

        repository.edit(projection);
        System.out.println("  → Projection edited: " + projection.getFilm().getTitle() + " - " + projection.getHall().getName());
    }

    /**
     * Proverava da li je sala slobodna u trazenom terminu.
     *
     * @param hallId identifikator sale
     * @param date datum projekcije
     * @param time vreme pocetka projekcije
     * @param excludeProjectionId projekcija koja se izuzima pri izmeni, moze biti {@code null}
     * @return {@code true} ako nema vremenskog preklapanja, inace {@code false}
     * @throws Exception ako se postojece projekcije ne mogu ucitati
     */
    private boolean isHallAvailable(Long hallId, java.time.LocalDate date, java.time.LocalTime time, Long excludeProjectionId) throws Exception {
        Projection template = new Projection();
        String query = " WHERE p.hall_id = " + hallId + " AND p.date = '" + date + "' AND p.status != 'PAST'";

        if (excludeProjectionId != null) {
            query += " AND p.id != " + excludeProjectionId; // Dodajte alias p.
        }

        List projections = repository.getByQuery(template, query);

        for (Object entity : projections) {
            if (entity instanceof Projection) {
                Projection existing = (Projection) entity;
                if (isTimeOverlap(existing.getTime(), time, existing.getFilm().getDuration())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Proverava da li se dva termina preklapaju, ukljucujuci 30 minuta pauze.
     *
     * @param existingTime vreme postojece projekcije
     * @param newTime vreme nove projekcije
     * @param filmDuration trajanje filma u minutima
     * @return {@code true} ako se termini preklapaju, inace {@code false}
     */
    private boolean isTimeOverlap(java.time.LocalTime existingTime, java.time.LocalTime newTime, int filmDuration) {
        java.time.LocalTime existingEnd = existingTime.plusMinutes(filmDuration + 30);
        java.time.LocalTime newEnd = newTime.plusMinutes(filmDuration + 30);
        return !(newTime.isAfter(existingEnd) || newEnd.isBefore(existingTime));
    }

    /**
     * Proverava da li za isti film, salu i termin vec postoji projekcija.
     *
     * @param filmId identifikator filma
     * @param hallId identifikator sale
     * @param date datum projekcije
     * @param time vreme pocetka projekcije
     * @param excludeProjectionId projekcija koja se izuzima pri izmeni, moze biti {@code null}
     * @return {@code true} ako duplikat postoji, inace {@code false}
     * @throws Exception ako se provera ne moze izvrsiti nad bazom
     */
    private boolean isDuplicateProjection(Long filmId, Long hallId, java.time.LocalDate date,
            java.time.LocalTime time, Long excludeProjectionId) throws Exception {
        Projection template = new Projection();
        String query = " WHERE p.film_id = " + filmId + " AND p.hall_id = " + hallId
                + " AND p.date = '" + date + "' AND p.time = '" + time + "' AND p.status != 'PAST'";

        if (excludeProjectionId != null) {
            query += " AND p.id != " + excludeProjectionId; // Dodajte alias p.
        }

        List projections = repository.getByQuery(template, query);
        return !projections.isEmpty();
    }

    /**
     * Ucitava postojecu projekciju po identifikatoru.
     *
     * @param projectionId identifikator projekcije
     * @return pronadjena projekcija ili {@code null} ako ne postoji
     * @throws Exception ako upit prema bazi ne uspe
     */
    private Projection getExistingProjection(Long projectionId) throws Exception {
        Projection template = new Projection();
        String query = " WHERE p.id = " + projectionId; // Dodajte alias p.
        List result = repository.getByQuery(template, query);
        return result.isEmpty() ? null : (Projection) result.get(0);
    }
}
