package com.billenius.schack.pieces;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class LongWalkers extends Piece {

    public LongWalkers(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
    }

    /**
     * Generell metod för att generera möjliga drag för LongWalkers
     *
     * @param directions       vilka håll. Exempel:
     * 
     *                         <pre>
     * {@code
     * new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } }
     * }</pre>
     * 
     *                         för att gå
     *                         som ett torn
     *
     * @param pieces
     * @param allowedToRecurse
     * @return
     */
    List<Point> getMoves(int[][] directions, Piece[][] pieces, boolean allowedToRecurse) {
        List<Point> movable = new ArrayList<>();

        for (int[] xy : directions) {
            int loopX = this.position.x, loopY = this.position.y;
            while (loopX + xy[0] >= 0 && loopX + xy[0] <= 7 && loopY + xy[1] >= 0 && loopY + xy[1] <= 7) {
                loopX += xy[0];
                loopY += xy[1];
                boolean shouldBreak = addMovesIfCan(new Point(loopX, loopY), movable, pieces, allowedToRecurse);
                if (shouldBreak) {
                    break;
                }
            }
        }

        return movable;
    }

}
