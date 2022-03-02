package schack;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import javax.imageio.ImageIO;

public final class King extends Piece {

    public King(boolean white, Point startingPosition) throws IOException {
        super(white, startingPosition);
        String colorName = white ? "White" : "Black";
        String fileName = "resized" + colorName + "King.png";
        String path = Paths.get("icons", fileName).toString();
        System.out.println(path);
        icon = ImageIO.read(new File(fileName));
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
    }

    public boolean isSeen(ArrayList<Piece> pieces) {
        return true;
    }

    @Override
    public LinkedHashSet<Point> validMoves(ArrayList<Piece> pieces) {
        LinkedHashSet<Point> unmovable = new LinkedHashSet<>();
        LinkedHashSet<Point> perhapsMovable = new LinkedHashSet<>();
        for (Piece piece : pieces) {
            Point p = piece.position;

            // Ifall en pjäs står runt omkring kungen går det inte att flytta dit
            if (Math.abs(p.x - this.position.x) == 1
                    && Math.abs(p.y - this.position.y) == 1) {
                unmovable.add(p);
            }

        }

        // Lägg till tiles kring kungen
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (y == 1 && x == 1) {
                    continue;
                } // Ifall det är utanför planen, skippa tror inte det funkar
                else if (x + this.position.x > 8
                        || x + this.position.x < 0
                        || y + this.position.y > 8
                        || y + this.position.y < 0) {
                    continue;
                }
                perhapsMovable.add(
                        new Point(this.position.x - 1 + x, this.position.y - 1 + y)
                );
            }
        }

        perhapsMovable.removeAll(unmovable);
        return perhapsMovable;
    }
}
