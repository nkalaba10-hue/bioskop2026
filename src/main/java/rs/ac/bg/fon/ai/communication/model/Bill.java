    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.communication.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;



/**
 *
 * @author nkala
 */
public class Bill implements GenericEntity {

    private Long id;
    private LocalDateTime dateTime;
    private List<Ticket> tickets;
    private BigDecimal totalAmount;
    private Employee savedBy;

    // Konstruktori
    public Bill() {
    }

    public Bill(LocalDateTime dateTime, List<Ticket> tickets, BigDecimal totalAmount, Employee savedBy) {
        this.dateTime = dateTime;
        this.tickets = tickets;
        this.totalAmount = totalAmount;
        this.savedBy = savedBy;
    }

    // Getters and setters
    @Override
	public Long getId() {
        return id;
    }

    @Override
	public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Employee getSavedBy() {
        return savedBy;
    }

    public void setSavedBy(Employee savedBy) {
        this.savedBy = savedBy;
    }

    @Override
    public String getTableName() {
        return "bill";
    }

    @Override
    public String getAttributeList() {
        StringBuilder sb = new StringBuilder();
        sb.append("date_time, total_amount, saved_by");
        return sb.toString();
    }

    @Override
    public String getAttributeValues() {
        StringBuilder sb = new StringBuilder();
        sb.append(sqlValue(dateTime)).append(", ")
                .append(totalAmount != null ? totalAmount : "0.00").append(", ")
                .append(savedBy != null ? savedBy.getId() : "NULL");
        return sb.toString();
    }

    @Override
    public String setAttributeValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("date_time = ").append(sqlValue(dateTime)).append(", ")
                .append("total_amount = ").append(totalAmount != null ? totalAmount : "0.00").append(", ")
                .append("saved_by = ").append(savedBy != null ? savedBy.getId() : "NULL");
        return sb.toString();
    }

    @Override
    public String getWhereCondition() {
        StringBuilder sb = new StringBuilder();
        sb.append("id = ").append(id);
        return sb.toString();
    }

    @Override
    public String getSelectAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT b.*, ")
                .append("e.id as employee_id, e.firstname, e.lastname, e.username, e.password ")
                .append("FROM bill b ")
                .append("LEFT JOIN employe e ON b.saved_by = e.id ");
                //.append(getOrderByClause());
        return sb.toString();
    }

    @Override
    public GenericEntity mapResultSetToObject(ResultSet rs) throws Exception {
        Bill bill = new Bill();

        // Osnovni podaci računa
        bill.setId(rs.getLong("id"));
        bill.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
        bill.setTotalAmount(rs.getBigDecimal("total_amount"));

        // Employee objekat (ako postoji)
        Long savedById = rs.getLong("saved_by");
        if (!rs.wasNull()) {
            Employee employee = new Employee();
            employee.setId(savedById);
            employee.setFirstname(rs.getString("firstname"));
            employee.setLastname(rs.getString("lastname"));
            employee.setUsername(rs.getString("username"));
            employee.setPassword(rs.getString("password"));
            bill.setSavedBy(employee);
        }

        // NOTE: Ticketi se ne učitavaju ovde - učitavaju se u GetTicketsByBillIdSO
        // Ticketi će se učitati kada budu potrebni
        return bill;
    }

    @Override
    public String getOrderByClause() {
        return " ORDER BY b.date_time DESC";
    }

    // hashCode, equals i toString metode
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.dateTime);
        hash = 17 * hash + Objects.hashCode(this.totalAmount);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Bill other = (Bill) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.dateTime, other.dateTime);
    }

    @Override
    public String toString() {
        return "Bill #" + id + " - " + totalAmount + " (" + dateTime + ")";
    }
}
