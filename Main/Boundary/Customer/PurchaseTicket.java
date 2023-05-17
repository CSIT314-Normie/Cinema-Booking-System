package Main.Boundary.Customer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class PurchaseTicket extends JFrame implements ActionListener{
    private ArrayList<String> userInfo;

    private final JButton homeButton = new JButton("Home"); 
    private final JButton confirmSeatsButton = new JButton("Confirm Seats");
    private final JButton paymentButton = new JButton("Make Payment");
    
    public PurchaseTicket(ArrayList<String> userInfo, String selectedScreeningID, ArrayList<String> movieInfo, ArrayList<String> selectedSeats) {
        super("CSIT 314 Cinema Booking System - Book movie");
        this.userInfo = userInfo;
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(homeButton); 

        // CONFIRM SEATS 
        JPanel middlePanel = new JPanel(new FlowLayout());
        for (String seatID: selectedSeats) {
            // add ticket type panel
            // add ticket type panel to frame
            JPanel ticketTypePanel = new JPanel(new FlowLayout());
            JLabel seatLabel = new JLabel("Seat " + seatID);


            ticketTypePanel.add(seatLabel);
        }

        add(topPanel, BorderLayout.NORTH);
        add(paymentButton, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
}
