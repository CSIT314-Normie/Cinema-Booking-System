package Main.Controller.Admin;


import Main.Entity.*;


import java.util.*;

/*
 * This controller handles updating of users account information - Done by USER ADMIN 
*/

public class UpdateUserAccountInfoController {
    private final User user = new User();

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

    
}
