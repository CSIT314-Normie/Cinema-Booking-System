package Main.Controller.Admin;

import Main.Entity.UserAdmin;

public class SuspendAccountController {

    private final UserAdmin userAdmin = new UserAdmin();
    private String email;

    public SuspendAccountController() {
    }

    public boolean suspendAccount(String userEmail) {
        return this.userAdmin.suspendAccount(userEmail);
    }
    
}
