package Main.Boundary.Customer;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;


import Main.Controller.*;
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

    // search movies text field (CUSTOMER ONLY)
    private final JTextField searchField = new JTextField(40);

    
    private final transient CustomerLoginController loginController;
    private final transient loyaltyPointController loyaltyPointController = new loyaltyPointController();
    private final transient AvailableMoviesController availableMoviesController = new AvailableMoviesController();
    

    public CustomerHome(ArrayList<String> userInfo) {
        super("CSIT 314 Cinema Booking System - Home");
        this.userInfo = userInfo;
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true); // Show the frame

        // Login "SESSION" for user to allow user to logout, can be further implemented
        loginController = new CustomerLoginController(userInfo.get(0), userInfo.get(1), userInfo.get(2));
        userRoleLabel.setText("User Role: " + userInfo.get(0) + " | Email: " + userInfo.get(2));
        JLabel loyaltyPoints = new JLabel(" | Loyalty Points: " + loyaltyPointController.getLoyaltyPoint(userInfo.get(2)));
        
        panel.setPreferredSize(new Dimension(1035, 50));
        panel.add(userRoleLabel);    
        panel.add(loyaltyPoints);
        panel.add(updateButton);
        panel.add(profileButton);
        panel.add(logoutButton);

    
        switch (userInfo.get(0)) {
            case "Customer":
                // Customer Home page
                searchedMovieList = availableMoviesController.getAvailableMovies();

                System.out.println("[+] Customer - Home Page");

                JButton viewTicketHistoryButton = new JButton("Ticketing History");
                
                panel.add(viewTicketHistoryButton);
                viewTicketHistoryButton.addActionListener(this);

                // Search panel for customer to search for movies
                JPanel searchPanel = new JPanel();
                searchPanel.setPreferredSize(new Dimension(1035, 50));

                searchField.setToolTipText("Search for movies");
                JButton searchButton = new JButton("Search");

                searchPanel.add(searchField, BorderLayout.WEST);
                searchPanel.add(searchButton, BorderLayout.EAST);

                // Add movie list to the content panel
                displaySearchedMovies();

                searchButton.addActionListener(e -> {
                    System.out.println("[+] Customer - Search for movies");
                    searchedMovies(searchField.getText().trim());
                    displaySearchedMovies();
                });

                scrollPane = new JScrollPane(movieListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrollPane.setPreferredSize(new Dimension(650, 650));

                // add search panel to the frame
                add(searchPanel, BorderLayout.CENTER);

                // add scroll pane to the frame
                add(scrollPane, BorderLayout.SOUTH);
                break;
        }

        // add panel to the frame
        add(panel, BorderLayout.NORTH);
        pack();

        // add action listener to the buttons
        logoutButton.addActionListener(this);
        updateButton.addActionListener(this);
        profileButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Logout":
                loginController.logout(userInfo.get(0));
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
        System.out.println("[+] Customer Movie panel clicked: " + movieTitle.getText());

        ArrayList<String> movieInfo = new ArrayList<>();
        int index = searchedMovieList.indexOf(movieTitle.getText());

        for (int i = index; i < index + 6 ; i++) {
            movieInfo.add(searchedMovieList.get(i));
        }

        System.out.println(movieInfo);

        // open Book.java with the movie title
        dispose();
        new MovieScreenings(userInfo, movieInfo);
    }

    

    /**
     * Search for movies - CUSTOMER ONLY
     * 
     * @param searchQuery
     */
    public void searchedMovies(String searchQuery) {
        searchedMovieList = availableMoviesController.getAvailableMovies();

        if (searchQuery.equals("") || searchQuery.equals("Search for movies") || searchQuery.equals(" ")) {
            System.out.println("[+] Search query is empty");
        } else {
            if (searchedMovieList.contains(searchQuery)) {
                System.out.println("[+] Movie found");

                // replace movies in searchMovieList with searched movies results
                for (int i = 0; i < searchedMovieList.size(); i += 7) {
                    if (!(searchedMovieList.get(i).toLowerCase().contains(searchQuery.toLowerCase()))) {
                        searchedMovieList.subList(i, i + 7).clear();
                    }
                }

            } else {
                System.out.println("[+] Movie not found");
            }
        }
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

class ImageRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        if (value instanceof ImageIcon) {
            ImageIcon imageIcon = (ImageIcon) value;
            JLabel label = new JLabel(imageIcon);
            label.setOpaque(true);
            label.setBackground(Color.WHITE);
            return label;
        } else {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}