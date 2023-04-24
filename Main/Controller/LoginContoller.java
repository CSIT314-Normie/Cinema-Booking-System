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
        String userEmail ="";       

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
            userEmail =  this.user.getDB().select("email", "email", this.email, "password", this.password).get(0);
        }
        return new ArrayList<>(Arrays.asList(userRole, canLogin, userEmail));
    }

    
    public boolean logout() {
        // Destroy user object
        if (this.user.logout()) {
            this.email = "";
            return true;
            
        }

        return false;
    }
}

