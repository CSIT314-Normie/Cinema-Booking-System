package Main.Boundary.Customer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import Main.Controller.Customer.*;
import Main.Boundary.*;

public class Register extends JFrame implements ActionListener {
    private final ArrayList<String> labelNameList = new ArrayList<>(Arrays.asList("First Name:", "Last Name:", "Email:", "Date of Birth:", "Password:"));
    private final ArrayList<JTextField> textfieldList = new ArrayList<>();

    private final JLabel createAccount = new JLabel("Create Account");
    private final JButton createButton = new JButton("Create Account");
    private final JButton backButton = new JButton("Back");

    // Frame's top, middle and bottom row
    private final JPanel topRow = new JPanel();
    private final JPanel botRow = new JPanel();

    // Frame overview
    private final JPanel overviewList = new JPanel(new BorderLayout());

    public Register() {
        // Set up of the frame
        super("Welcome to CSIT 314 Cinema Booking System - Registration");
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
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
            label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));

            // Create a text field next to the label in the panel
            JTextField textField = textfieldList.get(i);
            textField.setFont(new Font("Serif", Font.PLAIN, 25));
            textField.setPreferredSize(new Dimension(40, 20));
            textField.setColumns(10);

            // Add the label and text field to the panel e.g First Name: [text field]
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(label, BorderLayout.WEST);
            panel.add(textField, BorderLayout.CENTER);
            panel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));

            middleRow.add(panel);
        }

        // Middle row
        overviewList.add(middleRow, BorderLayout.CENTER);

        // Add actionlistener to create button
        backButton.addActionListener(this);
        createButton.addActionListener(this);

        botRow.add(backButton);
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
     * 
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Back":
                dispose();
                new Init();
                break;

            case "Create Account":
                ArrayList<String> fieldValueList = new ArrayList<>();
                textfieldList.forEach(textField -> fieldValueList.add(textField.getText()));

                if (fieldValueList.contains("")) {
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields");
                    return;
                }

                RegisterAccountController registerController = new RegisterAccountController(fieldValueList, "Customer");

                if (registerController.createUser(fieldValueList, "Customer")) {
                    JOptionPane.showMessageDialog(null, "Account created successfully");
                    dispose();
                    new Login();
                } else {
                    JOptionPane.showMessageDialog(null, "Account failed to create!");
                    dispose();
                    new Init();
                }
                break;
        }
    }
}