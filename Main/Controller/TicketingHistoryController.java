package Main.Controller;

import Main.Entity.*;

import java.util.*;

public class TicketingHistoryController {
    private Customer customer = new Customer();
    private String email;

    public TicketingHistoryController() {}

    public TicketingHistoryController(String email) {
        this.email = email;
    }
    
    public ArrayList<String[]> getTicketingHistory(String email) { 
        return this.customer.getTicketingHistory(email); 
    }
    
}
