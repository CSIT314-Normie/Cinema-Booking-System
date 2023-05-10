package Main.Controller.Customer;


import Main.Entity.*;


import java.util.*;

/**
 * This controller handles the registration of new accounts
 * USED BY CUSTOMER
 */
public class RegisterAccountController {
    private Customer customer;

    /**
     * Constructor for the RegisterAccountController class
     * @param values ArrayList of account information
     * @param role String of the role of the account
     */
    public RegisterAccountController(ArrayList<String> values, String role) {
        customer = new Customer(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), role);
    }

    /**
     * Creates a new account by calling the createUser method in the Customer class
     * @param values ArrayList of account information
     * @param role String of the role of the account
     * @return boolean of whether the account was created or not
     */
    public boolean createUser(ArrayList<String> values, String role) {
        return this.customer.createUser(values, role);
    } 
}