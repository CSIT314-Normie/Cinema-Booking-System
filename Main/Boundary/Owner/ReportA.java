package Main.Boundary.Owner;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.*;
import java.util.List; 
import javax.swing.*;
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.ChartPanel;  
import com.toedter.calendar.JDateChooser;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset; 

import Main.Controller.Owner.*;

public class ReportA extends JFrame implements ActionListener {
    private ArrayList<String> userInfo;
    private JComboBox modeList;
    private String currentMode;
    private JDateChooser dateChooser;

    // private TimeSeriesCollection dataset;
    // private TimeSeries series;
    private ChartPanel chartPanel;
    private DefaultCategoryDataset dataset; 
    private JFreeChart chart;
    // ChartPanel chartPanel;

    // button to return to home page
    private JButton homeButton = new JButton("Home");

    public ReportA(ArrayList<String> userInfo) {
        super("Report A");
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
        JPanel modePanel = new JPanel(new FlowLayout());
        modePanel.setBackground(Color.LIGHT_GRAY);
        modePanel.setPreferredSize(new Dimension(1035, 50));

        JLabel modeLabel = new JLabel();
        modeLabel.setText("Report type");

        String[] typeOfReportA = new String[] {"Select","Daily", "Weekly", "Monthly"};
        modeList = new JComboBox<>(typeOfReportA);
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

        // Create initial data
        // dataset = new TimeSeriesCollection();
        // series = new TimeSeries("Price");
        // dataset.addSeries(series);

        // Create chart
        JFreeChart chart = ChartFactory.createBarChart(
                                "Amount vs. Date",    // Chart title
                                "Date",               // X-Axis label
                                "Amount",             // Y-Axis label
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

    // get the selected item from the combobox
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
        if(currentMode == "Daily"){
            DateFormat monthYearFormat = new SimpleDateFormat("MM/yyyy");
            String monthYear = monthYearFormat.format(selectedDate).toString();

            // controller to get the daily report
            DailyReportAController dailyReportController = new DailyReportAController();
            HashMap<String, ArrayList<String>> dailyReport = dailyReportController.getDailyReport(monthYear);

            List<String> dates = new ArrayList<>();
            List<Double> amount = new ArrayList<>();

            for(String x: dailyReport.get("date")){
                dates.add(x);
                
            }
            for(String x: dailyReport.get("amount")){
                Double convertAmount = Double.parseDouble(x);
                amount.add(convertAmount); 
            }

            for (int i = 0; i < dates.size(); i++) {
                dataset.addValue(amount.get(i), "Amount", dates.get(i));
            }

        } else if(currentMode == "Monthly"){
            DateFormat monthFormat = new SimpleDateFormat("yyyy");
            String year = monthFormat.format(selectedDate).toString();

            // controller to get the monthly report
            MonthlyReportAController monthlyReportController = new MonthlyReportAController();
            HashMap<String, ArrayList<String>> monthlyReport = monthlyReportController.getMonthlyReport(year);

            List<String> month = new ArrayList<>();
            List<Double> amount = new ArrayList<>();

            for(String x: monthlyReport.get("month")){
                int monthNumber = Integer.parseInt(x);
                String monthName = Month.of(monthNumber).name();
                month.add(monthName);
                
            }
            for(String x: monthlyReport.get("amount")){
                Double convertAmount = Double.parseDouble(x);
                amount.add(convertAmount); 
            }
            for (int i = 0; i < month.size(); i++) {
                dataset.addValue(amount.get(i), "Amount", month.get(i));
            }
        }
        chartPanel.repaint();
        
    }
 
}
