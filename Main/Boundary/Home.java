package Main.Boundary;


import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.*;


public class Home extends JFrame implements ActionListener {
    JLabel userRoleLabel = new JLabel("User Role: ");

    JButton logoutButton = new JButton("Logout");
    JButton updateButton = new JButton("Update");
    JButton profileButton = new JButton("Profile");

    private ArrayList<String> userInfo;

    public Home(ArrayList<String> userInfo) {
        super("Welcome to CSIT 314 Cinema Booking System - Home");
        this.userInfo = userInfo;
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        // TODO: Modify homepage based on user role
        userRoleLabel.setText("User Role: " + userInfo.get(0) + " | Email: " + userInfo.get(2));
        userRoleLabel.setBounds(10, 10, 200, 50);

        // Logout button
        logoutButton.setBounds(500, 400, 100, 50);
        updateButton.setBounds(500, 400, 100, 50);

        // add buttons to the frame
        add(logoutButton);
        add(updateButton);
        add(profileButton);

        // add action listener to the buttons
        logoutButton.addActionListener(this);
        updateButton.addActionListener(this);
        profileButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Logout":
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
