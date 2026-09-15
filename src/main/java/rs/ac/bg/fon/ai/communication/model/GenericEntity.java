/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.ai.communication.model;

import java.io.Serializable;
import java.sql.ResultSet;

/**
 *
 * @author nkala
 */
public interface GenericEntity extends Serializable {

    /**
     * Vraća naziv tabele u bazi podataka
     */
    String getTableName();

    /**
     * Vraća listu kolona za INSERT upit (bez id)
     */
    String getAttributeList();

    /**
     * Vraća vrednosti za INSERT upit
     */
    String getAttributeValues();

    /**
     * Vraća SET deo za UPDATE upit
     */
    String setAttributeValues();

    /**
     * Vraća WHERE uslov za UPDATE i DELETE upite
     */
    String getWhereCondition();

    /**
     * Vraća kompletan SELECT upit za getAll operaciju
     */
    String getSelectAllQuery();

    /**
     * Mapira ResultSet red u objekat
     */
    GenericEntity mapResultSetToObject(ResultSet rs) throws Exception;

    /**
     * Vraća ID entiteta
     */
    Long getId();

    /**
     * Postavlja ID entiteta
     */
    void setId(Long id);

    /**
     * Vraća JOIN klauzule ako su potrebne za kompleksnije upite DEFAULT: prazan
     * string - može se override-ovati po potrebi
     */
    default String getJoinClause() {
        return "";
    }

    /**
     * Vraća ORDER BY klauzulu DEFAULT: order by id - može se override-ovati po
     * potrebi
     */
    default String getOrderByClause() {
        return " ORDER BY id";
    }

    /**
     * Pomoćna metoda za bezbedno dodavanje String vrednosti u SQL DEFAULT
     * implementacija - može se koristiti u konkretnim klasama
     */
    default String quote(String value) {
        if (value == null) {
            return "NULL";
        }
        return "'" + value.replace("'", "''") + "'";
    }

    /**
     * Pomoćna metoda za bezbedno dodavanje vrednosti u SQL DEFAULT
     * implementacija - može se koristiti u konkretnim klasama
     */
    default String sqlValue(Object value) {
        if (value == null) {
            return "NULL";
        }
        if (value instanceof String) {
            return quote((String) value);
        }
        if ((value instanceof java.time.LocalDate) || (value instanceof java.time.LocalTime) || (value instanceof java.time.LocalDateTime)
				|| (value instanceof java.sql.Date)) {
            return quote(value.toString());
        }
        if (value instanceof java.sql.Time) {
            return quote(value.toString());
        }
        if (value instanceof java.sql.Timestamp) {
            return quote(value.toString());
        }
        if (value instanceof Boolean) {
            return (Boolean) value ? "1" : "0";
        }
        return value.toString();
    }
}
