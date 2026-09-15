/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.ai.client.repository;

import java.sql.SQLException;

/**
 *
 * @author nkala
 */
public interface DbRepository<T, K> extends Repository<T, K> {

    default public void connect() throws SQLException {
        DbConnectionFactory.getInstance().getConnection();
    }

    default public void disconnect() throws SQLException {
        DbConnectionFactory.getInstance().getConnection().close();
    }

    default public void commit() throws SQLException {
        DbConnectionFactory.getInstance().getConnection().commit();
    }

    default public void rollback() throws SQLException {
        DbConnectionFactory.getInstance().getConnection().rollback();
    }
}
