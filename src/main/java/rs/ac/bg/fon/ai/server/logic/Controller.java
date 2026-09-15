/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.logic;

import java.time.LocalDate;
import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Bill;
import rs.ac.bg.fon.ai.communication.model.Employee;
import rs.ac.bg.fon.ai.communication.model.Film;
import rs.ac.bg.fon.ai.communication.model.Genre;
import rs.ac.bg.fon.ai.communication.model.Hall;
import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.communication.model.Ticket;
import rs.ac.bg.fon.ai.server.SObackground.UpdateProjectionStatusesSO;
import rs.ac.bg.fon.ai.server.SObill.EditBillSO;
import rs.ac.bg.fon.ai.server.SObill.GetAllBillsSO;
import rs.ac.bg.fon.ai.server.SObill.SaveBillSO;
import rs.ac.bg.fon.ai.server.SOempolyee.LoginSO;
import rs.ac.bg.fon.ai.server.SOempolyee.LoginSO.LoginParams;
import rs.ac.bg.fon.ai.server.SOfilm.AddMovieSO;
import rs.ac.bg.fon.ai.server.SOfilm.GetAllFilmsSO;
import rs.ac.bg.fon.ai.server.SOgenre.GetAllGenresSO;
import rs.ac.bg.fon.ai.server.SOgenre.GetGenresByFilmIdSO;
import rs.ac.bg.fon.ai.server.SOprojection.DeleteProjectionSO;
import rs.ac.bg.fon.ai.server.SOprojection.EditProjectionSO;
import rs.ac.bg.fon.ai.server.SOprojection.GetAllProjectionsSO;
import rs.ac.bg.fon.ai.server.SOprojection.GetProjectionByIdSO;
import rs.ac.bg.fon.ai.server.SOprojection.GetProjectionsByDateSO;
import rs.ac.bg.fon.ai.server.SOprojection.SaveProjectionSO;
import rs.ac.bg.fon.ai.server.SOprojection.UpdateProjectionSO;
import rs.ac.bg.fon.ai.server.SOticket.GetTicketsByBillIdSO;
import rs.ac.bg.fon.ai.server.SOticket.SaveTicketsSO;
import rs.ac.bg.fon.ai.server.hallOperations.DeleteHallSO;
import rs.ac.bg.fon.ai.server.hallOperations.GetAllHallsSO;
import rs.ac.bg.fon.ai.server.hallOperations.GetHallByIdSO;
import rs.ac.bg.fon.ai.server.hallOperations.HasUpcomingProjectionsForHallSO;
import rs.ac.bg.fon.ai.server.hallOperations.SaveHallSO;
import rs.ac.bg.fon.ai.server.hallOperations.UpdateHallSO;

/**
 *
 * @author nkala
 */
public class Controller {

    private static Controller instance;
//    private final DbRepository storageHall;
//    private final DbRepository storageProjection;
//    private final DbRepository storageGenre;
//    private final DbRepository storageFilm;
//    private final DbRepository storageTicket;
//    private final DbRepository storageBill;

    private Controller() {
//        this.storageHall = new RepositoryHall();
//        this.storageProjection = new RepositoryProjection();
//        this.storageGenre = new RepositoryGenre();
//        this.storageFilm = new RepositoryFilm();
//        this.storageTicket = new RepositoryTicket();
//        this.storageBill = new RepositoryBill();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
            return instance;
        }
        return instance;
    }

    // Hall operations
    public void saveHall(Hall hall) throws Exception {
        SaveHallSO so = new SaveHallSO();
        so.execute(hall);
    }

    public List<Hall> getHalls() throws Exception {
        GetAllHallsSO so = new GetAllHallsSO();
        so.execute(null);
        return so.getResult();
    }

    public void updateHall(Hall hall) throws Exception {
        UpdateHallSO so = new UpdateHallSO();
        so.execute(hall);
    }

    public void deleteHall(Hall hall) throws Exception {
        DeleteHallSO so = new DeleteHallSO();
        so.execute(hall);
    }

    public Hall getHallById(Long id) throws Exception {
        GetHallByIdSO so = new GetHallByIdSO();
        so.execute(id);
        return so.getResult();
    }

    public boolean hasUpcomingProjectionsForHall(Hall hall) throws Exception {
        HasUpcomingProjectionsForHallSO so = new HasUpcomingProjectionsForHallSO();
        so.execute(hall);
        return so.getResult();
    }

    // ========== PROJECTION OPERATIONS ==========
    public Projection saveProjection(Projection projection) throws Exception {
        SaveProjectionSO so = new SaveProjectionSO();
        so.execute(projection);
        return projection; // Vraća sa postavljenim ID-jem
    }

    public List<Projection> getAllProjections() throws Exception {
        GetAllProjectionsSO so = new GetAllProjectionsSO();
        so.execute(null);
        return so.getResult();
    }

    public Projection getProjectionById(Long id) throws Exception {
        GetProjectionByIdSO so = new GetProjectionByIdSO();
        so.execute(id);
        return so.getResult();
    }

    public List<Projection> getProjectionsByDate(LocalDate date) throws Exception {
        GetProjectionsByDateSO so = new GetProjectionsByDateSO();
        so.execute(date);
        return so.getResult();
    }

    public void updateProjection(Projection projection) throws Exception {
        UpdateProjectionSO so = new UpdateProjectionSO();
        so.execute(projection);
    }

    public void editProjection(Projection projection) throws Exception {
        EditProjectionSO so = new EditProjectionSO();
        so.execute(projection);
    }

    public void deleteProjection(Projection projection) throws Exception {
        DeleteProjectionSO so = new DeleteProjectionSO();
        so.execute(projection);
    }

    // ========== FILM OPERATIONS ==========
    public void addMovie(Film film) throws Exception {
        AddMovieSO so = new AddMovieSO();
        so.execute(film);
    }

    public List<Film> getAllFilms() throws Exception {
        GetAllFilmsSO so = new GetAllFilmsSO();
        so.execute(null);
        return so.getResult();
    }

    // ========== GENRE OPERATIONS ==========
    public List<Genre> getAllGenres() throws Exception {
        GetAllGenresSO so = new GetAllGenresSO();
        so.execute(null);
        return so.getResult();
    }

    public List<Genre> getGenresByID(Long filmId) throws Exception {
        GetGenresByFilmIdSO so = new GetGenresByFilmIdSO();
        so.execute(filmId);
        return so.getResult();
    }

    // ========== TICKET OPERATIONS ==========
    public void saveTickets(List<Ticket> tickets) throws Exception {
        SaveTicketsSO so = new SaveTicketsSO();
        so.execute(tickets);
    }

    public List<Ticket> getTicketsByBillId(Long billId) throws Exception {
        GetTicketsByBillIdSO so = new GetTicketsByBillIdSO();
        so.execute(billId);
        return so.getResult();
    }

    // ========== BILL OPERATIONS ==========
    public Bill saveBill(Bill bill) throws Exception {
        SaveBillSO so = new SaveBillSO();
        so.execute(bill);
        return bill; // Vraća sa postavljenim ID-jem
    }

    public List<Bill> getAllBills() throws Exception {
        GetAllBillsSO so = new GetAllBillsSO();
        so.execute(null);
        return so.getResult();
    }

    public void editBill(Bill bill) throws Exception {
        EditBillSO so = new EditBillSO();
        so.execute(bill);
    }

    // ========== EMPLOYEE OPERATIONS ==========
    public Employee login(String username, String password) throws Exception {
        LoginSO so = new LoginSO();
        so.execute(new LoginParams(username, password));
        return so.getResult();
    }

    // ========== BACKGROUND OPERATIONS ==========
    public int updateProjectionStatuses() throws Exception {
        UpdateProjectionStatusesSO so = new UpdateProjectionStatusesSO();
        so.execute(null);
        return so.getResult();
    }

    /*
    
    public void saveHall(Hall hall) throws Exception {
        storageHall.connect();
        try {
            if (!storageHall.getAll().contains(hall)) {
                storageHall.add(hall);
                storageHall.commit();
            } else {
                throw new Exception("Hall already exists!");
            }
        } catch (Exception e) {
            storageHall.rollback();
            throw e;
        } finally {
            storageHall.disconnect();
        }
    }

    public List<Hall> getHalls() throws SQLException, Exception {
        return storageHall.getAll();
    }

    public List<Projection> getAllProjections() throws Exception {
        return storageProjection.getAll();
    }

    public List<Genre> getGenresByID(Long id) throws Exception {
        return storageGenre.getAll(id);
    }

    public List<Film> getAllFilms() throws Exception {
        return storageFilm.getAll();
    }

    public Projection saveProjection(Projection p) throws Exception {
        storageProjection.connect();
        try {

            // PROVERA DA LI JE PROJEKCIJA U PROŠLOSTI
            LocalDateTime projectionDateTime = LocalDateTime.of(p.getDate(), p.getTime());
            if (projectionDateTime.isBefore(LocalDateTime.now())) {
                throw new Exception("Cannot add projection in the past! Selected time: "
                        + projectionDateTime + ", Current time: " + LocalDateTime.now());
            }
            // Provera preklapanja sa postojećim projekcijama
            for (Projection existing : getAllProjections()) {
                if (projectionsOverlap(existing, p)) {
                    throw new Exception("Projection overlaps with existing projection in the same hall!");
                }
            }

            // Ako nema preklapanja, dodaj novu projekciju
            storageProjection.add(p);
            storageProjection.commit();

            return p;
        } catch (Exception e) {
            storageProjection.rollback();
            throw e;

        } finally {
            storageProjection.disconnect();
        }
    }

    public void updateProjection(Projection projection) throws SQLException {
        storageProjection.connect();
        try {
            storageProjection.update(projection);
            storageProjection.commit();
        } catch (Exception ex) {
            storageProjection.rollback();
        } finally {
            storageProjection.disconnect();
        }
    }

    public void saveTickets(List<Ticket> tickets) throws SQLException {
        storageTicket.connect();
        try {
            storageTicket.addAll(tickets);
            storageTicket.commit();
        } catch (Exception e) {
            storageTicket.rollback();
        } finally {
            storageTicket.disconnect();
        }
    }

    public List<Bill> getAllBills() throws Exception {
        return storageBill.getAll();
    }

    public List<Ticket> getTicketsByBillId(Long id) throws Exception {
        return storageTicket.getAll(id);
    }

    public Projection getProjectionById(Long projectionId) throws Exception {
        return (Projection) storageProjection.getById(projectionId);
    }

    public List<Projection> getProjectionsByDate(LocalDate date) throws Exception {
        return storageProjection.getByDate(date);
    }

    public List<Genre> getAllGenres() throws Exception {
        return storageGenre.getAll();
    }

    public void addMovie(Film f) throws Exception {
        storageFilm.connect();
        try {
            if (!storageFilm.getAll().contains(f)) {
                storageFilm.add(f);
                storageFilm.commit();
            } else {
                throw new Exception("Film vec postoji!");
            }
        } catch (Exception ex) {
            storageFilm.rollback();
            throw ex;

        } finally {
            storageFilm.disconnect();
        }
    }

    public boolean hasUpcomingProjectionsForHall(Hall hall) throws Exception {
        return storageHall.hasUpcomingProjectionsForHall(hall.getId());
    }

    public void deleteHall(Hall hall) throws Exception {

        storageHall.connect();
        try {
            storageHall.delete(hall);
            storageHall.commit();

        } catch (Exception e) {
            storageHall.rollback();
            throw e;
        } finally {
            storageTicket.disconnect();
        }

    }

    public void updateHall(Hall hall) throws Exception {
        storageHall.connect();
        try {
            storageHall.edit(hall);
            storageHall.commit();

        } catch (Exception e) {
            storageHall.rollback();
            throw e;
        } finally {
            storageTicket.disconnect();
        }

    }

    private boolean projectionsOverlap(Projection p1, Projection p2) {
        if (p1.getId().equals(p2.getId())) {
            return false;
        }

        if (!p1.getHall().equals(p2.getHall())) {
            return false;
        }

        LocalDateTime start1 = LocalDateTime.of(p1.getDate(), p1.getTime());
        LocalDateTime end1 = start1.plusMinutes(p1.getFilm().getDuration()); // ako imate durationInMinutes

        LocalDateTime start2 = LocalDateTime.of(p2.getDate(), p2.getTime());
        LocalDateTime end2 = start2.plusMinutes(p2.getFilm().getDuration());

        return (start1.isBefore(end2) && end1.isAfter(start2));
    }

    public void editProjection(Projection projectionToEdit) throws Exception {
        storageProjection.connect();
        try {
            // PROVERA DA LI JE PROJEKCIJA U PROŠLOSTI
            LocalDateTime projectionDateTime = LocalDateTime.of(projectionToEdit.getDate(), projectionToEdit.getTime());
            if (projectionDateTime.isBefore(LocalDateTime.now())) {
                throw new Exception("Cannot change projection in the past! Selected time: "
                        + projectionDateTime + ", Current time: " + LocalDateTime.now());
            }
            // Provera preklapanja sa postojećim projekcijama
            for (Projection existing : getAllProjections()) {

                if (projectionsOverlap(existing, projectionToEdit)) {
                    throw new Exception("Projection overlaps with existing projection in the same hall!");
                }
            }

            storageProjection.edit(projectionToEdit);
            storageProjection.commit();
        } catch (Exception ex) {
            storageProjection.rollback();
            throw ex;
        } finally {
            storageProjection.disconnect();
        }
    }

    public void deleteProjection(Projection projection) throws Exception {
        storageProjection.connect();
        
        try {
            storageProjection.delete(projection);
            storageProjection.commit();
        } catch (Exception ex) {
            storageProjection.rollback();
            throw ex;
        } finally {
            storageProjection.disconnect();
        }

    }

    /**
     * Izračunava trenutni status projekcije na osnovu vremena
     */
//    private ProjectionStatus calculateProjectionStatus(Projection projection, LocalDateTime now) {
//        LocalDateTime projectionStart = LocalDateTime.of(projection.getDate(), projection.getTime());
//        LocalDateTime projectionEnd = projectionStart.plusMinutes(projection.getFilm().getDuration());
//
//        if (projectionEnd.isBefore(now)) {
//            return ProjectionStatus.PAST; // Projekcija je završena
//        } else if (projectionStart.isBefore(now) && projectionEnd.isAfter(now)) {
//            return ProjectionStatus.ACTIVE; // Projekcija je u toku
//        } else {
//            // Projekcija je u budućnosti - proveri kapacitet
//            if (projection.getSoldTickets() >= projection.getHall().getCapacity()) {
//                return ProjectionStatus.SOLD_OUT;
//            } else {
//                return ProjectionStatus.ACTIVE;
//            }
//        }
//    }
//
//    /**
//     * Ažurira statuse svih projekcija i vraća broj ažuriranih
//     */
//    public int updateProjectionStatuses() throws Exception {
//        List<Projection> projections = getAllProjections();
//        LocalDateTime now = LocalDateTime.now();
//        int updatedCount = 0;
//
//        for (Projection projection : projections) {
//            ProjectionStatus newStatus = calculateProjectionStatus(projection, now);
//            if (projection.getStatus() != newStatus) {
//                projection.setStatus(newStatus);
//                updateProjection(projection);
//                updatedCount++;
//            }
//        }
//        return updatedCount;
//    }
//
//    public Bill saveBill(Bill bill) throws Exception {
//        // KONEKTIJ SVE REPOZITORIJUME
//        storageBill.connect();
//        storageTicket.connect();
//        storageProjection.connect();
//
//        try {
//            // PROJEKCIJA IZ BILL-A (prva stavka)
//            Projection projection = bill.getTickets().get(0).getProjection();
//            int ticketCount = bill.getTickets().size();
//
//            // 1. PROVERI DA LI JE PROJEKCIJA JOŠ UVEK DOSTUPNA
//            Projection currentProjection = getCurrentProjectionFromDB(projection.getId());
//
//            // 2. VALIDACIJA NA NAJNOVIJIM PODACIMA IZ BAZE
//            validateTicketPurchase(currentProjection, ticketCount);
//
//            // 3. AŽURIRAJ PROJEKCIJU (smanji slobodna mesta)
//            updateProjectionAfterTicketSale(currentProjection, ticketCount);
//            storageProjection.update(currentProjection);
//
//            // 4. SAČUVAJ BILL
//            storageBill.add(bill);
//
//            // 5. SAČUVAJ TICKETE
//            for (Ticket t : bill.getTickets()) {
//                t.setBill(bill); // Osiguraj da svi tiketi imaju bill ID
//            }
//            storageTicket.addAll(bill.getTickets());
//
//            // 6. COMMIT
//            storageBill.commit();
//            storageTicket.commit();
//            storageProjection.commit();
//
//            return bill;
//
//        } catch (Exception ex) {
//            storageBill.rollback();
//            storageTicket.rollback();
//            storageProjection.rollback();
//            throw ex;
//        } finally {
//            storageBill.disconnect();
//            storageTicket.disconnect();
//            storageProjection.disconnect();
//        }
//    }
//
//    /**
//     * Uzima najnovije podatke o projekciji IZ BAZE (ne iz keša)
//     */
//    private Projection getCurrentProjectionFromDB(Long projectionId) throws Exception {
//        return (Projection) storageProjection.getById(projectionId);
//    }
//
//    private void validateTicketPurchase(Projection projection, int ticketCount) throws Exception {
//        int availableSeats = projection.getHall().getCapacity() - projection.getSoldTickets();
//
//        if (ticketCount <= 0) {
//            throw new Exception("Morate kupiti barem jednu kartu");
//        }
//
//        if (ticketCount > availableSeats) {
//            throw new Exception("Dostupno je samo " + availableSeats + " mesta. Pokušavate kupiti " + ticketCount);
//        }
//
//        LocalDateTime projectionTime = LocalDateTime.of(projection.getDate(), projection.getTime());
//        if (projectionTime.isBefore(LocalDateTime.now())) {
//            throw new Exception("Ne možete kupiti kartu za prošle projekcije");
//        }
//
//        if (projection.getStatus() == ProjectionStatus.SOLD_OUT) {
//            throw new Exception("Projekcija je već popunjena");
//        }
//    }
//
//    private void updateProjectionAfterTicketSale(Projection projection, int ticketCount) {
//        int newSoldCount = projection.getSoldTickets() + ticketCount;
//        projection.setSoldTickets(newSoldCount);
//
//        if (newSoldCount >= projection.getHall().getCapacity()) {
//            projection.setStatus(ProjectionStatus.SOLD_OUT);
//        }
//    }
}
