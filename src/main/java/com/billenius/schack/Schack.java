package com.billenius.schack;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.NetworkInterface;
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

        // Might throw an IOException if the icon of the Pieces isn't embedded correctly
        final Board board = new Board();
        frame.setContentPane(board);
        frame.getContentPane().addMouseListener(board);

        // Create menu
        final JMenuBar menuBar = new JMenuBar();
        final JMenu gameMenu = new JMenu("Game");
        final JMenu connectMenu = new JMenu("Connect");
        final JMenuItem askForRemi = new JMenuItem("Ask for remi");
        final JMenuItem surrender = new JMenuItem("Surrender");
        final JMenuItem showIP = new JMenuItem("Show IP");
        final JMenuItem connectToOpponent = new JMenuItem("Connect to opponent");

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
            int choice = JOptionPane.showConfirmDialog(board, whosGivingUp + " har gett upp\nStarta om?");
            if (choice == JOptionPane.YES_OPTION) {
                try {
                    board.restartGame();
                } catch (IOException ex) {
                }
            }
        });
        showIP.addActionListener((ActionEvent ae) -> {
            try {

                String ip = Inet4Address.getLocalHost().toString();
                JOptionPane.showMessageDialog(null, "IP: " + ip);

            } catch (HeadlessException | UnknownHostException e) {
            }
        });
        connectToOpponent.addActionListener((ActionEvent ae) -> {
            String opponentIP = JOptionPane.showInputDialog(null, "What's your opponents IP?");
            System.out.println("opponents ip: " + opponentIP);

        });

        // Add the menu stuff
        frame.setJMenuBar(menuBar);
        menuBar.add(gameMenu);
        menuBar.add(connectMenu);
        gameMenu.add(askForRemi);
        gameMenu.add(surrender);
        connectMenu.add(showIP);
        connectMenu.add(connectToOpponent);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new Schack();
    }
}
