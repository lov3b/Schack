package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.LinkedHashSet;

public class Horse extends Piece {

    public Horse(boolean isWhite, boolean isLeft, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
        setPieceIcon("Horse");
    }

    @Override
    public LinkedHashSet<Point> validMoves(Piece[][] pieces) {
        return new LinkedHashSet<>();
    }

   

}
