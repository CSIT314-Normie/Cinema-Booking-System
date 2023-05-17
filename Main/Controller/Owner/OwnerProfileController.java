package Main.Controller.Owner;


import Main.Entity.*;


import java.util.*;

public class OwnerProfileController {
    User user = new User();

    public ArrayList<String> getUserInfo(String email) {
        return user.getDB().select("*", email);
    }
    
}
