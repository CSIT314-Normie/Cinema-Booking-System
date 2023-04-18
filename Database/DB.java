package Database;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;

/**
 * @author: Foo Min Zhan
 * @implNote: This class is used to connect to the database, it also contains
 *            methods to perform DML statements.
 *            TestDB is used to test the database connection and creation of
 *            tables. It is not used in the actual program.
 */

public class DB {
    private static final String pw = new String(Base64.getDecoder().decode("akc5R2ZFNlhrTkY1QllLSkpmZTg="), StandardCharsets.UTF_8);
    private static final String url = "jdbc:mysql://booking:" + pw + "@csit314-project-do-user-13025854-0.b.db.ondigitalocean.com:25060/defaultdb?ssl-mode=REQUIRED";
    private static final String TestDB = "jdbc:mysql://booking:" + pw + "@csit314-project-do-user-13025854-0.b.db.ondigitalocean.com:25060/Test?ssl-mode=REQUIRED";
    private Connection conn;
    
    /**
     * Default constructor
     * This constructor is used to connect to the database, perform DDL statements
     * and create tables if they do not exist.
     * 
     */
    public DB() {
        try {
            conn = DriverManager.getConnection(url);

            // Check if connection is successful
            if (conn != null) {
                System.out.println("[+] Connected to the database on initialisation");
                PreparedStatement stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS users ("
                    + "fname VARCHAR(255) NOT NULL,"
                    + "lname VARCHAR(255) NOT NULL,"
                    + "email VARCHAR(255) PRIMARY KEY,"
                    + "dob VARCHAR(255) NOT NULL,"
                    + "password VARCHAR(255) NOT NULL,"
                    + "role VARCHAR(255) NOT NULL)"
                );
    
                stmt.executeUpdate();
            } else {
                System.out.println("[!] Failed to connect to the database!");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Constructor
     * This constructor is used to connect to the Test database
     * The constructor is commented out because it is not used in the actual program
     * @param test is used to explicitly inform test users that they are using Tets database
     */
    // public DB(String test) {
    //     try {
    //         conn = DriverManager.getConnection(TestDB);
    //         System.out.println("Connected to <" + test + "> database");
    //     } catch (SQLException ex) {
    //         System.out.println(ex.getMessage());
    //     }
    // }

    /**
     * This method is used to get the connection to the database
     * @return the connection to the database
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     * This method is used explicitly to close the connection to the database
     */
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    /**
     * Data Manipulation Language(DML) Statements
     * TODO: Implement DML statements 
     * */ 
    
    // public bool select(String table, String[] values) {
    // return true;
    // }

    /**
     * This method is used to insert a user into the database
     * @param values is an ArrayList of Strings that contains the information of user to be inserted into the database
     * @param role is the role of the user
     * @return
     */
    public boolean insertUser(ArrayList<String> values, String role) {
        PreparedStatement stmt;
    
        try {
            stmt = conn.prepareStatement("INSERT INTO users (fname, lname, email, dob, password, role) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, values.get(0));
            stmt.setString(2, values.get(1));
            stmt.setString(3, values.get(2));
            stmt.setString(4, values.get(3));
            stmt.setString(5, values.get(4));
            stmt.setString(6, role);
            stmt.executeUpdate();

            System.out.println(values.get(3) + " has been inserted into the database");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return true;
    }


    /**
     * This method is used to update a user from the database
     * @param values is an Arraylist of Strings that contains the information of user
     * @param role is the role of the user
     * @return
     */
    public boolean updateUser(ArrayList<String> values, String role) {
        PreparedStatement stmt;

        // try {

        // } catch (SQLException e) {
        //     System.err.println(e.getMessage());
        // }

        return true;
    }


    /**
     * This method is used to delete(suspend) a user from the database
     * @param values is an Arraylist of Strings that contains the information of user
     * @param role is the role of the user
     * @return
     */
    public boolean deleteUser(ArrayList<String> values, String role) {
        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement("DELETE FROM users WHERE email = ?");
            stmt.setString(1, values.get(3));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return true;
    }

    // public bool insertTicket(String table, String[] values) {
    // return true;
    // }

    // public bool updateTicket(String table, String[] values) {
    // return true;
    // }

    // public bool selectTicketHistory(String table, String[] values) {
    // return true;
    // }

    // public bool insertTicketHistory(String table, String[] values) {
    // return true;
    // }

    // public bool selectRR(String table, String[] values) {
    // return true;
    // }

    // public bool insertRR(String table, String[] values) {
    // return true;
    // }

    // public bool deleteRR(String table, String[] values) {
    // return true;
    // }










    
    /**
     * Test Driven Development
     * The following few methods are used to test the database connection
     * and the creation of tables
     * These methods are not used in the actual program
     * They are only used to test the database connection and creation of tables
     * They are commented out to prevent the program from running them
     * Uncomment them to test the database connection and creation of tables
     * Comment them out again to prevent the program from running them
     * 
     */
    // @org.junit.Test
    // public void testConnection() throws SQLException {
    // try {
    // conn = DriverManager.getConnection(url);
    // assertNotNull(conn);
    // } catch (SQLException ex) {
    // System.out.println(ex.getMessage());
    // }
    // }
    //
    // @org.junit.Test
    // public void createTable() {
    // try {
    // PreparedStatement stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS
    // users ("
    // + "id INT AUTO_INCREMENT PRIMARY KEY,"
    // + "fname VARCHAR(255) NOT NULL,"
    // + "lname VARCHAR(255) NOT NULL,"
    // + "email VARCHAR(255) NOT NULL,"
    // + "dob VARCHAR(255) NOT NULL,"
    // + "password VARCHAR(255) NOT NULL,"
    // + "role VARCHAR(255) NOT NULL"
    // + ")");
    // stmt.executeUpdate();
    // } catch (SQLException ex) {
    // System.out.println(ex.getMessage());
    // }
    // }

    // @org.junit.Test
    // public void insertUsers() {
    // ArrayList<String> fnameList = new ArrayList<>();
    // ArrayList<String> lnameList = new ArrayList<>();
    // ArrayList<String> emailList = new ArrayList<>();
    // ArrayList<String> dobList = new ArrayList<>();
    // ArrayList<String> passwordList = new ArrayList<>();
    // ArrayList<String> roleList = new ArrayList<>();

    // fnameList.add("Alice");
    // fnameList.add("Bob");
    // fnameList.add("Charlie");
    // fnameList.add("David");
    // lnameList.add("Smith");
    // lnameList.add("Jones");
    // lnameList.add("Brown");
    // lnameList.add("Williams");
    // emailList.add("Alice.S@mail.com");
    // emailList.add("Bob.J@mail.com");
    // emailList.add("Charlie.B@mail.com");
    // emailList.add("David.W@mail.com");
    // dobList.add("01/01/2000");
    // dobList.add("02/02/2000");
    // dobList.add("03/03/2000");
    // dobList.add("04/04/2000");
    // passwordList.add("password");
    // passwordList.add("password");
    // passwordList.add("password");
    // passwordList.add("password");
    // roleList.add("Customer");
    // roleList.add("Cinema Manager");
    // roleList.add("Cinema Owner");
    // roleList.add("User Admin");

    // for (int i = 0; i < 4; i++) {
    // try {
    // PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (fname,
    // lname, email, dob, password, role) VALUES (?, ?, ?, ?, ?, ?)");
    // stmt.setString(1, fnameList.get(i));
    // stmt.setString(2, lnameList.get(i));
    // stmt.setString(3, emailList.get(i));
    // stmt.setString(4, dobList.get(i));
    // stmt.setString(5, passwordList.get(i));
    // stmt.setString(6, roleList.get(i));

    // stmt.executeUpdate();
    // } catch (SQLException ex) {
    // System.out.println(ex.getMessage());
    // }
    // }
    // }

    // @org.junit.Test
    // public void deleteRows() {
    // try {
    // PreparedStatement stmt = conn.prepareStatement("DELETE FROM users;");
    // stmt.executeUpdate();
    // } catch (SQLException ex) {
    // System.out.println(ex.getMessage());
    // }
    // }

    // @org.junit.Test
    // public void deleteTable() {
    // try {
    // PreparedStatement stmt = conn.prepareStatement("DROP TABLE IF EXISTS users");
    // stmt.executeUpdate();
    // } catch (SQLException ex) {
    // System.out.println(ex.getMessage());
    // }
    // }

}
