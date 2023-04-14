package Main.Controller;

import Main.Entity.User;

import java.util.*;

public class RegisterController {
    private static User user = new User();

    public boolean createUser(ArrayList<String> values, String role) {
        return user.createUser(values, role);
    }

    
}
