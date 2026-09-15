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
public class Employee implements GenericEntity {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;

    public Employee() {
    }

    public Employee(String firstname, String lastname, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getTableName() {
        return "employe"; // Ostaje staro ime tabele u bazi
    }

    @Override
    public String getAttributeList() {
        return "firstname, lastname, username, password";
    }

    @Override
    public String getAttributeValues() {
        return quote(firstname) + ", " + quote(lastname) + ", "
                + quote(username) + ", " + quote(password);
    }

    @Override
    public String setAttributeValues() {
        return "firstname = " + quote(firstname) + ", "
                + "lastname = " + quote(lastname) + ", "
                + "username = " + quote(username) + ", "
                + "password = " + quote(password);
    }

    @Override
    public String getWhereCondition() {
        return "id = " + id;
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT * FROM employe ORDER BY lastname, firstname";
    }

    @Override
    public GenericEntity mapResultSetToObject(ResultSet rs) throws Exception {
        Employee employee = new Employee();
        employee.setId(rs.getLong("id"));
        employee.setFirstname(rs.getString("firstname"));
        employee.setLastname(rs.getString("lastname"));
        employee.setUsername(rs.getString("username"));
        employee.setPassword(rs.getString("password"));
        return employee;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Employee other = (Employee) obj;
        return Objects.equals(this.username, other.username);
    }

    @Override
    public String toString() {
        return firstname + " " + lastname + " (" + username + ")";
    }
}
