package rs.ac.bg.fon.ai.communication.model;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.*;

class ProjectionTest {
    private Projection projection;
    private Film film;
    private Hall hall;
    @BeforeEach void setUp() { projection = new Projection(); film = new Film(); film.setId(1L); film.setTitle("Film"); hall = new Hall(2L, "Sala", 50); }
    @AfterEach void tearDown() { projection = null; film = null; hall = null; }
    @Test void testProjection() { assertNotNull(projection); }
    @Test void testProjectionFilmHallLocalDateLocalTimeBigDecimal() { projection = new Projection(film, hall, LocalDate.of(2024, 5, 1), LocalTime.of(20, 0), BigDecimal.valueOf(500)); assertAll(() -> assertEquals(film, projection.getFilm()), () -> assertEquals(hall, projection.getHall()), () -> assertEquals(LocalDate.of(2024, 5, 1), projection.getDate()), () -> assertEquals(LocalTime.of(20, 0), projection.getTime()), () -> assertEquals(BigDecimal.valueOf(500), projection.getPrice()), () -> assertEquals(ProjectionStatus.ACTIVE, projection.getStatus()), () -> assertEquals(0, projection.getSoldTickets())); }
    @Test void testSeteriIGeteri() { projection.setId(3L); projection.setFilm(film); projection.setHall(hall); projection.setDate(LocalDate.of(2024, 5, 1)); projection.setTime(LocalTime.of(20, 0)); projection.setPrice(BigDecimal.valueOf(500)); projection.setStatus(ProjectionStatus.SOLD_OUT); projection.setSoldTickets(50); assertAll(() -> assertEquals(3L, projection.getId()), () -> assertEquals(film, projection.getFilm()), () -> assertEquals(hall, projection.getHall()), () -> assertEquals(ProjectionStatus.SOLD_OUT, projection.getStatus()), () -> assertEquals(50, projection.getSoldTickets())); }
    @Test void testSqlMetode() { projection.setId(3L); projection.setFilm(film); projection.setHall(hall); projection.setDate(LocalDate.of(2024, 5, 1)); projection.setTime(LocalTime.of(20, 0)); projection.setPrice(BigDecimal.valueOf(500)); projection.setStatus(ProjectionStatus.ACTIVE); projection.setSoldTickets(10); assertAll(() -> assertEquals("projection", projection.getTableName()), () -> assertEquals("film_id, hall_id, date, time, status, price, sold_tickets", projection.getAttributeList()), () -> assertEquals("1, 2, '2024-05-01', '20:00', 'ACTIVE', 500, 10", projection.getAttributeValues()), () -> assertEquals("id = 3", projection.getWhereCondition()), () -> assertTrue(projection.getSelectAllQuery().contains("FROM projection p"))); }
    @Test void testEqualsHashCodeIToString() { projection = new Projection(film, hall, LocalDate.of(2024, 5, 1), LocalTime.of(20, 0), BigDecimal.valueOf(500)); Projection isti = new Projection(film, hall, LocalDate.of(2024, 5, 1), LocalTime.of(20, 0), BigDecimal.valueOf(500)); assertAll(() -> assertEquals(projection, isti), () -> assertEquals(projection.hashCode(), isti.hashCode()), () -> assertFalse(projection.equals(null)), () -> assertFalse(projection.equals("projekcija")), () -> assertTrue(projection.toString().contains("Film")), () -> assertTrue(projection.toString().contains("Sala"))); }
}
