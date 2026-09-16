package rs.ac.bg.fon.ai.server.SOprojection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.ac.bg.fon.ai.communication.model.Projection;

class EditProjectionSOTest {
    private EditProjectionSO so;
    @BeforeEach void setUp() { so = new EditProjectionSO(); }
    @AfterEach void tearDown() { so = null; }
    @Test void testEditProjectionSO() { assertNotNull(so); }
    @Test void testPreconditionPogresanTip() { assertEquals("Invalid parameter type - expected Projection", assertThrows(Exception.class, () -> so.precondition("projekcija")).getMessage()); }
    @Test void testPreconditionBezId() { assertEquals("Projection ID is required for edit", assertThrows(Exception.class, () -> so.precondition(new Projection())).getMessage()); }
    @Test void testPreconditionBezFilma() { Projection projection = new Projection(); projection.setId(1L); assertEquals("Film is required", assertThrows(Exception.class, () -> so.precondition(projection)).getMessage()); }
}
