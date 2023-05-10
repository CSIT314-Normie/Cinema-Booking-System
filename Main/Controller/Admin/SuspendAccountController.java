package Main.Controller.Admin;

import Main.Entity.*;


/**
 * This controller handles the creation of accounts
 * USED BY USER ADMIN
 */


public class SuspendAccountController {

    private final UserAdmin userAdmin = new UserAdmin();
    private String email;

    public SuspendAccountController() {
    }

    /**
     * Suspends an account by calling the suspendAccount method in the UserAdmin class
     * @param userEmail String of the email of the account to be suspended
     * @return boolean of whether the account was suspended or not
     */
    public boolean suspendAccount(String userEmail) {
        return this.userAdmin.suspendAccount(userEmail);
    }
    
}
