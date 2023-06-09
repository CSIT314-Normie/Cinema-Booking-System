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
    
    public boolean updateUser(ArrayList<String> information, String email) {
        return super.updateAcc(information, email);
    }

    /**
     * Retrieve OWNER information (for profile) from database - fname, lname, email, dob, password
     * @param String email
     * @return ArrayList<String> values
     */
    public ArrayList<String> retriveOwnerInfo(String email) {
        ArrayList<String> values = new ArrayList<>();

        try {
            stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND role = 'Cinema Owner'");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                values.add(rs.getString("fname"));
                values.add(rs.getString("lname"));
                values.add(rs.getString("email"));
                values.add(rs.getString("dob"));
                values.add(rs.getString("password"));
            }
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return values;
    }

    
    /**
     * @param userEmail 
     * @param seatID
     * @param date 
     * @return boolean true if success, false if fail
     */
    public boolean seat_reserved(String userEmail, String seatID, String date) {
        try {
            stmt = conn.prepareStatement("INSERT INTO seat_reserved (email, date, seatID) VALUES (?, ?, ?)");
            stmt.setString(1, userEmail);
            stmt.setString(2, date);
            stmt.setString(3, seatID);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    } 

    //daily
    public HashMap<String, ArrayList<String>> getDailyVisitorsReport(String monthYear) {
        ArrayList<String> allSeat = new ArrayList<>();
        ArrayList<String> allDates = new ArrayList<>();
        HashMap<String, ArrayList<String>> allData = new HashMap<>();
        
        try {
            stmt = conn.prepareStatement("SELECT STR_TO_DATE(date, '%d/%m/%Y') AS day, COUNT(*) AS totalSeats FROM seat_reserved WHERE date LIKE ? GROUP BY day");
            stmt.setString(1, "%" + monthYear + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                allSeat.add(rs.getString("totalSeats"));
                allDates.add(rs.getString("day"));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        allData.put("totalSeats", allSeat);
        allData.put("date", allDates);

        return allData;
    }

    /**
     * To get weekly visitors 
     * @param ArrayList<String> datesOfWeek
     * @return HashMap<String, ArrayList<String>> weeklyReport
     */
    public HashMap<String, ArrayList<String>> getWeeklyVisitorsReport(ArrayList<String> datesOfWeek) {
        ArrayList<String> allSeat = new ArrayList<>();
        ArrayList<String> allDates = new ArrayList<>();
        HashMap<String, ArrayList<String>> allData = new HashMap<>();

        String firstDayOfWeek = datesOfWeek.get(0);
        String lastDayOfWeek = datesOfWeek.get(6);

        try {
            stmt = conn.prepareStatement("SELECT STR_TO_DATE(date, '%d/%m/%Y') AS day, COUNT(*) AS totalSeats FROM seat_reserved WHERE STR_TO_DATE(date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?, '%d/%m/%Y') AND STR_TO_DATE(?, '%d/%m/%Y') GROUP BY date");
            stmt.setString(1, firstDayOfWeek);
            stmt.setString(2, lastDayOfWeek);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                allDates.add(rs.getString("day"));
                allSeat.add(rs.getString("totalSeats"));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        allData.put("totalSeats", allSeat);
        allData.put("day", allDates);

        return allData;
    }

    // monthly
    public HashMap<String, ArrayList<String>> getMonthlyVisitorsReport(String year) {
        ArrayList<String> allSeat = new ArrayList<>();
        ArrayList<String> allMonth = new ArrayList<>();
        HashMap<String, ArrayList<String>> allData = new HashMap<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT EXTRACT(MONTH FROM STR_TO_DATE(date, '%d/%m/%Y')) AS month," +
                                                            "COUNT (*) AS totalSeats FROM seat_reserved " +
                                                            "WHERE EXTRACT(YEAR FROM STR_TO_DATE(date, '%d/%m/%Y')) = ? GROUP BY month");
            stmt.setString(1, year); 
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                allMonth.add(rs.getString("month"));
                allSeat.add(rs.getString("totalSeats"));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        allData.put("totalSeats", allSeat);
        allData.put("month", allMonth);

        return allData;
    }
}
