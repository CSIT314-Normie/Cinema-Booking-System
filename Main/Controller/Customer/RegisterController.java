package Main.Controller.Customer;


import Main.Entity.*;


import java.util.*;


public class RegisterController {
    private final Customer customer;

    public RegisterController(ArrayList<String> values, String role) {
        customer = new Customer(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), role);
    }

    /**
     * This method is used to create a user
     * @param values is an ArrayList of Strings that contains the user's first name, last name, email, etc
     * @param role is the role of the user
     * @return a boolean that indicates whether the user was created or not
     */
    public boolean createUser(ArrayList<String> values, String role) {
        return this.customer.createUser(values, role);
    }

    /**
     * This method is used to update a user
     * @param values is an ArrayList of Strings that contains the user's first name, last name, email, etc
     * @param role is the role of the user
     * @return a boolean that indicates whether the user was updated or not
     */
    public boolean updateUser(ArrayList<String> values, String role) {
        return this.customer.updateUser(values, role);
    }

}