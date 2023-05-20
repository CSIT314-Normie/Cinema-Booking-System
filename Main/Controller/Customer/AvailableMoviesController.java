package Main.Controller.Customer;

import Main.Entity.*;

import java.util.*;

public class AvailableMoviesController {
    Movie movie = new Movie();
 
    /*
     * Get all AVAILABLE movies
     * @return ArrayList<String> of all movies
     */
    public ArrayList<String> getAvailableMovies() { 
        ArrayList<String> availableMovies = movie.getAvailableMovies(); 

        return availableMovies;
    }
}
