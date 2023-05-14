package Main.Boundary.Owner;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class ReportA extends JFrame implements ActionListener {

    public ReportA() {
        super("Report A");
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
