package schack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board extends JFrame
{
    
    ArrayList<Piece> pieces = new ArrayList<>();
    
    public Board()
    {
        setTitle("Schack");
        setAlwaysOnTop(true);
        setResizable(false);
        setContentPane(cp);
        cp.setPreferredSize(new Dimension(800, 800) );
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    JPanel cp = new JPanel()
    {
        
        public void paintComponent(Graphics g)
        {
            Graphics2D g2 = (Graphics2D) g;
            
            g2.scale(100, 100);
            g2.setBackground(Color.WHITE);
            g2.setColor(Color.BLACK);
            
            for (int i = 0; i < 8; i += 2) {
                for (int j = 0; j < 8; j += 2) {
                    g.fillRect(i, j, 1, 1);
                }
            }
            
            for (int i = 1; i < 8; i += 2) {
                for (int j = 1; j < 8; j += 2) {
                    g.fillRect(i, j, 1, 1);
                }
            }
        }
    };
//        for(Piece p : pieces){
//            p.draw();
//        }

    public static void main(String[] args)
    {
        new Board();
    }
    
}
