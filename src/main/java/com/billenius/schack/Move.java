package com.billenius.schack;

import java.awt.Point;

import com.billenius.schack.pieces.Piece;

public class Move extends BasicMove {
    public Piece movedPiece;

    public Move(Piece piece, Point from, Point to) {
        super(from, to);
        this.movedPiece = piece;
    }

    public Move(Piece piece, BasicMove basicMove) {
        super(basicMove.from, basicMove.to);
        this.movedPiece = piece;
    }

}
