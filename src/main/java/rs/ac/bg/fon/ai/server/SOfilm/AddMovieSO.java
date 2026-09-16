/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.SOfilm;

import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Film;
import rs.ac.bg.fon.ai.communication.model.Genre;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;
import rs.ac.bg.fon.ai.server.repository.DbConnectionFactory;

/**
 * Sistemska operacija za dodavanje filma i povezivanje filma sa zanrovima.
 *
 * @author nkala
 * @version 1.0
 */
public class AddMovieSO extends AbstractSO {

    /**
     * Proverava da li parametar ispunjava poslovne preduslove operacije.
     *
     * @param param podatak koji se obradjuje
     * @throws Exception ako je parametar neispravan ili uslovi nisu ispunjeni
     */
    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof Film)) {
            throw new Exception("Invalid parameter type - expected Film");
        }

        Film film = (Film) param;

        // Validacija naslova
        if (film.getTitle() == null || film.getTitle().trim().isEmpty()) {
            throw new Exception("Film title is required");
        }

        if (film.getTitle().length() > 100) {
            throw new Exception("Film title cannot exceed 100 characters");
        }

        // Validacija trajanja
        if (film.getDuration() <= 0) {
            throw new Exception("Film duration must be positive");
        }

        if (film.getDuration() > 500) { // 8+ sati je predugo
            throw new Exception("Film duration cannot exceed 500 minutes");
        }

        // Validacija opisa
        if (film.getDescription() != null && film.getDescription().length() > 255) {
            throw new Exception("Film description cannot exceed 255 characters");
        }

        // Provera jedinstvenosti naslova
        Film template = new Film();
        List existingFilms = repository.getAll(template);

        for (Object entity : existingFilms) {
            if (entity instanceof Film) {
                Film existing = (Film) entity;
                if (existing.getTitle().equalsIgnoreCase(film.getTitle().trim())
                        && ((existing.getReleaseDate() == null && film.getReleaseDate() == null)
                        || (existing.getReleaseDate() != null && existing.getReleaseDate().equals(film.getReleaseDate())))) {
                    throw new Exception("Film with title '" + film.getTitle() + "' and release date already exists");
                }
            }
        }

        // Provera žanrova
        if (film.getGenres() == null || film.getGenres().isEmpty()) {
            throw new Exception("At least one genre is required");
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
        Film film = (Film) param;

        // Prvo sačuvaj film
        repository.add(film);

        // Zatim sačuvaj žanrove u film_genre tabelu
        saveFilmGenres(film);

        System.out.println("  → Film saved with ID: " + film.getId() + " and " + film.getGenres().size() + " genres");
    }

    /**
     * Cuva veze izmedju filma i svih njegovih zanrova u tabeli {@code film_genre}.
     *
     * @param film film cije veze sa zanrovima treba sacuvati
     * @throws Exception ako se veze ne mogu sacuvati u bazi
     */
    private void saveFilmGenres(Film film) throws Exception {
        String sql = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";
        
        var connection = DbConnectionFactory.getInstance().getConnection();

        try ( var statement = connection.prepareStatement(sql)) {

            for (Genre genre : film.getGenres()) {
                statement.setLong(1, film.getId());
                statement.setLong(2, genre.getId());
                statement.addBatch();
            }

            statement.executeBatch();
        }
    }
}
