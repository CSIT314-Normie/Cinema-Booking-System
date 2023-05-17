package Main.Controller.Customer;

import java.util.*;
 
import Main.Entity.MovieScreening;

public class ConfirmSeatingController {
    MovieScreening movieScreening = new MovieScreening();
    public ConfirmSeatingController() {}

    /**
     * To confirm the seats selected by the customer
     * @param ArrayList<String> selectedSeats: arraylist of seatID of selected seats
     * @param String screeningID
     * @param String Hall
     * @param String userEmail
     * @param String movieName 
     * @param String date
     * @return boolean 
     */
    public boolean confirmSeats(ArrayList<String> selectedSeats, String screeningID, String hall, String userEmail, String movieName, String date) {
        return movieScreening.confirmSeatReservation(selectedSeats, screeningID, hall, userEmail, movieName, date);
    }
    
}
