/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.ai.server.repository;

import java.util.List;

import rs.ac.bg.fon.ai.communication.model.GenericEntity;


/**
 *
 * @author nkala
 */
public interface Repository<T extends GenericEntity> {
    List<T> getAll(T entity) throws Exception;      // Template za sve
    void add(T entity) throws Exception;
    void edit(T entity) throws Exception; 
    void delete(T entity) throws Exception;
    List<T> getByQuery(T entity, String query) throws Exception; // Za sve upite
    T addWithReturn(T entity) throws Exception;
    T getById(T entity, Long id) throws Exception;
}
