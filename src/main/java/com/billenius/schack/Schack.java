package com.billenius.schack;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import com.billenius.schack.boards.Board;
import com.billenius.schack.boards.NetworkBoard;
import com.billenius.schack.boards.SameBoard;
import com.formdev.flatlaf.FlatLightLaf;

/**
 *
 * @author Love Billenius & Simon Hansson
 */
public class Schack {

    final JFrame frame;
    // Förlåt mig fader för jag kommer synda
    public final static Map<String, BufferedImage> pieceIcons = new HashMap<>();

    

    public Schack() throws IOException {
        // Set theme
        try {
            if (UIManager.getSystemLookAndFeelClassName()
                    .equals("javax.swing.plaf.metal.MetalLookAndFeel"))
                UIManager.setLookAndFeel(new FlatLightLaf());
            else
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception cantGetSystemTheme) {
        }

        String[] options = { "On the same screen", "Over the internet" };
        int choosenTransformations = JOptionPane.showOptionDialog(null,
                "How do you want to connect with your opponent",
                "Schack: Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        DefaultListModel<Move> listModel = new DefaultListModel<>();
        final Board board;
        if (choosenTransformations == 0)
            board = new SameBoard(listModel);
        else
            board = new NetworkBoard(listModel);

        frame = new JFrame();
        frame.setTitle("Schack");
        frame.setAlwaysOnTop(false);
        frame.setResizable(true);

        // Might throw an IOException if the icon of the Pieces isn't embedded correctly
        // final Board board = new BlistModeloard(listModel);

        // Logger
        final JList<Move> jlist = new JList<>(listModel);
        jlist.setVisible(true);
        jlist.setCellRenderer(new PieceRenderer());
        JScrollPane scrollPane = new JScrollPane(jlist);
        // scrollPane.setPreferredSize(new Dimension(120, 700));
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setPreferredSize(new Dimension(120, 800));

        JLabel infoText = new JLabel("Moves");
        infoText.setFont(new Font("Cantarell", Font.PLAIN, 18));

        infoText.setHorizontalAlignment(JLabel.CENTER);
        logPanel.add(infoText, BorderLayout.NORTH);
        logPanel.add(scrollPane, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(800);
        splitPane.setLeftComponent(board);
        splitPane.setRightComponent(logPanel);

        frame.setContentPane(splitPane);
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
