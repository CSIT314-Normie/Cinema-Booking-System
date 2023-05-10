package Main.Controller.Admin;


import Main.Entity.*;


import java.util.*;


/**
 * This controller handles the creation of accounts
 * USED BY USER ADMIN
 */

public class CreateAccountController {
    private final UserAdmin user;

    public CreateAccountController() {
        this.user = new UserAdmin();
    }

    /**
     * Creates a new account by calling the createUser method in the UserAdmin class
     * @param accountInfo ArrayList of account information
     * @param role String of the role of the account
     * @return boolean of whether the account was created or not
     */
    public boolean createAccount(ArrayList<String> accountInfo, String role) {
        return this.user.createUser(accountInfo, role);
    }

    /**
     * Validates the email by calling the validateEmail method in the UserAdmin class
     * @param email String of the email to be validated
     * @return boolean of whether the email is used or not
     */
    public boolean validateEmail(String email) {
        return this.user.validateEmail(email);
    }
}
