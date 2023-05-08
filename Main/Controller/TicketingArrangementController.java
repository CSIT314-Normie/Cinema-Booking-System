package Main.Controller;

import Main.Entity.*;

import java.util.*;

public class TicketingArrangementController {
    private Manager manager = new Manager();
    

    public TicketingArrangementController() {}

    public ArrayList<String[]> getTicketingArrangement() { 
        return this.manager.getTicketingArrangement(); 
    }
    
}
