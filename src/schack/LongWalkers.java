package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

public abstract class LongWalkers extends PieceKnownIfMoved {

    public LongWalkers(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
    }

    ArrayList<Point> getMoves(int[][] directions, Piece[][] pieces, boolean isSelected) {
        ArrayList<Point> movable = new ArrayList<>();

        for (int[] xy : directions) {
            int loopX = this.position.x, loopY = this.position.y;
            while (loopX + xy[0] >= 0 && loopX + xy[0] <= 7 && loopY + xy[1] >= 0 && loopY + xy[1] <= 7) {
                loopX += xy[0];
                loopY += xy[1];
                boolean shouldBreak = addMovesIfCan(new Point(loopX, loopY), movable, pieces, isSelected);

                if (shouldBreak) {
                    break;
                }
            }
        }

        return movable;
    }

}
