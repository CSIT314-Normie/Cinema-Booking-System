package Main.Boundary;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

import Main.Controller.LoginContoller;

import java.util.*;


public class Home extends JFrame implements ActionListener {
    JLabel userRoleLabel = new JLabel("User Role: ");
    JPanel panel = new JPanel();
    JButton logoutButton = new JButton("Logout");
    JButton updateButton = new JButton("Update");
    JButton profileButton = new JButton("Profile");

    private ArrayList<String> userInfo;
    private transient LoginContoller loginController;

    public Home(ArrayList<String> userInfo) {
        super("Welcome to CSIT 314 Cinema Booking System - Home");
        this.userInfo = userInfo;
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // Login "SESSION" for user to allow user to logout, can be further implemented to other pages
        loginController = new LoginContoller(userInfo.get(0), userInfo.get(1), userInfo.get(2));

        // set up panel 
        panel.setLayout(new GridLayout(1, 4));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(1035, 50));

        // TODO: Modify homepage based on user role
        // For user admin: show all users
        

        userRoleLabel.setText("User Role: " + userInfo.get(0) + " | Email: " + userInfo.get(2));
        userRoleLabel.setBounds(10, 10, 100, 50);

        // Logout button
        logoutButton.setBounds(500, 400, 100, 50);
        updateButton.setBounds(500, 400, 100, 50);

        // add user role lable and buttons to the frame
        panel.add(userRoleLabel);
        panel.add(updateButton);
        panel.add(profileButton);
        panel.add(logoutButton);
        
        
        add(panel);
        setVisible(true); // Show the frame

        // add action listener to the buttons
        logoutButton.addActionListener(this);
        updateButton.addActionListener(this);
        profileButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
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
