package com.limone.schack.pieces;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private List<Point> getCastlingIfPossible(Piece[][] pieces) {
        List<Point> possibleCastling = new ArrayList<>();
        if (this.isMoved()) {
            return possibleCastling;
        }

        boolean[] somethingBetweenOrSchackOnTheWay = new boolean[2]; // Vänster, höger
        int leftModifier = -1, rightModifier = 1;
        for (int modifier : new int[] { leftModifier, rightModifier }) {
            for (int loopX = this.position.x + modifier; loopX > 0 && loopX < 7; loopX += modifier) {
                if (pieces[loopX][this.position.y] != null || isInSchack(pieces, new Point(loopX, this.position.y))) {
                    somethingBetweenOrSchackOnTheWay[(modifier == leftModifier) ? 0 : 1] = true;
                    break;
                }
            }
        }
        leftModifier = 0;
        rightModifier = 1;
        for (int direction : new int[] { leftModifier, rightModifier }) {
            if (!somethingBetweenOrSchackOnTheWay[direction]) {
                Piece possibleRook = pieces[direction == leftModifier ? 0 : 7][this.position.y];
                if (possibleRook != null && !possibleRook.isMoved()) {
                    possibleCastling.add(new Point(direction == leftModifier ? 2 : 6, this.position.y));
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
        Piece rook = pieces[shouldGoToLeftSide ? 0 : 7][this.position.y];
        Piece king = this;

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
            boolean goToLeftSide = toMove.x < 5;
            castle(pieces, goToLeftSide);
        } else {
            super.move(pieces, toMove);
        }
    }

    @Override
    public List<Point> validMoves(Piece[][] pieces, boolean allowedToRecurse) {
        List<Point> movable = new ArrayList<>();

        for (int loopX = -1; loopX < 2; loopX++) {
            for (int loopY = -1; loopY < 2; loopY++) {
                if (loopY == 0 && loopX == 0) {
                    continue;
                }
                addMovesIfCan(new Point(this.position.x + loopX, this.position.y + loopY), movable, pieces,
                        allowedToRecurse);
            }

        }
        if (allowedToRecurse && !isInSchack(pieces)) {
            movable.addAll(getCastlingIfPossible(pieces));
        }
        return movable;
    }

}
