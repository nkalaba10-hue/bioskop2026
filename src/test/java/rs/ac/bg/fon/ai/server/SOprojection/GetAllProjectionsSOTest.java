package rs.ac.bg.fon.ai.server.SOprojection;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class GetAllProjectionsSOTest {
    private GetAllProjectionsSO so;
    @BeforeEach
    void setUp() {
        so = new GetAllProjectionsSO();
    }
    @AfterEach
    void tearDown() {
        so = null;
    }
    @Test
    void testGetAllProjectionsSO() {
        assertNotNull(so);
    }
    @Test
    void testPrecondition() {
        assertDoesNotThrow(() -> so.precondition(null));
    }
    @Test
    void testGetResultPreIzvrsavanja() {
        assertNull(so.getResult());
    }

    @Test
    void testExecuteUcitavaProjekcijeIzBaze() {
        assertDoesNotThrow(() -> so.execute(null));
        assertNotNull(so.getResult());
    }

}
