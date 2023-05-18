package Main.Controller.Customer;


import Main.Entity.*;


import java.util.*;

public class CustomerProfileController {
    Customer customer = new Customer();

    public ArrayList<String> getUserInfo(String email) {
        return customer.retrieveCustomerInfo(email);
    }
    
}
