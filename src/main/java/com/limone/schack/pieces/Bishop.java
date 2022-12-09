package com.limone.schack.pieces;

import java.awt.Point;
import java.io.IOException;
import java.util.List;

public class Bishop extends LongWalkers {

    public Bishop(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
    }

    @Override
    public List<Point> validMoves(Piece[][] pieces, boolean allowedToRecurse) {
        return getMoves(
                new int[][] { { -1, -1 }, { 1, 1 }, { -1, 1 }, { 1, -1 } },
                pieces,
                allowedToRecurse);
    }

}
