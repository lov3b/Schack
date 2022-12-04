package com.billenius.schack.boards;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import com.billenius.schack.Move;
import com.billenius.schack.SchackState;
import com.billenius.schack.pieces.Piece;

public final class SameBoard extends Board {

    public SameBoard(DefaultListModel<Move> listModel) throws IOException {
        super(listModel);
    }

    @Override
    protected void makeMove(Move move) {
        moveList.addElement(move);
        move.movedPiece.move(pieces, move.to);
        turnCount++;
        whitesTurn = !whitesTurn;

        SchackState state = getSchackState();
        switch (state) {
            case SCHACK:
                JOptionPane.showMessageDialog(this, "Du står i schack");
                break;
            case SCHACKMATT:
            case PATT:
                String stateStr = state.toString();
                String msg = stateStr.charAt(0) + stateStr.substring(1, stateStr.length()).toLowerCase();
                int choice = JOptionPane.showConfirmDialog(this, msg + "\nVill du starta om?");

                if (choice == JOptionPane.YES_OPTION)
                    try {
                        restartGame();
                    } catch (IOException ex) {
                    }

                break;
            default:
        }
    }

    @Override
    protected void toDoIfNoPreviousPieceSelected(Piece selectedPiece) {
        if (selectedPiece != null && selectedPiece.isWhite() == whitesTurn)
            validMovesToDraw.addAll(selectedPiece.validMoves(pieces, true));
        else
            validMovesToDraw = new ArrayList<>(); // Snabbare än .clear

    }

}
