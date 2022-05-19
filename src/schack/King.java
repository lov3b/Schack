package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

public final class King extends Piece {

    public King(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
        supremeRuler = true;
    }

    /**
     * Få en ArrayList med möjliga rockader
     *
     * @param pieces
     * @return
     */
    private ArrayList<Point> getCastlingIfPossible(Piece[][] pieces) {
        ArrayList<Point> possibleCastling = new ArrayList<>();
        if (this.isMoved()) {
            return possibleCastling;
        }

        boolean[] somethingBetweenOrSchackOnTheWay = new boolean[2]; // Vänster, höger
        int left_modifier = -1, right_modifier = 1;
        for (int modifier : new int[]{left_modifier, right_modifier}) {
            for (int loopX = this.position.x + modifier; loopX > 0 && loopX < 7; loopX += modifier) {
                if (pieces[loopX][this.position.y] != null || isInSchack(pieces, new Point(loopX, this.position.y))) {
                    somethingBetweenOrSchackOnTheWay[(modifier == left_modifier) ? 0 : 1] = true;
                    break;
                }
            }
        }
        left_modifier = 0;
        right_modifier = 1;
        for (int direction : new int[]{left_modifier, right_modifier}) {
            if (!somethingBetweenOrSchackOnTheWay[direction]) {
                Piece possibleRook = pieces[direction == left_modifier ? 0 : 7][this.position.y];
                if (possibleRook != null && !possibleRook.isMoved()) {
                    possibleCastling.add(new Point(direction == left_modifier ? 2 : 6, this.position.y));
                }
            }
        }
        return possibleCastling;
    }

    /**
     * Gör en rockad
     *
     * @param pieces
     * @param shouldGoToLeftSide avgör ifall rockaden är åt vänster håll
     */
    private void castle(Piece[][] pieces, boolean shouldGoToLeftSide) {
        final Piece rook = pieces[shouldGoToLeftSide ? 0 : 7][this.position.y];
        final Piece king = this;

        // Null där de stod
        pieces[king.position.x][king.position.y] = null;
        pieces[rook.position.x][rook.position.y] = null;
        // Uppdatera internt minne
        king.position.x = shouldGoToLeftSide ? 2 : 6;
        rook.position.x = shouldGoToLeftSide ? 3 : 5;
        // Uppdatera brädet
        pieces[king.position.x][king.position.y] = king;
        pieces[rook.position.x][rook.position.y] = rook;
    }

    @Override
    public void move(Piece[][] pieces, Point toMove) {
        if (Math.abs(position.x - toMove.x) == 2) {
            final boolean goToLeftSide = toMove.x < 5;
            castle(pieces, goToLeftSide);
        } else {
            super.move(pieces, toMove);
        }

    }

    @Override
    public ArrayList<Point> validMoves(Piece[][] pieces, boolean allowedToRecurse) {
        ArrayList<Point> movable = new ArrayList<>();

        for (int loopX = -1; loopX < 2; loopX++) {
            for (int loopY = -1; loopY < 2; loopY++) {
                if (loopY == 0 && loopX == 0) {
                    continue;
                }
                addMovesIfCan(new Point(this.position.x + loopX, this.position.y + loopY), movable, pieces, allowedToRecurse);
            }

        }
        if (allowedToRecurse && !isInSchack(pieces)) {
            movable.addAll(getCastlingIfPossible(pieces));
        }
        return movable;
    }

}
