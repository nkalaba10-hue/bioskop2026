package rs.ac.bg.fon.ai.server.hallOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.ac.bg.fon.ai.communication.model.Hall;

class DeleteHallSOTest {
    private DeleteHallSO so;
    @BeforeEach
    void setUp() {
        so = new DeleteHallSO();
    }
    @AfterEach
    void tearDown() {
        so = null;
    }
    @Test
    void testDeleteHallSO() {
        assertNotNull(so);
    }
    @Test
    void testPreconditionPogresanTip() {
        assertEquals("Invalid parameter type - expected Hall", assertThrows(Exception.class, () -> so.precondition("hall")).getMessage());
    }
    @Test
    void testPreconditionBezId() {
        assertEquals("Hall ID is required for deletion", assertThrows(Exception.class, () -> so.precondition(new Hall())).getMessage());
    }

    @Test
    void testExecuteBriseSaluIzBaze() throws Exception {
        Hall hall = new Hall("TS" + System.nanoTime(), 20);
        new SaveHallSO().execute(hall);

        so.execute(hall);

        GetHallByIdSO getHallSO = new GetHallByIdSO();
        Exception e = assertThrows(Exception.class, () -> getHallSO.execute(hall.getId()));
        assertEquals("Hall with ID " + hall.getId() + " not found", e.getMessage());
    }
}
