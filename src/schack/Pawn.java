package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

public class Pawn extends PieceKnownIfMoved {

    public Pawn(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
    }

    @Override
    public ArrayList<Point> validAttacks(Piece[][] pieces) {
        ArrayList<Point> movable = new ArrayList<>();

        // Kolla ifall vi kan ta någon
        for (int pawnX : new int[]{-1, 1}) {
            // Position vi kollar just nu, snett upp åt höger & vänster
            Point pos = new Point(this.position.x + pawnX, this.position.y + (this.white ? -1 : 1));
            if (pos.x < 0 || pos.x > 7 || pos.y < 0 || pos.y > 7) {
                continue;
            }
            Piece piece = pieces[pos.x][pos.y];
            if (piece == null || piece.white != piece.white) {
                movable.add(pos);
            }
        }

        return movable;
    }

    @Override
    public ArrayList<Point> validMoves(Piece[][] pieces, boolean isSelected) {
        ArrayList<Point> movable = new ArrayList<>();

        // Om bonden har gått en gång, får gå 1 steg, annars 2
        final int upTo = moved ? 1 : 2;

        // Kolla om man kan gå rakt frak
        for (int pawnDY = 1; pawnDY <= upTo; pawnDY++) {
            Point pos = new Point(this.position.x, this.position.y + (this.white ? -pawnDY : pawnDY));
            boolean shouldBreak = addMovesIfCan(pos, movable, pieces, isSelected);
            if (shouldBreak) {
                System.out.println("should brkje!");
                break;
            }
        }

        // Kolla ifall vi kan ta någon
        for (int pawnX : new int[]{-1, 1}) {
            // Position vi kollar just nu, snett upp åt höger & vänster
            Point pos = new Point(this.position.x + pawnX, this.position.y + (this.white ? -1 : 1));
            addAttackMovesIfCan(pos, movable, pieces);
        }
        System.out.println("len of movable: " + movable.size());
        return movable;
    }

    // Känns som det här skulle kunnat vara i validMoves, men nu är det såhär
    /**
     * Ifall det är en pjäs som står här och den inte är samma färg som oss,
     * lägg till i listan
     *
     * @param pos
     * @param movable
     * @param pieces
     */
    private void addAttackMovesIfCan(Point pos, ArrayList movable, Piece[][] pieces) {

        // Se till att vi inte är utanför brädet
        if (pos.x >= pieces.length || pos.x < 0 || pos.y >= pieces[0].length || pos.y < 0) {
            return;
        }

        Piece piece = pieces[pos.x][pos.y];
        // Ifall det är tomt här, gör ingenting
        if (piece == null) {
            return;
        } else if (piece.isWhite() != this.isWhite()) {
            tryToMoveAndCheckIfCheck(pieces, movable, pos);
        }

    }

    @Override
    protected boolean addMovesIfCan(Point pos, ArrayList movable, Piece[][] pieces, boolean isSelected) {
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
            tryToMoveAndCheckIfCheck(pieces, movable, pos);
        } catch (IndexOutOfBoundsException ioobe) {
            // This means that the player is at the edge
            System.out.println(pos);
        } catch (Exception e) {
            // For good meassure
        }
        return false;

    }

}
