package rs.ac.bg.fon.ai.server.SObackground;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class UpdateProjectionStatusesSOTest {
    private UpdateProjectionStatusesSO so;
    @BeforeEach
    void setUp() {
        so = new UpdateProjectionStatusesSO();
    }
    @AfterEach
    void tearDown() {
        so = null;
    }
    @Test
    void testUpdateProjectionStatusesSO() {
        assertNotNull(so);
    }
    @Test
    void testPrecondition() {
        assertDoesNotThrow(() -> so.precondition(null));
    }
    @Test
    void testGetResultPreIzvrsavanja() {
        assertEquals(0, so.getResult());
    }

    @Test
    void testExecuteAzuriraStatuseProjekcija() {
        assertDoesNotThrow(() -> so.execute(null));
        assertTrue(so.getResult() >= 0);
    }
}
