package schack;

import java.awt.Point;
import java.io.IOException;

public abstract class PieceKnownIfMoved extends Piece {

    protected boolean moved = false;

    public PieceKnownIfMoved(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
    }

    @Override
    public void move(Piece[][] pieces, Point toMove) {
        super.move(pieces, toMove);
        moved = true;
    }

    public boolean isMoved() {
        return moved;
    }

}
