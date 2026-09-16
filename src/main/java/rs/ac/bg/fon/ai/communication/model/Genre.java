/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.communication.model;

import java.sql.ResultSet;
import java.util.Objects;

/**
 * Predstavlja zanr filma.
 *
 * @author nkala
 * @version 1.0
 */
public class Genre implements GenericEntity {

    private Long id;
    private String name;

    /**
     * Kreira zanr sa unetim nazivom.
     *
     * @param name naziv zanra
     */
    public Genre(String name) {
        this.name = name;
    }

    /** Kreira prazan objekat zanra. */
    public Genre() {
    }

    // Getters and setters
    /** @return identifikator zanra. */
    @Override
	public Long getId() {
        return id;
    }

    /** @param id novi identifikator zanra. */
    @Override
	public void setId(Long id) {
        this.id = id;
    }

    /** @return naziv zanra. */
    public String getName() {
        return name;
    }

    /** @param name novi naziv zanra. */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTableName() {
        return "genre";
    }

    @Override
    public String getAttributeList() {
        return "name";
    }

    @Override
    public String getAttributeValues() {
        return quote(name);
    }

    @Override
    public String setAttributeValues() {
        return "name = " + quote(name);
    }

    @Override
    public String getWhereCondition() {
        return "id = " + id;
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT * FROM genre ORDER BY name";
    }

    @Override
    public GenericEntity mapResultSetToObject(ResultSet rs) throws Exception {
        Genre genre = new Genre();
        genre.setId(rs.getLong("id"));
        genre.setName(rs.getString("name"));
        return genre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Genre other = (Genre) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
