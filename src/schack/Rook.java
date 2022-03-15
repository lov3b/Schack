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
        for (int rookX = this.position.x-1; rookX >= 0; rookX--) {
            
            Point pos = new Point(rookX,this.position.y);


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
        for (int rookX = this.position.x+1; rookX <= 7; rookX++) {
            
            Point pos = new Point(rookX,this.position.y);


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

        // Ner
        for (int rookY = this.position.y+1; rookY <= 7; rookY++) {
            
            Point pos = new Point(this.position.x,rookY);


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

        // Upp
        for (int rookY = this.position.y-1; rookY >= 0; rookY--) {
            
            Point pos = new Point(this.position.x,rookY);


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
        System.out.println("Len of movable: " + movable.size());
        return movable;

    }

}
