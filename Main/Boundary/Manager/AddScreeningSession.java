package Main.Boundary.Manager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
 
import com.toedter.calendar.JDateChooser;


import Main.Controller.Manager.AddScreeningSessionController;

public class AddScreeningSession extends JFrame implements ActionListener {
    private ArrayList<String> userInfo;

    private ArrayList<String> movies;
    private ArrayList<String> cinema_halls; 
    private ArrayList<String> newScreening;

    private final JButton homeButton = new JButton("Home");
    private final JButton submitButton = new JButton("Submit");
    private final JDateChooser dateChooser = new JDateChooser();

    private final String[] labelList = {"Movie Name: ", "Hall: ", "Date: ", "Time Slot: "};
    private String[] movieNames;
    private String[] halls;
    private String[] timeSlots = {"Morning", "Afternoon 1", "Afternoon 2" , "Evening 1", "Evening 2"};

    private String[] selectedInfo = {"", "", "", "", "", ""}; // movie name, hall, date, time slot, start time, end time

    private transient AddScreeningSessionController addScreeningSessionController = new AddScreeningSessionController();
    
    public AddScreeningSession(ArrayList<String> userInfo) {
        super("Cinema Manager - Add Screening Session");
        this.userInfo = userInfo;
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null); 
        setVisible(true); // Show the frame

        this.movies = addScreeningSessionController.getAvailableMovies();
        this.cinema_halls = addScreeningSessionController.getAllHalls(); 

        this.movieNames = new String[movies.size() / 7];

        // get movie names
        for (int i = 0; i < movieNames.length; i++) {
            if (i == 0) {
                movieNames[i] = movies.get(i);
            }
            else {
                movieNames[i] = movies.get(i * 7);
            } 
        }

        // get halls
        halls = new String[cinema_halls.size() / 3];
        for (int i = 0; i < halls.length; i++) {
            if (i == 0) {
                halls[i] = cinema_halls.get(i);
            }
            else {
                halls[i] = cinema_halls.get(i * 3);
            }
        }
        
        JComboBox<String> movieNameComboBox = new JComboBox<>(movieNames);
        JComboBox<String> hallComboBox = new JComboBox<>(halls);
        JComboBox<String> timeSlotComboBox = new JComboBox<>(timeSlots);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(homeButton);

        // submit button to add the new movie to the database
        JPanel submitPanel = new JPanel(new FlowLayout());
        submitPanel.setPreferredSize(new Dimension(750, 50));
        submitButton.setPreferredSize(new Dimension(100, 30));
        submitPanel.add(submitButton);
        
        JPanel formPanel = new JPanel(); 
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));  
 
        JLabel title = new JLabel("Add Screening Session");
        title.setFont(new Font("Serif", Font.BOLD, 25));
        formPanel.add(title);

        // add labels and text fields
        for (int i = 0; i < labelList.length; i++) {
            JPanel panel = new JPanel(new FlowLayout());
            panel.setPreferredSize(new Dimension(750, 50));

            JLabel label = new JLabel(labelList[i]);
            label.setFont(new Font("Serif", Font.BOLD, 18));
            panel.add(label);

            if (labelList[i].equals("Movie Name: ")) { 
                movieNameComboBox.setSelectedIndex(0);
                panel.add(movieNameComboBox);
            }
            else if (labelList[i].equals("Hall: ")) { 
                panel.add(hallComboBox);
            }
            else if (labelList[i].equals("Date: ")) {
                dateChooser.setDateFormatString("dd/MM/yyyy");
                panel.add(dateChooser); 
            }
            else if (labelList[i].equals("Time Slot: ")) { 
                panel.add(timeSlotComboBox);
            }

            formPanel.add(panel);
        }

        formPanel.add(submitPanel);

        // add listener to dropdowns and date chooser
        movieNameComboBox.addActionListener(e -> selectedInfo[0] = movieNameComboBox.getSelectedItem().toString());

        hallComboBox.addActionListener(e -> selectedInfo[1] = hallComboBox.getSelectedItem().toString());

        timeSlotComboBox.addActionListener(
                e -> {
                    selectedInfo[3] = timeSlotComboBox.getSelectedItem().toString();

                    if (selectedInfo[3].equals("Morning")) {
                        selectedInfo[4] = "09:00am";
                        selectedInfo[5] = "12:00pm";
                    } else if (selectedInfo[3].equals("Afternoon 1")){
                        selectedInfo[4] = "12:15pm";
                        selectedInfo[5] = "15:15pm";
                    } else if (selectedInfo[3].equals("Afternoon 2")) {
                        selectedInfo[4] = "15:30pm";
                        selectedInfo[5] = "18:30pm";
                    } else if (selectedInfo[3].equals("Evening 1")) {
                        selectedInfo[4] = "18:45pm";
                        selectedInfo[5] = "21:45pm";
                    } else if (selectedInfo[3].equals("Evening 2")) {
                        selectedInfo[4] = "22:00pm";
                        selectedInfo[5] = "01:00am";
                    }
                });
        
        add(topPanel, BorderLayout.NORTH); 
        add(formPanel, BorderLayout.CENTER);

        homeButton.addActionListener(this);
        submitButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) { 
        switch (e.getActionCommand()) {
            case "Home":
                dispose();
                new ManagerHome(userInfo);
                break;
            case "Submit":
                Date selectedDate = dateChooser.getDate();
                if (selectedDate == null) {
                    JOptionPane.showMessageDialog(null, "Please select a date", "Error", JOptionPane.ERROR_MESSAGE); 
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    selectedInfo[2] = dateFormat.format(selectedDate);

                    this.newScreening = new ArrayList<>(Arrays.asList(selectedInfo));
                    this.newScreening.add("3 hours");
                    this.newScreening.add("Available");

                    if (newScreening.contains("")) {
                        JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if(!addScreeningSessionController.validateScreeningSession(this.newScreening)) {
                            JOptionPane.showMessageDialog(null, "Screening session already exists", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            addScreeningSessionController.addScreeningSession(this.newScreening);
                            JOptionPane.showMessageDialog(null, "Screening session added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

                            dispose();
                            new ScreeningSessions(userInfo);
                    } 
                } 
            }
                break;
        }   
    }


    
}
