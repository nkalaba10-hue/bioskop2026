package rs.ac.bg.fon.ai.server.SOprojection;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.*;

import rs.ac.bg.fon.ai.communication.model.Projection;

class GetProjectionsByDateSOTest {
    private GetProjectionsByDateSO so;
    @BeforeEach
    void setUp() {
        so = new GetProjectionsByDateSO();
    }
    @AfterEach
    void tearDown() {
        so = null;
    }
    @Test
    void testGetProjectionsByDateSO() {
        assertNotNull(so);
    }
    @Test
    void testPreconditionPogresanTip() {
        assertEquals("Invalid parameter type - expected LocalDate", assertThrows(Exception.class, () -> so.precondition("2026-01-01")).getMessage());
    }
    @Test
    void testPreconditionIspravanDatum() {
        assertDoesNotThrow(() -> so.precondition(LocalDate.now()));
    }
    @Test
    void testGetResultPreIzvrsavanja() {
        assertNull(so.getResult());
    }

    @Test
    void testExecuteVracaProjekcijeZaDatumIzBaze() throws Exception {
        GetAllProjectionsSO getAllSO = new GetAllProjectionsSO();
        getAllSO.execute(null);
        Projection projection = getAllSO.getResult().get(0);

        so.execute(projection.getDate());
        assertNotNull(so.getResult());
        assertTrue(so.getResult().stream()
                .allMatch(item -> projection.getDate().equals(item.getDate())));
    }
}
