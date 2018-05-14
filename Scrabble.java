
/**
 * Write a description of class Scrabble here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import javax.swing.*;
import java.awt.*;


public class Scrabble extends JFrame
{
    
    JPanel p = new JPanel();
    //squares
    Square[] squares = new Square[225];
    
    public static void main(String args[]){
        new Scrabble();
    }
    
    public Scrabble(){
        super("Scrabble");
        setSize(800,800);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        p.setLayout(new GridLayout(15,15));
        
        
        for(int i = 0; i < 225; i++){
            squares[i] = new Square();
            p.add(squares[i]);

        }
        
        for(int y = 0; y < 15; y++){
            for(int x = 0; x < 15; x++){
                squares[y*15 + x].xpos = x;
                squares[y*15 + x].ypos = y;
            }
        }

        add(p);
        
        setVisible(true);
        
    }
}
