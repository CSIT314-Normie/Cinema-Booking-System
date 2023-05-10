package Main.Controller.Customer;

import Main.Entity.*;

import java.util.*;

public class AvailableMoviesController {
    Movie movie = new Movie();

    public ArrayList<String> getAllMovies() {
        return movie.getAllMovies();
    }

    // get movies that are available
    public ArrayList<String> getAvailableMovies() {
        ArrayList<String> allMovies = movie.getAllMovies();
        ArrayList<String> availableMovies = new ArrayList<>();

        for(int i = 0; i < allMovies.size(); i += 7) {
            if(allMovies.get(i + 5).equals("Available")) {
                availableMovies.add(allMovies.get(i));
                availableMovies.add(allMovies.get(i + 1));
                availableMovies.add(allMovies.get(i + 2));
                availableMovies.add(allMovies.get(i + 3));
                availableMovies.add(allMovies.get(i + 4));
                availableMovies.add(allMovies.get(i + 5));   
                availableMovies.add(allMovies.get(i + 6));
            }
        }

        return availableMovies;
    }
}
