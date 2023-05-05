package Main.Boundary;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.html.StyleSheet.BoxPainter;

import Main.Controller.UpdateAccountInfoController;

public class UpdateAccountInfo extends JFrame implements ActionListener, MouseListener {
    private ArrayList<String> userInfo;

    private JPanel topPanel; 
    private final JButton logoutButton = new JButton("Logout"); 
    private final JButton homeButton = new JButton("Admin Home");
    private String[] labelList = {"First Name: ", "Last Name: ", "Email: ", "DOB", "role: "};
    private String[] roles = {"User Admin", "Cinema Manager"};
    private ArrayList<JTextField> fieldList = new ArrayList<JTextField>(4);
    private JComboBox<String> roleList = new JComboBox<String>(roles);
    private ArrayList<String> modifiedAcc;
    private String userEmail;
    private String selectedRole;

    private UpdateAccountInfoController updateAccountInfoController = new UpdateAccountInfoController();

    public UpdateAccountInfo(ArrayList<String> userInfo, String[] accountInfo){
        super("Admin - Edit User Account Info");
        this.userInfo = userInfo;
        setLayout(new FlowLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null); 
        setVisible(true); // Show the frame

        // add user role lable and buttons to the panel
        topPanel = new JPanel(new FlowLayout()); 

        // add user role lable and buttons to the panel 
        topPanel.add(homeButton);
        topPanel.add(logoutButton); 

        System.out.println("[+] Edit Account Info - " + accountInfo[0] + " | " + accountInfo[1] + " | " + accountInfo[2]);
        this.userEmail = accountInfo[2];

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        roleList.setPreferredSize(new Dimension(100, 30));

        for(int i = 0; i < labelList.length; i++){
            JPanel panel = new JPanel(new BorderLayout());
            JLabel label = new JLabel(labelList[i]);
            JTextField field = new JTextField(accountInfo[i], 20);
            fieldList.add(field);
            field.setText(accountInfo[i]);

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

        roleList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected item from the combo box
                selectedRole = (String) roleList.getSelectedItem();
                System.out.println("[+] Selected role: " + selectedRole); 
            }
        });

        JButton updateButton = new JButton("Update");
        formPanel.add(updateButton);

        add(topPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.SOUTH);

        homeButton.addActionListener(this);
        logoutButton.addActionListener(this);
        updateButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e){

        switch (e.getActionCommand()) {
            case "Update":
                modifiedAcc = new ArrayList<String>();
                for(int i = 0; i < fieldList.size(); i++){
                    if (i == 4) { // drop down menu for role
                        modifiedAcc.add(selectedRole);
                        System.out.println("[+] Modified Account Info user role: " + modifiedAcc.get(i));
                    } else {
                        modifiedAcc.add(fieldList.get(i).getText());
                    }
                }
                System.out.println("[+] Modified Account Info: " + modifiedAcc);
                if(updateAccountInfoController.updateAccountInfo(modifiedAcc, userEmail)){
                    System.out.println("[+] Account Info Updated Successfully!");
                    JOptionPane.showMessageDialog(null, "User account information successfully updated!", "Update Success", JOptionPane.INFORMATION_MESSAGE);
                    // dispose();
                    // new Home(userInfo);
                } else {
                    System.out.println("[-] Account Info Update Failed!");
                    JOptionPane.showMessageDialog(null, "User account information Update unsuccessful", "Update Failed", JOptionPane.ERROR_MESSAGE);
                }
                modifiedAcc.clear();
                break;
            case "Admin Home":
                dispose();
                new Home(userInfo);
                break;
        } 
    }

    @Override
    public void mouseClicked(MouseEvent e) { 
    }

    @Override
    public void mousePressed(MouseEvent e) { 
    }

    @Override
    public void mouseReleased(MouseEvent e) { 
    }

    @Override
    public void mouseEntered(MouseEvent e) { 
    }

    @Override
    public void mouseExited(MouseEvent e) { 
    }
}

