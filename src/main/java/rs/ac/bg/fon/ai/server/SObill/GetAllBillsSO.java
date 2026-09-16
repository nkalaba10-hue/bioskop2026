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
 * Sistemska operacija za ucitavanje svih racuna sa pripadajucim tiketima.
 *
 * @author nkala
 * @version 1.0
 */
public class GetAllBillsSO extends AbstractSO {

    private List<Bill> result;

    /** @return lista svih ucitanih racuna. */
    public List<Bill> getResult() {
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
        // Nema specifičnih preduslova
    }

    /**
     * Izvrsava poslovnu logiku sistemske operacije nad validiranim parametrom.
     *
     * @param param validiran podatak koji se obradjuje
     * @throws Exception ako operacija ne moze da se izvrsi
     */
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

    /**
     * Ucitava sve tikete koji pripadaju jednom racunu.
     *
     * @param billId identifikator racuna
     * @return lista tiketa racuna
     * @throws Exception ako se operacija ucitavanja tiketa ne izvrsi uspesno
     */
    private List<Ticket> getTicketsForBill(Long billId) throws Exception {
        GetTicketsByBillIdSO getTicketsSO = new GetTicketsByBillIdSO();
        getTicketsSO.execute(billId);
        return getTicketsSO.getResult();
    }
}
