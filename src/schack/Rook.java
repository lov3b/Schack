package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

public class Rook extends LongWalkers {

    public Rook(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
    }

    @Override
    public ArrayList<Point> validMoves(Piece[][] pieces, boolean allowedToRecurse) {
        return getMoves(
                new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}},
                pieces,
                allowedToRecurse
        );

    }

}
