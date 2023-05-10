package Main.Boundary.Admin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


import Main.Controller.UpdateController;
import Main.Boundary.*;


public class UpdateAccountInfo extends JFrame implements ActionListener, MouseListener {
    private final ArrayList<String> userInfo;

    private final JPanel topPanel;
    private final JButton logoutButton = new JButton("Logout");
    private final JButton homeButton = new JButton("Admin Home");
    private final String[] labelList = {"First Name: ", "Last Name: ", "Email: ", "DOB", "role: "};
    private final String[] roles = {"User Admin", "Cinema Manager"};
    private final ArrayList<JTextField> fieldList = new ArrayList<>(4);
    private final JComboBox<String> roleList = new JComboBox<>(roles);
    private ArrayList<String> modifiedAcc;
    private final String userEmail;
    private String selectedRole;

    private final transient UpdateController updateController = new UpdateController();

    public UpdateAccountInfo(ArrayList<String> userInfo, String[] accountInfo){
        super("User Admin - Update User Account Info");
        this.userInfo = userInfo;
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null); 
        setVisible(true); // Show the frame

        // add a top panel to contain the home and logout buttons
        topPanel = new JPanel(new FlowLayout());
        topPanel.setPreferredSize(new Dimension(1035, 50));

        // add user role label and buttons to the panel
        topPanel.add(homeButton);
        topPanel.add(logoutButton);

        System.out.println("[+] Edit Account Info - " + accountInfo[0] + " | " + accountInfo[1] + " | " + accountInfo[2]);
        this.userEmail = accountInfo[2];
        selectedRole = accountInfo[4];

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Editing user "+ accountInfo[0] + " " + accountInfo[1] +"'s Account Info");
        title.setFont(new Font("Serif", Font.PLAIN, 25));

        formPanel.add(title);
        
        roleList.setPreferredSize(new Dimension(200, 30));

        for(int i = 0; i < labelList.length; i++){
            JPanel panel = new JPanel(new BorderLayout());
            JLabel label = new JLabel(labelList[i]);
            JTextField field = new JTextField(accountInfo[i], 20);

            label.setFont(new Font("Serif", Font.PLAIN, 18));
            field.setPreferredSize(new Dimension(200, 30));
            field.setText(accountInfo[i]);

            fieldList.add(field);

            if (i == 4) { // drop down menu for role
                roleList.setSelectedIndex(accountInfo[i].equals("User Admin") ? 0 : 1);
                panel.add(label, BorderLayout.WEST);
                panel.add(roleList, BorderLayout.EAST);
                panel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
            } else {
                // add label and field to the form panel
                panel.add(label, BorderLayout.WEST);
                panel.add(field, BorderLayout.EAST);
                panel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
            } 
            
            formPanel.add(panel); 
        }

        roleList.addActionListener(e -> {
            // Get the selected item from the combo box
            selectedRole = (String) roleList.getSelectedItem();
            System.out.println("[+] Selected role: " + selectedRole);
        });

        JButton updateButton = new JButton("Update");
        formPanel.add(updateButton);

        add(topPanel);
        add(formPanel);

        homeButton.addActionListener(this);
        logoutButton.addActionListener(this);
        updateButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e){

        switch (e.getActionCommand()) {
            case "Update":
                modifiedAcc = new ArrayList<>();
                for(int i = 0; i < fieldList.size(); i++){
                    if (i == 4) { // drop down menu for role
                        modifiedAcc.add(selectedRole);
                        System.out.println("[+] Modified Account Info user role: " + modifiedAcc.get(i));
                    } else {
                        modifiedAcc.add(fieldList.get(i).getText());
                    }
                }
                System.out.println("[+] Modified Account Info: " + modifiedAcc);
                if(updateController.updateAccountInfo(userInfo.get(0), modifiedAcc, userEmail)){
                    System.out.println("[+] Account Info Updated Successfully!");
                    JOptionPane.showMessageDialog(null, "User account information successfully updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // dispose();
                    // new Home(userInfo);
                } else {
                    System.out.println("[-] Account Info Update Failed!");
                    JOptionPane.showMessageDialog(null, "User account information Update unsuccessful", "Error", JOptionPane.ERROR_MESSAGE);
                }
                modifiedAcc.clear();
                break;
            case "Admin Home":
                dispose();
                new Home(userInfo);
                break;
            case "Logout":
                dispose();
                new Login();
                break;
        } 
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}

