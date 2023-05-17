package Main.Boundary.Customer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList; 
import javax.swing.*;


import Main.Controller.Customer.SeatingPlanController;
import Main.Controller.Customer.ConfirmSeatingController;

public class SeatingPlan extends JFrame implements ActionListener {
    private ArrayList<String> userInfo;
    private String selectedScreeningID;
    private ArrayList<String> movieInfo;
    private String date;

    private ArrayList<String> screeningInfo;
    private ArrayList<String> seats;
    private ArrayList<String> reservedSeats; 

    private ArrayList<String> selectedSeats = new ArrayList<String>();
    private String hall;   

    private final JButton homeButton = new JButton("Home");
    private final JButton backButton = new JButton("Back");
    private final JButton confirmButton = new JButton("Confirm");
    JPanel ticketTypePanel = new JPanel(new FlowLayout());

    private final SeatingPlanController bookMovieController = new SeatingPlanController();
    private final ConfirmSeatingController confirmSeatingController = new ConfirmSeatingController();

    private  JPanel selectedSeatsPanel = new JPanel(new FlowLayout());

    public SeatingPlan(ArrayList<String> userInfo, String selectedScreeningID, ArrayList<String> movieInfo, String date) {
        super("CSIT 314 Cinema Booking System - Book movie: " + movieInfo.get(0));
        this.userInfo = userInfo; 
        this.selectedScreeningID = selectedScreeningID;
        this.movieInfo = movieInfo;
        this.date = date;
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        screeningInfo = bookMovieController.getScreeningInfo(selectedScreeningID);
        hall = screeningInfo.get(2);
        seats = bookMovieController.getSeats(hall);
        reservedSeats = bookMovieController.getReservedSeats(hall, selectedScreeningID); 

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(homeButton);
        topPanel.add(backButton);

        JPanel bookingPanel = new JPanel(new FlowLayout());
        bookingPanel.setPreferredSize(new Dimension(1035, 500));

        JPanel movieInfoPanel = new JPanel(new FlowLayout());
        movieInfoPanel.setPreferredSize(new Dimension(1035, 100));
        JLabel movieinfoLabel = new JLabel("Movie: " + movieInfo.get(0) + " | Hall: " + hall + " | Time: " + screeningInfo.get(5));
        movieInfoPanel.add(movieinfoLabel);
        
        // legend panel shows what the different colors mean
        JPanel legendPanel = new JPanel();
        legendPanel.setPreferredSize(new Dimension(1035, 50));

        for (int i = 0; i < 3; i++) {
            JLabel legendLabel = new JLabel();
            JButton legendButton = new JButton();
            legendButton.setEnabled(false);
            legendButton.setPreferredSize(new Dimension(20, 20));

            if (i == 0) {
                legendLabel.setText("Booked: ");
                legendButton.setBackground(Color.darkGray);
            } else if (i == 1) { 
                legendLabel.setText("Selected: ");
                legendButton.setBackground(Color.green);
            } else if (i == 2) { 
                legendLabel.setText("Available: ");
                legendButton.setBackground(Color.blue);
            }

            legendPanel.add(legendLabel);
            legendPanel.add(legendButton); 
        }

        // Panel to show the selected seats
        selectedSeatsPanel.setPreferredSize(new Dimension(1030, 200));

        // seats panel shows the seats in the hall for the selected screening
        JPanel seatsPanel = new JPanel(new GridLayout(4, 4, 10, 10));
        seatsPanel.setPreferredSize(new Dimension(400, 300));
        seatsPanel.setBorder(BorderFactory.createTitledBorder("Select seats"));

        for (int i = 0; i < seats.size(); i += 4) {
            String seatID = seats.get(i); 

            JButton seatButton = new JButton(seatID); 
            seatButton.setPreferredSize(new Dimension(50, 40));  

            // check if seat is reserved
            if (reservedSeats.contains(seatID)) { 
                seatButton.setEnabled(false);
                seatButton.setBackground(Color.darkGray);
            } else {
                seatButton.setEnabled(true);
                seatButton.setBackground(Color.blue);

                // add action listener to seat button to select/deselect seat
                seatButton.addActionListener(e -> { 
                    if(selectedSeats.contains(seatID)) {
                        // remove seat from selected seats if already selected
                        selectedSeats.remove(seatID);
                        seatButton.setBackground(Color.blue);

                    } else {
                        // add seat to selected seats if not already selected
                        selectedSeats.add(seatID);
                        seatButton.setBackground(Color.green);
                    } 
                    confirmSeats();
                }); 
            }
            seatsPanel.add(seatButton);
        }

        bookingPanel.add(movieInfoPanel);
        bookingPanel.add(legendPanel); 
        bookingPanel.add(seatsPanel);

        

        add(topPanel, BorderLayout.NORTH);
        add(bookingPanel, BorderLayout.CENTER);
        add(selectedSeatsPanel, BorderLayout.SOUTH);

        // listeners for buttons
        homeButton.addActionListener(this);
        backButton.addActionListener(this);
        confirmButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home":
                dispose();
                new CustomerHome(userInfo);
                break;
            case "Back":
                dispose();
                new MovieScreenings(userInfo, movieInfo);
                break;
            case "Confirm":
                System.out.println("Selected seats: " + selectedSeats.toString());

                if (selectedSeats.size() == 0) {
                    JOptionPane.showMessageDialog(null, "Please select at least one seat.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    if (confirmSeatingController.confirmSeats(selectedSeats, selectedScreeningID, hall, userInfo.get(2), movieInfo.get(0), date)) {
                        JOptionPane.showMessageDialog(null, "Seats confirmed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                         
                    } else {
                        JOptionPane.showMessageDialog(null, "Error confirming seats.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                dispose();
                new PurchaseTicket(userInfo, selectedScreeningID, movieInfo, selectedSeats);
                break;
        }
    } 

    // confirm selected seats
    public void confirmSeats() { 
        selectedSeatsPanel.removeAll();

        JLabel selectedSeatsLabel = new JLabel("Selected seats: ");
        JLabel selectedSeatsInfoLabel = new JLabel(selectedSeats.toString());

        selectedSeatsPanel.add(selectedSeatsLabel);
        selectedSeatsPanel.add(selectedSeatsInfoLabel);
        selectedSeatsPanel.add(confirmButton);

        selectedSeatsPanel.revalidate();
        selectedSeatsPanel.repaint(); 
    }
}
