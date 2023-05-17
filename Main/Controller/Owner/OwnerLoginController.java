package Main.Controller.Owner;


import Main.Entity.*;


import java.util.*;


public class OwnerLoginController {
    private User user;
    private String email;
    private String password;

    /**
     * This constructor is used to create a LoginController object
     * @param email is the email of the user
     * @param password is the password of the user
     */
    public OwnerLoginController(String email, String password) {
        this.user = new User();
        this.email = email;
        this.password = password;
    }

    /**
     * This constructor is used to create a LoginController object with the user's role
     * @param userRole is the role of the user
     * @param canLogin is a String that indicates whether the user can log in or not
     * @param email is the email of the user
     */
    public OwnerLoginController(String userRole, String canLogin, String email) {
        if (canLogin.equals("T")) {
            this.user = new User();
            ArrayList<String> userInfo = this.user.getDB().select("*", email);
            this.user = new Owner(userInfo.get(0), userInfo.get(1), email, userInfo.get(3), userInfo.get(4), userRole);
        }
    }

    /**
     * This method is used to log in a user
     * @return an ArrayList of Strings that contains the user's role, whether the user can log in or not, and the user's email
     */
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

    /**
     * This method is used to log out a user
     * @param userRole is the role of the user
     * @return true if the user is logged out, false otherwise
     */
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

    /**
     * This method is used to get ALL user accounts from the database
     * @return an ArrayList of String arrays that contains the information of all user accounts
     */
    public ArrayList<String[]> getAllUserAccounts() {
        if (this.user instanceof UserAdmin) {
            UserAdmin userAdmin = (UserAdmin) this.user;
            return userAdmin.getAllUserAccounts();
        }
        return null;
    }
}

