/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.SOticket;

import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.communication.model.Ticket;
import rs.ac.bg.fon.ai.server.SOprojection.GetProjectionByIdSO;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;

/**
 * Sistemska operacija za ucitavanje tiketa koji pripadaju jednom racunu.
 *
 * @author nkala
 * @version 1.0
 */
public class GetTicketsByBillIdSO extends AbstractSO {

    private List<Ticket> result;

    /** @return lista tiketa trazenog racuna. */
    public List<Ticket> getResult() {
        return result;
    }

    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof Long)) {
            throw new Exception("Invalid parameter type - expected Long Bill ID");
        }

        Long billId = (Long) param;

        if (billId == null || billId <= 0) {
            throw new Exception("Invalid bill ID");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Long billId = (Long) param;
        Ticket template = new Ticket();

        String query = " WHERE bill_id = " + billId;
        List resultList = repository.getByQuery(template, query);

        result = new ArrayList<>();
        for (Object entity : resultList) {
            if (entity instanceof Ticket) {
                Ticket ticket = (Ticket) entity;

                // Učitaj projekciju za svaki ticket
                Projection projection = getProjectionById(ticket.getProjection().getId());
                ticket.setProjection(projection);

                result.add(ticket);
            }
        }

        System.out.println("  → Retrieved " + result.size() + " tickets for bill ID: " + billId);
    }

    private Projection getProjectionById(Long projectionId) throws Exception {
        GetProjectionByIdSO getProjectionSO = new GetProjectionByIdSO();
        getProjectionSO.execute(projectionId);
        return getProjectionSO.getResult();
    }
}
