package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.LinkedHashSet;

public class Rook extends LongWalkers {

    public Rook(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
        setPieceIcon("Rook");
    }

    @Override
    public LinkedHashSet<Point> validMoves(Piece[][] pieces, boolean isSelected) {
        return getMoves(
                new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}, 
                pieces, 
                isSelected
        );

    }

}
