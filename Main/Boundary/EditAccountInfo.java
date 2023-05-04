package Main.Boundary;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList; 

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField; 

public class EditAccountInfo extends JFrame implements ActionListener, MouseListener {
    private ArrayList<String> userInfo;

    private JPanel panel;
    private JPanel formPanel = new JPanel(new FlowLayout());
    private final JButton logoutButton = new JButton("Logout"); 
    private final JButton homeButton = new JButton("Admin Home");
    private String[] labelList = {"First Name: ", "Last Name: ", "Email: ", "Password: ", "User Role: "};
    private ArrayList<JTextField> fieldList = new ArrayList<JTextField>(5);

    public EditAccountInfo(ArrayList<String> userInfo, ArrayList<String> accountInfo){
        super("Admin - Edit User Account Info");
        this.userInfo = userInfo;
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null); 
        setVisible(true); // Show the frame

        // add user role lable and buttons to the panel
        panel = new JPanel(new FlowLayout());
        panel.setPreferredSize(new Dimension(1035, 50));

        // add user role lable and buttons to the panel 
        panel.add(homeButton);
        panel.add(logoutButton); 

        System.out.println("[+] Edit Account Info - " + userInfo.get(0) + " | " + userInfo.get(1) + " | " + userInfo.get(2));

        for(int i = 0; i < labelList.length; i++){
            JLabel label = new JLabel(labelList[i]);
            JTextField field = new JTextField(accountInfo.get(i), 20);
            fieldList.add(field);
            field.setText(accountInfo.get(i));

            // add label and field to the form panel
            formPanel.add(label);
            formPanel.add(field);
        }

        add(panel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        homeButton.addActionListener(this);
        logoutButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == homeButton){ 
            new Home(userInfo);
            dispose();
        } else if(e.getSource() == logoutButton){ 
            new Login();
            dispose();
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

