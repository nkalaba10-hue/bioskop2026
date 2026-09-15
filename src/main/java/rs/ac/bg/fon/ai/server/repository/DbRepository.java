/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.ai.server.repository;

import java.sql.SQLException;

import rs.ac.bg.fon.ai.communication.model.GenericEntity;


/**
 *
 * @author nkala
 */
public interface DbRepository<T extends GenericEntity> extends Repository<T> {
    // SAMO connection management - nema query metoda
    default void connect() throws SQLException {
        DbConnectionFactory.getInstance().getConnection();
    }
    default void disconnect() throws SQLException {
        DbConnectionFactory.getInstance().getConnection().close();
    }
    default void commit() throws SQLException {
        DbConnectionFactory.getInstance().getConnection().commit();
    }
    default void rollback() throws SQLException {
        DbConnectionFactory.getInstance().getConnection().rollback();
    }
}