package rs.ac.bg.fon.ai.server.SObill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.ac.bg.fon.ai.communication.model.Bill;

class SaveBillSOTest {
    private SaveBillSO so;
    @BeforeEach void setUp() { so = new SaveBillSO(); }
    @AfterEach void tearDown() { so = null; }
    @Test void testSaveBillSO() { assertNotNull(so); }
    @Test void testPreconditionPogresanTip() { assertEquals("Invalid parameter type - expected Bill", assertThrows(Exception.class, () -> so.precondition("racun")).getMessage()); }
    @Test void testPreconditionBezDatuma() { assertEquals("Bill date and time is required", assertThrows(Exception.class, () -> so.precondition(new Bill())).getMessage()); }
    @Test void testPreconditionNeispravanIznos() { Bill bill = new Bill(); bill.setDateTime(LocalDateTime.now()); bill.setTotalAmount(BigDecimal.ZERO); assertEquals("Total amount must be positive", assertThrows(Exception.class, () -> so.precondition(bill)).getMessage()); }
    @Test void testPreconditionBezKarata() { Bill bill = new Bill(); bill.setDateTime(LocalDateTime.now()); bill.setTotalAmount(BigDecimal.ONE); assertEquals("Bill must have at least one ticket", assertThrows(Exception.class, () -> so.precondition(bill)).getMessage()); }
}
