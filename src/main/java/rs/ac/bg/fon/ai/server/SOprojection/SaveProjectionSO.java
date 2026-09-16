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
 * Sistemska operacija za cuvanje nove projekcije nakon provere sale i termina.
 *
 * @author nkala
 * @version 1.0
 */
public class SaveProjectionSO extends AbstractSO {

    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof Projection)) {
            throw new Exception("Invalid parameter type - expected Projection");
        }

        Projection projection = (Projection) param;

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

        // Provera da li je sala dostupna u dato vreme
        if (!isHallAvailable(projection.getHall().getId(), projection.getDate(), projection.getTime(), null)) {
            throw new Exception("Hall is not available at the selected date and time");
        }

        // Provera da li je projekcija u budućnosti
        LocalDateTime projectionDateTime = LocalDateTime.of(projection.getDate(), projection.getTime());
        if (projectionDateTime.isBefore(LocalDateTime.now())) {
            throw new Exception("Projection must be in the future");
        }

        // Provera jedinstvenosti (isti film, sala, datum i vreme)
        if (isDuplicateProjection(projection.getFilm().getId(), projection.getHall().getId(),
                projection.getDate(), projection.getTime(), null)) {
            throw new Exception("Projection with same film, hall, date and time already exists");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Projection projection = (Projection) param;

        // Postavi podrazumevane vrednosti
        if (projection.getStatus() == null) {
            projection.setStatus(ProjectionStatus.ACTIVE);
        }
        if (projection.getSoldTickets() < 0) {
            projection.setSoldTickets(0);
        }

        repository.add(projection);
        System.out.println("  → Projection saved with ID: " + projection.getId());
    }

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

    private boolean isTimeOverlap(java.time.LocalTime existingTime, java.time.LocalTime newTime, int filmDuration) {
        // Dodajemo trajanje filma + 30 minuta za čišćenje
        java.time.LocalTime existingEnd = existingTime.plusMinutes(filmDuration + 30);
        java.time.LocalTime newEnd = newTime.plusMinutes(filmDuration + 30);

        return !(newTime.isAfter(existingEnd) || newEnd.isBefore(existingTime));
    }

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
}
