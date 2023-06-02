import javax.swing.*;
import java.awt.*;

public class Gui {
    public static void main(String[] args) {
        JFrame j = new JFrame();
        j.setSize(500, 500);
        j.getContentPane().setBackground(Color.white);
        j.setVisible(true);

        JLabel Quizard = new JLabel("Quizard");
        Quizard.setBounds(100, 100, 200, 200);


        j.add(Quizard);
    }
}

