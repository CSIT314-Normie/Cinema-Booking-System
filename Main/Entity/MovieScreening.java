package Main.Entity;

import Database.DB;

import java.util.*;
import java.sql.*;

public class MovieScreening {
    private final DB db = new DB();
    private final Connection conn = this.db.getConnection();

    // private String screeningID; // primary key
    // private String movieName; // foreign key to movie
    // private String Hall;    // foreign key to hall
    // private String startTime;
    // private String endTime;
    // private String duration;
    // private String date;
    // private String screeningStatus;

    public MovieScreening() {

    }

    public DB getDb() {
        return db;
    }

    /*
     * To get ALL movie screening information
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
                allScreenings.add(rs.getString("date"));
                allScreenings.add(rs.getString("startTime"));
                allScreenings.add(rs.getString("endTime"));
                allScreenings.add(rs.getString("duration"));
                allScreenings.add(rs.getString("screeningStatus"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allScreenings;
    }

    /** 
     * To get all halls
     * @return ArrayList<String> halls
     */
    public ArrayList<String> getAllHalls() {
        ArrayList<String> allHalls = new ArrayList<>();
        
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM cinema_halls");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                allHalls.add(rs.getString("Hall"));
                allHalls.add(rs.getString("cinemaName"));
                allHalls.add(rs.getString("noOfSeats")); 
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allHalls;
    }


    /**
     * To get a screenings for a specific hall
     * @param hall
     * @return ArrayList<String> screenings
    */
    public ArrayList<String> getScreeningsForHall(String hall) {
        ArrayList<String> allScreenings = new ArrayList<>();
        
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movie_screening WHERE hall = ?");
            stmt.setString(1, hall);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                allScreenings.add(rs.getString("screeningId"));
                allScreenings.add(rs.getString("movieName"));
                allScreenings.add(rs.getString("hall"));
                allScreenings.add(rs.getString("date"));
                allScreenings.add(rs.getString("startTime"));
                allScreenings.add(rs.getString("endTime"));
                allScreenings.add(rs.getString("duration"));
                allScreenings.add(rs.getString("screeningStatus"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allScreenings;
    }

    /** 
     * Validate if a movie screening can be added
     * @param ArrayList<String> values
     * @return boolean
     */
    public boolean validateScreeningSession(ArrayList<String> values) {
        PreparedStatement stmt;
        boolean valid = true;
    
        try {
            // Comments on this function are in the same function in User.java
            stmt = conn.prepareStatement("SELECT * FROM movie_screening WHERE hall = ? AND date = ? AND timeSlot = ? AND startTime = ? AND endTime = ?");
            stmt.setString(1, values.get(1));
            stmt.setString(2, values.get(2));
            stmt.setString(3, values.get(3));
            stmt.setString(4, values.get(4)); 
            stmt.setString(5, values.get(5));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                valid = false;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return valid;
    }

    /**
     * Insert a new movie screening
     * @param ArrayList<String> values
     * @return boolean
     */
    public boolean insertScreeningSession(ArrayList<String> values) {
        PreparedStatement stmt;
    
        try {
            // Comments on this function are in the same function in User.java
            stmt = conn.prepareStatement("INSERT INTO movie_screening (movieName, Hall, date, timeSlot, startTime, endTime, duration, screeningStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, values.get(0));
            stmt.setString(2, values.get(1)); 
            stmt.setString(3, values.get(2));
            stmt.setString(4, values.get(3));
            stmt.setString(5, values.get(4));
            stmt.setString(6, values.get(5)); 
            stmt.setString(7, values.get(6));
            stmt.setString(8, values.get(7));

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
    public boolean updateScreeningStatus(String screeningID, String newScreeningStatus) {
        PreparedStatement stmt;
    
        try {
            stmt = conn.prepareStatement("UPDATE movie_screening SET screeningStatus = ? WHERE screeningID = ?");
            stmt.setString(1, newScreeningStatus);
            stmt.setString(2, screeningID);  

            stmt.executeUpdate(); 

            System.out.println("screening " + screeningID + "'s screening status has been updated");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return true;
    } 

    /**
     * Update screening session info - normally just the Hall
     * @param screeningID
     * @param updateColumn
     * @param value
     * @return boolean
     */
    public boolean updateScreeningSession(int screeningID, String updateColumn, String value) {
        PreparedStatement stmt;
    
        try {
            // Update the column specified by the user with the value specified by the user

            // UPDATE movie_screening SET hall = 'hall' WHERE screeningID = 'screeningID'
            stmt = conn.prepareStatement("UPDATE movie_screening SET " + updateColumn + " = ? WHERE screeningID = ?");
            stmt.setString(1, value);
            stmt.setInt(2, screeningID);  

            stmt.executeUpdate(); 

            // UPDATE seat_Reserved SET hall = 'hall' WHERE screeningID = 'screeningID'
            stmt = conn.prepareStatement("UPDATE seat_Reserved SET " + updateColumn + " = ? WHERE screeningID = ?");
            stmt.setString(1, value);
            stmt.setInt(2, screeningID);

            stmt.executeUpdate();

            System.out.println("screening " + screeningID + "'s " + updateColumn + " has been updated");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return true;
    }


     /*
     * get all halls in a cinema
     */
    public ArrayList<String> getCinemaHalls(String cinemaName) {
        ArrayList<String> hallInfo = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM cinema_halls WHERE cinemaName = ?");
            stmt.setString(1, cinemaName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                hallInfo.add(rs.getString("hall"));
                hallInfo.add(rs.getString("cinemaName"));
                hallInfo.add(rs.getString("noOfSeats"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hallInfo;
    }


     /*
    * To get all the seat information for a hall
    */
    public ArrayList<String> getAllSeats(String hall) {
        ArrayList<String> allSeats = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM seat WHERE hall = ?");
            stmt.setString(1, hall);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                allSeats.add(rs.getString("seatId")); 
                allSeats.add(rs.getString("Hall"));
                allSeats.add(rs.getString("seatRow"));
                allSeats.add(rs.getString("seatNumber"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allSeats;
    }


    /**
     * Get seats reserved for a specific movie screening (movie name and screening ID)
     * @param movieName
     * @param screeningID
     * @param hall
     * @return ArrayList<String> reservedSeats
     */
    public ArrayList<String> getSeatsReservedForScreening(String movieName, String screeningID, String hall) {
        ArrayList<String> allSeats = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM seat_reserved WHERE hall = ? AND movieName = ? AND screeningID = ?");
            stmt.setString(1, hall);
            stmt.setString(2, movieName);
            stmt.setString(3, screeningID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                allSeats.add(rs.getString("seatId")); 
                allSeats.add(rs.getString("Hall"));
                allSeats.add(rs.getString("seatRow"));
                allSeats.add(rs.getString("seatNumber"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allSeats;
    }

    /**
     * To get all screenings for a movie - CUSTOMER
     * @param movieName
     * @return ArrayList<String> screenings
     */
    public ArrayList<String> getAllScreeningsForAMovie(String movieName) {
        ArrayList<String> allScreenings = new ArrayList<>();
        
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movie_screening WHERE movieName = ?");
            stmt.setString(1, movieName);
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

    
}
