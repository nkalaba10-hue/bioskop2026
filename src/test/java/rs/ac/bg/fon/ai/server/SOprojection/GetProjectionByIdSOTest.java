package rs.ac.bg.fon.ai.server.SOprojection;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class GetProjectionByIdSOTest {
    private GetProjectionByIdSO so;
    @BeforeEach void setUp() { so = new GetProjectionByIdSO(); }
    @AfterEach void tearDown() { so = null; }
    @Test void testGetProjectionByIdSO() { assertNotNull(so); }
    @Test void testPreconditionPogresanTip() { assertEquals("Invalid parameter type - expected Long ID", assertThrows(Exception.class, () -> so.precondition("1")).getMessage()); }
    @Test void testPreconditionNulaIliManje() { assertEquals("Invalid projection ID", assertThrows(Exception.class, () -> so.precondition(0L)).getMessage()); assertEquals("Invalid projection ID", assertThrows(Exception.class, () -> so.precondition(-1L)).getMessage()); }
    @Test void testGetResultPreIzvrsavanja() { assertNull(so.getResult()); }
}
