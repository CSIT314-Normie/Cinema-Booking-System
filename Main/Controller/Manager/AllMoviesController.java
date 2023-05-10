package Main.Controller.Manager;

import Main.Entity.*;

import java.util.*;

public class AllMoviesController {
    Movie movie = new Movie();

    public AllMoviesController() {} 

    public ArrayList<String> getAllMovies() {
        return movie.getAllMovies();
    }
}
