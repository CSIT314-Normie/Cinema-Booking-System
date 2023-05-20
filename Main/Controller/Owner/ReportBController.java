package Main.Controller.Owner;

import java.time.LocalDate;

import Main.Boundary.Owner.ReportB;


public class ReportBController {

    public ReportB generateDailyReport() throws Exception
    {
        try{
            LocalDate now = LocalDate.now();  
            ReportB reportb = new ReportB(now.toString());
            reportb.generateDailyReport();
            return reportb;
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    public ReportB generateWeeklyReport() throws Exception
    {
        try{
            LocalDate now = LocalDate.now();  
            ReportB reportb = new ReportB(now.toString());
            reportb.generateWeeklyReport();
            return reportb;
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    public ReportB generateMonthlyReport() throws Exception
    {
        try{
            LocalDate now = LocalDate.now();  
            ReportB reportb = new ReportB(now.toString());
            reportb.generateMonthlyReport();
            return reportb;
        }
        catch(Exception e)
        {
            throw e;
        }
    }
}
