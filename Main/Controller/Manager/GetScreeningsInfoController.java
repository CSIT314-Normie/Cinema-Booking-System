package Main.Controller.Manager;

import java.util.ArrayList;

import Main.Entity.*;

public class GetScreeningsInfoController {

    MovieScreening movieScreening = new MovieScreening();

    /**
     * To get ALL movie screening information
     * @return ArrayList<String> screenings
     */
    public ArrayList<String> getAllScreenings() {
        ArrayList<String> screenings = movieScreening.getAllScreenings();

        // add seats reserved for each screening to the ArrayList
        for (int i = 0; i < screenings.size(); i += 9) {
            String screeningID = screenings.get(i);
            // String movieName = screenings.get(i + 1);
            String hall = screenings.get(i + 2);

            ArrayList<String> seatsReserved = movieScreening.getSeatsReservedForScreening(hall, screeningID);
            int seatsReservedCount = seatsReserved.size() / 2;

            screenings.add(i + 8, Integer.toString(seatsReservedCount));
        }

        return screenings;
    }

    /**
     * To get a screening for a specific hall - for Manager
     * @param hall hall to get screening for
     * @return ArrayList<String> screenings
     */
    public ArrayList<String> getScreeningsForHall(String hall) {
        ArrayList<String> screenings = movieScreening.getScreeningsForHall(hall); 

        // add seats reserved for each screening to the ArrayList
        for (int i = 0; i < screenings.size(); i += 9) {
            String screeningID = screenings.get(i);
            // String movieName = screenings.get(i + 1); 

            ArrayList<String> seatsReserved = movieScreening.getSeatsReservedForScreening(hall, screeningID);
            int seatsReservedCount = seatsReserved.size();

            screenings.add(i + 8, Integer.toString(seatsReservedCount));
        }

        return screenings;
    } 
}
