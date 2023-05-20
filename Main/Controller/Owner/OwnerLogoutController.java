package Main.Controller.Owner;

import Main.Entity.Owner;

public class OwnerLogoutController {
    Owner owner = new Owner();

    public OwnerLogoutController() {
    }

     /**
     * This method is used to log out a user
     * @param userRole is the role of the user
     * @return true if the user is logged out, false otherwise
     */
    public boolean logout(String userRole) {  
        owner.logout();
        return true;
    }
}
