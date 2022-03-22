package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.LinkedHashSet;

public class Queen extends Piece {

    public Queen(boolean isWhite, Point point) throws IOException {
        super(isWhite, point);
        setPieceIcon("Queen");
    }

    @Override
    public LinkedHashSet<Point> validMoves(Piece[][] pieces) {
        LinkedHashSet<Point> movable = new LinkedHashSet<>();

        // Vänster
        for (int rookX = this.position.x - 1; rookX >= 0; rookX--) {
            boolean shouldBreak = checkMove(new Point(rookX, this.position.y), movable, pieces);
            if (shouldBreak) {
                break;
            }
        }

        // Höger
        for (int rookX = this.position.x + 1; rookX <= 7; rookX++) {
            boolean shouldBreak = checkMove(new Point(rookX, this.position.y), movable, pieces);
            if (shouldBreak) {
                break;
            }
        }

        // Ner
        for (int rookY = this.position.y + 1; rookY <= 7; rookY++) {
            boolean shouldBreak = checkMove(new Point(this.position.x, rookY), movable, pieces);
            if (shouldBreak) {
                break;
            }
        }

        // Upp
        for (int rookY = this.position.y - 1; rookY >= 0; rookY--) {
            boolean shouldBreak = checkMove(new Point(this.position.x, rookY), movable, pieces);
            if (shouldBreak) {
                break;
            }
        }
        // Upp vänster
        for (int bishopX = this.position.x - 1, bishopY = this.position.y - 1; bishopX >= 0 && bishopY >= 0; bishopX--, bishopY--) {
            boolean shouldBreak = checkMove(new Point(bishopX, bishopY), movable, pieces);
            if (shouldBreak) {
                break;
            }

        }

        // Upp höger 
        for (int bishopX = this.position.x + 1, bishopY = this.position.y - 1; bishopX <= 7 && bishopY >= 0; bishopX++, bishopY--) {
            boolean shouldBreak = checkMove(new Point(bishopX, bishopY), movable, pieces);
            if (shouldBreak) {
                break;
            }

        }
        // Ner höger
        for (int bishopX = this.position.x + 1, bishopY = this.position.y + 1; bishopX <= 7 && bishopY <= 7; bishopX++, bishopY++) {
            boolean shouldBreak = checkMove(new Point(bishopX, bishopY), movable, pieces);
            if (shouldBreak) {
                break;
            }

        }
        // Ner vänster
        for (int bishopX = this.position.x - 1, bishopY = this.position.y + 1; bishopX >= 0 && bishopY <= 7; bishopX--, bishopY++) {
            boolean shouldBreak = checkMove(new Point(bishopX, bishopY), movable, pieces);
            if (shouldBreak) {
                break;
            }

        }
        return movable;
    }
}
