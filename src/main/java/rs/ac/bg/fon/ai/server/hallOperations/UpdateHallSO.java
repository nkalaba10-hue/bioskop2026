/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.hallOperations;

import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Hall;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;

/**
 *
 * @author nkala
 */
public class UpdateHallSO extends AbstractSO {

    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof Hall)) {
            throw new Exception("Invalid parameter type - expected Hall");
        }
        
        Hall hall = (Hall) param;
        
        // Provera ID-a
        if (hall.getId() == null) {
            throw new Exception("Hall ID is required for update");
        }
        
        // Validacija naziva
        if (hall.getName() == null || hall.getName().trim().isEmpty()) {
            throw new Exception("Hall name is required");
        }
        
        if (hall.getName().trim().length() < 2) {
            throw new Exception("Hall name must be at least 2 characters long");
        }
        
        if (hall.getName().length() > 20) {
            throw new Exception("Hall name cannot exceed 20 characters");
        }
        
        // Validacija kapaciteta
        if (hall.getCapacity() <= 0) {
            throw new Exception("Hall capacity must be positive");
        }
        
        if (hall.getCapacity() > 1000) {
            throw new Exception("Hall capacity cannot exceed 1000 seats");
        }
        
        // Provera da li sala sa istim imenom već postoji (isključujući trenutnu salu)
        Hall template = new Hall();
        List existingHalls = repository.getAll(template);
        
        for (Object entity : existingHalls) {
            if (entity instanceof Hall) {
                Hall existing = (Hall) entity;
                if (existing.getName().equalsIgnoreCase(hall.getName().trim()) && 
                    !existing.getId().equals(hall.getId())) {
                    throw new Exception("Hall with name '" + hall.getName() + "' already exists");
                }
            }
        }
        
        // Provera da li sala postoji u bazi
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
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Hall hall = (Hall) param;
        repository.edit(hall);
        System.out.println("  → Hall updated: " + hall.getName());
    }
}
