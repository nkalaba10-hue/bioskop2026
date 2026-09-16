/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.SOticket;

import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Ticket;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;

/**
 * Sistemska operacija za nezavisno cuvanje liste tiketa.
 *
 * @author nkala
 * @version 1.0
 */
public class SaveTicketsSO extends AbstractSO {

    /**
     * Proverava da li parametar ispunjava poslovne preduslove operacije.
     *
     * @param param podatak koji se obradjuje
     * @throws Exception ako je parametar neispravan ili uslovi nisu ispunjeni
     */
    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof List)) {
            throw new Exception("Invalid parameter type - expected List of Tickets");
        }

        List<Ticket> tickets = (List<Ticket>) param;

        if (tickets == null || tickets.isEmpty()) {
            throw new Exception("Ticket list cannot be empty");
        }

        for (Ticket ticket : tickets) {
            if (ticket.getProjection() == null || ticket.getProjection().getId() == null) {
                throw new Exception("Each ticket must have a valid projection");
            }

            if (ticket.getPrice() == null || ticket.getPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
                throw new Exception("Each ticket must have a positive price");
            }

            if (ticket.getBill() == null || ticket.getBill().getId() == null) {
                throw new Exception("Each ticket must belong to a bill");
            }
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
        List<Ticket> tickets = (List<Ticket>) param;

        // Ova metoda je više pomoćna - tiketi se obično čuvaju kroz SaveBillSO
        // Ali implementiramo je za slučaj da se koristi nezavisno
        for (Ticket ticket : tickets) {
            repository.add(ticket);
        }

        System.out.println("  → Saved " + tickets.size() + " tickets");
    }
}
