package rs.ac.bg.fon.ai.server.SObill;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class GetAllBillsSOTest {
    private GetAllBillsSO so;
    @BeforeEach void setUp() { so = new GetAllBillsSO(); }
    @AfterEach void tearDown() { so = null; }
    @Test void testGetAllBillsSO() { assertNotNull(so); }
    @Test void testPrecondition() { assertDoesNotThrow(() -> so.precondition(null)); }
    @Test void testGetResultPreIzvrsavanja() { assertNull(so.getResult()); }
}
