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
        final ArrayList<Point> possibleCastling = new ArrayList<>();
        if (this.isMoved()) {
            return possibleCastling;
        }

        boolean[] somethingBetweenOrSchackOnTheWay = new boolean[2]; // Vänster, höger
        final int LEFT_MODIFIER = -1, RIGHT_MODIFIER = 1;
        for (final int modifier : new int[]{LEFT_MODIFIER, RIGHT_MODIFIER}) {
            for (int loopX = this.position.x + modifier; loopX > 0 && loopX < 7; loopX += modifier) {
                if (pieces[loopX][this.position.y] != null || isInSchack(pieces, new Point(loopX, this.position.y))) {
                    somethingBetweenOrSchackOnTheWay[(modifier == LEFT_MODIFIER) ? 0 : 1] = true;
                    break;
                }
            }
        }
        final int LEFT_DIRECTION = 0, RIGHT_DIRECTION = 1;
        for (final int direction : new int[]{LEFT_DIRECTION, RIGHT_DIRECTION}) {
            if (!somethingBetweenOrSchackOnTheWay[direction]) {
                final Piece possibleRook = pieces[direction == LEFT_DIRECTION ? 0 : 7][this.position.y];
                if (possibleRook != null && !possibleRook.isMoved()) {
                    possibleCastling.add(new Point(direction == LEFT_DIRECTION ? 2 : 6, this.position.y));
                }
            }
        }
        /*
        // Vänster
        boolean nothingInBetweenAndNotSchackOnTheWay = true;
        for (int loopX = this.position.x - 1; loopX > 0; loopX--) {
            if (pieces[loopX][this.position.y] != null || isInSchack(pieces, new Point(loopX, this.position.y))) {
                nothingInBetweenAndNotSchackOnTheWay = false;
                break;
            }
        }
        if (nothingInBetweenAndNotSchackOnTheWay) {
            Piece possibleRook = pieces[0][this.position.y];
            if (possibleRook != null && !possibleRook.isMoved()) {
                possibleCastling.add(new Point(2, this.position.y));
            }
        }

        // Höger
        nothingInBetweenAndNotSchackOnTheWay = true;
        for (int loopX = this.position.x + 1; loopX < 7; loopX++) {
            if (pieces[loopX][this.position.y] != null || isInSchack(pieces, new Point(loopX, this.position.y))) {
                nothingInBetweenAndNotSchackOnTheWay = false;
                break;
            }
        }
        if (nothingInBetweenAndNotSchackOnTheWay) {
            Piece possibleRook = pieces[7][this.position.y];
            if (possibleRook != null && !possibleRook.isMoved()) {
                possibleCastling.add(new Point(6, this.position.y));
            }
        }
         */
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
