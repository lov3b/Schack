package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

public class Pawn extends PieceKnownIfMoved {

    public Pawn(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
    }

    @Override
    public ArrayList<Point> validAttacks(Piece[][] pieces, boolean shouldNotCareIfAttackSpaceIsEmptyOrNot) {
        ArrayList<Point> movable = new ArrayList<>();

        // Kolla ifall vi kan ta någon
        for (int pawnX : new int[]{-1, 1}) {
            // Position vi kollar just nu, snett upp åt höger & vänster
            Point pos = new Point(this.position.x + pawnX, this.position.y + (this.isWhite() ? -1 : 1));
            if (pos.x < 0 || pos.x > 7 || pos.y < 0 || pos.y > 7) {
                continue;
            }
            Piece piece = pieces[pos.x][pos.y];
            if (piece == null || piece.isWhite() != this.isWhite()
                    || (shouldNotCareIfAttackSpaceIsEmptyOrNot && piece.isWhite() != this.isWhite())) {
                movable.add(pos);
            }
        }

        return movable;
    }

    @Override
    public ArrayList<Point> validMoves(Piece[][] pieces, boolean isSelected) {
        ArrayList<Point> movable = new ArrayList<>();

        // Om bonden har gått en gång, får gå 1 steg, annars 2
        final int upTo = this.isMoved() ? 1 : 2;

        // Kolla om man kan gå rakt frak
        for (int pawnDY = 1; pawnDY <= upTo; pawnDY++) {
            Point pos = new Point(this.position.x, this.position.y + (this.isWhite() ? -pawnDY : pawnDY));
            boolean shouldBreak = addMovesIfCan(pos, movable, pieces, isSelected);
            if (shouldBreak) {
                break;
            }
        }

        // Kolla ifall vi kan ta någon
        for (int pawnX : new int[]{-1, 1}) {
            // Position vi kollar just nu, snett upp åt höger & vänster
            Point pos = new Point(this.position.x + pawnX, this.position.y + (this.isWhite() ? -1 : 1));
            movable.addAll(addAttackMovesIfCan(pos, pieces));
        }
        return movable;
    }

    // Känns som det här skulle kunnat vara i validMoves, men nu är det såhär
    /**
     * Ifall det är en pjäs som står här och den inte är samma färg som oss,
     * lägg till i listan
     *
     * @param pos
     * @param movable
     * @param pieces
     */
    private ArrayList<Point> addAttackMovesIfCan(Point pos, Piece[][] pieces) {
        ArrayList movable = new ArrayList<Point>();
        // Se till att vi inte är utanför brädet
        if (pos.x >= pieces.length || pos.x < 0 || pos.y >= pieces[0].length || pos.y < 0) {
            return movable;
        }

        Piece piece = pieces[pos.x][pos.y];
        // Ifall det är tomt här, gör ingenting
        if (piece != null && piece.isWhite() != this.isWhite()) {
            if (!isInSchackHere(pieces, pos)) {
                movable.add(pos);
            }
        }
        return movable;
    }

    @Override
    protected boolean addMovesIfCan(Point pos, ArrayList movable, Piece[][] pieces, boolean isSelected) {
        if (pos.x < 0 || pos.x > 7 || pos.y < 0 || pos.y > 7) {
            return false;
        }

        Piece pieceToCheck = pieces[pos.x][pos.y];
        if (pieceToCheck == null) {
            if (!isInSchackHere(pieces, pos)) {
                movable.add(pos);
            }
            return false;
        }
        return true;

    }
}
