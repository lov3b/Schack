package schack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JPanel;

public class Board extends JPanel {

    public static final int SCALE = 100;
    ArrayList<Piece> pieces = new ArrayList<>();

    public Board() throws IOException {

        this.pieces = initPieces();
        setPreferredSize(new Dimension(800, 800));

    }

    private ArrayList<Piece> initPieces() throws IOException {
        // White pieces
        ArrayList<Piece> pieces = new ArrayList<>();
        ArrayList<Piece> whites = (ArrayList) Stream.of(
                new King(true, new Point(4, 7))
        ).collect(Collectors.toList());

        // Black pieces
        ArrayList<Piece> blacks = (ArrayList) Stream.of(
                new King(false, new Point(4, 0))
        ).collect(Collectors.toList());

        pieces.addAll(whites);
        pieces.addAll(blacks);

        return pieces;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        drawSquares(g2);

        pieces.forEach(p -> p.draw(g2));

        Piece p = pieces.get(1);
        LinkedHashSet<Point> legal = p.legalMoves(pieces);
        g2.setColor(Color.yellow);
        legal.forEach(point -> g2.fillOval(point.x * SCALE, point.y * SCALE, SCALE, SCALE));

        System.out.println(legal.size());
    }

    private void drawSquares(Graphics2D g2) {

        g2.setBackground(Color.WHITE);
        g2.setColor(Color.DARK_GRAY);

        for (int i = 0; i < 8; i += 2) {
            for (int j = 0; j < 8; j += 2) {
                g2.fillRect(i * SCALE, j * SCALE, 1 * SCALE, 1 * SCALE);
            }
        }

        for (int i = 1; i < 8; i += 2) {
            for (int j = 1; j < 8; j += 2) {
                g2.fillRect(i * SCALE, j * SCALE, 1 * SCALE, 1 * SCALE);
            }
        }

    }
}
