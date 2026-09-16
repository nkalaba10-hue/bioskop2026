/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.hallOperations;

import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Hall;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;

/**
 * Sistemska operacija za cuvanje nove bioskopske sale.
 *
 * @author nkala
 * @version 1.0
 */
public class SaveHallSO extends AbstractSO {

    private Hall result;

    /** @return sacuvana sala nakon uspesnog izvrsavanja operacije. */
    public Hall getResult() {
        return result;
    }

    /**
     * Proverava da li parametar ispunjava poslovne preduslove operacije.
     *
     * @param param podatak koji se obradjuje
     * @throws Exception ako je parametar neispravan ili uslovi nisu ispunjeni
     */
    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof Hall)) {
            throw new Exception("Invalid parameter type - expected Hall");
        }

        Hall hall = (Hall) param;

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

        // Provera da li sala sa istim imenom već postoji
        Hall template = new Hall();
        List existingHalls = repository.getAll(template);

        for (Object entity : existingHalls) {
            if (entity instanceof Hall) {
                Hall existing = (Hall) entity;
                if (existing.getName().equalsIgnoreCase(hall.getName().trim())) {
                    throw new Exception("Hall with name '" + hall.getName() + "' already exists");
                }
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
        Hall hall = (Hall) param;
        repository.add(hall);
         this.result = hall;
        System.out.println("  → Hall saved with ID: " + hall.getId());
    }
}