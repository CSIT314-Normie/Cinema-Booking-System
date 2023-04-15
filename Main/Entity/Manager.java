package Main.Entity;

import java.util.ArrayList;

public class Manager extends User {
    

    public Manager() {
        super();
    }

    public Manager(String fname, String lname, String email, String dob, String password, String role) {
        super(fname, lname, email, dob, password, role);
    }

    public boolean createUser(ArrayList<String> information, String role) {
        return super.getDB().insertUser(information, role);
    }

    public boolean updateUser(ArrayList<String> information, String role) {
        return super.getDB().updateUser(information, role);
    }
}