package Main.Controller.Admin;


import Main.Entity.*;


import java.util.*;

public class AdminProfileController {
    User user = new User();

    public ArrayList<String> getUserInfo(String email) {
        return user.getDB().select("*", email);
    }
    
}
