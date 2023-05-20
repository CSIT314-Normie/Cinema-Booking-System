package Main.Boundary.Manager;

import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import Main.Controller.Manager.*;
/*
 * This is the page where the manager can view and manage all screening sessions 
 */

public class ScreeningSessions extends JFrame implements ActionListener {

    private final ArrayList<String> userInfo;

    private final String[] halls = {"all halls", "A", "B", "C", "D"};
    private String selectedHall = "all halls";
    private String newScreeningStatus;
    private String[] selectedScreening;

    private final JComboBox<String> hallDropDown = new JComboBox<>(halls);

    private final JButton homeButton = new JButton("Home"); 
    private final JButton updateStatusButton = new JButton("Update Status");
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
        centerPanel.setPreferredSize(new java.awt.Dimension(1035, 650));
        centerPanel.add(hallDropDown);

        displayScreenings();

        // add listener for hallDropDown
        hallDropDown.addActionListener(e -> {
            // display screenings for the selected hall
            selectedHall = (String) hallDropDown.getSelectedItem();
            displayScreenings();
        });

        screeningListPanel.setPreferredSize(new Dimension(1035, 600));
        centerPanel.add(screeningListPanel);

        // buttons to manage screening sessions
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setPreferredSize(new Dimension(1035, 50));
        buttonPanel.add(updateStatusButton);
        buttonPanel.add(addSessionButton);
        buttonPanel.add(deleteSessionButton);

        // add panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // add action listeners
        homeButton.addActionListener(this); 
        updateStatusButton.addActionListener(this);
        addSessionButton.addActionListener(this);
        deleteSessionButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home":
                dispose();
                new ManagerHome(userInfo);
                
                break; 

            case "Update Status":
                // TODO: test after booking has been implemented!
                if (selectedScreening == null) {
                    JOptionPane.showMessageDialog(null, "Please select a screening session.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    UpdateScreeningStatusController updateScreeningStatusController = new UpdateScreeningStatusController();
                    if (selectedScreening[7].equals("Available") && selectedScreening[8].equals("16")) {
                        newScreeningStatus = "Fully Booked";
                    } else {
                        newScreeningStatus = "Available";
                    }

                    // update screening status
                   if (updateScreeningStatusController.updateScreeningStatus(selectedScreening[0], newScreeningStatus)) {
                          JOptionPane.showMessageDialog(null, "Screening status updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                          displayScreenings();
                    } else {
                          JOptionPane.showMessageDialog(null, "Screening status update failed.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;

            case "Add Screening":
                // ADD NEW SCREENING
                dispose();
                new AddScreeningSession(userInfo);

                break;
            case "Delete Screening":
                // check if a screening session is selected
                if (selectedScreening == null) {
                    JOptionPane.showMessageDialog(null, "Please select a screening session.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // check if the date of screening has passed
                    String date = selectedScreening[3];
                    Date currentDate = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                    try { 
                        if (currentDate.after(sdf.parse(date))) {
                            // delete screening session
                            SuspendScreeningController deleteScreeningSessionController = new SuspendScreeningController();
                            if (deleteScreeningSessionController.suspendScreening(selectedScreening[0])) {
                                JOptionPane.showMessageDialog(null, "Screening session suspended.", "Success", JOptionPane.INFORMATION_MESSAGE);
                                displayScreenings();
                            } else {
                                JOptionPane.showMessageDialog(null, "Suspend screening session failed.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Cannot suspend screening session that has not ended.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        
                    } catch (ParseException ex) {
                        System.err.println("Error parsing date: " + ex.getMessage());
                    }
 
                }
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
        
        String[] columns = {"Screening ID", "Movie Name", "Hall", "Date", "Start Time", "End Time", "Duration", "Screening Status", "Reserved Seats"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (int i = 0; i < screenings.size(); i += 9) {
            String screeningID = screenings.get(i);
            String movieName = screenings.get(i + 1);
            String hall = screenings.get(i + 2);
            String date = screenings.get(i + 3);
            String startTime = screenings.get(i + 4);
            String endTime = screenings.get(i + 5);
            String duration = screenings.get(i + 6);
            String screeningStatus = screenings.get(i + 7);
            String seatsReserved = screenings.get(i + 8);
 
            tableModel.addRow(new Object[] {screeningID, movieName, hall, date, startTime, endTime, duration, screeningStatus, seatsReserved});
        }

        JTable screeningTable = new JTable(tableModel);  

        // add listener for screeningTable
        ListSelectionModel selectionModel = screeningTable.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
                if (!selectionModel.isSelectionEmpty()) {
                    int selectedRow = selectionModel.getMinSelectionIndex(); 
                    selectedScreening = new String[] {tableModel.getValueAt(selectedRow, 0).toString(),
                                                    tableModel.getValueAt(selectedRow, 1).toString(), 
                                                    tableModel.getValueAt(selectedRow, 2).toString(), 
                                                    tableModel.getValueAt(selectedRow, 3).toString(), 
                                                    tableModel.getValueAt(selectedRow, 4).toString(), 
                                                    tableModel.getValueAt(selectedRow, 5).toString(), 
                                                    tableModel.getValueAt(selectedRow, 6).toString(), 
                                                    tableModel.getValueAt(selectedRow, 7).toString(),
                                                    tableModel.getValueAt(selectedRow, 8).toString()}; 
                }
            }
        );

         // Add the JTable to a JScrollPane
        scrollPane = new JScrollPane(screeningTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(900, 400));

        screeningListPanel.add(scrollPane);

        screeningListPanel.repaint();
        screeningListPanel.revalidate();
    }
    
}
