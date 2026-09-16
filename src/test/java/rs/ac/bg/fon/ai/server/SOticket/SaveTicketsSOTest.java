package rs.ac.bg.fon.ai.server.SOticket;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.*;

class SaveTicketsSOTest {
    private SaveTicketsSO so;
    @BeforeEach void setUp() { so = new SaveTicketsSO(); }
    @AfterEach void tearDown() { so = null; }
    @Test void testSaveTicketsSO() { assertNotNull(so); }
    @Test void testPreconditionPogresanTip() { assertEquals("Invalid parameter type - expected List of Tickets", assertThrows(Exception.class, () -> so.precondition("karte")).getMessage()); }
    @Test void testPreconditionPraznaLista() { assertEquals("Ticket list cannot be empty", assertThrows(Exception.class, () -> so.precondition(List.of())).getMessage()); }
}
