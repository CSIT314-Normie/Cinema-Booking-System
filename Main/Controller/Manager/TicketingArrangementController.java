package Main.Controller.Manager;

import Main.Entity.*;

import java.util.*;

/* 
* This controller handles the ticketing arrangement information
* - get ticketing arrangement - CINEMA MANAGER
*/

public class TicketingArrangementController {
    private final Ticket ticket = new Ticket();
    

    public TicketingArrangementController() {}

    /**
     * To handle the retrieval of ticketing arrangement information - CINEMA MANAGER
     * @return ArrayList<String[]> ticketing arrangement information
     */
    public ArrayList<String[]> getTicketingArrangement() { 
        return ticket.getTicketingArrangement(); 
    }
    
}
