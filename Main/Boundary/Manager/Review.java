package Main.Boundary.Manager;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.*;

import Main.Boundary.Customer.CustomerHome;
import Main.Controller.Admin.MovieReviewController;

public class Review extends JFrame implements ActionListener {
    // Create header labels
    private final JLabel movieHeader = new JLabel("Movie");
    private final JLabel ratingHeader = new JLabel("Rating");
    private final JLabel reviewHeader = new JLabel("Review");
    private final JLabel updateHeader = new JLabel("Update");
    private final JButton homeButton = new JButton("Home");

    // Create a panel to hold the movie details
    private final JPanel moviePanel = new JPanel(new GridLayout(0, 5));

    // Create Movie Controller
    private final transient MovieReviewController movieReviewController;

    private final ArrayList<JLabel> movieNameLabels = new ArrayList<>();
    private final ArrayList<JTextField> rateFields = new ArrayList<>();
    private final ArrayList<JTextField> reviewFields = new ArrayList<>();
    private final ArrayList<JButton> updateButtons = new ArrayList<>();

    private final ArrayList<String> userInfo;

    public Review(ArrayList<String> userInfo) {
        super("Movie Review");
        this.userInfo = userInfo;
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        // Set up Movie controller
        movieReviewController = new MovieReviewController();
    
        // Set padding for header labels
        movieHeader.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 50));
        ratingHeader.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 50));
        reviewHeader.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 50));
        updateHeader.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 50));
        
        // Add header labels to panel
        moviePanel.add(movieHeader);
        moviePanel.add(ratingHeader);
        moviePanel.add(reviewHeader);
        moviePanel.add(updateHeader);
        moviePanel.add(homeButton);
        
        // Iterate through the list of movies
        for (int i = 0; i < movieReviewController.getUserWatchedMovies().size(); i += 3) {
            // Create a new JLabel and JTextField for each movie detail
            JLabel movieNameLabel = new JLabel(movieReviewController.getUserWatchedMovies().get(i));
            JTextField rateField = new JTextField(movieReviewController.getUserWatchedMovies().get(i + 1));
            JTextField reviewField = new JTextField(movieReviewController.getUserWatchedMovies().get(i + 2));
            JButton updateButton = new JButton("Update");

            // Set default text for rating and review fields
            rateField.setText(movieReviewController.getUserWatchedMovies().get(i + 1));
            reviewField.setText(movieReviewController.getUserWatchedMovies().get(i + 2));
            
            // Set padding for movie detail labels
            movieNameLabel.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 50));
            rateField.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 50));
            reviewField.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 50));
            updateButton.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 50));

            // Add the JLabels and JTextFields to the panel
            moviePanel.add(movieNameLabel);
            moviePanel.add(rateField);
            moviePanel.add(reviewField);
            moviePanel.add(updateButton);

            // Add the movie name label to the list of movie name labels
            movieNameLabels.add(movieNameLabel);

            // Add the text fields to the list of text fields
            rateFields.add(rateField);
            reviewFields.add(reviewField);

            // Add the update button to the list of update buttons
            updateButtons.add(updateButton);
        }

        // Add the panel to the frame
        add(moviePanel);
    

        // Add action listeners to the update buttons
        updateButtons.forEach(button -> button.addActionListener(this));
        homeButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == homeButton) {
            dispose();
            new CustomerHome(userInfo);
        } else {
            int index = updateButtons.indexOf(e.getSource());
            String movieName = movieNameLabels.get(index).getText();
            String rating = rateFields.get(index).getText();
            String review = reviewFields.get(index).getText();
    
            if (rating.isEmpty() || review.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields");
                return;
            }
    
            if (Integer.parseInt(rating) < 1 || Integer.parseInt(rating) > 5) {
                JOptionPane.showMessageDialog(null, "Rating must be between 1 and 5");
                return;
            }
    
            
            if (movieReviewController.updateMovieRateReview(movieName, rating, review)) {
                JOptionPane.showMessageDialog(null, "Movie details updated successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Movie details update failed");
            }
        }
    }
}