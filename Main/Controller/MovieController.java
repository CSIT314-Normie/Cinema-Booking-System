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

    public ArrayList<String> getAllMovies() {
        return movie.getAllMovies();
    }

    // get movies that are avaialble
    public ArrayList<String> getAvailableMovies() {
        ArrayList<String> allMovies = movie.getAllMovies();
        ArrayList<String> availableMovies = new ArrayList<>();

        for(int i = 0; i < allMovies.size(); i += 6) {
            if(allMovies.get(i + 5).equals("Available")) {
                availableMovies.add(allMovies.get(i));
                availableMovies.add(allMovies.get(i + 1));
                availableMovies.add(allMovies.get(i + 2));
                availableMovies.add(allMovies.get(i + 3));
                availableMovies.add(allMovies.get(i + 4));
                availableMovies.add(allMovies.get(i + 5));   
            }
        }

        return availableMovies;
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

    public boolean insertMovie(ArrayList<String> movieInfo) {
        return movie.insertMovie(movieInfo);
    }
}
