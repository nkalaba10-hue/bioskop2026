/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.communication.model;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author nkala
 */
public class Film implements GenericEntity {

    private Long id;
    private String title;
    private int duration; // trajanje u minutima
    private String description;
    private LocalDate releaseDate;
    private List<Genre> genres;

    // Konstruktori
    public Film() {
    }

    public Film(String title, int duration, String description, LocalDate releaseDate, List<Genre> genres) {
        this.title = title;
        this.duration = duration;
        this.description = description;
        this.releaseDate = releaseDate;
        this.genres = genres;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String getTableName() {
        return "film";
    }

    @Override
    public String getAttributeList() {
        StringBuilder sb = new StringBuilder();
        sb.append("title, duration, description, release_date");
        return sb.toString();
    }

    @Override
    public String getAttributeValues() {
        StringBuilder sb = new StringBuilder();
        sb.append(quote(title)).append(", ")
                .append(duration).append(", ")
                .append(quote(description)).append(", ")
                .append(sqlValue(releaseDate));
        return sb.toString();
    }

    @Override
    public String setAttributeValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("title = ").append(quote(title)).append(", ")
                .append("duration = ").append(duration).append(", ")
                .append("description = ").append(quote(description)).append(", ")
                .append("release_date = ").append(sqlValue(releaseDate));
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
        sb.append("SELECT * FROM ")
                .append(getTableName());
                //.append(getOrderByClause());
        return sb.toString();
    }

    @Override
    public GenericEntity mapResultSetToObject(ResultSet rs) throws Exception {
        Film film = new Film();

        // Osnovni podaci filma
        film.setId(rs.getLong("id"));
        film.setTitle(rs.getString("title"));
        film.setDuration(rs.getInt("duration"));
        film.setDescription(rs.getString("description"));

        if (rs.getDate("release_date") != null) {
            film.setReleaseDate(rs.getDate("release_date").toLocalDate());
        }

        // NOTE: Žanrovi se ne učitavaju ovde jer zahtevaju JOIN sa film_genre tabelom
        // Žanrovi će se učitati u posebnoj SO klasi (GetGenresByFilmIdSO)
        return film;
    }

    @Override
    public String getOrderByClause() {
        return " ORDER BY title";
    }

    // hashCode, equals i toString metode
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.title);
        hash = 17 * hash + Objects.hashCode(this.releaseDate);
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
        Film other = (Film) obj;
        return Objects.equals(this.title, other.title)
                && Objects.equals(this.releaseDate, other.releaseDate);
    }

    @Override
    public String toString() {
        return title + (releaseDate != null ? " (" + releaseDate.getYear() + ")" : "");
    }
}
