package schack;

import java.awt.Point;
import java.io.IOException;

public abstract class PieceKnownIfMoved extends Piece {

    public PieceKnownIfMoved(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
        lastTurnMoved = -1;
    }

    @Override
    public void move(Piece[][] pieces, Point toMove) {
        super.move(pieces, toMove);
        lastTurnMoved++;
    }

    public boolean isMoved() {
        return lastTurnMoved > -1;
    }

}
