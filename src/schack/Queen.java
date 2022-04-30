package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

public class Queen extends LongWalkers {

    public Queen(boolean isWhite, Point point) throws IOException {
        super(isWhite, point);
    }

    @Override
    public ArrayList<Point> validMoves(Piece[][] pieces, boolean isSelected) {
        return getMoves(
                new int[][]{{1, 0}, {-1, 0}, {0, 1}, {-1, -1}, {0, -1}, {1, 1}, {-1, 1}, {1, -1}},
                pieces,
                isSelected
        );
    }
}
