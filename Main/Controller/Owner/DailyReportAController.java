package Main.Controller.Owner;
import Main.Entity.*;
import java.util.*;
public class DailyReportAController {
    Payment payment = new Payment();

    public DailyReportAController(){}
    /**
     * To get daily revenue based on given month
     * @return HashMap<String, ArrayList<String>> dailyReport
     */
    public HashMap<String, ArrayList<String>> getDailyReport(String monthYear) {
        HashMap<String, ArrayList<String>> dailyReport = payment.getDailyReport(monthYear);
        return dailyReport;
    }
    
}
