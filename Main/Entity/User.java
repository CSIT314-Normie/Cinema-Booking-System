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

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
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
