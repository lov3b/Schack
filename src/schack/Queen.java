package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.LinkedHashSet;

public class Queen extends Piece {

    public Queen(boolean isWhite, Point point) throws IOException {
        super(isWhite,point);
        setPieceIcon("Queen");
    }

    @Override
    public LinkedHashSet<Point> validMoves(Piece[][] pieces) {
        return new LinkedHashSet<>();
    }

   
}
