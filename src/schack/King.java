package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public final class King extends PieceKnownIfMoved {

    public King(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
        setPieceIcon("King");
    }

  

    public boolean isSeen(ArrayList<Piece> pieces) {
        return true;
    }

    @Override
    public LinkedHashSet<Point> validMoves(Piece[][] pieces) {
        LinkedHashSet<Point> movable = new LinkedHashSet<>();

        for (int loopX = -1; loopX < 2; loopX++) {
            for (int loopY = -1; loopY < 2; loopY++) {
                if (loopY == 0 && loopX == 0) {
                    continue;
                }
             addMovesIfCan(new Point(this.position.x + loopX, this.position.y + loopY), movable, pieces);
            }

        }
        return movable;
    }

    @Override
    public String toString() {
        return "Piece{" + "hasMoved=" + hasMoved + "position=" + position + ", isWhite=" + isWhite + '}';
    }

}
