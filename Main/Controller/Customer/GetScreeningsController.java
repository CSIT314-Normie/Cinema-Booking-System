package Main.Controller.Customer;

import java.util.ArrayList;

import Main.Entity.MovieScreening;

public class GetScreeningsController {
    MovieScreening movieScreening = new MovieScreening();

    public GetScreeningsController() {}

    /**
     * To get ALL movie screening information
     * @return ArrayList<String> allScreenings
     */
    public ArrayList<String> getAllScreenings(String movieName) {
        return movieScreening.getAllScreeningsForAMovie(movieName);
    }
    
}
