package Main.Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Init extends JFrame implements ActionListener {
    private final JFrame pageFrame = new JFrame();
    private final JButton registerButton, loginButton;


    public Init() {
        // Set up of the frame
        super("Welcome to CSIT 314 Cinema Booking System");
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        // Set up of the buttons
        registerButton = new JButton("Register");
        loginButton = new JButton("Login");
        
        // set button middle of the screen
        registerButton.setBounds(500, 300, 100, 50);
        loginButton.setBounds(500, 400, 100, 50);

        // add buttons to the frame
        add(registerButton);
        add(loginButton);
        
        // add action listener to the buttons
        registerButton.addActionListener(this);
        loginButton.addActionListener(this);

        System.out.println("Compiled");
        
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

