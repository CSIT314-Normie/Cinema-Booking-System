package Main.Boundary;

import Main.Controller.LogoutController;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.*;


public class Home extends JFrame implements ActionListener {
    JButton logoutButton = new JButton("Logout");

    public Home(String userRole) {
        super("Welcome to CSIT 314 Cinema Booking System - Home");
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        // TODO: Modify homepage based on user role
        System.out.println(userRole);

        // Logout button
        logoutButton.setBounds(500, 400, 100, 50);

        // add buttons to the frame
        add(logoutButton);

        // add action listener to the buttons
        logoutButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Logout":
                dispose();
                new Login();
                System.out.println("[+]Successfully logged out");
                break;
        }
    }   
}
