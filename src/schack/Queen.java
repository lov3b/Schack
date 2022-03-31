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
        for (int loopX = this.position.x - 1; loopX >= 0; loopX--) {
            boolean shouldBreak = addMovesIfCan(new Point(loopX, this.position.y), movable, pieces);
            if (shouldBreak) {
                break;
            }
        }

        // Höger
        for (int loopX = this.position.x + 1; loopX <= 7; loopX++) {
            boolean shouldBreak = addMovesIfCan(new Point(loopX, this.position.y), movable, pieces);
            if (shouldBreak) {
                break;
            }
        }

        // Ner
        for (int loopY = this.position.y + 1; loopY <= 7; loopY++) {
            boolean shouldBreak = addMovesIfCan(new Point(this.position.x, loopY), movable, pieces);
            if (shouldBreak) {
                break;
            }
        }

        // Upp
        for (int loopY = this.position.y - 1; loopY >= 0; loopY--) {
            boolean shouldBreak = addMovesIfCan(new Point(this.position.x, loopY), movable, pieces);
            if (shouldBreak) {
                break;
            }
        }
        // Upp vänster
        for (int loopX = this.position.x - 1, loopY = this.position.y - 1; loopX >= 0 && loopY >= 0; loopX--, loopY--) {
            boolean shouldBreak = addMovesIfCan(new Point(loopX, loopY), movable, pieces);
            if (shouldBreak) {
                break;
            }

        }

        // Upp höger 
        for (int loopX = this.position.x + 1, loopY = this.position.y - 1; loopX <= 7 && loopY >= 0; loopX++, loopY--) {
            boolean shouldBreak = addMovesIfCan(new Point(loopX, loopY), movable, pieces);
            if (shouldBreak) {
                break;
            }

        }
        // Ner höger
        for (int loopX = this.position.x + 1, loopY = this.position.y + 1; loopX <= 7 && loopY <= 7; loopX++, loopY++) {
            boolean shouldBreak = addMovesIfCan(new Point(loopX, loopY), movable, pieces);
            if (shouldBreak) {
                break;
            }

        }
        // Ner vänster
        for (int loopX = this.position.x - 1, loopY = this.position.y + 1; loopX >= 0 && loopY <= 7; loopX--, loopY++) {
            boolean shouldBreak = addMovesIfCan(new Point(loopX, loopY), movable, pieces);
            if (shouldBreak) {
                break;
            }

        }
        return movable;
    }
}
