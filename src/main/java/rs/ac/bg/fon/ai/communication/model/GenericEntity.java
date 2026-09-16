/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.ai.communication.model;

import java.io.Serializable;
import java.sql.ResultSet;

/**
 * Zajednicki ugovor za domenske objekte koji se cuvaju u relacionoj bazi.
 *
 * Implementacije opisuju tabelu, SQL delove upita i nacin mapiranja jednog
 * reda rezultata u domenski objekat.
 *
 * @author nkala
 * @version 1.0
 */
public interface GenericEntity extends Serializable {

    /** Vraca naziv tabele kojoj entitet pripada.
     * @return naziv tabele u bazi podataka */
    String getTableName();

    /** Vraca listu kolona za INSERT operaciju, bez primarnog kljuca.
     * @return SQL lista naziva kolona */
    String getAttributeList();

    /** Vraca SQL prikaz trenutnih vrednosti atributa za INSERT upit.
     * @return SQL lista vrednosti atributa */
    String getAttributeValues();

    /** Vraca SET deo SQL UPDATE upita za trenutne vrednosti entiteta.
     * @return izraz za postavljanje atributa */
    String setAttributeValues();

    /** Vraca uslov koji odredjuje red za izmenu ili brisanje.
     * @return SQL uslov bez kljucne reci WHERE */
    String getWhereCondition();

    /** Vraca osnovni SELECT upit za ucitavanje entiteta.
     * @return SELECT upit bez dodatnih kriterijuma */
    String getSelectAllQuery();

    /** Mapira trenutni red rezultata upita u domenski objekat.
     * @param rs rezultat upita pozicioniran na red koji se mapira
     * @return novi domenski objekat popunjen podacima iz rezultata
     * @throws Exception ako podaci ne mogu da se procitaju ili mapiraju */
    GenericEntity mapResultSetToObject(ResultSet rs) throws Exception;

    /** Vraca identifikator entiteta.
     * @return identifikator ili {@code null} ako objekat jos nije sacuvan */
    Long getId();

    /** Postavlja identifikator entiteta.
     * @param id novi identifikator */
    void setId(Long id);

    /** Vraca dodatne JOIN klauzule kada su potrebne.
     * @return prazan string u podrazumevanoj implementaciji */
    default String getJoinClause() {
        return "";
    }

    /** Vraca podrazumevanu ORDER BY klauzulu.
     * @return sortiranje po identifikatoru */
    default String getOrderByClause() {
        return " ORDER BY id";
    }

    /** Pretvara tekst u SQL vrednost i bezbedno obradjuje navodnike.
     * @param value tekstualna vrednost ili {@code null}
     * @return SQL literal, odnosno {@code NULL} za null vrednost */
    default String quote(String value) {
        if (value == null) {
            return "NULL";
        }
        return "'" + value.replace("'", "''") + "'";
    }

    /** Pretvara podrzanu Java vrednost u SQL literal.
     * @param value vrednost koja se zapisuje u SQL upit
     * @return SQL literal odgovarajuce vrednosti */
    default String sqlValue(Object value) {
        if (value == null) {
            return "NULL";
        }
        if (value instanceof String) {
            return quote((String) value);
        }
        if ((value instanceof java.time.LocalDate) || (value instanceof java.time.LocalTime)
                || (value instanceof java.time.LocalDateTime) || (value instanceof java.sql.Date)) {
            return quote(value.toString());
        }
        if (value instanceof java.sql.Time || value instanceof java.sql.Timestamp) {
            return quote(value.toString());
        }
        if (value instanceof Boolean) {
            return (Boolean) value ? "1" : "0";
        }
        return value.toString();
    }
}
