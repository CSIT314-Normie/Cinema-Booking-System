package Main.Boundary.Customer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Main.Controller.Customer.AddMovieRatingAndReviewController;

import java.util.*;
 

/**
 * AddMovieRatingAndReview.java
 * This class is the UI for the customer to add a rating and review for a movie that they have watched.
 * - get movies that the customer has watched (ticketing history)
 * - allow customer to select a movie to rate and review
 */
public class AddMovieRatingAndReview extends JFrame implements ActionListener{
    private ArrayList<String> userInfo;
    private String movieName;

    private final JButton homeButton = new JButton("Home");
    private final JButton backButton = new JButton("Back");
    private final JButton submitButton = new JButton("Submit");

    private final String[] labelList = {"Movie Title: ", "Movie Review: ", "Movie Rating: "};
    private JTextArea movieReview = new JTextArea(5, 40);
    private String rating = "";
    private ArrayList<String> reviewInfo = new ArrayList<>();

    public AddMovieRatingAndReview(ArrayList<String> userInfo, String movieName) { 
        super("Add Movie Rating and Review - " + movieName);
        this.userInfo = userInfo;
        this.movieName = movieName;
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null); 

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setPreferredSize(new Dimension(1035, 50));
        topPanel.add(homeButton);
        topPanel.add(backButton);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS)); 

        ButtonGroup ratingGroup = new ButtonGroup();
        JRadioButton oneStar = new JRadioButton("1");
        JRadioButton twoStar = new JRadioButton("2");
        JRadioButton threeStar = new JRadioButton("3");
        JRadioButton fourStar = new JRadioButton("4");
        JRadioButton fiveStar = new JRadioButton("5");

        ratingGroup.add(oneStar);
        ratingGroup.add(twoStar);
        ratingGroup.add(threeStar);
        ratingGroup.add(fourStar);
        ratingGroup.add(fiveStar);

        
        for (int i = 0; i < labelList.length; i++) {
            JPanel rowPanel = new JPanel(new FlowLayout());
            rowPanel.setPreferredSize(new Dimension(700, 40));

            JLabel label = new JLabel(labelList[i]);
            rowPanel.add(label);
            
            if (i == 0) {
                JLabel movieTitle = new JLabel(movieName);
                rowPanel.add(movieTitle);
            } else if (i == 1) {
                movieReview.setLineWrap(true);
                movieReview.setWrapStyleWord(true);
                rowPanel.add(movieReview);
            } else { 
                oneStar.setActionCommand("1");
                twoStar.setActionCommand("2");
                threeStar.setActionCommand("3");
                fourStar.setActionCommand("4");
                fiveStar.setActionCommand("5");

                rowPanel.add(oneStar);
                rowPanel.add(twoStar);
                rowPanel.add(threeStar);
                rowPanel.add(fourStar);
                rowPanel.add(fiveStar);
            }

            centerPanel.add(rowPanel);
        } 

        // submit button panel
        JPanel submitPanel = new JPanel(new FlowLayout());
        submitPanel.setPreferredSize(new Dimension(1035, 50));
        submitPanel.add(submitButton);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(submitPanel, BorderLayout.SOUTH);

        // add listener for buttons
        homeButton.addActionListener(this);
        submitButton.addActionListener(this);
        backButton.addActionListener(this);

        // add listener for ButtonGroup radio buttons
        oneStar.addActionListener(e -> rating = e.getActionCommand());

        twoStar.addActionListener(e -> rating = e.getActionCommand());

        threeStar.addActionListener(e -> rating = e.getActionCommand());

        fourStar.addActionListener(e -> rating = e.getActionCommand());

        fiveStar.addActionListener(e -> rating = e.getActionCommand());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home":
                dispose();
                new CustomerHome(userInfo);
                break;
            
            case "Back":
                dispose();
                new TicketingHistory(userInfo);
                break;

            case "Submit":
                reviewInfo.clear();
                reviewInfo.add(userInfo.get(2)); // userEmail
                reviewInfo.add(movieName);
                reviewInfo.add(movieReview.getText().toString());
                reviewInfo.add(rating);

                System.out.println(reviewInfo);

                // check if any fields are empty
                if (reviewInfo.contains("")) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                } else {
                    // add review and rating to database
                    AddMovieRatingAndReviewController reviewController = new AddMovieRatingAndReviewController();
                    if (reviewController.addReviewRating(reviewInfo)) {
                        JOptionPane.showMessageDialog(null, "Movie rating and review submitted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        
                        dispose();
                        new CustomerHome(userInfo); 
                    } else {
                        JOptionPane.showMessageDialog(null, "Add movie rating and review failed.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } 
                
                break;
        }
    } 
}
