package Main.Controller.Owner;

import java.util.ArrayList;
import java.util.HashMap;

import Main.Entity.Payment;

public class WeeklyReportAController {
    Payment payment = new Payment();

    public HashMap<String, ArrayList<String>> getWeeklyReport(ArrayList<String> dates) {
        return payment.getWeeklyReport(dates);
    }
}
