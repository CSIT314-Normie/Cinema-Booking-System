package Main.Controller;


import Main.Entity.*;


import java.util.*;


public class UpdateController {
    private User user = new User();

    public boolean updateAccount(ArrayList<String> updatedInfo, String email) {
        return user.getDB().updateUser(updatedInfo, email);
    }
}
