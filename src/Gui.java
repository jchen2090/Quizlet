import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui implements ActionListener {
    public void actionPerformed (ActionEvent e) {
    }
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(500, 500);
        f.getContentPane();
        f.setBackground(Color.white);
        f.setVisible(true);

        JPanel p = new JPanel();
        p.setLayout(null);
        p.setSize(500, 500);

        JLabel Quizzard = new JLabel("Quizzard");
        Quizzard.setBounds(175, 50, 200, 100);
        Quizzard.setFont(new Font("BOLD", Font.BOLD, 25));

        JButton create = new JButton("Create New List");
        create.setBounds(185, 175, 75, 30);

        JButton load = new JButton("Load Old List");
        load.setBounds(180, 225, 75, 30);

        p.add(create);
        p.add(load);
        p.add(Quizzard);
        f.add(p);

        if (!DataHandler.dataFileExists()) {
            p.remove(load);
        }

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        if (create.getModel().isPressed()) {
            JPanel panel1 = new JPanel();
            panel1.setSize(500, 500);
            p.setVisible(false);
            f.add(panel1);
            panel1.setBackground(Color.BLACK);
        }
      }
}

