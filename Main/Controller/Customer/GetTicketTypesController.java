package Main.Controller.Customer;

import java.util.ArrayList;

import Main.Entity.Ticket;

public class GetTicketTypesController {
    
    Ticket ticket = new Ticket();

    public GetTicketTypesController() {
    }

    /**
     * To get ticket types and prices
     * @return ArrayList<String[]> ticket types and prices
     */
    public ArrayList<String[]> getTicketTypes() {
        ArrayList<String[]> ticketTypes = ticket.getTicketingArrangement();

        return ticketTypes;
    }

}
