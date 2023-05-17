package Main.Controller.Manager;


import Main.Entity.*;


import java.util.*;

public class ManagerProfileController {
    User user = new User();

    public ArrayList<String> getUserInfo(String email) {
        return user.getDB().select("*", email);
    }
    
}
