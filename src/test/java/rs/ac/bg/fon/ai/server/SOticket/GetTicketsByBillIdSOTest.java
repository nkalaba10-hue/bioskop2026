package rs.ac.bg.fon.ai.server.SOticket;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import rs.ac.bg.fon.ai.communication.model.Bill;
import rs.ac.bg.fon.ai.server.SObill.GetAllBillsSO;

class GetTicketsByBillIdSOTest {
    private GetTicketsByBillIdSO so;
    @BeforeEach
    void setUp() {
        so = new GetTicketsByBillIdSO();
    }
    @AfterEach
    void tearDown() {
        so = null;
    }
    @Test
    void testGetTicketsByBillIdSO() {
        assertNotNull(so);
    }
    @Test
    void testPreconditionPogresanTip() {
        assertEquals("Invalid parameter type - expected Long Bill ID", assertThrows(Exception.class, () -> so.precondition("1")).getMessage());
    }
    @Test
    void testPreconditionNulaIliManje() {
        assertEquals("Invalid bill ID", assertThrows(Exception.class, () -> so.precondition(0L)).getMessage());
        assertEquals("Invalid bill ID", assertThrows(Exception.class, () -> so.precondition(-1L)).getMessage());
    }
    @Test
    void testGetResultPreIzvrsavanja() {
        assertNull(so.getResult());
    }

    @Test
    void testExecuteVracaTiketeRacunaIzBaze() throws Exception {
        GetAllBillsSO getBillsSO = new GetAllBillsSO();
        getBillsSO.execute(null);
        Bill bill = getBillsSO.getResult().stream()
                .filter(item -> !item.getTickets().isEmpty())
                .findFirst()
                .orElseThrow();

        so.execute(bill.getId());
        assertNotNull(so.getResult());
        assertFalse(so.getResult().isEmpty());
    }
}
