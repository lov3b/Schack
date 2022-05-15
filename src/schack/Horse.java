package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

public class Horse extends Piece {

    public Horse(boolean isWhite, boolean isLeft, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
    }

    @Override
    public ArrayList<Point> validMoves(Piece[][] pieces, boolean allowedToRecurse) {
        ArrayList<Point> movable = new ArrayList<>();

        for (int dx : new int[]{-2, -1, 1, 2}) {
            for (int direction : new int[]{-1, 1}) {
                int stepLength = (3 - Math.abs(dx));
                int dy = direction * stepLength;
                Point potentialMove = new Point(this.position.x + dx, this.position.y + dy);
                addMovesIfCan(potentialMove, movable, pieces, allowedToRecurse);
            }
        }
        return movable;
    }

}
