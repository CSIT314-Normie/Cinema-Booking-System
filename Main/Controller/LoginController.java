package Main.Controller;


import Main.Entity.*;


import java.util.*;


public class LoginController {
    private User user;
    private String email;
    private String password; 

    public LoginController(String email, String password) {
        this.user = new User();
        this.email = email;
        this.password = password;
    }


    public LoginController(String userRole, String canLogin, String email) {
        if (canLogin.equals("T")) {
            this.user = new User();
            ArrayList<String> userInfo = this.user.getDB().select("*", email);
            
            switch (userRole) {
                case "Customer":
                    this.user = new Customer(userInfo.get(0), userInfo.get(1), email, userInfo.get(3), userInfo.get(4), userRole);
                    break;
                
                case "Cinema Manager":
                    this.user = new Manager(userInfo.get(0), userInfo.get(1), email, userInfo.get(3), userInfo.get(4), userRole);
                    break;
                
                case "Cinema Owner":
                    this.user = new Owner(userInfo.get(0), userInfo.get(1), email, userInfo.get(3), userInfo.get(4), userRole);
                    break;

                case "User Admin":
                    this.user = new UserAdmin(userInfo.get(0), userInfo.get(1), email, userInfo.get(3), userInfo.get(4), userRole);
                    break;
            }
        }
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
                
                case "Cinema Manager":
                    userRole = "Cinema Manager";
                    break;
                
                case "Cinema Owner":
                    userRole = "Cinema Owner";
                    break;

                case "User Admin":
                    userRole = "User Admin";
                    break;
            }

            canLogin = "T";
            userEmail = this.user.getDB().select("email", "email", this.email, "password", this.password).get(0);
        }
        return new ArrayList<>(Arrays.asList(userRole, canLogin, userEmail));
    }

    
    public boolean logout(String userRole) {
        switch (userRole) {
            case "Customer":
                Customer customer = (Customer) this.user;
                customer.logout();
                return true;

            case "Cinema Manager":
                Manager manager = (Manager) this.user;
                manager.logout();
                return true;

            case "Cinema Owner":
                Owner owner = (Owner) this.user;
                owner.logout();
                return true;

            case "User Admin":
                UserAdmin userAdmin = (UserAdmin) this.user;
                userAdmin.logout();
                return true;
        }

        return false;
    }

    public ArrayList<String[]> getAllUserAccounts() {
        if (this.user instanceof UserAdmin) {
            UserAdmin userAdmin = (UserAdmin) this.user;
            return userAdmin.getAllUserAccounts();
        }
        return null;
    }
}

