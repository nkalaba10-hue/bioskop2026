package rs.ac.bg.fon.ai.communication.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class GenreTest {
    private Genre genre;
    @BeforeEach
    void setUp() {
        genre = new Genre();
    }
    @AfterEach
    void tearDown() {
        genre = null;
    }
    @Test
    void testGenre() {
        assertNotNull(genre);
    }
    @Test
    void testGenreString() {
        genre = new Genre("Drama");
        assertEquals("Drama", genre.getName());
    }
    @Test
    void testSetId() {
        genre.setId(1L);
        assertEquals(1L, genre.getId());
    }
    @Test
    void testSetName() {
        genre.setName("Drama");
        assertEquals("Drama", genre.getName());
    }
    @Test
    void testSqlMetode() {
        genre.setId(2L);
        genre.setName("O'Brien");
        assertAll(() -> assertEquals("genre", genre.getTableName()), () -> assertEquals("name", genre.getAttributeList()), () -> assertEquals("'O''Brien'", genre.getAttributeValues()), () -> assertEquals("name = 'O''Brien'", genre.setAttributeValues()), () -> assertEquals("id = 2", genre.getWhereCondition()), () -> assertEquals("SELECT * FROM genre", genre.getSelectAllQuery()));
    }
    @Test
    void testEqualsHashCodeIToString() {
        genre.setId(1L);
        genre.setName("Drama");
        Genre isti = new Genre("Drama");
        isti.setId(1L);
        assertAll(() -> assertEquals(genre, isti), () -> assertEquals(genre.hashCode(), isti.hashCode()), () -> assertFalse(genre.equals(null)), () -> assertFalse(genre.equals("Drama")), () -> assertEquals("Drama", genre.toString()));
    }

    @Test
    void testSetNamePrazanString() {
        assertThrows(IllegalArgumentException.class, () -> genre.setName(" "));
    }

    @Test
    void testSetNameNull() {
        assertThrows(NullPointerException.class, () -> genre.setName(null));
    }
}
