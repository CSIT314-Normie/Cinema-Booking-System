package Main.Boundary.Manager;

import Main.Controller.Manager.TicketingArrangementController;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

import Main.Boundary.Home;
import Main.Controller.*;


public class TicketingArrangement extends JFrame implements ActionListener{
    private final ArrayList<String> labelNameList = new ArrayList<>(Arrays.asList("Ticket Type:", "Price:"));
    private final ArrayList<String> userInfo;
    //private ArrayList<String> DBUserInfo = new ArrayList<>();
    private final transient TicketingArrangementController ticketingArrangementController = new TicketingArrangementController();
    private final ArrayList<String[]> ticketingArrangementList;

    private final JLabel myTicketArrangementLabel = new JLabel("Ticket Information");

    private final JButton updateButton = new JButton("Update Ticket Price");
    /*private final JButton updateStudentButton = new JButton("Update Ticket Price for Student");
    private final JButton updateAdultButton = new JButton("Update Ticket Price for Adult");
    private final JButton updateSeniorButton = new JButton("Update Ticket Price for Senior");*/
    private final JButton homeButton = new JButton("Home");

    // Frame's top, middle and bottom row
    private final JPanel topRow = new JPanel();
    private final JPanel botRow = new JPanel();

    // Frame overview
    private final JPanel overviewList = new JPanel(new BorderLayout());

    public TicketingArrangement(ArrayList<String> userInfo) {
        super("Cinema Manager - Ticketing Arrangement");
        this.userInfo = userInfo;
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        // Get the user info from the database
        // Need to add into sequence diagram
        this.ticketingArrangementList = ticketingArrangementController.getTicketingArrangement();
    

        // Put a JLabel called "Ticket Information" on the top row
        myTicketArrangementLabel.setFont(new Font("Serif", Font.PLAIN, 40));
        topRow.add(myTicketArrangementLabel);

        // Top row "My Profile"
        overviewList.add(topRow, BorderLayout.NORTH);

         // Middle row contains the labels and text fields
        JPanel middleRow = new JPanel();
        middleRow.setLayout(new BoxLayout(middleRow, BoxLayout.Y_AXIS));

        for(int j = 0; j < 3; j++){
            for (int i = 0; i < 2; i++) {
                // Create a label with the label name
                JLabel label = new JLabel(labelNameList.get(i));

                label.setFont(new Font("Serif", Font.PLAIN, 25));
                label.setPreferredSize(new Dimension(200, 20));
                label.setHorizontalAlignment(SwingConstants.RIGHT);
                label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
    
                // Create a ticket label 
                JLabel ticketLabel;

                if (i == 1) {
                    ticketLabel = new JLabel("$"+ ticketingArrangementList.get(j)[i]);
                } else {
                    ticketLabel = new JLabel(ticketingArrangementList.get(j)[i]);
                }

                ticketLabel.setFont(new Font("Serif", Font.PLAIN, 25));
                ticketLabel.setPreferredSize(new Dimension(40, 20));
                ticketLabel.setHorizontalAlignment(SwingConstants.LEFT);


                // Add the label and text field to the panel e.g [First Name]: [John]
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(label, BorderLayout.WEST);
                panel.add(ticketLabel, BorderLayout.CENTER);
                panel.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0));
    
                middleRow.add(panel);
                
            }
        }
        
        // Middle row
        overviewList.add(middleRow, BorderLayout.CENTER);

        // Add actionlistener to create button
        //updateButton.addActionListener(this);
        updateButton.addActionListener(this);
        homeButton.addActionListener(this);
        //reviewButton.addActionListener(this);
        /*botRow.add(updateStudentButton);
        botRow.add(updateAdultButton);
        botRow.add(updateSeniorButton);*/
        botRow.add(updateButton);
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
            case "Update Ticket Price":
                dispose();
                new UpdateTicket(userInfo);
                break;
        
        }
    }
    
}
