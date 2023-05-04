package Main.Boundary;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.*;

import Main.Controller.LoginContoller;
import Main.Controller.MovieController;

public class Home extends JFrame implements ActionListener, MouseListener {
    private final JLabel userRoleLabel = new JLabel();
    private final JPanel panel = new JPanel(new FlowLayout());
    private final JButton logoutButton = new JButton("Logout");
    private final JButton updateButton = new JButton("Update");
    private final JButton profileButton = new JButton("Profile");
    private JScrollPane scrollPane;

    private ArrayList<String> userInfo;
    private transient LoginContoller loginController;
    private transient MovieController movieController = new MovieController();

    // Get movies from database
    private  JPanel movieListPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));  
    private ArrayList<String> searchedMovieList = movieController.getMovies(); // default to all movies   

    // search movies text field (CUSTOMER ONLY)
    private JTextField searchField = new JTextField(40);

    // Get all accounts from database (USER ADMIN ONLY)
    private ArrayList<String[]> allAccounts;

    public Home(ArrayList<String> userInfo) {
        super("Welcome to CSIT 314 Cinema Booking System - Home");
        this.userInfo = userInfo;
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null); 
        setVisible(true); // Show the frame

        // Login "SESSION" for user to allow user to logout, can be further implemented
        loginController = new LoginContoller(userInfo.get(0), userInfo.get(1), userInfo.get(2));
        userRoleLabel.setText("User Role: " + userInfo.get(0) + " | Email: " + userInfo.get(2));
        panel.setPreferredSize(new Dimension(1035, 50));

        // add user role lable and buttons to the panel
        panel.add(userRoleLabel);
        panel.add(updateButton);
        panel.add(profileButton);
        panel.add(logoutButton);

        if (this.userInfo.get(0).equals("Admin")) { 
            // Get all accounts from database
            this.allAccounts = loginController.getAllUserAccounts();
            JLabel allAccountsLabel = new JLabel("All User Accounts");

            // Add all accounts to the content panel
            String columns[] = {"First Name", "Last Name", "Email", "Date of Birth", "Role"};
            
            DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
            
            for (String[] row : allAccounts) {
                tableModel.addRow(row);
            }

            JTable allUserTable = new JTable(tableModel);

            // Add the JTable to a JScrollPane
            scrollPane = new JScrollPane(allUserTable);

            // add label & scrollpane to the frame
            add(allAccountsLabel, BorderLayout.CENTER);
            add(scrollPane, BorderLayout.SOUTH);

        } else if (this.userInfo.get(0).equals("Customer")) { 
            // view ticket history
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

            searchButton.addActionListener( e -> {
                System.out.println("[+] Customer - Search for movies"); 
                searchedMovies(searchField.getText().trim()); 
                displaySearchedMovies();
            });

            scrollPane = new JScrollPane(movieListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setPreferredSize(new Dimension(650, 650));

            // add search panel to the frame
            add(searchPanel, BorderLayout.CENTER);

            // add scrollpane to the frame
            add(scrollPane, BorderLayout.SOUTH);

        } else if (this.userInfo.get(0).equals("Manager")) { 

        } else if (this.userInfo.get(0).equals("Owner")) { 

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
            
            case "Ticketing History":
                System.out.println("[+] Customer - Move to Ticketing History page");
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
        System.out.println("Movie panel clicked: " + movieTitle.getText());
        
        // open Book.java with the movie title
        dispose();
        new Book(userInfo, movieTitle.getText());
        
    }

    /*
     * Search for movies - CUSTOMER ONLY
     * @param searchQuery
     */

    public void searchedMovies(String searchQuery) {
        searchedMovieList = movieController.getMovies();

        if (searchQuery.equals("") || searchQuery.equals("Search for movies") || searchQuery.equals(" ")) {
            System.out.println("[+] Search query is empty");
        } else { 
            if (searchedMovieList.contains(searchQuery)) {
                System.out.println("[+] Movie found"); 

                // replace movies in searchMovieList with searched movies results
                for (int i = 0; i < searchedMovieList.size(); i += 4) {
                    if (!(searchedMovieList.get(i).toLowerCase().contains(searchQuery.toLowerCase()))) {
                        searchedMovieList.subList(i, i + 4).clear(); 
                    }
                }

            } else {
                System.out.println("[+] Movie not found"); 
            }
        }
    }

    /*
     * Display searched movies - CUSTOMER ONLY
     */

    public void displaySearchedMovies() { 
        movieListPanel.removeAll(); 

         // Add movies to the content panel, and display
        for (int i = 0; i < searchedMovieList.size(); i += 4) {
            JPanel moviePanel = new JPanel();
            moviePanel.setLayout(new BoxLayout(moviePanel, BoxLayout.Y_AXIS));
            moviePanel.setPreferredSize(new Dimension(200, 700));

            JLabel movieTitle = new JLabel(searchedMovieList.get(i)); 

            ImageIcon image = new ImageIcon((new File("./Main/Boundary/assets/" + searchedMovieList.get(i + 1))).getAbsolutePath());
            Image scaledImage = image.getImage().getScaledInstance(100,200, Image.SCALE_SMOOTH);
            image = new ImageIcon(scaledImage);

            JLabel movieImage = new JLabel(image);
            
            JLabel movieRate = new JLabel("Stars: " + searchedMovieList.get(i + 2));
            JLabel movieReview = new JLabel("# Review: " + searchedMovieList.get(i + 3));
            
            movieTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            movieImage.setAlignmentX(Component.CENTER_ALIGNMENT);
            movieRate.setAlignmentX(Component.CENTER_ALIGNMENT);
            movieReview.setAlignmentX(Component.CENTER_ALIGNMENT);

            // set font size for each Jlabel to be 13
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
