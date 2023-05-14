package Main.Controller.Customer;

import java.util.*;

import Main.Entity.Customer;

public class loyaltyPointController {
    private final Customer customer = new Customer();

    public loyaltyPointController() {}

    /**
     * To get loyalty point of a customer
     * @param email
     * @return loyaltyPoint
     */
    public String getLoyaltyPoint(String email) {
        return customer.getLoyaltyPoint(email);
    }
}
