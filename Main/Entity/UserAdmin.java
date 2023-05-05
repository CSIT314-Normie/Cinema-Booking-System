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

    public boolean deleteUser(ArrayList<String> information, String role) {
        return super.getDB().deleteUser(information, role);
    }

    public ArrayList<String[]> getAllUserAccounts() {
        return super.getDB().selectAll("*", "*");
    }

    public boolean updateUserAccInfo(ArrayList<String> modifiedAcc, String userEmail) {
        return super.getDB().updateUserAcc(modifiedAcc, userEmail);
    }

}
