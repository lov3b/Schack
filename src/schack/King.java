package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import javax.swing.text.Position;

public final class King extends PieceKnownIfMoved {

    public King(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
        setPieceIcon("King");
    }

    public boolean isSeen(ArrayList<Piece> pieces) {
        return true;
    }

    private void addCastlingIfCan(Piece[][] pieces, LinkedHashSet<Point> movable, Point toMove, Point selected) {
        if (hasMoved) {
            return;
        }

        // Vänster
        boolean nothingInBetween = true;
        for (int loopX = this.position.x - 1; loopX >= 0; loopX--) {

            // Kolla ifall vi kollar tornet och inget är emellan
            if (loopX == 0 && nothingInBetween) {
                movable.add(new Point(2, this.position.y));
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
                movable.add(new Point(6, this.position.y));
            }

            // Kolla ifall det är tomt emellan kung och torn
            if (pieces[loopX][this.position.y] != null) {
                nothingInBetween = false;
            }
        }

    }

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
    public void move(Piece[][] pieces, Point toMove, Point selected) {
        if (Math.abs(selected.x - toMove.x) == 2) {
            final boolean goToLeftSide = (toMove.x < 5) ? true : false;
            castle(pieces, goToLeftSide);
        } else {
            super.move(pieces, toMove, selected);
        }

    }

    @Override
    public LinkedHashSet<Point> validMoves(Piece[][] pieces) {
        LinkedHashSet<Point> movable = new LinkedHashSet<>();

        for (int loopX = -1; loopX < 2; loopX++) {
            for (int loopY = -1; loopY < 2; loopY++) {
                if (loopY == 0 && loopX == 0) {
                    continue;
                }
                addMovesIfCan(new Point(this.position.x + loopX, this.position.y + loopY), movable, pieces);
            }

        }
        addCastlingIfCan(pieces, movable, position, position);
        return movable;
    }

    @Override
    public String toString() {
        return "Piece{" + "hasMoved=" + hasMoved + "position=" + position + ", isWhite=" + isWhite + '}';
    }

}
