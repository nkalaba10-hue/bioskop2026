/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.fon.ai.communication.model.GenericEntity;

/**
*
* @author nkala
*/
public class DBBroker implements DbRepository<GenericEntity> {

   @Override
   public List<GenericEntity> getAll(GenericEntity entity) throws Exception {
       List<GenericEntity> entities = new ArrayList<>();
       Connection connection = null;
       Statement statement = null;
       ResultSet resultSet = null;

       try {
           connection = DbConnectionFactory.getInstance().getConnection();
           String query = entity.getSelectAllQuery() + entity.getOrderByClause();

           System.out.println("Executing query: " + query);

           statement = connection.createStatement();
           resultSet = statement.executeQuery(query);

           while (resultSet.next()) {
               GenericEntity mappedEntity = entity.mapResultSetToObject(resultSet);
               entities.add(mappedEntity);
           }

           System.out.println("Retrieved " + entities.size() + " entities from " + entity.getTableName());

       } catch (SQLException ex) {
           System.err.println("Error retrieving entities from " + entity.getTableName() + ": " + ex.getMessage());
           throw ex;
       } finally {
           closeResources(resultSet, statement, connection);
       }

       return entities;
   }

   @Override
   public void add(GenericEntity entity) throws Exception {
       Connection connection = null;
       Statement statement = null;
       ResultSet generatedKeys = null;

       try {
           connection = DbConnectionFactory.getInstance().getConnection();

           StringBuilder sb = new StringBuilder();
           sb.append("INSERT INTO ")
                   .append(entity.getTableName())
                   .append(" (")
                   .append(entity.getAttributeList())
                   .append(")")
                   .append(" VALUES (")
                   .append(entity.getAttributeValues())
                   .append(")");

           String query = sb.toString();
           System.out.println("Executing INSERT: " + query);

           statement = connection.createStatement();
           int affectedRows = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

           if (affectedRows == 0) {
               throw new SQLException("Creating entity failed, no rows affected.");
           }

           generatedKeys = statement.getGeneratedKeys();
           if (generatedKeys.next()) {
               Long id = generatedKeys.getLong(1);
               entity.setId(id); // ← OVO VRATI! OVO JE KLJUČNO!
               System.out.println("  → Entity ID set to: " + id);
           } else {
               throw new SQLException("Creating entity failed, no ID obtained.");
           }

       } catch (SQLException ex) {
           System.err.println("Error creating entity in " + entity.getTableName() + ": " + ex.getMessage());
           throw ex;
       } finally {
           closeResources(generatedKeys, statement, connection);
       }
   }

   @Override
   public void edit(GenericEntity entity) throws Exception {
       Connection connection = null;
       Statement statement = null;

       try {
           connection = DbConnectionFactory.getInstance().getConnection();

           StringBuilder sb = new StringBuilder();
           sb.append("UPDATE ")
                   .append(entity.getTableName())
                   .append(" SET ")
                   .append(entity.setAttributeValues())
                   .append(" WHERE ")
                   .append(entity.getWhereCondition());

           String query = sb.toString();
           System.out.println("Executing UPDATE: " + query);

           statement = connection.createStatement();
           int affectedRows = statement.executeUpdate(query);

           if (affectedRows == 0) {
               throw new SQLException("Updating entity failed, no rows affected. Entity may not exist.");
           }

           System.out.println("Entity updated successfully. Affected rows: " + affectedRows);

       } catch (SQLException ex) {
           System.err.println("Error updating entity in " + entity.getTableName() + ": " + ex.getMessage());
           throw ex;
       } finally {
           closeResources(null, statement, connection);
       }
   }

   @Override
   public void delete(GenericEntity entity) throws Exception {
       Connection connection = null;
       Statement statement = null;

       try {
           connection = DbConnectionFactory.getInstance().getConnection();

           StringBuilder sb = new StringBuilder();
           sb.append("DELETE FROM ")
                   .append(entity.getTableName())
                   .append(" WHERE ")
                   .append(entity.getWhereCondition());

           String query = sb.toString();
           System.out.println("Executing DELETE: " + query);

           statement = connection.createStatement();
           int affectedRows = statement.executeUpdate(query);

           if (affectedRows == 0) {
               throw new SQLException("Deleting entity failed, no rows affected. Entity may not exist.");
           }

           System.out.println("Entity deleted successfully. Affected rows: " + affectedRows);

       } catch (SQLException ex) {
           System.err.println("Error deleting entity from " + entity.getTableName() + ": " + ex.getMessage());
           throw ex;
       } finally {
           closeResources(null, statement, connection);
       }
   }

   @Override
   public List<GenericEntity> getByQuery(GenericEntity entity, String query) throws Exception {
       List<GenericEntity> entities = new ArrayList<>();
       Connection connection = null;
       Statement statement = null;
       ResultSet resultSet = null;

       try {
           connection = DbConnectionFactory.getInstance().getConnection();

           String fullQuery = entity.getSelectAllQuery() + " " + query;
           if (!query.toUpperCase().contains("ORDER BY")) {
               fullQuery += entity.getOrderByClause();
           }
           System.out.println("Executing custom query: " + fullQuery);

           statement = connection.createStatement();
           resultSet = statement.executeQuery(fullQuery);

           while (resultSet.next()) {
               GenericEntity mappedEntity = entity.mapResultSetToObject(resultSet);
               entities.add(mappedEntity);
           }

           System.out.println("Retrieved " + entities.size() + " entities with custom query");

       } catch (SQLException ex) {
           System.err.println("Error executing custom query: " + ex.getMessage());
           throw ex;
       } finally {
           closeResources(resultSet, statement, connection);
       }

       return entities;
   }

   @Override
   public GenericEntity addWithReturn(GenericEntity entity) throws Exception {
       add(entity); // Koristimo postojeću add metodu
       return entity; // Vraćamo entitet sa postavljenim ID-jem
   }

   /**
    * Pomoćna metoda za bezbedno zatvaranje resursa
    */
   private void closeResources(ResultSet rs, Statement stmt, Connection conn) {
       try {
           if (rs != null && !rs.isClosed()) {
               rs.close();
           }
       } catch (SQLException ex) {
           System.err.println("Error closing ResultSet: " + ex.getMessage());
       }

       try {
           if (stmt != null && !stmt.isClosed()) {
               stmt.close();
           }
       } catch (SQLException ex) {
           System.err.println("Error closing Statement: " + ex.getMessage());
       }

       // Connection se NE zatvara ovde jer se menadžira u DbRepository interfejsu
       // kroz connect(), disconnect(), commit(), rollback() metode
   }

   /**
    * Dodatna pomoćna metoda za izvršavanje COUNT upita
    */
   public int executeCountQuery(String countQuery) throws Exception {
       Connection connection = null;
       Statement statement = null;
       ResultSet resultSet = null;

       try {
           connection = DbConnectionFactory.getInstance().getConnection();
           System.out.println("Executing COUNT query: " + countQuery);

           statement = connection.createStatement();
           resultSet = statement.executeQuery(countQuery);

           if (resultSet.next()) {
               return resultSet.getInt(1);
           }

           return 0;

       } catch (SQLException ex) {
           System.err.println("Error executing COUNT query: " + ex.getMessage());
           throw ex;
       } finally {
           closeResources(resultSet, statement, connection);
       }
   }

   /**
    * Dodatna pomoćna metoda za proveru postojanja entiteta
    */
   public boolean exists(GenericEntity entity, String condition) throws Exception {
       Connection connection = null;
       Statement statement = null;
       ResultSet resultSet = null;

       try {
           connection = DbConnectionFactory.getInstance().getConnection();

           String query = "SELECT COUNT(*) FROM " + entity.getTableName() + " WHERE " + condition;
           System.out.println("Executing EXISTS query: " + query);

           statement = connection.createStatement();
           resultSet = statement.executeQuery(query);

           if (resultSet.next()) {
               return resultSet.getInt(1) > 0;
           }

           return false;

       } catch (SQLException ex) {
           System.err.println("Error executing EXISTS query: " + ex.getMessage());
           throw ex;
       } finally {
           closeResources(resultSet, statement, connection);
       }

   }

   @Override
   public GenericEntity getById(GenericEntity entity, Long id) throws Exception {
       Connection connection = null;
       Statement statement = null;
       ResultSet resultSet = null;

       try {
           connection = DbConnectionFactory.getInstance().getConnection();

           // Koristimo prvo slovo tabele kao alias (npr: bill -> b, projection -> p)
           String tableAlias = entity.getTableName().substring(0, 1);
           String query = entity.getSelectAllQuery() + " WHERE " + tableAlias + ".id = " + id
                   + entity.getOrderByClause();

           System.out.println("Executing getById query: " + query);

           statement = connection.createStatement();
           resultSet = statement.executeQuery(query);

           if (resultSet.next()) {
               return entity.mapResultSetToObject(resultSet);
           } else {
               throw new SQLException("Entity with id " + id + " not found in " + entity.getTableName());
           }

       } catch (SQLException ex) {
           System.err.println("Error retrieving entity by id from " + entity.getTableName() + ": " + ex.getMessage());
           throw ex;
       } finally {
           closeResources(resultSet, statement, connection);
       }
   }   
}
