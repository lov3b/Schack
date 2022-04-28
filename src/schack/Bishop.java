package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.LinkedHashSet;

public class Bishop extends LongWalkers {

    public Bishop(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
        setPieceIcon("Bishop");
    }

    @Override
    public LinkedHashSet<Point> validMoves(Piece[][] pieces, boolean isSelected) {
        return getMoves(
                new int[][]{{-1,-1},{1, 1}, {-1, 1}, {1, -1}},
                pieces,
                isSelected
        );
    }

    @Override
    public String toString() {
        return "Bishop{" + "position=" + position + ", isWhite=" + white + '}';
    }
}
