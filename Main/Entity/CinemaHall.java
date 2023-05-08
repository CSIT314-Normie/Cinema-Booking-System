package Main.Entity;

import Database.DB;


import java.util.*;
import java.sql.*;

public class CinemaHall {
    private DB db = new DB();
    private Connection conn = this.db.getConnection();

    private String Hall;
    private String cinemaName;
    private String noOfSeats;

    public CinemaHall() {

    }

    public DB getDb() {
        return db;
    }

    /*
     * get all halls in a cinema
     */
    public ArrayList<String> getCinemaHalls(String cinemaname) {
        ArrayList<String> hallInfo = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM cinema_halls WHERE cinemaName = ?");
            stmt.setString(1, cinemaname);
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

    
}
