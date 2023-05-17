package Main.Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import Main.Boundary.Admin.AdminLogin;
import Main.Boundary.Customer.*;
import Main.Boundary.Manager.ManagerLogin;
import Main.Boundary.Owner.OwnerLogin;
import Main.Controller.InitController;


public class Init extends JFrame implements ActionListener {
    private final JLabel logoLabel;
    private final JButton registerButton, AdminLoginButton, ManagerLoginButton, OwnerLoginButton, CustomerLoginButton;

    private final transient InitController initController = new InitController();
    private final transient Image logo = new ImageIcon("Main/Boundary/assets/logo.png").getImage();

    public Init() {
        // Set up of the frame
        super("Welcome to CSIT 314 Cinema Booking System");
        setLayout(new GridBagLayout());
        setSize(1035, 750);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        // grid bag constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.VERTICAL;

        // Set up of the logo
        logoLabel = new JLabel(new ImageIcon(logo), JLabel.CENTER);
        logoLabel.setBounds(100, 50, 400, 400);

        // Set up of the buttons
        registerButton = new JButton("Register");
        AdminLoginButton = new JButton("Admin Login");
        ManagerLoginButton = new JButton("Manager Login");
        OwnerLoginButton = new JButton("Owner Login");
        CustomerLoginButton = new JButton("Customer Login");

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(registerButton);
        buttonPanel.add(AdminLoginButton);
        buttonPanel.add(ManagerLoginButton);
        buttonPanel.add(OwnerLoginButton);
        buttonPanel.add(CustomerLoginButton);


        // add logo and button panel to the frame
        add(logoLabel, gbc);
        add(buttonPanel, gbc); // Add the panel instead of individual buttons

        // add action listener to the buttons
        registerButton.addActionListener(this);
        AdminLoginButton.addActionListener(this);
        ManagerLoginButton.addActionListener(this);
        OwnerLoginButton.addActionListener(this);
        CustomerLoginButton.addActionListener(this);


        // Check if the database is connected
        if (initController.isInit()) {
            System.out.println("Compiled");
        } else {
            JOptionPane.showMessageDialog(null, "Database is not connected\nRestart application", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Action listener for the buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Register":
                dispose();
                new Register();
                break;

            case "Admin Login":
                dispose();
                new AdminLogin();
                break;

            case "Manager Login":
                dispose();
                new ManagerLogin();
                break;

            case "Owner Login":
                dispose();
                new OwnerLogin();
                break;

            case "Customer Login":
                dispose();
                new CustomerLogin();
                break;

        }
    }
}