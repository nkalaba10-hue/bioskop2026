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

import rs.ac.bg.fon.ai.client.logic.Controller;
import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.communication.model.Ticket;

/**
 *
 * @author nkala
 */
public class RepositoryTicket implements DbRepository<Ticket, Long> {

    private Connection connection;

    @Override
    public List<Ticket> getAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void add(Ticket t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void edit(Ticket t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Ticket t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Ticket getById(Long k) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Ticket> getAll(Long k) throws Exception {
        List<Ticket> tickets = new ArrayList<>();
        try {
            connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "SELECT id, projection_id, price, bill_id FROM Ticket WHERE bill_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, k);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ticket t = new Ticket();
                t.setId(rs.getLong("id"));
                t.setPrice(rs.getBigDecimal("price"));
                Long projectionId = rs.getLong("projection_id");
                Projection projection = Controller.getInstance().getProjectionById(projectionId); // nova metoda
                t.setProjection(projection);
                tickets.add(t);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw e;
        }

        return tickets;
    }

    @Override
    public void addAll(List<Ticket> data) throws SQLException {

        try {
            connection = DbConnectionFactory.getInstance().getConnection();

            for (Ticket t : data) {
                String sql = "INSERT INTO Ticket (projection_id, price, bill_id) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                // postavljanje parametara
                statement.setLong(1, t.getProjection().getId());
                statement.setBigDecimal(2, t.getPrice());
                if (t.getBill() != null) {
                    statement.setLong(3, t.getBill().getId());
                } else {
                    statement.setNull(3, java.sql.Types.BIGINT);
                }

                statement.executeUpdate();

                // dohvat generisanog ID-a
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    t.setId(rs.getLong(1));
                }

                rs.close();
                statement.close();
                System.out.println("Uspesno kreiranje Ticket-a!");
            }

        } catch (SQLException ex) {
            System.out.println("Neuspesno kreiranje Ticket-a!");
            throw ex;

        }

    }
}
