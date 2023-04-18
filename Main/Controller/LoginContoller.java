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

        if (!role.isEmpty()) {
            switch (role.get(0)) {
                case "Customer":
                    userRole = "Customer";
                    canLogin = "T";
                    break;
                
                case "Manager":
                    userRole = "Manager";
                    canLogin = "T";
                    break;
                
                case "Owner":
                    userRole = "Owner";
                    canLogin = "T";
                    break;

                case "Admin":
                    userRole = "Admin";
                    canLogin = "T";
                    break;
            }
        }
        return new ArrayList<>(Arrays.asList(userRole, canLogin));
    }
}

