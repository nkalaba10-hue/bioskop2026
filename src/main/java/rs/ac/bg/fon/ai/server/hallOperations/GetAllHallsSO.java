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
 * Sistemska operacija za ucitavanje svih sala.
 *
 * @author nkala
 * @version 1.0
 */
public class GetAllHallsSO extends AbstractSO {
    
    private List<Hall> result;

    /** @return lista svih ucitanih sala. */
    public List<Hall> getResult() {
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
        // Nema specifičnih preduslova za getAll operaciju
        // Možete dodati proveru autorizacije ako je potrebno
    }

    /**
     * Izvrsava poslovnu logiku sistemske operacije nad validiranim parametrom.
     *
     * @param param validiran podatak koji se obradjuje
     * @throws Exception ako operacija ne moze da se izvrsi
     */
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
