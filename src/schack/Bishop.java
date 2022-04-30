package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

public class Bishop extends LongWalkers {

    public Bishop(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
    }

    @Override
    public ArrayList<Point> validMoves(Piece[][] pieces, boolean isSelected) {
        return getMoves(
                new int[][]{{-1, -1}, {1, 1}, {-1, 1}, {1, -1}},
                pieces,
                isSelected
        );
    }

}
