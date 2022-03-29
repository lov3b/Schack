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

    public King(boolean isWhite) throws IOException {
        super(isWhite, isWhite ? new Point(4, 7) : new Point(4, 0));
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
                boolean shouldBreak = addMovesIfCan(new Point(this.position.x + loopX, this.position.y + loopY), movable, pieces);
                if (shouldBreak) {
                    break;
                }

            }

        }
        return movable;
    }

    @Override
    public String toString() {
        return "Piece{" + "hasMoved=" + hasMoved + "position=" + position + ", isWhite=" + isWhite + '}';
    }

}
