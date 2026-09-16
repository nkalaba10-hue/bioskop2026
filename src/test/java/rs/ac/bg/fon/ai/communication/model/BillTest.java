package rs.ac.bg.fon.ai.communication.model;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.*;

class BillTest {
    private Bill bill;
    @BeforeEach void setUp() { bill = new Bill(); }
    @AfterEach void tearDown() { bill = null; }
    @Test void testBill() { assertNotNull(bill); }
    @Test void testBillLocalDateTimeListBigDecimalEmployee() { LocalDateTime datum = LocalDateTime.of(2024, 5, 1, 20, 0); List<Ticket> tickets = List.of(new Ticket()); Employee zaposleni = new Employee("Petar", "Petrovic", "pera", "lozinka"); bill = new Bill(datum, tickets, BigDecimal.valueOf(500), zaposleni); assertAll(() -> assertEquals(datum, bill.getDateTime()), () -> assertEquals(tickets, bill.getTickets()), () -> assertEquals(BigDecimal.valueOf(500), bill.getTotalAmount()), () -> assertEquals(zaposleni, bill.getSavedBy())); }
    @Test void testSeteriIGeteri() { LocalDateTime datum = LocalDateTime.of(2024, 5, 1, 20, 0); bill.setId(1L); bill.setDateTime(datum); bill.setTickets(List.of(new Ticket())); bill.setTotalAmount(BigDecimal.valueOf(500)); bill.setSavedBy(new Employee()); assertAll(() -> assertEquals(1L, bill.getId()), () -> assertEquals(datum, bill.getDateTime()), () -> assertEquals(1, bill.getTickets().size()), () -> assertEquals(BigDecimal.valueOf(500), bill.getTotalAmount()), () -> assertNotNull(bill.getSavedBy())); }
    @Test void testSqlMetode() { bill.setId(2L); bill.setDateTime(LocalDateTime.of(2024, 5, 1, 20, 0)); bill.setTotalAmount(BigDecimal.valueOf(500)); Employee zaposleni = new Employee(); zaposleni.setId(3L); bill.setSavedBy(zaposleni); assertAll(() -> assertEquals("bill", bill.getTableName()), () -> assertEquals("date_time, total_amount, saved_by", bill.getAttributeList()), () -> assertEquals("'2024-05-01T20:00', 500, 3", bill.getAttributeValues()), () -> assertEquals("date_time = '2024-05-01T20:00', total_amount = 500, saved_by = 3", bill.setAttributeValues()), () -> assertEquals("id = 2", bill.getWhereCondition()), () -> assertTrue(bill.getSelectAllQuery().contains("FROM bill b"))); }
    @Test void testEqualsHashCodeIToString() { LocalDateTime datum = LocalDateTime.of(2024, 5, 1, 20, 0); bill.setId(1L); bill.setDateTime(datum); bill.setTotalAmount(BigDecimal.valueOf(500)); Bill isti = new Bill(); isti.setId(1L); isti.setDateTime(datum); isti.setTotalAmount(BigDecimal.valueOf(500)); assertAll(() -> assertEquals(bill, isti), () -> assertEquals(bill.hashCode(), isti.hashCode()), () -> assertFalse(bill.equals(null)), () -> assertFalse(bill.equals("racun")), () -> assertTrue(bill.toString().contains("Bill #1")), () -> assertTrue(bill.toString().contains("500"))); }
}
