package Main.Boundary.Owner;

import java.awt.*;
import java.awt.event.*; 
import java.text.*;

import java.time.Month;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset; 

import Main.Controller.Owner.*;


public class ReportB extends JFrame implements ActionListener {
    private ArrayList<String> userInfo;
    
    private JComboBox modeList;
    private String currentMode;
    private JDateChooser dateChooser;

    // button to return to home page
    private JButton homeButton = new JButton("Home");

    private ChartPanel chartPanel;
    private DefaultCategoryDataset dataset; 
    private JFreeChart chart;

    public ReportB(ArrayList<String> userInfo) {
        super("Report B");  
        this.userInfo = userInfo;
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        // top panel to return to home page
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setPreferredSize(new Dimension(1035, 40));
        topPanel.add(homeButton);
 
        // Create dataset
        dataset = new DefaultCategoryDataset();
 
        // mode panel to select report type
        JPanel modePanel = new JPanel();
        modePanel.setBackground(Color.LIGHT_GRAY);
        modePanel.setPreferredSize(new Dimension(1035, 50));

        JLabel modeLabel = new JLabel();
        modeLabel.setText("Report type");

        String[] typeOfReportB = new String[] {"Select","Daily", "Weekly", "Monthly"};
        modeList = new JComboBox<>(typeOfReportB);
        modeList.addActionListener(this);

        modePanel.add(modeLabel);
        modePanel.add(modeList);
        
         // report panel to display the chart
        JPanel reportPanel = new JPanel();
        reportPanel.setBackground(Color.GRAY );
        reportPanel.setPreferredSize(new Dimension(900, 630));

        dateChooser = new JDateChooser();
        dateChooser.setLocale(Locale.US);
        dateChooser.setPreferredSize(new Dimension(250, 30));
  
        // btn to trigger the combobox and chart
        JButton confirmBtn = new JButton("Confirm");
        confirmBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                updateChart();
            }
        });

        JPanel datePanel = new JPanel();
        datePanel.add(new JLabel("Select :"));
        datePanel.add(dateChooser);
        datePanel.add(confirmBtn);

        // Create chart
        JFreeChart chart = ChartFactory.createBarChart(
                                "No. of Vistors",    // Chart title
                                "Date",               // X-Axis label
                                "Visitors",             // Y-Axis label
                                dataset,              // Dataset
                                PlotOrientation.VERTICAL,
                                true,
                                true,
                                false
        );

        // Create chart panel
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(900,500));

        reportPanel.add(datePanel,BorderLayout.PAGE_START);
        reportPanel.add(chartPanel, BorderLayout.PAGE_END);


        // add all panels to the frame
        this.add(topPanel,BorderLayout.NORTH);
        this.add(modePanel,BorderLayout.CENTER);
        this.add(reportPanel,BorderLayout.SOUTH); 

        // add action listener to home button to return to owner's home page
        homeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new OwnerHome(userInfo);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==modeList){
            System.out.println(modeList.getSelectedItem());
            currentMode = modeList.getSelectedItem().toString();
        }
    }

    /**
     * Replot the chart
     */
    private void updateChart(){
        dataset.clear();
        // Get selected date from the date chooser
        Date selectedDate = dateChooser.getDate();
        if("Daily".equals(currentMode)){
            DateFormat monthYearFormat = new SimpleDateFormat("MM/yyyy");
            String monthYear = monthYearFormat.format(selectedDate).toString();
            DailyReportBController dailyReportController = new DailyReportBController();
            HashMap<String, ArrayList<String>> dailyReport = dailyReportController.getDailyReport(monthYear);
            List<String> dates = new ArrayList<>();
            List<Double> visitors = new ArrayList<>();

            for(String x: dailyReport.get("date")){
                dates.add(x);
            }

            for(String x: dailyReport.get("totalSeats")){
                Double convertSeatID = Double.parseDouble(x);
                visitors.add(convertSeatID); 
            }

            for (int i = 0; i < dates.size(); i++) {
                dataset.addValue(visitors.get(i), "visitors", dates.get(i));
            }

        }else if("Monthly".equals(currentMode)){
            DateFormat monthFormat = new SimpleDateFormat("yyyy");
            String year = monthFormat.format(selectedDate).toString();
            MonthlyReportBController monthlyReportController = new MonthlyReportBController();
            HashMap<String, ArrayList<String>> monthlyReport = monthlyReportController.getMonthlyReport(year);
            List<String> month = new ArrayList<>();
            List<Double> visitors = new ArrayList<>();

            for(String x: monthlyReport.get("month")){
                int monthNumber = Integer.parseInt(x);
                String monthName = Month.of(monthNumber).name();
                month.add(monthName); 
            }

            for(String x: monthlyReport.get("totalSeats")){
                Double convertSeatID = Double.parseDouble(x);
                visitors.add(convertSeatID); 
            }

            for (int i = 0; i < month.size(); i++) {
                dataset.addValue(visitors.get(i), "visitors", month.get(i));
            }

        } else if("Weekly".equals(currentMode)){
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String selectedDateStr = dateFormat.format(selectedDate).toString();

            try {
                selectedDate = dateFormat.parse(selectedDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            } 
            
            // get the week of the month
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedDate);

            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

            // get date of the first day of the week
            ArrayList<String> datesOfWeek = new ArrayList<>();

            // Set the calendar to the first day of the specified week
            calendar.set(Calendar.WEEK_OF_YEAR, weekOfYear);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); 

            for (int i = 1; i <= 7; i++) { 
                Date date = calendar.getTime();
                String dateOfWeek = dateFormat.format(date).toString();

                datesOfWeek.add(dateOfWeek);

                // move to the next day
                calendar.add(Calendar.DAY_OF_WEEK, 1);
            }

            System.out.println("datesOfWeek: " + datesOfWeek);

            WeeklyReportBController weeklyReportBController = new WeeklyReportBController();
            HashMap<String, ArrayList<String>> weeklyReport = weeklyReportBController.getWeeklyReport(datesOfWeek);

            List<String> week = new ArrayList<>();
            List<Double> visitors = new ArrayList<>();

            for(String x: weeklyReport.get("day")){
                week.add(x);
            }

            for(String x: weeklyReport.get("totalSeats")){
                Double convertSeatID = Double.parseDouble(x);
                visitors.add(convertSeatID); 
            }

            for (int i = 0; i < week.size(); i++) {
                dataset.addValue(visitors.get(i), "visitors", week.get(i));
            }
        }
        chartPanel.repaint();
    }
}
