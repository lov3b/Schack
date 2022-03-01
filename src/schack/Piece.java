package schack;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public abstract class Piece extends Component {

    public Point position;
    public boolean white;
    public boolean castled = false;
    protected BufferedImage icon;

    public Piece(boolean white, Point startingPosition) {
        this.white = white;
        this.position = startingPosition;
    }

    public abstract LinkedHashSet<Point> legalMoves(ArrayList<Piece> pieces);

    public void draw(Graphics2D g2) {

        g2.drawImage(icon, position.x * Board.SCALE, position.y * Board.SCALE, (ImageObserver) this);
//        g2.drawImage(icon, 4 * Board.SCALE, 6* Board.SCALE, (ImageObserver) this);
    }

}
