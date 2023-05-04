package Main.Boundary;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.*;

import Main.Controller.LoginContoller;
import Main.Controller.MovieController;


public class Book extends JFrame implements ActionListener {
    private ArrayList<String> userInfo;

    private final JLabel userRoleLabel = new JLabel();
    private final JButton homeButton = new JButton("Home");
    private final JButton logoutButton = new JButton("Logout");
    private final JButton updateButton = new JButton("Update");
    private final JButton profileButton = new JButton("Profile");
    private final JPanel panel = new JPanel(new FlowLayout());
    private final JLabel movieNameLabel;

    private LoginContoller loginController;

    public Book(ArrayList<String> userInfo, String movieName) {
        super("Welcome to CSIT 314 Cinema Booking System - Book movie");
        this.userInfo = userInfo;
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        loginController = new LoginContoller(userInfo.get(0), userInfo.get(1), userInfo.get(2));
        userRoleLabel.setText("User Role: " + userInfo.get(0) + " | Email: " + userInfo.get(2));
        panel.setPreferredSize(new Dimension(1035, 50));

        // add user role lable and buttons to the panel
        panel.add(userRoleLabel);
        panel.add(updateButton);
        panel.add(profileButton);
        panel.add(logoutButton);
        panel.add(homeButton);

        movieNameLabel = new JLabel("Movie Name: ");

        add(panel, BorderLayout.NORTH);
        add(movieNameLabel, BorderLayout.CENTER); 
        
        homeButton.addActionListener(this);
        logoutButton.addActionListener(this);
        updateButton.addActionListener(this);
        profileButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home":
                new Home(userInfo);
                dispose();
                break;

            case "Logout":
                loginController.logout(userInfo.get(0));
                dispose();
                new Login();
                System.out.println("[+] Successfully logged out");
                break;

            case "Update":
                System.out.println("[+] Move to Update page");
                dispose();
                new Update(userInfo);
                break;

            case "Profile":
                System.out.println("[+] Move to Profile page");
                dispose();
                new Profile(userInfo);
                break; 
        }
    }
}
