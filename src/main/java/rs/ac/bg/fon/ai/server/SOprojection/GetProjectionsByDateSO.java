/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.SOprojection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;

/**
 * Sistemska operacija za ucitavanje projekcija zakazanih za odredjeni datum.
 *
 * @author nkala
 * @version 1.0
 */
public class GetProjectionsByDateSO extends AbstractSO {

    private List<Projection> result;

    /** @return lista projekcija za trazeni datum. */
    public List<Projection> getResult() {
        return result;
    }

    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof LocalDate)) {
            throw new Exception("Invalid parameter type - expected LocalDate");
        }

        LocalDate date = (LocalDate) param;

        if (date == null) {
            throw new Exception("Date is required");
        }

        if (date.isBefore(LocalDate.now())) {
            System.out.println("  → Warning: Retrieving projections for past date");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        LocalDate date = (LocalDate) param;
        Projection template = new Projection();

        // ISPRAVLJEN UPIT - sada je pravilna SQL sintaksa
        String query = "WHERE p.date = '" + date + "' ORDER BY p.time";

        List resultList = repository.getByQuery(template, query);

        result = new ArrayList<>();
        for (Object entity : resultList) {
            if (entity instanceof Projection) {
                result.add((Projection) entity);
            }
        }

        System.out.println("  → Retrieved " + result.size() + " projections for date: " + date);
    }
}
