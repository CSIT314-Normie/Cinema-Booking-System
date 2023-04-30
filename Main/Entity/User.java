package Main.Entity;


import Database.DB;


import java.sql.*;
import java.util.*;


public class User {
    private DB db = new DB();
    private Connection conn = db.getConnection();
    private PreparedStatement stmt;

    private String fname;
    private String lname;
    private String email;
    private String dob;
    private String password;
    private String role;

    public User() {
        this.fname = "";
        this.lname = "";
        this.email = "";
        this.dob = "";
        this.password = "";
        this.role = "";
    }

    public User(String fname, String lname, String email, String dob, String password, String role) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.dob = dob;
        this.password = password;
        this.role = role;
    }
   
    public DB getDB() {
        return db;
    }

    public boolean logout() {
        this.email = "";
        this.fname = "";
        this.lname = "";
        this.dob = "";
        this.password = "";
        this.role = "";
        
        return true;
    }

    /**
     * This method is used to insert a user into the database
     * @param values is an ArrayList of Strings that contains the information of user to be inserted into the database
     * @param role is the role of the user
     * @return true if the user is inserted into the database, false otherwise
     */
    public boolean insertUser(ArrayList<String> values, String role) {   
        try {
            stmt = conn.prepareStatement("INSERT INTO users (fname, lname, email, dob, password, role) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, values.get(0));
            stmt.setString(2, values.get(1));
            stmt.setString(3, values.get(2));
            stmt.setString(4, values.get(3));
            stmt.setString(5, values.get(4));
            stmt.setString(6, role);
            stmt.executeUpdate();

            System.out.println(values.get(3) + " has been inserted into the database");
        } catch (SQLException e) { 
            System.err.println(e.getMessage());
        }

        return true;
    }   


    /**
     * This method is used to update a user from the database, the user is identified by the email
     * and it changes fname, lname, email, dob and password only.
     * @param values is an Arraylist of Strings that contains the information of user
     * @param role is the role of the user
     * @return true if the user is updated from the database, false otherwise
     */
    public boolean updateAcc(ArrayList<String> values, String email) {
        try {
            stmt = conn.prepareStatement("UPDATE users SET fname = ?, lname = ?, email = ?, dob = ?, password = ? WHERE email = ?");
            stmt.setString(1, values.get(0));
            stmt.setString(2, values.get(1));
            stmt.setString(3, values.get(2));
            stmt.setString(4, values.get(3));
            stmt.setString(5, values.get(4));
            stmt.setString(6, email);
            stmt.executeUpdate();

            System.out.println(values.get(2) + " has been updated in the database");
            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }


    /**
     * This method is used to update a user ROLE from the database, the user is identified by the email
     * and it changes role only
     * @param values is an Arraylist of Strings that contains the information of user
     * @param role is the role of the user
     * @return true if the user is updated from the database, false otherwise
     */
    public boolean updateUserRole(String role, String email) {
        try {
            stmt = conn.prepareStatement("UPDATE users SET role = ? WHERE email = ?");
            stmt.setString(1, role);
            stmt.setString(2, email);
            stmt.executeUpdate();

            System.out.println(email + "'s role has been updated in the database");
            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }
}
