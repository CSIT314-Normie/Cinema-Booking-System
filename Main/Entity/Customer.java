package Main.Entity;


import Database.DB;


import java.util.*;
import java.sql.*;


public class Customer extends User {
    private final DB db = new DB();
    private final Connection conn = db.getConnection();
    private PreparedStatement stmt;

    /**
     * Constructor for Customer with no information
     */
    public Customer() {
        super();
    }

    /**
     * Constructor for Customer with full information
     * @param fname first name
     * @param lname last name
     * @param email email 
     * @param dob date of birth 
     * @param password password 
     * @param role role 
     */
    public Customer(String fname, String lname, String email, String dob, String password, String role) {
        super(fname, lname, email, dob, password, role);
    }


    /**
     * Create user with arraylist information and role
     * @param information arraylist of information
     * @param role role
     * @return boolean true if success, false if fail
     */
    public boolean createUser(ArrayList<String> information, String role) {
        return super.insertUser(information, role);
    }


    /**
     * Update user with arraylist information and role
     * @param information arraylist of information 
     * @param role role 
     * @return boolean true if success, false if fail
     */
    public boolean updateUser(ArrayList<String> information, String role) {
        return super.updateAcc(information, role);
    }


    /** 
     * Get ticketing history of a customer
     * @param email of customer
     * @return ArrayList<String> ticketing history
     */
    public ArrayList<String> getTicketingHistory(String email) {
        ArrayList<String> movieNames = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();

        try {
            stmt = conn.prepareStatement("SELECT DISTINCT movieName FROM seat_reserved WHERE userEmail = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) { 
                movieNames.add(rs.getString("movieName"));
            }

            // get basic information of movies
            for (String movieName : movieNames) {
                stmt = conn.prepareStatement("SELECT * FROM movies WHERE name = ?");
                stmt.setString(1, movieName); 
                rs = stmt.executeQuery();

                if (rs.next()) {
                    values.add(rs.getString("name"));
                    values.add(rs.getString("image"));
                    values.add(rs.getString("rate"));
                    values.add(rs.getString("review"));
                    values.add(rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return values;
    }

    /**
     * Get loyalty point of a customer
     * @param email of customer
     * @return loyalty points
     */
    public String getLoyaltyPoint(String email) {
        try {
            stmt = conn.prepareStatement("SELECT points FROM loyal_points WHERE email = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("points");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return "0";
    }
    
    /**
     * Update loyalty points of a customer after ticketing
     * @param String email of customer
     * @return boolean true if success, false if fail
     */
    public boolean updateLoyaltyPoints(String email) {
        try {
            stmt = conn.prepareStatement("UPDATE loyal_points SET points = 1 WHERE email = ?"); 
            stmt.setString(1, email);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
    
    /** 
     * update user_movie table after ticketing
     * @param email of customer
     * @param movieID of movie
     */
    public boolean updateUserMovie(String email, String movieID) {
        try {
            stmt = conn.prepareStatement("INSERT INTO user_movie (email, movieID) VALUES (?, ?)");
            stmt.setString(1, email);
            stmt.setString(2, movieID);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}
