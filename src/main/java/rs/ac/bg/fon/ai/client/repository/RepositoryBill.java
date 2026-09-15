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
import rs.ac.bg.fon.ai.communication.model.Bill;
import rs.ac.bg.fon.ai.communication.model.Employee;
import rs.ac.bg.fon.ai.communication.model.Ticket;

/**
 *
 * @author nkala
 */
public class RepositoryBill implements DbRepository<Bill, Long> {

    private Connection connection;

    @Override
    public List<Bill> getAll() throws Exception {
        List<Bill> bills = new ArrayList<>();
        try {
            connection = DbConnectionFactory.getInstance().getConnection();

            String sql = "SELECT id, date_time, total_amount, saved_by FROM Bill";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Bill bill = new Bill();
                bill.setId(rs.getLong("id"));
                bill.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
                bill.setTotalAmount(rs.getBigDecimal("total_amount"));

                // saved_by može biti NULL
                Long savedById = rs.getLong("saved_by");
                if (!rs.wasNull()) {
                    Employee e = new Employee();
                    e.setId(savedById);
                    bill.setSavedBy(e);
                } else {
                    bill.setSavedBy(null);
                }

                List<Ticket> tickets = Controller.getInstance().getTicketsByBillId(bill.getId());
                
                
                bill.setTickets(tickets);
                bills.add(bill);
                
                for(Ticket t : tickets){
                    t.setBill(bill);
                }
            }

            rs.close();
            statement.close();
            System.out.println("Uspesno ucitavanje svih Bill-ova!");
        } catch (SQLException ex) {
            System.out.println("Neuspesno ucitavanje Bill-ova!");
            throw ex;
        }

        return bills;
    }

    @Override
    public void add(Bill t) throws SQLException {

        try {
            connection = DbConnectionFactory.getInstance().getConnection();

            String sql = "INSERT INTO Bill (date_time, total_amount, saved_by) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // postavljanje parametara
            statement.setTimestamp(1, java.sql.Timestamp.valueOf(t.getDateTime()));
            statement.setBigDecimal(2, t.getTotalAmount());
            if (t.getSavedBy() != null) {
                statement.setLong(3, t.getSavedBy().getId());
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
            System.out.println("Uspesno kreiranje Bill-a!");
        } catch (SQLException ex) {
            System.out.println("Neuspesno kreiranje Bill-a!");
            throw ex;
        }
    }

    @Override
    public void edit(Bill t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Bill t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Bill getById(Long k) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Bill> getAll(Long k) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
