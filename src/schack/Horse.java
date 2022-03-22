package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.LinkedHashSet;

public class Horse extends Piece {

    public Horse(boolean isWhite, boolean isLeft, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
        setPieceIcon("Horse");
    }

    @Override
    public LinkedHashSet<Point> validMoves(Piece[][] pieces) {
        LinkedHashSet<Point> movable = new LinkedHashSet<>();

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
            checkMove(pos.x, pos.y, movable, pieces);
        }

        return movable;

    }

}
