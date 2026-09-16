/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.hallOperations;

import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Hall;
import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;

/**
* Sistemska operacija koja proverava da li sala ima buduce projekcije.
*
* @author nkala
* @version 1.0
*/
public class HasUpcomingProjectionsForHallSO extends AbstractSO {

   private boolean result;

   /** @return {@code true} ako sala ima najmanje jednu buducu projekciju. */
   public boolean getResult() {
       return result;
   }

   @Override
   protected void precondition(Object param) throws Exception {
       if (!(param instanceof Hall)) {
           throw new Exception("Invalid parameter type - expected Hall");
       }

       Hall hall = (Hall) param;

       if (hall.getId() == null) {
           throw new Exception("Hall ID is required");
       }
   }

   @Override
   protected void executeOperation(Object param) throws Exception {
       Hall hall = (Hall) param;
       Projection template = new Projection();

       String query = " WHERE hall_id = " + hall.getId() + " AND date >= CURDATE() AND status != 'PAST'";
       List upcomingProjections = repository.getByQuery(template, query);

       result = !upcomingProjections.isEmpty();
       System.out.println("  → Hall " + hall.getName() + " has upcoming projections: " + result);
   }
}
