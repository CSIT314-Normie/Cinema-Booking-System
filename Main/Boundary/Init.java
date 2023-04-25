package Main.Boundary;

import Main.Controller.InitController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Init extends JFrame implements ActionListener {
    private final JFrame pageFrame = new JFrame();
    private final JLabel logoLabel;
    private final JButton registerButton, loginButton;

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
        setVisible(true); // Show the frame

        // grid bag constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.VERTICAL;

        // Set up of the logo
        logoLabel = new JLabel(new ImageIcon(logo), JLabel.CENTER);
        logoLabel.setBounds(100, 50, 400, 400);

        // Set up of the buttons
        registerButton = new JButton("Register");
        loginButton = new JButton("Login");

        // set button middle of the screen
        registerButton.setBounds(500, 550, 100, 40);
        loginButton.setBounds(500, 600, 100, 40);

        // add logo and buttons to the frame
        add(logoLabel, gbc);
        add(registerButton, gbc);
        add(loginButton, gbc);

        // add action listener to the buttons
        registerButton.addActionListener(this);
        loginButton.addActionListener(this);

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
                // dispose the current frame and open the register page
                dispose();
                pageFrame.setVisible(false);
                new Register();
                break;

            case "Login":
                // dispose the current frame and open the login page
                dispose();
                pageFrame.setVisible(false);
                new Login();
                break;
            default:
                break;
        }
    }
}
