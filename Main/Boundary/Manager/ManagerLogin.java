package Main.Boundary.Manager;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import Main.Boundary.Init;

import Main.Controller.Manager.*;


public class ManagerLogin extends JFrame implements ActionListener {
    private final JLabel welcomeLabel = new JLabel("Welcome MANAGER");
    private final ArrayList<String> labelList = new ArrayList<>(Arrays.asList("Email:", "Password:"));
    private final ArrayList<JTextField> textfieldList = new ArrayList<>();

    private final JButton createButton = new JButton();
    private final JButton loginButton = new JButton();

    private final JPanel topRow = new JPanel();
    private final JPanel middleRow = new JPanel();
    private final JPanel botRow = new JPanel();

    // Frame overview
    private final JPanel overviewList = new JPanel(new BorderLayout());

    public ManagerLogin() {
        super("Welcome MANAGER");
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        // Top row "Welcome to CSIT 314 Cinema Booking System"
        welcomeLabel.setFont(new Font("Serif", Font.PLAIN, 40));
        topRow.add(welcomeLabel);
        topRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        overviewList.add(topRow, BorderLayout.NORTH);

        // Middle row contains the labels and text fields
        middleRow.setLayout(new BoxLayout(middleRow, BoxLayout.Y_AXIS));

        labelList.forEach(labelName -> {
            JLabel label = new JLabel(labelName);
            JComponent field = labelName.equals("Password:") ? new JPasswordField() : new JTextField();
            JPanel row = new JPanel();

            label.setFont(new Font("Serif", Font.PLAIN, 25));
            label.setPreferredSize(new Dimension(200, 20));
            label.setHorizontalAlignment(SwingConstants.RIGHT);
            label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30)); // add 10 pixels of padding to the right of the label

            field.setPreferredSize(new Dimension(200, 20));
            textfieldList.add((JTextField) field);

            row.add(label, BorderLayout.WEST);
            row.add(field, BorderLayout.CENTER);
            row.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

            middleRow.add(row);
        });

        JPanel buttonRow = new JPanel(new GridLayout(2, 1, 0, 10));
        buttonRow.setPreferredSize(new Dimension(200, 60));

        loginButton.setText("Login");
        loginButton.setPreferredSize(new Dimension(200, 20));
        loginButton.addActionListener(this);
        buttonRow.add(loginButton);

        createButton.setText("Back");
        createButton.setPreferredSize(new Dimension(200, 20));
        createButton.addActionListener(this);
        buttonRow.add(createButton);
        
        botRow.add(buttonRow);

        // put middle row and bot row in overview list
        overviewList.add(middleRow, BorderLayout.CENTER);
        overviewList.add(botRow, BorderLayout.SOUTH);

        // add overview list to frame
        add(overviewList);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            ManagerLoginController loginController = new ManagerLoginController(textfieldList.get(0).getText(), textfieldList.get(1).getText());
            ArrayList<String> loginResult = loginController.login();

            
            if (loginResult.get(1).equals("T")) {
                dispose();
                new ManagerHome(loginResult);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid email or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } else if (e.getSource() == createButton) {
            dispose();
            new Init();
        }
    }
}
