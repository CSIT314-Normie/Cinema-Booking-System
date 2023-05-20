package Main.Controller.Customer;

import java.util.ArrayList;

import Main.Entity.Movie;

public class SearchMovieTitleController {
    Movie movie = new Movie();

    public SearchMovieTitleController() { 
    }

    public ArrayList<String> searchMovieTitle(String searchQuery) {
        return movie.searchMovieTitle(searchQuery);
    }
    
}
