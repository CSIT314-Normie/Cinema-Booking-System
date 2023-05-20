package Main.Controller.Customer;

import java.util.ArrayList;

import Main.Entity.MovieScreening;

public class SearchMovieScreeningCinemaController {
    MovieScreening movieScreening = new MovieScreening();

    public SearchMovieScreeningCinemaController() { 
    }

    public ArrayList<String> searchMovieScreeningCinema(String movieName, String cinemaName, String date) { 
        return movieScreening.searchMovieScreeningForCinema(movieName, cinemaName, date);
    }
}
