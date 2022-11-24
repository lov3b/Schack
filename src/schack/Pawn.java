package schack;

import java.awt.HeadlessException;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Pawn extends Piece {

    public Pawn(boolean isWhite, Point startingPosition) throws IOException {
        super(isWhite, startingPosition);
    }

    /**
     * Ger tillbaks alla ställen pjäsen kan attackera
     *
     * @param pieces
     * @param shouldNotCareIfAttackSpaceIsEmptyOrNot Ifall man ska kolla ifall
     * det är något i möjliga attackrutor ifall
     * @return Alla lämpliga attackMoves
     */
    @Override
    public List<Point> validAttacks(Piece[][] pieces, boolean shouldNotCareIfAttackSpaceIsEmptyOrNot) {
        List<Point> movable = new ArrayList<>();

        // Kolla ifall vi kan ta någon
        for (int pawnX : new int[]{-1, 1}) {
            // Position vi kollar just nu, snett upp åt höger & vänster
            Point pos = new Point(this.position.x + pawnX, this.position.y + (this.isWhite() ? -1 : 1));
            if (pos.x < 0 || pos.x > 7 || pos.y < 0 || pos.y > 7) {
                continue;
            }
            Piece piece = pieces[pos.x][pos.y];
            if (piece == null || piece.isWhite() != this.isWhite()
                    || (shouldNotCareIfAttackSpaceIsEmptyOrNot && piece.isWhite() != this.isWhite())) {
                movable.add(pos);
            }
        }

        return movable;
    }

    @Override
    public List<Point> validMoves(Piece[][] pieces, boolean allowedToRecurse) {
        List<Point> movable = new ArrayList<>();

        // Om bonden har gått en gång, får gå 1 steg, annars 2
        final int upTo = this.isMoved() ? 1 : 2;

        // Kolla om man kan gå rakt frak
        for (int pawnDY = 1; pawnDY <= upTo; pawnDY++) {
            final Point pos = new Point(this.position.x, this.position.y + (this.isWhite() ? -pawnDY : pawnDY));
            boolean shouldBreak = addMovesIfCan(pos, movable, pieces, allowedToRecurse);
            if (shouldBreak) {
                break;
            }
        }

        // Kolla ifall vi kan ta någon
        for (int pawnX : new int[]{-1, 1}) {
            // Position vi kollar just nu, snett upp åt höger & vänster
            final Point pos = new Point(this.position.x + pawnX, this.position.y + (this.isWhite() ? -1 : 1));
            movable.addAll(addAttackMovesIfCan(pos, pieces));
        }
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
    private List<Point> addAttackMovesIfCan(Point pos, Piece[][] pieces) {
        List<Point> movable = new ArrayList();
        // Se till att vi inte är utanför brädet
        if (pos.x >= pieces.length || pos.x < 0 || pos.y >= pieces[0].length || pos.y < 0) {
            return movable;
        }
        final Piece potentialEnemy = pieces[pos.x][pos.y];
        // Ifall det är tomt här, gör ingenting
        if (potentialEnemy != null && potentialEnemy.isWhite() != this.isWhite()) {
            if (!isInSchack(pieces, pos)) {
                movable.add(pos);
            }
        }
        return movable;
    }

    @Override
    protected boolean addMovesIfCan(Point pos, List movable, Piece[][] pieces, boolean allowedToRecurse) {
        if (pos.x < 0 || pos.x > 7 || pos.y < 0 || pos.y > 7) {
            return false;
        }

        Piece pieceToCheck = pieces[pos.x][pos.y];
        if (pieceToCheck == null) {
            if (!isInSchack(pieces, pos)) {
                movable.add(pos);
            }
            return false;
        }
        return true;

    }

    @Override
    public void move(Piece[][] pieces, Point toMove) {
        super.move(pieces, toMove);

        // Check if the pawn has moved to the end and should be transformed
        if (this.position.y == 0 && this.isWhite()
                || this.position.y == 7 && !this.isWhite()) {
            transform(pieces);
        }
    }

    private void transform(Piece[][] pieces) throws HeadlessException {
        String[] transformations = {"Queen", "Rook", "Bishop", "Horse"};
        int choosenTransformations = JOptionPane.showOptionDialog(null,
                "What do you want to transform into?",
                "Click a button",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                transformations,
                transformations[0]
        );
        try {
            switch (choosenTransformations) {
                case 0:
                    pieces[position.x][position.y] = new Queen(this.isWhite(), this.position);
                    break;
                case 1:
                    pieces[position.x][position.y] = new Rook(this.isWhite(), this.position);
                    break;
                case 2:
                    pieces[position.x][position.y] = new Bishop(this.isWhite(), this.position);
                    break;
                default:
                    pieces[position.x][position.y] = new Horse(this.isWhite(), this.position);
                    break;
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
}
