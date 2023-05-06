package Main.Boundary;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

public class UpdateMovieInfo extends JFrame implements ActionListener {
    private ArrayList<String> userInfo;
    private String[] movieInfo;

    JButton homButton = new JButton("Home");

    public UpdateMovieInfo(ArrayList<String> userInfo, String[] movieInfo) {
         // Set up of the frame
         super("Update Movie Info - " + movieInfo[0]);
         setLayout(new FlowLayout());
         setSize(1035, 750);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setVisible(true);
         setResizable(false);
         setLocationRelativeTo(null);

        this.userInfo = userInfo;
        this.movieInfo = movieInfo;

        add(homButton);

        homButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home":
                dispose();
                new Home(userInfo);
                break;
        }
    }
    
}
