package Main.Boundary.CinemaOwner;


import javax.swing.JComboBox;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*; 
import java.util.*;
 

public class ReportA extends JFrame implements ActionListener

{
    public ReportA(){
        super("Revenue for each cinema");
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null); 
        setVisible(true);

        // combobox for the type of report for ReportA (Revenue for each cinema)
        String[] typeOfReportA = {"Hourly", "Daily", "Weekly"};

        
        //JComboBox<String> ReportAList = new JComboBox(typeOfReportA);       
        //add(ReportAList);
        //this.pack();
    }   
    
    // action listener for comboBox
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
}



   

    
    
