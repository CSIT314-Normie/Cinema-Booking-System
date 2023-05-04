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
    private static final ArrayList<String> whitelist = new ArrayList<>(
            Arrays.asList("fname", "lname", "email", "dob", "password", "role", "*"));

    private static final String pw = new String(Base64.getDecoder().decode("akc5R2ZFNlhrTkY1QllLSkpmZTg="),
            StandardCharsets.UTF_8);
    private static final String url = "jdbc:mysql://booking:" + pw
            + "@csit314-project-do-user-13025854-0.b.db.ondigitalocean.com:25060/defaultdb?ssl-mode=REQUIRED";
    private static final String TestDB = "jdbc:mysql://booking:" + pw
            + "@csit314-project-do-user-13025854-0.b.db.ondigitalocean.com:25060/Test?ssl-mode=REQUIRED";

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
            System.out.println("[+] Connected to the database on initialisation");

            ArrayList<PreparedStatement> stmts = new ArrayList<>();
            String[] tables = { "users", "movies", "user_movies", "add Admin" };

            stmts.add(conn.prepareStatement("CREATE TABLE IF NOT EXISTS users ("
                    + "fname VARCHAR(255) NOT NULL,"
                    + "lname VARCHAR(255) NOT NULL,"
                    + "email VARCHAR(255) PRIMARY KEY,"
                    + "dob VARCHAR(255) NOT NULL,"
                    + "password VARCHAR(255) NOT NULL,"
                    + "role VARCHAR(255) NOT NULL)"));

            stmts.add(conn.prepareStatement("CREATE TABLE IF NOT EXISTS movies ("
                    + "name VARCHAR(255) PRIMARY KEY,"
                    + "image VARCHAR(255) NOT NULL,"
                    + "rate VARCHAR(255) NOT NULL,"
                    + "review VARCHAR(255) NOT NULL)"));

            stmts.add(conn.prepareStatement("CREATE TABLE IF NOT EXISTS user_movies ("
                    + "email VARCHAR(255) NOT NULL,"
                    + "movieName VARCHAR(255) NOT NULL,"
                    + "rate VARCHAR(255) NOT NULL,"
                    + "review VARCHAR(255) NOT NULL,"
                    + "PRIMARY KEY (email, movieName),"
                    + "FOREIGN KEY (email) REFERENCES users(email),"
                    + "FOREIGN KEY (movieName) REFERENCES movies(name))"));

            stmts.add(conn.prepareStatement(
                    "INSERT INTO users (fname, lname, email, dob, password, role) SELECT 'user', 'admin', 'ua', 'ua', 'ua', 'User Admin' FROM dual WHERE NOT EXISTS (SELECT * FROM users WHERE email = 'ua');"));

            stmts.add(conn.prepareStatement(
                    "CREATE TRIGGER update_movies_rate_review " +
                            "AFTER UPDATE ON user_movies " +
                            "FOR EACH ROW " +
                            "BEGIN " +
                            "    UPDATE movies SET " +
                            "        rate = (SELECT AVG(rate) FROM user_movies WHERE movieName = NEW.movieName), " +
                            "        review = (SELECT COUNT(*) FROM user_movies WHERE movieName = NEW.movieName) " +
                            "    WHERE name = NEW.movieName; " +
                            "END"));

            stmts.forEach(stmt -> {
                try {
                    stmt.execute();
                    if (stmt.getUpdateCount() == 0) {
                        System.out.println("[+] Table " + tables[stmts.indexOf(stmt)] + " create");
                    }
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            });
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (NullPointerException e) {
            System.out.println("[-] Connection to the database failed");
        }
    }

    /**
     * Constructor
     * This constructor is used to connect to the Test database
     * The constructor is commented out because it is not used in the actual program
     * 
     * @param test is used to explicitly inform test users that they are using Tets
     *             database
     */
    // public DB(String test) {
    // try {
    // conn = DriverManager.getConnection(TestDB);
    // System.out.println("Connected to <" + test + "> database");
    // } catch (SQLException ex) {
    // System.out.println(ex.getMessage());
    // }
    // }

    /**
     * This method is used to get the connection to the database
     * 
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
     */

    /**
     * Select information from the database using the email of the user
     * E.g. SELECT fname FROM users WHERE email = '';
     * 
     * @param info  is the information to be selected, e.g. fname, lname, dob,
     *              password, role or asterisk(*) for all
     * @param email is the email of the user
     * @return arraylist of strings containing the information, if the information
     *         is not in whitelisted
     *         or not found, an empty arraylist is returned
     */
    public ArrayList<String> select(String info, String email) {
        ArrayList<String> values = new ArrayList<>();

        if (!whitelist.contains(info)) {
            return new ArrayList<>();
        }

        PreparedStatement stmt;

        try {
            if (info.equals("*")) {
                stmt = conn
                        .prepareStatement("SELECT fname, lname, email, dob, password, role FROM users WHERE email = ?");
            } else {
                stmt = conn.prepareStatement("SELECT " + info + " FROM users WHERE email = ?");
            }
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (info.equals("*")) {
                while (rs.next()) {
                    values.add(rs.getString("fname"));
                    values.add(rs.getString("lname"));
                    values.add(rs.getString("email"));
                    values.add(rs.getString("dob"));
                    values.add(rs.getString("password"));
                    values.add(rs.getString("role"));
                }
            } else {
                while (rs.next()) {
                    values.add(rs.getString(info));
                }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return values;
    }

    /**
     * Select ALL users from the database using 2 columns of information from the
     * user
     * E.g. SELECT role FROM users WHERE email = '' AND password = '';
     * 
     * @param info is the information to be selected, e.g. fname, lname, dob,
     *             password, role or *
     * @param key1 is the first WHERE clause of the user (e.g. email)
     * @return arraylist of arrays containing the information, if the information is
     *         not in whitelisted
     *         or not found, an empty arraylist is returned
     */
    public ArrayList<String[]> selectAll(String info, String email) {
        ArrayList<String[]> values = new ArrayList<>();

        if (!whitelist.contains(info)) {
            return new ArrayList<>();
        }

        PreparedStatement stmt;

        try {
            if (info.equals("*") && email.equals("*")) {
                stmt = conn.prepareStatement("SELECT fname, lname, email, dob, role FROM users ");
            } else {
                stmt = conn.prepareStatement("SELECT " + info + " FROM users WHERE email = ?");
                stmt.setString(1, email);
            }
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String[] temp = new String[5];
                temp[0] = rs.getString("fname");
                temp[1] = rs.getString("lname");
                temp[2] = rs.getString("email");
                temp[3] = rs.getString("dob");
                temp[4] = rs.getString("role");
                values.add(temp);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return values;
    }

    /**
     * Select information from the database using 2 columns of information from the
     * user
     * E.g. SELECT role FROM users WHERE email = '' AND password = '';
     * 
     * @param info   is the information to be selected, e.g. fname, lname, dob,
     *               password, role or *
     * @param key1   is the first WHERE clause of the user
     * @param value1 is the first value of the WHERE clause of the user
     * @param key2   is the second WHERE clause of the user
     * @param value2 is the second value of the WHERE clause of the user
     * @return arraylist of strings containing the information, if the information
     *         is not in whitelisted
     *         or not found, an empty arraylist is returned
     */
    public ArrayList<String> select(String info, String key1, String value1, String key2, String value2) {
        ArrayList<String> values = new ArrayList<>();

        if (!whitelist.contains(info) || !whitelist.contains(key1) || !whitelist.contains(key2)) {
            return new ArrayList<>();
        }

        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement("SELECT " + info + " FROM users WHERE " + key1 + " = ? AND " + key2 + " = ?");
            stmt.setString(1, value1);
            stmt.setString(2, value2);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                values.add(rs.getString(info));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("[-] Database connection issue");
        }

        return values;
    }

    /**
     * This method is used to delete(suspend) a user from the database
     * 
     * @param values is an Arraylist of Strings that contains the information of
     *               user
     * @param role   is the role of the user
     * @return true if the user is deleted from the database, false otherwise
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

    public boolean selectRR(ArrayList<String> values, String email) {
        return true;
    }

    public ArrayList<String[]> selectAllTicketingHistory(String key) {
        PreparedStatement stmt;

        ArrayList<String[]> values = new ArrayList<>();

        try {
            stmt = conn.prepareStatement("SELECT * FROM user_movies WHERE email = ?");
            stmt.setString(1, key);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String[] temp = new String[4];
                temp[0] = rs.getString("movieName");
                temp[1] = rs.getString("rate");
                temp[2] = rs.getString("review");

                values.add(temp);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return values;
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
    //     try {
    //         conn = DriverManager.getConnection(url);
    //     } catch (SQLException ex) {
    //         System.out.println(ex.getMessage());
    //     }
    // }

    // @org.junit.Test
    // public void createTable() {
    //     try {
    //         PreparedStatement stmt = conn.prepareStatement(
    //                 "CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, fname VARCHAR(255) NOT NULL, lname VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL, dob VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, role VARCHAR(255) NOT NULL)");
    //         stmt.executeUpdate();
    //     } catch (SQLException ex) {
    //         System.out.println(ex.getMessage());
    //     }
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
    // PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (fname, lname, email, dob, password, role) VALUES (?, ?, ?, ?, ?, ?)");
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
    //     try {
    //         PreparedStatement stmt = conn.prepareStatement("DELETE FROM users;");
    //         stmt.executeUpdate();
    //     } catch (SQLException ex) {
    //         System.out.println(ex.getMessage());
    //     }
    // }

    // @org.junit.Test
    // public void deleteTable() {
    //     try {
    //         PreparedStatement stmt = conn.prepareStatement("DROP TABLE IF EXISTS users");
    //         stmt.executeUpdate();
    //     } catch (SQLException ex) {
    //         System.out.println(ex.getMessage());
    //     }
    // }

}
