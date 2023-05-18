package Main.Boundary.Manager;


import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*; 
import java.util.*;
 
import Main.Controller.Manager.*;


public class AddMovie extends JFrame implements ActionListener {
    private final ArrayList<String> userInfo;
    private final JPanel topPanel;
    private final JPanel centerPanel;
    private final JPanel movieImgPanel = new JPanel(new FlowLayout());

    private final JButton homeButton = new JButton("Home");
    private final JButton logoutButton = new JButton("Logout");
    private final JButton uploadButton = new JButton("Upload image");
    private final JButton deleteImgButton = new JButton("Delete image");
    private final JButton submitButton = new JButton("Submit");

    // As of now, there will be 4 fields (Movie Name, movie image, movie description, movie status (Fully Booked or Available (Default))
    private final String[] labelList = {"Movie Name: ", "Movie Image: ", "Movie Description: ", "Movie Status: ", "Movie Duration: "};
    private final ArrayList<JTextField> fieldList = new ArrayList<>(2); // 2 text fields: movie name and movie description
    private final JComboBox<String> statusList = new JComboBox<>(new String[]{"Available", "Unavailable"});
    private String movieStatus = "Available"; // default status
    private String movieImgFileName; 

    private final transient AddMovieController addNewMovieController = new AddMovieController();

    public AddMovie(ArrayList<String> userInfo) {
        super("Cinema Manager - Add Movie");
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
        topPanel.add(homeButton);
        topPanel.add(logoutButton);

        // panel to enter new movie information
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Add New Movie");
        title.setFont(new Font("Serif", Font.PLAIN, 25));
        centerPanel.add(title);

        // submit button to add the new movie to the database
        submitButton.setPreferredSize(new Dimension(100, 30));

        // add in fields for movie information
        // Movie Name, movie image, movie showing time, movie description, movie status (Fully Booked or Available)
        // - if fully booked, then the movie will not be shown in the movie list (customer homepage)
        // - if available, then the movie will be shown in the movie list (customer homepage)
        for (int i = 0; i < labelList.length; i++) {
            JPanel panel = new JPanel(new BorderLayout());
            JLabel label = new JLabel(labelList[i]);
            JTextField field = new JTextField(20);

            label.setFont(new Font("Serif", Font.PLAIN, 18));
            field.setPreferredSize(new Dimension(200, 30)); 

            fieldList.add(field);

            if (i == 3) { // drop down menu for role
                statusList.setSelectedItem("Available");
                panel.add(label, BorderLayout.WEST);
                panel.add(statusList, BorderLayout.EAST);
                panel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
            } 
            else if (i == 1) {
                // to upload movie image from local file system
                uploadButton.setSize(200, 30);
                panel.add(label, BorderLayout.WEST);
                panel.add(uploadButton, BorderLayout.CENTER);

                uploadButton.setFocusable(false);
                
            } else {
                // add label and field to the form panel
                panel.add(label, BorderLayout.WEST);
                panel.add(field, BorderLayout.EAST);
                panel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
            } 
            
            centerPanel.add(panel); 
        } 

        displayUploadedImage();

        // add listener to the drop down menu
        statusList.addActionListener(e -> movieStatus = (String) statusList.getSelectedItem());

        add(topPanel);
        add(centerPanel);

        // add action listeners to the buttons
        homeButton.addActionListener(this);
        logoutButton.addActionListener(this);
        uploadButton.addActionListener(this);
        deleteImgButton.addActionListener(this);
        submitButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home":
                dispose();
                new ManagerHome(userInfo);
                break;
            case "Logout":
                dispose();
                new ManagerLogin();
                break;
            case "Upload image": 
                // Create a new file chooser dialog
                JFileChooser fileChooser = new JFileChooser();

                // Set the file chooser to only allow image files
                fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                    public boolean accept(File file) {
                        return file.getName().toLowerCase().endsWith(".jpg")
                                || file.getName().toLowerCase().endsWith(".jpeg")
                                || file.getName().toLowerCase().endsWith(".png")
                                || file.isDirectory();
                    }

                    public String getDescription() {
                        return "Image files (*.jpg, *.jpeg, *.png)";
                    }
                });

                // Show the file chooser dialog and wait for the user to select a file
                int result = fileChooser.showOpenDialog(this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    movieImgFileName = selectedFile.getName();
                    String absPath = selectedFile.getAbsolutePath();

                    // copy image to /Boundary/assets
                    File dest = new File("./Main/Boundary/assets/" + movieImgFileName);
                    try {
                        java.nio.file.Files.copy(selectedFile.toPath(), dest.toPath());
                    } catch (Exception ex) {
                        System.out.println("[-] Error copying file to /Boundary/assets");
                    }

                    System.out.println("[+] Selected file: " + absPath + " , " + movieImgFileName);
                }
                displayUploadedImage();
                break;
            case "Delete image":
                // if image has already been copied to /Boundary/assets, then delete it
                File dest = new File("./Main/Boundary/assets/" + movieImgFileName);
                if (dest.exists()) {
                    try {
                        java.nio.file.Files.delete(dest.toPath());
                    } catch (Exception ex) {
                        System.out.println("[-] Error deleting file from /Boundary/assets");
                    }
                }
                movieImgFileName = null;
                displayUploadedImage();

                break;
            case "Submit": 
                // - check if all fields are filled in and image is uploaded
                // - if not, then display error message 

                // check if all fields are filled in
                if (fieldList.contains("")) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);

                    // check if movie image file is uploaded
                    if (movieImgFileName == null) {
                        JOptionPane.showMessageDialog(this, "Please upload movie image", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    if (movieStatus == null) {
                        JOptionPane.showMessageDialog(this, "Please select movie status", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // check if movie image file is uploaded
                    if (movieImgFileName == null) {
                        JOptionPane.showMessageDialog(this, "Please upload movie image", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (movieStatus == null) {
                        JOptionPane.showMessageDialog(this, "Please select movie status", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    System.out.println("[+] All fields filled in");
                    ArrayList<String> movieInfo = new ArrayList<>();
                    
                    // get all the values from the text fields
                    for (int i = 0; i < labelList.length; i++) {
                        // index 0: movie name, index 2: movies description
                        if (i == 0 || i == 2) {
                            movieInfo.add(fieldList.get(i).getText());
                        } 

                        if (i == 1) {
                            movieInfo.add(movieImgFileName);
                        }

                        if (i == 3) {
                            movieInfo.add(movieStatus);
                        }

                        if (i == 4) {
                            movieInfo.add(fieldList.get(i).getText());
                        }
                    }
                    // add image and status to movieInfo
                    // movieInfo.set(1, movieImgFileName);
                    // movieInfo.set(3, movieStatus);
                    System.out.println("[+] movieInfo arrayList: " + movieInfo.size());

                    // add movie to database
                    if (addNewMovieController.insertMovie(movieInfo)) {
                        JOptionPane.showMessageDialog(this, "Movie added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        // new Home(userInfo);
                        // dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error adding movie", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                }
        }
    }

    public void displayUploadedImage() {
        System.out.println("[+] Displaying uploaded image");

        movieImgPanel.removeAll();
        
        // add a label to display the movie image uploaded 
        JLabel label = new JLabel("Movie Image: ");
        JLabel movieImgLabel = new JLabel(); 
        movieImgLabel.setPreferredSize(new Dimension(200, 200));

        movieImgPanel.add(label);

        if (movieImgFileName != null) {
            JPanel imgPanel = new JPanel();
            imgPanel.setPreferredSize(new Dimension(150, 250));

            JLabel movieIcon = new JLabel();
            ImageIcon imageIcon = new ImageIcon("./Main/Boundary/assets/" + movieImgFileName); 
            Image newimg = imageIcon.getImage().getScaledInstance(150, 230,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
            imageIcon = new ImageIcon(newimg);  // transform it back
            movieIcon.setIcon(imageIcon);

            movieImgLabel.setText(movieImgFileName); // movie image name
            imgPanel.add(movieIcon);
            imgPanel.add(movieImgLabel);

            movieImgPanel.add(imgPanel);
        } else {
            movieImgLabel.setText("No image uploaded");
            movieImgPanel.add(movieImgLabel);
        } 

        // delete the movie image uploaded 
        movieImgPanel.add(deleteImgButton);
        
        // refresh the panel
        movieImgPanel.revalidate();
        movieImgPanel.repaint();

        centerPanel.add(movieImgPanel);
        centerPanel.add(submitButton);
    }
}   
