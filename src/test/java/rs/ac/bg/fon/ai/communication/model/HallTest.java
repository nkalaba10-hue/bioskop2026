package rs.ac.bg.fon.ai.communication.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class HallTest {
    private Hall hall;
    @BeforeEach
    void setUp() {
        hall = new Hall();
    }
    @AfterEach
    void tearDown() {
        hall = null;
    }
    @Test
    void testHall() {
        assertNotNull(hall);
    }
    @Test
    void testKonstruktori() {
        Hall bezId = new Hall("Sala 1", 50);
        Hall saId = new Hall(1L, "Sala 2", 100);
        assertAll(() -> assertEquals("Sala 1", bezId.getName()), () -> assertEquals(50, bezId.getCapacity()), () -> assertEquals(1L, saId.getId()), () -> assertEquals("Sala 2", saId.getName()), () -> assertEquals(100, saId.getCapacity()));
    }
    @Test
    void testSetId() {
        hall.setId(1L);
        assertEquals(1L, hall.getId());
    }
    @Test
    void testSetName() {
        hall.setName("Sala 1");
        assertEquals("Sala 1", hall.getName());
    }
    @Test
    void testSetCapacity() {
        hall.setCapacity(50);
        assertEquals(50, hall.getCapacity());
    }
    @Test
    void testSqlMetode() {
        hall = new Hall(2L, "Sala 1", 50);
        assertAll(() -> assertEquals("hall", hall.getTableName()), () -> assertEquals("name, capacity", hall.getAttributeList()), () -> assertEquals("'Sala 1', 50", hall.getAttributeValues()), () -> assertEquals("name = 'Sala 1', capacity = 50", hall.setAttributeValues()), () -> assertEquals("id = 2", hall.getWhereCondition()), () -> assertEquals("SELECT * FROM hall", hall.getSelectAllQuery()));
    }
    @Test
    void testEqualsHashCodeIToString() {
        hall = new Hall(1L, "Sala 1", 50);
        Hall isti = new Hall(2L, "Sala 1", 100);
        assertAll(() -> assertEquals(hall, isti), () -> assertFalse(hall.equals(null)), () -> assertFalse(hall.equals("Sala 1")), () -> assertTrue(hall.toString().contains("Sala 1")), () -> assertTrue(hall.toString().contains("50")));
    }

    @Test
    void testSetNameNull() {
        assertThrows(NullPointerException.class, () -> hall.setName(null));
    }

    @Test
    void testSetCapacityNula() {
        assertThrows(IllegalArgumentException.class, () -> hall.setCapacity(0));
    }

    @Test
    void testSetNamePredugacak() {
        assertThrows(IllegalArgumentException.class,
                () -> hall.setName("Naziv sale koji ima vise od dvadeset znakova"));
    }

    @Test
    void testSetNamePrekratak() {
        assertThrows(IllegalArgumentException.class, () -> hall.setName("A"));
    }

    @Test
    void testSetCapacityPrevelik() {
        assertThrows(IllegalArgumentException.class, () -> hall.setCapacity(1001));
    }
}
