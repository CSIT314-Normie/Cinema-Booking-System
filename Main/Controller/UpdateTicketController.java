package Main.Controller;

import java.util.ArrayList;

import Main.Entity.Manager;

public class UpdateTicketController {
    private Manager manager = new Manager();

    public UpdateTicketController() {}

    public boolean updateTicketPrice(String type, String price) { 
        return this.manager.updateTicketPrice(type, price); 
    }
}
