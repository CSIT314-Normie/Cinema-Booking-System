
package Main.Boundary.Owner;


import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.awt.BorderLayout;
import javax.management.modelmbean.ModelMBean;
import javax.swing.*;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.ChartPanel;  
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import org.jfree.chart.JFreeChart;  
import org.jfree.data.category.DefaultCategoryDataset;  


public class ReportA extends JFrame implements ActionListener {
    JComboBox modeList;
    String currentMode;
    DefaultCategoryDataset dataset; 
    JFreeChart lineChart;
    ChartPanel chartPanel;

    public ReportA() {
        super("Report A");

        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);


        JPanel modePanel = new JPanel();
        modePanel.setBackground(Color.LIGHT_GRAY);
        modePanel.setSize(100,50);

        JLabel modeLabel = new JLabel();
        modeLabel.setText("Report type");

        String[] typeOfReportA = new String[] {"Select","Daily", "Weekly", "Monthly"};
        modeList = new JComboBox<>(typeOfReportA);
        modeList.addActionListener(this);

        modePanel.add(modeLabel);
        modePanel.add(modeList);
        
        JPanel reportPanel = new JPanel();
        reportPanel.setBackground(Color.GRAY );
        reportPanel.setSize(900,600);
        
        // Create chart  
        // lineChart =  createChart();
        // ChartFactory.createLineChart(  
        //     "Revenue Report", // Chart title  
        //     "Date", // X-Axis Label  
        //     "Earning", // Y-Axis Label  
        //     dataset  //dataset
        // );  
        chartPanel = new ChartPanel(createChart(createDataset()));  
        

        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setLocale(Locale.US);
        
        JPanel datePanel = new JPanel();
        dateChooser.setPreferredSize(new Dimension(250, 30));
        datePanel.add(new JLabel("Select :"));
        datePanel.add(dateChooser);

        chartPanel.setPreferredSize(new Dimension(900,500));

        reportPanel.add(datePanel,BorderLayout.PAGE_START);
        reportPanel.add(chartPanel, BorderLayout.PAGE_END);

        // if(currentMode == "Select"){
        //     dataset = new DefaultCategoryDataset();

        // }else if(currentMode != "Select"){
        //     dataset = createDataset(); 
        // }

        this.add(modePanel,BorderLayout.PAGE_START);
        this.add(reportPanel,BorderLayout.CENTER);
        
      

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        if (e.getSource()==modeList){
            System.out.println(modeList.getSelectedItem());
            currentMode = modeList.getSelectedItem().toString();
            if(currentMode == "Select"){
                dataset = new DefaultCategoryDataset();
    
            }else if(currentMode != "Select"){
                chartPanel = new ChartPanel(createChart(createDataset()));
                System.out.println("dataset");
                //lineChart.setNotify(true);
                // lineChart.getXYPlot().setDataset(createDataset());
                // chart.getXYPlot().setDataset(chart.getXYPlot().getDataset());
            }
    
        }
    }
    //call controller and load dataset
    //
    private DefaultCategoryDataset createDataset() {  

      
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();  

        /**
         * dataset.addValue(dailyEarning, mode, )
         */
        dataset.addValue(200, "series1", "2016-12-19");  
        dataset.addValue(150, "series1", "2016-12-20");  
        dataset.addValue(100, "series1", "2016-12-21");  
        dataset.addValue(210, "series1", "2016-12-22");  
        dataset.addValue(240, "series1", "2016-12-23");  
        dataset.addValue(195, "series1", "2016-12-24");  
        dataset.addValue(245, "series1", "2016-12-25");  
       
      
        return dataset;  
      }  
      /**
       * Todo: get the date on select 
       * 
       */

    /**
     * call controller 
     * 
     */
    private DefaultCategoryDataset loadData(String mode, String date) {  

      
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();  

        /**
         * dataset.addValue(dailyEarning, mode, )
         */
        dataset.addValue(200, "series1", "2016-12-19");  
        dataset.addValue(150, "series1", "2016-12-20");  
        dataset.addValue(100, "series1", "2016-12-21");  
        dataset.addValue(210, "series1", "2016-12-22");  
        dataset.addValue(240, "series1", "2016-12-23");  
        dataset.addValue(195, "series1", "2016-12-24");  
        dataset.addValue(245, "series1", "2016-12-25");  
       
      
        return dataset;  
      }
      private JFreeChart createChart(DefaultCategoryDataset dataset) {
        final  JFreeChart lineChart = ChartFactory.createLineChart(  
            "Revenue Report", // Chart title  
            "Date", // X-Axis Label  
            "Earning", // Y-Axis Label  
            dataset  //dataset
        );  
        return lineChart;
    }  
}
