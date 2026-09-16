package rs.ac.bg.fon.ai.server.hallOperations;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class GetHallByIdSOTest {
    private GetHallByIdSO so;
    @BeforeEach void setUp() { so = new GetHallByIdSO(); }
    @AfterEach void tearDown() { so = null; }
    @Test void testGetHallByIdSO() { assertNotNull(so); }
    @Test void testPreconditionPogresanTip() { Exception e = assertThrows(Exception.class, () -> so.precondition("1")); assertEquals("Invalid parameter type - expected Long ID", e.getMessage()); }
    @Test void testPreconditionNulaIliManje() { assertEquals("Invalid hall ID", assertThrows(Exception.class, () -> so.precondition(0L)).getMessage()); assertEquals("Invalid hall ID", assertThrows(Exception.class, () -> so.precondition(-1L)).getMessage()); }
    @Test void testGetResultPreIzvrsavanja() { assertNull(so.getResult()); }
}
