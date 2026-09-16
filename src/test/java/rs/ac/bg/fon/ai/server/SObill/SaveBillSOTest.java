package rs.ac.bg.fon.ai.server.SObill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.ac.bg.fon.ai.communication.model.Bill;
import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.communication.model.Ticket;
import rs.ac.bg.fon.ai.server.SOfilm.GetAllFilmsSO;
import rs.ac.bg.fon.ai.server.SOprojection.DeleteProjectionSO;
import rs.ac.bg.fon.ai.server.SOprojection.SaveProjectionSO;
import rs.ac.bg.fon.ai.server.SOprojection.UpdateProjectionSO;
import rs.ac.bg.fon.ai.server.hallOperations.GetAllHallsSO;
import rs.ac.bg.fon.ai.server.repository.DbConnectionFactory;

class SaveBillSOTest {
    private SaveBillSO so;
    @BeforeEach
    void setUp() {
        so = new SaveBillSO();
    }
    @AfterEach
    void tearDown() {
        so = null;
    }
    @Test
    void testSaveBillSO() {
        assertNotNull(so);
    }
    @Test
    void testPreconditionPogresanTip() {
        assertEquals("Invalid parameter type - expected Bill", assertThrows(Exception.class, () -> so.precondition("racun")).getMessage());
    }
    @Test
    void testPreconditionBezDatuma() {
        assertEquals("Bill date and time is required", assertThrows(Exception.class, () -> so.precondition(new Bill())).getMessage());
    }
    @Test
    void testSetTotalAmountNula() {
        Bill bill = new Bill();
        bill.setDateTime(LocalDateTime.now());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bill.setTotalAmount(BigDecimal.ZERO));
        assertEquals("Bill total amount must be positive", exception.getMessage());
    }
    @Test
    void testPreconditionBezKarata() {
        Bill bill = new Bill();
        bill.setDateTime(LocalDateTime.now());
        bill.setTotalAmount(BigDecimal.ONE);
        assertEquals("Bill must have at least one ticket", assertThrows(Exception.class, () -> so.precondition(bill)).getMessage());
    }

    @Test
    void testExecuteCuvaRacunITiketUBazu() throws Exception {
        GetAllBillsSO getBillsSO = new GetAllBillsSO();
        getBillsSO.execute(null);
        GetAllFilmsSO getFilmsSO = new GetAllFilmsSO();
        getFilmsSO.execute(null);
        GetAllHallsSO getHallsSO = new GetAllHallsSO();
        getHallsSO.execute(null);
        Projection projection = new Projection(getFilmsSO.getResult().get(0), getHallsSO.getResult().get(0),
                LocalDate.now().plusYears(14), LocalTime.of(1, 0), BigDecimal.valueOf(500));
        new SaveProjectionSO().execute(projection);
        Bill bill = new Bill(LocalDateTime.now(), List.of(), BigDecimal.valueOf(500),
                getBillsSO.getResult().get(0).getSavedBy());
        Ticket ticket = new Ticket(projection, BigDecimal.valueOf(500), bill);
        bill.setTickets(List.of(ticket));

        try {
            so.execute(bill);
        assertNotNull(bill.getId());
        assertNotNull(ticket.getId());
    } finally {
            var connection = DbConnectionFactory.getInstance().getConnection();
            try (var deleteTickets = connection.prepareStatement("DELETE FROM ticket WHERE bill_id = ?");
                    var deleteBill = connection.prepareStatement("DELETE FROM bill WHERE id = ?")) {
                if (bill.getId() != null) {
                    deleteTickets.setLong(1, bill.getId());
                    deleteTickets.executeUpdate();
        deleteBill.setLong(1, bill.getId());
                    deleteBill.executeUpdate();
                    connection.commit();
    }
            }
            projection.setSoldTickets(0);
            new UpdateProjectionSO().execute(projection);
            new DeleteProjectionSO().execute(projection);
    }
    }
}
