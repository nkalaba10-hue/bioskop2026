/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.ai.client.repository;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author nkala
 */
public interface Repository<T, K> {

    List<T> getAll() throws Exception;

    void add(T t) throws Exception;

    void edit(T t) throws Exception;

    void delete(T t) throws Exception;

    T getById(K k) throws Exception;

    List<T> getAll(K k) throws Exception;
    
    default List<T> getByDate(LocalDate date) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    default void addAll(List<T> data) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    default void update(T t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
