package rs.ac.bg.fon.ai.server.SOfilm;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class GetAllFilmsSOTest {
    private GetAllFilmsSO so;
    @BeforeEach
    void setUp() {
        so = new GetAllFilmsSO();
    }
    @AfterEach
    void tearDown() {
        so = null;
    }
    @Test
    void testGetAllFilmsSO() {
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
    void testExecuteUcitavaFilmoveIzBaze() {
        assertDoesNotThrow(() -> so.execute(null));
        assertNotNull(so.getResult());
    }
}
