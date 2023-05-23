package Main.Controller.Admin;


import Main.Entity.*;


import java.util.*;

public class AdminProfileController {
    UserAdmin admin = new UserAdmin();

    public ArrayList<String> getUserInfo(String email) {
        return admin.retriveAdminInfo(email);
    }
    
}
