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

    public Book(ArrayList<String> userInfo, String movieName) {
        super("Welcome to CSIT 314 Cinema Booking System - Book movie");
        this.userInfo = userInfo;
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        System.out.println("Book movie: " + movieName);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Logout":
                break;

            case "Update":
                break;

            case "Profile":
                break;
        }
    }
}
