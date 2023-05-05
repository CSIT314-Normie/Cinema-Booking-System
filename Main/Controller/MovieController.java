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

    public ArrayList<String> getMovies() {
        return movie.getMovies();
    }

    // set email
    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getUserWatchedMovies() {
        return movie.getUserWatchedMovies(this.email);
    }

    public boolean updateMovieRateReview(String movieName, String rating, String review) {
        return movie.updateMovieRR(this.email, movieName, rating, review);
    }
}
