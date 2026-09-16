package rs.ac.bg.fon.ai.communication.model;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.*;

class FilmTest {
    private Film film;
    @BeforeEach void setUp() { film = new Film(); }
    @AfterEach void tearDown() { film = null; }
    @Test void testFilm() { assertNotNull(film); }
    @Test void testFilmStringIntStringLocalDateList() { LocalDate datum = LocalDate.of(2024, 5, 1); List<Genre> zanrovi = List.of(new Genre("Drama")); film = new Film("Film", 120, "Opis", datum, zanrovi); assertAll(() -> assertEquals("Film", film.getTitle()), () -> assertEquals(120, film.getDuration()), () -> assertEquals("Opis", film.getDescription()), () -> assertEquals(datum, film.getReleaseDate()), () -> assertEquals(zanrovi, film.getGenres())); }
    @Test void testSeteriIGeteri() { LocalDate datum = LocalDate.of(2024, 5, 1); List<Genre> zanrovi = List.of(new Genre("Drama")); film.setId(1L); film.setTitle("Film"); film.setDuration(120); film.setDescription("Opis"); film.setReleaseDate(datum); film.setGenres(zanrovi); assertAll(() -> assertEquals(1L, film.getId()), () -> assertEquals("Film", film.getTitle()), () -> assertEquals(120, film.getDuration()), () -> assertEquals("Opis", film.getDescription()), () -> assertEquals(datum, film.getReleaseDate()), () -> assertEquals(zanrovi, film.getGenres())); }
    @Test void testSqlMetode() { film = new Film("O'Brien", 120, "Opis", LocalDate.of(2024, 5, 1), List.of()); film.setId(2L); assertAll(() -> assertEquals("film", film.getTableName()), () -> assertEquals("title, duration, description, release_date", film.getAttributeList()), () -> assertEquals("'O''Brien', 120, 'Opis', '2024-05-01'", film.getAttributeValues()), () -> assertEquals("title = 'O''Brien', duration = 120, description = 'Opis', release_date = '2024-05-01'", film.setAttributeValues()), () -> assertEquals("id = 2", film.getWhereCondition()), () -> assertEquals("SELECT * FROM film", film.getSelectAllQuery()), () -> assertEquals(" ORDER BY title", film.getOrderByClause())); }
    @Test void testEqualsHashCodeIToString() { LocalDate datum = LocalDate.of(2024, 5, 1); film = new Film("Film", 120, "Opis", datum, List.of()); Film isti = new Film("Film", 90, "Drugi", datum, List.of()); Film razlicit = new Film("Drugi", 120, "Opis", datum, List.of()); assertAll(() -> assertEquals(film, isti), () -> assertEquals(film.hashCode(), isti.hashCode()), () -> assertNotEquals(film, razlicit), () -> assertFalse(film.equals(null)), () -> assertTrue(film.toString().contains("Film")), () -> assertTrue(film.toString().contains("2024"))); }
}
