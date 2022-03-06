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
        LinkedHashSet<Point> unmovable = new LinkedHashSet<>();
        LinkedHashSet<Point> perhapsMovable = new LinkedHashSet<>();

//        // Ner höger
//        for (int x = 0, y = 0; x < 8 && x > -8; x++, y++) {
//            System.out.println("x: " + x + ", y: " + y);
//        }
//        // Upp höger
//        for (int x = 0, y = 0; x < 8 && x > -8; x++, y--) {
//            System.out.println("x: " + x + ", y: " + y);
//        }
//        // Ner vänster
//        for (int x = 0, y = 0; x < 8 && x > -8; x--, y--) {
//            System.out.println("x: " + x + ", y: " + y);
//        }
//        // Upp vänster
//        for (int x = 0, y = 0; x < 8 && x > -8; x--, y++) {
//            System.out.println("x: " + x + ", y: " + y);
//        }
        return new LinkedHashSet<>();
    }

    public static void main(String[] args) {
        // Ner höger
        for (int x = 0, y = 0; x < 8 && x > -8; x++, y++) {
            System.out.println("x: " + x + ", y: " + y);
        }
        // Upp höger
        for (int x = 0, y = 0; x < 8 && x > -8; x++, y--) {
            System.out.println("x: " + x + ", y: " + y);
        }
        // Ner vänster
        for (int x = 0, y = 0; x < 8 && x > -8; x--, y--) {
            System.out.println("x: " + x + ", y: " + y);
        }
        // Upp vänster
        for (int x = 0, y = 0; x < 8 && x > -8; x--, y++) {
            System.out.println("x: " + x + ", y: " + y);
        }
    }

    @Override
    public void move(Piece[][] pieces) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String toString() {
        return "Bishop{" + "position=" + position + ", isWhite=" + isWhite + '}';
    }
}
