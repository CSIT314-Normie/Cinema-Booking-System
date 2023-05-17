package Main.Boundary.Customer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import Main.Boundary.Home;
import Main.Controller.Customer.GetTicketTypesController;
import Main.Controller.Customer.PurchaseTicketController;

public class PurchaseTicket extends JFrame implements ActionListener{
    private ArrayList<String> userInfo;
    private ArrayList<String> screeningInfo;
    private ArrayList<String> movieInfo;
    private String date;

    private ArrayList<String[]> ticketTypes = new ArrayList<String[]>();

    private final JButton homeButton = new JButton("Home");  
    private final JButton paymentButton = new JButton("Make Payment");

    private JPanel totalPricePanel = new JPanel(new FlowLayout());

    private JTextField cardNumberTextField = new JTextField(15);
    private JTextField cardNameTextField = new JTextField(15);


    private String[] movieBookingInfo = {"Movie Name: ", "Date: ", "Time: "};
    private String selectedTicketType = "Adult";
    private double totalPrice = 0.0;

    // controllers
    private final GetTicketTypesController getTicketTypesController = new GetTicketTypesController();
    
    public PurchaseTicket(ArrayList<String> userInfo, ArrayList<String> screeningInfo, ArrayList<String> movieInfo, ArrayList<String> selectedSeats, String date) {
        super("CSIT 314 Cinema Booking System - Book movie");
        this.userInfo = userInfo;
        this.screeningInfo = screeningInfo;
        this.movieInfo = movieInfo;
        this.date = date;
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(homeButton); 

        // get ticket types and prices
        ticketTypes = getTicketTypesController.getTicketTypes();

        // display movie booking info and selected seats 
        JPanel middlePanel = new JPanel(new FlowLayout());
        middlePanel.setBorder(BorderFactory.createTitledBorder("Booking Information:"));
        middlePanel.setPreferredSize(new Dimension(700, 200));

        // display movie screening info
        JPanel movieScreeningInfoPanel = new JPanel(new FlowLayout());
        for (int i = 0; i < movieBookingInfo.length; i++) {
            JPanel panel = new JPanel(new FlowLayout()); 
            JLabel label = new JLabel();

            if (i == 0) {
                label.setText(movieBookingInfo[i] + screeningInfo.get(1));
            } else if (i == 1) {
                label.setText(movieBookingInfo[i] + screeningInfo.get(3));
            } else {
                label.setText(movieBookingInfo[i] + screeningInfo.get(5));
            }

            panel.add(label);
            movieScreeningInfoPanel.add(panel);
        } 
         
        middlePanel.add(movieScreeningInfoPanel);

        for (String seatID: selectedSeats) { 
            JPanel panel = new JPanel(new BorderLayout()); 
            JLabel seatLabel = new JLabel("Seat: " + seatID);

            panel.add(seatLabel);
            middlePanel.add(panel);
        }

        // for making payment
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setPreferredSize(new Dimension(700, 400));

        // display ticket types and prices
        JPanel ticketTypesPanel = new JPanel(new FlowLayout());
        ticketTypesPanel.setBorder(BorderFactory.createTitledBorder("Ticket Types:")); 
        ButtonGroup buttonGroup = new ButtonGroup();

        // select ticket type
        for (String[] ticketType: ticketTypes) {
            JPanel panel = new JPanel(new FlowLayout()); 
            JLabel label = new JLabel();
            JRadioButton radioButton = new JRadioButton();

            label.setText(ticketType[0] + " - $" + ticketType[1]);
            radioButton.setActionCommand(ticketType[0]); 

            buttonGroup.add(radioButton);

            panel.add(label);
            panel.add(radioButton);
            ticketTypesPanel.add(panel);

            // action listener for radio button
            radioButton.addActionListener(e -> {
                selectedTicketType = e.getActionCommand();
                totalPrice = Double.parseDouble(ticketType[1]) * selectedSeats.size();
                System.out.println("Selected ticket type: " + selectedTicketType);
                System.out.println("Total price: " + totalPrice);
                displayPrice();
            });
        }

        bottomPanel.add(ticketTypesPanel);

        // Make Payment
        JPanel paymentInfoPanel = new JPanel(new BorderLayout());
        paymentInfoPanel.setBorder(BorderFactory.createTitledBorder("Make Payment:"));
        paymentInfoPanel.setPreferredSize(new Dimension(700, 200));

        // display total price
        displayPrice();
        ticketTypesPanel.add(totalPricePanel);

        // enter card number and name
        JPanel cardNumberPanel = new JPanel(new FlowLayout());
        JLabel cardNumberLabel = new JLabel("Card Number: "); 
        cardNumberTextField.setPreferredSize(new Dimension(200, 20));

        cardNumberPanel.add(cardNumberLabel);
        cardNumberPanel.add(cardNumberTextField);

        JPanel cardNamePanel = new JPanel(new FlowLayout());
        JLabel cardNameLabel = new JLabel("Card Holder's Name: "); 
        cardNameTextField.setPreferredSize(new Dimension(200, 20));

        cardNamePanel.add(cardNameLabel);
        cardNamePanel.add(cardNameTextField);

        paymentInfoPanel.add(cardNumberPanel, BorderLayout.NORTH);
        paymentInfoPanel.add(cardNamePanel, BorderLayout.CENTER);
        paymentInfoPanel.add(paymentButton, BorderLayout.SOUTH);

        bottomPanel.add(paymentInfoPanel); 

        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER); 
        add(bottomPanel, BorderLayout.SOUTH);

        // action listener for buttons
        homeButton.addActionListener(this);
        paymentButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home":
                dispose();
                new Home(userInfo);
                
                break;
            case "Make Payment": 
                // check whether ticket type is selected
                if (selectedTicketType.equals("") || selectedTicketType.equals(null)) {
                    JOptionPane.showMessageDialog(null, "Please select a ticket type.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                }

                // check whether card number is entered
                if (cardNumberTextField.getText().isEmpty() || cardNumberTextField.getText().equals("") || cardNumberTextField.getText().equals(null)) {
                    JOptionPane.showMessageDialog(null, "Please enter card number.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                }

                // check whether card holder name is entered
                if (cardNameTextField.getText().isEmpty() || cardNameTextField.getText().equals("") || cardNameTextField.getText().equals(null)) {
                    JOptionPane.showMessageDialog(null, "Please enter card holder's name.", "Error", JOptionPane.ERROR_MESSAGE); 
                    break;
                }

                // make payment
                PurchaseTicketController purchaseTicketController = new PurchaseTicketController();
                
                if (purchaseTicketController.makePayment(userInfo.get(2), String.valueOf(totalPrice), date)) {
                    JOptionPane.showMessageDialog(null, "Payment successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new Home(userInfo);
                } else {
                    JOptionPane.showMessageDialog(null, "Payment failed.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                // update loyalty points
                

            
                break;
        }
    }
    
    // display total price
    public void displayPrice() {
        totalPricePanel.removeAll();

        JLabel totalPriceLabel = new JLabel("Total Price: $" + totalPrice);
        totalPricePanel.add(totalPriceLabel); 

        totalPricePanel.revalidate();
        totalPricePanel.repaint();
    }
}
