package Main.Entity;


import Database.DB;


import java.util.*;
import java.sql.*;


public class Movie {
    private final DB db = new DB();
    private final Connection conn = this.db.getConnection();  

    public Movie() {}

    public DB getDB() {
        return db;
    } 

    /**
     * To get all movies - regardless of status
     * @return ArrayList<String> allMovies
     */
    public ArrayList<String> getAllMovies() {
        ArrayList<String> allMovies = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movies");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                allMovies.add(rs.getString("name"));
                allMovies.add(rs.getString("image"));
                allMovies.add(rs.getString("rate"));
                allMovies.add(rs.getString("review"));
                allMovies.add(rs.getString("description"));
                allMovies.add(rs.getString("status"));
                allMovies.add(rs.getString("duration"));
            }

        } catch (SQLException e) {
           System.err.println(e.getMessage());
        }

        return allMovies;
    }

    /**
     * To get all available movies 
     * @return ArrayList<String> allMovies
     */
    public ArrayList<String> getAvailableMovies() {
        ArrayList<String> allMovies = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movies WHERE status = 'Available'");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                allMovies.add(rs.getString("name"));
                allMovies.add(rs.getString("image"));
                allMovies.add(rs.getString("rate"));
                allMovies.add(rs.getString("review"));
                allMovies.add(rs.getString("description"));
                allMovies.add(rs.getString("status"));
                allMovies.add(rs.getString("duration"));
            }

        } catch (SQLException e) {
           System.err.println(e.getMessage());
        }

        return allMovies;
    }

    /**
     * For user to search for a movie by title - CUSTOMER
     * @param String searchQuery
     * @return ArrayList<String> movies
     */
    public ArrayList<String> searchMovieTitle(String searchQuery) {
        ArrayList<String> movies = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movies WHERE name LIKE ? AND status = 'Available'");
            stmt.setString(1, "%" + searchQuery + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                movies.add(rs.getString("name"));
                movies.add(rs.getString("image"));
                movies.add(rs.getString("rate"));
                movies.add(rs.getString("review"));
                movies.add(rs.getString("description"));
                movies.add(rs.getString("status"));
                movies.add(rs.getString("duration"));
            }
        } catch (SQLException e) {
           System.err.println(e.getMessage());
        }

        return movies;
    }

    /**
     * To get all movies a user has booked/watched - CUSTOMER
     * @param String email
     * @return ArrayList<String> movies
     */
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
           System.err.println(e.getMessage());
        }

        return movies;
    }

    /**
     * To search for a movie - MANAGER
     * @param searchQuery
     * @return
     */
    public ArrayList<String> searchMovie(String searchQuery) {
        ArrayList<String> movies = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movies WHERE name LIKE ?");
            stmt.setString(1, "%" + searchQuery + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                movies.add(rs.getString("name"));
                movies.add(rs.getString("image"));
                movies.add(rs.getString("rate"));
                movies.add(rs.getString("review"));
                movies.add(rs.getString("description"));
                movies.add(rs.getString("status"));
                movies.add(rs.getString("duration"));
            }

        } catch (SQLException e) {
           System.err.println(e.getMessage());
        }

        return movies;

    }

    /**
     * To insert a movie into the database - Manager
     * @param values
     * @return ArrayList<String> movies
     */
    public boolean insertMovie(ArrayList<String> values) {
        PreparedStatement stmt;
    
        try {
            // Comments on this function are in the same function in User.java
            stmt = conn.prepareStatement("INSERT INTO movies (name, image, rate, review, description, status, duration) VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, values.get(0));
            stmt.setString(2, values.get(1));
            stmt.setString(3, "0");
            stmt.setString(4, "0");
            stmt.setString(5, values.get(2));
            stmt.setString(6, values.get(3));
            stmt.setString(7, values.get(4));
            stmt.executeUpdate();

            System.out.println(values.get(0) + " has been inserted into the database");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return true;
    }

    /**
     * To update a movie's information - Manager
     * @param values
     * @param movieName
     * @return ArrayList<String> movies
     */
    public boolean updateMovieInfo(ArrayList<String> values, String movieName) {
        PreparedStatement stmt;
    
        try {
            // Comments on this function are in the same function in User.java
            stmt = conn.prepareStatement("UPDATE movies SET name = ?, description = ?, status = ?, duration = ? WHERE name = ?");
            stmt.setString(1, values.get(0));
            stmt.setString(2, values.get(1));
            stmt.setString(3, values.get(2));
            stmt.setString(4, values.get(3));
            stmt.setString(5, movieName); 
            stmt.executeUpdate();

            System.out.println(movieName + " has been updated in the database");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return true;
    }

    /**
     * To suspend a movie's status after viewing period - Manager
     * @param movieName
     * @return ArrayList<String> movies
     */
    public boolean suspendMovie(String movieName) {
        PreparedStatement stmt;
    
        try {
            // Comments on this function are in the same function in User.java
            stmt = conn.prepareStatement("UPDATE movies set status = ? name = ?");
            stmt.setString(1,  "suspended");
            stmt.setString(5, movieName); 
            stmt.executeUpdate();

            System.out.println(movieName + " has been suspended in the database");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return true;
    }

}
