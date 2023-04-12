package Main.Boundary;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.function.Predicate;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Init extends JFrame implements ActionListener {
    private JButton registerButton, loginButton;

    public Init() {
        // Set up of the frame
        super("Welcome to CSIT 314 Cinema Booking System");
        setLayout(new FlowLayout());
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        // Set up of the buttons
        registerButton = new JButton("Register");
        loginButton = new JButton("Login");
        add(registerButton);
        add(loginButton);
        
        registerButton.addActionListener(this);
        loginButton.addActionListener(this);


    }

    // Action listener for the buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Register":
                new Register();
                break;
            case "Login":
                new Login();
                break;
            default:
                break;
        }
    }
}

