package schack;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Love Billenius & Simon Hansson
 */
public class Schack {

    final JFrame frame;

    public Schack() throws IOException {
        // Set theme
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception cantGetSystemTheme) {
        }

        frame = new JFrame();
        frame.setTitle("Schack");
        frame.setAlwaysOnTop(false);
        frame.setResizable(false);

        //  Might throw an IOException if the icon of the Pieces isn't embedded correctly
        final Board board = new Board();
        frame.setContentPane(board);
        frame.getContentPane().addMouseListener(board);

        // Create menu        
        final JMenuBar menuBar = new JMenuBar();
        final JMenu gameMenu = new JMenu("Game");
        final JMenuItem askForRemi = new JMenuItem("Ask for remi");
        final JMenuItem surrender = new JMenuItem("Surrender");

        askForRemi.addActionListener((ActionEvent ae) -> {
            String whosWantingRemi = board.isWhitesTurn() ? "Vit" : "Svart";
            int choice = JOptionPane.showConfirmDialog(board, whosWantingRemi + " erbjuder remi\nAccepterar du?");
            if (choice == JOptionPane.YES_OPTION) {
                choice = JOptionPane.showConfirmDialog(board, "Lika\nStarta om?");
                if (choice == JOptionPane.YES_OPTION) {
                    try {
                        board.restartGame();
                    } catch (IOException ex) {
                    }
                }
            }
        });
        surrender.addActionListener((ActionEvent ae) -> {
            String whosGivingUp = board.isWhitesTurn() ? "Vit" : "Svart";
            final int choice = JOptionPane.showConfirmDialog(board, whosGivingUp + " har gett upp\nStarta om?");
            if (choice == JOptionPane.YES_OPTION) {
                try {
                    board.restartGame();
                } catch (IOException ex) {
                }
            }
        });

        // Add the menu stuff
        frame.setJMenuBar(menuBar);
        menuBar.add(gameMenu);
        gameMenu.add(askForRemi);
        gameMenu.add(surrender);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public static void main(String[] args) throws IOException {
        new Schack();
    }
}
