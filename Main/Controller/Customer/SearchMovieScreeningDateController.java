package Main.Controller.Customer;
 
import java.util.ArrayList;

import Main.Entity.MovieScreening;

public class SearchMovieScreeningDateController {
    MovieScreening movieScreening = new MovieScreening();

    public SearchMovieScreeningDateController() { 
    }

    public ArrayList<String> searchMovieScreeningDate(String movieName, String date, String cinemaName) { 
        return movieScreening.searchMovieScreeningForDate(movieName, date, cinemaName);
    }
}
