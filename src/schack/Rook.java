package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.LinkedHashSet;

public class Rook extends Piece {

    public Rook(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
        setPieceIcon("Rook");
    }

    @Override
    public LinkedHashSet<Point> validMoves(Piece[][] pieces) {
        return new LinkedHashSet<>();
    }

    
}
