package Main.Entity;


import Database.DB;


import java.util.*;
import java.sql.*;


public class Movie {
    private DB db = new DB();
    private String name;
    private String imageName;
    private String rate;
    private String review;
    
    public Movie(String name, String imageName, String rate, String review) {
        this.name = name;
        this.imageName = imageName;
        this.rate = rate;
        this.review = review;
    }


    public Movie() {
    }


    public DB getDB() {
        return db;
    }


    public ArrayList<String> getMovies(String email) {
        ArrayList<String> movies = new ArrayList<>();
        
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT movieName FROM user_movies where email = ?");
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                movies.add(rs.getString("movieName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }
}
