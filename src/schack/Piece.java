package schack;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;
import javax.imageio.ImageIO;

public abstract class Piece {

    public Point position;
    public boolean white;
    /** SPECIAL RULÖES APPLY TO THE KING, (ITS GOOD TO BE THE KING:)*/
    public boolean supremeRuler= false;
    protected BufferedImage icon;

    public Piece(boolean white, Point startingPosition) throws IOException {
        this.white = white;
        this.position = startingPosition;
    }

    public Piece(boolean white) {
        this.white = white;
    }

    public void setPosition(Point p) {
        this.position = p;
    }

    protected void setPieceIcon(String className) throws IOException {
        String colorName = white ? "White" : "Black";
        String fileName = colorName + className + ".png";
        InputStream is = getClass().getResourceAsStream("/img/" + fileName);
        icon = ImageIO.read(is);
    }

    public abstract LinkedHashSet<Point> validMoves(Piece[][] pieces);

    public LinkedHashSet<Point> validAttacks(Piece[][] pieces) {
        return validMoves(pieces);
    }

    public void draw(Graphics2D g2) {

        g2.drawImage(
                icon,
                position.x * Board.SIZE_OF_TILE,
                position.y * Board.SIZE_OF_TILE,
                null
        );
    }

    public void move(Piece[][] pieces, Point toMove, Point selected) {

        try {
            pieces[toMove.x][toMove.y] = this; //new Rook(true,new Point(toMove));
            pieces[selected.x][selected.y] = null;
            this.position = new Point(toMove);

        } catch (Exception e) {

        }
    }

    protected boolean addMovesIfCan(Point pos, LinkedHashSet movable, Piece[][] pieces) {
        // Instead of checking index and null, try-catch
        try {
            // Ifall vi kollar utanför brädet kommer detta att faila
            Piece p = pieces[pos.x][pos.y];

            // Ifall pjäsen här har samma färg som oss, break
            // Ifall det inte är någon pjäs här kommer det att gå ner till
            // catch(NullPointerException) och då lägger vi till detta drag i listan
            if (p.white == this.white) {
                return true;
            } else {
                // Detta betyder att det är en med motsatts pjäs här
                // Vi kan ta men inte gå längre.
                movable.add(pos);
                return true;
            }
        } catch (NullPointerException npe) {
            // This is an empty spot
            movable.add(pos);
        } catch (IndexOutOfBoundsException ioobe) {
            // This means that the player is at the edge
        } catch (Exception e) {
            // For good meassure
        }
        return false;

    }

    @Override
    public String toString() {
        return "Piece{" + "position=" + position + ", isWhite=" + white + '}';
    }

    public boolean isWhite() {
        return white;
    }

}
