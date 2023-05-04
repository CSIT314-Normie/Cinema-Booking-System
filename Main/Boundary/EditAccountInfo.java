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
import javax.swing.JPanel; 

public class EditAccountInfo extends JFrame implements ActionListener, MouseListener {
    private ArrayList<String> userInfo;

    private JPanel panel;
    private final JButton logoutButton = new JButton("Logout"); 
    private final JButton homeButton = new JButton("Admin Home");

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

        // Fields to edit - first name, last name, email, password, user role


        add(panel, BorderLayout.NORTH);

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

