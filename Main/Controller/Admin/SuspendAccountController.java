package Main.Controller.Admin;

import Main.Entity.UserAdmin;

/*
 * This controller handles the suspension of accounts - Done by the USER ADMIN
 */

public class SuspendAccountController {

    private final UserAdmin userAdmin = new UserAdmin();
    private String email;

    public SuspendAccountController() {
    }

    public boolean suspendAccount(String userEmail) {
        return this.userAdmin.suspendAccount(userEmail);
    }
    
}
