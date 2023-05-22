package Main.Entity;


import java.sql.*;
import java.util.*;


import Database.DB;

public class Payment {
    private final DB db = new DB();
    private final Connection conn = db.getConnection();
    private PreparedStatement stmt;

    /**
     * Constructor for Payment with no information
     */
    public Payment() {
    } 

    /**
     * Make payment - Customer
     * @param userEmail of customer
     * @param amount to pay
     * @param date of payment
     * @return boolean true if success, false if fail
     */
    public boolean makePayment(String userEmail, String amount, String date) {
        try {
            stmt = conn.prepareStatement("INSERT INTO payments (email, date, amount) VALUES (?, ?, ?)");
            stmt.setString(1, userEmail);
            stmt.setString(2, date);
            stmt.setString(3, amount);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    } 

    /**
     * Get all payments made in a day - REPORT A
     * @param monthYear month of the year
     * @return Hashmap of all payments made in a day
     */ 
    public HashMap<String, ArrayList<String>> getDailyReport(String monthYear) {
        ArrayList<String> allPayments = new ArrayList<>();
        ArrayList<String> allDates = new ArrayList<>();
        HashMap<String, ArrayList<String>> allData = new HashMap<>();
        try {
            stmt = conn.prepareStatement("SELECT STR_TO_DATE(date, '%d/%m/%Y') AS day, SUM(amount) AS amount FROM payments WHERE date LIKE ? GROUP BY day");
            stmt.setString(1, "%" + monthYear + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                allPayments.add(rs.getString("amount"));
                allDates.add(rs.getString("day"));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        allData.put("amount", allPayments);
        allData.put("date", allDates);

        return allData;
    }

    /**
     * Get all payments made in a month - REPORT A
     * @param year 
     * @return Hashmap 
     */
    public HashMap<String, ArrayList<String>> getMonthlyReport(String year) {
        ArrayList<String> allPayments = new ArrayList<>();
        ArrayList<String> allMonth = new ArrayList<>();
        HashMap<String, ArrayList<String>> allData = new HashMap<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT EXTRACT(MONTH FROM STR_TO_DATE(date, '%d/%m/%Y')) AS month," +
                                                            "SUM(amount) AS total_amount FROM payments " +
                                                            "WHERE EXTRACT(YEAR FROM STR_TO_DATE(date, '%d/%m/%Y')) = ? GROUP BY month");
            stmt.setString(1, year); 
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                allMonth.add(rs.getString("month"));
                allPayments.add(rs.getString("total_amount"));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        allData.put("amount", allPayments);
        allData.put("month", allMonth);

        return allData;
    }

    /**
     * Get all payments made in a week - REPORT A
     * @param dates - all dates in a specific week
     * @return Hashmap of all payments made in a week
     */
    public HashMap<String, ArrayList<String>> getWeeklyReport(ArrayList<String> dates) {
        ArrayList<String> allPayments = new ArrayList<>();
        ArrayList<String> allWeek = new ArrayList<>();
        HashMap<String, ArrayList<String>> allData = new HashMap<>();
        String firstDayOfWeek = dates.get(0);
        String lastDayOfWeek = dates.get(dates.size() - 1);

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM payments WHERE date BETWEEN ? AND ?");
            stmt.setString(1, firstDayOfWeek); 
            stmt.setString(2, lastDayOfWeek);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                allWeek.add(rs.getString("date"));
                allPayments.add(rs.getString("amount"));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        allData.put("amount", allPayments);
        allData.put("week", allWeek);

        return allData;
    }
} 
