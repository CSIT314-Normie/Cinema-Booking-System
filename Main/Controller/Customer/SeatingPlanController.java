package Main.Controller.Customer;

import java.util.ArrayList;

import Main.Entity.MovieScreening;

public class SeatingPlanController {

    MovieScreening movieScreening = new MovieScreening();

    public SeatingPlanController() {}

    /**
     * To get movie screening information
     * @return ArrayList<String> allScreenings
     */
    public ArrayList<String> getScreeningInfo(String screeningID) {
        return movieScreening.getScreeningInfo(screeningID);
    }

    /**
     * To get screening hall seats  
     * @param String Hall
     * @return ArrayList<String> seats
     */
    public ArrayList<String> getSeats(String Hall) {
        return movieScreening.getAllSeats(Hall);
    }

    /** 
     * To get reserved seats for the hall
     * @param String Hall
     * @param String screeningID
     */
    public ArrayList<String> getReservedSeats(String Hall, String screeningID) {
        return movieScreening.getSeatsReservedForScreening(Hall, screeningID);
    } 
}
