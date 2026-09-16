/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.SOgenre;

import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Genre;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;

/**
 * Sistemska operacija za ucitavanje svih zanrova.
 *
 * @author nkala
 * @version 1.0
 */
public class GetAllGenresSO extends AbstractSO {

    private List<Genre> result;

    /** @return lista svih ucitanih zanrova. */
    public List<Genre> getResult() {
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
        // Nema preduslova
    }

    /**
     * Izvrsava poslovnu logiku sistemske operacije nad validiranim parametrom.
     *
     * @param param validiran podatak koji se obradjuje
     * @throws Exception ako operacija ne moze da se izvrsi
     */
    @Override
    protected void executeOperation(Object param) throws Exception {
        Genre template = new Genre();
        List resultList = repository.getAll(template);

        result = new ArrayList<>();
        for (Object entity : resultList) {
            if (entity instanceof Genre) {
                result.add((Genre) entity);
            }
        }

        System.out.println("  → Retrieved " + result.size() + " genres");
    }
}
