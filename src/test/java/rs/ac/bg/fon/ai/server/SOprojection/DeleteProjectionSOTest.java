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

class DeleteProjectionSOTest {
    private DeleteProjectionSO so;
    @BeforeEach
    void setUp() {
        so = new DeleteProjectionSO();
    }
    @AfterEach
    void tearDown() {
        so = null;
    }
    @Test
    void testDeleteProjectionSO() {
        assertNotNull(so);
    }
    @Test
    void testPreconditionPogresanTip() {
        assertEquals("Invalid parameter type - expected Projection", assertThrows(Exception.class, () -> so.precondition("projekcija")).getMessage());
    }
    @Test
    void testPreconditionBezId() {
        assertEquals("Projection ID is required for deletion", assertThrows(Exception.class, () -> so.precondition(new Projection())).getMessage());
    }

    @Test
    void testExecuteBriseProjekcijuIzBaze() throws Exception {
        GetAllFilmsSO getFilmsSO = new GetAllFilmsSO();
        getFilmsSO.execute(null);
        GetAllHallsSO getHallsSO = new GetAllHallsSO();
        getHallsSO.execute(null);
        Projection projection = new Projection(getFilmsSO.getResult().get(0), getHallsSO.getResult().get(0),
                LocalDate.now().plusYears(11), LocalTime.of(1, 0), BigDecimal.valueOf(500));
        new SaveProjectionSO().execute(projection);

        so.execute(projection);
        assertThrows(Exception.class, () -> new GetProjectionByIdSO().execute(projection.getId()));
    }
}
