package Main.Controller;


import Main.Entity.*;


import java.util.*;


public class MovieController {
    Movie movie = new Movie();
    private String email;
    
    public MovieController() {}

    public MovieController(String email) {
        this.email = email;
    }

    // set email
    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getMovies() {
        return movie.getMovies(this.email);
    }

    public boolean updateMovie(String movieName, String rating, String review) {
        return movie.updateMovie(this.email, movieName, rating, review);
    }

}
