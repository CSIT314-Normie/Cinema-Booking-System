package Main.Boundary.Customer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;

import Main.Controller.Customer.*;

public class PurchaseTicket extends JFrame implements ActionListener{
    private ArrayList<String> userInfo;
    private ArrayList<String> screeningInfo;
    private ArrayList<String> movieInfo;
    private ArrayList<String> selectedSeats;
    private String date; // screening date
    private String bookingDate; // booking date - present date for payments
    private int loyaltyPts;
    private String cinemaName;

    private ArrayList<String[]> ticketTypes = new ArrayList<>();

    private final JButton homeButton = new JButton("Home");  
    private final JButton paymentButton = new JButton("Make Payment");
    private final JButton redeemButton = new JButton("Redeem loyalty points - 10 points for 1 ticket");

    private JPanel totalPricePanel = new JPanel(new FlowLayout());
    private JPanel loyaltyPointsPanel = new JPanel(new FlowLayout());

    private JTextField cardNumberTextField = new JTextField(15);
    private JTextField cardNameTextField = new JTextField(15);

    private String[] movieBookingInfo = {"Movie Name: ", "Date: ", "Time: ", "Cinema: "};
    private String selectedTicketType = "";
    private int noOfTicketsToPay;
    private double totalPrice = 0.0;
    private double priceForOneTicket = 0.0;

    // controllers
    private final transient GetTicketTypesController getTicketTypesController = new GetTicketTypesController();
    private final transient LoyaltyPointController loyaltyPointController = new LoyaltyPointController(); 
    
    public PurchaseTicket(ArrayList<String> userInfo, ArrayList<String> screeningInfo, ArrayList<String> movieInfo, ArrayList<String> selectedSeats, String date, String cinemaName) {
        super("Book Movie - Purchase Tickets");
        this.userInfo = userInfo;
        this.screeningInfo = screeningInfo;
        this.movieInfo = movieInfo;
        this.selectedSeats = selectedSeats;
        this.date = date;
        this.noOfTicketsToPay = selectedSeats.size();
        this.cinemaName = cinemaName;
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);  

        // initialise booking date
        Date currentDate = new Date();

        String pattern = "dd/MM/yyyy"; // Specify the desired format pattern
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        bookingDate = dateFormat.format(currentDate);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setPreferredSize(new Dimension(700, 50));
        topPanel.add(homeButton); 

        // get loyalty points
        loyaltyPts = Integer.parseInt(loyaltyPointController.getLoyaltyPoint(userInfo.get(2)));

        // get ticket types and prices
        ticketTypes = getTicketTypesController.getTicketTypes();

        // display movie booking info and selected seats 
        JPanel middlePanel = new JPanel(new FlowLayout());
        middlePanel.setPreferredSize(new Dimension(700, 240));
        middlePanel.setBorder(BorderFactory.createTitledBorder("Booking Information:")); 

        // display movie screening info - middle panel
        for (int i = 0; i < movieBookingInfo.length; i++) {
            JPanel panel = new JPanel(new FlowLayout()); 
            panel.setPreferredSize(new Dimension(700, 40));
            JLabel label = new JLabel();

            if (i == 0) {
                label.setText(movieBookingInfo[i] + screeningInfo.get(1));
            } else if (i == 1) {
                label.setText(movieBookingInfo[i] + screeningInfo.get(3));
            } else if (i == 2) {
                label.setText(movieBookingInfo[i] + screeningInfo.get(4));
            } else if (i == 3) {
                label.setText(movieBookingInfo[i] + cinemaName);
            }

            panel.add(label);
            middlePanel.add(panel);
        }  

        // display selected seats - middle panel
        for (int i = 0; i < selectedSeats.size(); i++) { 
            String seatID = selectedSeats.get(i);
            JPanel panel = new JPanel(new BorderLayout()); 
            JLabel seatLabel = new JLabel("Seat: " + seatID);

            panel.add(seatLabel);
            middlePanel.add(panel);
        }

        // bottom panel - select ticket types and making payment
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setPreferredSize(new Dimension(700, 300));

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
                priceForOneTicket = Double.parseDouble(ticketType[1]);
                totalPrice = Double.parseDouble(ticketType[1]) * noOfTicketsToPay;
                displayPrice();
            });
        }

        // show current loyalty points
        displayLoyaltyPoints();

        bottomPanel.add(ticketTypesPanel);
        bottomPanel.add(loyaltyPointsPanel);

        // display redeem button to redeem loyalty points - ONLY IF USER HAS ENOUGH LOYALTY POINTS
        if (loyaltyPts / 10 > 0) {
            JPanel redeemPanel = new JPanel(new FlowLayout());
            redeemPanel.setPreferredSize(new Dimension(700, 50));
            redeemPanel.add(redeemButton);
            bottomPanel.add(redeemPanel);
        }

        // Make Payment
        JPanel paymentInfoPanel = new JPanel(new FlowLayout());
        paymentInfoPanel.setBorder(BorderFactory.createTitledBorder("Make Payment:"));
        paymentInfoPanel.setPreferredSize(new Dimension(700, 200));

        // display total price
        displayPrice();
        ticketTypesPanel.add(totalPricePanel);

        // enter card number and name
        JPanel cardNumberPanel = new JPanel(new FlowLayout());
        JLabel cardNumberLabel = new JLabel("Card Number: "); 
        cardNumberTextField.setPreferredSize(new Dimension(600, 20));

        cardNumberPanel.add(cardNumberLabel);
        cardNumberPanel.add(cardNumberTextField);

        JPanel cardNamePanel = new JPanel(new FlowLayout());
        JLabel cardNameLabel = new JLabel("Card Holder's Name: "); 
        cardNameTextField.setPreferredSize(new Dimension(600, 20));

        cardNamePanel.add(cardNameLabel);
        cardNamePanel.add(cardNameTextField);

        // payment button panel
        JPanel paymentButtonPanel = new JPanel(new FlowLayout());
        paymentButtonPanel.setPreferredSize(new Dimension(600, 50));
        paymentButtonPanel.add(paymentButton);

        paymentInfoPanel.add(cardNumberPanel);
        paymentInfoPanel.add(cardNamePanel);
        paymentInfoPanel.add(paymentButtonPanel);

        bottomPanel.add(paymentInfoPanel); 

        add(topPanel);
        add(middlePanel); 
        add(bottomPanel);

        // action listener for buttons
        homeButton.addActionListener(this);
        paymentButton.addActionListener(this);
        redeemButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home":
                dispose();
                new CustomerHome(userInfo);
                
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

                // check whether cardholder name is entered
                if (cardNameTextField.getText().isEmpty() || cardNameTextField.getText().equals("") || cardNameTextField.getText().equals(null)) {
                    JOptionPane.showMessageDialog(null, "Please enter card holder's name.", "Error", JOptionPane.ERROR_MESSAGE); 
                    break;
                }

                // make payment, confirm seats booked AND send confirmation email
                PurchaseTicketController purchaseTicketController = new PurchaseTicketController();
                ConfirmSeatingController confirmSeatingController = new ConfirmSeatingController();
                ConfirmationEmailController confirmationEmailController = new ConfirmationEmailController();

                String[] bookingInfo = {screeningInfo.get(4), cinemaName};
                
                if (purchaseTicketController.makePayment(userInfo.get(2), String.valueOf(totalPrice), bookingDate) && confirmSeatingController.confirmSeats(selectedSeats, screeningInfo.get(0), screeningInfo.get(2), userInfo.get(2), movieInfo.get(0), date) && confirmationEmailController.confirmationEmail(userInfo.get(2), movieInfo.get(0), date, selectedSeats, bookingInfo, String.valueOf(totalPrice))) {
                    JOptionPane.showMessageDialog(null, "Payment successful. Seats booked.", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // AND update loyalty points
                    UpdateLoyaltyPointsController updateLoyaltyPointsController = new UpdateLoyaltyPointsController();

                    if (updateLoyaltyPointsController.updateLoyaltyPoints(userInfo.get(2))) {
                        JOptionPane.showMessageDialog(null, "Loyalty points updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new CustomerHome(userInfo);
                    } else {
                        JOptionPane.showMessageDialog(null, "Loyalty points not updated.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    
                } else {
                    JOptionPane.showMessageDialog(null, "Payment failed.", "Error", JOptionPane.ERROR_MESSAGE);
                } 
                break;
            
            case "Redeem loyalty points - 10 points for 1 ticket":
                RedeemLoyaltyPointsController redeemLoyaltyPointsController = new RedeemLoyaltyPointsController();

                // check current loyalty points
                // check no. of tickets 
                int redeemedTickets = 0;
                int redeemableTickets = loyaltyPts / 10; 
                int left = redeemableTickets - noOfTicketsToPay;

                if (left < 0) {
                    redeemedTickets = redeemableTickets;
                } else {
                    redeemedTickets = redeemableTickets - left;
                } 

                noOfTicketsToPay -= redeemedTickets;
 
                if (redeemLoyaltyPointsController.redeemLoyaltyPoints(userInfo.get(2), String.valueOf(redeemedTickets * 10))) {
                    JOptionPane.showMessageDialog(null, "Loyalty points redeemed.", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // disable redeem button
                    redeemButton.setEnabled(false);

                    // update total price payable
                    totalPrice = noOfTicketsToPay * priceForOneTicket; 
                    displayLoyaltyPoints();
                    displayPrice();
                } else {
                    JOptionPane.showMessageDialog(null, "Loyalty points not redeemed.", "Error", JOptionPane.ERROR_MESSAGE);
                } 
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

    // display loyalty points
    public void displayLoyaltyPoints() {
        loyaltyPointsPanel.removeAll(); 

        // get loyalty points
        loyaltyPts = Integer.parseInt(loyaltyPointController.getLoyaltyPoint(userInfo.get(2)));

        JLabel loyaltyPointsLabel = new JLabel("Current loyalty points: " + loyaltyPts);
        loyaltyPointsPanel.add(loyaltyPointsLabel); 

        loyaltyPointsPanel.revalidate();
        loyaltyPointsPanel.repaint();
    }
}
