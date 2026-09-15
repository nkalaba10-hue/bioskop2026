///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package repository;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import model.Genre;
//
///**
// *
// * @author nkala
// */
//public class RepositoryGenre implements DbRepository<Genre, Long> {
//
//    private Connection connection;
//
//    @Override
//    public List<Genre> getAll() throws Exception {
//        List<Genre> genres = new ArrayList<>();
//        String sql = "SELECT id, name FROM Genre"; // ime tabele i kolona
//        try (PreparedStatement ps = DbConnectionFactory.getInstance().getConnection().prepareStatement(sql);
//             ResultSet rs = ps.executeQuery()) {
//
//            while (rs.next()) {
//                Genre g = new Genre();
//                g.setId(rs.getLong("id"));
//                g.setName(rs.getString("name"));
//                genres.add(g);
//            }
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return genres;
//    }
//
//    @Override
//    public void add(Genre t) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public void edit(Genre t) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public void delete(Genre t) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public Genre getById(Long k) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    
//    @Override
//    public List<Genre> getAll(Long id) throws Exception {
//        List<Genre> genres = new ArrayList<>();
//        String sql = "SELECT g.id, g.name "
//                + "FROM Genre g "
//                + "INNER JOIN Film_Genre fg ON g.id = fg.genre_id "
//                + "WHERE fg.film_id = ?";
//
//        connection = DbConnectionFactory.getInstance().getConnection();
//        PreparedStatement stmt = connection.prepareStatement(sql);
//        stmt.setLong(1, id);
//        ResultSet rs = stmt.executeQuery();
//        while (rs.next()) {
//            Genre genre = new Genre();
//            genre.setId(rs.getLong("id"));
//            genre.setName(rs.getString("name"));
//            genres.add(genre);
//        }
//        rs.close();
//        stmt.close();
//        return genres;
//    }
//}
