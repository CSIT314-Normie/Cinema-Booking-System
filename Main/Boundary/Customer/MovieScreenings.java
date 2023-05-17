package Main.Boundary.Customer;

import java.awt.*; 
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.SimpleDateFormat;

import javax.swing.*;
import java.util.*; 
import com.toedter.calendar.JDateChooser;

import Main.Controller.*;
import Main.Controller.Customer.*;


public class MovieScreenings extends JFrame implements ActionListener {
    private final ArrayList<String> userInfo;
    private final ArrayList<String> movieInfo;  
    private final String movieName;

    private final JLabel userRoleLabel = new JLabel();
    private final JButton homeButton = new JButton("Home"); 
    private final JButton profileButton = new JButton("Profile");
    private final JButton bookButton = new JButton("Book");
    private final JPanel panel = new JPanel(new FlowLayout());
    private final JLabel movieNameLabel; 
    private final JLabel selectedScreeningLabel = new JLabel(); 

    private final transient LoginController loginController;
    private final transient GetMovieScreeningsController getScreeningsController = new GetMovieScreeningsController();

    private ArrayList<String> allScreeningsForMovie;

    private final String[] cinemas = {"All", "Greenville Cinema", "Townsville Cinema"};
    private final JComboBox<String> cinemaComboBox = new JComboBox<>(cinemas);
    private JDateChooser dateChooser = new JDateChooser();
    private String selectedCinema = "All"; // by default
    private String dateString = "All"; // by default
    private String selectedScreeningID;

    private final JPanel movieBookingPanel = new JPanel(new BorderLayout());
    private final JPanel screeningPanel = new JPanel(new FlowLayout());

    public MovieScreenings(ArrayList<String> userInfo, ArrayList<String> movieInfo) {
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

        // GET ALL SCREENINGS FOR A MOVIE (ALL DATES)
        allScreeningsForMovie = getScreeningsController.getAllScreenings(movieName, dateString);

        panel.setPreferredSize(new Dimension(1035, 50));

        // add user role label and buttons to the panel 
        panel.add(profileButton); 
        panel.add(homeButton); 

        movieBookingPanel.setPreferredSize(new Dimension(1035, 650));
        movieNameLabel = new JLabel("Booking Movie: " + movieName); 

        // add select cinema and date labels and combo boxes to the panel
        JPanel selectionPanel = new JPanel(new FlowLayout());
        selectionPanel.setPreferredSize(new Dimension(1035, 50));

        JLabel selectCinemaLabel = new JLabel("Select Cinema: ");
        JLabel selectDateLabel = new JLabel("Select Date: ");
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setPreferredSize(new Dimension(150, 20));
        cinemaComboBox.setPreferredSize(new Dimension(150, 20));
        
        selectionPanel.add(selectCinemaLabel);
        selectionPanel.add(cinemaComboBox); 
        selectionPanel.add(selectDateLabel);
        selectionPanel.add(dateChooser);
 
        // Add a PropertyChangeListener to listen for changes to the selected date
        dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                if (e.getPropertyName().equals("date")) {
                    Date selectedDate = dateChooser.getDate();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    dateString = dateFormat.format(selectedDate);

                    System.out.println("Selected date: " + dateString);

                    // update the screening panel
                    displayMovieScreenings();
                }
            } 
        });

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

        // add book button to the booking panel
        JPanel bookButtonPanel = new JPanel(new FlowLayout());
        bookButtonPanel.setPreferredSize(new Dimension(1035, 50));
        bookButtonPanel.add(selectedScreeningLabel);
        bookButtonPanel.add(bookButton);

        // add movie info panel to the movie booking panel
        movieBookingPanel.add(movieInfoPanel, BorderLayout.WEST); 
        movieBookingPanel.add(screeningPanel, BorderLayout.EAST); 
        
        // add panels to the frame
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER); 
        add(bookButtonPanel, BorderLayout.SOUTH);
        
        // add listeners to the buttons
        homeButton.addActionListener(this); 
        profileButton.addActionListener(this);
        bookButton.addActionListener(this);

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
                new CustomerHome(userInfo);
                dispose();
                break;

            case "Logout":
                loginController.logout(userInfo.get(0));
                dispose();
                new CustomerLogin();
                System.out.println("[+] Successfully logged out");
                break;

            case "Update":
                System.out.println("[+] Move to Update page");
                dispose();
                new CustomerUpdate(userInfo);
                break;

            case "Profile":
                System.out.println("[+] Move to Profile page");
                dispose();
                new CustomerProfile(userInfo);
                break;
            
            case "Book": 
                System.out.println("[+] Move to Booking page");
                System.out.println("selected screening's date: " + dateString);
                dispose();
                new SeatingPlan(userInfo, selectedScreeningID, movieInfo, dateString);
                break;
        }
    }

    public void displayMovieScreenings(){    
        // GET ALL SCREENINGS FOR A MOVIE
        allScreeningsForMovie = getScreeningsController.getAllScreenings(movieName, dateString);

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
            String screeningID = allScreeningsForMovie.get(i);
            String date = allScreeningsForMovie.get(i + 3);
            String time = allScreeningsForMovie.get(i + 4);
            
            // for greenville cinema (cinema one)
            if (allScreeningsForMovie.get(i + 2).equals("A") || allScreeningsForMovie.get(i + 2).equals("B")) {
                sessionButton = new JButton(date  + " " + time);
                cinemaOnePanel.add(sessionButton);
            
                // add action listener to the session button
                sessionButton.addActionListener(e -> {
                    selectedScreeningID = screeningID; 
                    dateString = date;
                    selectedScreeningLabel.setText("Selected screening: " + movieName + " " + date + " " + time);
                }); 
            }   

            // for townsville cinema (cinema two)
            if (allScreeningsForMovie.get(i + 2).equals("C") || allScreeningsForMovie.get(i + 2).equals("D")) {
                sessionButton = new JButton(date + " " + time);
                cinemaTwoPanel.add(sessionButton);

                // add action listener to the session button
                sessionButton.addActionListener(e -> {
                    selectedScreeningID = screeningID;
                    dateString = date;
                    selectedScreeningLabel.setText("Selected screening: " + movieName + " " + date + " " + time);
                });
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
