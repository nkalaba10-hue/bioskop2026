/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.hallOperations;

import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Hall;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;

/**
 *
 * @author nkala
 */
public class GetAllHallsSO extends AbstractSO {
    
    private List<Hall> result;

    public List<Hall> getResult() {
        return result;
    }

    @Override
    protected void precondition(Object param) throws Exception {
        // Nema specifičnih preduslova za getAll operaciju
        // Možete dodati proveru autorizacije ako je potrebno
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Hall template = new Hall();
        List resultList = repository.getAll(template);
        
        // Konvertujemo GenericEntity listu u Hall listu
        result = new ArrayList<>();
        for (Object entity : resultList) {
            if (entity instanceof Hall) {
                result.add((Hall) entity);
            }
        }
        
        System.out.println("  → Retrieved " + result.size() + " halls");
    }
}
