package Main.Controller.Customer;

import Main.Entity.Customer;

public class RedeemLoyaltyPointsController {
    Customer customer = new Customer();
    
    public boolean redeemLoyaltyPoints(String email, String points) {
        return customer.redeemLoyaltyPoints(email, points);
    }

    
}
