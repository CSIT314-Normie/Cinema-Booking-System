package Main.Controller.Owner;
import Main.Entity.*;
import java.util.*;

public class DailyReportBController {
    Owner owner = new Owner();

    public DailyReportBController(){}

    /**
     * To get daily visitors - report B
     * @return HashMap<String, ArrayList<String>> dailyReport
     */
    public HashMap<String, ArrayList<String>> getDailyReport(String monthYear) {
        HashMap<String, ArrayList<String>> dailyReport = owner.getDailyVisitorsReport(monthYear);
        return dailyReport;
    }

}
