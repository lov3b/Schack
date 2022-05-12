package schack;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;

public abstract class Piece {

    /**
     * Variabel som alltid bör vara samma värde som pjäsen är i brädes av
     * Piece[][]
     */
    public Point position;
    /**
     * Sant ifall pjäsens färg är vit, falskt ifall den är svart
     */
    public boolean isWhite;
    /**
     * SPECIAL RULÖES APPLY TO THE KING, (ITS GOOD TO BE THE KING:)
     */
    public boolean supremeRuler = false;
    /**
     * Bild av pjäsen som ritas ut på bärdet
     */
    protected BufferedImage icon;

    public Piece(boolean white, Point startingPosition) throws IOException {
        this.isWhite = white;
        this.position = startingPosition;
        setPieceIcon();
    }

    public Piece(boolean white) {
        this.isWhite = white;
    }

    public void setPosition(Point p) {
        this.position = p;
    }

    /**
     * Ladda in pjäsbild från paketet img
     *
     * @param className
     * @throws IOException ifall det inte finns någon bild på pjäsen
     */
    private void setPieceIcon() throws IOException {
        String className = this.getClass().getSimpleName();
        String colorName = isWhite ? "White" : "Black";
        String fileName = colorName + className + ".png";
        InputStream is = getClass().getResourceAsStream("/img/" + fileName);
        icon = ImageIO.read(is);
    }

    /**
     * Ger tillbaks alla ställen pjäsen kan gå till
     *
     * @param pieces
     * @param isSelected
     * @return
     */
    public abstract ArrayList<Point> validMoves(Piece[][] pieces, boolean isSelected);

    /**
     * Ger tillbaks alla ställen pjäsen kan attackera
     *
     * @param pieces
     * @return
     */
    public ArrayList<Point> validAttacks(Piece[][] pieces) {
        return validMoves(pieces, false);
    }

    /**
     * Ritar ut pjäsen baserat på den ihågkommna positionen
     *
     * @param g2
     */
    public void draw(Graphics2D g2) {

        g2.drawImage(
                icon,
                position.x * Board.SIZE_OF_TILE,
                position.y * Board.SIZE_OF_TILE,
                null
        );
    }

    /**
     * Flyttar pjäsen till toMove
     *
     * @param pieces
     * @param toMove
     */
    public void move(Piece[][] pieces, Point toMove) {

        // Gör ingenting ifall vi är utanför brädet
        if (toMove.x >= pieces.length || toMove.y < 0 || position.x >= pieces[0].length || position.y < 0) {
            return;
        }

        pieces[toMove.x][toMove.y] = this;
        pieces[position.x][position.y] = null;
        this.position = new Point(toMove);
    }

    protected boolean addMovesIfCan(Point pos, ArrayList<Point> movable, Piece[][] pieces, boolean isSelected) {
        // Ifall vi är utanför brädet ge tillbaka false
        if (pos.x > 7 || pos.x < 0 || pos.y > 7 || pos.y < 0) {
            return false;
        }

        Piece pieceToCheck = pieces[pos.x][pos.y];

        // Detta är en tom plats
        if (pieceToCheck == null) {
            if (!isSelected) {
                movable.add(pos);
            } else {
                movable.addAll(tryToMoveAndCheckIfCheck(pieces, pos));
            }
            // Fortsätt att gå
            return false;
        }

        /**
         * Ifall det är en pjäs i motståndarlaget här kan vi ta den men inte gå
         * längre Ifall det är samma färg som oss betyder det att vi inte kan
         * lägga till den
         */
        if (pieceToCheck.isWhite() != this.isWhite) {
            /**
             * Detta betyder att det är en motsatts pjäs här, vi kan ta men inte
             * gå längre
             */
            if (!isSelected) {
                movable.add(pos);
            } else {
                movable.addAll(tryToMoveAndCheckIfCheck(pieces, pos));
            }
        }
        return true;

    }

    /**
     * Testa att flytta pjäs till pos och ifall det inte är schack så lägg ge
     * tillbaka möjligt drag i en ArrayList
     *
     * @param pieces
     * @param pos
     * @return
     */
    ArrayList<Point> tryToMoveAndCheckIfCheck(Piece[][] pieces, Point pos) {
        ArrayList<Point> movable = new ArrayList<>();
        // Kom ihåg vart vi var
        Point previousPosition = new Point(this.position);

        // Kom ihåg motståndarpjäs
        Piece opponentPiece = pieces[pos.x][pos.y];

        // Testa att flytta
        pieces[pos.x][pos.y] = this;
        pieces[previousPosition.x][previousPosition.y] = null;
        this.position = new Point(pos);

        boolean inSchack = isInSchack(pos, pieces);

        // Flytta tillbaka
        pieces[previousPosition.x][previousPosition.y] = this;
        pieces[pos.x][pos.y] = opponentPiece;
        this.position = new Point(previousPosition);

        if (!inSchack) {
            movable.add(pos);
        }
        return movable;
    }

    /**
     * Kolla ifall det är schack vid den här positionen
     *
     * @param pos
     * @param pieces
     * @return true ifall det är schack
     */
    boolean isInSchack(Point pos, Piece[][] pieces) {
        ArrayList<Point> enemyAttacks = new ArrayList<>();

        // Fråga alla pjäser vart de kan gå/ta
        for (Piece[] pieceArr : pieces) {
            for (Piece piece : pieceArr) {
                if (piece != null && piece.isWhite != this.isWhite()) {
                    // Lägg till alla attacker för mostståndaren
                    enemyAttacks.addAll(piece.validAttacks(pieces));
                }
            }
        }

        // Kollar ifall kungen står i schack just nu
        for (Point enemyAttack : enemyAttacks) {
            Piece attackedPiece = pieces[enemyAttack.x][enemyAttack.y];
            if (attackedPiece != null && attackedPiece.supremeRuler) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + "position=" + position + ", isWhite=" + isWhite + '}';
//        return "Piece{" + "position=" + position + ", isWhite=" + white + '}';
    }

    public boolean isWhite() {
        return isWhite;
    }

    /**
     * Kompabilitet med PieceKnownIfMoved
     *
     * @return false
     */
    public boolean isMoved() {
        return false;
    }

}
