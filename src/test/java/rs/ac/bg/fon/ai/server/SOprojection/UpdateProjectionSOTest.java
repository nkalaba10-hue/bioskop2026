package rs.ac.bg.fon.ai.server.SOprojection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.ac.bg.fon.ai.communication.model.Projection;

class UpdateProjectionSOTest {
    private UpdateProjectionSO so;
    @BeforeEach void setUp() { so = new UpdateProjectionSO(); }
    @AfterEach void tearDown() { so = null; }
    @Test void testUpdateProjectionSO() { assertNotNull(so); }
    @Test void testPreconditionPogresanTip() { assertEquals("Invalid parameter type - expected Projection", assertThrows(Exception.class, () -> so.precondition("projekcija")).getMessage()); }
    @Test void testPreconditionBezId() { assertEquals("Projection ID is required for update", assertThrows(Exception.class, () -> so.precondition(new Projection())).getMessage()); }
    @Test void testPreconditionNegativneKarte() { Projection projection = new Projection(); projection.setId(1L); projection.setSoldTickets(-1); assertEquals("Sold tickets cannot be negative", assertThrows(Exception.class, () -> so.precondition(projection)).getMessage()); }
}
