package Main.Boundary.Customer;


import java.awt.event.*;
import java.io.File;
import java.awt.*;
import javax.swing.*;
import java.util.*;


import Main.Controller.Customer.TicketingHistoryController;

public class TicketingHistory extends JFrame implements ActionListener{
    private final ArrayList<String> userInfo;
    private final JButton homeButton = new JButton("Home");
    private final JPanel topRow = new JPanel(); 

    private final transient TicketingHistoryController ticketingHistoryController = new TicketingHistoryController();
    private final ArrayList<String> ticketHistory;

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
        System.out.println("ticketHistory: " + ticketHistory);

        JPanel ticketingHistoryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        for (int i = 0; i < ticketHistory.size(); i+=5) {
            String movieName = ticketHistory.get(i);
            JPanel singleHistoryPanel = new JPanel();
            singleHistoryPanel.setLayout(new BoxLayout(singleHistoryPanel, BoxLayout.Y_AXIS));
            singleHistoryPanel.setPreferredSize(new Dimension(600, 400));

            JLabel movieTitle = new JLabel(ticketHistory.get(i)); 
            JLabel movieRating = new JLabel("Rating: " + ticketHistory.get(i + 2));
            JLabel movieReview = new JLabel("# Review: " + ticketHistory.get(i + 3));
            JTextArea movieDesc = new JTextArea(ticketHistory.get(i + 4), 5, 18);
            movieDesc.setLineWrap(true);
            movieDesc.setWrapStyleWord(true);
            movieDesc.setEditable(false);
            movieDesc.setBackground(new Color(240, 240, 240, 0));

            // movie image
            ImageIcon image = new ImageIcon((new File("./Main/Boundary/assets/" + ticketHistory.get(i + 1))).getAbsolutePath());
            Image scaledImage = image.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
            image = new ImageIcon(scaledImage);

            JLabel movieImage = new JLabel(image);

            movieTitle.setAlignmentX(Component.CENTER_ALIGNMENT); 
            movieImage.setAlignmentX(Component.CENTER_ALIGNMENT);
            movieRating.setAlignmentX(Component.CENTER_ALIGNMENT);
            movieReview.setAlignmentX(Component.CENTER_ALIGNMENT);
            movieDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

            // set font size for each JLabel
            movieTitle.setFont(new Font("Arial", Font.BOLD, 18));
            movieRating.setFont(new Font("Arial", Font.BOLD, 15)); 
            movieReview.setFont(new Font("Arial", Font.BOLD, 15));
            movieDesc.setFont(new Font("Arial", Font.PLAIN, 12));

            // review button
            JButton reviewButton = new JButton("Add Rating & Review");

            singleHistoryPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            singleHistoryPanel.add(movieTitle); 
            singleHistoryPanel.add(movieImage);
            singleHistoryPanel.add(movieRating);
            singleHistoryPanel.add(movieReview);
            singleHistoryPanel.add(movieDesc);
            singleHistoryPanel.add(reviewButton); 
            
            reviewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            reviewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { 

                    new AddMovieRatingAndReview(userInfo, movieName);
                    dispose();
                }
            });

            ticketingHistoryPanel.add(singleHistoryPanel);
        }

        JScrollPane scrollPane = new JScrollPane(ticketingHistoryPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        topRow.add(homeButton, BorderLayout.WEST);
        add(topRow, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        homeButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == homeButton) {
            new CustomerHome(userInfo);
            dispose();
        }
    }
    
}
