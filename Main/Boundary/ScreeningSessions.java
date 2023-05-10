package Main.Boundary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList; 

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScreeningSessions extends JFrame implements ActionListener {

    private final ArrayList<String> userInfo;

    private final String[] halls = {"A", "B", "C", "D"};
    private String selectedHall;

    private final JComboBox<String> hallDropDown = new JComboBox<>(halls);

    private final JButton homeButton = new JButton("Home");
    private final JButton updateSessionButton = new JButton("Update Screening Status");
    private final JButton addSessionButton = new JButton("Add Screening");
    private final JButton deleteSessionButton = new JButton("Delete Screening");

    private final JPanel screeningListPanel = new JPanel(new FlowLayout());


    public ScreeningSessions(ArrayList<String> userInfo) {
        super("CSIT 314 Cinema Booking System - Home");
        this.userInfo = userInfo;
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null); 
        setVisible(true); // Show the frame

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setPreferredSize(new java.awt.Dimension(1035, 50));
        topPanel.add(homeButton);

        JPanel centerPanel = new JPanel(new FlowLayout());
        centerPanel.setPreferredSize(new java.awt.Dimension(1035, 700));
        centerPanel.add(hallDropDown);

        // add listener for hallDropDown
        hallDropDown.addActionListener(e -> {
            // display screenings for the selected hall
            displayScreenings();
        });

        screeningListPanel.setPreferredSize(new java.awt.Dimension(1035, 600));

        homeButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home":
                new Home(userInfo);
                dispose();
                break;
            case "Update Screening Status":
                 
                break;
            case "Add Screening":
                 
                break;
            case "Delete Screening":
                 
                break;
        }
         
    }

    /**
     * display all screening sessions in a table format
     * - based on the selected hall
     */
    public void displayScreenings() {
        screeningListPanel.removeAll();

        JLabel screeningIDLabel = new JLabel("Movie Screenings for: Hall " + selectedHall);
        screeningListPanel.add(screeningIDLabel);

        if (selectedHall.equals("") || selectedHall == null) {
            // display all screening sessions for all halls

        } else {
            // get all screening sessions for the selected hall 
        }
    }
    
}
