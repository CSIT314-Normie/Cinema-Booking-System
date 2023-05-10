package Main.Boundary;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import Main.Boundary.Customer.Review;
import Main.Controller.ProfileController;

public class Profile extends JFrame implements ActionListener {
    private final ArrayList<String> labelNameList = new ArrayList<>(Arrays.asList("First Name:", "Last Name:", "Email:", "Date of Birth:", "Password:"));
    private final ArrayList<String> userInfo;
    private final ArrayList<String> DBUserInfo;

    private final JLabel myProfileLabel = new JLabel("My Profile");

    private final JButton updateButton = new JButton("Edit Profile");
    private final JButton homeButton = new JButton("Home");
    private final JButton reviewButton = new JButton("Review");

    private final transient ProfileController profileController = new ProfileController();

    // Frame's top, middle and bottom row
    private final JPanel topRow = new JPanel();
    private final JPanel botRow = new JPanel();

    // Frame overview
    private final JPanel overviewList = new JPanel(new BorderLayout());

    public Profile(ArrayList<String> userInfo) {
        super("Profile");
        this.userInfo = userInfo;
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        // Get the user info from the database
        DBUserInfo = profileController.getUserInfo(this.userInfo.get(2));

        // Put a JLabel called "My Profile" on the top row
        myProfileLabel.setFont(new Font("Serif", Font.PLAIN, 40));
        topRow.add(myProfileLabel);

        // Top row "My Profile"
        overviewList.add(topRow, BorderLayout.NORTH);

        // Middle row contains the labels and text fields
        JPanel middleRow = new JPanel();
        middleRow.setLayout(new BoxLayout(middleRow, BoxLayout.Y_AXIS));

        for (int i = 0; i < 4; i++) {
            // Create a label with the label name E.g "First Name", "Last Name", etc
            JLabel label = new JLabel(labelNameList.get(i));
            label.setFont(new Font("Serif", Font.PLAIN, 25));
            label.setPreferredSize(new Dimension(200, 20));
            label.setHorizontalAlignment(SwingConstants.RIGHT);
            label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));

            // Create a label with the user info from the database E.g. "First Name: John"
            JLabel userInfoLabel = new JLabel(DBUserInfo.get(i));
            userInfoLabel.setFont(new Font("Serif", Font.PLAIN, 25));
            userInfoLabel.setPreferredSize(new Dimension(40, 20));
            userInfoLabel.setHorizontalAlignment(SwingConstants.LEFT);

            // Add the label and text field to the panel e.g [First Name]: [John]
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(label, BorderLayout.WEST);
            panel.add(userInfoLabel, BorderLayout.CENTER);
            panel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));

            middleRow.add(panel);
        }

        // Middle row
        overviewList.add(middleRow, BorderLayout.CENTER);

        // Add actionlistener to create button
        updateButton.addActionListener(this);
        homeButton.addActionListener(this);

        if (userInfo.get(0) == "Customer") {
            botRow.add(reviewButton);
            reviewButton.addActionListener(this);
        }

        botRow.add(updateButton);
        botRow.add(homeButton);
        

        // Bottom row contains the button
        overviewList.add(botRow, BorderLayout.SOUTH);

        // Set the preferred size of the overviewList panel
        overviewList.setPreferredSize(new Dimension(800, 500));

        // Add the overviewList to the frame
        add(overviewList);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Edit Profile":
                dispose();
                new Update(userInfo);
                break;

            case "Home":
                dispose();
                new Home(userInfo);
                break;
            
            case "Review":
                dispose();
                new Review(userInfo);
                break;
        }
    }
}
