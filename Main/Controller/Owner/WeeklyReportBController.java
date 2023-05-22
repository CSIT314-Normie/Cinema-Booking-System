package Main.Controller.Owner;
import Main.Entity.*;
import java.util.*;

public class WeeklyReportBController {
    Owner owner = new Owner();

    public WeeklyReportBController(){}

    /**
     * To get weekly visitors - Report B
     * @return HashMap<String, ArrayList<String>> dailyReport
     */
    public HashMap<String, ArrayList<String>> getWeeklyReport(ArrayList<String> datesOfWeek) {
        HashMap<String, ArrayList<String>> weeklyReport = owner.getWeeklyVisitorsReport(datesOfWeek);
        return weeklyReport;
    }

}
