package Main.Controller.Owner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import Main.Entity.*;
import java.util.*;
import Main.Boundary.Owner.ReportB;


public class ReportBController {

    public class DailyReportBController {
        Visitor visitor = new Visitor();
    
        public DailyReportBController(){}
        /**
         * @return dailyReport
         */
        public HashMap<String, ArrayList<String>> getDailyReport(String monthYear) {
            HashMap<String, ArrayList<String>> dailyReport = visitor.getDailyReport(monthYear);
            return dailyReport;
        }
        
    }

    public class WeeklyReportBController {
        Visitor visitor = new Visitor();
    
        public WeeklyReportBController(){}
        /**
         * @return weeklyReport
         */
        public HashMap<String, ArrayList<String>> getWeeklyReport(String monthYear) {
            HashMap<String, ArrayList<String>> weeklyReport = visitor.getWeeklyReport(monthYear);
            return weeklyReport;
        }
        
    }

    public class MonthlyReportBController {
        Visitor visitor = new Visitor();
    
        public MonthlyReportBController(){}
        /**
         * @return monthlyReport
         */
        public HashMap<String, ArrayList<String>> getMonthlyReport(String year) {
            HashMap<String, ArrayList<String>> monthlyReport = visitor.getMonthlyReport(year);
            return monthlyReport;
        }
        
    }
    
    
    

    // public ReportB generateWeeklyReport() throws Exception
    // {
    //     try{
    //         LocalDate now = LocalDate.now();  
    //         ReportB reportb = new ReportB(now.toString());
    //         reportb.generateWeeklyReport();
    //         return reportb;
    //     }
    //     catch(Exception e)
    //     {
    //         throw e;
    //     }
    // }

    // public ReportB generateMonthlyReport() throws Exception
    // {
    //     try{
    //         LocalDate now = LocalDate.now();  
    //         ReportB reportb = new ReportB(now.toString());
    //         reportb.generateMonthlyReport();
    //         return reportb;
    //     }
    //     catch(Exception e)
    //     {
    //         throw e;
    //     }
    // }
}
