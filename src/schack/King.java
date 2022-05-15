package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

public final class King extends PieceKnownIfMoved {

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

        // Vänster
        boolean nothingInBetween = true;
        for (int loopX = this.position.x - 1; loopX >= 0; loopX--) {

            // Kolla ifall vi kollar tornet och inget är emellan
            if (loopX == 0 && nothingInBetween) {

                // Check så att man bara kan göra rockad ifall tornet inte rört sig
                Piece possibleRook = pieces[loopX][this.position.y];
                if (possibleRook != null && !possibleRook.isMoved()) {
                    possibleCastling.add(new Point(2, this.position.y));
                }
            }

            // Kolla ifall det är tomt emellan kung och torn
            if (pieces[loopX][this.position.y] != null) {
                nothingInBetween = false;
            }
        }

        // Höger
        nothingInBetween = true;
        for (int loopX = this.position.x + 1; loopX <= 7; loopX++) {

            // Kolla ifall vi kollar tornet och inget är emellan
            if (loopX == 7 && nothingInBetween) {
                // Check så att man bara kan göra rockad ifall tornet inte rört sig
                Piece possibleRook = pieces[loopX][this.position.y];
                if (possibleRook != null && !possibleRook.isMoved()) {
                    possibleCastling.add(new Point(6, this.position.y));
                }
            }

            // Kolla ifall det är tomt emellan kung och torn
            if (pieces[loopX][this.position.y] != null) {
                nothingInBetween = false;
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
            final boolean goToLeftSide = toMove.x < 5;
            castle(pieces, goToLeftSide);
        } else {
            super.move(pieces, toMove);
        }

    }

    @Override
    public ArrayList<Point> validMoves(Piece[][] pieces, boolean isSelected) {
        ArrayList<Point> movable = new ArrayList<>();

        for (int loopX = -1; loopX < 2; loopX++) {
            for (int loopY = -1; loopY < 2; loopY++) {
                if (loopY == 0 && loopX == 0) {
                    continue;
                }
                addMovesIfCan(new Point(this.position.x + loopX, this.position.y + loopY), movable, pieces, isSelected);
            }

        }
        movable.addAll(getCastlingIfPossible(pieces));
        return movable;
    }

}
