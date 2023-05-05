package Main.Controller;


import Main.Entity.*;


import java.util.*;


public class CreateAccountController {
    private UserAdmin user;  

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
