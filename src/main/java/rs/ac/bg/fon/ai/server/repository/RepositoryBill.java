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
//import model.Bill;
//import model.Employe;
//import model.Ticket;
//
///**
// *
// * @author nkala
// */
//public class RepositoryBill implements DbRepository<Bill, Long> {
//
//    private Connection connection;
//
//    @Override
//    public List<Bill> getAll() throws Exception {
//        List<Bill> bills = new ArrayList<>();
//        try {
//            connection = DbConnectionFactory.getInstance().getConnection();
//
//            String sql = "SELECT b.id, b.date_time, b.total_amount, b.saved_by,e.id,e.firstname,e.lastname, e.username, e.password FROM Bill b"
//                    + " LEFT JOIN Employe e ON b.saved_by = e.id ";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            ResultSet rs = statement.executeQuery();
//
//            while (rs.next()) {
//                Bill bill = new Bill();
//                bill.setId(rs.getLong("b.id"));
//                bill.setDateTime(rs.getTimestamp("b.date_time").toLocalDateTime());
//                bill.setTotalAmount(rs.getBigDecimal("b.total_amount"));
//
//                // saved_by može biti NULL
//                Long savedById = rs.getLong("b.saved_by");
//                if (!rs.wasNull()) {
//                    Employe e = new Employe();
//                    e.setId(savedById);
//                    e.setFirstname(rs.getString("e.firstname"));
//                    e.setLastname(rs.getString("e.lastname"));
//                    e.setUsername(rs.getString("e.username"));
//                    e.setPassword(rs.getString("e.password"));
//
//                    bill.setSavedBy(e);
//                } else {
//                    bill.setSavedBy(null);
//                }
//
//                List<Ticket> tickets = Controller.getInstance().getTicketsByBillId(bill.getId());
//                
//                
//                bill.setTickets(tickets);
//                bills.add(bill);
//                
//                for(Ticket t : tickets){
//                    t.setBill(bill);
//                }
//            }
//
//            rs.close();
//            statement.close();
//            System.out.println("Uspesno ucitavanje svih Bill-ova!");
//        } catch (SQLException ex) {
//            System.out.println("Neuspesno ucitavanje Bill-ova!");
//            throw ex;
//        }
//
//        return bills;
//    }
//
//    @Override
//    public void add(Bill b) throws SQLException {
//
//        try {
//            connection = DbConnectionFactory.getInstance().getConnection();
//
//            String sql = "INSERT INTO Bill (date_time, total_amount, saved_by) VALUES (?, ?, ?)";
//            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//
//            // postavljanje parametara
//            statement.setTimestamp(1, java.sql.Timestamp.valueOf(b.getDateTime()));
//            statement.setBigDecimal(2, b.getTotalAmount());
//            if (b.getSavedBy() != null) {
//                statement.setLong(3, b.getSavedBy().getId());
//            } else {
//                statement.setNull(3, java.sql.Types.BIGINT);
//            }
//
//            statement.executeUpdate();
//
//            // dohvat generisanog ID-a
//            ResultSet rs = statement.getGeneratedKeys();
//            if (rs.next()) {
//                b.setId(rs.getLong(1));
//            }
//
//            rs.close();
//            statement.close();
//            System.out.println("Uspesno kreiranje Bill-a!");
//        } catch (SQLException ex) {
//            System.out.println("Neuspesno kreiranje Bill-a!");
//            throw ex;
//        }
//    }
//
//    @Override
//    public void edit(Bill t) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public void delete(Bill t) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public Bill getById(Long k) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public List<Bill> getAll(Long k) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//}
