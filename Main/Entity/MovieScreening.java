package Main.Entity;

import Database.DB;

import java.util.*;
import java.sql.*;

public class MovieScreening {
    private DB db = new DB();
    private Connection conn = this.db.getConnection();

    private String screeningID; // primary key
    private String movieName; // foreign key to movie
    private String Hall;    // foreign key to hall
    private String startTime;
    private String endTime;
    private String duration;
    private String date;
    private String screeningStatus;

    public MovieScreening() {

    }

    public DB getDb() {
        return db;
    }

    /*
     * To get all the movie screening information
     */
    public ArrayList<String> getAllScreenings() {
        ArrayList<String> allScreenings = new ArrayList<>();
        
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movie_screening");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                allScreenings.add(rs.getString("screeningId"));
                allScreenings.add(rs.getString("movieName"));
                allScreenings.add(rs.getString("hall"));
                allScreenings.add(rs.getString("startTime"));
                allScreenings.add(rs.getString("endTime"));
                allScreenings.add(rs.getString("duration"));
                allScreenings.add(rs.getString("date"));
                allScreenings.add(rs.getString("screeningStatus"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allScreenings;
    }

    /*
     * Insert a new movie screening
     */
    public boolean insertScreening(ArrayList<String> values) {
        PreparedStatement stmt;
    
        try {
            // Comments on this function are in the same function in User.java
            stmt = conn.prepareStatement("INSERT INTO movie_screening (screeningID, movieName, Hall, startTime, endTime, duration, date, screeningStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, values.get(0));
            stmt.setString(2, values.get(1)); 
            stmt.setString(3, values.get(2));
            stmt.setString(4, values.get(3));
            stmt.setString(5, values.get(4));
            stmt.setString(6, values.get(5));
            stmt.setString(7, values.get(6));

            stmt.executeUpdate();

            System.out.println(values.get(0) + " has been inserted into the database");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return true;
    }

    /* 
     * update screening status
    */
    public boolean updateScreeningStatus(ArrayList<String> values) {
        PreparedStatement stmt;
    
        try {
            // Comments on this function are in the same function in User.java
            stmt = conn.prepareStatement("UPDATE movie_screening SET screeningStatus = ? WHERE screeningID = ?");
            stmt.setString(1, values.get(0));
            stmt.setString(2, values.get(1));  

            stmt.executeUpdate();

            System.out.println(values.get(1) + " screening status has been updated");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return true;
    } 
}
