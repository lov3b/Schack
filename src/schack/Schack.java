package schack;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Love Billenius & Simon Hansson
 */
public class Schack extends JFrame {

    public Dimension size = new Dimension(800, 800);

    public Schack() {
        setTitle("Schack");
        setAlwaysOnTop(true);
        setResizable(false);
        setContentPane(new Board());
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        new Schack();

    }

}
