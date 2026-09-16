package rs.ac.bg.fon.ai.communication.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class EmployeTest {
    private Employe employe;
    @BeforeEach void setUp() { employe = new Employe(); }
    @AfterEach void tearDown() { employe = null; }
    @Test void testEmploye() { assertNotNull(employe); }
    @Test void testEmployeStringStringStringString() { employe = new Employe("Petar", "Petrovic", "pera", "lozinka"); assertAll(() -> assertEquals("Petar", employe.getFirstname()), () -> assertEquals("Petrovic", employe.getLastname()), () -> assertEquals("pera", employe.getUsername()), () -> assertEquals("lozinka", employe.getPassword())); }
    @Test void testSeteriIGeteri() { employe.setId(1L); employe.setFirstname("Petar"); employe.setLastname("Petrovic"); employe.setUsername("pera"); employe.setPassword("lozinka"); assertAll(() -> assertEquals(1L, employe.getId()), () -> assertEquals("Petar", employe.getFirstname()), () -> assertEquals("Petrovic", employe.getLastname()), () -> assertEquals("pera", employe.getUsername()), () -> assertEquals("lozinka", employe.getPassword())); }
}
