package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.LinkedHashSet;

public class Pawn extends PieceKnownIfMoved {

    public Pawn(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
        setPieceIcon("Pawn");
    }

    @Override
    public LinkedHashSet<Point> validMoves(Piece[][] pieces) {
        // TODO: Lösa bugg där bunder på kanterna inte kan röra sig
        LinkedHashSet<Point> movable = new LinkedHashSet<>();

        // Om bonden har gått en gång, får gå 1 steg, annars 2
        final int upTo = hasMoved ? 1 : 2;
        // Kolla om man kan gå rakt frak
        for (int pawnDY = 1; pawnDY <= upTo; pawnDY++) {
            System.out.println("this.position.x: " + this.position.x);
            System.out.println("calced y: " + (this.position.y + (this.isWhite ? -pawnDY : pawnDY)));
            Point pos = new Point(this.position.x, this.position.y + (this.isWhite ? -pawnDY : pawnDY));
            boolean shouldBreak = addMovesIfCan(pos, movable, pieces);
            if (shouldBreak) {
                System.out.println("should brkje!");
                break;
            }
        }

        // Kolla ifall vi kan ta någon
        for (int pawnX : new int[]{-1, 1}) {
            // Position vi kollar just nu, snett upp åt höger & vänster
            Point pos = new Point(this.position.x + pawnX, this.position.y + (this.isWhite ? -1 : 1));
            addAttackMovesIfCan(pos, movable, pieces);
        }
        System.out.println("len of movable: " + movable.size());
        return movable;
    }

    // Känns som det här skulle kunnat vara i validMoves, men nu är det såhär
    private void addAttackMovesIfCan(Point pos, LinkedHashSet movable, Piece[][] pieces) {

        // Ifall det är en pjäs som står här och den inte är samma färg som oss, lägg till
        try {
            Piece p = pieces[pos.x][pos.y];
            if (p.isWhite != this.isWhite) {
                movable.add(pos);
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected boolean addMovesIfCan(Point pos, LinkedHashSet movable, Piece[][] pieces) {
        if (pos.x < 0 || pos.x > 7 || pos.y < 0 || pos.y > 7) {
            return false;
        }
        // Instead of checking index and null, try-catch
        try {
            // Ifall vi kollar utanför brädet kommer detta att faila
            Piece p = pieces[pos.x][pos.y];

            // Ifall pjäsen här har samma färg som oss, break
            // Ifall det inte är någon pjäs här kommer det att gå ner till
            // catch(NullPointerException) och då lägger vi till detta drag i listan
            // Ifall det är inte är en pjäs här, kasta ett NullPointerException
            // Detta är för att vara så lik super-implementationen som möjligt
            if (p == null) {
                throw new NullPointerException();
            } else {
                // Detta betyder att det finns en pjäs här
                // Vi kan ta men inte gå längre.
                return true;
            }
        } catch (NullPointerException npe) {
            // This is an empty spot
            movable.add(pos);
        } catch (IndexOutOfBoundsException ioobe) {
            // This means that the player is at the edge
            System.out.println(pos);
        } catch (Exception e) {
            // For good meassure
        }
        return false;

    }

    @Override
    public String toString() {
        return "Pawn{" + "position=" + position + ", isWhite=" + isWhite + '}';
    }
}
