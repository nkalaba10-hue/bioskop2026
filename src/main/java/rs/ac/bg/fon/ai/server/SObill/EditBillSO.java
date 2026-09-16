/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.SObill;

import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Bill;
import rs.ac.bg.fon.ai.communication.model.GenericEntity;
import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.communication.model.ProjectionStatus;
import rs.ac.bg.fon.ai.communication.model.Ticket;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;

/**
 * Sistemska operacija za izmenu postojeceg racuna i njegovih tiketa.
 *
 * @author nkala
 * @version 1.0
 */
public class EditBillSO extends AbstractSO {

   @Override
   protected void precondition(Object param) throws Exception {
       if (param == null || !(param instanceof Bill)) {
           throw new Exception("Invalid bill data");
       }

       Bill bill = (Bill) param;

       if (bill.getId() == null) {
           throw new Exception("Bill ID is required for editing");
       }

       if (bill.getTickets() == null || bill.getTickets().isEmpty()) {
           throw new Exception("Bill must have at least one ticket");
       }

       
       Long firstProjectionId = bill.getTickets().get(0).getProjection().getId();
       for (Ticket ticket : bill.getTickets()) {
           if (!ticket.getProjection().getId().equals(firstProjectionId)) {
               throw new Exception("All tickets must be for the same projection");
           }
       }
   }

   @Override
   protected void executeOperation(Object param) throws Exception {
       Bill bill = (Bill) param;

       

       
       List<Ticket> existingTickets = getExistingTickets(bill.getId());
      

       // 2. Ažuriraj osnovne podatke računa
       repository.edit(bill);
       

       // 3. Obradi tikete - dodaj nove ili obriši višak
       processTickets(bill, existingTickets);

       // 4. Ažuriraj projekciju (sold tickets)
       updateProjectionSoldTickets(bill);
      
   }

   private List<Ticket> getExistingTickets(Long billId) throws Exception {
       String query = " WHERE bill_id = " + billId;
       List<GenericEntity> results = repository.getByQuery(new Ticket(), query);

       List<Ticket> tickets = new ArrayList<>();
       for (GenericEntity entity : results) {
           tickets.add((Ticket) entity);
       }
       return tickets;
   }

   private void processTickets(Bill newBill, List<Ticket> existingTickets) throws Exception {
       int requestedCount = newBill.getTickets().size();
       int existingCount = existingTickets.size();

      

       if (requestedCount > existingCount) {
           // Dodaj nove tikete
           int ticketsToAdd = requestedCount - existingCount;
          
           // Koristi prvi ticket kao template za nove tikete
           Ticket templateTicket = newBill.getTickets().get(0);

           for (int i = 0; i < ticketsToAdd; i++) {
               Ticket newTicket = new Ticket();
               newTicket.setProjection(templateTicket.getProjection());
               newTicket.setPrice(templateTicket.getPrice());
               newTicket.setBill(newBill);
               repository.add(newTicket);
           }

       } else if (requestedCount < existingCount) {
           // Obriši višak tiketa
           int ticketsToDelete = existingCount - requestedCount;
           System.out.println("  → Deleting " + ticketsToDelete + " excess tickets");

           // Obriši poslednjih X tiketa
           for (int i = 0; i < ticketsToDelete; i++) {
               int indexToDelete = existingCount - 1 - i;
               if (indexToDelete >= 0 && indexToDelete < existingTickets.size()) {
                   Ticket ticketToDelete = existingTickets.get(indexToDelete);
                   repository.delete(ticketToDelete);
               }
           }
       } else {
          
       }

       
   }

   private void updateProjectionSoldTickets(Bill bill) throws Exception {
       if (bill.getTickets().isEmpty()) {
           return;
       }

       // Uzmi prvi ticket da dobiješ projekciju (svi su isti)
       Ticket firstTicket = bill.getTickets().get(0);
       Projection projection = firstTicket.getProjection();

       // Ažuriraj broj prodatih karata za projekciju
       String query = " WHERE p.id = " + projection.getId();
       List<GenericEntity> results = repository.getByQuery(new Projection(), query);

       if (!results.isEmpty()) {
           Projection currentProjection = (Projection) results.get(0);
           currentProjection.setSoldTickets(bill.getTickets().size());

           // Automatsko ažuriranje statusa na SOLD_OUT ako je potrebno
           if (currentProjection.getSoldTickets() >= currentProjection.getHall().getCapacity()) {
               currentProjection.setStatus(ProjectionStatus.SOLD_OUT);
           } else {
               currentProjection.setStatus(ProjectionStatus.ACTIVE);
           }

           repository.edit(currentProjection);
           System.out.println("  → Updated projection " + projection.getId()
                   + " - sold tickets: " + currentProjection.getSoldTickets());
       }
   }
}
