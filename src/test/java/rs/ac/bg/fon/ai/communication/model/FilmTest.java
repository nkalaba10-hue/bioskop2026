package rs.ac.bg.fon.ai.communication.model;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.*;

class FilmTest {
    private Film film;
    @BeforeEach
    void setUp() {
        film = new Film();
    }
    @AfterEach
    void tearDown() {
        film = null;
    }
    @Test
    void testFilm() {
        assertNotNull(film);
    }
    @Test
    void testFilmStringIntStringLocalDateList() {
        LocalDate datum = LocalDate.of(2024, 5, 1);
        List<Genre> zanrovi = List.of(new Genre("Drama"));
        film = new Film("Film", 120, "Opis", datum, zanrovi);
        assertAll(() -> assertEquals("Film", film.getTitle()), () -> assertEquals(120, film.getDuration()), () -> assertEquals("Opis", film.getDescription()), () -> assertEquals(datum, film.getReleaseDate()), () -> assertEquals(zanrovi, film.getGenres()));
    }
    @Test
    void testSetId() {
        film.setId(1L);
        assertEquals(1L, film.getId());
    }
    @Test
    void testSetTitle() {
        film.setTitle("Film");
        assertEquals("Film", film.getTitle());
    }
    @Test
    void testSetDuration() {
        film.setDuration(120);
        assertEquals(120, film.getDuration());
    }
    @Test
    void testSetDescription() {
        film.setDescription("Opis");
        assertEquals("Opis", film.getDescription());
    }
    @Test
    void testSetReleaseDate() {
        LocalDate datum = LocalDate.of(2024, 5, 1);
        film.setReleaseDate(datum);
        assertEquals(datum, film.getReleaseDate());
    }
    @Test
    void testSetGenres() {
        List<Genre> zanrovi = List.of(new Genre("Drama"));
        film.setGenres(zanrovi);
        assertEquals(zanrovi, film.getGenres());
    }
    @Test
    void testSqlMetode() {
        film = new Film("O'Brien", 120, "Opis", LocalDate.of(2024, 5, 1), List.of());
        film.setId(2L);
        assertAll(() -> assertEquals("film", film.getTableName()), () -> assertEquals("title, duration, description, release_date", film.getAttributeList()), () -> assertEquals("'O''Brien', 120, 'Opis', '2024-05-01'", film.getAttributeValues()), () -> assertEquals("title = 'O''Brien', duration = 120, description = 'Opis', release_date = '2024-05-01'", film.setAttributeValues()), () -> assertEquals("id = 2", film.getWhereCondition()), () -> assertEquals("SELECT * FROM film", film.getSelectAllQuery()), () -> assertEquals(" ORDER BY title", film.getOrderByClause()));
    }
    @Test
    void testEqualsHashCodeIToString() {
        LocalDate datum = LocalDate.of(2024, 5, 1);
        film = new Film("Film", 120, "Opis", datum, List.of());
        Film isti = new Film("Film", 90, "Drugi", datum, List.of());
        Film razlicit = new Film("Drugi", 120, "Opis", datum, List.of());
        assertAll(() -> assertEquals(film, isti), () -> assertEquals(film.hashCode(), isti.hashCode()), () -> assertNotEquals(film, razlicit), () -> assertFalse(film.equals(null)), () -> assertTrue(film.toString().contains("Film")), () -> assertTrue(film.toString().contains("2024")));
    }

    @Test
    void testSetTitleNull() {
        assertThrows(NullPointerException.class, () -> film.setTitle(null));
    }

    @Test
    void testSetDurationPrevelikaVrednost() {
        assertThrows(IllegalArgumentException.class, () -> film.setDuration(501));
    }

    @Test
    void testSetTitlePrazanString() {
        assertThrows(IllegalArgumentException.class, () -> film.setTitle(" "));
    }

    @Test
    void testSetDurationNula() {
        assertThrows(IllegalArgumentException.class, () -> film.setDuration(0));
    }

    @Test
    void testSetDescriptionPredugacak() {
        assertThrows(IllegalArgumentException.class, () -> film.setDescription("x".repeat(256)));
    }
}
