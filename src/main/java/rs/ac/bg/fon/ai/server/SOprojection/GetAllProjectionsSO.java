/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.SOprojection;

import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;

/**
 * Sistemska operacija za ucitavanje svih projekcija.
 *
 * @author nkala
 * @version 1.0
 */
public class GetAllProjectionsSO extends AbstractSO {

    private List<Projection> result;

    /** @return lista svih ucitanih projekcija. */
    public List<Projection> getResult() {
        return result;
    }

    @Override
    protected void precondition(Object param) throws Exception {
        // Nema specifičnih preduslova
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Projection template = new Projection();
        List resultList = repository.getAll(template);

        result = new ArrayList<>();
        for (Object entity : resultList) {
            if (entity instanceof Projection) {
                result.add((Projection) entity);
            }
        }

        System.out.println("  → Retrieved " + result.size() + " projections");
    }
}
