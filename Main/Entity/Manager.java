package Main.Entity;

import java.sql.*; 
import java.util.ArrayList;

import Database.DB;

public class Manager extends User {
    
    private final DB db = new DB();
    private final Connection conn = db.getConnection();
    private PreparedStatement stmt;
    
    public Manager() {
        super();
    }

    public Manager(String fname, String lname, String email, String dob, String password, String role) {
        super(fname, lname, email, dob, password, role);
    }

    public boolean createUser(ArrayList<String> information, String role) {
        return super.insertUser(information, role);
    }

    public boolean updateUser(ArrayList<String> information, String email) {
        return super.updateAcc(information, email);
    }

    /**
     * Retrieve manager information (for profile) from database - fname, lname, email, dob, password
     * @param String email
     * @return ArrayList<String> values
     */
    public ArrayList<String> retriveManagerInfo(String email) {
        ArrayList<String> values = new ArrayList<>();

        try {
            stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND role = 'Cinema Manager'");
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
}
