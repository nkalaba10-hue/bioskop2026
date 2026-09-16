/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.SObackground;

import java.time.LocalDateTime;
import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.communication.model.ProjectionStatus;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;

/**
 * Sistemska operacija koja azurira statuse svih projekcija prema vremenu,
 * broju prodatih karata i kapacitetu sale.
 *
 * @author nkala
 * @version 1.0
 */
public class UpdateProjectionStatusesSO extends AbstractSO {

   private int result;

   /** @return broj projekcija kojima je promenjen status. */
   public int getResult() {
       return result;
   }

   @Override
   protected void precondition(Object param) throws Exception {
       // Nema preduslova - ovo je sistemska operacija
   }

   @Override
   protected void executeOperation(Object param) throws Exception {
       Projection template = new Projection();
       List<Projection> allProjections = (List<Projection>) repository.getAll(template);

       int updatedCount = 0;
       LocalDateTime now = LocalDateTime.now();

       for (Projection projection : allProjections) {
           ProjectionStatus newStatus = calculateNewStatus(projection, now);

           if (newStatus != projection.getStatus()) {
               projection.setStatus(newStatus);
               repository.edit(projection);
               updatedCount++;
           }
       }

       result = updatedCount;
       System.out.println("  → Updated " + updatedCount + " projection statuses");
   }

   private ProjectionStatus calculateNewStatus(Projection projection, LocalDateTime now) {
       // Kreiraj LocalDateTime od LocalDate i LocalTime iz Projection klase
       LocalDateTime projectionStart = LocalDateTime.of(projection.getDate(), projection.getTime());
       LocalDateTime projectionEnd = projectionStart.plusMinutes(projection.getFilm().getDuration());

       // 1. Prošle projekcije - one čiji je završetak prošao
       if (projectionEnd.isBefore(now)) {
           return ProjectionStatus.PAST;
       }

       // 2. Projekcije u toku - one koje su počele ali još nisu završile
       if (projectionStart.isBefore(now) && projectionEnd.isAfter(now)) {
           return ProjectionStatus.ACTIVE;
       }

       // 3. SOLD_OUT projekcije ostaju SOLD_OUT
       if (projection.getStatus() == ProjectionStatus.SOLD_OUT) {
           return ProjectionStatus.SOLD_OUT;
       }

       // 4. Provera da li je popunjena sala za buduće projekcije
       if (projection.getSoldTickets() >= projection.getHall().getCapacity()) {
           return ProjectionStatus.SOLD_OUT;
       }

       // 5. Aktivne projekcije (buduće)
       return ProjectionStatus.ACTIVE;
   }
}
