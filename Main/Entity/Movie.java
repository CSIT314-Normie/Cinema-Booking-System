package Main.Entity;


import Database.DB;


import java.util.*;
import java.sql.*;


public class Movie {
    private DB db = new DB();
    private Connection conn = this.db.getConnection();
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


    public ArrayList<String> getMovies() {
        ArrayList<String> movies = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movies");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                movies.add(rs.getString("name"));
                movies.add(rs.getString("image"));
                movies.add(rs.getString("rate"));
                movies.add(rs.getString("review"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }


    public ArrayList<String> getUserWatchedMovies(String email) {
        ArrayList<String> movies = new ArrayList<>();
        
        try {
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


    public boolean updateMovieRR(String email, String movieName, String rate, String review) {
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


    public boolean insertMovie(ArrayList<String> values) {
        PreparedStatement stmt;
    
        try {
            stmt = conn.prepareStatement("INSERT INTO movies (name, image, rate, review) VALUES (?, ?, ?, ?)");
            stmt.setString(1, values.get(0));
            stmt.setString(2, values.get(1));
            stmt.setString(3, values.get(2));
            stmt.setString(4, values.get(3));
            stmt.executeUpdate();

            System.out.println(values.get(0) + " has been inserted into the database");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return true;
    }
}
