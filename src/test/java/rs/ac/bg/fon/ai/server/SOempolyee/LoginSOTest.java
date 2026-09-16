package rs.ac.bg.fon.ai.server.SOempolyee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.ac.bg.fon.ai.server.repository.DbConnectionFactory;


class LoginSOTest {

    private LoginSO loginSO;

    @BeforeEach
    void setUp() {
        loginSO = new LoginSO();
    }

    @AfterEach
    void tearDown() {
        loginSO = null;
    }

    @Test
    void testLoginSO() {
        assertNotNull(loginSO);
    }

    @Test
    void testLoginParams() {
        LoginSO.LoginParams params = new LoginSO.LoginParams("nikola", "lozinka");
        assertEquals("nikola", params.getUsername());
        assertEquals("lozinka", params.getPassword());
    }

    @Test
    void testPreconditionIspravniParametri() {
        LoginSO.LoginParams params = new LoginSO.LoginParams("nikola", "lozinka");
        assertDoesNotThrow(() -> loginSO.precondition(params));
    }

    @Test
    void testPreconditionPogresanTipParametra() {
        Exception e = assertThrows(Exception.class,
                () -> loginSO.precondition("nikola"));
        assertEquals("Invalid parameter type - expected LoginParams", e.getMessage());
    }

    @Test
    void testPreconditionNullUsername() {
        LoginSO.LoginParams params = new LoginSO.LoginParams(null, "lozinka");

        Exception e = assertThrows(Exception.class,
                () -> loginSO.precondition(params));
        assertEquals("Username is required", e.getMessage());
    }

    @Test
    void testPreconditionPrazanUsername() {
        LoginSO.LoginParams params = new LoginSO.LoginParams("   ", "lozinka");

        Exception e = assertThrows(Exception.class,
                () -> loginSO.precondition(params));
        assertEquals("Username is required", e.getMessage());
    }

    @Test
    void testPreconditionNullPassword() {
        LoginSO.LoginParams params = new LoginSO.LoginParams("nikola", null);

        Exception e = assertThrows(Exception.class,
                () -> loginSO.precondition(params));
        assertEquals("Password is required", e.getMessage());
    }

    @Test
    void testPreconditionPrazanPassword() {
        LoginSO.LoginParams params = new LoginSO.LoginParams("nikola", "   ");

        Exception e = assertThrows(Exception.class,
                () -> loginSO.precondition(params));
        assertEquals("Password is required", e.getMessage());
    }

    @Test
    void testExecutePrijavljujePostojecegZaposlenog() throws Exception {
        String username;
        String password;
        var connection = DbConnectionFactory.getInstance().getConnection();

        try (var statement = connection.prepareStatement(
                "SELECT username, password FROM employe LIMIT 1");
                var resultSet = statement.executeQuery()) {
            assertTrue(resultSet.next(), "Baza mora imati zaposlenog za login test");
            username = resultSet.getString("username");
            password = resultSet.getString("password");
    }

        loginSO.execute(new LoginSO.LoginParams(username, password));
        assertNotNull(loginSO.getResult());
        assertEquals(username, loginSO.getResult().getUsername());
    }
}

