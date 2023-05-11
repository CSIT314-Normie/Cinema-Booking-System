package Main.Boundary.Manager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList; 

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Main.Boundary.Home;

import Main.Controller.Manager.*;
/*
 * This is the page where the manager can view and manage all screening sessions 
 */

public class ScreeningSessions extends JFrame implements ActionListener {

    private final ArrayList<String> userInfo;

    private final String[] halls = {"all halls", "A", "B", "C", "D"};
    private String selectedHall = "all halls";

    private final JComboBox<String> hallDropDown = new JComboBox<>(halls);

    private final JButton homeButton = new JButton("Home");
    private final JButton updateSessionButton = new JButton("Update Screening Status");
    private final JButton addSessionButton = new JButton("Add Screening");
    private final JButton deleteSessionButton = new JButton("Delete Screening");

    private transient GetScreeningsInfoController getScreeningsInfoController = new GetScreeningsInfoController();

    private final JPanel screeningListPanel = new JPanel(new FlowLayout());
    private ArrayList<String> screenings;
    private JScrollPane scrollPane;

    public ScreeningSessions(ArrayList<String> userInfo) {
        super("Cinema Manager - Screening Sessions");
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

        displayScreenings();

        // add listener for hallDropDown
        hallDropDown.addActionListener(e -> {
            // display screenings for the selected hall
            selectedHall = (String) hallDropDown.getSelectedItem();
            displayScreenings();
        });

        screeningListPanel.setPreferredSize(new Dimension(1035, 600));

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(screeningListPanel, BorderLayout.SOUTH);

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

        JLabel screeningIDLabel = new JLabel("Movie Screenings for Hall: " + selectedHall);
        screeningIDLabel.setPreferredSize(new Dimension(800, 50));

        screeningListPanel.add(screeningIDLabel);

        if (!selectedHall.equals("all halls")) {
            screenings = getScreeningsInfoController.getScreeningsForHall(selectedHall);
        } else {
            screenings = getScreeningsInfoController.getAllScreenings();
        }
        
        String[] columns = {"Movie Name", "Hall", "Date", "Start Time", "End Time", "Duration", "Screening Status", "Reserved Seats"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (int i = 0; i < screenings.size(); i += 9) {
            String movieName = screenings.get(i + 1);
            String hall = screenings.get(i + 2);
            String date = screenings.get(i + 3);
            String startTime = screenings.get(i + 4);
            String endTime = screenings.get(i + 5);
            String duration = screenings.get(i + 6);
            String screeningStatus = screenings.get(i + 7);
            String seatsReserved = screenings.get(i + 8);
 
            tableModel.addRow(new Object[] {movieName, hall, date, startTime, endTime, duration, screeningStatus, seatsReserved});
        }

        JTable screeningTable = new JTable(tableModel);  

         // Add the JTable to a JScrollPane
        scrollPane = new JScrollPane(screeningTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(900, 400));

        screeningListPanel.add(scrollPane);

        screeningListPanel.repaint();
        screeningListPanel.revalidate();
    }
    
}
