package rs.ac.bg.fon.ai.server.hallOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.ac.bg.fon.ai.communication.model.Hall;

class UpdateHallSOTest {
    private UpdateHallSO so;
    @BeforeEach void setUp() { so = new UpdateHallSO(); }
    @AfterEach void tearDown() { so = null; }
    @Test void testUpdateHallSO() { assertNotNull(so); }
    @Test void testPreconditionPogresanTip() { assertEquals("Invalid parameter type - expected Hall", assertThrows(Exception.class, () -> so.precondition("hall")).getMessage()); }
    @Test void testPreconditionBezId() { assertEquals("Hall ID is required for update", assertThrows(Exception.class, () -> so.precondition(new Hall("Sala", 10))).getMessage()); }
    @Test void testPreconditionNeispravniPodaci() { assertEquals("Hall name is required", assertThrows(Exception.class, () -> so.precondition(new Hall(1L, null, 10))).getMessage()); assertEquals("Hall capacity must be positive", assertThrows(Exception.class, () -> so.precondition(new Hall(1L, "Sala", 0))).getMessage()); }
}
