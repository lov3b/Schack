package schack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import javax.swing.JPanel;

public class Board extends JPanel {

    public static final int SIZE_OF_TILE = 100;
    private Piece[][] pieces = new Piece[8][8];

    public Board() throws IOException {

        this.pieces = initPieces();
        setPreferredSize(new Dimension(800, 800));

    }

    private Piece[][] initPieces() throws IOException {

        Piece[][] piecesRet = {
            {null, null, null, null, new King(false), null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, new King(true), null, null, null}
        };

        // Pawns
        for (int i = 0; i < piecesRet[1].length; i++) {
            piecesRet[1][i] = new Pawn(false, new Point(i, 1));
            piecesRet[6][i] = new Pawn(true, new Point(i, 6));
        }

        return piecesRet;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        drawSquares(g2);

//        for (Piece[] pieceArr : pieces) {
//            for (Piece p : pieceArr) {
//                if (p != null) {
//                    p.draw(g2);
//                }
//            }
//        }
        // Draw piece
        Arrays.stream(pieces).forEach(pieceArr -> Arrays.stream(pieceArr).forEach(piece -> {
            if (piece != null) {
                piece.draw(g2);
            }
        }));

        // Check valid moves method
        Arrays.stream(pieces).forEach(pieceArr -> Arrays.stream(pieceArr).forEach(piece -> {
            if (piece != null) {
                // Draw eglible moves
                LinkedHashSet<Point> validMoves = piece.validMoves(pieces);
                Color c = new Color((int) (230 * Math.random()), (int) (230 * Math.random()), (int) (230 * Math.random()));
                g2.setColor(c);
                validMoves.forEach(point -> g2.fillOval(point.x * SIZE_OF_TILE, point.y * SIZE_OF_TILE, SIZE_OF_TILE, SIZE_OF_TILE));
                System.out.println("x:" + piece.position.x + ", y:" + piece.position.y + ": " + validMoves.size());
            }
        }));
        printPieces();

//        // Check valid moves method
//        for (Piece[] piecesOne : pieces) {
//            for (Piece p : piecesOne) {
//                if (p == null) {
//                    continue;
//                }
//                LinkedHashSet<Point> validMoves = p.validMoves(pieces);
//                Color c = new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random()));
//                g2.setColor(c);
//                validMoves.forEach(point -> g2.fillOval(point.x * SIZE_OF_TILE, point.y * SIZE_OF_TILE, SIZE_OF_TILE, SIZE_OF_TILE));
//                System.out.println("x:" + p.position.x + ", y:" + p.position.y + ": " + validMoves.size());
//            }
//        }
    }

    private void printPieces() {
        System.out.println("");
        for (int i = 0; i < pieces.length; i++) {
            Piece[] pieceArr = pieces[i];
            for (int j = 0; j < pieceArr.length; j++) {
                if (j == 0) {
                    System.out.print("|");
                }
                Piece piece = pieceArr[j];

                // Titta inte Nicklas. Det är bara debug, jag ska ta bort det sedan lovar :P
                String type;
                if (piece instanceof Pawn) {
                    type = "Pawn";
                } else if (piece instanceof King) {
                    type = "King";
                } else {
                    type = "Nill";
                }

                String toPrint = piece == null ? "nill??|" : type + piece.position.x + piece.position.y + "|";
                System.out.print(toPrint);

            }
            System.out.println("");

        }

    }

    private void drawSquares(Graphics2D g2) {

        g2.setBackground(Color.WHITE);
        g2.setColor(Color.DARK_GRAY);

        for (int i = 0; i < 8; i += 2) {
            for (int j = 0; j < 8; j += 2) {
                g2.fillRect(i * SIZE_OF_TILE, j * SIZE_OF_TILE, 1 * SIZE_OF_TILE, 1 * SIZE_OF_TILE);
            }
        }

        for (int i = 1; i < 8; i += 2) {
            for (int j = 1; j < 8; j += 2) {
                g2.fillRect(i * SIZE_OF_TILE, j * SIZE_OF_TILE, 1 * SIZE_OF_TILE, 1 * SIZE_OF_TILE);
            }
        }

    }
}
