
/**
 * Tile object of a letter
 *
 * @author Kristy Lee, Kory Yang
 * @version (a version number or a date)
 */
public class Tile
{
    private int xPos, yPos;
    private char letter;
    private int value;

    /**
     * Constructor for objects of class Tile
     */
    
    public Tile(){
       
    }
    
    public Tile(char letter){
        this.letter = letter;
        
        switch(letter){
            case 'a': break; 
            case 'b': break;
            
            
        }
    }
    
    public void setXPos(int xPos){
        this.xPos = xPos;
    }
    
    public void setYPos(int yPos){
        this.yPos = yPos;
    }
    
    public int getValue(){
        return this.value;
    }
    
    public String toString(){
        return this.letter + "";
        
    }
    
}
