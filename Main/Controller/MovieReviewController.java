package Main.Controller;


import Main.Entity.*;


import java.util.*;


public class MovieReviewController {
    Movie movie = new Movie();
    private String email;
    
    public MovieReviewController() {}   

    public ArrayList<String> getUserWatchedMovies() {
        return movie.getUserWatchedMovies(this.email);
    }

    public boolean updateMovieRateReview(String movieName, String rating, String review) {
        return movie.updateMovieRR(this.email, movieName, rating, review);
    } 
}
