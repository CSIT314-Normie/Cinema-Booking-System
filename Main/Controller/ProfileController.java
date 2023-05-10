package Main.Controller;


import Main.Entity.*;


import java.util.*;

/**
 * This controller handles the profile of the user
 * Used as a common controller for all users
 */
public class ProfileController {
    User user = new User();

    public ArrayList<String> getUserInfo(String email) {
        return user.getDB().select("*", email);
    }
    
}
