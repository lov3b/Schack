package com.billenius.schack;

import java.awt.Point;
import java.io.Serializable;

public class BasicMove implements Serializable {
    public Point from;
    public Point to;

    public BasicMove(Point from, Point to) {
        this.from = from;
        this.to = to;
    }

    public BasicMove(Move m) {
        this.from = m.from;
        this.to = m.to;
    }

    public String toString() {
        return (from.x + 1) + ":" + (from.y + 1)
                + " " + '\u27F6' + " " + (to.x + 1) + ":" + (to.y + 1);
    }
}
