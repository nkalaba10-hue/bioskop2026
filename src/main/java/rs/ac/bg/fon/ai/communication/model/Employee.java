/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.communication.model;

import java.sql.ResultSet;
import java.util.Objects;

/**
 * Predstavlja zaposlenog koji moze da se sacuva i ucita iz baze podataka.
 *
 * @author nkala
 * @version 1.0
 */
public class Employee implements GenericEntity {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;

    /** Kreira prazan objekat zaposlenog. */
    public Employee() {
    }

    /**
     * Kreira zaposlenog sa imenom, prezimenom i podacima za prijavljivanje.
     *
     * @param firstname ime zaposlenog
     * @param lastname prezime zaposlenog
     * @param username korisnicko ime
     * @param password lozinka
     */
    public Employee(String firstname, String lastname, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    /** @return jedinstveni identifikator zaposlenog. */
    @Override
	public Long getId() {
        return id;
    }

    /** @param id novi identifikator zaposlenog. */
    @Override
	public void setId(Long id) {
        this.id = id;
    }

    /** @return ime zaposlenog. */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Postavlja ime zaposlenog.
     * @param firstname novo ime zaposlenog
     * @throws NullPointerException ako je ime {@code null}
     * @throws IllegalArgumentException ako je ime prazno
     */
    public void setFirstname(String firstname) {
        validateRequiredText(firstname, "Firstname");
        this.firstname = firstname;
    }

    /** @return prezime zaposlenog. */
    public String getLastname() {
        return lastname;
    }

    /**
     * Postavlja prezime zaposlenog.
     * @param lastname novo prezime zaposlenog
     * @throws NullPointerException ako je prezime {@code null}
     * @throws IllegalArgumentException ako je prezime prazno
     */
    public void setLastname(String lastname) {
        validateRequiredText(lastname, "Lastname");
        this.lastname = lastname;
    }

    /** @return korisnicko ime zaposlenog. */
    public String getUsername() {
        return username;
    }

    /**
     * Postavlja korisnicko ime zaposlenog.
     * @param username novo korisnicko ime
     * @throws NullPointerException ako je korisnicko ime {@code null}
     * @throws IllegalArgumentException ako je korisnicko ime prazno
     */
    public void setUsername(String username) {
        validateRequiredText(username, "Username");
        this.username = username;
    }

    /** @return lozinka zaposlenog. */
    public String getPassword() {
        return password;
    }

    /**
     * Postavlja lozinku zaposlenog.
     * @param password nova lozinka
     * @throws NullPointerException ako je lozinka {@code null}
     * @throws IllegalArgumentException ako je lozinka prazna
     */
    public void setPassword(String password) {
        validateRequiredText(password, "Password");
        this.password = password;
    }

    private static void validateRequiredText(String value, String fieldName) {
        if (value == null) {
            throw new NullPointerException(fieldName + " is required");
        }
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTableName() {
        return "employe"; // Ostaje staro ime tabele u bazi
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAttributeList() {
        return "firstname, lastname, username, password";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAttributeValues() {
        return quote(firstname) + ", " + quote(lastname) + ", "
                + quote(username) + ", " + quote(password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String setAttributeValues() {
        return "firstname = " + quote(firstname) + ", "
                + "lastname = " + quote(lastname) + ", "
                + "username = " + quote(username) + ", "
                + "password = " + quote(password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWhereCondition() {
        return "id = " + id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSelectAllQuery() {
        return "SELECT * FROM employe";
    }

    /**
     * {@inheritDoc}
     */
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
