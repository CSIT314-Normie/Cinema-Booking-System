package Main.Controller;


import Main.Entity.*;


import java.util.*;

/**
 * This controller handles updating of info
 * - update of profile info - all USER
 * - update of users account information - USER ADMIN
 * - update of ticket price - CINEMA MANAGER
 * - update of movie session (to be implemented) - CINEMA MANAGER
 */

public class UpdateController {
    private User user = new User();

    public boolean updateAccount(ArrayList<String> updatedInfo, String email) {
        return user.updateAcc(updatedInfo, email);
    }

    /** 
     * To handle the update of account information - USER ADMIN
     * @param role only user admin can update account information
     * @param modifiedAcc arraylist of modified account information
     * @param userEmail email of the user to be updated
     * @return boolean true if account information is updated successfully, false otherwise
     */
    public boolean updateAccountInfo(String role, ArrayList<String> modifiedAcc, String userEmail) {
        if (role.equals("User Admin")) {
            UserAdmin userAdmin = new UserAdmin();
            return userAdmin.updateUserAccInfo(modifiedAcc, userEmail); 
        } else {
            return false;
        } 
    }

    /**
     * To handle the update of movie information - CINEMA MANAGER
     * @param role only cinema manager can update ticket price 
     * @param type type of ticket
     * @param price price of ticket
     * @return
     */
    public boolean updateTicketPrice(String role, String type, String price) { 
        if (role.equals("Cinema Manager")) {
            Manager manager = new Manager();
            return manager.updateTicketPrice(type, price);
        } else {
            return false;
        } 
    }
}
