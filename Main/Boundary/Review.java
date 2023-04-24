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

        // Create a panel to hold the movie details
        JPanel moviePanel = new JPanel(new GridLayout(0, 3));

        // Create header labels
        JLabel movieHeader = new JLabel("Movie");
        JLabel ratingHeader = new JLabel("Rating");
        JLabel reviewHeader = new JLabel("Review");

        // Set padding for header labels
        int topPadding = 20;
        int leftPadding = 50;
        int bottomPadding = 10;
        int rightPadding = 50;

        movieHeader.setBorder(BorderFactory.createEmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding));
        ratingHeader.setBorder(BorderFactory.createEmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding));
        reviewHeader.setBorder(BorderFactory.createEmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding));

        // Add header labels to panel
        moviePanel.add(movieHeader);
        moviePanel.add(ratingHeader);
        moviePanel.add(reviewHeader);

        // Iterate through the list of movies
        for (int i = 0; i < movieController.getMovies().size(); i += 3) {
            // Create a new JLabel for each movie detail
            JLabel movieNameLabel = new JLabel(movieController.getMovies().get(i));
            JLabel rateLabel = new JLabel(movieController.getMovies().get(i + 1));
            JLabel reviewLabel = new JLabel(movieController.getMovies().get(i + 2));

            // Set padding for movie detail labels
            movieNameLabel.setBorder(BorderFactory.createEmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding));
            rateLabel.setBorder(BorderFactory.createEmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding));
            reviewLabel.setBorder(BorderFactory.createEmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding));

            // Add the JLabels to the panel
            moviePanel.add(movieNameLabel);
            moviePanel.add(rateLabel);
            moviePanel.add(reviewLabel);
        }

        // Add the panel to the frame
        add(moviePanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
