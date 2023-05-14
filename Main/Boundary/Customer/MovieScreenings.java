package Main.Boundary.Customer;

import java.awt.*; 
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import java.util.*;
import java.util.List;

import Main.Controller.*;
import Main.Boundary.*; 
import Main.Controller.Customer.*;


public class MovieScreenings extends JFrame implements ActionListener {
    private final ArrayList<String> userInfo;
    private final List<String> movieInfo; // NOT IN USED (remove?)
    private final String movieName;

    private final JLabel userRoleLabel = new JLabel();
    private final JButton homeButton = new JButton("Home"); 
    private final JButton profileButton = new JButton("Profile");
    private final JPanel panel = new JPanel(new FlowLayout());
    private final JLabel movieNameLabel; // NOT IN USED (remove?)

    private final transient LoginController loginController;
    private final transient GetMovieScreeningsController getScreeningsController = new GetMovieScreeningsController();

    private ArrayList<String> allScreeningsForMovie;

    private final String[] cinemas = {"All", "Greenville Cinema", "Townsville Cinema"};
    private final JComboBox<String> cinemaComboBox = new JComboBox<>(cinemas);
    private String selectedCinema = "All"; // by default

    private final JPanel movieBookingPanel = new JPanel(new BorderLayout());
    private final JPanel screeningPanel = new JPanel(new FlowLayout());

    public MovieScreenings(ArrayList<String> userInfo, List<String> movieInfo) {
        super("CSIT 314 Cinema Booking System - Book movie: " + movieInfo.get(0));
        this.userInfo = userInfo;
        this.movieInfo = movieInfo;
        this.movieName = movieInfo.get(0);
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        loginController = new LoginController(userInfo.get(0), userInfo.get(1), userInfo.get(2));
        userRoleLabel.setText("User Role: " + userInfo.get(0) + " | Email: " + userInfo.get(2));

        // GET ALL SCREENINGS FOR A MOVIE
        allScreeningsForMovie = getScreeningsController.getAllScreenings(movieName);

        panel.setPreferredSize(new Dimension(1035, 50));

        // add user role label and buttons to the panel
        // panel.add(userRoleLabel); 
        panel.add(profileButton); 
        panel.add(homeButton); 

        movieBookingPanel.setPreferredSize(new Dimension(1035, 650));
        movieNameLabel = new JLabel("Booking Movie: " + movieName); 

        // add select cinema label and combo box to the panel
        JPanel selectionPanel = new JPanel(new FlowLayout());
        selectionPanel.setPreferredSize(new Dimension(1035, 50));

        JLabel selectCinemaLabel = new JLabel("Select Cinema: ");
        selectionPanel.add(selectCinemaLabel);
        selectionPanel.add(cinemaComboBox); 

        // add selection panel to the movie booking panel
        movieBookingPanel.add(selectionPanel, BorderLayout.NORTH);

        // panel to display movie image and description
        JPanel movieInfoPanel = new JPanel(new FlowLayout());
        movieInfoPanel.setPreferredSize(new Dimension(350, 600));
        movieInfoPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 0)); 

        // add movie image to the movie info panel
        ImageIcon movieImage = new ImageIcon(new File("./Main/Boundary/assets/" + movieInfo.get(1)).getAbsolutePath());
        Image scaledImage = movieImage.getImage().getScaledInstance(337,450, Image.SCALE_SMOOTH);
        movieImage = new ImageIcon(scaledImage);

        JLabel movieNameLabel = new JLabel(movieInfo.get(0)); 
        JLabel movieImageLabel = new JLabel(movieImage);
        JTextArea movieDescriptionText = new JTextArea(movieInfo.get(4), 10, 20);

        movieNameLabel.setFont(new Font("Serif", Font.BOLD, 16)); 
        movieNameLabel.setPreferredSize(new Dimension(337, 30));
        movieDescriptionText.setPreferredSize(new Dimension(337, 150));
        movieDescriptionText.setLineWrap(true);
        movieDescriptionText.setWrapStyleWord(true);
        movieDescriptionText.setOpaque(false);
        movieDescriptionText.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(movieBookingPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(800, 650));
        
        // add movie name, image and description to the movie info panel
        movieInfoPanel.add(movieNameLabel);
        movieInfoPanel.add(movieImageLabel);
        movieInfoPanel.add(movieDescriptionText); 

        displayMovieScreenings();

        // add movie info panel to the movie booking panel
        movieBookingPanel.add(movieInfoPanel, BorderLayout.WEST); 
        movieBookingPanel.add(screeningPanel, BorderLayout.EAST);
        
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER); 
        
        homeButton.addActionListener(this); 
        profileButton.addActionListener(this);

        // add listener to the combo box
        cinemaComboBox.addActionListener(e -> {
            selectedCinema = (String) cinemaComboBox.getSelectedItem();
            System.out.println("Selected cinema: " + selectedCinema); // for debugging

            displayMovieScreenings();
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home":
                new Home(userInfo);
                dispose();
                break;

            case "Logout":
                loginController.logout(userInfo.get(0));
                dispose();
                new Login();
                System.out.println("[+] Successfully logged out");
                break;

            case "Update":
                System.out.println("[+] Move to Update page");
                dispose();
                new Update(userInfo);
                break;

            case "Profile":
                System.out.println("[+] Move to Profile page");
                dispose();
                new Profile(userInfo);
                break;
        }
    }

    public void displayMovieScreenings(){    
        // GET ALL SCREENINGS FOR A MOVIE
        allScreeningsForMovie = getScreeningsController.getAllScreenings(movieName);

        screeningPanel.removeAll(); 

        // display movie screening information
        JPanel movieScreeningPanel = new JPanel(new BorderLayout());
        movieScreeningPanel.setPreferredSize(new Dimension(600, 450)); 

        JPanel cinemaOnePanel = new JPanel(new FlowLayout());
        JPanel cinemaTwoPanel = new JPanel(new FlowLayout());

        cinemaOnePanel.setPreferredSize(new Dimension(400, 220));
        cinemaOnePanel.setBorder(BorderFactory.createTitledBorder("Greenville Cinema"));

        cinemaTwoPanel.setPreferredSize(new Dimension(400, 220));
        cinemaTwoPanel.setBorder(BorderFactory.createTitledBorder("Townsville Cinema")); 

        for (int i = 0; i < allScreeningsForMovie.size(); i += 9) { 
            JButton sessionButton;
            // for greenville cinema (cinema one)
            if (allScreeningsForMovie.get(i + 2).equals("A") || allScreeningsForMovie.get(i + 2).equals("B")) {
                sessionButton = new JButton(allScreeningsForMovie.get(i + 3));
                cinemaOnePanel.add(sessionButton);
            }

            // for townsville cinema (cinema two)
            if (allScreeningsForMovie.get(i + 2).equals("C") || allScreeningsForMovie.get(i + 2).equals("D")) {
                sessionButton = new JButton(allScreeningsForMovie.get(i + 3));
                cinemaTwoPanel.add(sessionButton);
            }
        }

        if (selectedCinema.equals("Greenville Cinema")) {
            cinemaOnePanel.setVisible(true);
            cinemaTwoPanel.setVisible(false);
        } else if (selectedCinema.equals("Townsville Cinema")) {
            cinemaOnePanel.setVisible(false);
            cinemaTwoPanel.setVisible(true);
        } else {
            cinemaOnePanel.setVisible(true);
            cinemaTwoPanel.setVisible(true);
        }
        
        movieScreeningPanel.add(cinemaOnePanel, BorderLayout.NORTH);
        movieScreeningPanel.add(cinemaTwoPanel, BorderLayout.CENTER);
        
        screeningPanel.add(movieScreeningPanel);

        screeningPanel.repaint();
        screeningPanel.revalidate();
    }
}
