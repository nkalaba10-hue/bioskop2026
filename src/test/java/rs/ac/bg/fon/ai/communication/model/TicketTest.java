package rs.ac.bg.fon.ai.communication.model;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.*;

class TicketTest {
    private Ticket ticket;
    @BeforeEach void setUp() { ticket = new Ticket(); }
    @AfterEach void tearDown() { ticket = null; }
    @Test void testTicket() { assertNotNull(ticket); }
    @Test void testTicketProjectionBigDecimalBill() { Projection projection = new Projection(); Bill bill = new Bill(); ticket = new Ticket(projection, BigDecimal.valueOf(500), bill); assertAll(() -> assertEquals(projection, ticket.getProjection()), () -> assertEquals(BigDecimal.valueOf(500), ticket.getPrice()), () -> assertEquals(bill, ticket.getBill()), () -> assertEquals(Ticket.Action.NONE, ticket.getAction())); }
    @Test void testSeteriIGeteri() { Projection projection = new Projection(); Bill bill = new Bill(); ticket.setId(1L); ticket.setProjection(projection); ticket.setPrice(BigDecimal.valueOf(500)); ticket.setBill(bill); ticket.setAction(Ticket.Action.ADD); assertAll(() -> assertEquals(1L, ticket.getId()), () -> assertEquals(projection, ticket.getProjection()), () -> assertEquals(BigDecimal.valueOf(500), ticket.getPrice()), () -> assertEquals(bill, ticket.getBill()), () -> assertEquals(Ticket.Action.ADD, ticket.getAction())); }
    @Test void testSqlMetode() { Projection projection = new Projection(); projection.setId(2L); Bill bill = new Bill(); bill.setId(3L); ticket.setId(1L); ticket.setProjection(projection); ticket.setPrice(BigDecimal.valueOf(500)); ticket.setBill(bill); assertAll(() -> assertEquals("ticket", ticket.getTableName()), () -> assertEquals("projection_id, price, bill_id", ticket.getAttributeList()), () -> assertEquals("2, 500, 3", ticket.getAttributeValues()), () -> assertEquals("projection_id = 2, price = 500, bill_id = 3", ticket.setAttributeValues()), () -> assertEquals("id = 1", ticket.getWhereCondition()), () -> assertTrue(ticket.getSelectAllQuery().contains("FROM ticket t"))); }
    @Test void testEqualsHashCodeIToString() { ticket.setId(1L); ticket.setPrice(BigDecimal.valueOf(500)); Ticket isti = new Ticket(); isti.setId(1L); assertAll(() -> assertEquals(ticket, isti), () -> assertEquals(ticket.hashCode(), isti.hashCode()), () -> assertFalse(ticket.equals(null)), () -> assertFalse(ticket.equals("karta")), () -> assertTrue(ticket.toString().contains("Ticket #1")), () -> assertTrue(ticket.toString().contains("500"))); }
}
