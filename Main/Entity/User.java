package Main.Entity;


import Database.DB;


public class User {
    private DB db = new DB();
    private String fname;
    private String lname;
    private String email;
    private String dob;
    private String password;
    private String role;

    public User() {
        this.fname = "";
        this.lname = "";
        this.email = "";
        this.dob = "";
        this.password = "";
        this.role = "";
    }

    public User(String fname, String lname, String email, String dob, String password, String role) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.dob = dob;
        this.password = password;
        this.role = role;
    }
   
    public DB getDB() {
        return db;
    }

    public boolean logout() {
        this.email = "";
        this.fname = "";
        this.lname = "";
        this.dob = "";
        this.password = "";
        this.role = "";
        
        return true;
    }
}
