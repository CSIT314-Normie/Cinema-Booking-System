package Main.Boundary.Customer;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import java.util.*;


import Main.Controller.Customer.*;


public class CustomerHome extends JFrame implements ActionListener, MouseListener {
    private final ArrayList<String> userInfo;

    private final JLabel userRoleLabel = new JLabel();
    private final JPanel panel = new JPanel(new FlowLayout());
    private final JButton logoutButton = new JButton("Logout");
    private final JButton updateButton = new JButton("Update");
    private final JButton profileButton = new JButton("Profile");
    private JScrollPane scrollPane;

    // Get movies from database
    private final JPanel movieListPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    private ArrayList<String> searchedMovieList;

    private final JTextField searchField = new JTextField(40);
    
    private final transient LoyaltyPointController loyaltyPointController = new LoyaltyPointController();
    private final transient AvailableMoviesController availableMoviesController = new AvailableMoviesController();
    private final transient SearchMovieTitleController searchMovieTitleController = new SearchMovieTitleController();
    

    public CustomerHome(ArrayList<String> userInfo) {
        super("CSIT 314 Cinema Booking System - Home");
        this.userInfo = userInfo;
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        userRoleLabel.setText("User Role: " + userInfo.get(0) + " | Email: " + userInfo.get(2));
        JLabel loyaltyPoints = new JLabel(" | Loyalty Points: " + loyaltyPointController.getLoyaltyPoint(userInfo.get(2)));
        JPanel searchPanel = new JPanel();
        JButton searchButton = new JButton("Search");
        JButton viewTicketHistoryButton = new JButton("Ticketing History");

        panel.setPreferredSize(new Dimension(1035, 50));
        searchPanel.setPreferredSize(new Dimension(1035, 50));


        panel.add(userRoleLabel);    
        panel.add(loyaltyPoints);
        panel.add(updateButton);
        panel.add(profileButton);
        panel.add(logoutButton);
        panel.add(viewTicketHistoryButton);    
    
        searchedMovieList = availableMoviesController.getAvailableMovies();
    
        searchField.setToolTipText("Search for movies");
        

        searchPanel.add(searchField, BorderLayout.WEST);
        searchPanel.add(searchButton, BorderLayout.EAST);

        displaySearchedMovies();

        searchButton.addActionListener(e -> {
            searchedMovieList = searchMovieTitleController.searchMovieTitle(searchField.getText());
            displaySearchedMovies();
        });

        scrollPane = new JScrollPane(movieListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(650, 650));

        add(searchPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        add(panel, BorderLayout.NORTH);
        pack();

        logoutButton.addActionListener(this);
        updateButton.addActionListener(this);
        profileButton.addActionListener(this);
        viewTicketHistoryButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Logout":
                CustomerLogoutController logoutController = new CustomerLogoutController();
                logoutController.logout();
                
                dispose();
                new CustomerLogin();
                break;

            case "Update":
                dispose();
                new CustomerUpdate(userInfo);
                break;

            case "Profile":
                dispose();
                new CustomerProfile(userInfo);
                break;
            
            case "Ticketing History":
                dispose();
                new TicketingHistory(userInfo);
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // get the clicked movie panel
        JPanel clickedMoviePanel = (JPanel) e.getSource();
        JLabel movieTitle = (JLabel) clickedMoviePanel.getComponent(0); 

        ArrayList<String> movieInfo = new ArrayList<>();
        int index = searchedMovieList.indexOf(movieTitle.getText());

        for (int i = index; i < index + 6 ; i++) {
            movieInfo.add(searchedMovieList.get(i));
        }

        // open Book.java with the movie title
        dispose();
        new MovieScreenings(userInfo, movieInfo);
    }


    /*
     * Display searched movies - CUSTOMER ONLY
     * All movies are displayed by default. Panel is cleared before displaying
     * searched movies
     */

    public void displaySearchedMovies() {
        movieListPanel.removeAll();

        // Add movies to the content panel, and display
        for (int i = 0; i < searchedMovieList.size(); i += 7) {
            JPanel moviePanel = new JPanel();
            moviePanel.setLayout(new BoxLayout(moviePanel, BoxLayout.Y_AXIS));
            moviePanel.setPreferredSize(new Dimension(200, 700));

            JLabel movieTitle = new JLabel(searchedMovieList.get(i));

            ImageIcon image = new ImageIcon((new File("./Main/Boundary/assets/" + searchedMovieList.get(i + 1))).getAbsolutePath());
            Image scaledImage = image.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
            image = new ImageIcon(scaledImage);

            JLabel movieImage = new JLabel(image);
            JLabel movieRate = new JLabel("Stars: " + searchedMovieList.get(i + 2));
            JLabel movieReview = new JLabel("# Review: " + searchedMovieList.get(i + 3));

            movieTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            movieImage.setAlignmentX(Component.CENTER_ALIGNMENT);
            movieRate.setAlignmentX(Component.CENTER_ALIGNMENT);
            movieReview.setAlignmentX(Component.CENTER_ALIGNMENT);

            // set font size for each JLabel to be 13
            movieTitle.setFont(new Font("Arial", Font.BOLD, 30));
            movieRate.setFont(new Font("Arial", Font.BOLD, 20));
            movieReview.setFont(new Font("Arial", Font.BOLD, 20));

            moviePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            moviePanel.add(movieTitle);
            moviePanel.add(movieImage);
            moviePanel.add(movieRate);
            moviePanel.add(movieReview);
            moviePanel.addMouseListener(this);

            movieListPanel.add(moviePanel);
        }

        movieListPanel.repaint();
        movieListPanel.revalidate();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
 