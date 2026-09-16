/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.communication.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Predstavlja termin prikazivanja filma u odredjenoj sali.
 *
 * Projekcija sadrzi datum, vreme, cenu, status i broj prodatih karata.
 *
 * @author nkala
 * @version 1.0
 */
public class Projection implements GenericEntity {

    private Long id;
    private Film film;
    private Hall hall;
    private LocalDate date;
    private LocalTime time;
    private ProjectionStatus status;
    private BigDecimal price;
    private int soldTickets;

    // Konstruktori
    /** Kreira praznu projekciju. */
    public Projection() {
    }

    /**
     * Kreira aktivnu projekciju sa pocetnim brojem prodatih karata jednakim nuli.
     *
     * @param film film koji se prikazuje
     * @param hall sala u kojoj se projekcija odrzava
     * @param date datum projekcije
     * @param time vreme pocetka projekcije
     * @param price cena jedne karte
     */
    public Projection(Film film, Hall hall, LocalDate date, LocalTime time, BigDecimal price) {
        this.film = film;
        this.hall = hall;
        this.date = date;
        this.time = time;
        this.status = ProjectionStatus.ACTIVE;
        this.price = price;
        this.soldTickets = 0;
    }

    // Getters and setters
    /** @return identifikator projekcije. */
    @Override
	public Long getId() {
        return id;
    }

    /** @param id novi identifikator projekcije. */
    @Override
	public void setId(Long id) {
        this.id = id;
    }

    /** @return film koji se prikazuje. */
    public Film getFilm() {
        return film;
    }

    /**
     * Postavlja film koji se prikazuje.
     * @param film film koji se prikazuje
     * @throws NullPointerException ako je film {@code null}
     */
    public void setFilm(Film film) {
        if (film == null) {
            throw new NullPointerException("Projection film is required");
        }
        this.film = film;
    }

    /** @return sala u kojoj se projekcija odrzava. */
    public Hall getHall() {
        return hall;
    }

    /**
     * Postavlja salu u kojoj se projekcija odrzava.
     * @param hall sala projekcije
     * @throws NullPointerException ako je sala {@code null}
     */
    public void setHall(Hall hall) {
        if (hall == null) {
            throw new NullPointerException("Projection hall is required");
        }
        this.hall = hall;
    }

    /** @return datum projekcije. */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Postavlja datum projekcije.
     * @param date novi datum projekcije
     * @throws NullPointerException ako je datum {@code null}
     */
    public void setDate(LocalDate date) {
        if (date == null) {
            throw new NullPointerException("Projection date is required");
        }
        this.date = date;
    }

    /** @return vreme pocetka projekcije. */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Postavlja vreme pocetka projekcije.
     * @param time novo vreme pocetka
     * @throws NullPointerException ako je vreme {@code null}
     */
    public void setTime(LocalTime time) {
        if (time == null) {
            throw new NullPointerException("Projection time is required");
        }
        this.time = time;
    }

    /** @return trenutni status projekcije. */
    public ProjectionStatus getStatus() {
        return status;
    }

    /**
     * Postavlja status projekcije.
     * @param status novi status projekcije
     * @throws NullPointerException ako je status {@code null}
     */
    public void setStatus(ProjectionStatus status) {
        if (status == null) {
            throw new NullPointerException("Projection status is required");
        }
        this.status = status;
    }

    /** @return cena jedne karte. */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Postavlja cenu jedne karte.
     * @param price nova pozitivna cena
     * @throws NullPointerException ako je cena {@code null}
     * @throws IllegalArgumentException ako cena nije veca od nule
     */
    public void setPrice(BigDecimal price) {
        if (price == null) {
            throw new NullPointerException("Projection price is required");
        }
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Projection price must be positive");
        }
        this.price = price;
    }

    /** @return broj prodatih karata. */
    public int getSoldTickets() {
        return soldTickets;
    }

    /**
     * Postavlja broj prodatih karata.
     * @param soldTickets novi broj prodatih karata
     * @throws IllegalArgumentException ako je broj negativan
     */
    public void setSoldTickets(int soldTickets) {
        if (soldTickets < 0) {
            throw new IllegalArgumentException("Sold tickets cannot be negative");
        }
        this.soldTickets = soldTickets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTableName() {
        return "projection";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAttributeList() {
        StringBuilder sb = new StringBuilder();
        sb.append("film_id, hall_id, date, time, status, price, sold_tickets");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAttributeValues() {
        StringBuilder sb = new StringBuilder();
        sb.append(film != null ? film.getId() : "NULL").append(", ")
                .append(hall != null ? hall.getId() : "NULL").append(", ")
                .append(sqlValue(date)).append(", ")
                .append(sqlValue(time)).append(", ")
                .append(sqlValue(status != null ? status.toString() : ProjectionStatus.ACTIVE.toString())).append(", ")
                .append(price != null ? price : "0.00").append(", ")
                .append(soldTickets);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String setAttributeValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("film_id = ").append(film != null ? film.getId() : "NULL").append(", ")
                .append("hall_id = ").append(hall != null ? hall.getId() : "NULL").append(", ")
                .append("date = ").append(sqlValue(date)).append(", ")
                .append("time = ").append(sqlValue(time)).append(", ")
                .append("status = ").append(sqlValue(status != null ? status.toString() : ProjectionStatus.ACTIVE.toString())).append(", ")
                .append("price = ").append(price != null ? price : "0.00").append(", ")
                .append("sold_tickets = ").append(soldTickets);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWhereCondition() {
        StringBuilder sb = new StringBuilder();
        sb.append("id = ").append(id);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSelectAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ")
                .append("p.id, p.film_id, p.hall_id, p.date, p.time, p.status, p.price, p.sold_tickets, ")
                .append("f.id as film_id, f.title as film_title, f.duration as film_duration, ")
                .append("f.description as film_description, f.release_date as film_release_date, ")
                .append("h.id as hall_id, h.name as hall_name, h.capacity as hall_capacity ")
                .append("FROM projection p ")
                .append("JOIN film f ON p.film_id = f.id ")
                .append("JOIN hall h ON p.hall_id = h.id ");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GenericEntity mapResultSetToObject(ResultSet rs) throws Exception {
        Projection projection = new Projection();

        // Osnovni podaci projekcije
        projection.setId(rs.getLong("id"));
        projection.setDate(rs.getDate("date").toLocalDate());
        projection.setTime(rs.getTime("time").toLocalTime());
        projection.setStatus(ProjectionStatus.valueOf(rs.getString("status")));
        projection.setPrice(rs.getBigDecimal("price"));
        projection.setSoldTickets(rs.getInt("sold_tickets"));

        // Film objekat
        Film film = new Film();
        film.setId(rs.getLong("film_id"));
        film.setTitle(rs.getString("film_title"));
        film.setDuration(rs.getInt("film_duration"));
        film.setDescription(rs.getString("film_description"));
        if (rs.getDate("film_release_date") != null) {
            film.setReleaseDate(rs.getDate("film_release_date").toLocalDate());
        }
        projection.setFilm(film);

        // Hall objekat
        Hall hall = new Hall();
        hall.setId(rs.getLong("hall_id"));
        hall.setName(rs.getString("hall_name"));
        hall.setCapacity(rs.getInt("hall_capacity"));
        projection.setHall(hall);

        return projection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getJoinClause() {
        // Već je uključeno u getSelectAllQuery, ali možemo override-ovati ako treba
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrderByClause() {
        return " ORDER BY p.date, p.time";
    }

    // hashCode, equals i toString metode
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.film);
        hash = 17 * hash + Objects.hashCode(this.hall);
        hash = 17 * hash + Objects.hashCode(this.date);
        hash = 17 * hash + Objects.hashCode(this.time);
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
        Projection other = (Projection) obj;
        return Objects.equals(this.film, other.film)
                && Objects.equals(this.hall, other.hall)
                && Objects.equals(this.date, other.date)
                && Objects.equals(this.time, other.time)
                && Objects.equals(this.price, other.price);
    }

    @Override
    public String toString() {
        return film != null && hall != null
                ? film.getTitle() + " - " + hall.getName() + " (" + date + " " + time + ")"
                : "Projection [id=" + id + "]";
    }
}
