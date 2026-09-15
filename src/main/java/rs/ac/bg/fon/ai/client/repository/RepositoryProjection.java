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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.fon.ai.client.logic.Controller;
import rs.ac.bg.fon.ai.communication.model.Film;
import rs.ac.bg.fon.ai.communication.model.Genre;
import rs.ac.bg.fon.ai.communication.model.Hall;
import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.communication.model.ProjectionStatus;

/**
 *
 * @author nkala
 */
public class RepositoryProjection implements DbRepository<Projection, Long> {

    private Connection connection;

    @Override
    public List<Projection> getAll() throws Exception {

        List<Projection> projections = new ArrayList<>();
        try {
            String sql = "SELECT pr.id AS prid, pr.date AS pdate, pr.time AS ptime, pr.status AS pstatus, "
                    + "pr.price AS pprice, pr.sold_tickets AS psold, "
                    + "f.id AS fid, f.title AS fname, f.release_date AS frelease, f.duration AS fduration, f.description AS fdescription, "
                    + "h.id AS hid, h.name AS hname, h.capacity AS hcapacity "
                    + "FROM Projection pr "
                    + "INNER JOIN Film f ON pr.film_id = f.id "
                    + "INNER JOIN Hall h ON pr.hall_id = h.id";

            connection = DbConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                // ---- Projection ----
                Projection p = new Projection();
                p.setId(rs.getLong("prid"));
                p.setDate(rs.getDate("pdate").toLocalDate());
                p.setTime(rs.getTime("ptime").toLocalTime());
                p.setStatus(ProjectionStatus.valueOf(rs.getString("pstatus")));
                p.setPrice(rs.getBigDecimal("pprice"));
                p.setSoldTickets(rs.getInt("psold"));

                // ---- Film ----
                Film f = new Film();
                f.setId(rs.getLong("fid"));
                f.setTitle(rs.getString("fname"));
                f.setReleaseDate(rs.getDate("frelease").toLocalDate());
                f.setDuration(rs.getInt("fduration"));
                f.setDescription(rs.getString("fdescription"));

                // ---- Žanrovi ----
                List<Genre> genres = Controller.getInstance().getGenresByID(f.getId()); // vidi ispod
                f.setGenres(genres);

                p.setFilm(f);

                // ---- Hall ----
                Hall h = new Hall();
                h.setId(rs.getLong("hid"));
                h.setName(rs.getString("hname"));
                h.setCapacity(rs.getInt("hcapacity"));
                p.setHall(h);

                projections.add(p);
            }

            rs.close();
            statement.close();
            System.out.println("Uspesno ucitavanje liste Projection!");
        } catch (SQLException ex) {
            System.out.println("Neuspesno ucitavanje liste Projection!");
            throw ex;
        }
        return projections;
    }

    @Override
    public void add(Projection t) throws Exception {
        try {
            String sql = "INSERT INTO Projection (film_id, hall_id, date, time, status, price, sold_tickets) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            connection = DbConnectionFactory.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // postavljanje parametara
            statement.setLong(1, t.getFilm().getId());
            statement.setLong(2, t.getHall().getId());
            statement.setDate(3, java.sql.Date.valueOf(t.getDate()));
            statement.setTime(4, java.sql.Time.valueOf(t.getTime()));
            statement.setString(5, t.getStatus().toString());
            statement.setBigDecimal(6, t.getPrice());
            statement.setInt(7, t.getSoldTickets());

            statement.executeUpdate();

            // dohvat generisanog ID-a
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getLong(1));
            }

            rs.close();
            statement.close();
            System.out.println("Uspesno kreiranje Projekcije!");
        } catch (SQLException ex) {
            System.out.println("Neuspesno kreiranje Projekcije!");
            throw ex;
        }
    }

    @Override
    public void edit(Projection t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Projection t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Projection getById(Long k) throws Exception {
        Projection p = null;

        try {
            String sql = "SELECT pr.id AS prid, pr.date AS pdate, pr.time AS ptime, pr.status AS pstatus, "
                    + "pr.price AS pprice, pr.sold_tickets AS psold, "
                    + "f.id AS fid, f.title AS fname, f.release_date AS frelease, f.duration AS fduration, f.description AS fdescription, "
                    + "h.id AS hid, h.name AS hname, h.capacity AS hcapacity "
                    + "FROM Projection pr "
                    + "INNER JOIN Film f ON pr.film_id = f.id "
                    + "INNER JOIN Hall h ON pr.hall_id = h.id "
                    + "WHERE pr.id = ?";

            connection = DbConnectionFactory.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, k);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // ---- Projection ----
                p = new Projection();
                p.setId(rs.getLong("prid"));
                p.setDate(rs.getDate("pdate").toLocalDate());
                p.setTime(rs.getTime("ptime").toLocalTime());
                p.setStatus(ProjectionStatus.valueOf(rs.getString("pstatus")));
                p.setPrice(rs.getBigDecimal("pprice"));
                p.setSoldTickets(rs.getInt("psold"));

                // ---- Film ----
                Film f = new Film();
                f.setId(rs.getLong("fid"));
                f.setTitle(rs.getString("fname"));
                f.setReleaseDate(rs.getDate("frelease").toLocalDate());
                f.setDuration(rs.getInt("fduration"));
                f.setDescription(rs.getString("fdescription"));

                // ---- Žanrovi ----
                List<Genre> genres = Controller.getInstance().getGenresByID(f.getId());
                f.setGenres(genres);
                p.setFilm(f);

                // ---- Hall ----
                Hall h = new Hall();
                h.setId(rs.getLong("hid"));
                h.setName(rs.getString("hname"));
                h.setCapacity(rs.getInt("hcapacity"));
                p.setHall(h);
            }

            rs.close();
            ps.close();
            System.out.println("Uspesno ucitana projekcija ID = " + k);
        } catch (SQLException ex) {
            System.out.println("Neuspesno ucitavanje projekcije ID = " + k);
            throw ex;
        }

        return p;
    }

    @Override
    public List<Projection> getAll(Long k) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Projection t) throws SQLException {
        String sql = "UPDATE Projection SET sold_tickets = ? WHERE id = ?";
        PreparedStatement ps = DbConnectionFactory.getInstance().getConnection().prepareStatement(sql);
        ps.setInt(1, t.getSoldTickets());
        ps.setLong(2, t.getId());
        ps.executeUpdate();
    }

    @Override
    public List<Projection> getByDate(LocalDate date) throws Exception {
        List<Projection> projections = new ArrayList<>();

        try {
            connection = DbConnectionFactory.getInstance().getConnection();

            String sql = "SELECT pr.id AS prid, pr.date AS pdate, pr.time AS ptime, pr.status AS pstatus, "
                    + "pr.price AS pprice, pr.sold_tickets AS psold, "
                    + "f.id AS fid, f.title AS fname, f.release_date AS frelease, f.duration AS fduration, f.description AS fdescription, "
                    + "h.id AS hid, h.name AS hname, h.capacity AS hcapacity "
                    + "FROM Projection pr "
                    + "INNER JOIN Film f ON pr.film_id = f.id "
                    + "INNER JOIN Hall h ON pr.hall_id = h.id "
                    + "WHERE DATE(pr.date) = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, java.sql.Date.valueOf(date));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Film f = new Film();
                f.setId(rs.getLong("fid"));
                f.setTitle(rs.getString("fname"));
                f.setReleaseDate(rs.getDate("frelease").toLocalDate());
                f.setDuration(rs.getInt("fduration"));
                f.setDescription(rs.getString("fdescription"));

                // ---- Žanrovi ----
                List<Genre> genres = Controller.getInstance().getGenresByID(f.getId());
                f.setGenres(genres);
               

                // ---- Hall ----
                Hall h = new Hall();
                h.setId(rs.getLong("hid"));
                h.setName(rs.getString("hname"));
                h.setCapacity(rs.getInt("hcapacity"));
               

                Projection p = new Projection();
                p.setId(rs.getLong("prid"));
                p.setDate(rs.getDate("pdate").toLocalDate());
                p.setTime(rs.getTime("ptime").toLocalTime());
                p.setStatus(ProjectionStatus.valueOf(rs.getString("pstatus")));
                p.setPrice(rs.getBigDecimal("pprice"));
                p.setSoldTickets(rs.getInt("psold"));
                p.setFilm(f);
                p.setHall(h);

                projections.add(p);
            }

        } catch (Exception ex) {
            throw new Exception("Error while loading projections for date: " + date, ex);
        }

        return projections;
    }

}
