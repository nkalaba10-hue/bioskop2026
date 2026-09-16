package rs.ac.bg.fon.ai.communication.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class EmployeTest {
    private Employe employe;
    @BeforeEach
    void setUp() {
        employe = new Employe();
    }
    @AfterEach
    void tearDown() {
        employe = null;
    }
    @Test
    void testEmploye() {
        assertNotNull(employe);
    }
    @Test
    void testEmployeStringStringStringString() {
        employe = new Employe("Petar", "Petrovic", "pera", "lozinka");
        assertAll(() -> assertEquals("Petar", employe.getFirstname()), () -> assertEquals("Petrovic", employe.getLastname()), () -> assertEquals("pera", employe.getUsername()), () -> assertEquals("lozinka", employe.getPassword()));
    }
    @Test
    void testSetId() {
        employe.setId(1L);
        assertEquals(1L, employe.getId());
    }
    @Test
    void testSetFirstname() {
        employe.setFirstname("Petar");
        assertEquals("Petar", employe.getFirstname());
    }
    @Test
    void testSetLastname() {
        employe.setLastname("Petrovic");
        assertEquals("Petrovic", employe.getLastname());
    }
    @Test
    void testSetUsername() {
        employe.setUsername("pera");
        assertEquals("pera", employe.getUsername());
    }
    @Test
    void testSetPassword() {
        employe.setPassword("lozinka");
        assertEquals("lozinka", employe.getPassword());
    }

    @Test
    void testSetPasswordNull() {
        assertThrows(NullPointerException.class, () -> employe.setPassword(null));
    }

    @Test
    void testSetFirstnamePrazanString() {
        assertThrows(IllegalArgumentException.class, () -> employe.setFirstname(" "));
    }

    @Test
    void testSetLastnameNull() {
        assertThrows(NullPointerException.class, () -> employe.setLastname(null));
    }

    @Test
    void testSetUsernameNull() {
        assertThrows(NullPointerException.class, () -> employe.setUsername(null));
    }
}
