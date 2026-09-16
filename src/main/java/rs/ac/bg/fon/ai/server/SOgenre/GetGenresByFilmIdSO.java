/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.SOgenre;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Genre;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;
import rs.ac.bg.fon.ai.server.repository.DbConnectionFactory;

/**
 * Sistemska operacija za ucitavanje svih zanrova jednog filma.
 *
 * @author nkala
 * @version 1.0
 */
public class GetGenresByFilmIdSO extends AbstractSO {

    private List<Genre> result;

    /** @return lista zanrova trazenog filma. */
    public List<Genre> getResult() {
        return result;
    }

    /**
     * Proverava da li parametar ispunjava poslovne preduslove operacije.
     *
     * @param param podatak koji se obradjuje
     * @throws Exception ako je parametar neispravan ili uslovi nisu ispunjeni
     */
    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof Long)) {
            throw new Exception("Invalid parameter type - expected Long Film ID");
        }

        Long filmId = (Long) param;

        if (filmId == null || filmId <= 0) {
            throw new Exception("Invalid film ID");
        }
    }

    /**
     * Izvrsava poslovnu logiku sistemske operacije nad validiranim parametrom.
     *
     * @param param validiran podatak koji se obradjuje
     * @throws Exception ako operacija ne moze da se izvrsi
     */
    @Override
    protected void executeOperation(Object param) throws Exception {
        Long filmId = (Long) param;
        result = new ArrayList<>();
        var connection = DbConnectionFactory.getInstance().getConnection(); 

        String sql = "SELECT g.id, g.name FROM genre g "
                + "INNER JOIN film_genre fg ON g.id = fg.genre_id "
                + "WHERE fg.film_id = ?";

        try (var statement = connection.prepareStatement(sql)) {

            statement.setLong(1, filmId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getLong("id"));
                genre.setName(rs.getString("name"));
                result.add(genre);
            }
        }

        System.out.println("  → Retrieved " + result.size() + " genres for film ID: " + filmId);
    }
}
