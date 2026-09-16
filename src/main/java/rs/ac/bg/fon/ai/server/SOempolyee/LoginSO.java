/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.SOempolyee;

import java.sql.ResultSet;

import rs.ac.bg.fon.ai.communication.model.Employee;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;
import rs.ac.bg.fon.ai.server.repository.DbConnectionFactory;

/**
 * Sistemska operacija za prijavljivanje zaposlenog u aplikaciju.
 *
 * Proverava korisnicko ime i lozinku, a nakon uspesne prijave cuva
 * pronadjenog zaposlenog kao rezultat operacije.
 *
 * @author nkala
 * @version 1.0
 */
public class LoginSO extends AbstractSO {

   /** Zaposleni koji je uspesno prijavljen. */
   private Employee result;

   /**
    * Vraca zaposlenog koji je pronadjen prilikom prijavljivanja.
    *
    * @return prijavljeni zaposleni ili {@code null} pre uspesnog izvrsavanja.
    */
   public Employee getResult() {
       return result;
   }

   /**
    * Proverava da li parametar sadrzi korisnicko ime i lozinku.
    *
    * @param param parametar tipa {@link LoginParams}
    * @throws Exception ako parametar nije odgovarajuceg tipa ili su podaci prazni
    */
   @Override
   protected void precondition(Object param) throws Exception {
       if (!(param instanceof LoginParams)) {
           throw new Exception("Invalid parameter type - expected LoginParams");
       }

       LoginParams loginParams = (LoginParams) param;

       if (loginParams.getUsername() == null || loginParams.getUsername().trim().isEmpty()) {
           throw new Exception("Username is required");
       }

       if (loginParams.getPassword() == null || loginParams.getPassword().trim().isEmpty()) {
           throw new Exception("Password is required");
       }
   }

   /**
    * Pretrazuje bazu po korisnickom imenu i lozinci.
    *
    * @param param parametar tipa {@link LoginParams}
    * @throws Exception ako korisnik ne postoji ili dodje do greske pri radu sa bazom
    */
   @Override
   protected void executeOperation(Object param) throws Exception {
       LoginParams loginParams = (LoginParams) param;
       result = null;

       String sql = "SELECT * FROM employe WHERE username = ? AND password = ?";
       
       var connection = DbConnectionFactory.getInstance().getConnection();

       try ( var statement = connection.prepareStatement(sql)) {

           statement.setString(1, loginParams.getUsername());
           statement.setString(2, loginParams.getPassword());

           ResultSet rs = statement.executeQuery();

           if (rs.next()) {
               result = new Employee();
               result.setId(rs.getLong("id"));
               result.setFirstname(rs.getString("firstname"));
               result.setLastname(rs.getString("lastname"));
               result.setUsername(rs.getString("username"));
               result.setPassword(rs.getString("password"));
           }
       }

       if (result == null) {
           throw new Exception("Invalid username or password");
       }

       System.out.println("  → User logged in: " + result.getUsername());
   }

   // Pomoćna klasa za login parametre
   /** Parametri potrebni za prijavljivanje korisnika. */
   public static class LoginParams {

       private String username;
       private String password;

       /**
        * Kreira parametre za prijavljivanje.
        *
        * @param username korisnicko ime
        * @param password lozinka
        */
       public LoginParams(String username, String password) {
           this.username = username;
           this.password = password;
       }

       /** @return korisnicko ime. */
       public String getUsername() {
           return username;
       }

       /** @return lozinka. */
       public String getPassword() {
           return password;
       }
   }
}
