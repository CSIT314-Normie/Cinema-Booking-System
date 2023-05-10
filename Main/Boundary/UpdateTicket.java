package Main.Boundary;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel; 
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Main.Controller.TicketingArrangementController;

public class UpdateTicket extends JFrame implements ActionListener{
    private final ArrayList<String> labelNameList = new ArrayList<>(Arrays.asList("Ticket Type:", "Price"));
    //private final ArrayList<JTextField> textfieldList = new ArrayList<>();

    private final JLabel updateTicketPrice = new JLabel("Update Ticket Price");
    private final JButton updateButton = new JButton("Update");
    private final JButton homeButton = new JButton("Home");
    private final JButton backButton = new JButton("Back");

    // Frame's top, middle and bottom row
    private final JPanel topRow = new JPanel();
    private final JPanel botRow = new JPanel();

    // Frame overview
    private final JPanel overviewList = new JPanel(new BorderLayout());

    private final ArrayList<String> userInfo;

    JTextField textField;
    JComboBox<String> dropdown;

    public UpdateTicket(ArrayList<String> userInfo) {
        super("Welcome to CSIT 314 Cinema Booking System - Update Ticket Price");
        this.userInfo = userInfo;   
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

         // Add the text fields to the textfieldList
         /*for (int i = 0; i < 1; i++) {
            textfieldList.add(new JTextField());
         }*/

         // Put a JLabel called "Update Ticket Price" on the top row
         updateTicketPrice.setFont(new Font("Serif", Font.BOLD, 30));
         topRow.add(updateTicketPrice);
 
         // Top row "Update Account"
         overviewList.add(topRow, BorderLayout.NORTH);

         // Middle row contains the labels and text fields
        JPanel middleRow = new JPanel();
        middleRow.setLayout(new BoxLayout(middleRow, BoxLayout.Y_AXIS));

        //-- For Ticket Type label and dropdownList --
        // Create a label with the label name E.g "First Name", "Last Name", etc
        JLabel ticketTypelabel = new JLabel(labelNameList.get(0));
        ticketTypelabel.setFont(new Font("Serif", Font.PLAIN, 25));
        ticketTypelabel.setPreferredSize(new Dimension(200, 20));
        ticketTypelabel.setHorizontalAlignment(SwingConstants.RIGHT);
        ticketTypelabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));

        //Create dropdown list for ticket type
        String[] options = {"Student", "Adult", "Senior"};
        dropdown = new JComboBox<>(options);
        dropdown.setFont(new Font("Serif", Font.PLAIN, 25));
        dropdown.setPreferredSize(new Dimension(40, 30));
        dropdown.setSelectedIndex(0);

         // Add the label and dropdown list to the panel e.g. First Name: [text field]
         JPanel panelOne = new JPanel(new BorderLayout());
         panelOne.add(ticketTypelabel, BorderLayout.WEST);
         panelOne.add(dropdown, BorderLayout.CENTER);
         panelOne.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
 
         middleRow.add(panelOne);

        //-- For Price label and textfield --
        // Create a label with the label name E.g "First Name", "Last Name", etc
        JLabel priceLabel = new JLabel(labelNameList.get(1));
        priceLabel.setFont(new Font("Serif", Font.PLAIN, 25));
        priceLabel.setPreferredSize(new Dimension(200, 20));
        priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        priceLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));

        // Create a text field next to the label in the panel
        textField = new JTextField();
        textField.setFont(new Font("Serif", Font.PLAIN, 25));
        textField.setPreferredSize(new Dimension(40, 30));
        textField.setColumns(10);

        // Add the label and text field to the panel e.g. First Name: [text field]
        JPanel panelTwo = new JPanel(new BorderLayout());
        panelTwo.add(priceLabel, BorderLayout.WEST);
        panelTwo.add(textField, BorderLayout.CENTER);
        panelTwo.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));

        middleRow.add(panelTwo);

        // Add the middle row to the overview
        overviewList.add(middleRow, BorderLayout.CENTER);

        updateButton.addActionListener(this);
        homeButton.setBounds(500, 400, 100, 50);
        homeButton.addActionListener(this);
        backButton.setBounds(500, 400, 100, 50);
        backButton.addActionListener(this);

        botRow.add(updateButton);
        botRow.add(homeButton);
        botRow.add(backButton);

        // Add the bottom row to the overview
        overviewList.add(botRow, BorderLayout.SOUTH);
    
        // Add the overview to the frame
        add(overviewList);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String price = textField.getText();
        String ticketType = (String) dropdown.getSelectedItem();
        switch (e.getActionCommand()){
            case "Update":
            TicketingArrangementController updateTicketController = new TicketingArrangementController();
                if(price.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields");
                    break;
                } 

                if(updateTicketController.updateTicketPrice(userInfo.get(0), ticketType, price)){
                    JOptionPane.showMessageDialog(null, "Ticket Price updated successfully");
                    dispose();
                    new TicketingArrangement(userInfo);
                }else{
                    JOptionPane.showMessageDialog(null, "Ticket Price update failed");
                }
                break;
            
            case "Home":
                dispose();
                new Home(userInfo);
                break;
            case "Back":
                dispose();
                new TicketingArrangement(userInfo);
                break;
        }
            
    }
    
}
