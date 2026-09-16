package rs.ac.bg.fon.ai.server.SOgenre;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class GetGenresByFilmIdSOTest {
    private GetGenresByFilmIdSO so;
    @BeforeEach void setUp() { so = new GetGenresByFilmIdSO(); }
    @AfterEach void tearDown() { so = null; }
    @Test void testGetGenresByFilmIdSO() { assertNotNull(so); }
    @Test void testPreconditionPogresanTip() { assertEquals("Invalid parameter type - expected Long Film ID", assertThrows(Exception.class, () -> so.precondition("1")).getMessage()); }
    @Test void testPreconditionNulaIliManje() { assertEquals("Invalid film ID", assertThrows(Exception.class, () -> so.precondition(0L)).getMessage()); assertEquals("Invalid film ID", assertThrows(Exception.class, () -> so.precondition(-1L)).getMessage()); }
    @Test void testGetResultPreIzvrsavanja() { assertNull(so.getResult()); }
}
