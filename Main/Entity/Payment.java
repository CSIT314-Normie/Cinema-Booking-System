package Main.Entity;


import java.sql.*;


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
     * Make payment
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
}
