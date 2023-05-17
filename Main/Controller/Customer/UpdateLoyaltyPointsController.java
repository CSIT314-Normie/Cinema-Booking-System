package Main.Controller.Customer;

import Main.Entity.Customer;

public class UpdateLoyaltyPointsController {
    Customer customer = new Customer();
    
    public UpdateLoyaltyPointsController() {
    }

    /**
    * To update loyalty points of a customer
    * @param email 
    * @return boolean update status
    */
    public boolean updateLoyaltyPoints(String email) {
        return this.customer.updateLoyaltyPoints(email);
    }
    
}
