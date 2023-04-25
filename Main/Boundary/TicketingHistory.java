package Main.Boundary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import java.awt.*; 
import javax.swing.*;
import java.util.*;

import javax.swing.JFrame;

import Main.Controller.TicketingHistoryController;

public class TicketingHistory extends JFrame implements ActionListener{
    private ArrayList<String> userInfo;
    private final JButton homeButton = new JButton("Home");

    public TicketingHistory(ArrayList<String> userInfo) {
        super("Welcome to CSIT 314 Cinema Booking System - Ticketing History");
        this.userInfo = userInfo;
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        // display the ticketing history (ie, the list of movies the user has booked)
        System.out.println("Customer " + userInfo.get(0) + "Ticketing History");
        
        add(homeButton);

        homeButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO add  action handling code here
        if (e.getSource() == homeButton) {
            new Home(userInfo);
            dispose();
        }
    }
    
}
