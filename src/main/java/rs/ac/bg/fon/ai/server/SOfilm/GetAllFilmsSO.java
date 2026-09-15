/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.SOfilm;

import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.fon.ai.communication.model.Film;
import rs.ac.bg.fon.ai.communication.model.Genre;
import rs.ac.bg.fon.ai.server.SOgenre.GetGenresByFilmIdSO;
import rs.ac.bg.fon.ai.server.abstractso.AbstractSO;

/**
 *
 * @author nkala
 */
public class GetAllFilmsSO extends AbstractSO {

    private List<Film> result;

    public List<Film> getResult() {
        return result;
    }

    @Override
    protected void precondition(Object param) throws Exception {
        // Nema specifičnih preduslova
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Film template = new Film();
        List resultList = repository.getAll(template);

        result = new ArrayList<>();
        for (Object entity : resultList) {
            if (entity instanceof Film) {
                Film film = (Film) entity;

                // Učitaj žanrove za svaki film
                List<Genre> genres = getGenresForFilm(film.getId());
                film.setGenres(genres);

                result.add(film);
            }
        }

        System.out.println("  → Retrieved " + result.size() + " films");
    }

    private List<Genre> getGenresForFilm(Long filmId) throws Exception {
        GetGenresByFilmIdSO getGenresSO = new GetGenresByFilmIdSO();
        getGenresSO.execute(filmId);
        return getGenresSO.getResult();
    }
}
