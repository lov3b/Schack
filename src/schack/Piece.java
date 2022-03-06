package schack;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;
import javax.imageio.ImageIO;

public abstract class Piece extends Component {

    public Point position;
    public boolean isWhite;
    protected BufferedImage icon;

    public Piece(boolean isWhite, Point startingPosition) {
        this.isWhite = isWhite;
        this.position = startingPosition;
    }

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public void setPosition(Point p) {
        this.position = p;
    }

    protected void setPieceIcon(String className) throws IOException {
        String colorName = isWhite ? "White" : "Black";
        String fileName = colorName + className + ".png";
        InputStream is = getClass().getResourceAsStream("../img/" + fileName);
        icon = ImageIO.read(is);
    }

    public abstract LinkedHashSet<Point> validMoves(Piece[][] pieces);

    public void draw(Graphics2D g2) {

        g2.drawImage(
                icon,
                position.x * Board.SIZE_OF_TILE,
                position.y * Board.SIZE_OF_TILE,
                (ImageObserver) this
        );
    }

    public abstract void move(Piece[][] pieces);

    @Override
    public String toString() {
        return "Piece{" + "position=" + position + ", isWhite=" + isWhite + '}';
    }

}
