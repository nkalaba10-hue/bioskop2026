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

import rs.ac.bg.fon.ai.communication.model.Film;
import rs.ac.bg.fon.ai.communication.model.Hall;
import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.server.SOfilm.GetAllFilmsSO;
import rs.ac.bg.fon.ai.server.hallOperations.GetAllHallsSO;

class SaveProjectionSOTest {
    private SaveProjectionSO so;
    @BeforeEach
    void setUp() {
        so = new SaveProjectionSO();
    }
    @AfterEach
    void tearDown() {
        so = null;
    }
    @Test
    void testSaveProjectionSO() {
        assertNotNull(so);
    }
    @Test
    void testPreconditionPogresanTip() {
        assertEquals("Invalid parameter type - expected Projection", assertThrows(Exception.class, () -> so.precondition("projekcija")).getMessage());
    }
    @Test
    void testPreconditionBezFilma() {
        assertEquals("Film is required", assertThrows(Exception.class, () -> so.precondition(new Projection())).getMessage());
    }

    @Test
    void testExecuteCuvaProjekcijuUBazu() throws Exception {
        GetAllFilmsSO getFilmsSO = new GetAllFilmsSO();
        getFilmsSO.execute(null);
        Film film = getFilmsSO.getResult().get(0);
        GetAllHallsSO getHallsSO = new GetAllHallsSO();
        getHallsSO.execute(null);
        Hall hall = getHallsSO.getResult().get(0);
        Projection projection = new Projection(film, hall, LocalDate.now().plusYears(10),
                LocalTime.of(1, 0), BigDecimal.valueOf(500));

        try {
            so.execute(projection);
        assertNotNull(projection.getId());
    } finally {
            if (projection.getId() != null) {
                new DeleteProjectionSO().execute(projection);
    }
        }
    }
}
