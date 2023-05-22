package Main.Controller.Admin;
 
import Main.Entity.UserAdmin; 

public class AdminLogoutController {

    UserAdmin userAdmin = new UserAdmin();

    public AdminLogoutController() {
    }

    /**
     * This method is used to log out a user
     * @param userRole is the role of the user
     * @return true if the user is logged out, false otherwise
     */
    public boolean logout() { 
        userAdmin.logout();
        return true;
    }
    
}
