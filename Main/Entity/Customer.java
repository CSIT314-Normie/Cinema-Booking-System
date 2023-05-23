package Main.Entity;

import Database.DB; 

import java.util.*;
import java.util.Date;
import java.sql.*; 

import javax.mail.*;
import javax.mail.internet.*; 
import javax.activation.*;

public class Customer extends User {
    private final DB db = new DB();
    private final Connection conn = db.getConnection();
    private PreparedStatement stmt;

    /**
     * Constructor for Customer with no information
     */
    public Customer() {
        super();
    }

    /**
     * Constructor for Customer with full information
     * @param fname first name
     * @param lname last name
     * @param email email 
     * @param dob date of birth 
     * @param password password 
     * @param role role 
     */
    public Customer(String fname, String lname, String email, String dob, String password, String role) {
        super(fname, lname, email, dob, password, role);
    }


    /**
     * Create user with arraylist information and role
     * @param information arraylist of information
     * @param role role
     * @return boolean true if success, false if fail
     */
    public boolean createUser(ArrayList<String> information, String role) {
        return super.insertUser(information, role);
    }

    /**
     * Retrieve customer information (for profile) from database - fname, lname, email, dob, password
     * @param String email
     * @return ArrayList<String> values
     */
    public ArrayList<String> retrieveCustomerInfo(String email) {
        ArrayList<String> values = new ArrayList<>();

        try {
            stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND role = 'Customer'");
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
     * Update user with arraylist information and role
     * @param information arraylist of information 
     * @param role role 
     * @return boolean true if success, false if fail
     */
    public boolean updateUser(ArrayList<String> information, String role) {
        return super.updateAcc(information, role);
    }


    /** 
     * Get ticketing history of a customer
     * @param email of customer
     * @return ArrayList<String> ticketing history
     */
    public ArrayList<String> getTicketingHistory(String email) {
        ArrayList<String> movieNames = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();

        try {
            stmt = conn.prepareStatement("SELECT DISTINCT movieName FROM seat_reserved WHERE userEmail = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) { 
                movieNames.add(rs.getString("movieName"));
            }

            // get basic information of movies
            for (String movieName : movieNames) {
                stmt = conn.prepareStatement("SELECT * FROM movies WHERE name = ?");
                stmt.setString(1, movieName); 
                rs = stmt.executeQuery();

                if (rs.next()) {
                    values.add(rs.getString("name"));
                    values.add(rs.getString("image"));
                    values.add(rs.getString("rate"));
                    values.add(rs.getString("review"));
                    values.add(rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return values;
    }

    /**
     * Get loyalty point of a customer
     * @param email of customer
     * @return loyalty points
     */
    public String getLoyaltyPoint(String email) {
        try {
            stmt = conn.prepareStatement("SELECT points FROM loyal_points WHERE email = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("points");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return "0";
    }
    
    /**
     * Update loyalty points of a customer after ticketing
     * @param String email of customer
     * @return boolean true if success, false if fail
     */
    public boolean updateLoyaltyPoints(String email) {
        try {
            stmt = conn.prepareStatement("UPDATE loyal_points SET points = points + 1 WHERE email = ?"); 
            stmt.setString(1, email);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }


    /**
     * Redeem loyalty points of a customer - 10 points for 1 ticket
     * @param String email of customer
     * @param String points to deduct
     * @return boolean true if success, false if fail
     */
    public boolean redeemLoyaltyPoints(String email, String deductPts) {
        try {
            stmt = conn.prepareStatement("UPDATE loyal_points SET points = points - ? WHERE email = ?"); 
            stmt.setString(1, deductPts);
            stmt.setString(2, email);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /** 
     * To receive confirmation email after ticketing
     * @param String email of customer
     * @param String movieName
     * @param String date
     * @param ArrayList<String> seats
     * @param String[] bookingInfo
     * @param String total price paid
     * @return boolean true if success, false if fail
     */
    public boolean confirmationEmail(String email, String movieName, String date, ArrayList<String> seatsArrayList, String[] bookingInfo, String totalPrice) {
        Properties props = System.getProperties();

	    props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host 
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

	    Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("normiescinema@gmail.com", "ptosesouwaijppwa");
            }
        });

        String seats = "";
        for (String seat : seatsArrayList) {
            seats += seat + " ";
        }

        String subject = "Normies Cinema - Ticketing Confirmation";
        String body = "Dear " + email + ",\n\n"
                    + "Thank you for booking with us!\n\n"
                    + "Movie: " + movieName + "\n"
                    + "Date: " + date + "\n"
                    + "Time: " + bookingInfo[0] + "\n"
                    + "Cinema: " + bookingInfo[1] + "\n"
                    + "Seats: " + seats + "\n"
                    + "Total Price: $" + totalPrice + "0\n\n"
                    + "We look forward to seeing you soon!\n\n"
                    + "Regards,\n"
                    + "Cinema Booking System";
         
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("normiescinema@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(body);
            
            Transport.send(msg);

            return true;
        } catch (MessagingException mex) {
            System.out.println("send failed, exception: " + mex);
        }

        return false;
    }
}
