package Main.Controller.Admin;

import Main.Entity.UserAdmin;

/**
 * This controller handles the suspension of account and movies
 * - suspend account - USER ADMIN 
 */

public class SuspendController {

    private UserAdmin userAdmin = new UserAdmin();
    private String email;

    public SuspendController() {
    }

    public boolean suspendAccount(String userEmail) {
        return this.userAdmin.suspendAccount(userEmail);
    }
    
}
