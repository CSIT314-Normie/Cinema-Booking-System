package Main.Boundary;


import Main.Controller.MovieController;


import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.*;


public class Review extends JFrame implements ActionListener {
    

    public Review(ArrayList<String> userInfo) {
        super("Welcome to CSIT 314 Cinema Booking System - Review");
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        
        // Set up Movie controller
        MovieController movieController = new MovieController(userInfo.get(2));

        System.out.println(movieController.getMovies());


    }
    

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
