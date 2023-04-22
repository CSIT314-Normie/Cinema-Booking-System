package Main.Controller;

import Main.Entity.*;
import java.util.*;

public class GetAllAccounts {
    private User user = new User();

    public ArrayList<Object[]> getAllAccounts() {
        ArrayList<Object[]> allAccounts = user.getDB().selectAll("*", "*");

        return allAccounts;
    }
}
