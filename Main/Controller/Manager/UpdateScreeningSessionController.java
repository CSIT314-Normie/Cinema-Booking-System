package Main.Controller.Manager;

import java.util.*;

import Main.Entity.MovieScreening;

public class UpdateScreeningSessionController {
    MovieScreening movieScreening = new MovieScreening();

    public UpdateScreeningSessionController() {
    }

    /**
     * Get all Halls
     * @return ArrayList<String> halls
     */
    public ArrayList<String> getAllHalls() { 
        return movieScreening.getAllHalls();
    }

    /**
     * Validate if the newly selected Hall is available for the time slot (check: HALL, date, timeSlot startTime, endTime)
     * @param ArrayList<String> screeningSessionInfo
     * @return boolean: true if the hall is available, false otherwise
     */
    public boolean validateHall(ArrayList<String> screeningSessionInfo) {
        return this.movieScreening.validateScreeningSession(screeningSessionInfo);
    }

    /**
     * Update the screening session's hall
     * @param int screeningID
     * @param String updateColumn
     * @param String newHall
     * @return boolean: true if the screening session is updated, false otherwise
     */
    public boolean updateScreeningSession(int screeningID, String updateColumn ,String newHall) {
        return this.movieScreening.updateScreeningSession(screeningID, updateColumn , newHall);
    }
}