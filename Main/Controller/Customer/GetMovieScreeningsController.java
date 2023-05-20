package Main.Controller.Customer;

import java.util.ArrayList;

import Main.Entity.MovieScreening;

public class GetMovieScreeningsController {
    MovieScreening movieScreening = new MovieScreening();

    public GetMovieScreeningsController() {}

    /**
     * To get ALL screenings for a movie 
     * @return ArrayList<String> allScreenings
     */
    public ArrayList<String> getAllScreenings(String movieName) {
        return movieScreening.getAllScreeningsForAMovie(movieName);
    }
    
}
