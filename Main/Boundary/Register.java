package Main.Boundary;

import Main.Controller.RegisterController;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.*;


public class Register extends JFrame implements ActionListener {
    private final ArrayList<String> labelNameList = new ArrayList<>(Arrays.asList("First Name:", "Last Name:", "Email:", "Date of Birth:", "Password:"));
    private final ArrayList<JTextField> textfieldList = new ArrayList<>();

    private final JLabel createAccount = new JLabel("Create Account");
    private final JButton createButton = new JButton();

    // Frame's top, middle and bottom row
    private final JPanel topRow = new JPanel();
    private final JPanel botRow = new JPanel();

    // Frame overview
    private final JPanel overviewList = new JPanel(new BorderLayout());

    public Register() {
        // Set up of the frame
        super("Welcome to CSIT 314 Cinema Booking System - REGISTRATION");
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(true);
        setLocationRelativeTo(null);

        // Add the text fields to the textfieldList
        for (int i = 0; i < 5; i++) {
            textfieldList.add(i != 4 ? new JTextField() : new JPasswordField());
        }

        // Put a JLabel called "Create Account" on the top row
        createAccount.setFont(new Font("Serif", Font.PLAIN, 40));
        topRow.add(createAccount);

        // Top row "Create Account"
        overviewList.add(topRow, BorderLayout.NORTH);

        // Middle row contains the labels and text fields
        JPanel middleRow = new JPanel();
        middleRow.setLayout(new BoxLayout(middleRow, BoxLayout.Y_AXIS));

        for (int i = 0; i < 5; i++) {
            // Create a label with the label name E.g "First Name", "Last Name", etc
            JLabel label = new JLabel(labelNameList.get(i));
            label.setFont(new Font("Serif", Font.PLAIN, 25));
            label.setPreferredSize(new Dimension(200, 20));
            label.setHorizontalAlignment(SwingConstants.RIGHT);
            label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30)); // add 10 pixels of padding to the right of the label

            // Create a text field next to the label in the panel
            JTextField textField = textfieldList.get(i);
            textField.setFont(new Font("Serif", Font.PLAIN, 25));
            textField.setPreferredSize(new Dimension(40, 20));
            textField.setColumns(10);

            // Add the label and text field to the panel e.g First Name: [text field]
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(label, BorderLayout.WEST);
            panel.add(textField, BorderLayout.CENTER);
            panel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0)); // add 10 pixels of padding to top and bottom of each panel

            middleRow.add(panel);
        }

        // Middle row
        overviewList.add(middleRow, BorderLayout.CENTER);

        // Set the position of the button
        createButton.setText("Create Account");
        createButton.addActionListener(this);
        botRow.add(createButton);

        // Bottom row contains the button
        overviewList.add(botRow, BorderLayout.SOUTH);

        // Set the preferred size of the overviewList panel
        overviewList.setPreferredSize(new Dimension(800, 500));

        // Add the overviewList to the frame
        add(overviewList);
    }


    /**
     * Action Listener for the Create Account button
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            System.out.println("Create Account button is clicked, Call ENTITY to create account");
            
            RegisterController registerController = new RegisterController();

            ArrayList<String> fieldValueList = new ArrayList<>();
            
            for (JTextField textField : textfieldList) {
                fieldValueList.add(textField.getText());
            }
            
            if (registerController.createUser(fieldValueList, "Customer")) {
                System.out.println("Account created successfully");
            } else {
                System.out.println("Account creation failed");
            }
        }
    }
}