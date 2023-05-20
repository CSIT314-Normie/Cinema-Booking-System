package Main.Controller.Owner;
import Main.Entity.*;
import java.util.*;
public class MonthlyReportAController {
    Payment payment = new Payment();

    public MonthlyReportAController(){}
    /**
     * To get monthly revenue based on given year
     * @return HashMap<String, ArrayList> monthlyReport
     */
    public HashMap<String, ArrayList<String>> getMonthlyReport(String year) {
        HashMap<String, ArrayList<String>> monthlyReport = payment.getMonthlyReport(year);
        return monthlyReport;
    }
    
}
