package Main.Boundary.Admin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;


import Main.Boundary.Init;


import Main.Controller.*;
import Main.Controller.Admin.*;


public class AdminHome extends JFrame implements ActionListener {
    private final JPanel panel = new JPanel(new FlowLayout());
    private final JPanel accountsPanel = new JPanel(new BorderLayout());
    
    private JScrollPane scrollPane;

    private final JButton logoutButton = new JButton("Logout");
    private final JButton updateButton = new JButton("Update");
    private final JButton profileButton = new JButton("Profile");
     
    private final JButton editAccountButton = new JButton("Edit Account");
    private final JButton suspendAccountButton = new JButton("Suspend Account");
    private final JButton addAccountButton = new JButton("Create Account");

    private final JLabel userRoleLabel = new JLabel();
    private final ArrayList<String> userInfo;
    private ArrayList<String[]> allAccounts;   
    private DefaultTableModel tableModel;
    private String[] selectedAccount;


    private final transient LoginController loginController;
    private final transient SuspendAccountController suspendAccountController = new SuspendAccountController();
    

    public AdminHome(ArrayList<String> userInfo) {
        super("CSIT 314 Cinema Booking System - Home");
        this.userInfo = userInfo;
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true); // Show the frame

        // Login "SESSION" for user to allow user to logout, can be further implemented
        loginController = new LoginController(userInfo.get(0), userInfo.get(1), userInfo.get(2));
        userRoleLabel.setText("User Role: " + userInfo.get(0) + " | Email: " + userInfo.get(2));
        panel.setPreferredSize(new Dimension(1035, 50));

        // add user role label and buttons to the panel
        panel.add(userRoleLabel);
        panel.add(updateButton);
        panel.add(profileButton);
        panel.add(logoutButton);

        this.allAccounts = loginController.getAllUserAccounts();

        accountsPanel.setPreferredSize(new Dimension(750, 600));
        editAccountButton.setSize(50, 30);


        displayAllAccounts();

        add(panel, BorderLayout.NORTH);
        add(accountsPanel, BorderLayout.CENTER);
        pack();


        editAccountButton.addActionListener(this);
        suspendAccountButton.addActionListener(this);
        addAccountButton.addActionListener(this);
        logoutButton.addActionListener(this);
        updateButton.addActionListener(this);
        profileButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Logout":
                loginController.logout(userInfo.get(0));
                dispose();
                new Init();
                System.out.println("[+] Successfully logged out");
                break;

            case "Update":
                dispose();
                new AdminUpdate(userInfo);
                break;

            case "Profile":
                dispose();
                new AdminProfile(userInfo);
                break;

            case "Edit Account":
                if (selectedAccount == null) {
                    JOptionPane.showMessageDialog(null, "Please select an account to edit", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    dispose();
                    new UpdateAccountInfo(userInfo, selectedAccount);
                }
                break;

            case "Suspend Account":
                if (selectedAccount == null) {
                    JOptionPane.showMessageDialog(null, "Please select an account to delete", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    System.out.println("[+] Admin - Suspend account");
                    if (suspendAccountController.suspendAccount(selectedAccount[2])) {
                        JOptionPane.showMessageDialog(null, "Account suspended successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error suspending account", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    allAccounts = loginController.getAllUserAccounts();
                    displayAllAccounts();
                }
                break;

            case "Create Account":
                dispose();
                new CreateAccount(userInfo);
                break;
        }
    }


    /*
     * Display all user accounts in a table, and allow user admins to "EDIT",
     * "DELETE" and "SUSPEND" accounts - USER ADMIN ONLY
     */
    private void displayAllAccounts() {
        allAccounts.clear();
        allAccounts = loginController.getAllUserAccounts();

        // Remove all components from the panel
        accountsPanel.removeAll();

        JPanel buttonPane = new JPanel();
        buttonPane.add(editAccountButton);
        buttonPane.add(suspendAccountButton);
        buttonPane.add(addAccountButton);

        accountsPanel.add(buttonPane, BorderLayout.CENTER);

        String[] columns = { "First Name", "Last Name", "Email", "Date of Birth", "Role", "activeStatus" };
        tableModel = new DefaultTableModel(columns, 0);

        for (String[] row : allAccounts) {
            tableModel.addRow(row);
        }

        JTable allUserTable = new JTable(tableModel);

        // Add the JTable to a JScrollPane
        scrollPane = new JScrollPane(allUserTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(750, 400));

        accountsPanel.add(scrollPane, BorderLayout.NORTH);

        // add listener for each row in the table
        ListSelectionModel selectionModel = allUserTable.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = allUserTable.getSelectedRow();
                // Perform action on the selected row (account) here
                // add account data to selectedAccount array
                selectedAccount = new String[] { 
                    tableModel.getValueAt(selectedRow, 0).toString(),
                    tableModel.getValueAt(selectedRow, 1).toString(),
                    tableModel.getValueAt(selectedRow, 2).toString(),
                    tableModel.getValueAt(selectedRow, 3).toString(),
                    tableModel.getValueAt(selectedRow, 4).toString()
                };
            }
        });

        // refresh the panel
        accountsPanel.revalidate();
        accountsPanel.repaint();
    }
}
