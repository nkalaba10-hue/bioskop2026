/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.communication.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Objects;

/**
 * Predstavlja kartu za jednu projekciju koja pripada odredjenom racunu.
 *
 * @author nkala
 * @version 1.0
 */
public class Ticket implements GenericEntity {

    /** Moguce akcije koje se mogu izvrsiti nad tiketom. */
    public enum Action {
        ADD, DELETE, UPDATE, NONE
    }

    private Action action = Action.NONE;

    // ... ostala polja
    /** @return akcija koja je oznacena nad tiketom. */
    public Action getAction() {
        return action;
    }

    /** @param action nova akcija nad tiketom. */
    public void setAction(Action action) {
        this.action = action;
    }

    private Long id;
    private Projection projection;
    private BigDecimal price;
    private Bill bill; // račun kome pripada

    // Konstruktori
    /** Kreira prazan tiket. */
    public Ticket() {
    }

    /**
     * Kreira tiket za projekciju po zadatoj ceni i vezuje ga za racun.
     *
     * @param projection projekcija za koju se tiket izdaje
     * @param price cena tiketa
     * @param bill racun kome tiket pripada
     */
    public Ticket(Projection projection, BigDecimal price, Bill bill) {
        this.projection = projection;
        this.price = price;
        this.bill = bill;
    }

    /** @return identifikator tiketa. */
    @Override
	public Long getId() {
        return id;
    }

    /** @param id novi identifikator tiketa. */
    @Override
	public void setId(Long id) {
        this.id = id;
    }

    /** @return projekcija za koju tiket vazi. */
    public Projection getProjection() {
        return projection;
    }

    /** @param projection projekcija za koju tiket vazi. */
    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    /** @return cena tiketa. */
    public BigDecimal getPrice() {
        return price;
    }

    /** @param price nova cena tiketa. */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /** @return racun kome tiket pripada. */
    public Bill getBill() {
        return bill;
    }

    /** @param bill racun kome tiket pripada. */
    public void setBill(Bill bill) {
        this.bill = bill;
    }

    @Override
    public String getTableName() {
        return "ticket";
    }

    @Override
    public String getAttributeList() {
        StringBuilder sb = new StringBuilder();
        sb.append("projection_id, price, bill_id");
        return sb.toString();
    }

    @Override
    public String getAttributeValues() {
        StringBuilder sb = new StringBuilder();
        sb.append(projection != null ? projection.getId() : "NULL").append(", ")
                .append(price != null ? price : "0.00").append(", ")
                .append(bill != null ? bill.getId() : "NULL");
        return sb.toString();
    }

    @Override
    public String setAttributeValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("projection_id = ").append(projection != null ? projection.getId() : "NULL").append(", ")
                .append("price = ").append(price != null ? price : "0.00").append(", ")
                .append("bill_id = ").append(bill != null ? bill.getId() : "NULL");
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
        sb.append("SELECT t.*, ")
                .append("p.id as projection_id, p.date as projection_date, p.time as projection_time, ")
                .append("p.status as projection_status, p.price as projection_price, p.sold_tickets as projection_sold_tickets, ")
                .append("f.id as film_id, f.title as film_title, f.duration as film_duration, ")
                .append("h.id as hall_id, h.name as hall_name, h.capacity as hall_capacity, ")
                .append("b.id as bill_id, b.date_time as bill_date, b.total_amount as bill_total ")
                .append("FROM ticket t ")
                .append("LEFT JOIN projection p ON t.projection_id = p.id ")
                .append("LEFT JOIN film f ON p.film_id = f.id ")
                .append("LEFT JOIN hall h ON p.hall_id = h.id ")
                .append("LEFT JOIN bill b ON t.bill_id = b.id ");
        //.append(getOrderByClause());
        return sb.toString();
    }

    @Override
    public GenericEntity mapResultSetToObject(ResultSet rs) throws Exception {
        Ticket ticket = new Ticket();

        // Osnovni podaci tiketa
        ticket.setId(rs.getLong("id"));
        ticket.setPrice(rs.getBigDecimal("price"));

        // Projection objekat
        if (rs.getLong("projection_id") != 0) {
            Projection projection = new Projection();
            projection.setId(rs.getLong("projection_id"));
            projection.setDate(rs.getDate("projection_date").toLocalDate());
            projection.setTime(rs.getTime("projection_time").toLocalTime());
            projection.setStatus(ProjectionStatus.valueOf(rs.getString("projection_status")));
            projection.setPrice(rs.getBigDecimal("projection_price"));
            projection.setSoldTickets(rs.getInt("projection_sold_tickets"));

            // Film objekat
            Film film = new Film();
            film.setId(rs.getLong("film_id"));
            film.setTitle(rs.getString("film_title"));
            film.setDuration(rs.getInt("film_duration"));
            projection.setFilm(film);

            // Hall objekat
            Hall hall = new Hall();
            hall.setId(rs.getLong("hall_id"));
            hall.setName(rs.getString("hall_name"));
            hall.setCapacity(rs.getInt("hall_capacity"));
            projection.setHall(hall);

            ticket.setProjection(projection);
        }

        // Bill objekat (samo osnovni podaci)
        if (rs.getLong("bill_id") != 0) {
            Bill bill = new Bill();
            bill.setId(rs.getLong("bill_id"));
            if (rs.getTimestamp("bill_date") != null) {
                bill.setDateTime(rs.getTimestamp("bill_date").toLocalDateTime());
            }
            bill.setTotalAmount(rs.getBigDecimal("bill_total"));
            ticket.setBill(bill);
        }

        return ticket;
    }

    @Override
    public String getOrderByClause() {
        return " ORDER BY t.id";
    }

    // hashCode, equals i toString metode
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.projection);
        hash = 17 * hash + Objects.hashCode(this.bill);
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
        Ticket other = (Ticket) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.projection, other.projection)
                && Objects.equals(this.bill, other.bill);
    }

    @Override
    public String toString() {
        if (projection != null && projection.getFilm() != null) {
            return "Ticket for: " + projection.getFilm().getTitle() + " - " + price + " RSD";
        }
        return "Ticket #" + id + " - " + price + " RSD";
    }
}
