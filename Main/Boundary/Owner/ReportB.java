package Main.Boundary.Owner;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class ReportB extends JFrame implements ActionListener {

    public ReportB() {
        super("Report B");
        setLayout(new BorderLayout());
        setSize(1035, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}
