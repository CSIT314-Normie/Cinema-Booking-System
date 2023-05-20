package Main.Boundary.Owner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


import Main.Controller.Owner.*;

public class OwnerHome extends JFrame implements ActionListener{
    private final ArrayList<String> userInfo;

    private final JLabel userRoleLabel = new JLabel();
    private final JPanel panel = new JPanel(new FlowLayout());
    private final JButton logoutButton = new JButton("Logout");
    private final JButton updateButton = new JButton("Update");
    private final JButton profileButton = new JButton("Profile");
    private final JButton reportAButton = new JButton("Report A");
    private final JButton reportBButton = new JButton("Report B");
   
    private final transient OwnerLoginController loginController;



    public OwnerHome(ArrayList<String> userInfo) {
        super("CSIT 314 Cinema Booking System - Home");
        this.userInfo = userInfo;
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        // Login "SESSION" for user to allow user to logout, can be further implemented
        loginController = new OwnerLoginController(userInfo.get(0), userInfo.get(1), userInfo.get(2));
        userRoleLabel.setText("User Role: " + userInfo.get(0) + " | Email: " + userInfo.get(2));
        panel.setPreferredSize(new Dimension(1035, 50));

        panel.add(userRoleLabel);
        panel.add(updateButton);
        panel.add(profileButton);
        panel.add(logoutButton);
        panel.add(reportAButton);
        panel.add(reportBButton);

        add(panel, BorderLayout.NORTH);
        pack();

        reportAButton.addActionListener(this);
        reportBButton.addActionListener(this);  
        logoutButton.addActionListener(this);
        updateButton.addActionListener(this);
        profileButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Logout":
                OwnerLogoutController logoutController = new OwnerLogoutController();
                logoutController.logout(userInfo.get(2));

                dispose();
                new OwnerLogin();
                System.out.println("[+] Successfully logged out");
                break;

            case "Update":
                System.out.println("[+] Move to Update page");
                dispose();
                new OwnerUpdate(userInfo);
                break;

            case "Profile":
                System.out.println("[+] Move to Profile page");
                dispose();
                new OwnerProfile(userInfo);
                break;

            case "Report A":
                System.out.println("[+] Cinema Owner - Move to Report A page");
                dispose();
                new ReportA();
                break;
            case "Report B":
                System.out.println("[+] Cinema Owner - Move to Report B page");
                dispose();
                //new ReportB();
                break;
        }
    }
}
