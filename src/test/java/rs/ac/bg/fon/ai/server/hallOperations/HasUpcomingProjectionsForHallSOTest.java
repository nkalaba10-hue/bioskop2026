package rs.ac.bg.fon.ai.server.hallOperations;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.ac.bg.fon.ai.communication.model.Hall;

class HasUpcomingProjectionsForHallSOTest {
    private HasUpcomingProjectionsForHallSO so;
    @BeforeEach
    void setUp() {
        so = new HasUpcomingProjectionsForHallSO();
    }
    @AfterEach
    void tearDown() {
        so = null;
    }
    @Test
    void testHasUpcomingProjectionsForHallSO() {
        assertNotNull(so);
    }
    @Test
    void testPreconditionPogresanTip() {
        assertEquals("Invalid parameter type - expected Hall", assertThrows(Exception.class, () -> so.precondition("hall")).getMessage());
    }
    @Test
    void testPreconditionBezId() {
        assertEquals("Hall ID is required", assertThrows(Exception.class, () -> so.precondition(new Hall())).getMessage());
    }
    @Test
    void testGetResultPreIzvrsavanja() {
        assertFalse(so.getResult());
    }

    @Test
    void testExecuteProveravaBuduceProjekcijeSale() throws Exception {
        GetAllHallsSO getAllSO = new GetAllHallsSO();
        getAllSO.execute(null);
        Hall hall = getAllSO.getResult().get(0);
        assertDoesNotThrow(() -> so.execute(hall));
    }
}
