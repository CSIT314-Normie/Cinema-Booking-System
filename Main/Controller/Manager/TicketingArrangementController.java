package Main.Controller.Manager;

import Main.Entity.*;

import java.util.*;

/* 
* This controller handles the ticketing arrangement information
* - update of ticket price - CINEMA MANAGER
* - get ticketing arrangement - CINEMA MANAGER
*/

public class TicketingArrangementController {
    private final Manager manager = new Manager();
    

    public TicketingArrangementController() {}

    public ArrayList<String[]> getTicketingArrangement() { 
        return this.manager.getTicketingArrangement(); 
    }
    
    /**
     * To handle the update of movie information - CINEMA MANAGER
     * @param role only cinema manager can update ticket price 
     * @param type type of ticket
     * @param price price of ticket
     * @return boolean if the update is successful
     */
    public boolean updateTicketPrice(String role, String type, String price) { 
        if (role.equals("Cinema Manager")) {
            Manager manager = new Manager();
            return manager.updateTicketPrice(type, price);
        } else {
            return false;
        } 
    }
}
