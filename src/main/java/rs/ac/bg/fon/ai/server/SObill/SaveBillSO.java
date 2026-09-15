/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.SObill;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rs.ac.bg.fon.ai.communication.model.Bill;
import rs.ac.bg.fon.ai.communication.model.GenericEntity;
import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.communication.model.ProjectionStatus;
import rs.ac.bg.fon.ai.communication.model.Ticket;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;

/**
 *
 * @author nkala
 */
public class SaveBillSO extends AbstractSO {

    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof Bill)) {
            throw new Exception("Invalid parameter type - expected Bill");
        }

        Bill bill = (Bill) param;

        // Validacija vremena
        if (bill.getDateTime() == null) {
            throw new Exception("Bill date and time is required");
        }

        // Validacija ukupnog iznosa
        if (bill.getTotalAmount() == null || bill.getTotalAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new Exception("Total amount must be positive");
        }

        // Validacija tiketa
        if (bill.getTickets() == null || bill.getTickets().isEmpty()) {
            throw new Exception("Bill must have at least one ticket");
        }

        // Provera dostupnosti projekcija i kapaciteta
        for (Ticket ticket : bill.getTickets()) {
            if (ticket.getProjection() == null || ticket.getProjection().getId() == null) {
                throw new Exception("Each ticket must have a valid projection");
            }

            if (ticket.getPrice() == null || ticket.getPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
                throw new Exception("Each ticket must have a positive price");
            }

            // Provera da li je projekcija aktivna
            Projection projection = getProjectionById(ticket.getProjection().getId());
            if (projection == null) {
                throw new Exception("Projection not found for ticket");
            }

            if (projection.getStatus() != ProjectionStatus.ACTIVE) {
                throw new Exception("Cannot create ticket for " + projection.getStatus() + " projection");
            }

            // Provera kapaciteta
            if (projection.getSoldTickets() >= projection.getHall().getCapacity()) {
                throw new Exception("Projection is sold out");
            }
        }

        // Provera sume tiketa vs ukupan iznos
        BigDecimal ticketsTotal = bill.getTickets().stream()
                .map(Ticket::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (ticketsTotal.compareTo(bill.getTotalAmount()) != 0) {
            throw new Exception("Tickets total amount does not match bill total amount");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Bill bill = (Bill) param;

        ;

        
        repository.add(bill);
        
        // 2. Postavi bill reference na sve tikete
        for (Ticket ticket : bill.getTickets()) {
            ticket.setBill(bill);
        }

       
        for (Ticket ticket : bill.getTickets()) {
            repository.add(ticket);
        }
        
        updateProjectionsSoldTickets(bill);
        
    }

    private void updateProjectionsSoldTickets(Bill bill) throws Exception {
        // Grupiši tikete po projekciji
        Map<Long, Integer> projectionTicketCounts = new HashMap<>();
        for (Ticket ticket : bill.getTickets()) {
            Long projectionId = ticket.getProjection().getId();
            projectionTicketCounts.put(projectionId, projectionTicketCounts.getOrDefault(projectionId, 0) + 1);
        }

        // Ažuriraj svaku projekciju koristeći repository
        for (Map.Entry<Long, Integer> entry : projectionTicketCounts.entrySet()) {
            Long projectionId = entry.getKey();
            int additionalTickets = entry.getValue();

            // Uzmi trenutnu projekciju koristeći getByQuery
            Projection projection = getProjectionById(projectionId);
            if (projection != null) {
                // Ažuriraj broj prodatih karata
                projection.setSoldTickets(projection.getSoldTickets() + additionalTickets);

                // Automatsko ažuriranje statusa na SOLD_OUT ako je potrebno
                if (projection.getSoldTickets() >= projection.getHall().getCapacity()) {
                    projection.setStatus(ProjectionStatus.SOLD_OUT);
                }

                // Koristi repository za update
                repository.edit(projection);
                System.out.println("  → Updated projection " + projectionId + " - sold tickets: " + projection.getSoldTickets());
            }
        }
    }

    private Projection getProjectionById(Long projectionId) throws Exception {
        String query = " WHERE p.id = " + projectionId;
        List<GenericEntity> results = repository.getByQuery(new Projection(), query);
        if (!results.isEmpty()) {
            return (Projection) results.get(0);
        }
        return null;
    }
}
