package schack;

import java.awt.Point;
import java.io.IOException;
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
        for (int rookX = 0-this.position.x; rookX < 8-this.position.x; rookX++) {
            
                if (this.position.y == 0 && rookX == 0) {
                    continue;
                }
                Point pos = new Point(this.position.x + rookX, this.position.y);

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
        System.out.println("Len of movable: " + movable.size());
        return movable;

    }

    
}
