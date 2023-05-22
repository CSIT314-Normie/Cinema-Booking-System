package Main.Controller.Owner;
import Main.Entity.*;
import java.util.*;

public class MonthlyReportBController {
    Owner owner = new Owner();

    public MonthlyReportBController(){}

    public HashMap<String, ArrayList<String>> getMonthlyReport(String year) {
        HashMap<String, ArrayList<String>> monthlyReport = owner.getMonthlyVisitorsReport(year);
        return monthlyReport;
    }
}
