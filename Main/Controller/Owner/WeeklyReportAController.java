package Main.Controller.Owner;

import java.util.ArrayList;
import java.util.HashMap;

import Main.Entity.Payment;

public class WeeklyReportAController {
    Payment payment = new Payment();

    public WeeklyReportAController() {
    }

    /**
     * Get all payments made in a week - REPORT A
     * @param dates of the week
     * @return Hashmap of all payments made in a week
     */
    public HashMap<String, ArrayList<String>> getWeeklyReport(ArrayList<String> datesOfWeek) {
        return payment.getWeeklyReport(datesOfWeek);
    }
}
