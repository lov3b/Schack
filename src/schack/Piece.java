package schack;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public abstract class Piece {

    public Point position;
    public boolean white;
    /**
     * SPECIAL RULÖES APPLY TO THE KING, (ITS GOOD TO BE THE KING:)
     */
    public boolean supremeRuler = false;
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

    public abstract ArrayList<Point> validMoves(Piece[][] pieces, boolean isSelected);

    public ArrayList<Point> validAttacks(Piece[][] pieces) {
        return validMoves(pieces, false);
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
                tryToMoveAndCheckIfCheck(pieces, movable, pos);
            }
            // Fortsätt att gå
            return false;
        }

        /**
         * Ifall det är en pjäs i motståndarlaget här kan vi ta den men inte gå
         * längre Ifall det är samma färg som oss betyder det att vi inte kan
         * lägga till den
         */
        if (pieceToCheck.isWhite() != this.white) {
            /**
             * Detta betyder att det är en motsatts pjäs här, vi kan ta men inte
             * gå längre
             */
            if (!isSelected) {
                movable.add(pos);
            } else {
                tryToMoveAndCheckIfCheck(pieces, movable, pos);
            }
        }
        return true;

    }

    void tryToMoveAndCheckIfCheck(Piece[][] pieces, ArrayList movable, Point pos) {
        // Kom ihåg vart vi var
        Point previousPosition = new Point(this.position);

        // Kom ihåg motståndarpjäs
        Piece opponentPiece = pieces[pos.x][pos.y];

        // Testa att flytta
        pieces[pos.x][pos.y] = this;
        pieces[previousPosition.x][previousPosition.y] = null;
        this.position = new Point(pos);

        boolean inSchack = checkIfSchack(pos, pieces);

        // Flytta tillbaka
        pieces[previousPosition.x][previousPosition.y] = this;
        pieces[pos.x][pos.y] = opponentPiece;
        this.position = new Point(previousPosition);

        if (!inSchack) {
            movable.add(pos);
        }
    }

    boolean checkIfSchack(Point pos, Piece[][] pieces) {
        boolean ourColor = this.isWhite();
        Piece selectedPiece = this;
        ArrayList<Point> attacks = new ArrayList<>();

        // Fråga alla pjäser vart de kan gå/ta
        for (Piece[] pieceArr : pieces) {
            for (Piece piece : pieceArr) {
                // Ifall det är tomrum skippa
                if (piece == null) {
                    continue;
                } else if (piece.isWhite() == ourColor) {
                    continue;
                }

                // Lägg till alla attacker för mostståndaren
                attacks.addAll(piece.validAttacks(pieces));
            }
        }

        // Kollar ifall kungen står i schack just nu
        for (Point attack : attacks) {
            Piece attacked = pieces[attack.x][attack.y];
            if (attacked == null) {
                continue;
            }
            if (attacked.supremeRuler) {
                return true;
            }
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
