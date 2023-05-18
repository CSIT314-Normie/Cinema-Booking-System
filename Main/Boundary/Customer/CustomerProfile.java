package Main.Boundary.Customer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import Main.Controller.Customer.*;

public class CustomerProfile extends JFrame implements ActionListener {
    private final ArrayList<String> labelNameList = new ArrayList<>(Arrays.asList("First Name:", "Last Name:", "Email:", "Date of Birth:", "Password:", "Loyalty Points:"));
    private final ArrayList<String> userInfo;
    private final ArrayList<String> DBUserInfo; 

    private final JLabel myProfileLabel = new JLabel("My Profile");

    private final JButton updateButton = new JButton("Edit Profile");
    private final JButton homeButton = new JButton("Home");  

    private final CustomerProfileController profileController = new CustomerProfileController();
    private final loyaltyPointController loyaltyPointController = new loyaltyPointController();

    // Frame's top, middle and bottom row
    private final JPanel topRow = new JPanel();
    private final JPanel botRow = new JPanel();

    // Frame overview
    private final JPanel overviewList = new JPanel(new BorderLayout());

    public CustomerProfile(ArrayList<String> userInfo) {
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

        // Get the loyalty points from the database
        DBUserInfo.add(loyaltyPointController.getLoyaltyPoint(this.userInfo.get(2)));

        // Put a JLabel called "My Profile" on the top row
        myProfileLabel.setFont(new Font("Serif", Font.PLAIN, 40));
        topRow.add(myProfileLabel);

        // Top row "My Profile"
        overviewList.add(topRow, BorderLayout.NORTH);

        // Middle row contains the labels and text fields
        JPanel middleRow = new JPanel();
        middleRow.setLayout(new BoxLayout(middleRow, BoxLayout.Y_AXIS));

        System.out.println(DBUserInfo);
        System.out.println(labelNameList);

        for (int i = 0; i < labelNameList.size(); i++) {
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
         
        botRow.add(updateButton);
        botRow.add(homeButton);
        
        // Bottom row contains the button
        overviewList.add(botRow, BorderLayout.SOUTH);

        // Set the preferred size of the overviewList panel
        overviewList.setPreferredSize(new Dimension(800, 550));

        // Add the overviewList to the frame
        add(overviewList);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Edit Profile":
                dispose();
                new CustomerUpdate(userInfo);
                break;

            case "Home":
                dispose();
                new CustomerHome(userInfo);
                break;
             
        }
    }
}
