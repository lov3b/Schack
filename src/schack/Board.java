package schack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener {

    public static final int SIZE_OF_TILE = 100;
    private Piece[][] pieces = new Piece[8][8];
    private ArrayList<Point> validMovesToDraw = new ArrayList<>();
    private Point previouslyClickedPoint = new Point();
    private final Color moveableColor = new Color(255, 191, 0);
    short turnCount = 0;
    private boolean whitesTurn = true;

    public Board() throws IOException {
        this.pieces = getPieces();
        setPreferredSize(new Dimension(800, 800));
    }

    /**
     * Nollställ brädet
     *
     * @throws IOException
     */
    public void restartGame() throws IOException {
        this.pieces = getPieces();
        this.whitesTurn = true;
        getParent().repaint();
    }

    private Piece[][] getPieces() throws IOException {
        Piece[][] piecesRet = {
            {new Rook(false, new Point(0, 0)), null, null, null, null, null, null, new Rook(true, new Point(0, 7))},
            {new Horse(false, true, new Point(1, 0)), null, null, null, null, null, null, new Horse(true, true, new Point(1, 7))},
            {new Bishop(false, new Point(2, 0)), null, null, null, null, null, null, new Bishop(true, new Point(2, 7))},
            {new Queen(false, new Point(3, 0)), null, null, null, null, null, null, new Queen(true, new Point(3, 7))},
            {new King(false, new Point(4, 0)), null, null, null, null, null, null, new King(true, new Point(4, 7))},
            {new Bishop(false, new Point(5, 0)), null, null, null, null, null, null, new Bishop(true, new Point(5, 7))},
            {new Horse(false, true, new Point(6, 0)), null, null, null, null, null, null, new Horse(true, true, new Point(6, 7))},
            {new Rook(false, new Point(7, 0)), null, null, null, null, null, null, new Rook(true, new Point(7, 7))}
        };

        // Sätt ut bönder
        for (int i = 0; i < pieces.length; i++) {
            piecesRet[i][1] = new Pawn(false, new Point(i, 1));
            piecesRet[i][6] = new Pawn(true, new Point(i, 6));
        }

        return piecesRet;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        drawSquares(g2);

        // Måla alla ställen man kan gå till
        g2.setColor(moveableColor);
        for (Point validMove : validMovesToDraw) {
            if (validMove == null) {
                continue;
            }
            g2.fillOval(validMove.x * SIZE_OF_TILE, validMove.y * SIZE_OF_TILE, SIZE_OF_TILE, SIZE_OF_TILE);
        }

        // Måla alla pjäser
        for (Piece[] pieceArr : pieces) {
            for (Piece piece : pieceArr) {
                if (piece == null) {
                    continue;
                }
                piece.draw(g2);
            }
        }
    }

    private void drawSquares(Graphics2D g2) {
        g2.setBackground(Color.WHITE);
        g2.setColor(Color.DARK_GRAY);

        for (int i = 0; i < 8; i += 2) {
            for (int j = 0; j < 8; j += 2) {
                g2.fillRect(i * SIZE_OF_TILE, j * SIZE_OF_TILE, SIZE_OF_TILE, SIZE_OF_TILE);
                g2.fillRect((i + 1) * SIZE_OF_TILE, (j + 1) * SIZE_OF_TILE, SIZE_OF_TILE, SIZE_OF_TILE);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        final int mouseCoordinateX = (int) (mouseEvent.getX() / SIZE_OF_TILE);
        final int mouseCoordinateY = (int) (mouseEvent.getY() / SIZE_OF_TILE);
        final Point clickedCoordinate = new Point(mouseCoordinateX, mouseCoordinateY);

        // Ifall vi har tryckt på en pjäs och sedan ska gå dit
        if (validMovesToDraw.contains(clickedCoordinate)) {
            final Piece selectedPiece = pieces[previouslyClickedPoint.x][previouslyClickedPoint.y];
            if (selectedPiece == null) {
                validMovesToDraw.clear();
                return;
            }
            selectedPiece.move(pieces, clickedCoordinate);
            turnCount++;
            whitesTurn = !whitesTurn;

            final ArrayList<Point> allValidMoves = getMoves(whitesTurn);
            final ArrayList<Point> opposingAttacks = getAttacks(!whitesTurn);

            final boolean weCanMove = !allValidMoves.isEmpty();
            boolean inSchack = false;

            for (Point attack : opposingAttacks) {
                final Piece attacked = pieces[attack.x][attack.y];
                if (attacked == null) {
                    continue;
                }
                if (attacked.supremeRuler) {
                    validMovesToDraw.clear();
                    getParent().repaint();
                    // Kolla ifall vi är i schackmatt
                    if (weCanMove) {
                        JOptionPane.showMessageDialog(this, "Du står i schack");
                    } else {
                        final int choise = JOptionPane.showConfirmDialog(this, "Schackmatt\nVill du starta om?");
                        if (choise == JOptionPane.YES_OPTION) {
                            try {
                                restartGame();
                            } catch (IOException ex) {
                                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    inSchack = true;
                }
            }
            if (!inSchack && !weCanMove) {
                JOptionPane.showMessageDialog(this, "Patt");
            }

        } else {
            previouslyClickedPoint = new Point(clickedCoordinate);
            validMovesToDraw.clear();
        }

        // Om vi inte redan har valt en pjäs klickar vi på en pjäs
        if (!validMovesToDraw.contains(clickedCoordinate)) {
            final Piece selectedPiece = pieces[mouseCoordinateX][mouseCoordinateY];

            if (selectedPiece != null && selectedPiece.isWhite() == whitesTurn) {
                validMovesToDraw.addAll(selectedPiece.validMoves(pieces, true));
            } else {
                validMovesToDraw.clear();
            }
        } else {
            validMovesToDraw.clear();
        }

        getParent().repaint();
    }

    private ArrayList<Point> getMoves(boolean whiteMovesAreWanted) {
        final ArrayList<Point> allValidMoves = new ArrayList<>();
        for (Piece[] pieceArr : pieces) {
            for (Piece piece : pieceArr) {
                if (piece == null || whiteMovesAreWanted != piece.isWhite()) {
                    continue;
                }
                allValidMoves.addAll(piece.validMoves(pieces, true));
            }
        }
        return allValidMoves;
    }

    public ArrayList<Point> getAttacks(boolean whiteAttacksAreWanted) {
        final ArrayList attacks = new ArrayList();
        for (Piece[] pieceArr : pieces) {
            for (Piece piece : pieceArr) {
                if (piece == null || whiteAttacksAreWanted != piece.isWhite()) {
                    continue;
                }
                attacks.addAll(piece.validAttacks(pieces, true));
            }
        }
        return attacks;
    }

    public boolean isWhitesTurn() {
        return whitesTurn;
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

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }
}
