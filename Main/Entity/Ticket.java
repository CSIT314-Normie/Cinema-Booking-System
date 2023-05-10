package Main.Entity;
import Database.DB;

import java.util.*;
import java.sql.*;
public class Ticket {
    private DB db = new DB();
    private Connection conn = this.db.getConnection();
    private String ticketType;
    private String price;
   
    
    public Ticket(String ticketType, String price) {
        this.ticketType = ticketType;
        this.price = price;
    }


    public Ticket() {}


    public DB getDB() {
        return db;
    }

    /**
     * To handle the retrieval of ticketing arrangement information from
     * the database - CINEMA MANAGER
     * @return ArrayList<String[]> ticketing arrangement information
     */
    public ArrayList<String[]> getTicketingArrangement() {
        ArrayList<String[]> values = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ticket_arrangement");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String[] temp = new String[2];
                temp[0] = rs.getString("ticketType");
                temp[1] = rs.getString("price");

                values.add(temp);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return values;
    }

    /**
     * To handle the update of ticket price in the database - CINEMA MANAGER
     * @param type of tickets, e.g., student, adult, senior citizen
     * @param price of tickets for the type
     * @return
     */
    public boolean updateTicketPrice(String type, String price) {
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("UPDATE ticket_arrangement SET price = ? WHERE ticketType = ?");
            stmt.setString(1, price);
            stmt.setString(2, type);
            stmt.executeUpdate();

            System.out.println(type + "ticket price has been updated in the database");
            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

}
