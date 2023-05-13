package Main.Boundary.Customer;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

import Main.Boundary.Home;
import Main.Controller.*;
import Main.Controller.Admin.*;


public class TicketingHistory extends JFrame implements ActionListener{
    private final ArrayList<String> userInfo;
    private final JButton homeButton = new JButton("Home");
    private final JPanel topRow = new JPanel(); 

    private final transient TicketingHistoryController ticketingHistoryController = new TicketingHistoryController();
    private final ArrayList<String[]> ticketHistory;

    public TicketingHistory(ArrayList<String> userInfo) {   
        super("Ticketing History");
        this.userInfo = userInfo;
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        this.ticketHistory = ticketingHistoryController.getTicketingHistory(this.userInfo.get(2));

        // display the ticketing history (ie, the list of movies the user has booked) 
        // create a panel to display the ticketing history
        JPanel ticketingHistoryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        for (String[] strings : ticketHistory) {
            JPanel singleHistoryPanel = new JPanel();
            singleHistoryPanel.setLayout(new BoxLayout(singleHistoryPanel, BoxLayout.Y_AXIS));
            singleHistoryPanel.setPreferredSize(new Dimension(200, 600));

            JLabel movieTitle = new JLabel(strings[0]);
            JLabel movieRate = new JLabel("Stars: " + strings[1]);
            JLabel movieReview = new JLabel("# Review: " + strings[2]);

            movieTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            movieRate.setAlignmentX(Component.CENTER_ALIGNMENT);
            movieReview.setAlignmentX(Component.CENTER_ALIGNMENT);

            // set font size for each JLabel
            movieTitle.setFont(new Font("Arial", Font.BOLD, 20));
            movieRate.setFont(new Font("Arial", Font.BOLD, 15));
            movieReview.setFont(new Font("Arial", Font.BOLD, 15));

            singleHistoryPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            singleHistoryPanel.add(movieTitle);
            singleHistoryPanel.add(movieRate);
            singleHistoryPanel.add(movieReview);

            ticketingHistoryPanel.add(singleHistoryPanel);
        }

        JScrollPane scrollPane = new JScrollPane(ticketingHistoryPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        topRow.add(homeButton, BorderLayout.WEST);
        add(topRow, BorderLayout.NORTH);
        add(ticketingHistoryPanel, BorderLayout.CENTER);

        homeButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO add  action handling code here
        if (e.getSource() == homeButton) {
            new Home(userInfo);
            dispose();
        }
    }
    
}
