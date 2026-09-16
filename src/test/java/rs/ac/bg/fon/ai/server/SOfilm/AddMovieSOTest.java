package rs.ac.bg.fon.ai.server.SOfilm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.ac.bg.fon.ai.communication.model.Film;
import rs.ac.bg.fon.ai.communication.model.Genre;
import rs.ac.bg.fon.ai.server.SOgenre.GetAllGenresSO;
import rs.ac.bg.fon.ai.server.repository.DbConnectionFactory;

class AddMovieSOTest {
    private AddMovieSO so;
    @BeforeEach
    void setUp() {
        so = new AddMovieSO();
    }
    @AfterEach
    void tearDown() {
        so = null;
    }
    @Test
    void testAddMovieSO() {
        assertNotNull(so);
    }
    @Test
    void testPreconditionPogresanTip() {
        assertEquals("Invalid parameter type - expected Film", assertThrows(Exception.class, () -> so.precondition("film")).getMessage());
    }
    @Test
    void testPreconditionBezNaslova() {
        assertEquals("Film title is required", assertThrows(Exception.class, () -> so.precondition(new Film())).getMessage());
    }
    @Test
    void testSetDurationNula() {
        Film film = new Film();
        film.setTitle("Film");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> film.setDuration(0));
        assertEquals("Film duration must be between 1 and 500 minutes", exception.getMessage());
    }

    @Test
    void testExecuteCuvaFilmUBazu() throws Exception {
        GetAllGenresSO getGenresSO = new GetAllGenresSO();
        getGenresSO.execute(null);
        Genre genre = getGenresSO.getResult().get(0);
        Film film = new Film("Test film " + System.nanoTime(), 100, "Test opis",
                LocalDate.now(), List.of(genre));

        try {
            so.execute(film);
        assertNotNull(film.getId());
    } finally {
            if (film.getId() != null) {
                var connection = DbConnectionFactory.getInstance().getConnection();
                try (var deleteGenres = connection.prepareStatement(
                        "DELETE FROM film_genre WHERE film_id = ?");
                        var deleteFilm = connection.prepareStatement(
                                "DELETE FROM film WHERE id = ?")) {
                    deleteGenres.setLong(1, film.getId());
                    deleteGenres.executeUpdate();
        deleteFilm.setLong(1, film.getId());
                    deleteFilm.executeUpdate();
                    connection.commit();
    }
            }
        }
    }
}
