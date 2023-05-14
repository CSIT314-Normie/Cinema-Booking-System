package Main.Controller.Manager;

import java.util.*;

import Main.Entity.MovieScreening;
import Main.Entity.Movie;

public class AddScreeningSessionController {
    Movie movie = new Movie();
    MovieScreening movieScreening = new MovieScreening(); 

    public AddScreeningSessionController() {
    }

    /**
     * Get all Halls
     * @return ArrayList<String> halls
     */
    public ArrayList<String> getAllHalls() { 
        return movieScreening.getAllHalls(); 
    }

    /**
     * Get all movies 
     * @return ArrayList<String> movies
     */
    public ArrayList<String> getAvailableMovies() { 
        return movie.getAvailableMovies();
    }
 
    /**
     * Validate Screening Session - check if the Hall for the time slot is available (check: HALL, date, timeSlot startTime, endTime)
     * @param screeningSessionInfo
     * @return boolean (TRUE if available, FALSE if unavailable)
     */
    public boolean validateScreeningSession(ArrayList<String> screeningSessionInfo) {
        return this.movieScreening.validateScreeningSession(screeningSessionInfo);
    }

    /**
     * Insert Screening Session 
     * @param ArrayList<String> screeningSessionInfo
     * @return boolean
     */
    public boolean addScreeningSession(ArrayList<String> screeningSessionInfo) { 

        return this.movieScreening.insertScreeningSession(screeningSessionInfo);
    }


}
