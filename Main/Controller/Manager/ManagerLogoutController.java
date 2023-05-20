package Main.Controller.Manager;

import Main.Entity.Manager; 

public class ManagerLogoutController {
    Manager manager = new Manager();

    public ManagerLogoutController() {
    }

     /**
     * This method is used to log out a user
     * @param userRole is the role of the user
     * @return true if the user is logged out, false otherwise
     */
    public boolean logout(String userEmail) { 
        manager.logout();
        return true;  
    } 
}
