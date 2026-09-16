package rs.ac.bg.fon.ai.server.hallOperations;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class GetAllHallsSOTest {
    private GetAllHallsSO so;
    @BeforeEach
    void setUp() {
        so = new GetAllHallsSO();
    }
    @AfterEach
    void tearDown() {
        so = null;
    }
    @Test
    void testGetAllHallsSO() {
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
    void testExecuteUcitavaSaleIzBaze() {
        assertDoesNotThrow(() -> so.execute(null));
        assertNotNull(so.getResult());
    }

}
