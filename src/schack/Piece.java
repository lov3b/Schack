package schack;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
    private boolean isWhite;
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

    /**
     * Ladda in pjäsbild från paketet img
     *
     * @param className
     * @throws IOException ifall det inte finns någon bild på pjäsen
     */
    private void setPieceIcon() throws IOException {
        String className = this.getClass().getSimpleName();
        String colorName = this.isWhite() ? "White" : "Black";
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
     * @param shouldNotCareIfAttackSpaceIsEmptyOrNot För bönder ifall den ska
     * kolla ifall det är något i möjliga attackrutor ifall
     * @return Alla lämpliga attackMoves
     */
    public ArrayList<Point> validAttacks(Piece[][] pieces, boolean shouldNotCareIfAttackSpaceIsEmptyOrNot) {
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

    /**
     * Lägg till move ifall det går, alltså inte är schack där
     *
     * @param pos drag att lägga till ifall det går
     * @param movable lägger till drag i denna ArrayList
     * @param pieces Piece[][] över brädet
     * @param isSelected
     * @return true ifall man inte kan gå längre i denna riktning
     */
    protected boolean addMovesIfCan(Point pos, ArrayList<Point> movable, Piece[][] pieces, boolean isSelected) {
        // Ifall vi är utanför brädet ge tillbaka false
        if (pos.x > 7 || pos.x < 0 || pos.y > 7 || pos.y < 0) {
            return false;
        }

        Piece pieceToCheck = pieces[pos.x][pos.y];

        // Detta är en tom plats
        if (pieceToCheck == null) {
            if (!isSelected || !isInSchack(pieces, pos)) {
                movable.add(pos);
            }
            return false;
        }

        /**
         * Ifall det är en pjäs i motståndarlaget här kan vi ta den men inte gå
         * längre Ifall det är samma färg som oss betyder det att vi inte kan
         * lägga till den
         */
        if ((pieceToCheck.isWhite() != this.isWhite())
                && ((isSelected && !isInSchack(pieces, pos)) || !isSelected)) {
            movable.add(pos);
        }
        return true;

    }

    /**
     * Kolla ifall det är schack vid den här positionen
     *
     * @param pieces Piece[][] över hela brädet
     * @param pos Kollar ifall det är schack om denna Piece flyttar hit
     * @return true ifall det är schack
     */
   protected boolean isInSchack(Piece[][] pieces, Point pos) {
        // Kom ihåg vart vi var
        Point previousPosition = new Point(this.position);

        // Kom ihåg motståndarpjäs
        Piece guyThatsAlreadyHere = pieces[pos.x][pos.y];

        // Testa att flytta
        pieces[pos.x][pos.y] = this;
        pieces[previousPosition.x][previousPosition.y] = null;
        this.position = pos;

        boolean inSchack = isInSchack(pieces);

        // Flytta tillbaka
        pieces[previousPosition.x][previousPosition.y] = this;
        pieces[pos.x][pos.y] = guyThatsAlreadyHere;
        this.position = previousPosition;

        return inSchack;
    }

    /**
     * Kolla ifall det är schack
     *
     * @param pieces Piece[][] över hela brädet
     * @return true ifall det är schack
     */
    private boolean isInSchack(Piece[][] pieces) {
        ArrayList<Point> enemyAttacks = new ArrayList<>();

        // Fråga alla pjäser vart de kan gå/ta
        for (Piece[] pieceArr : pieces) {
            for (Piece piece : pieceArr) {
                if (piece != null && piece.isWhite != this.isWhite()) {
                    // Lägg till alla attacker för mostståndaren
                    enemyAttacks.addAll(piece.validAttacks(pieces, false));
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

    /**
     *
     * @return true ifall pjäsen är vit
     */
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
