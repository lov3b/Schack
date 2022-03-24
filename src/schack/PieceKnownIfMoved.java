package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

public abstract class PieceKnownIfMoved extends Piece {

    protected boolean hasMoved = false;

    public PieceKnownIfMoved(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
    }

    public boolean isSeen(ArrayList<Piece> pieces) {
        return true;
    }

    @Override
    public void move(Piece[][] pieces, Point toMove, Point selected) {
        super.move(pieces, toMove, selected);
        hasMoved = true;
    }

}
