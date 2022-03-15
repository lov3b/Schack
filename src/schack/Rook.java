package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Rook extends Piece {

    public Rook(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
        setPieceIcon("Rook");
    }

    @Override
    public LinkedHashSet<Point> validMoves(Piece[][] pieces) {
        LinkedHashSet<Point> movable = new LinkedHashSet<>();
//Behöver skriva att om rookX = this.position.x så ska vi istället loopa igenom 
//int rookY = 0-this.position.y; rookY < 8-this.position.Y; rookY++
//men jag är trög och har spenderat alldles förmycket tid på att vara trög :^)

        // Vänster
        for (int rookX = this.position.x; rookX >= 0; rookX--) {
            Point pos = new Point(this.position.x - rookX, this.position.y);

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

        // Höger
        for (int rookX = 1; rookX <= this.position.x + rookX; rookX++) {
            Point pos = new Point(this.position.x + rookX, this.position.y);

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

//        for (int rookY = 0 - this.position.y; rookY < 8 - this.position.y; rookY++) {
//            if (this.position.y == 0 && rookY == 0) {
//                continue;
//            }
//            Point pos = new Point(this.position.x, this.position.y + rookY);
//
//            // Instead of checking index and null, try-catch
//            try {
//                Piece p = pieces[pos.x][pos.y];
//                System.out.println(p);
//                // If this piece is the same team as ours, skip
//                if (p.isWhite == this.isWhite) {
//                    continue;
//                }
//                movable.add(pos);
//
//            } catch (NullPointerException npe) {
//                // This is an empty spot
//                movable.add(pos);
//            } catch (Exception e) {
//                // This means that the player is at the edge
//            }
//        }
//
//        // En lista för att kolla alla vi har lagt till innan en pjäs av samma färg.
//        HashSet<Point> toBeRemoved = new HashSet();
//
//        for (int rookX = 0 - this.position.x; rookX < 8 - this.position.x; rookX++) {
//
//            if (this.position.y == 0 && rookX == 0) {
//                continue;
//            }
//            Point pos = new Point(this.position.x + rookX, this.position.y);
//
//            // Instead of checking index and null, try-catch
//            try {
//                Piece p = pieces[pos.x][pos.y];
//                System.out.println(p);
//
//                // Funkar bara åt vänster
//                if (pieces[pos.x + 1][pos.y] != null && pieces[pos.x + 1][pos.y] != this) {
//                    toBeRemoved.add(pos);
//                }
//
//                // If this piece is the same team as ours, skip
//                if (p.isWhite == this.isWhite) {
//                    movable.removeAll(toBeRemoved);
//                    toBeRemoved.clear();
//                    continue;
//                }
////                toBeRemoved.add(pos);
//                movable.add(pos);
//
//            } catch (NullPointerException npe) {
//                // This is an empty spot
//                movable.add(pos);
//            } catch (Exception e) {
//                // This means that the player is at the edge
//            }
//
//        }
        System.out.println("Len of movable: " + movable.size());
        return movable;

    }

}
