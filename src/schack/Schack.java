package schack;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
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

    public Schack() throws IOException {
        // Set theme
        try {
//            FlatSolarizedLightIJTheme.setup();
            FlatLightLaf.setup();
            embedMenuBarIfSupported();

        } catch (Exception cantThemeWithFlatLaf) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception cantThemeWithSystemLAF) {
            }
        }

        JFrame frame = new JFrame();
        frame.setTitle("Schack");
        frame.setAlwaysOnTop(false);
        frame.setResizable(false);

        //  Might throw an IOException if the icon of the Pieces isn't embedded correctly
        Board board = new Board();
        frame.setContentPane(board);
        frame.getContentPane().addMouseListener(board);

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
                JOptionPane.showMessageDialog(frame, "Local IP: " + localIp);
            } catch (UnknownHostException ex) {
                JOptionPane.showMessageDialog(frame, "Could not get local IP");
            }
        });
        askForRemi.addActionListener((ActionEvent ae) -> {
            System.out.println("I BEG FOR LE MERCY! (TODO)");
        });
        surrender.addActionListener((ActionEvent ae) -> {
            System.out.println("I'M FRENCH! (TODO)");
        });

        // Add the menu stuff
        frame.setJMenuBar(menuBar);
        menuBar.add(gameMenu);
        menuBar.add(connectMenu);
        connectMenu.add(connectToOpponent);
        connectMenu.add(showLocalIP);
        gameMenu.add(askForRemi);
        gameMenu.add(surrender);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public static void main(String[] args) throws IOException {
        new Schack();
    }

    private void embedMenuBarIfSupported() {
        // Currently only supported in Windows 10+
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            String versionNumberStr = os.split(" ")[1];
            try {
                int versionNumber = Integer.parseInt(versionNumberStr);
                if (versionNumber >= 10) {
                    System.setProperty("flatlaf.menuBarEmbedded", "true");
                }
            } catch (Exception e) {
            }

        }
    }

}
