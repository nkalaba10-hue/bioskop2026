/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.SOprojection;

import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;

/**
 * Sistemska operacija za pronalazenje projekcije po identifikatoru.
 *
 * @author nkala
 * @version 1.0
 */
public class GetProjectionByIdSO extends AbstractSO {

    private Projection result;

    /** @return pronadjena projekcija. */
    public Projection getResult() {
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
        if (!(param instanceof Long)) {
            throw new Exception("Invalid parameter type - expected Long ID");
        }

        Long projectionId = (Long) param;

        if (projectionId == null || projectionId <= 0) {
            throw new Exception("Invalid projection ID");
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
        Long projectionId = (Long) param;
        Projection template = new Projection();

        String query = " WHERE p.id = " + projectionId;
        List resultList = repository.getByQuery(template, query);

        if (resultList.isEmpty()) {
            throw new Exception("Projection with ID " + projectionId + " not found");
        }

        result = (Projection) resultList.get(0);
        System.out.println("  → Retrieved projection: " + result.getFilm().getTitle() + " - " + result.getHall().getName());
    }
}
