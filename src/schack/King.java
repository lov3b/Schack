package schack;

import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import javax.imageio.ImageIO;

public final class King extends Piece {

    boolean eglibleForCastling = true;

    public King(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
        String colorName = isWhite ? "White" : "Black";
        String fileName = colorName + "King.png";
        InputStream is = King.class.getResourceAsStream("../img/" + fileName);
        icon = ImageIO.read(is);
    }

    public King(boolean isWhite) throws IOException {
        super(isWhite, isWhite ? new Point(4, 7) : new Point(4, 0));
        setPieceIcon("King");
    }

    public boolean isSeen(ArrayList<Piece> pieces) {
        return true;
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
                        System.out.println("equals");
                        continue;
                    }
                    movable.add(pos);

                } catch (NullPointerException npe) {
                    // This is an empty spot
                    System.out.println("null: " + pos);
                    movable.add(pos);
                } catch (Exception e) {
                    // This means that the player is at the edge
                }
            }

        }
        System.out.println("Len of movable: " + movable.size());
        return movable;

    }

    @Override
    public void move(Piece[][] pieces) {
        eglibleForCastling = false;
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

     @Override
    public String toString() {
        return "Piece{" + "eglibleForCastling=" + eglibleForCastling + "position=" + position + ", isWhite=" + isWhite + '}';
    }

}
