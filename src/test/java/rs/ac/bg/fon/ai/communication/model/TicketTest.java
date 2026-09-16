package rs.ac.bg.fon.ai.communication.model;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.*;

class TicketTest {
    private Ticket ticket;
    @BeforeEach
    void setUp() {
        ticket = new Ticket();
    }
    @AfterEach
    void tearDown() {
        ticket = null;
    }
    @Test
    void testTicket() {
        assertNotNull(ticket);
    }
    @Test
    void testTicketProjectionBigDecimalBill() {
        Projection projection = new Projection();
        Bill bill = new Bill();
        ticket = new Ticket(projection, BigDecimal.valueOf(500), bill);
        assertAll(() -> assertEquals(projection, ticket.getProjection()), () -> assertEquals(BigDecimal.valueOf(500), ticket.getPrice()), () -> assertEquals(bill, ticket.getBill()), () -> assertEquals(Ticket.Action.NONE, ticket.getAction()));
    }
    @Test
    void testSetId() {
        ticket.setId(1L);
        assertEquals(1L, ticket.getId());
    }
    @Test
    void testSetProjection() {
        Projection projection = new Projection();
        ticket.setProjection(projection);
        assertEquals(projection, ticket.getProjection());
    }
    @Test
    void testSetPrice() {
        ticket.setPrice(BigDecimal.valueOf(500));
        assertEquals(BigDecimal.valueOf(500), ticket.getPrice());
    }
    @Test
    void testSetBill() {
        Bill bill = new Bill();
        ticket.setBill(bill);
        assertEquals(bill, ticket.getBill());
    }
    @Test
    void testSetAction() {
        ticket.setAction(Ticket.Action.ADD);
        assertEquals(Ticket.Action.ADD, ticket.getAction());
    }
    @Test
    void testSqlMetode() {
        Projection projection = new Projection();
        projection.setId(2L);
        Bill bill = new Bill();
        bill.setId(3L);
        ticket.setId(1L);
        ticket.setProjection(projection);
        ticket.setPrice(BigDecimal.valueOf(500));
        ticket.setBill(bill);
        assertAll(() -> assertEquals("ticket", ticket.getTableName()), () -> assertEquals("projection_id, price, bill_id", ticket.getAttributeList()), () -> assertEquals("2, 500, 3", ticket.getAttributeValues()), () -> assertEquals("projection_id = 2, price = 500, bill_id = 3", ticket.setAttributeValues()), () -> assertEquals("id = 1", ticket.getWhereCondition()), () -> assertTrue(ticket.getSelectAllQuery().contains("FROM ticket t")));
    }
    @Test
    void testEqualsHashCodeIToString() {
        ticket.setId(1L);
        ticket.setPrice(BigDecimal.valueOf(500));
        Ticket isti = new Ticket();
        isti.setId(1L);
        assertAll(() -> assertEquals(ticket, isti), () -> assertEquals(ticket.hashCode(), isti.hashCode()), () -> assertFalse(ticket.equals(null)), () -> assertFalse(ticket.equals("karta")), () -> assertTrue(ticket.toString().contains("Ticket #1")), () -> assertTrue(ticket.toString().contains("500")));
    }

    @Test
    void testSetPriceNegativnaVrednost() {
        assertThrows(IllegalArgumentException.class, () -> ticket.setPrice(BigDecimal.valueOf(-1)));
    }

    @Test
    void testSetPriceNull() {
        assertThrows(NullPointerException.class, () -> ticket.setPrice(null));
    }

    @Test
    void testSetProjectionNull() {
        assertThrows(NullPointerException.class, () -> ticket.setProjection(null));
    }

    @Test
    void testSetBillNull() {
        assertThrows(NullPointerException.class, () -> ticket.setBill(null));
    }

    @Test
    void testSetActionNull() {
        assertThrows(NullPointerException.class, () -> ticket.setAction(null));
    }
}
