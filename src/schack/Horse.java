package schack;

import java.awt.Point;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.ArrayList;

public class Horse extends Piece {

    public Horse(boolean isWhite, boolean isLeft, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
    }

    @Override
    public ArrayList<Point> validMoves(Piece[][] pieces, boolean isSelected) {
        ArrayList<Point> movable = new ArrayList<>();

        // TODO: Integrera
        /*
        for (int dx : new int[]{-2, -1, 1, 2}) {
            for (int y = -1; y <= 1; y += 2) {
                int dy = y * (3 - abs(dx));
                
            }
        }
        */
        
        
        // Postitioner att checka
        Point[] positions = {
            // Snett höger uppåt
            new Point(this.position.x + 1, this.position.y - 2),
            // Snett höger neråt
            new Point(this.position.x + 1, this.position.y + 2),
            // Långt höger åt sidan uppåt
            new Point(this.position.x + 2, this.position.y - 1),
            // Långt höger åt sidan neråt
            new Point(this.position.x + 2, this.position.y + 1),
            // Snett vänster uppåt
            new Point(this.position.x - 1, this.position.y - 2),
            // Snett vänster neråt
            new Point(this.position.x - 1, this.position.y + 2),
            // Långt vänster åt sidan uppåt
            new Point(this.position.x - 2, this.position.y - 1),
            // Långt vänster åt sidan neråt
            new Point(this.position.x - 2, this.position.y + 1)
        };

        for (Point pos : positions) {
            // Ifall en är blockerad så ska vi inte sluta kolla
            addMovesIfCan(pos, movable, pieces, isSelected);
        }

        return movable;

    }

}
