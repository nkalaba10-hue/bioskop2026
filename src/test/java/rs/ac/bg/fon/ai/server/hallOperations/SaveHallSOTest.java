package rs.ac.bg.fon.ai.server.hallOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.ac.bg.fon.ai.communication.model.Hall;

class SaveHallSOTest {
    private SaveHallSO so;
    @BeforeEach void setUp() { so = new SaveHallSO(); }
    @AfterEach void tearDown() { so = null; }
    @Test void testSaveHallSO() { assertNotNull(so); }
    @Test void testPreconditionPogresanTip() { assertEquals("Invalid parameter type - expected Hall", assertThrows(Exception.class, () -> so.precondition("hall")).getMessage()); }
    @Test void testPreconditionNeispravanNaziv() { assertEquals("Hall name is required", assertThrows(Exception.class, () -> so.precondition(new Hall(null, 10))).getMessage()); assertEquals("Hall name must be at least 2 characters long", assertThrows(Exception.class, () -> so.precondition(new Hall("A", 10))).getMessage()); }
    @Test void testPreconditionNeispravanKapacitet() { assertEquals("Hall capacity must be positive", assertThrows(Exception.class, () -> so.precondition(new Hall("Sala", 0))).getMessage()); assertEquals("Hall capacity cannot exceed 1000 seats", assertThrows(Exception.class, () -> so.precondition(new Hall("Sala", 1001))).getMessage()); }
    @Test void testGetResultPreIzvrsavanja() { assertNull(so.getResult()); }
}
