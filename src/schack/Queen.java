package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.LinkedHashSet;

public class Queen extends LongWalkers {

    public Queen(boolean isWhite, Point point) throws IOException {
        super(isWhite, point);
        setPieceIcon("Queen");
    }

    @Override
    public LinkedHashSet<Point> validMoves(Piece[][] pieces, boolean isSelected) {
        return getMoves(
                new int[][]{{1, 0}, {-1, 0}, {0, 1}, {-1, -1}, {0, -1}, {1, 1}, {-1, 1}, {1, -1}},
                pieces,
                isSelected
        );
    }
}
