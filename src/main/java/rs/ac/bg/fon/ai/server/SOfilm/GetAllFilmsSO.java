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
 * Sistemska operacija za ucitavanje svih filmova sa njihovim zanrovima.
 *
 * @author nkala
 * @version 1.0
 */
public class GetAllFilmsSO extends AbstractSO {

    private List<Film> result;

    /** @return lista svih ucitanih filmova. */
    public List<Film> getResult() {
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
        // Nema specifičnih preduslova
    }

    /**
     * Izvrsava poslovnu logiku sistemske operacije nad validiranim parametrom.
     *
     * @param param validiran podatak koji se obradjuje
     * @throws Exception ako operacija ne moze da se izvrsi
     */
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

    /**
     * Ucitava sve zanrove koji pripadaju jednom filmu.
     *
     * @param filmId identifikator filma
     * @return lista zanrova filma
     * @throws Exception ako se zanrovi ne mogu ucitati
     */
    private List<Genre> getGenresForFilm(Long filmId) throws Exception {
        GetGenresByFilmIdSO getGenresSO = new GetGenresByFilmIdSO();
        getGenresSO.execute(filmId);
        return getGenresSO.getResult();
    }
}
