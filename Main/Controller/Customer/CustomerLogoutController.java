package Main.Controller.Customer;

import Main.Entity.Customer; 

public class CustomerLogoutController {
    Customer customer = new Customer();

    public CustomerLogoutController() {
    }

    /**
     * This method is used to log out a user
     * @param userRole is the role of the user
     * @return true if the user is logged out, false otherwise
     */
    public boolean logout() { 
        customer.logout();
        return true;
    } 

}
