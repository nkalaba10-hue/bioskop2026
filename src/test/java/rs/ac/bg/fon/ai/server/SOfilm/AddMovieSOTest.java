package rs.ac.bg.fon.ai.server.SOfilm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.ac.bg.fon.ai.communication.model.Film;

class AddMovieSOTest {
    private AddMovieSO so;
    @BeforeEach void setUp() { so = new AddMovieSO(); }
    @AfterEach void tearDown() { so = null; }
    @Test void testAddMovieSO() { assertNotNull(so); }
    @Test void testPreconditionPogresanTip() { assertEquals("Invalid parameter type - expected Film", assertThrows(Exception.class, () -> so.precondition("film")).getMessage()); }
    @Test void testPreconditionBezNaslova() { assertEquals("Film title is required", assertThrows(Exception.class, () -> so.precondition(new Film())).getMessage()); }
    @Test void testPreconditionNeispravnoTrajanje() { Film film = new Film(); film.setTitle("Film"); film.setDuration(0); assertEquals("Film duration must be positive", assertThrows(Exception.class, () -> so.precondition(film)).getMessage()); }
}
