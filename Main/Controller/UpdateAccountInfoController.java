package Main.Controller;

import Main.Entity.*;

import java.util.*;

public class UpdateAccountInfoController {

    private UserAdmin userAdmin = new UserAdmin();
    private String email;
    // private ArrayList<String> modifiedAcc;

    public UpdateAccountInfoController() {}

    public UpdateAccountInfoController(String email) {
        this.email = email;
    }
    
    public boolean updateAccountInfo(ArrayList<String> modifiedAcc, String userEmail) {
        return this.userAdmin.updateUserAccInfo(modifiedAcc, userEmail); 
    }
}
