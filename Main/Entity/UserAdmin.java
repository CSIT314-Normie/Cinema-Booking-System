package Main.Entity;

import Database.DB;

import java.sql.*;
import java.util.*;


public class UserAdmin extends User {
    private final DB db = new DB();
    private final Connection conn = db.getConnection();
    private PreparedStatement stmt;

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

    /**
     * Select ALL users from the database using 2 columns of information from the
     * user
     * E.g. SELECT role FROM users WHERE email = '' AND password = '';
     * 
     * @param info is the information to be selected, e.g. fname, lname, dob,
     *             password, role or *
     * @param key1 is the first WHERE clause of the user (e.g. email)
     * @return arraylist of arrays containing the information, if the information is
     *         not in whitelisted
     *         or not found, an empty arraylist is returned
     */ 
    public ArrayList<String[]> getAllUserAccounts() {
         
        ArrayList<String[]> values = new ArrayList<>();  

        try { 
            stmt = conn.prepareStatement("SELECT fname, lname, email, dob, role, activeStatus FROM users");
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String[] temp = new String[6];
                temp[0] = rs.getString("fname");
                temp[1] = rs.getString("lname");
                temp[2] = rs.getString("email");
                temp[3] = rs.getString("dob");
                temp[4] = rs.getString("role");
                temp[5] = rs.getString("activeStatus");
                values.add(temp);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return values; 
    }

    /**
     * This method is used to update a user's information in the database 
     * @param modifiedAcc an Arraylist of Strings that contains the modified information of user
     * @param email is the email of the user to be updated
     * @return true if the user info is updated, false otherwise
     */
    public boolean updateUserAccInfo(ArrayList<String> modifiedAcc, String userEmail) {
        try {
            // update users table
            stmt = conn.prepareStatement("UPDATE users SET fname = ?, lname = ?, email= ?, dob = ?, role = ? WHERE email = ?");
            stmt.setString(1, modifiedAcc.get(0));
            stmt.setString(2, modifiedAcc.get(1));
            stmt.setString(3, modifiedAcc.get(2));
            stmt.setString(4, modifiedAcc.get(3));
            stmt.setString(5, modifiedAcc.get(4));
            stmt.setString(6, userEmail);
            stmt.executeUpdate(); 

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return true;
    }
    
    /**
     * This method is used to delete(suspend) a user from the database
     * 
     * @param values is an Arraylist of Strings that contains the information of
     *               user
     * @param role   is the role of the user
     * @return true if the user is deleted from the database, false otherwise
     */
    public boolean suspendAccount(String userEmail) { 

        try {
            stmt = conn.prepareStatement("UPDATE users SET activeStatus = ? WHERE email = ?");
            stmt.setString(1, "Inactive");
            stmt.setString(2, userEmail);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return true;
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
