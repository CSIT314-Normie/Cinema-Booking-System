package Main.Boundary.Manager;

import java.awt.*; 
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel; 

import java.util.*;

import Main.Controller.Manager.GetAllReviewsController;
import Main.Controller.Manager.DeleteReviewController;


public class ManageReviews extends JFrame implements ActionListener {
    private ArrayList<String> userInfo;

    // data
    private ArrayList<String> allReviews;
    private String selectedReviewId;

    // Create header labels 
    private final JButton homeButton = new JButton("Home");

    // Create panels
    private JPanel reviewsPanel = new JPanel(new FlowLayout());
    private JScrollPane scrollPane;

    private final GetAllReviewsController getAllReviewsController = new GetAllReviewsController();
    private final DeleteReviewController deleteReviewController = new DeleteReviewController();


    public ManageReviews(ArrayList<String> userInfo) {
        super("Manage Movie Review");
        this.userInfo = userInfo;
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        // get all reviews
        this.allReviews = getAllReviewsController.getAllReviews();

        // top panel 
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setPreferredSize(new Dimension(1035, 50));
        topPanel.add(homeButton);

        // display all reviews in a table
        displayAllReviews(); 

        // manage reviews panel - to delete reviews
        JPanel manageReviewsPanel = new JPanel(new FlowLayout());
        manageReviewsPanel.setPreferredSize(new Dimension(1035, 50));
        
        // button to delete review
        JButton deleteReviewButton = new JButton("Delete Review");
        manageReviewsPanel.add(deleteReviewButton);

        // add panels to frame
        add(topPanel, BorderLayout.NORTH); 
        add(reviewsPanel, BorderLayout.CENTER);
        add(manageReviewsPanel, BorderLayout.SOUTH);

        homeButton.addActionListener(this);
        deleteReviewButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home":
                dispose();
                new ManagerHome(userInfo);
                
                break;
            case "Delete Review":
                // check if a review is selected
                if (selectedReviewId == null) {
                    JOptionPane.showMessageDialog(null, "Please select a review to delete");
                } else {
                    // delete review
                   if (deleteReviewController.deleteAReview(selectedReviewId)) {
                       JOptionPane.showMessageDialog(null, "Review deleted successfully");
                       displayAllReviews();
                   } else {
                       JOptionPane.showMessageDialog(null, "Error deleting review");
                   } 
                }
                break;
        }
    }

    /**
     * To display all reviews in a table
     */
    public void displayAllReviews() {
        reviewsPanel.removeAll();

        // get all reviews
        this.allReviews = getAllReviewsController.getAllReviews();

        // columns
        String[] columns = {"Review ID", "email", "Movie Name", "Review", "Rating"};

        // create table
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (int i = 0; i < this.allReviews.size(); i += 5) {
            String reviewId = this.allReviews.get(i);
            String email = this.allReviews.get(i + 1);  
            String movieName = this.allReviews.get(i + 2);
            String review = this.allReviews.get(i + 3);
            String rating = this.allReviews.get(i + 4);

            // add row to table
            tableModel.addRow(new Object[] {reviewId, email, movieName, review, rating});
        }

        // create table
        JTable table = new JTable(tableModel);
        table.setRowHeight(50);

        // add listener for table
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            if (!selectionModel.isSelectionEmpty()) {
                int row = selectionModel.getMinSelectionIndex(); 
                 
                // get review id
                selectedReviewId = tableModel.getValueAt(row, 0).toString();
            }
        });

        // add table to scroll pane
        this.scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);    
        this.scrollPane.setPreferredSize(new Dimension(950, 600));

        reviewsPanel.add(scrollPane);

        reviewsPanel.repaint();
        reviewsPanel.revalidate();
    }
}
