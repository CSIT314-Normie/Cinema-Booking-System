package Main.Controller.Manager;


import Main.Entity.*;


import java.util.*;

public class ManagerProfileController {
    Manager manager = new Manager();

    public ArrayList<String> getUserInfo(String email) {
        return manager.retriveManagerInfo(email);
    }
    
}
