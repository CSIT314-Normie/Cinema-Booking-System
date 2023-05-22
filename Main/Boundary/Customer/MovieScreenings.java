package Main.Boundary.Customer;

import java.awt.*; 
import java.awt.event.*; 
import java.io.File;
import java.text.SimpleDateFormat;
import javax.swing.*;

import java.util.*; 
import com.toedter.calendar.JDateChooser;

import Main.Controller.Customer.*;

public class MovieScreenings extends JFrame implements ActionListener {
    private final ArrayList<String> userInfo;
    private final ArrayList<String> movieInfo;  
    private final String movieName;

    private final JLabel userRoleLabel = new JLabel();
    private final JButton homeButton = new JButton("Home"); 
    private final JButton profileButton = new JButton("Profile");
    private final JButton searchButton = new JButton("Search");
    private final JButton bookButton = new JButton("Book");

    private final JPanel panel = new JPanel(new FlowLayout()); 
    private final JLabel selectedScreeningLabel = new JLabel(); 

    // controllers 
    private final transient GetMovieScreeningsController getScreeningsController = new GetMovieScreeningsController();
    private final transient GetCinemasController getCinemasController = new GetCinemasController();
    private final transient GetMovieReviewsController getReviewsController = new GetMovieReviewsController();

    // data
    private ArrayList<String> allScreeningsForMovie;
    private ArrayList<String> allReviewsForMovie;
    private ArrayList<String> allCinemas; 
    private final JComboBox<String> cinemaComboBox;
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

        userRoleLabel.setText("User Role: " + userInfo.get(0) + " | Email: " + userInfo.get(2));

        // GET ALL REVIEWS FOR A MOVIE
        allReviewsForMovie = getReviewsController.getMovieReviews(movieName);

        // GET ALL SCREENINGS FOR A MOVIE 
        allScreeningsForMovie = getScreeningsController.getAllScreenings(movieName);

        // GET ALL CINEMAS 
        this.allCinemas = getCinemasController.getCinemas();
        this.allCinemas.add("All"); // to allow user to select all cinemas

        panel.setPreferredSize(new Dimension(1035, 50));

        // add user role label and buttons to the panel 
        panel.add(profileButton); 
        panel.add(homeButton); 

        movieBookingPanel.setPreferredSize(new Dimension(1035, 1000));
        JLabel movieNameLabel = new JLabel("Booking Movie: " + movieName); 

        // add select cinema and date labels and combo boxes to the panel
        JPanel selectionPanel = new JPanel(new FlowLayout());
        selectionPanel.setPreferredSize(new Dimension(1035, 50));

        // for user to select cinema 
        cinemaComboBox = new JComboBox<>(allCinemas.toArray(new String[0]));
        cinemaComboBox.setSelectedItem("All"); // by default

        JLabel selectCinemaLabel = new JLabel("Select Cinema: ");
        JLabel selectDateLabel = new JLabel("Select Date: ");
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setPreferredSize(new Dimension(150, 20));
        cinemaComboBox.setPreferredSize(new Dimension(150, 20));
        
        selectionPanel.add(selectCinemaLabel);
        selectionPanel.add(cinemaComboBox); 
        selectionPanel.add(selectDateLabel);
        selectionPanel.add(dateChooser);
        selectionPanel.add(searchButton);

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

        // JLabel movieNameLabel = new JLabel(movieInfo.get(0)); 
        JLabel movieImageLabel = new JLabel(movieImage);
        JTextArea movieDescriptionText = new JTextArea(movieInfo.get(4), 10, 20);

        movieNameLabel.setFont(new Font("Serif", Font.BOLD, 16)); 
        movieNameLabel.setPreferredSize(new Dimension(337, 30));
        movieDescriptionText.setPreferredSize(new Dimension(337, 150));
        movieDescriptionText.setLineWrap(true);
        movieDescriptionText.setWrapStyleWord(true);
        movieDescriptionText.setOpaque(false);
        movieDescriptionText.setEditable(false);

        // add movie name, image and description to the movie info panel
        movieInfoPanel.add(movieNameLabel);
        movieInfoPanel.add(movieImageLabel);
        movieInfoPanel.add(movieDescriptionText); 

        // reviews panel
        JPanel reviewsPanel = new JPanel(new FlowLayout());
        reviewsPanel.setPreferredSize(new Dimension(700, 200));
        reviewsPanel.setBorder(BorderFactory.createTitledBorder("Movie Reviews"));

        // add all reviews for the movie to the reviews panel
        for (int i = 0; i < allReviewsForMovie.size(); i+=3) { 
            JPanel panel = new JPanel(new FlowLayout());
            panel.setPreferredSize(new Dimension(900, 30)); 
            panel.setBorder(BorderFactory.createLoweredBevelBorder());

            JLabel reviewLabel = new JLabel(allReviewsForMovie.get(i + 1));
            JLabel ratingLabel = new JLabel("Rated: " + allReviewsForMovie.get(i + 2) + "/5");
            reviewLabel.setPreferredSize(new Dimension(700, 20));
            ratingLabel.setPreferredSize(new Dimension(100, 20));
            reviewLabel.setFont(new Font("Serif", Font.LAYOUT_LEFT_TO_RIGHT, 12));
            ratingLabel.setFont(new Font("Serif", Font.LAYOUT_LEFT_TO_RIGHT, 12));

            panel.add(reviewLabel);
            panel.add(ratingLabel);

            reviewsPanel.add(panel);
        }

        // add movie info panel to the movie booking panel
        movieBookingPanel.add(movieInfoPanel, BorderLayout.WEST); 
        movieBookingPanel.add(screeningPanel, BorderLayout.EAST); 
        movieBookingPanel.add(reviewsPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(movieBookingPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(800, 650));
        
        // panel to display movie screenings - by default shows ALL screenings for ALL cinemas & ALL dates
        displayMovieScreenings(); 

        // add book button to the booking panel
        JPanel bookButtonPanel = new JPanel(new FlowLayout());
        bookButtonPanel.setPreferredSize(new Dimension(1035, 50));
        bookButtonPanel.add(selectedScreeningLabel);
        bookButtonPanel.add(bookButton);

        
        // add panels to the frame
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER); 
        add(bookButtonPanel, BorderLayout.SOUTH);
        
        // add listeners to the buttons
        homeButton.addActionListener(this); 
        profileButton.addActionListener(this);
        bookButton.addActionListener(this);
        searchButton.addActionListener(this);

        // Add a PropertyChangeListener to listen for changes to the selected date
        dateChooser.addPropertyChangeListener(e -> {
            if (e.getPropertyName().equals("date")) {
                Date selectedDate = dateChooser.getDate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                dateString = dateFormat.format(selectedDate);

                System.out.println("Selected date: " + dateString); 
            }
        });

        // add listener to the combo box
        cinemaComboBox.addActionListener(e -> {
            selectedCinema = (String) cinemaComboBox.getSelectedItem();
            System.out.println("Selected cinema: " + selectedCinema); // for debugging 
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home":
                dispose();
                new CustomerHome(userInfo);
                break;  

            case "Profile": 
                dispose();
                new CustomerProfile(userInfo);
                break;
            
            case "Search": 
                // search based on selected cinema 
                SearchMovieScreeningCinemaController searchMovieScreeningCinemaController = new SearchMovieScreeningCinemaController();
                allScreeningsForMovie = searchMovieScreeningCinemaController.searchMovieScreeningCinema(movieName, selectedCinema, dateString);
                System.out.println("[cinema] allScreeningsForMovie: " + allScreeningsForMovie);
                displayMovieScreenings(); 

                // search based on selected date
                SearchMovieScreeningDateController searchMovieScreeningDateController = new SearchMovieScreeningDateController();
                allScreeningsForMovie = searchMovieScreeningDateController.searchMovieScreeningDate(movieName, dateString, selectedCinema);
                System.out.println("[date] allScreeningsForMovie: " + allScreeningsForMovie);
                displayMovieScreenings(); 
                
                break;
            
            case "Book":  
                if (selectedScreeningID == null) {
                    JOptionPane.showMessageDialog(null, "Please select a screening", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    dispose();
                    new SeatingPlan(userInfo, selectedScreeningID, movieInfo, dateString);
                }
                
                break;
        }
    }

    
    /**
     * Display all the movie screenings for the selected movie
     */
    public void displayMovieScreenings(){     
        screeningPanel.removeAll(); 

        // display movie screening information
        JPanel movieScreeningPanel = new JPanel(new BorderLayout());
        movieScreeningPanel.setPreferredSize(new Dimension(600, 450)); 

        JPanel cinemaOnePanel = new JPanel(new FlowLayout());
        JPanel cinemaTwoPanel = new JPanel(new FlowLayout());

        cinemaOnePanel.setPreferredSize(new Dimension(400, 220));
        cinemaOnePanel.setBorder(BorderFactory.createTitledBorder(allCinemas.get(0)));

        cinemaTwoPanel.setPreferredSize(new Dimension(400, 220));
        cinemaTwoPanel.setBorder(BorderFactory.createTitledBorder(allCinemas.get(1))); 

        for (int i = 0; i < allScreeningsForMovie.size(); i += 7) { 
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

        if (selectedCinema.equals(allCinemas.get(0))) {
            cinemaOnePanel.setVisible(true);
            cinemaTwoPanel.setVisible(false);
        } else if (selectedCinema.equals(allCinemas.get(1))) {
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
