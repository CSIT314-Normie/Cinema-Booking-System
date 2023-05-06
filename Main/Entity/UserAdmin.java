package Main.Entity;


import java.util.*;


public class UserAdmin extends User {

    public UserAdmin() {
        super();
    }

    public UserAdmin(String fname, String lname, String email, String dob, String password, String role) {
        super(fname, lname, email, dob, password, role);
    }

    public boolean createUser(ArrayList<String> information, String role) {
        return super.insertUser(information, role);
    }

    public boolean updateUser(ArrayList<String> information, String role) {
        return super.updateAcc(information, role);
    } 

    public ArrayList<String[]> getAllUserAccounts() {
        return super.getDB().selectAll("*", "*");
    }

    public boolean updateUserAccInfo(ArrayList<String> modifiedAcc, String userEmail) {
        return super.getDB().updateUserAcc(modifiedAcc, userEmail);
    }
    
    public boolean suspendAccount(String userEmail) {
        return super.getDB().suspendUser(userEmail);
    }

    public boolean validateEmail(String email) {
        ArrayList<String> result = super.getDB().select("*", email);

        if (result.size() == 0) {
            return true; // Email is not in the database
        } else {
            return false; // Email is in the database
        }
    }

}
