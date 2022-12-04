package com.billenius.schack;

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class PieceRenderer extends JLabel implements ListCellRenderer<Move> {

    @Override
    public Component getListCellRendererComponent(JList<? extends Move> list, Move move, int index,
            boolean isSelected,
            boolean cellHasFocus) {
        BufferedImage image = move.movedPiece.getIcon();
        ImageIcon ii = new ImageIcon(image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH));

        setIcon(ii);
        setText(move.toString());

        return this;
    }
}