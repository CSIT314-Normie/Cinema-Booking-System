package Main.Entity;

import Database.DB;

import java.util.*;
import java.sql.*;

public class Seat {
    private DB db = new DB();
    private Connection conn = this.db.getConnection();

    public Seat() {

    }

    public DB getDb() {
        return db;
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
}
