package Main.Controller.Customer;


import Main.Entity.*;


import java.util.*;

public class CustomerProfileController {
    User user = new User();

    public ArrayList<String> getUserInfo(String email) {
        return user.getDB().select("*", email);
    }
    
}
