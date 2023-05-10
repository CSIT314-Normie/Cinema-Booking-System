package Main.Controller.Admin;


import Main.Entity.*;


import java.util.*;

// This controller handles the creation of accounts - Done by the USER ADMIN

public class CreateAccountController {
    private final UserAdmin user;

    public CreateAccountController() {
        this.user = new UserAdmin();
    }

    public boolean createAccount(ArrayList<String> accountInfo, String role) {
        return this.user.createUser(accountInfo, role);
    }

    public boolean validateEmail(String email) {
        return this.user.validateEmail(email);
    }
}
