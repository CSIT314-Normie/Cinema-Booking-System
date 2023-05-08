package Main.Boundary;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.util.*;

import Main.Controller.LoginContoller;
import Main.Controller.MovieController;
import Main.Controller.SuspendAccountController;

public class Home extends JFrame implements ActionListener, MouseListener {
    private ArrayList<String> userInfo;

    private final JLabel userRoleLabel = new JLabel();
    private final JPanel panel = new JPanel(new FlowLayout());
    private final JButton logoutButton = new JButton("Logout");
    private final JButton updateButton = new JButton("Update");
    private final JButton profileButton = new JButton("Profile");
    private JScrollPane scrollPane;
 
    private transient LoginContoller loginController;
    private transient MovieController movieController = new MovieController(); 

    // Get movies from database
    private JPanel movieListPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));  
    private ArrayList<String> searchedMovieList;  

    // search movies text field (CUSTOMER ONLY)
    private JTextField searchField = new JTextField(40);

    // manage user accounts (USER ADMIN ONLY)
    private JPanel accountsPanel = new JPanel(new BorderLayout());
    private DefaultTableModel tableModel;
    private ArrayList<String[]> allAccounts;
    private String[] selectedAccount;

    private JButton editAccountButton = new JButton("Edit Account");
    private JButton suspendAccountButton = new JButton("Suspend Account");
    private JButton addAccountButton = new JButton("Create Account");

    // View ALL movies (CINEMA MANAGER ONLY)
    private JPanel allMoviesPanel = new JPanel(new FlowLayout());
    private ArrayList<String> allMoviesList;
    private String[] selectedMovie;

    private JButton editMovieButton = new JButton("Edit Movie Info");
    
    // suspend acc controller
    private SuspendAccountController suspendAccountController = new SuspendAccountController();

    
    public Home(ArrayList<String> userInfo) {
        super("CSIT 314 Cinema Booking System - Home");
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

        // To display different home page for different user role
        switch (userInfo.get(0)) {
            case "User Admin":
                // Admin Home page
                // Get all accounts from database
                this.allAccounts = loginController.getAllUserAccounts(); 

                accountsPanel.setPreferredSize(new Dimension(750, 600));
                editAccountButton.setSize(50, 30);

                // Add all accounts to the content panel 
                displayAllAccounts();
                
                // addscrollpane to the frame  
                add(accountsPanel, BorderLayout.CENTER);

                editAccountButton.addActionListener(this);
                suspendAccountButton.addActionListener(this);
                addAccountButton.addActionListener(this);

                break;
            case "Customer":
                // Customer Home page
                searchedMovieList = movieController.getAvailableMovies();

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

                break; 
            case "Cinema Manager":
                allMoviesList = movieController.getAllMovies();
                //Ticket Arrangement options for price of tickets
                JButton viewTicketArrangementButton = new JButton("Ticket Arrangement"); 

                JPanel cinemaManagerPanel = new JPanel(new FlowLayout());
                cinemaManagerPanel.setPreferredSize(new Dimension(1035, 100));
                allMoviesPanel.setPreferredSize(new Dimension(1035, 500));

                JButton addMovieButton = new JButton("Add Movie");
                
                cinemaManagerPanel.add(viewTicketArrangementButton);
                cinemaManagerPanel.add(addMovieButton);
                cinemaManagerPanel.add(editMovieButton);
                
                displayMovies();

                add(cinemaManagerPanel, BorderLayout.CENTER);
                add(allMoviesPanel, BorderLayout.SOUTH);

                pack();
                
                addMovieButton.addActionListener(this);
                viewTicketArrangementButton.addActionListener(this);
                editMovieButton.addActionListener(this);
                break;
            
            case "Cinema Owner":
                // Default Home  
                // TODO: Cinema Owner Home page
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

            case "Edit Account":
                if (selectedAccount == null) {
                    JOptionPane.showMessageDialog(null, "Please select an account to edit", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    System.out.println("[+] Admin - Move to Edit Account page");
                    dispose();
                    new UpdateAccountInfo(userInfo, selectedAccount);
                } 
                break;
            
            case "Suspend Account":
                if (selectedAccount == null) {
                    JOptionPane.showMessageDialog(null, "Please select an account to delete", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    System.out.println("[+] Admin - Suspend account");
                    if (suspendAccountController.suspendAccount(selectedAccount[2])) {
                        JOptionPane.showMessageDialog(null, "Account suspended successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error suspending account", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    allAccounts = loginController.getAllUserAccounts();
                    displayAllAccounts();
                } 
                break;
            
            case "Create Account":
                System.out.println("[+] Admin - Move to Add Account page");
                dispose();
                new CreateAccount(userInfo);
                break;
            
            case "Ticketing History":
                System.out.println("[+] Customer - Move to Ticketing History page");
                dispose();
                new TicketingHistory(userInfo);
                break;

            case "Ticket Arrangement":
                System.out.println("[+] Cinema Manager - Move to Ticketing Arrangement page");
                dispose();
                new TicketingArrangement(userInfo);
                break;
            
            case "Add Movie":
                System.out.println("[+] Cinema Manager - Move to Add Movie page");
                dispose();
                new AddMovie(userInfo);
                break;
            
            case "Edit Movie Info":
                // check if a movie is selected
                if (selectedMovie == null) {
                    JOptionPane.showMessageDialog(null, "Please select a movie to edit", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    System.out.println("[+] Cinema Manager - Move to Edit Movie Info page");
                    dispose();
                    new UpdateMovieInfo(userInfo, selectedMovie);
                }
                break;
            
            case "View Screening Sessions":
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
     * Display all user accounts in a table, and allow user admins to "EDIT", "DELETE" and "SUSPEND" accounts - USER ADMIN ONLY
     * 
     */
    public void displayAllAccounts() {
        allAccounts.clear();
        allAccounts = loginController.getAllUserAccounts(); 

        // Remove all components from the panel
        accountsPanel.removeAll();

        JPanel buttonPane = new JPanel();
        buttonPane.add(editAccountButton); 
        buttonPane.add(suspendAccountButton);
        buttonPane.add(addAccountButton);

        accountsPanel.add(buttonPane, BorderLayout.CENTER); 

        String columns[] = {"First Name", "Last Name", "Email", "Date of Birth", "Role", "activeStatus"};
        tableModel = new DefaultTableModel(columns, 0);

        for (String[] row : allAccounts) {
            tableModel.addRow(row);
        } 
         
        JTable allUserTable = new JTable(tableModel);    
        
        // Add the JTable to a JScrollPane
        scrollPane = new JScrollPane(allUserTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(750, 400));
         
        accountsPanel.add(scrollPane, BorderLayout.NORTH);

        // add listener for each row in the table
        ListSelectionModel selectionModel = allUserTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = allUserTable.getSelectedRow();
                    // Perform action on the selected row (account) here
                    // add account data to selectedAccount array
                    selectedAccount = new String[] {tableModel.getValueAt(selectedRow, 0).toString(),
                                                    tableModel.getValueAt(selectedRow, 1).toString(),
                                                    tableModel.getValueAt(selectedRow, 2).toString(),
                                                    tableModel.getValueAt(selectedRow, 3).toString(),
                                                    tableModel.getValueAt(selectedRow, 4).toString()};  
                }
            }
        });   

        // refresh the panel
        accountsPanel.revalidate();
        accountsPanel.repaint();
    }

    /*
     * Search for movies - CUSTOMER ONLY
     * @param searchQuery 
     */

    public void searchedMovies(String searchQuery) {
        searchedMovieList = movieController.getAvailableMovies();

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
     * All movies are displayed by default. Panel is cleared before displaying searched movies
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
            Image scaledImage = image.getImage().getScaledInstance(150,200, Image.SCALE_SMOOTH);
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
 

    /*
     * Display movies in the system and their information - CINEMA MANAGER ONLY
     */
    public void displayMovies() {
        allMoviesPanel.removeAll();  

        String[] columns = {"Movie Name", "Image", "Rating", "Review", "Description", "Status" , "Duration"};
        tableModel = new DefaultTableModel(columns, 0); 

        // Add movies to the content panel, and display
        for (int i = 0; i < allMoviesList.size(); i += 7) {
            // get image of the movie
            String imagePath = "./Main/Boundary/assets/" + allMoviesList.get(i + 1);
            ImageIcon image = new ImageIcon("./Main/Boundary/assets/" + allMoviesList.get(i + 1));
            Image scaledImage = image.getImage().getScaledInstance(150,200, Image.SCALE_SMOOTH);
            image = new ImageIcon(scaledImage); 

            // add movie info to a row
            tableModel.addRow(new Object[] {allMoviesList.get(i), image, allMoviesList.get(i + 2), allMoviesList.get(i + 3), allMoviesList.get(i + 4), allMoviesList.get(i + 5), allMoviesList.get(i + 6)});
        }

        JTable allMoviesTable = new JTable(tableModel);  
        allMoviesTable.setRowHeight(220);
        
        // render image in the table
        allMoviesTable.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());

        // add listener for each row in the table
        ListSelectionModel selectionModel = allMoviesTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = allMoviesTable.getSelectedRow();
                    // Perform action on the selected row (movie) here
                    // add movie data to selectedMovie array (do not add the image)
                    selectedMovie = new String[] {tableModel.getValueAt(selectedRow, 0).toString(),   
                                                    tableModel.getValueAt(selectedRow, 4).toString(),
                                                    tableModel.getValueAt(selectedRow, 5).toString(),
                                                    tableModel.getValueAt(selectedRow, 6).toString()};  
                }
            }
        });   

        // Add the JTable to a JScrollPane
        scrollPane = new JScrollPane(allMoviesTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(750, 400));
         
        allMoviesPanel.add(scrollPane);

        allMoviesPanel.repaint();
        allMoviesPanel.revalidate(); 
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
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
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