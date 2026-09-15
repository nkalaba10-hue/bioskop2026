///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package repository;
//
//import java.util.List;
//import model.Hall;
//import java.sql.*;
//import java.util.ArrayList;
//
///**
// *
// * @author nkala
// */
//public class RepositoryHall implements DbRepository<Hall, Long> {
//
//    private Connection connection;
//
//    @Override
//    public boolean hasUpcomingProjectionsForHall(Long id) throws SQLException {
//        String sql = "SELECT COUNT(*) FROM projection WHERE hall_id = ? AND date >= CURDATE()";
//
//        try (PreparedStatement stmt = DbConnectionFactory.getInstance().getConnection().prepareStatement(sql)) {
//            stmt.setLong(1, id);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                return rs.getLong(1) > 0;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public List<Hall> getAll() throws Exception {
//
//        List<Hall> halls = new ArrayList<>();
//
//        try {
//            String sql = "SELECT id, name, capacity FROM Hall ORDER BY name";
//            Connection conn = DbConnectionFactory.getInstance().getConnection();
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                Hall h = new Hall(rs.getString("name"), rs.getInt("capacity"));
//                h.setId(rs.getLong("id"));
//                halls.add(h);
//
//            }
//            System.out.println("Ucitane Hale");
//        } catch (Exception e) {
//            System.out.println("Neuspešno učitavanje liste Sala!");
//            throw e;
//        }
//        return halls;
//    }
//
//    @Override
//    public void add(Hall hall) throws Exception {
//        try {
//            String upit = "INSERT INTO Hall (capacity,name) VALUES (?,?)";
//            connection = DbConnectionFactory.getInstance().getConnection();
//            System.out.println("dsaasd");
//            PreparedStatement statement = connection.prepareStatement(upit, Statement.RETURN_GENERATED_KEYS);
//
//            statement.setInt(1, hall.getCapacity());
//            statement.setString(2, hall.getName());
//
//            statement.executeUpdate();
//
//            ResultSet rs = statement.getGeneratedKeys();
//
//            System.out.println("dsaasd");
//
//            if (rs.next()) {
//                hall.setId(rs.getLong(1));
//            }
//
//            rs.close();
//            statement.close();
//            System.out.println("Uspesno kreiranje Sale!");
//        } catch (SQLException ex) {
//            System.out.println("Neuspesno kreiranje Sale!");
//            throw ex;
//        }
//    }
//
//    @Override
//    public void edit(Hall hall) throws Exception {
//        String sql = "UPDATE hall SET name = ?, capacity = ? WHERE id = ?";
//
//        connection = DbConnectionFactory.getInstance().getConnection();
//        
//        PreparedStatement statement = null;
//
//        try {
//            
//            statement = connection.prepareStatement(sql);
//
//            statement.setString(1, hall.getName());
//            statement.setInt(2, hall.getCapacity());
//            statement.setLong(3, hall.getId());
//
//            statement.executeUpdate();
//            statement.close();
//            System.out.println("uspesan update");
//        } catch (Exception e) {
//            throw e;
//        } 
//    }
//
//    @Override
//    public void delete(Hall hall) throws Exception {
//        String sql = "DELETE FROM hall WHERE id = ?";
//        connection = DbConnectionFactory.getInstance().getConnection();
//
//        PreparedStatement statement = null;
//
//        try {
//            
//            statement = connection.prepareStatement(sql);
//
//            statement.setLong(1, hall.getId());
//            statement.executeUpdate();
//            statement.close();
//            System.out.println("uspesno obrisano");
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//
//    @Override
//    public Hall getById(Long k) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public List<Hall> getAll(Long k) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//}
