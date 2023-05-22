package Main.Controller.Manager;

import java.util.ArrayList;

import Main.Entity.Movie;

public class SearchMovieController {
    Movie movie = new Movie();

    public SearchMovieController() {
    }

    public ArrayList<String> searchMovie(String searchQuery) {
        return movie.searchMovie(searchQuery);
    }
}
