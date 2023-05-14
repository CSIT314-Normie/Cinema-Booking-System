package Main.Boundary.Customer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Flow;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import Main.Controller.Customer.BookMovieController;

public class BookMovie extends JFrame implements ActionListener {
    private ArrayList<String> userInfo;
    private String selectedScreeningID;
    private String movieName;

    private ArrayList<String> screeningInfo;
    private ArrayList<String> seats;
    private ArrayList<String> reservedSeats;

    private ArrayList<String> selectedSeats = new ArrayList<String>();
    private String hall;   

    private final JButton homeButton = new JButton("Home");
    private final JButton confirmButton = new JButton("Confirm");

    private final BookMovieController bookMovieController = new BookMovieController();

    public BookMovie(ArrayList<String> userInfo, String selectedScreeningID, String movieName) {
        super("CSIT 314 Cinema Booking System - Book movie: " + movieName);
        this.userInfo = userInfo; 
        this.selectedScreeningID = selectedScreeningID;
        this.movieName = movieName;
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        screeningInfo = bookMovieController.getScreeningInfo(selectedScreeningID);
        hall = screeningInfo.get(2);
        seats = bookMovieController.getSeats(hall);
        reservedSeats = bookMovieController.getReservedSeats(hall, selectedScreeningID); 

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(homeButton);

        JPanel bookingPanel = new JPanel(new FlowLayout());
        bookingPanel.setPreferredSize(new Dimension(1035, 500));

        JPanel movieInfoPanel = new JPanel(new FlowLayout());
        movieInfoPanel.setPreferredSize(new Dimension(1035, 100));
        JLabel movieinfoLabel = new JLabel("Movie: " + movieName + " | Hall: " + hall + " | Time: " + screeningInfo.get(5));
        movieInfoPanel.add(movieinfoLabel);
        
        // legend panel shows what the different colors mean
        JPanel legendPanel = new JPanel();
        legendPanel.setPreferredSize(new Dimension(1035, 50));

        for (int i = 0; i < 3; i++) {
            JLabel legendLabel = new JLabel();
            JButton legendButton = new JButton();
            legendButton.setEnabled(false);
            legendButton.setPreferredSize(new Dimension(20, 20));

            if (i == 0) {
                legendLabel.setText("Booked: ");
                legendButton.setBackground(Color.darkGray);
            } else if (i == 1) { 
                legendLabel.setText("Selected: ");
                legendButton.setBackground(Color.green);
            } else if (i == 2) { 
                legendLabel.setText("Available: ");
                legendButton.setBackground(Color.blue);
            }

            legendPanel.add(legendLabel);
            legendPanel.add(legendButton); 
        }

        // seats panel shows the seats in the hall for the selected screening
        JPanel seatsPanel = new JPanel(new GridLayout(4, 4, 10, 10));
        seatsPanel.setPreferredSize(new Dimension(400, 300));
        seatsPanel.setBorder(BorderFactory.createTitledBorder("Select seats"));

        for (int i = 0; i < seats.size(); i += 4) {
            String seatID = seats.get(i); 

            JButton seatButton = new JButton(seatID); 
            seatButton.setPreferredSize(new Dimension(50, 40));  

            // check if seat is reserved
            if (reservedSeats.contains(seatID)) { 
                seatButton.setEnabled(false);
                seatButton.setBackground(Color.darkGray);
            } else {
                seatButton.setEnabled(true);
                seatButton.setBackground(Color.blue);

                // add action listener to seat button to select/deselect seat
                seatButton.addActionListener(e -> { 
                    if(selectedSeats.contains(seatID)) {
                        // remove seat from selected seats if already selected
                        selectedSeats.remove(seatID);
                        seatButton.setBackground(Color.blue);
                    } else {
                        // add seat to selected seats if not already selected
                        selectedSeats.add(seatID);
                        seatButton.setBackground(Color.green);
                    } 
                }); 
            }
            seatsPanel.add(seatButton);
        }

        bookingPanel.add(movieInfoPanel);
        bookingPanel.add(legendPanel); 
        bookingPanel.add(seatsPanel);

        add(topPanel, BorderLayout.NORTH);
        add(bookingPanel, BorderLayout.CENTER);

        // listeners for buttons
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
}
