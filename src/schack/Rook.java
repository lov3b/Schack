package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Rook extends Piece {

    public Rook(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
        setPieceIcon("Rook");
    }

    @Override
    public LinkedHashSet<Point> validMoves(Piece[][] pieces) {
        LinkedHashSet<Point> movable = new LinkedHashSet<>();
//Behöver skriva att om rookX = this.position.x så ska vi istället loopa igenom 
//int rookY = 0-this.position.y; rookY < 8-this.position.Y; rookY++
//men jag är trög och har spenderat alldles förmycket tid på att vara trög :^)

        // Vänster
        for (int rookX = this.position.x - 1; rookX >= 0; rookX--) {
            boolean shouldBreak = checkMove(rookX, this.position.y, movable, pieces);
            if (shouldBreak) {
                break;
            }
        }

        // Höger
        for (int rookX = this.position.x + 1; rookX <= 7; rookX++) {
            boolean shouldBreak = checkMove(rookX, this.position.y, movable, pieces);
            if (shouldBreak) {
                break;
            }
        }

        // Ner
        for (int rookY = this.position.y + 1; rookY <= 7; rookY++) {
            boolean shouldBreak = checkMove(this.position.x, rookY, movable, pieces);
            if (shouldBreak) {
                break;
            }
        }

        // Upp
        for (int rookY = this.position.y - 1; rookY >= 0; rookY--) {
            boolean shouldBreak = checkMove(this.position.x, rookY, movable, pieces);
            if (shouldBreak) {
                break;
            }
        }
        System.out.println("Len of movable: " + movable.size());
        return movable;

    }

  

}
