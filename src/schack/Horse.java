package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Horse extends Piece {

    public Horse(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
    }

    @Override
    public List<Point> validMoves(Piece[][] pieces, boolean allowedToRecurse) {
        List<Point> movable = new ArrayList<>();

        for (int dx : new int[]{-2, -1, 1, 2}) {
            for (int direction : new int[]{-1, 1}) {
                int stepLength = (3 - Math.abs(dx)),
                        dy = direction * stepLength;
                Point potentialMove = new Point(this.position.x + dx, this.position.y + dy);
                addMovesIfCan(potentialMove, movable, pieces, allowedToRecurse);
            }
        }
        return movable;
    }

}
