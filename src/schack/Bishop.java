package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.LinkedHashSet;

public class Bishop extends Piece {

    public Bishop(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
        setPieceIcon("Bishop");
    }

    @Override
    public LinkedHashSet<Point> validMoves(Piece[][] pieces) {
        LinkedHashSet<Point> movable = new LinkedHashSet<>();

        // Upp vänster
        for (int bishopX = this.position.x - 1, bishopY = this.position.y - 1; bishopX >= 0 && bishopY >= 0; bishopX--, bishopY--) {
            boolean shouldBreak = addMovesIfCan(new Point(bishopX, bishopY), movable, pieces);
            if (shouldBreak) {
                break;
            }

        }

        // Upp höger 
        for (int bishopX = this.position.x + 1, bishopY = this.position.y - 1; bishopX <= 7 && bishopY >= 0; bishopX++, bishopY--) {
            boolean shouldBreak = addMovesIfCan(new Point(bishopX, bishopY), movable, pieces);
            if (shouldBreak) {
                break;
            }

        }
        // Ner höger
        for (int bishopX = this.position.x + 1, bishopY = this.position.y + 1; bishopX <= 7 && bishopY <= 7; bishopX++, bishopY++) {
            boolean shouldBreak = addMovesIfCan(new Point(bishopX, bishopY), movable, pieces);
            if (shouldBreak) {
                break;
            }

        }
        // Ner vänster
        for (int bishopX = this.position.x - 1, bishopY = this.position.y + 1; bishopX >= 0 && bishopY <= 7; bishopX--, bishopY++) {
            boolean shouldBreak = addMovesIfCan(new Point(bishopX, bishopY), movable, pieces);
            if (shouldBreak) {
                break;
            }

        }
        return movable;
    }

    @Override
    public String toString() {
        return "Bishop{" + "position=" + position + ", isWhite=" + isWhite + '}';
    }
}
