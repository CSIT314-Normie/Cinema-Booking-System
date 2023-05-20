package Main.Boundary.Manager;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;


import Main.Controller.Manager.*;

public class ManagerHome extends JFrame implements ActionListener {
    private final ArrayList<String> userInfo;

    private final JLabel userRoleLabel = new JLabel();
    private final JPanel panel = new JPanel(new FlowLayout());
    private final JButton logoutButton = new JButton("Logout");
    private final JButton updateButton = new JButton("Update");
    private final JButton profileButton = new JButton("Profile");
    private JTextField searchField = new JTextField(20);
     
    private final JPanel allMoviesPanel = new JPanel(new FlowLayout());
    private ArrayList<String> allMoviesList;
    private String[] selectedMovie;
    private final JButton editMovieButton = new JButton("Edit Movie Info");

    private final transient ManagerLoginController loginController;
    private final transient AllMoviesController allMoviesController = new AllMoviesController();
    private final transient SearchMovieController searchMovieController = new SearchMovieController();

    public ManagerHome(ArrayList<String> userInfo) {
        super("CSIT 314 Cinema Booking System - Home");
        this.userInfo = userInfo;
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true); // Show the frame

        // Login "SESSION" for user to allow user to logout, can be further implemented
        loginController = new ManagerLoginController(userInfo.get(0), userInfo.get(1), userInfo.get(2));
        userRoleLabel.setText("User Role: " + userInfo.get(0) + " | Email: " + userInfo.get(2));
        panel.setPreferredSize(new Dimension(1035, 50));

        panel.add(userRoleLabel);
        panel.add(updateButton);
        panel.add(profileButton);
        panel.add(logoutButton);
        
        // get all movies
        allMoviesList = allMoviesController.getAllMovies();

        // buttons for cinema manager - view ticket arrangement, add movie, edit movie, screenings page and manage reviews
        JButton ticketArrangementButton = new JButton("Ticket Arrangement");
        JButton addMovieButton = new JButton("Add Movie");
        JButton editMovieButton = new JButton("Edit Movie Info");
        JButton screeningButton = new JButton("Screening");
        JButton manageReviewsButton = new JButton("Manage Reviews");

        JPanel cinemaManagerPanel = new JPanel(new FlowLayout());
        cinemaManagerPanel.setPreferredSize(new Dimension(1035, 100));
        allMoviesPanel.setPreferredSize(new Dimension(1035, 500));

        cinemaManagerPanel.add(ticketArrangementButton);
        cinemaManagerPanel.add(addMovieButton);
        cinemaManagerPanel.add(editMovieButton);
        cinemaManagerPanel.add(screeningButton);
        cinemaManagerPanel.add(manageReviewsButton);

        // search panel to search for movies
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setPreferredSize(new Dimension(1035, 50)); 
        JButton searchButton = new JButton("Search");

        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        cinemaManagerPanel.add(searchPanel);

        displayMovies();

        add(cinemaManagerPanel, BorderLayout.CENTER);
        add(allMoviesPanel, BorderLayout.SOUTH); 

        addMovieButton.addActionListener(this);
        ticketArrangementButton.addActionListener(this);
        editMovieButton.addActionListener(this);
        screeningButton.addActionListener(this); 

        // add panel to the frame
        add(panel, BorderLayout.NORTH);
        pack();

        // add action listener to the buttons
        logoutButton.addActionListener(this);
        updateButton.addActionListener(this);
        profileButton.addActionListener(this);
        searchButton.addActionListener(this);
        manageReviewsButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Logout":
                loginController.logout(userInfo.get(0));
                dispose();
                new ManagerLogin();
                System.out.println("[+] Successfully logged out");
                break;

            case "Update":
                System.out.println("[+] Move to Update page");
                dispose();
                new ManagerUpdate(userInfo);
                break;

            case "Profile":
                System.out.println("[+] Move to Profile page");
                dispose();
                new ManagerProfile(userInfo);
                break;

            case "Ticket Arrangement":
                System.out.println("[+] Cinema Manager - Move to Ticketing Arrangement page");
                dispose();
                new TicketingArrangement(userInfo);
                break;

            case "Search":
                String searchQuery = searchField.getText();

                System.out.println("[+] Search for: " + searchQuery);
                allMoviesList = searchMovieController.searchMovie(searchQuery);

                displayMovies();
                break;

            case "Add Movie":
                System.out.println("[+] Cinema Manager - Move to Add Movie page");
                dispose();
                new AddMovie(userInfo);
                break;

            case "Edit Movie Info":
                // check if a movie is selected
                if (selectedMovie == null) {
                    JOptionPane.showMessageDialog(null, "Please select a movie to edit", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    System.out.println("[+] Cinema Manager - Move to Edit Movie Info page");
                    dispose();
                    new UpdateMovieInfo(userInfo, selectedMovie);
                }
                break;

            case "Screening":
                // go to screening sessions page
                System.out.println("[+] Cinema Manager - Move to Screening page");
                dispose();
                new ScreeningSessions(userInfo);
                break;
            
            case "Manage Reviews":
                // go to manage reviews page
                System.out.println("[+] Cinema Manager - Move to Manage Reviews page");
                dispose();
                new ManageReviews(userInfo);
                break;
        }
    }

    /*
     * Display movies in the system and their information - CINEMA MANAGER ONLY
     */
    public void displayMovies() {
        DefaultTableModel tableModel;
        JScrollPane scrollPane;
        allMoviesPanel.removeAll();

        String[] columns = { "Movie Name", "Image", "Rating", "Review", "Description", "Status", "Duration" };
        tableModel = new DefaultTableModel(columns, 0);

        // Add movies to the content panel, and display
        for (int i = 0; i < allMoviesList.size(); i += 7) {
            // get image of the movie
            ImageIcon image = new ImageIcon("./Main/Boundary/assets/" + allMoviesList.get(i + 1));
            Image scaledImage = image.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
            image = new ImageIcon(scaledImage);

            // add movie info to a row
            tableModel.addRow(
                new Object[] { 
                    allMoviesList.get(i), 
                    image, allMoviesList.get(i + 2), 
                    allMoviesList.get(i + 3), 
                    allMoviesList.get(i + 4), 
                    allMoviesList.get(i + 5), 
                    allMoviesList.get(i + 6) 
                }
            );
        }

        JTable allMoviesTable = new JTable(tableModel);
        allMoviesTable.setRowHeight(220);

        // render image in the table
        allMoviesTable.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());

        // add listener for each row in the table
        ListSelectionModel selectionModel = allMoviesTable.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = allMoviesTable.getSelectedRow();
                // Perform action on the selected row (movie) here
                // add movie data to selectedMovie array (do not add the image)
                selectedMovie = new String[] { 
                    tableModel.getValueAt(selectedRow, 0).toString(),
                    tableModel.getValueAt(selectedRow, 4).toString(),
                    tableModel.getValueAt(selectedRow, 5).toString(),
                    tableModel.getValueAt(selectedRow, 6).toString()
                };
            }
        });

        // Add the JTable to a JScrollPane
        scrollPane = new JScrollPane(allMoviesTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(750, 400));

        allMoviesPanel.add(scrollPane);
        allMoviesPanel.repaint();
        allMoviesPanel.revalidate();
    }
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