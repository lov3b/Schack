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

    public Piece(boolean isWhite, Point startingPosition) throws IOException {
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

    public void move(Piece[][] pieces, Point toMove, Point selected) {

        try {
            pieces[toMove.x][toMove.y] = this; //new Rook(true,new Point(toMove));
            pieces[selected.x][selected.y] = null;
            this.position = new Point(toMove);
            Board.printPieces(pieces);

        } catch (Exception e) {
           
        }
    }

    protected boolean checkMove(int x, int y, LinkedHashSet movable, Piece[][] pieces) {
        Point pos = new Point(x, y);

        // Instead of checking index and null, try-catch
        try {
            Piece p = pieces[pos.x][pos.y];
            System.out.println(p);
            // If this piece is the same team as ours, skip
            if (p.isWhite == this.isWhite) {
                return true;
            }

            movable.add(pos);
            return true;

        } catch (NullPointerException npe) {
            // This is an empty spot
            movable.add(pos);
        } catch (Exception e) {
            // This means that the player is at the edge
        }
        return false;

    }

    @Override
    public String toString() {
        return "Piece{" + "position=" + position + ", isWhite=" + isWhite + '}';
    }

}
