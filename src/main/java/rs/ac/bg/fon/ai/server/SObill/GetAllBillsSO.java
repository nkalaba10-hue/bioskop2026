/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.SObill;

import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Bill;
import rs.ac.bg.fon.ai.communication.model.Ticket;
import rs.ac.bg.fon.ai.server.SOticket.GetTicketsByBillIdSO;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;

/**
 *
 * @author nkala
 */
public class GetAllBillsSO extends AbstractSO {

    private List<Bill> result;

    public List<Bill> getResult() {
        return result;
    }

    @Override
    protected void precondition(Object param) throws Exception {
        // Nema specifičnih preduslova
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Bill template = new Bill();
        List resultList = repository.getAll(template);

        result = new ArrayList<>();
        for (Object entity : resultList) {
            if (entity instanceof Bill) {
                Bill bill = (Bill) entity;

                // Učitaj tikete za svaki račun
                List<Ticket> tickets = getTicketsForBill(bill.getId());
                bill.setTickets(tickets);

                result.add(bill);
            }
        }

        System.out.println("  → Retrieved " + result.size() + " bills");
    }

    private List<Ticket> getTicketsForBill(Long billId) throws Exception {
        GetTicketsByBillIdSO getTicketsSO = new GetTicketsByBillIdSO();
        getTicketsSO.execute(billId);
        return getTicketsSO.getResult();
    }
}
