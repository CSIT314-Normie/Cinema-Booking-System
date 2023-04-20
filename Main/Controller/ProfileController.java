package Main.Controller;


import Main.Entity.*;


import java.util.*;

public class ProfileController {
    User user = new User();

    public ArrayList<String> getUserInfo(String email) {
        return user.getDB().select("*", email);
    }
    
}
