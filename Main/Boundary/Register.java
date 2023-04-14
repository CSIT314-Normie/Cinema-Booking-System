package Main.Boundary;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class Register extends JFrame implements ActionListener {
    private final ArrayList<JLabel> labelsList = new ArrayList<>();
    private final ArrayList<String> labelNameList = new ArrayList<>(Arrays.asList("First Name", "Last Name", "Email", "Date of Birth", "Password"));
    
    private final JLabel createAccount = new JLabel("Create Account");
    private final JTextField fname = new JTextField();    
    private final JTextField lname = new JTextField(); 
    private final JTextField email = new JTextField();
    private final JTextField dob = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton createButton = new JButton();
    private final JPanel topRow = new JPanel();
    private final JPanel midRow = new JPanel();
    private final JPanel botRow = new JPanel();

    public Register() {
        // Set up of the frame
        super("Welcome to CSIT 314 Cinema Booking System - REGISTRATION");
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(true);
        setLocationRelativeTo(null); 

        // put a text called "Create Account" at the top and  middle of the screen
        createAccount.setBounds(500, 50, 300, 50);
        createAccount.setFont(new Font("Serif", Font.PLAIN, 40));
        topRow.add(createAccount);
        
        labelNameList.forEach(name -> {
            labelsList.add(new JLabel(name));
            JLabel label = labelsList.get(labelsList.size() - 1);
            label.setFont(new Font("Serif", Font.PLAIN, 30));
            midRow.add(label);
        });
        
    
        JPanel outer = new JPanel(new BorderLayout());
        outer.add(topRow, BorderLayout.NORTH);
        outer.add(midRow, BorderLayout.CENTER);

        add(outer);
        
        // // set the position of the text field
        // fname.setBounds(600, 150, 200, 50);
        // lname.setBounds(600, 200, 200, 50);
        // email.setBounds(600, 250, 200, 50);
        // dob.setBounds(600, 300, 200, 50);
        // passwordField.setBounds(600, 350, 200, 50);
          
        
        // // set the position of the button
        // createButton.setBounds(500, 400, 300, 50);
        // createButton.setText("Create Account");
        // createButton.addActionListener(this);

        
        // // add(fname);
        // // add(lname);
        // // add(email);
        // // add(dob);
        // // add(passwordField);
        // add(createButton);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            System.out.println("Create Account button is clicked");
        }
    }
}
