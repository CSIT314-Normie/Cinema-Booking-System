package Main.Controller.Customer;

import java.util.ArrayList;

import Main.Entity.Customer;

public class ConfirmationEmailController {
    
    Customer customer = new Customer();

    public ConfirmationEmailController() {
    }

    public boolean confirmationEmail(String email, String movieName, String date, ArrayList<String> seats, String totalPrice) {
        return customer.confirmationEmail(email, movieName, date, seats, totalPrice);
    }
}
