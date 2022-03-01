package schack;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

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

        // Create menu        
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenu connectMenu = new JMenu("Connect");
        JMenuItem connectToOpponent = new JMenuItem("Connect to opponent");
        JMenuItem showLocalIP = new JMenuItem("Show IP");
        JMenuItem askForRemi = new JMenuItem("Ask for remi");
        JMenuItem surrender = new JMenuItem("Surrender");

        // Actions
        connectToOpponent.addActionListener((ActionEvent ae) -> {
            System.out.println("Connecting (TODO)");
        });
        showLocalIP.addActionListener((ActionEvent ae) -> {
            try {
                String localIp = InetAddress.getLocalHost().toString();
                JOptionPane.showMessageDialog(this, "Local IP: " + localIp);
            } catch (UnknownHostException ex) {
                JOptionPane.showMessageDialog(this, "Could not get local IP");
            }
        });
        askForRemi.addActionListener((ActionEvent ae) -> {
            System.out.println("I BEG FOR LE MERCY! (TODO)");
        });
        surrender.addActionListener((ActionEvent ae) -> {
            System.out.println("I'M FRENCH! (TODO)");
        });

        // Add the menu stuff
        setJMenuBar(menuBar);
        menuBar.add(gameMenu);
        menuBar.add(connectMenu);
        connectMenu.add(connectToOpponent);
        connectMenu.add(showLocalIP);
        gameMenu.add(askForRemi);
        gameMenu.add(surrender);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public static void main(String[] args) {
        new Schack();

    }

}
