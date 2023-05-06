package Main.Boundary;

import Main.Controller.ProfileController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class TicketingArrangement extends JFrame implements ActionListener{
    private final ArrayList<String> labelNameList = new ArrayList<>(Arrays.asList("Ticket Type:", "Price:"));
    private ArrayList<String> userInfo;
    private ArrayList<String> DBUserInfo = new ArrayList<>();

    private final JLabel myTicketArrangementLabel = new JLabel("Ticket Information");

    private final JButton updateStudentButton = new JButton("Update Ticket Price for Student");
    private final JButton updateAdultButton = new JButton("Update Ticket Price for Adult");
    private final JButton updateSeniorButton = new JButton("Update Ticket Price for Senior");
    private final JButton homeButton = new JButton("Home");

    private static final ProfileController profileController = new ProfileController();

    // Frame's top, middle and bottom row
    private final JPanel topRow = new JPanel();
    private final JPanel botRow = new JPanel();

    // Frame overview
    private final JPanel overviewList = new JPanel(new BorderLayout());

    public TicketingArrangement(ArrayList<String> userInfo) {
        super("Welcome to CSIT 314 Cinema Booking System - Ticketing Arrangement");
        this.userInfo = userInfo;
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        // Get the user info from the database
        DBUserInfo = profileController.getUserInfo(this.userInfo.get(2));

        // Put a JLabel called "Ticket Information" on the top row
        myTicketArrangementLabel.setFont(new Font("Serif", Font.PLAIN, 40));
        topRow.add(myTicketArrangementLabel);

        // Top row "My Profile"
        overviewList.add(topRow, BorderLayout.NORTH);

         // Middle row contains the labels and text fields
        JPanel middleRow = new JPanel();
        middleRow.setLayout(new BoxLayout(middleRow, BoxLayout.Y_AXIS));

        for(int j = 0; j<3; j++){
            for (int i = 0; i < 2; i++) {
                // Create a label with the label name E.g "First Name", "Last Name", etc
                JLabel label = new JLabel(labelNameList.get(i));
                label.setFont(new Font("Serif", Font.PLAIN, 25));
                label.setPreferredSize(new Dimension(200, 20));
                label.setHorizontalAlignment(SwingConstants.RIGHT);
                label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
    
                /*// Create a label with the user info from the database E.g. "First Name: John"
                JLabel userInfoLabel = new JLabel(DBUserInfo.get(i));
                userInfoLabel.setFont(new Font("Serif", Font.PLAIN, 25));
                userInfoLabel.setPreferredSize(new Dimension(40, 20));
                userInfoLabel.setHorizontalAlignment(SwingConstants.LEFT);*/
    
                // Add the label and text field to the panel e.g [First Name]: [John]
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(label, BorderLayout.WEST);
                //panel.add(userInfoLabel, BorderLayout.CENTER);
                panel.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0));
    
                middleRow.add(panel);
                
            }
        }
        
        // Middle row
        overviewList.add(middleRow, BorderLayout.CENTER);

        // Add actionlistener to create button
        //updateButton.addActionListener(this);
        homeButton.addActionListener(this);
        //reviewButton.addActionListener(this);
        botRow.add(updateStudentButton);
        botRow.add(updateAdultButton);
        botRow.add(updateSeniorButton);
        botRow.add(homeButton);


        // Bottom row contains the button
        overviewList.add(botRow, BorderLayout.SOUTH);

        // Set the preferred size of the overviewList panel
        overviewList.setPreferredSize(new Dimension(800, 600));

        // Add the overviewList to the frame
        add(overviewList);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home":
                dispose();
                new Home(userInfo);
                break;
        
        }
    }
    
}
