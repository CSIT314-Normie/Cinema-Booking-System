package Main.Boundary.Manager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;
import com.toedter.calendar.JCalendar;

import Main.Boundary.Home;
import Main.Controller.Manager.UpdateScreeningSessionController;

public class UpdateScreeningSession extends JFrame implements ActionListener {
    private ArrayList<String> userInfo;
    private ArrayList<String> screeningInfo;  

    private JButton homeButton = new JButton("Home");
    private JButton updateSessionButton = new JButton("Update Screening");

    private String[] labelList = {"Movie Name: ", "Hall: ", "Date: ", "Time Slot: ", "Start Time: ", "End Time: ", "Screening Status: "};
    private String[] halls;
    private String selectedHall;

    private final UpdateScreeningSessionController updateScreeningSessionController = new UpdateScreeningSessionController();
    
    public UpdateScreeningSession(ArrayList<String> userInfo, ArrayList<String> screeningInfo) {
        super("Cinema Manager - Update Screening Session");
        this.userInfo = userInfo;
        this.screeningInfo = screeningInfo;
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null); 
        setVisible(true); // Show the frame

        ArrayList<String> cinema_halls = updateScreeningSessionController.getAllHalls();
        halls = new String[cinema_halls.size() / 3];

        for (int i = 0; i < halls.length; i++) {
            if (i == 0) {
                halls[i] = cinema_halls.get(i);
            }
            else {
                halls[i] = cinema_halls.get(i * 3);
            }
        }

        // cinema manager can update the screening session's hall ONLY  
        JComboBox<String> hallDropDown = new JComboBox<>(halls);
        selectedHall = screeningInfo.get(2); // set the selected hall to the current hall of the screening session

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setPreferredSize(new Dimension(1035, 50));
        topPanel.add(homeButton);

        JPanel formPanel = new JPanel(); 
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS)); 
        formPanel.setPreferredSize(new Dimension(750, 470));

        // display the current screening session's info 
        // allow cinema manager to update the screening session's hall
        for (int i = 1; i < screeningInfo.size() - 1; i++) {
            JPanel rowPanel = new JPanel(new FlowLayout());
            rowPanel.setPreferredSize(new Dimension(750, 25)); 
            JLabel label = new JLabel(labelList[i - 1]);
            rowPanel.add(label);

            if (i == 2) {
                // select HALL 
                hallDropDown.setSelectedItem(screeningInfo.get(i));
                rowPanel.add(hallDropDown);
            } else {
                JLabel info = new JLabel(screeningInfo.get(i));
                rowPanel.add(info);
            }
             
            formPanel.add(rowPanel);
        }

        // listener for hall drop down
        hallDropDown.addActionListener(e -> {
            JComboBox cb = (JComboBox)e.getSource();
            selectedHall = (String)cb.getSelectedItem();

            System.out.println("current movie info: " + screeningInfo.toString());
            System.out.println("selected hall: " + selectedHall);
        });


        JPanel updateScreeningPanel = new JPanel(new FlowLayout());
        updateScreeningPanel.setPreferredSize(new Dimension(750, 50));
        updateScreeningPanel.add(updateSessionButton);

        formPanel.add(updateScreeningPanel);

        add(topPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        homeButton.addActionListener(this);
        updateSessionButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home":
                dispose();
                new Home(userInfo);
                
                break;
            case "Update Screening":
                // update the screening session's hall
                int screeningID = Integer.parseInt(screeningInfo.get(0));
                if (updateScreeningSessionController.updateScreeningSession(screeningID, "Hall" , selectedHall)) {
                    JOptionPane.showMessageDialog(null, "Screening session updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    dispose();
                    new ScreeningSessions(userInfo);
                } else {
                    JOptionPane.showMessageDialog(null, "Screening session failed to update!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                break;
        }
    }
    

}
