package rs.ac.bg.fon.ai.server.SOgenre;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import rs.ac.bg.fon.ai.communication.model.Film;
import rs.ac.bg.fon.ai.server.SOfilm.GetAllFilmsSO;

class GetGenresByFilmIdSOTest {
    private GetGenresByFilmIdSO so;
    @BeforeEach
    void setUp() {
        so = new GetGenresByFilmIdSO();
    }
    @AfterEach
    void tearDown() {
        so = null;
    }
    @Test
    void testGetGenresByFilmIdSO() {
        assertNotNull(so);
    }
    @Test
    void testPreconditionPogresanTip() {
        assertEquals("Invalid parameter type - expected Long Film ID", assertThrows(Exception.class, () -> so.precondition("1")).getMessage());
    }
    @Test
    void testPreconditionNulaIliManje() {
        assertEquals("Invalid film ID", assertThrows(Exception.class, () -> so.precondition(0L)).getMessage());
        assertEquals("Invalid film ID", assertThrows(Exception.class, () -> so.precondition(-1L)).getMessage());
    }
    @Test
    void testGetResultPreIzvrsavanja() {
        assertNull(so.getResult());
    }

    @Test
    void testExecuteVracaZanroveFilmaIzBaze() throws Exception {
        GetAllFilmsSO getAllSO = new GetAllFilmsSO();
        getAllSO.execute(null);
        Film film = getAllSO.getResult().get(0);

        so.execute(film.getId());
        assertNotNull(so.getResult());
    }
}
