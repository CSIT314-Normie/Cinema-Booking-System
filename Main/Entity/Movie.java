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


    public Movie() {}


    public DB getDB() {
        return db;
    }


    public ArrayList<String> getMovies(String email) {
        ArrayList<String> movies = new ArrayList<>();
        
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT movieName, rate, review FROM user_movies where email = ?");
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                movies.add(rs.getString("movieName"));
                movies.add(rs.getString("rate"));
                movies.add(rs.getString("review"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }


    public boolean updateMovie(String email, String movieName, String rate, String review) {
        Connection conn = this.db.getConnection();

        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE user_movies set rate = ?, review = ? where email = ? and movieName = ?");
            stmt.setString(1, rate);
            stmt.setString(2, review);
            stmt.setString(3, email);
            stmt.setString(4, movieName);
            stmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
