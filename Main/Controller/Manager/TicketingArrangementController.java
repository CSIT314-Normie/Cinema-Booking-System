package Main.Controller.Manager;

import Main.Entity.*;

import java.util.*;

/* 
* This controller handles the ticketing arrangement information
* - update of ticket price - CINEMA MANAGER
* - get ticketing arrangement - CINEMA MANAGER
*/

public class TicketingArrangementController {
    Ticket ticket = new Ticket();
    

    public TicketingArrangementController() {}

    public ArrayList<String[]> getTicketingArrangement() { 
        return ticket.getTicketingArrangement(); 
    }
    
    /**
     * To handle the update of movie information - CINEMA MANAGER
     * @param role only cinema manager can update ticket price 
     * @param type type of ticket
     * @param price price of ticket
     * @return boolean if the update is successful
     */
    public boolean updateTicketPrice(String type, String price) { 
        return ticket.updateTicketPrice(type, price);
    }
}
