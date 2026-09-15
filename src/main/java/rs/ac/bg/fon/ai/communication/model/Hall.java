/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.communication.model;

import java.sql.ResultSet;
import java.util.Objects;

/**
 *
 * @author nkala
 */
public class Hall implements GenericEntity {

    private Long id;
    private String name;
    private int capacity;

    public Hall() {
    }

    public Hall(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public Hall(Long id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String getTableName() {
        return "hall";
    }

    @Override
    public String getAttributeList() {
        StringBuilder sb = new StringBuilder();
        sb.append("name, capacity");
        return sb.toString();
    }

    @Override
    public String getAttributeValues() {
        StringBuilder sb = new StringBuilder();
        sb.append(quote(name))
                .append(", ")
                .append(capacity);
        return sb.toString();
    }

    @Override
    public String setAttributeValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("name = ").append(quote(name))
                .append(", capacity = ").append(capacity);
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
                .append(getTableName())
                .append(getOrderByClause());
        return sb.toString();
    }

    @Override
    public GenericEntity mapResultSetToObject(ResultSet rs) throws Exception {
        Hall hall = new Hall();
        hall.setId(rs.getLong("id"));
        hall.setName(rs.getString("name"));
        hall.setCapacity(rs.getInt("capacity"));
        return hall;
    }


    // Override-ovana getOrderByClause za specifično sortiranje
    @Override
    public String getOrderByClause() {
        return " ORDER BY name";
    }

    // hashCode, equals i toString metode
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + this.capacity;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        final Hall other = (Hall) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return name + " (" + capacity + " seats)";
    }
}
