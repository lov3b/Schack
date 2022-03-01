package schack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;

/**
 *
 * @author Love Billenius & Simon Hansson
 */
public class Schack extends JFrame
{

    public Dimension size = new Dimension(800, 800);

    public Schack()
    {
        setSize(size);
        setAlwaysOnTop(true);
        setBackground(Color.black);

        setVisible(true);
    }
    
    private void drawSquares(Graphics g){
        
    }

    public static void main(String[] args)
    {
        new Schack();

    }

}
