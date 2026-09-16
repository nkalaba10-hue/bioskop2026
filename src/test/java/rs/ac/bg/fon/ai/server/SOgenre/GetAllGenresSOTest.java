package rs.ac.bg.fon.ai.server.SOgenre;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class GetAllGenresSOTest {
    private GetAllGenresSO so;
    @BeforeEach
    void setUp() {
        so = new GetAllGenresSO();
    }
    @AfterEach
    void tearDown() {
        so = null;
    }
    @Test
    void testGetAllGenresSO() {
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
    void testExecuteUcitavaZanroveIzBaze() {
        assertDoesNotThrow(() -> so.execute(null));
        assertNotNull(so.getResult());
    }

}
