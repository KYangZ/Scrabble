
/**
 * Write a description of class Square here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.lang.Character;

public class Square extends JButton implements ActionListener {
    public int xpos;
    public int ypos;
    public String letter = "";
    public Tile t;
    
    public Square(int x, int y){
        this.xpos = x;
        this.ypos = y;
        this.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e){
        
        JPanel panel = new JPanel();
        panel.add(new JLabel("Pick a letter:"));
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        
        for(Tile t : Main.s.players[Main.s.getTurn()].getPlayerBagArrayList()){
            model.addElement(t.toString());
        }
        
        //System.out.println(Main.s.tempWord);
        
        
        //Main.s.tempWord.add(Main.s.players[Main.s.getTurn()].playerBag.remove());
        
        

        JComboBox comboBox = new JComboBox(model);
        panel.add(comboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Letter", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (result) {
            case JOptionPane.OK_OPTION:
                letter = comboBox.getSelectedItem().toString();
                //add code to temporarily remove the tile from your bag
                int index = 0;
                for(Tile t : Main.s.players[Main.s.getTurn()].getPlayerBagArrayList()){
                    if(t.toString().equals(letter)){
                        Main.s.tempWord.add(Main.s.players[Main.s.getTurn()].playerBag.remove(index));
                        t.location = this;
                        //System.out.println(t.location.xpos + "," + t.location.ypos);
                        break;
                    }
                    index++;
                }       
                setEnabled(false);
                setText(letter);
                setOpaque(true);
                setBackground(Color.LIGHT_GRAY);
                break;
                
            case JOptionPane.CANCEL_OPTION:
            
                break;
        }
        
        
        
        
    }
    
    public void reset(){
        setEnabled(true);
        setText("");
        setOpaque(false);
        setBackground(null);
    }
    
}
