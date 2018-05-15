
/**
 * Write a description of class Main here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import javax.swing.*;
import java.awt.*;

public class Main
{
    public static Scrabble s = new Scrabble();
    
    public static void main(String args[]){
        JOptionPane.showMessageDialog(null,"Welcome to Scrabble! \nHow to play: click on any tile to place a letter.\nType 'pass' to pass your turn.");
        s.runScrabble();
    }
}
