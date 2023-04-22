package Main.Controller;


import Main.Entity.*;


import java.util.*;


public class MovieController {
    Movie movie = new Movie();
    private String email;
    
    public MovieController(String email) {
        this.email = email;
    }

    public ArrayList<String> getMovies() {
        return movie.getMovies(this.email);
    }

}
