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
    public LinkedHashSet<Point> validMoves(Piece[][] pieces, boolean isSelected) {
        LinkedHashSet<Point> movable = new LinkedHashSet<>();

        // Upp vänster
        for (int loopX = this.position.x - 1, loopY = this.position.y - 1; loopX >= 0 && loopY >= 0; loopX--, loopY--) {
            boolean shouldBreak = addMovesIfCan(new Point(loopX, loopY), movable, pieces, isSelected);
            if (shouldBreak) {
                break;
            }

        }

        // Upp höger 
        for (int loopX = this.position.x + 1, loopY = this.position.y - 1; loopX <= 7 && loopY >= 0; loopX++, loopY--) {
            boolean shouldBreak = addMovesIfCan(new Point(loopX, loopY), movable, pieces, isSelected);
            if (shouldBreak) {
                break;
            }

        }
        // Ner höger
        for (int loopX = this.position.x + 1, loopY = this.position.y + 1; loopX <= 7 && loopY <= 7; loopX++, loopY++) {
            boolean shouldBreak = addMovesIfCan(new Point(loopX, loopY), movable, pieces, isSelected);
            if (shouldBreak) {
                break;
            }

        }
        // Ner vänster
        for (int loopX = this.position.x - 1, loopY = this.position.y + 1; loopX >= 0 && loopY <= 7; loopX--, loopY++) {
            boolean shouldBreak = addMovesIfCan(new Point(loopX, loopY), movable, pieces, isSelected);
            if (shouldBreak) {
                break;
            }

        }
        return movable;
    }

    @Override
    public String toString() {
        return "Bishop{" + "position=" + position + ", isWhite=" + white + '}';
    }
}
