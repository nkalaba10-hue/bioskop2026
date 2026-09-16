package rs.ac.bg.fon.ai.server.SOprojection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.server.SOfilm.GetAllFilmsSO;
import rs.ac.bg.fon.ai.server.hallOperations.GetAllHallsSO;

class UpdateProjectionSOTest {
    private UpdateProjectionSO so;
    @BeforeEach
    void setUp() {
        so = new UpdateProjectionSO();
    }
    @AfterEach
    void tearDown() {
        so = null;
    }
    @Test
    void testUpdateProjectionSO() {
        assertNotNull(so);
    }
    @Test
    void testPreconditionPogresanTip() {
        assertEquals("Invalid parameter type - expected Projection", assertThrows(Exception.class, () -> so.precondition("projekcija")).getMessage());
    }
    @Test
    void testPreconditionBezId() {
        assertEquals("Projection ID is required for update", assertThrows(Exception.class, () -> so.precondition(new Projection())).getMessage());
    }
    @Test
    void testSetSoldTicketsNegativnaVrednost() {
        Projection projection = new Projection();
        projection.setId(1L);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> projection.setSoldTickets(-1));
        assertEquals("Sold tickets cannot be negative", exception.getMessage());
    }

    @Test
    void testExecuteAzuriraBrojProdatihKarata() throws Exception {
        GetAllFilmsSO getFilmsSO = new GetAllFilmsSO();
        getFilmsSO.execute(null);
        GetAllHallsSO getHallsSO = new GetAllHallsSO();
        getHallsSO.execute(null);
        Projection projection = new Projection(getFilmsSO.getResult().get(0), getHallsSO.getResult().get(0),
                LocalDate.now().plusYears(13), LocalTime.of(1, 0), BigDecimal.valueOf(500));
        new SaveProjectionSO().execute(projection);

        try {
            projection.setSoldTickets(1);
            so.execute(projection);

            GetProjectionByIdSO getSO = new GetProjectionByIdSO();
            getSO.execute(projection.getId());
        assertEquals(1, getSO.getResult().getSoldTickets());
    } finally {
            projection.setSoldTickets(0);
            so.execute(projection);
            new DeleteProjectionSO().execute(projection);
    }
    }
}
