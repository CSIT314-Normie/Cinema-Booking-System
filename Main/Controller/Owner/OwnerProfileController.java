package Main.Controller.Owner;


import Main.Entity.*;


import java.util.*;

public class OwnerProfileController {
    Owner owner = new Owner();

    public ArrayList<String> getUserInfo(String email) {
        return owner.retriveOwnerInfo(email);
    }
    
}
