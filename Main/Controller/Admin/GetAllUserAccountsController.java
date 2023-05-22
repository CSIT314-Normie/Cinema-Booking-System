package Main.Controller.Admin;

import java.util.*;

import Main.Entity.UserAdmin;

public class GetAllUserAccountsController {

    UserAdmin admin = new UserAdmin();

    public GetAllUserAccountsController() { 
    }

     /**
     * This method is used to get ALL user accounts from the database
     * @return an ArrayList of String arrays that contains the information of all user accounts
     */
    public ArrayList<String[]> getAllUserAccounts() { 
        return admin.getAllUserAccounts();  
    }
}