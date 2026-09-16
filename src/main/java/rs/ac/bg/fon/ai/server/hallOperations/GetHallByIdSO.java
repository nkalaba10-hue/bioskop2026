/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.hallOperations;

import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Hall;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;

/**
* Sistemska operacija za pronalazenje sale po identifikatoru.
*
* @author nkala
* @version 1.0
*/
public class GetHallByIdSO extends AbstractSO {
   
   private Hall result;

   /** @return pronadjena sala. */
   public Hall getResult() {
       return result;
   }

   @Override
   protected void precondition(Object param) throws Exception {
       if (!(param instanceof Long)) {
           throw new Exception("Invalid parameter type - expected Long ID");
       }
       
       Long hallId = (Long) param;
       
       if (hallId == null || hallId <= 0) {
           throw new Exception("Invalid hall ID");
       }
   }

   @Override
   protected void executeOperation(Object param) throws Exception {
       Long hallId = (Long) param;
       Hall template = new Hall();
       
       String query = " WHERE id = " + hallId;
       List resultList = repository.getByQuery(template, query);
       
       if (resultList.isEmpty()) {
           throw new Exception("Hall with ID " + hallId + " not found");
       }
       
       result = (Hall) resultList.get(0);
       System.out.println("  → Retrieved hall: " + result.getName());
   }
}