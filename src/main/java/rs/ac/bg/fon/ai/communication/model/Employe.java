/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.communication.model;

/**
 * Predstavlja zaposlenog sa osnovnim podacima za prijavljivanje u sistem.
 *
 * @author nkala
 * @version 1.0
 */
public class Employe {

    /** Jedinstveni identifikator zaposlenog. */
    private Long id;

    private String firstname;
    private String lastname;
    private String username;
    private String password;

    /** Kreira prazan objekat zaposlenog. */
    public Employe() {
    }

    /**
     * Kreira zaposlenog sa osnovnim podacima.
     *
     * @param firstname ime zaposlenog
     * @param lastname prezime zaposlenog
     * @param username korisnicko ime
     * @param password lozinka
     */
    public Employe( String firstname, String lastname, String username, String password) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
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

    /** @return identifikator zaposlenog. */
    public Long getId() {
        return id;
    }

    /** @param id novi identifikator zaposlenog. */
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

    private static void validateRequiredText(String value, String fieldName) {
        if (value == null) {
            throw new NullPointerException(fieldName + " is required");
        }
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
    }



}
