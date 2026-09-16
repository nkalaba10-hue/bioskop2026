package rs.ac.bg.fon.ai.server.SObill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.ac.bg.fon.ai.communication.model.Bill;

class EditBillSOTest {
    private EditBillSO so;
    @BeforeEach void setUp() { so = new EditBillSO(); }
    @AfterEach void tearDown() { so = null; }
    @Test void testEditBillSO() { assertNotNull(so); }
    @Test void testPreconditionNullIliPogresanTip() { assertEquals("Invalid bill data", assertThrows(Exception.class, () -> so.precondition(null)).getMessage()); assertEquals("Invalid bill data", assertThrows(Exception.class, () -> so.precondition("racun")).getMessage()); }
    @Test void testPreconditionBezId() { assertEquals("Bill ID is required for editing", assertThrows(Exception.class, () -> so.precondition(new Bill())).getMessage()); }
    @Test void testPreconditionBezKarata() { Bill bill = new Bill(); bill.setId(1L); assertEquals("Bill must have at least one ticket", assertThrows(Exception.class, () -> so.precondition(bill)).getMessage()); }
}
