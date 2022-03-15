package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bishop extends Piece {

    public Bishop(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
        setPieceIcon("Bishop");
    }

    @Override
    public LinkedHashSet<Point> validMoves(Piece[][] pieces) {
        LinkedHashSet<Point> movable = new LinkedHashSet<>();

        // Upp vänster
        for (int bishopX = this.position.x - 1, bishopY = this.position.y - 1; bishopX >= 0 && bishopY >= 0; bishopX--, bishopY--) {

            Point pos = new Point(bishopX, bishopY);

            // Instead of checking index and null, try-catch
            try {
                Piece p = pieces[pos.x][pos.y];
                System.out.println(p);
                // If this piece is the same team as ours, skip
                if (p.isWhite == this.isWhite) {
                    break;
                }

                movable.add(pos);
                break;

            } catch (NullPointerException npe) {
                // This is an empty spot
                movable.add(pos);
            } catch (Exception e) {
                // This means that the player is at the edge
            }

        }

        // Upp höger 
        for (int bishopX = this.position.x + 1, bishopY = this.position.y - 1; bishopX <= 7 && bishopY >= 0; bishopX++, bishopY--) {

            Point pos = new Point(bishopX, bishopY);

            // Instead of checking index and null, try-catch
            try {
                Piece p = pieces[pos.x][pos.y];
                System.out.println(p);
                // If this piece is the same team as ours, skip
                if (p.isWhite == this.isWhite) {
                    break;
                }

                movable.add(pos);
                break;

            } catch (NullPointerException npe) {
                // This is an empty spot
                movable.add(pos);
            } catch (Exception e) {
                // This means that the player is at the edge
            }

        }
        // Ner höger
        for (int bishopX = this.position.x + 1, bishopY = this.position.y + 1; bishopX <= 7 && bishopY <= 7; bishopX++, bishopY++) {

            Point pos = new Point(bishopX, bishopY);

            // Instead of checking index and null, try-catch
            try {
                Piece p = pieces[pos.x][pos.y];
                System.out.println(p);
                // If this piece is the same team as ours, skip
                if (p.isWhite == this.isWhite) {
                    break;
                }

                movable.add(pos);
                break;

            } catch (NullPointerException npe) {
                // This is an empty spot
                movable.add(pos);
            } catch (Exception e) {
                // This means that the player is at the edge
            }

        }
        // Ner vänster
        for (int bishopX = this.position.x - 1, bishopY = this.position.y + 1; bishopX >= 0 && bishopY <= 7; bishopX--, bishopY++) {

            Point pos = new Point(bishopX, bishopY);

            // Instead of checking index and null, try-catch
            try {
                Piece p = pieces[pos.x][pos.y];
                System.out.println(p);
                // If this piece is the same team as ours, skip
                if (p.isWhite == this.isWhite) {
                    break;
                }

                movable.add(pos);
                break;

            } catch (NullPointerException npe) {
                // This is an empty spot
                movable.add(pos);
            } catch (Exception e) {
                // This means that the player is at the edge
            }

        }
        return movable;
    }

    @Override
    public String toString() {
        return "Bishop{" + "position=" + position + ", isWhite=" + isWhite + '}';
    }
}
