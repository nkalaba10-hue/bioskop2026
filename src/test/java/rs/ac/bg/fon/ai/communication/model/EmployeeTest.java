package rs.ac.bg.fon.ai.communication.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class EmployeeTest {
    private Employee employee;
    @BeforeEach void setUp() { employee = new Employee(); }
    @AfterEach void tearDown() { employee = null; }
    @Test void testEmployee() { assertNotNull(employee); }
    @Test void testEmployeeStringStringStringString() { employee = new Employee("Petar", "Petrovic", "pera", "lozinka"); assertAll(() -> assertEquals("Petar", employee.getFirstname()), () -> assertEquals("Petrovic", employee.getLastname()), () -> assertEquals("pera", employee.getUsername()), () -> assertEquals("lozinka", employee.getPassword())); }
    @Test void testSeteriIGeteri() { employee.setId(1L); employee.setFirstname("Petar"); employee.setLastname("Petrovic"); employee.setUsername("pera"); employee.setPassword("lozinka"); assertAll(() -> assertEquals(1L, employee.getId()), () -> assertEquals("Petar", employee.getFirstname()), () -> assertEquals("Petrovic", employee.getLastname()), () -> assertEquals("pera", employee.getUsername()), () -> assertEquals("lozinka", employee.getPassword())); }
    @Test void testSqlMetode() { employee = new Employee("Petar", "Petrovic", "pera", "lozinka"); employee.setId(2L); assertAll(() -> assertEquals("employe", employee.getTableName()), () -> assertEquals("firstname, lastname, username, password", employee.getAttributeList()), () -> assertEquals("'Petar', 'Petrovic', 'pera', 'lozinka'", employee.getAttributeValues()), () -> assertEquals("id = 2", employee.getWhereCondition())); }
    @Test void testEqualsHashCodeIToString() { employee = new Employee("Petar", "Petrovic", "pera", "lozinka"); Employee isti = new Employee("Drugi", "Korisnik", "pera", "druga"); Employee razlicit = new Employee("Petar", "Petrovic", "mika", "lozinka"); assertAll(() -> assertEquals(employee, isti), () -> assertEquals(employee.hashCode(), isti.hashCode()), () -> assertNotEquals(employee, razlicit), () -> assertFalse(employee.equals(null)), () -> assertTrue(employee.toString().contains("pera"))); }
}
