import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;


public class DB {
    private static final String pw = new String(Base64.getDecoder().decode("akc5R2ZFNlhrTkY1QllLSkpmZTg="), StandardCharsets.UTF_8);
    private static final String url = "jdbc:mysql://booking:" + pw + "@csit314-project-do-user-13025854-0.b.db.ondigitalocean.com:25060/defaultdb?ssl-mode=REQUIRED";
    private Connection conn;

    // TODO: probably improve this somehow
    public DB() {
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connected to the database!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Data Manipulation Language(DML) Statements     
    // TODO: Implement DML statements
    // public bool select(String table, String[] values) {
    //     return true;
    // } 

    // public bool insert(String table, String[] values) {
    //     return true;
    // }

    // public bool update(String table, String[] values) {
    //     return true;
    // }

    // public bool delete(String table, String[] values) {
    //     return true;
    // }


    @org.junit.Test
    public void testConnection() throws SQLException {
        try {
            conn = DriverManager.getConnection(url);
            assertNotNull(conn);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

