package rs.ac.bg.fon.ai.server.SOticket;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.*;

import rs.ac.bg.fon.ai.communication.model.Bill;
import rs.ac.bg.fon.ai.communication.model.Ticket;
import rs.ac.bg.fon.ai.server.SObill.GetAllBillsSO;
import rs.ac.bg.fon.ai.server.repository.DbConnectionFactory;

class SaveTicketsSOTest {
    private SaveTicketsSO so;
    @BeforeEach
    void setUp() {
        so = new SaveTicketsSO();
    }
    @AfterEach
    void tearDown() {
        so = null;
    }
    @Test
    void testSaveTicketsSO() {
        assertNotNull(so);
    }
    @Test
    void testPreconditionPogresanTip() {
        assertEquals("Invalid parameter type - expected List of Tickets", assertThrows(Exception.class, () -> so.precondition("karte")).getMessage());
    }
    @Test
    void testPreconditionPraznaLista() {
        assertEquals("Ticket list cannot be empty", assertThrows(Exception.class, () -> so.precondition(List.of())).getMessage());
    }

    @Test
    void testExecuteCuvaTiketUBazu() throws Exception {
        GetAllBillsSO getBillsSO = new GetAllBillsSO();
        getBillsSO.execute(null);
        Bill bill = getBillsSO.getResult().stream()
                .filter(item -> !item.getTickets().isEmpty())
                .findFirst()
                .orElseThrow();
        Ticket ticket = new Ticket(bill.getTickets().get(0).getProjection(),
                BigDecimal.ONE, bill);

        try {
            so.execute(List.of(ticket));
        assertNotNull(ticket.getId());
    } finally {
            if (ticket.getId() != null) {
                var connection = DbConnectionFactory.getInstance().getConnection();
                try (var statement = connection.prepareStatement("DELETE FROM ticket WHERE id = ?")) {
                    statement.setLong(1, ticket.getId());
                    statement.executeUpdate();
                    connection.commit();
    }
            }
        }
    }
}
