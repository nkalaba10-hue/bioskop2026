/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.client.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Hall;

/**
 *
 * @author nkala
 */
public class RepositoryHall implements DbRepository<Hall, Long> {

    private Connection connection;

    @Override
    public List<Hall> getAll() throws Exception {

        List<Hall> halls = new ArrayList<>();

        try {
            String sql = "SELECT id, name, capacity FROM Hall ORDER BY name";
            Connection conn = DbConnectionFactory.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Hall h = new Hall(rs.getLong("id"), rs.getString("name"), rs.getInt("capacity"));
                halls.add(h);

            }
        } catch (Exception e) {
            System.out.println("Neuspešno učitavanje liste Sala!");
            throw e;
        }
        return halls;
    }

    @Override
    public void add(Hall hall) throws Exception {
        try {
            String upit = "INSERT INTO Hall (capacity,name) VALUES (?,?)";
            connection = DbConnectionFactory.getInstance().getConnection();
            System.out.println("dsaasd");
            PreparedStatement statement = connection.prepareStatement(upit, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, hall.getCapacity());
            statement.setString(2, hall.getName());

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();

            System.out.println("dsaasd");

            if (rs.next()) {
                hall.setId(rs.getLong(1));
            }

            rs.close();
            statement.close();
            System.out.println("Uspesno kreiranje Sale!");
        } catch (SQLException ex) {
            System.out.println("Neuspesno kreiranje Sale!");
            throw ex;
        }
    }

    @Override
    public void edit(Hall t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Hall t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Hall getById(Long k) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Hall> getAll(Long k) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
