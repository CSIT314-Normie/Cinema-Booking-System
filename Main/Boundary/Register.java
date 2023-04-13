package Main.Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Register extends JFrame implements ActionListener {
    private final JTextArea fname = new JTextArea("First Name");
    private final JTextArea lname = new JTextArea("Last Name"); 
    private final JTextArea email = new JTextArea("Email");
    private final JTextArea dob = new JTextArea("Date of Birth");
    private final JTextArea password = new JTextArea("Password");


    public Register() {
        super("Welcome to CSIT 314 Cinema Booking System - REGISTRATION");
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
