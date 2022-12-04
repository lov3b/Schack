package com.billenius.schack.boards;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.billenius.schack.Move;
import com.billenius.schack.SchackState;
import com.billenius.schack.pieces.Piece;

public final class NetworkBoard extends Board {
    private final Socket socket;
    private ServerSocket serverSocket = null;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    private Boolean myColor = null;

    public NetworkBoard(DefaultListModel<Move> listModel) throws IOException {
        super(listModel);
        String[] options = { "Opponent connects to me", "I'am connecting to the opponent" };
        int choosenOption = JOptionPane.showOptionDialog(null,
                "Are you going to connect to the opponent or the reverse?",
                "Schack: Networking",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        if (choosenOption == 0) {
            String ip = Inet4Address.getLocalHost().toString();

            final JFrame frame = new JFrame("Schack: Networking");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel panel = new JPanel(new BorderLayout());
            panel.setPreferredSize(new Dimension(200, 200));

            JLabel loading = new JLabel(
                    "Waiting for opponent to connect...\nYour ip is " + ip + " Listening on port 1339");
            loading.setFont(new Font("Cantarell", Font.PLAIN, 24));
            loading.setHorizontalAlignment(JLabel.CENTER);

            panel.add(loading, BorderLayout.CENTER);
            frame.add(panel);
            frame.pack();
            frame.setVisible(true);
            serverSocket = new ServerSocket(1339);
            this.socket = serverSocket.accept();
            // myColor = new Random().nextBoolean();
            myColor = true;
            frame.setVisible(false);

            // Get input/output stream
            System.out.println("Getting inputstream");
            inputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("Getting outputstream");
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } else {
            String ip = JOptionPane.showInputDialog(null,
                    "What's the IP of your opponent?",
                    "Schack: Networking",
                    JOptionPane.QUESTION_MESSAGE);
            this.socket = new Socket(ip, 1339);
            myColor = false;

            // Get input/output stream
            System.out.println("Getting outputstream");
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Getting inputstream");
            inputStream = new ObjectInputStream(socket.getInputStream());
        }
    }

    @Override
    protected void makeMove(Move move) {
        try {
            move.movedPiece.move(pieces, move.to);
            outputStream.writeObject(move);

            SchackState state = getSchackState();
            switch (state) {
                case SCHACK:
                    JOptionPane.showMessageDialog(this, "Du står i schack");
                    break;
                case SCHACKMATT:
                case PATT:
                    String stateStr = state.toString();
                    String msg = stateStr.charAt(0) + stateStr.substring(1, stateStr.length()).toLowerCase();
                    int choice = JOptionPane.showConfirmDialog(this, msg + "\nVill du starta om?");

                    if (choice == JOptionPane.YES_OPTION)
                        try {
                            restartGame();
                        } catch (IOException ex) {
                        }

                    break;
                default:
            }

            move = (Move) inputStream.readObject();
            move.movedPiece.move(pieces, move.to);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void toDoIfNoPreviousPieceSelected(Piece selectedPiece) {
        if (selectedPiece != null && selectedPiece.isWhite() == myColor)
            validMovesToDraw.addAll(selectedPiece.validMoves(pieces, true));
    }

}
