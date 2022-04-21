package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.LinkedHashSet;

public class Rook extends PieceKnownIfMoved {

    public Rook(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
        setPieceIcon("Rook");
    }

    @Override
    public LinkedHashSet<Point> validMoves(Piece[][] pieces, boolean isSelected) {
        LinkedHashSet<Point> movable = new LinkedHashSet<>();
//Behöver skriva att om rookX = this.position.x så ska vi istället loopa igenom 
//int rookY = 0-this.position.y; rookY < 8-this.position.Y; rookY++
//men jag är trög och har spenderat alldles förmycket tid på att vara trög :^)

        // Vänster
        for (int loopX = this.position.x - 1; loopX >= 0; loopX--) {
            boolean shouldBreak = addMovesIfCan(new Point(loopX, this.position.y), movable, pieces, isSelected);
            if (shouldBreak) {
                break;
            }
        }

        // Höger
        for (int loopX = this.position.x + 1; loopX <= 7; loopX++) {
            boolean shouldBreak = addMovesIfCan(new Point(loopX, this.position.y), movable, pieces, isSelected);
            if (shouldBreak) {
                break;
            }
        }

        // Ner
        for (int loopY = this.position.y + 1; loopY <= 7; loopY++) {
            boolean shouldBreak = addMovesIfCan(new Point(this.position.x, loopY), movable, pieces, isSelected);
            if (shouldBreak) {
                break;
            }
        }

        // Upp
        for (int loopY = this.position.y - 1; loopY >= 0; loopY--) {
            boolean shouldBreak = addMovesIfCan(new Point(this.position.x, loopY), movable, pieces, isSelected);
            if (shouldBreak) {
                break;
            }
        }
        return movable;

    }

}
