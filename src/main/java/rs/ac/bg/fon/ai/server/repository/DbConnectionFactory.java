/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import rs.ac.bg.fon.ai.server.constants.DatabaseConfigManager;

/**
 *
 * @author nkala
 */
public class DbConnectionFactory {

    private Connection connection;
    private static DbConnectionFactory instance;

    private DbConnectionFactory() {

    }

    public static DbConnectionFactory getInstance() {
        if (instance == null) {
            instance = new DbConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                // UZMI PARAMETRE IZ CONFIG FAJLA
                String url = DatabaseConfigManager.getUrl();
                String username = DatabaseConfigManager.getUsername();
                String password = DatabaseConfigManager.getPassword();

                connection = DriverManager.getConnection(url, username, password);
                connection.setAutoCommit(false);
                System.out.println("Konekcija uspostavljena: " + url);
            }
        } catch (Exception ex) {
            throw new SQLException("Greška pri konekciji na bazu: " + ex.getMessage());
        }
        return connection;
    }
}
