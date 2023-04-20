package Main.Controller;

import Main.Entity.*;


import java.util.*;


public class LoginContoller {
    private User user;
    private String email;
    private String password;


    public LoginContoller(String email, String password) {
        this.user = new User();
        this.email = email;
        this.password = password;
    }


    public ArrayList<String> login() {
        ArrayList<String> role = this.user.getDB().select("role", "email", this.email, "password", this.password);
        String userRole = "";
        String canLogin = "F";
        String userFname = "";
        String userLname = "";
        String userEmail ="";
        String userDOB = "";
        

        if (!role.isEmpty()) {
            switch (role.get(0)) {
                case "Customer":
                    userRole = "Customer";
                    break;
                
                case "Manager":
                    userRole = "Manager";
                    break;
                
                case "Owner":
                    userRole = "Owner";
                    break;

                case "Admin":
                    userRole = "Admin";
                    break;
            }

            canLogin = "T";
            userFname =  this.user.getDB().select("fname", "email", this.email, "password", this.password).get(0);
            userLname =  this.user.getDB().select("lname", "email", this.email, "password", this.password).get(0);
            userEmail =  this.user.getDB().select("email", "email", this.email, "password", this.password).get(0);
            userDOB =  this.user.getDB().select("dob", "email", this.email, "password", this.password).get(0);
        }
        return new ArrayList<>(Arrays.asList(userRole, canLogin, userFname, userLname, userEmail, userDOB));
    }
}

