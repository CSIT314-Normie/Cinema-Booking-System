package Main.Entity;

import Database.DB;


import java.util.*;
import java.sql.*;

public class SeatReserved {
    private DB db = new DB();
    private Connection conn = this.db.getConnection();

    private String reservationID;
    private String seatID;
    private String userEmail;
    private String movieName;
    private String screeningID;

    public SeatReserved() {

    }

    public DB getDb() {
        return db;
    } 
}
