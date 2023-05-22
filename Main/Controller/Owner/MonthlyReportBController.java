package Main.Controller.Owner;
import Main.Entity.*;
import java.util.*;

public class MonthlyReportBController {
    Owner owner = new Owner();

    public MonthlyReportBController(){}

    /**
     * To get monthly visitors - Report B
     * @return HashMap<String, ArrayList<String>> monthlyReport
     */
    public HashMap<String, ArrayList<String>> getMonthlyReport(String year) {
        HashMap<String, ArrayList<String>> monthlyReport = owner.getMonthlyVisitorsReport(year);
        return monthlyReport;
    }
}
