package Database;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;

/**
 * @implNote: This class is used to connect to the database, it also contains
 *            methods to perform DML statements.
 *            TestDB is used to test the database connection and creation of
 *            tables. It is not used in the actual program.
 */

public class DB {
    private static final ArrayList<String> whitelist = new ArrayList<>(Arrays.asList("fname", "lname", "email", "dob", "password", "role", "*"));

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

            ArrayList<PreparedStatement> stmts = new ArrayList<>();
            ArrayList<PreparedStatement> insertion = new ArrayList<>();

            String[] tables = { 
                "users", 
                "movies",  
                "reviews", 
                "ticket_arrangement", 
                "cinema_halls", 
                "seats", 
                "movie_screening", 
                "seat_reserved",
                "loyal_points",
                "payments",
            };

            String[] insert = {
                "Add Admin",
                "Add Cine Hall A",
                "Add Cine Hall B",
                "Add Cine Hall C",
                "Add Cine Hall D",
                "Add Screening",
                "Add Loyal Points",
                "Add Trigger",
                "Add Trigger 2",
                "Add Trigger 3"
            };

            // Create users table
            stmts.add(conn.prepareStatement("CREATE TABLE IF NOT EXISTS users ("
                + "fname VARCHAR(255) NOT NULL,"
                + "lname VARCHAR(255) NOT NULL,"
                + "email VARCHAR(255) PRIMARY KEY,"
                + "dob VARCHAR(255) NOT NULL,"
                + "password VARCHAR(255) NOT NULL,"
                + "role VARCHAR(255) NOT NULL, " 
                + "activeStatus VARCHAR(15) NOT NULL)"));

            // create movies table
            stmts.add(conn.prepareStatement("CREATE TABLE IF NOT EXISTS movies ("
                + "name VARCHAR(255) PRIMARY KEY,"
                + "image VARCHAR(255) NOT NULL,"
                + "rate VARCHAR(255) NOT NULL,"
                + "review VARCHAR(255) NOT NULL, "
                + "description VARCHAR(500) NOT NULL,"
                + "status VARCHAR(15) NOT NULL,"
                + "duration VARCHAR(255) NOT NULL)"));
 
            // create reviews table (reviews of movies)
            stmts.add(conn.prepareStatement("CREATE TABLE IF NOT EXISTS reviews ("
                + "reviewID INT AUTO_INCREMENT PRIMARY KEY,"
                + "email VARCHAR(255) NOT NULL,"
                + "movieName VARCHAR(255) NOT NULL,"
                + "review VARCHAR(255) NOT NULL," 
                + "rating VARCHAR(255) NOT NULL,"
                + "FOREIGN KEY (email) REFERENCES users(email) ON UPDATE CASCADE,"
                + "FOREIGN KEY (movieName) REFERENCES movies(name) ON UPDATE CASCADE)"));

            // create ticket_arrangement table (types of tickets and their prices)
            stmts.add(conn.prepareStatement("CREATE TABLE IF NOT EXISTS ticket_arrangement ("
                + "ticketType VARCHAR(255) PRIMARY KEY,"
                + "price VARCHAR(255) NOT NULL)"));

            // create cinema table (cinema name and location)
            // each hall has 12 seats
            stmts.add(conn.prepareStatement("CREATE TABLE IF NOT EXISTS cinema_halls ("
                + "Hall VARCHAR(10) PRIMARY KEY,"
                + "cinemaName VARCHAR(255) NOT NULL," 
                + "noOfSeats int)"));

            // create seats table (seats in the cinema hall)
            stmts.add(conn.prepareStatement("CREATE TABLE IF NOT EXISTS seats ("
                + "seatID VARCHAR(10) PRIMARY KEY,"
                + "Hall VARCHAR(10) NOT NULL,"
                + "seatRow VARCHAR(10) NOT NULL,"
                + "seatNumber VARCHAR(10) NOT NULL,"
                + "cinemaName VARCHAR(255) NOT NULL,"
                + "FOREIGN KEY (Hall) REFERENCES cinema_halls(Hall))"));

            // ceate movie screening table (movie name, screening ID, Hall ID)
            stmts.add(conn.prepareStatement("CREATE TABLE IF NOT EXISTS movie_screening ("
                    + "screeningID INT AUTO_INCREMENT PRIMARY KEY,"
                    + "movieName VARCHAR(255) NOT NULL,"
                    + "Hall VARCHAR(10) NOT NULL,"
                    + "date VARCHAR(255) NOT NULL,"
                    + "timeSlot VARCHAR(255) NOT NULL,"
                    + "startTime VARCHAR(255) NOT NULL,"
                    + "endTime VARCHAR(255) NOT NULL,"
                    + "duration VARCHAR(255) NOT NULL,"
                    + "screeningStatus VARCHAR(15) NOT NULL,"
                    + "FOREIGN KEY (movieName) REFERENCES movies(name) ON UPDATE CASCADE,"
                    + "FOREIGN KEY (Hall) REFERENCES cinema_halls(Hall))"));
           

            // create seat_reserved table (reserved seats, which movie, which hall, which screening session)
            stmts.add(conn.prepareStatement("CREATE TABLE IF NOT EXISTS seat_reserved ("
                + "reservationID INT AUTO_INCREMENT PRIMARY KEY,"
                + "Hall VARCHAR(10) NOT NULL,"
                + "seatID VARCHAR(10) NOT NULL,"
                + "userEmail VARCHAR(255) NOT NULL,"
                + "movieName VARCHAR(255) NOT NULL,"
                + "screeningID INT NOT NULL,"
                + "date VARCHAR(255) NOT NULL,"
                + "FOREIGN KEY (userEmail) REFERENCES users(email) ON UPDATE CASCADE,"
                + "FOREIGN KEY (movieName) REFERENCES movies(name) ON UPDATE CASCADE,"
                + "FOREIGN KEY (screeningID) REFERENCES movie_screening(screeningID),"
                + "FOREIGN KEY (seatID) REFERENCES seats(seatID))"));
            
            // create table for loyal points
            stmts.add(conn.prepareStatement("CREATE TABLE IF NOT EXISTS loyal_points ("
                + "email VARCHAR(255) PRIMARY KEY,"
                + "points VARCHAR(255) DEFAULT 0,"
                + "FOREIGN KEY (email) REFERENCES users(email) ON UPDATE CASCADE)"));

            // Create table for payments
            stmts.add(conn.prepareStatement("CREATE TABLE IF NOT EXISTS payments ("
            + "paymentID INT AUTO_INCREMENT PRIMARY KEY,"
            + "email VARCHAR(255) NOT NULL,"
            + "date VARCHAR(255) NOT NULL,"
            + "amount VARCHAR(255) NOT NULL,"
            + "FOREIGN KEY (email) REFERENCES users(email) ON UPDATE CASCADE)"));

            insertion.add(conn.prepareStatement( "INSERT INTO users (fname, lname, email, dob, password, role) SELECT 'user', 'admin', 'ua', 'ua', 'ua', 'User Admin' FROM dual WHERE NOT EXISTS (SELECT * FROM users WHERE email = 'ua');"));

            // 2 cinemas, 2 halls in each cinema, 12 seats in each hall
            insertion.add(conn.prepareStatement("INSERT INTO cinema_halls (Hall, cinemaName, noOfSeats) SELECT 'A', 'Greenville cinema', 12 FROM dual WHERE NOT EXISTS (SELECT * FROM cinema_halls WHERE Hall = 'A');"));

            insertion.add(conn.prepareStatement("INSERT INTO cinema_halls (Hall, cinemaName, noOfSeats) SELECT 'B', 'Greenville Cinema', 12 FROM dual WHERE NOT EXISTS (SELECT * FROM cinema_halls WHERE Hall = 'B');"));

            insertion.add(conn.prepareStatement("INSERT INTO cinema_halls (Hall, cinemaName, noOfSeats) SELECT 'C', 'Townsville Cinema', 12 FROM dual WHERE NOT EXISTS (SELECT * FROM cinema_halls WHERE Hall = 'C');"));

            insertion.add(conn.prepareStatement("INSERT INTO cinema_halls (Hall, cinemaName, noOfSeats) SELECT 'D', 'Townsville Cinema', 12 FROM dual WHERE NOT EXISTS (SELECT * FROM cinema_halls WHERE Hall = 'D');"));

            insertion.add(conn.prepareStatement("INSERT INTO movie_screening (movieName, Hall, date, timeSlot, startTime, endTime, duration, screeningStatus) SELECT 'Barbie Movie', 'A', '12/06/2023', 'Afternoon 1', '12:15pm', '15:15pm', '3 hours', 'Available' FROM dual WHERE NOT EXISTS (SELECT * FROM movie_screening WHERE screeningID = '1');"));

            // insert customers into loyal_points so that they can keep track of their points
            insertion.add(conn.prepareStatement("INSERT IGNORE INTO loyal_points(email) SELECT email FROM users WHERE role = 'customer';"));

            // trigger to update the rate and review of a movie when a new review is added
            insertion.add(conn.prepareStatement(
                "CREATE TRIGGER update_movies_rate_review " +
                "   AFTER INSERT " +
                "   ON reviews " + 
                "   FOR EACH ROW " +
                "   BEGIN " +
                "       UPDATE movies " +
                "           SET rate = (SELECT ROUND(AVG(rating), 2) FROM reviews WHERE movieName = NEW.movieName), " +
                "               review = (SELECT COUNT(*) FROM reviews WHERE movieName = NEW.movieName) " +
                "       WHERE name = NEW.movieName; " +
                "   END"));

            // trigger to update the rate and review of a movie when a review is deleted
            insertion.add(conn.prepareStatement(
                "CREATE TRIGGER update_movies_rate_review_delete " +
                "   AFTER DELETE " +
                "   ON reviews " + 
                "   FOR EACH ROW " +
                "   BEGIN " +
                "       UPDATE movies " +
                "           SET rate = IFNULL((SELECT ROUND(AVG(rating), 2) FROM reviews WHERE movieName = OLD.movieName), 0), " +
                "               review = IFNULL((SELECT COUNT(*) FROM reviews WHERE movieName = OLD.movieName), 0) " +
                "       WHERE name = OLD.movieName; " +
                "   END"));

            // trigger to update movie name
            insertion.add(conn.prepareStatement(
                "CREATE TRIGGER update_movie_name " +
                "   AFTER UPDATE " +
                "   ON movies " + 
                "   FOR EACH ROW " +
                "   BEGIN " +
                "       UPDATE movie_screening " +
                "           SET movieName = NEW.name " +
                "       WHERE movieName = OLD.name; " +
                "   END")); 

            stmts.forEach(stmt -> {
                try {
                    stmt.execute();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            });


            insertion.forEach(inserts -> {
                try {
                    inserts.execute();
                } catch (SQLException e) {
                    if (e.getErrorCode() != 1359) {
                        System.err.println(e.getMessage());
                    }

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
                        .prepareStatement("SELECT fname, lname, email, dob, password, role, activeStatus FROM users WHERE email = ?");
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
                    values.add(rs.getString("activeStatus"));
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
