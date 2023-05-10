package Main.Controller;


import Main.Entity.*;


import java.util.*;


public class RegisterAccountController {
    private Customer customer;

    public RegisterAccountController(ArrayList<String> values, String role) {
        customer = new Customer(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), role);
    }

    public boolean createUser(ArrayList<String> values, String role) {
        return this.customer.createUser(values, role);
    } 
}