package Main.Boundary.Admin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import Main.Boundary.Home;
import Main.Controller.Admin.CreateAccountController;


public class CreateAccount extends JFrame implements ActionListener {
    private final ArrayList<String> userInfo;

    private final ArrayList<String> labelNameList = new ArrayList<>(Arrays.asList("First Name:", "Last Name:", "Email:", "Date of Birth:", "Password:", "Role"));
    private final ArrayList<JTextField> textfieldList = new ArrayList<>();
    private final JComboBox<String> roleList = new JComboBox<>(new String[]{"User Admin", "Cinema Manager", "Customer", "Cinema Owner"});
    private String role;

    private final JLabel createAccount = new JLabel("Create Account");
    private final JButton createButton = new JButton("Create Account");
    private final JButton backButton = new JButton("Back");

    // Frame's top, middle and bottom row
    private final JPanel topRow = new JPanel();
    private final JPanel botRow = new JPanel();

    // Frame overview
    private final JPanel overviewList = new JPanel(new BorderLayout());

    public CreateAccount(ArrayList<String> userInfo) {
        // Set up of the frame
        super("Welcome to CSIT 314 Cinema Booking System - Registration");
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        this.userInfo = userInfo;

        // Add the text fields to the textfieldList
        for (int i = 0; i < 5; i++) {
            textfieldList.add(i != 4 ? new JTextField() : new JPasswordField());
        }

        // Put a JLabel called "Create Account" on the top row
        createAccount.setFont(new Font("Serif", Font.PLAIN, 25));
        topRow.add(createAccount);

        // Top row "Create Account"
        overviewList.add(topRow, BorderLayout.NORTH);

        // Middle row contains the labels and text fields
        JPanel middleRow = new JPanel();
        middleRow.setLayout(new BoxLayout(middleRow, BoxLayout.Y_AXIS));

        for (int i = 0; i < 6; i++) { 
            JPanel panel = new JPanel(new BorderLayout());

            // Create a label with the label name E.g "First Name", "Last Name", etc
            JLabel label = new JLabel(labelNameList.get(i));
            label.setFont(new Font("Serif", Font.PLAIN, 20));
            label.setPreferredSize(new Dimension(200, 20));
            label.setHorizontalAlignment(SwingConstants.RIGHT);
            label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));

            // Set the role to the last text field
            if (i == 5) { 
                roleList.setFont(new Font("Serif", Font.PLAIN, 20));
                roleList.setPreferredSize(new Dimension(100, 20)); 
                roleList.setSelectedItem("Customer"); // Default role is Customer

                panel.add(label, BorderLayout.WEST);
                panel.add(roleList, BorderLayout.CENTER);

                middleRow.add(panel);
            } else {
                // Create a text field next to the label in the panel
                JTextField textField = textfieldList.get(i);
                textField.setFont(new Font("Serif", Font.PLAIN, 20));
                textField.setPreferredSize(new Dimension(40, 20));
                textField.setColumns(10);

                // Add the label and text field to the panel e.g. First Name: [text field]
                panel.add(label, BorderLayout.WEST);
                panel.add(textField, BorderLayout.CENTER);
                panel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));

                middleRow.add(panel);
                }

        }

        // roleList.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         role = (String) roleList.getSelectedItem();
        //     }
        // });

        // Implement lambda expression for actionlistener
        roleList.addActionListener(e -> role = (String) roleList.getSelectedItem());
        

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
                new Home(userInfo);
                break;

            case "Create Account":
                ArrayList<String> fieldValueList = new ArrayList<>();
                textfieldList.forEach(textField -> fieldValueList.add(textField.getText()));

                if (fieldValueList.contains("")) {
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                CreateAccountController CreateAccountController = new CreateAccountController();

                // validate email address
                if (!CreateAccountController.validateEmail(fieldValueList.get(2))) {
                    JOptionPane.showMessageDialog(null, "Invalid email address", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    // create new user account
                    if (CreateAccountController.createAccount(fieldValueList, role)) {
                        JOptionPane.showMessageDialog(null, "Account created successfully"); 
                    } else {
                        JOptionPane.showMessageDialog(null, "Account failed to create!", "Error", JOptionPane.ERROR_MESSAGE);
                        dispose();
                        new CreateAccount(userInfo);
                    }
                } 
                break;
        }
    } 
    
}
