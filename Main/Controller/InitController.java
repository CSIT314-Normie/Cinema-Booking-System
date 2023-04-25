package Main.Controller;


import Database.DB;


public class InitController {
    private DB db = new DB();

    
    public boolean isInit() {
        try {
            this.db.getConnection();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
