package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.List;

public class Queen extends LongWalkers {

    public Queen(boolean isWhite, Point point) throws IOException {
        super(isWhite, point);
    }

    @Override
    public List<Point> validMoves(Piece[][] pieces, boolean allowedToRecurse) {
        return getMoves(
                new int[][]{{1, 0}, {-1, 0}, {0, 1}, {-1, -1}, {0, -1}, {1, 1}, {-1, 1}, {1, -1}},
                pieces,
                allowedToRecurse
        );
    }
}
