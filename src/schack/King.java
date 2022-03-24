package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public final class King extends PieceKnownIfMoved {

    public King(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
        setPieceIcon("King");
    }

    public King(boolean isWhite) throws IOException {
        super(isWhite, isWhite ? new Point(4, 7) : new Point(4, 0));
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
        for (int kingX = this.position.x - 1; kingX >= 0; kingX--) {
            if (kingX == 0) {
                try {

                } catch (Exception e) {
                }
            }

            boolean shouldBreak = addMovesIfCan(new Point(kingX, this.position.y), movable, pieces);
            if (shouldBreak) {
                break;
            }
        }

        // Höger
        for (int kingX = this.position.x + 1; kingX <= 7; kingX++) {
            boolean shouldBreak = addMovesIfCan(new Point(kingX, this.position.y), movable, pieces);
            if (shouldBreak) {
                break;
            }
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
                Point pos = new Point(this.position.x + loopX, this.position.y + loopY);

                // Instead of checking index and null, try-catch
                try {
                    Piece p = pieces[pos.x][pos.y];
                    System.out.println(p);
                    // If this piece is the same team as ours, skip
                    if (p.isWhite == this.isWhite) {
                        continue;
                    }
                    movable.add(pos);

                } catch (NullPointerException npe) {
                    // This is an empty spot
                    movable.add(pos);
                } catch (Exception e) {
                    // This means that the player is at the edge
                }
            }

        }
        return movable;

    }

    @Override
    public String toString() {
        return "Piece{" + "hasMoved=" + hasMoved + "position=" + position + ", isWhite=" + isWhite + '}';
    }

}
