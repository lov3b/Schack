package schack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener {

    public static final int SIZE_OF_TILE = 100;
    private Piece[][] pieces = new Piece[8][8];
    private LinkedHashSet<Point> validMovesToDraw = new LinkedHashSet<>();
    private Point selectedPiece = new Point();
    private Color moveableColor = new Color(255, 191, 0);

    public Board() throws IOException {

        this.pieces = initPieces();
        setPreferredSize(new Dimension(800, 800));

    }

    private Piece[][] initPieces() throws IOException {

        Piece[][] piecesRet = {
            {new Rook(false, new Point(0, 0)), null, null, null, null, null, null, new Rook(true, new Point(0, 7))},
            {new Horse(false, true, new Point(1, 0)), null, null, null, null, null, null, new Horse(true, true, new Point(1, 7))},
            {new Bishop(false, new Point(2, 0)), null, null, null, null, null, null, new Bishop(true, new Point(2, 7))},
            {new Queen(false, new Point(3, 0)), null, null, null, new Rook(false, new Point(3, 4)), null, null, new Queen(true, new Point(3, 7))},
            {new King(false), null, null, null, null, null, null, new King(true)},
            {null, null, null, null, null, null, null, new King(false, new Point(5, 7))},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null}
        };

        // Test 
        piecesRet[2][2] = null;
        piecesRet[2][3] = null;

        // Sätt ut bönder
        for (int i = 0; i < pieces.length; i++) {
            piecesRet[i][1] = new Pawn(false, new Point(i, 1));
            piecesRet[i][6] = new Pawn(true, new Point(i, 6));
        }

//        // Sätt ut bönder no point
//        for (int i = 0; i < pieces.length; i++) {
//            piecesRet[i][1] = new Pawn(false);
//            piecesRet[i][6] = new Pawn(true);
//        }
//        // Ställ in varje Point i varje pjäs
//        for (int x = 0; x < piecesRet.length; x++) {
//            for (int y = 0; y < piecesRet.length; y++) {
//                try {
//                    Point before = new Point(piecesRet[x][y].position);
//                    piecesRet[x][y].position = new Point(x, y);
//                    System.out.println("Efter "+piecesRet[x][y].position + " Innan " + before);
//                } catch (NullPointerException e) {
//                }
//            }
//        }
        printPieces(piecesRet);
        return piecesRet;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        drawSquares(g2);

        // måla alla ställen man kan gå¨till
        validMovesToDraw.forEach(point -> {
            if (point != null) {
                g2.setColor(moveableColor);
                g2.fillOval(point.x * SIZE_OF_TILE, point.y * SIZE_OF_TILE, SIZE_OF_TILE, SIZE_OF_TILE);
            }
        });
        // Draw piece
        Arrays.stream(pieces).forEach(pieceArr -> Arrays.stream(pieceArr).forEach(piece -> {
            if (piece != null) {
                piece.draw(g2);
            }
        }));
        // Check valid moves method
        //        Arrays.stream(pieces).forEach(pieceArr -> Arrays.stream(pieceArr).forEach(piece -> {
        //            if (piece != null) {
        //                // Draw eglible moves
        //                LinkedHashSet<Point> validMoves = piece.validMoves(pieces);
        //                Color c = new Color((int) (230 * Math.random()), (int) (230 * Math.random()), (int) (230 * Math.random()));
        //                g2.setColor(c);
        //                validMoves.forEach(point -> g2.fillOval(point.x * SIZE_OF_TILE, point.y * SIZE_OF_TILE, SIZE_OF_TILE, SIZE_OF_TILE));
        //                System.out.println("x:" + piece.position.x + ", y:" + piece.position.y + ": " + validMoves.size());
        //            }
        //        }));

        // draw valid moves
//        for (int y = 0; y < pieces.length; y++) {
//            for (int x = 0; x < pieces.length; x++) {
//                Piece p = pieces[y][x];
//                if (p == null) {
//                    continue;
//                }
//
//                LinkedHashSet<Point> validMoves = p.validMoves(pieces);
//                Color c = new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random()));
//                g2.setColor(c);
//                validMoves.forEach(point -> g2.fillOval(point.x * SIZE_OF_TILE, point.y * SIZE_OF_TILE, SIZE_OF_TILE, SIZE_OF_TILE));
//            }
//        }
    }

    public static void printPieces(Piece[][] pieces) {
        System.out.println("");
        for (int loopX = 0; loopX < pieces.length; loopX++) {
            for (int loopY = 0; loopY < pieces.length; loopY++) {
                if (loopY == 0) {
                    System.out.print("|");
                }
                Piece piece = pieces[loopY][loopX];

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

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int mouseCoordinateX = (int) (mouseEvent.getX() / SIZE_OF_TILE);
        int mouseCoordinateY = (int) (mouseEvent.getY() / SIZE_OF_TILE);

        Point clicked = new Point(mouseCoordinateX, mouseCoordinateY);

        // Ifall vi har tryckt på en pjäs och sedan ska gå dit
        if (validMovesToDraw.contains(clicked)) {
            try {
                Piece p = pieces[selectedPiece.x][selectedPiece.y];
                p.move(pieces, clicked, selectedPiece);
                validMovesToDraw.clear();
                System.out.println("came here");

            } catch (Exception e) {

            }

        } else {

            selectedPiece = new Point(clicked);
            validMovesToDraw.clear();

        }

        System.out.println("X: " + mouseCoordinateX + ", Y: " + mouseCoordinateY);
        try {
            Piece p = pieces[mouseCoordinateX][mouseCoordinateY];
            LinkedHashSet validMoves = p.validMoves(pieces);
            System.out.println("valid moves " + validMoves);
            validMovesToDraw.addAll(validMoves);
            System.out.println("valid moves to draw " + validMovesToDraw);

        } catch (Exception e) {
            validMovesToDraw.clear();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
