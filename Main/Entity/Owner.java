package Main.Entity;


import java.util.ArrayList;

import java.sql.*;
import java.util.*;

import Database.DB;


public class Owner extends User {
    private final DB db = new DB();
    private final Connection conn = db.getConnection();
    private PreparedStatement stmt;

    public Owner() {
        super();
    }
    
    public Owner(String fname, String lname, String email, String dob, String password, String role) {
        super(fname, lname, email, dob, password, role);
    }
    
    public boolean createUser(ArrayList<String> information, String role) {
        return super.insertUser(information, role);
    }
    
    public boolean updateUser(ArrayList<String> information, String role) {
        return super.updateAcc(information, role);
    }

    /**
     * @param userEmail 
     * @param seat_reserved 
     * @param date 
     * @return boolean true if success, false if fail
     */
    public boolean seat_reserved(String userEmail, String reservationID, String date) {
        try {
            stmt = conn.prepareStatement("INSERT INTO payments (email, date, reservationID) VALUES (?, ?, ?)");
            stmt.setString(1, userEmail);
            stmt.setString(2, date);
            stmt.setString(3, reservationID);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    } 
    //daily
    public HashMap<String, ArrayList<String>> getDailyReport(String monthYear) {
        ArrayList<String> allOwner = new ArrayList<>();
        ArrayList<String> allDates = new ArrayList<>();
        HashMap<String, ArrayList<String>> allData = new HashMap<>();
        try {
            stmt = conn.prepareStatement("SELECT STR_TO_DATE(date, '%d/%m/%Y') AS day, SUM(reservationID) AS reservationID FROM seat_reserved WHERE date LIKE ? GROUP BY day");
            stmt.setString(1, "%" + monthYear + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                allOwner.add(rs.getString("reservationID"));
                allDates.add(rs.getString("day"));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        allData.put("reservationID", allOwner);
        allData.put("date", allDates);

        return allData;
    }

    // //weekly
    // public HashMap<String, ArrayList<String>> getWeeklyReport(String monthYear) {
    //     ArrayList<String> allOwner = new ArrayList<>();
    //     ArrayList<String> allDates = new ArrayList<>();
    //     HashMap<String, ArrayList<String>> allData = new HashMap<>();
    //     try {
    //         stmt = conn.prepareStatement("SELECT STR_TO_DATE(date, '%d/%m/%Y') AS day, SUM(reservationID) AS reservationID FROM seat_reserved WHERE date LIKE ? GROUP BY day");
    //         stmt.setString(1, "%" + monthYear + "%");
    //         ResultSet rs = stmt.executeQuery();
    //         while (rs.next()){
    //             allOwner.add(rs.getString("reservationID"));
    //             allDates.add(rs.getString("day"));
    //         }

    //     } catch (SQLException e) {
    //         System.err.println(e.getMessage());
    //     }
    //     allData.put("reservationID", allOwner);
    //     allData.put("date", allDates);

    //     return allData;
    // }

    // monthly
    public HashMap<String, ArrayList<String>> getMonthlyReport(String year) {
        ArrayList<String> allOwner = new ArrayList<>();
        ArrayList<String> allMonth = new ArrayList<>();
        HashMap<String, ArrayList<String>> allData = new HashMap<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT EXTRACT(MONTH FROM STR_TO_DATE(date, '%d/%m/%Y')) AS month," +
                                                            "SUM(reservationID) AS total_reservationID FROM seat_reserved " +
                                                            "WHERE EXTRACT(YEAR FROM STR_TO_DATE(date, '%d/%m/%Y')) = ? GROUP BY month");
            stmt.setString(1, year); 
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                allMonth.add(rs.getString("month"));
                allOwner.add(rs.getString("total_reservationID"));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        allData.put("reservationID", allOwner);
        allData.put("month", allMonth);

        return allData;
    }
}
