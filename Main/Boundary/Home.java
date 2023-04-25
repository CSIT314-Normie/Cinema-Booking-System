package Main.Boundary;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.*;

import Main.Controller.LoginContoller;
import Main.Controller.MovieController;

public class Home extends JFrame implements ActionListener {
    private final JLabel userRoleLabel = new JLabel();
    private final JPanel panel = new JPanel(new FlowLayout());
    private final JButton logoutButton = new JButton("Logout");
    private final JButton updateButton = new JButton("Update");
    private final JButton profileButton = new JButton("Profile");

    private ArrayList<String> userInfo;
    private transient LoginContoller loginController;
    private transient MovieController movieController = new MovieController();

    // Get movies from database
    private final ArrayList<String> movieList = movieController.getMovies();


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

        // add user role lable and buttons to the frame
        panel.add(userRoleLabel);
        panel.add(updateButton);
        panel.add(profileButton);
        panel.add(logoutButton);

        // Set up panel for content to be displayed
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Height 700 is the height of the content panel
        contentPanel.setPreferredSize(new Dimension(1035, 700));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Add movie list to the content panel
        JPanel movieListPanel = new JPanel(new GridLayout(1, 0)); // Change layout manager to have 1 row and multiple columns

        for (int i = 0; i < movieList.size(); i+= 4) {
            JPanel moviePanel = new JPanel(new GridLayout(0, 1));
            
            JLabel movieTitle = new JLabel(movieList.get(i));
            JLabel movieImage = new JLabel("path\\to\\image" + movieList.get(i + 1));
            JLabel movieRate = new JLabel("Stars: " + movieList.get(i + 2));
            JLabel movieReivew = new JLabel("# Review: " + movieList.get(i + 3)); 
            
            moviePanel.add(movieTitle);
            moviePanel.add(movieImage);
            moviePanel.add(movieRate);
            moviePanel.add(movieReivew);

            movieListPanel.add(moviePanel);
        }

        // Calculate preferred width for movieListPanel
        int preferredWidth = movieList.size() * 100; // Assume each movie label takes 100 pixels in width
        movieListPanel.setPreferredSize(new Dimension(preferredWidth, 50));

        JScrollPane scrollPane = new JScrollPane(movieListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(250 * 4, 100)); // Set the preferred width to be 1/4 of the movieListPanel width


        // add panels to the frame
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

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
        }
    }
}
