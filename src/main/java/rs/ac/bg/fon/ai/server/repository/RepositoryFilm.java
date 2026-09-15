///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package repository;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import logic.Controller;
//import model.Film;
//import model.Genre;
//
///**
// *
// * @author nkala
// */
//public class RepositoryFilm implements DbRepository<Film, Long> {
//
//    private Connection connection;
//
//    @Override
//    public void add(Film f) throws Exception {
//
//        String sqlFilm = "INSERT INTO film (title, duration,description, release_date) VALUES (?, ?, ?, ? )";
//        String sqlFilmGenre = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";
//
//        connection = DbConnectionFactory.getInstance().getConnection();
//
//        try (PreparedStatement psFilm = connection.prepareStatement(sqlFilm, Statement.RETURN_GENERATED_KEYS); PreparedStatement psFilmGenre = connection.prepareStatement(sqlFilmGenre)) {
//
//            psFilm.setString(1, f.getTitle());
//            psFilm.setString(3, f.getDescription());
//            psFilm.setInt(2, f.getDuration());
//            psFilm.setDate(4, Date.valueOf(f.getReleaseDate()));
//
//            psFilm.executeUpdate();
//
//            ResultSet rs = psFilm.getGeneratedKeys();
//            if (rs.next()) {
//                Long filmId = rs.getLong(1);
//                for (Genre g : f.getGenres()) {
//                    psFilmGenre.setLong(1, filmId);
//                    psFilmGenre.setLong(2, g.getId());
//                    psFilmGenre.addBatch();
//                }
//                psFilmGenre.executeBatch();
//            }
//
//            connection.commit();
//
//        } catch (SQLException e) {
//
//            throw e;
//        }
//    }
//
//    @Override
//    public void edit(Film t) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public void delete(Film t) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public Film getById(Long k) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public List<Film> getAll(Long k) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public List<Film> getAll() throws Exception {
//        List<Film> films = new ArrayList<>();
//        try {
//            String upit = """
//            SELECT 
//                f.id AS fid,
//                f.title AS ftitle,
//                f.description AS fdesc,
//                f.release_date AS frelease,
//                f.duration AS fduration
//            FROM Film f
//        """;
//
//            connection = DbConnectionFactory.getInstance().getConnection();
//            Statement statement = connection.createStatement();
//            ResultSet rs = statement.executeQuery(upit);
//
//            while (rs.next()) {
//                Film film = new Film();
//                film.setId(rs.getLong("fid"));
//                film.setTitle(rs.getString("ftitle"));
//                film.setDescription(rs.getString("fdesc"));
//                film.setReleaseDate(rs.getDate("frelease").toLocalDate());
//                film.setDuration(rs.getInt("fduration"));
//
//                // --- Učitaj žanrove za film ---
//                List<Genre> genres = Controller.getInstance().getGenresByID(film.getId());
//                film.setGenres(genres);
//
//                films.add(film);
//            }
//
//            rs.close();
//            statement.close();
//            System.out.println("Uspešno učitana lista Filmova!");
//        } catch (SQLException ex) {
//            System.out.println("Neuspešno učitavanje liste Filmova!");
//            throw ex;
//        }
//
//        return films;
//    }
//
//}
