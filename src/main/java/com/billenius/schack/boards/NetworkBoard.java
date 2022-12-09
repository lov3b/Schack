package com.billenius.schack.boards;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.billenius.schack.BasicMove;
import com.billenius.schack.Move;
import com.billenius.schack.SchackState;
import com.billenius.schack.pieces.Piece;

public final class NetworkBoard extends Board implements Runnable {
    public final static int PORT = 1339;
    private final Socket socket;
    private ServerSocket serverSocket = null;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private final Thread inputListener;
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

        // Nu ska vi bli en Server
        if (choosenOption == 0) {
            String ip = Inet4Address.getLocalHost().getHostAddress();

            final JFrame frame = new JFrame("Schack: Networking");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel panel = new JPanel(new BorderLayout());
            panel.setPreferredSize(new Dimension(600, 200));

            JLabel loading = new JLabel(
                    "<html>Waiting for opponent to connect...<br/>Listening on <i>" + ip + ":" + PORT + "</i></html>");
            loading.setFont(new Font("Cantarell", Font.PLAIN, 24));
            loading.setHorizontalAlignment(JLabel.CENTER);

            panel.add(loading, BorderLayout.CENTER);
            frame.add(panel);
            frame.pack();
            frame.setVisible(true);
            serverSocket = new ServerSocket(PORT);
            this.socket = serverSocket.accept();
            // myColor = new Random().nextBoolean();
            myColor = true; // VIT
            frame.setVisible(false);

            // Get input/output stream
            System.out.println("Getting inputstream");
            inputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("Getting outputstream");
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        }
        // Nu ska vi bli en klient
        else {
            String ip = JOptionPane.showInputDialog(null,
                    "What's the IP of your opponent?",
                    "Schack: Networking",
                    JOptionPane.QUESTION_MESSAGE);
            if (ip.equals(""))
                ip = "localhost";
            this.socket = new Socket(ip, 1339);
            myColor = false; // SVART

            // Get input/output stream
            System.out.println("Getting outputstream");
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Getting inputstream");
            inputStream = new ObjectInputStream(socket.getInputStream());
        }
        inputListener = new Thread(this);
        inputListener.start();
    }

    @Override
    protected void makeMove(Move move) {
        try {
            turnCount++;
            moveList.addElement(move);
            move.movedPiece.move(pieces, move.to);
            System.out.println("repainting");
            getParent().repaint();
            System.out.println("Sending move to opponent");
            outputStream.writeObject(new BasicMove(move));
            System.out.println("Move sent");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void toDoIfNoPreviousPieceSelected(Piece selectedPiece) {
        if (selectedPiece != null && selectedPiece.isWhite() == myColor)
            validMovesToDraw.addAll(selectedPiece.validMoves(pieces, true));
    }

    @Override
    public void run() {
        try {
            while (true) {
                BasicMove basicMove = (BasicMove) inputStream.readObject();
                Move move = new Move(pieces[basicMove.from.x][basicMove.from.y], basicMove);
                System.out.println("Got move");
                moveList.addElement(move);
                turnCount++;

                System.out.println("Moving piece");
                move.movedPiece.move(pieces, move.to);
                System.out.println("Repainting");
                getParent().repaint();

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

            }
        } catch (EOFException | SocketException e) {
            JOptionPane.showMessageDialog(this, "Lost connection to opponent");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e);
        }

    }

}
