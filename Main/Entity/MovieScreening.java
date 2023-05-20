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
            System.err.println(e.getMessage());
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
            System.err.println(e.getMessage());
        }

        return allHalls;
    }


    /**
     * To get all cinemas
     * @return ArrayList<String> cinemas
     */
    public ArrayList<String> getAllCinemas() {
        ArrayList<String> allCinemas = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT cinemaName FROM cinema_halls");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){ 
                allCinemas.add(rs.getString("cinemaName"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return allCinemas;
    }

    /** 
     * To get all screenings for a movie for a specific date - for customer
     * @param String movieName
     * @param String date
     * @param String cinemaName
     * @return ArrayList<String> screenings
    */
    public ArrayList<String> searchMovieScreeningForDate(String movieName, String date, String cinemaName) {
        ArrayList<String> screenings = new ArrayList<>();

        try {
            if (date.equals("All") && cinemaName.equals("All")) {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movie_screening WHERE movieName = ? AND screeningStatus = 'Available'");
                stmt.setString(1, movieName); 

                ResultSet rs = stmt.executeQuery();

                while (rs.next()){
                    screenings.add(rs.getString("screeningId"));
                    screenings.add(rs.getString("movieName"));
                    screenings.add(rs.getString("hall"));
                    screenings.add(rs.getString("date"));
                    screenings.add(rs.getString("startTime"));
                    screenings.add(rs.getString("endTime"));
                    screenings.add(rs.getString("duration")); 
                }
            } else if (date.equals("All") && !cinemaName.equals("All")) {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movie_screening WHERE movieName = ? AND Hall IN (SELECT Hall FROM cinema_halls WHERE cinemaName = ?) AND screeningStatus = 'Available'");
                stmt.setString(1, movieName);
                stmt.setString(2, cinemaName);

                ResultSet rs = stmt.executeQuery();

                while (rs.next()){
                    screenings.add(rs.getString("screeningId"));
                    screenings.add(rs.getString("movieName"));
                    screenings.add(rs.getString("hall"));
                    screenings.add(rs.getString("date"));
                    screenings.add(rs.getString("startTime"));
                    screenings.add(rs.getString("endTime"));
                    screenings.add(rs.getString("duration")); 
                }
            } else if (!date.equals("All") && !cinemaName.equals("All")) {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movie_screening WHERE movieName = ? AND Hall IN (SELECT Hall FROM cinema_halls WHERE cinemaName = ?) AND date = ? AND screeningStatus = 'Available'");
                stmt.setString(1, movieName);
                stmt.setString(2, cinemaName);
                stmt.setString(3, date);

                ResultSet rs = stmt.executeQuery();

                while (rs.next()){
                    screenings.add(rs.getString("screeningId"));
                    screenings.add(rs.getString("movieName"));
                    screenings.add(rs.getString("hall"));
                    screenings.add(rs.getString("date"));
                    screenings.add(rs.getString("startTime"));
                    screenings.add(rs.getString("endTime"));
                    screenings.add(rs.getString("duration")); 
                }
            } else if (!date.equals("All") && cinemaName.equals("All")){
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movie_screening WHERE movieName = ? AND date = ? AND screeningStatus = 'Available'");
                stmt.setString(1, movieName);
                stmt.setString(2, date);

                ResultSet rs = stmt.executeQuery();

                while (rs.next()){
                    screenings.add(rs.getString("screeningId"));
                    screenings.add(rs.getString("movieName"));
                    screenings.add(rs.getString("hall"));
                    screenings.add(rs.getString("date"));
                    screenings.add(rs.getString("startTime"));
                    screenings.add(rs.getString("endTime"));
                    screenings.add(rs.getString("duration")); 
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return screenings;
    }

    /** 
     * To get all screenings for a movie for a specific cinema - for customer
     * @param String movieName
     * @param String cinemaName
     * @param String date
     * @return ArrayList<String> screenings
    */
    public ArrayList<String> searchMovieScreeningForCinema(String movieName, String cinemaName, String date) {
        ArrayList<String> screenings = new ArrayList<>();

        try {
            if (date.equals("All") && cinemaName.equals("All")) {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movie_screening WHERE movieName = ? AND screeningStatus = 'Available'");
                stmt.setString(1, movieName); 

                ResultSet rs = stmt.executeQuery();

                while (rs.next()){
                    screenings.add(rs.getString("screeningId"));
                    screenings.add(rs.getString("movieName"));
                    screenings.add(rs.getString("hall"));
                    screenings.add(rs.getString("date"));
                    screenings.add(rs.getString("startTime"));
                    screenings.add(rs.getString("endTime"));
                    screenings.add(rs.getString("duration")); 
                }
            } else if (date.equals("All") && !cinemaName.equals("All")) {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movie_screening WHERE movieName = ? AND Hall IN (SELECT Hall FROM cinema_halls WHERE cinemaName = ?) AND screeningStatus = 'Available'");
                stmt.setString(1, movieName);
                stmt.setString(2, cinemaName);

                ResultSet rs = stmt.executeQuery();

                while (rs.next()){
                    screenings.add(rs.getString("screeningId"));
                    screenings.add(rs.getString("movieName"));
                    screenings.add(rs.getString("hall"));
                    screenings.add(rs.getString("date"));
                    screenings.add(rs.getString("startTime"));
                    screenings.add(rs.getString("endTime"));
                    screenings.add(rs.getString("duration")); 
                }
            } else if (!date.equals("All") && !cinemaName.equals("All")) {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movie_screening WHERE movieName = ? AND Hall IN (SELECT Hall FROM cinema_halls WHERE cinemaName = ?) AND date = ? AND screeningStatus = 'Available'");
                stmt.setString(1, movieName);
                stmt.setString(2, cinemaName);
                stmt.setString(3, date);

                ResultSet rs = stmt.executeQuery();

                while (rs.next()){
                    screenings.add(rs.getString("screeningId"));
                    screenings.add(rs.getString("movieName"));
                    screenings.add(rs.getString("hall"));
                    screenings.add(rs.getString("date"));
                    screenings.add(rs.getString("startTime"));
                    screenings.add(rs.getString("endTime"));
                    screenings.add(rs.getString("duration")); 
                }
            } else if (!date.equals("All") && cinemaName.equals("All")){
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movie_screening WHERE movieName = ? AND date = ? AND screeningStatus = 'Available'");
                stmt.setString(1, movieName);
                stmt.setString(2, date);

                ResultSet rs = stmt.executeQuery();

                while (rs.next()){
                    screenings.add(rs.getString("screeningId"));
                    screenings.add(rs.getString("movieName"));
                    screenings.add(rs.getString("hall"));
                    screenings.add(rs.getString("date"));
                    screenings.add(rs.getString("startTime"));
                    screenings.add(rs.getString("endTime"));
                    screenings.add(rs.getString("duration")); 
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return screenings;
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
            System.err.println(e.getMessage());
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
    * To get all the seat information for a hall
    * @param hall
    * @return ArrayList<String> allSeats
    */
    public ArrayList<String> getAllSeats(String hall) {
        ArrayList<String> allSeats = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM seats WHERE hall = ?");
            stmt.setString(1, hall);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                allSeats.add(rs.getString("seatId")); 
                allSeats.add(rs.getString("Hall"));
                allSeats.add(rs.getString("seatRow"));
                allSeats.add(rs.getString("seatNumber"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return allSeats;
    } 

    /**
     * Get seats reserved for a specific movie screening (movie name and screening ID) 
     * @param hall
     * @param screeningID
     * @return ArrayList<String> reservedSeats
     */
    public ArrayList<String> getSeatsReservedForScreening(String hall, String screeningID) {
        ArrayList<String> allSeats = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM seat_reserved WHERE hall = ? AND screeningID = ?");
            stmt.setString(1, hall); 
            stmt.setString(2, screeningID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                allSeats.add(rs.getString("Hall")); 
                allSeats.add(rs.getString("seatId")); 
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
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
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movie_screening WHERE movieName = ? AND screeningStatus = 'Available'");
                stmt.setString(1, movieName);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()){
                    allScreenings.add(rs.getString("screeningId"));
                    allScreenings.add(rs.getString("movieName"));
                    allScreenings.add(rs.getString("hall"));
                    allScreenings.add(rs.getString("date"));
                    allScreenings.add(rs.getString("startTime"));
                    allScreenings.add(rs.getString("endTime"));
                    allScreenings.add(rs.getString("duration")); 
                }  
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return allScreenings;
    }

    /**
     * To get a screening for a movie - CUSTOMER
     * @param screeningID
     * @return ArrayList<String> screening
     */
    public ArrayList<String> getScreeningInfo(String screeningID) {
        ArrayList<String> screening = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movie_screening WHERE screeningID = ?");  
                stmt.setString(1, screeningID);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()){
                    screening.add(rs.getString("screeningId"));
                    screening.add(rs.getString("movieName"));
                    screening.add(rs.getString("hall"));
                    screening.add(rs.getString("Date"));
                    screening.add(rs.getString("startTime"));
                    screening.add(rs.getString("endTime"));
                    screening.add(rs.getString("duration"));
                    screening.add(rs.getString("date"));
                    screening.add(rs.getString("screeningStatus"));
                }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return screening;
    }

 
    /**
     * confirm seat reservation 
     * @param ArrayList<String> selectedSeats: arraylist of seatID of selected seats
     * @param String screeningID
     * @param String Hall
     * @param String userEmail
     * @param String movieName
     * @param String date
     * @return boolean 
     */
    public boolean confirmSeatReservation(ArrayList<String> selectedSeats, String screeningID, String hall, String userEmail, String movieName, String date) {
        PreparedStatement stmt;
        String seatID; 

        try {
            for (int i = 0; i < selectedSeats.size(); i++) {
                seatID = selectedSeats.get(i); 

                stmt = conn.prepareStatement("INSERT INTO seat_reserved (Hall, seatID, userEmail, movieName, screeningID, date) VALUES (?, ?, ?, ?, ?, ?)");  
                stmt.setString(1, hall);
                stmt.setString(2, seatID);
                stmt.setString(3, userEmail);
                stmt.setString(4, movieName);
                stmt.setString(5, screeningID);
                stmt.setString(6, date);
                
                stmt.executeUpdate(); 

                System.out.println("seat " + seatID + " has been reserved");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return true;
    }
}

