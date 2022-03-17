package schack;

import java.awt.Point;
import java.io.IOException;
import java.util.LinkedHashSet;

public class Queen extends Piece {

    public Queen(boolean isWhite, Point point) throws IOException {
        super(isWhite, point);
        setPieceIcon("Queen");
    } 

    @Override
    public LinkedHashSet<Point> validMoves(Piece[][] pieces) {
        LinkedHashSet<Point> movable = new LinkedHashSet<>();

        // Upp vänster
        for (int bishopX = this.position.x - 1, bishopY = this.position.y - 1; bishopX >= 0 && bishopY >= 0; bishopX--, bishopY--) {

            Point pos = new Point(bishopX, bishopY);

            // Instead of checking index and null, try-catch
            try {
                Piece p = pieces[pos.x][pos.y];
                System.out.println(p);
                // If this piece is the same team as ours, skip
                if (p.isWhite == this.isWhite) {
                    break;
                }

                movable.add(pos);
                break;

            } catch (NullPointerException npe) {
                // This is an empty spot
                movable.add(pos);
            } catch (Exception e) {
                // This means that the player is at the edge
            }

        }

        // Upp höger 
        for (int bishopX = this.position.x + 1, bishopY = this.position.y - 1; bishopX <= 7 && bishopY >= 0; bishopX++, bishopY--) {

            Point pos = new Point(bishopX, bishopY);

            // Instead of checking index and null, try-catch
            try {
                Piece p = pieces[pos.x][pos.y];
                System.out.println(p);
                // If this piece is the same team as ours, skip
                if (p.isWhite == this.isWhite) {
                    break;
                }

                movable.add(pos);
                break;

            } catch (NullPointerException npe) {
                // This is an empty spot
                movable.add(pos);
            } catch (Exception e) {
                // This means that the player is at the edge
            }

        }
        // Ner höger
        for (int bishopX = this.position.x + 1, bishopY = this.position.y + 1; bishopX <= 7 && bishopY <= 7; bishopX++, bishopY++) {

            Point pos = new Point(bishopX, bishopY);

            // Instead of checking index and null, try-catch
            try {
                Piece p = pieces[pos.x][pos.y];
                System.out.println(p);
                // If this piece is the same team as ours, skip
                if (p.isWhite == this.isWhite) {
                    break;
                }

                movable.add(pos);
                break;

            } catch (NullPointerException npe) {
                // This is an empty spot
                movable.add(pos);
            } catch (Exception e) {
                // This means that the player is at the edge
            }

        }
        // Ner vänster
        for (int bishopX = this.position.x - 1, bishopY = this.position.y + 1; bishopX >= 0 && bishopY <= 7; bishopX--, bishopY++) {

            Point pos = new Point(bishopX, bishopY);

            // Instead of checking index and null, try-catch
            try {
                Piece p = pieces[pos.x][pos.y];
                System.out.println(p);
                // If this piece is the same team as ours, skip
                if (p.isWhite == this.isWhite) {
                    break;
                }

                movable.add(pos);
                break;

            } catch (NullPointerException npe) {
                // This is an empty spot
                movable.add(pos);
            } catch (Exception e) {
                // This means that the player is at the edge
            }

        }// Vänster
        for (int rookX = this.position.x - 1; rookX >= 0; rookX--) {

            Point pos = new Point(rookX, this.position.y);

            // Instead of checking index and null, try-catch
            try {
                Piece p = pieces[pos.x][pos.y];
                System.out.println(p);
                // If this piece is the same team as ours, skip
                if (p.isWhite == this.isWhite) {
                    break;
                }

                movable.add(pos);
                break;

            } catch (NullPointerException npe) {
                // This is an empty spot
                movable.add(pos);
            } catch (Exception e) {
                // This means that the player is at the edge
            }

        }

        // Höger
        for (int rookX = this.position.x + 1; rookX <= 7; rookX++) {

            Point pos = new Point(rookX, this.position.y);

            // Instead of checking index and null, try-catch
            try {
                Piece p = pieces[pos.x][pos.y];
                System.out.println(p);
                // If this piece is the same team as ours, skip
                if (p.isWhite == this.isWhite) {
                    break;
                }

                movable.add(pos);
                break;

            } catch (NullPointerException npe) {
                // This is an empty spot
                movable.add(pos);
            } catch (Exception e) {
                // This means that the player is at the edge
            }

        }

        // Ner
        for (int rookY = this.position.y + 1; rookY <= 7; rookY++) {

            Point pos = new Point(this.position.x, rookY);

            // Instead of checking index and null, try-catch
            try {
                Piece p = pieces[pos.x][pos.y];
                System.out.println(p);
                // If this piece is the same team as ours, skip
                if (p.isWhite == this.isWhite) {
                    break;
                }

                movable.add(pos);
                break;

            } catch (NullPointerException npe) {
                // This is an empty spot
                movable.add(pos);
            } catch (Exception e) {
                // This means that the player is at the edge
            }

        }

        // Upp
        for (int rookY = this.position.y - 1; rookY >= 0; rookY--) {

            Point pos = new Point(this.position.x, rookY);

            // Instead of checking index and null, try-catch
            try {
                Piece p = pieces[pos.x][pos.y];
                System.out.println(p);
                // If this piece is the same team as ours, skip
                if (p.isWhite == this.isWhite) {
                    break;
                }

                movable.add(pos);
                break;

            } catch (NullPointerException npe) {
                // This is an empty spot
                movable.add(pos);
            } catch (Exception e) {
                // This means that the player is at the edge
            }

        }
        return movable;
    }
}
