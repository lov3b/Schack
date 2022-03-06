package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.LinkedHashSet;

public class Pawn extends Piece {

    public Pawn(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
        setPieceIcon("Pawn");
    }

    Pawn(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public LinkedHashSet<Point> validMoves(Piece[][] pieces) {

        return new LinkedHashSet<>();
    }

    @Override
    public void move(Piece[][] pieces) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
@Override
    public String toString() {
        return "Pawn{" + "position=" + position + ", isWhite=" + isWhite + '}';
    }
}
