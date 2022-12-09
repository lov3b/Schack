package com.billenius.schack;

import java.awt.Point;

import com.billenius.schack.pieces.Piece;

public class Move {
    public Piece movedPiece;
    public Point from;
    public Point to;
    public String color;

    public Move(Piece movedPiece, Point from, Point to) {
        this.from = from;
        this.to = to;
        this.color = movedPiece.isWhite() ? "White" : "Black";
        this.movedPiece = movedPiece;
    }

    public String toString() {
        return (from.x + 1) + ":" + (from.y + 1)
                + " " + '\u27F6' + " " + (to.x + 1) + ":" + (to.y + 1);
    }
}
