package Main.Boundary;


import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

import Main.Controller.UpdateController;

import java.util.*;


public class Update extends JFrame implements ActionListener {
    private final ArrayList<String> labelNameList = new ArrayList<>(Arrays.asList("First Name:", "Last Name:", "Email:", "Date of Birth:", "Password:"));
    private final ArrayList<JTextField> textfieldList = new ArrayList<>();

    private final JLabel updateAccount = new JLabel("Update Account");
    private final JButton updateButton = new JButton();
    private final JButton homeButton = new JButton("Home");

    // Frame's top, middle and bottom row
    private final JPanel topRow = new JPanel();
    private final JPanel botRow = new JPanel();

    // Frame overview
    private final JPanel overviewList = new JPanel(new BorderLayout());
    
    private ArrayList<String> userInfo;
    private String email;

    public Update(ArrayList<String> userInfo) {
        super("Welcome to CSIT 314 Cinema Booking System - Update Account");
        this.userInfo = userInfo;   
        this.email = userInfo.get(4);
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);


        // Add the text fields to the textfieldList
        for (int i = 0; i < 5; i++) {
            textfieldList.add(i != 4 ? new JTextField(userInfo.get(i + 2)) : new JPasswordField());
        }

        // Put a JLabel called "Create Account" on the top row
        updateAccount.setFont(new Font("Serif", Font.PLAIN, 40));
        topRow.add(updateAccount);

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
            textField.setPreferredSize(new Dimension(40, 30));
            textField.setColumns(10);

            // Add the label and text field to the panel e.g First Name: [text field]
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(label, BorderLayout.WEST);
            panel.add(textField, BorderLayout.CENTER);
            panel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));

            middleRow.add(panel);
        }

        // Add the middle row to the overview
        overviewList.add(middleRow, BorderLayout.CENTER);

        updateButton.setText("Update");
        updateButton.addActionListener(this);

        homeButton.setBounds(500, 400, 100, 50);
        homeButton.addActionListener(this);

        botRow.add(updateButton);
        botRow.add(homeButton);

        // Add the bottom row to the overview
        overviewList.add(botRow, BorderLayout.SOUTH);
    
        // Add the overview to the frame
        add(overviewList);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {          
            case "Update":
                ArrayList<String> updatedUserInfo = new ArrayList<>();
                textfieldList.forEach(textField -> updatedUserInfo.add(textField.getText()));

                // Check if the user has entered any empty fields
                if (updatedUserInfo.contains("")) {
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields");
                    break;
                }

                UpdateController updateController = new UpdateController();

                if (updateController.updateAccount(updatedUserInfo, this.email)) {
                    JOptionPane.showMessageDialog(null, "Account updated successfully");
                    dispose();
                    new Home(userInfo);
                } else {
                    JOptionPane.showMessageDialog(null, "Account update failed");
                }

                break;

            case "Home":
                System.out.println("[+] Move to Home page");
                dispose();
                new Home(userInfo);
                break;
        }
    }   
}
