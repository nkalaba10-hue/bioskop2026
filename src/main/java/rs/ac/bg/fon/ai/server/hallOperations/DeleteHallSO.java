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
 * Sistemska operacija za brisanje sale koja nema zakazane buduce projekcije.
 *
 * @author nkala
 * @version 1.0
 */
public class DeleteHallSO extends AbstractSO {

   @Override
   protected void precondition(Object param) throws Exception {
       if (!(param instanceof Hall)) {
           throw new Exception("Invalid parameter type - expected Hall");
       }
       
       Hall hall = (Hall) param;
       
       // Provera ID-a
       if (hall.getId() == null) {
           throw new Exception("Hall ID is required for deletion");
       }
       
       // Provera da li sala postoji
       Hall template = new Hall();
       List existingHalls = repository.getAll(template);
       boolean hallExists = false;
       
       for (Object entity : existingHalls) {
           if (entity instanceof Hall) {
               Hall existing = (Hall) entity;
               if (existing.getId().equals(hall.getId())) {
                   hallExists = true;
                   break;
               }
           }
       }
       
       if (!hallExists) {
           throw new Exception("Hall with ID " + hall.getId() + " not found in database");
       }
       
       // Provera da li ima budućih projekcija za salu
       Projection projectionTemplate = new Projection();
       String query = " WHERE hall_id = " + hall.getId() + " AND date >= CURDATE() AND status != 'PAST'";
       List upcomingProjections = repository.getByQuery(projectionTemplate, query);
       
       if (!upcomingProjections.isEmpty()) {
           throw new Exception("Cannot delete hall - there are upcoming projections scheduled");
       }
       
       // Provera da li ima bilo kakvih projekcija u prošlosti (opciono)
       String allProjectionsQuery = " WHERE hall_id = " + hall.getId();
       List allProjections = repository.getByQuery(projectionTemplate, allProjectionsQuery);
       
       if (!allProjections.isEmpty()) {
           System.out.println("  → Warning: Hall has " + allProjections.size() + " historical projections");
           // Možete dodati dodatnu logiku ako želite
       }
   }

   @Override
   protected void executeOperation(Object param) throws Exception {
       Hall hall = (Hall) param;
       repository.delete(hall);
       System.out.println("  → Hall deleted: " + hall.getName() + " (ID: " + hall.getId() + ")");
   }
}
