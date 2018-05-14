
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
    
    public Square(){
        this.xpos = xpos;
        this.ypos = ypos;
        this.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e){
        
        JPanel panel = new JPanel();
        panel.add(new JLabel("Please make a selection:"));
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        
        
        for(int i = 'A'; i < 'Z'; i++){
            char l = (char) i ;
            model.addElement(Character.toString(l));
        }
        
        JComboBox comboBox = new JComboBox(model);
        panel.add(comboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Letter", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (result) {
            case JOptionPane.OK_OPTION:
                letter = comboBox.getSelectedItem().toString();
                System.out.println("You selected " + comboBox.getSelectedItem());
                break;
        }
        
        setOpaque(true);
        setBackground(Color.BLUE);
        setText(letter);
        
    }
    
    
}
