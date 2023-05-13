package Main.Boundary.CinemaOwner;

import javax.swing.JComboBox;

import javafx.event.ActionEvent;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*; 
import java.util.*;
 



public class ReportA extends JFrame implements ActionListener

{
    public ReportA(){
        // to set up frame
        super("Report of Revenue for each Cinema");
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        


           // combobox for the type of report for ReportA (Revenue for each cinema)
           String[] typeOfReportA = {"Hourly", "Daily", "Weekly"};

           JComboBox ReportAList = new JComboBox(typeOfReportA);
           ReportAList.setSelectedIndex(3);
           ReportAList.addActionListener(this);
        
        this.add(ReportAList);

        this.pack();
        this.setVisible(true);
    }
    
    // action listener for comboBox
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
}



   

    
    
