
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
        
        switch(letter){ //assign point values
            case 'a': this.value = 1;  break; 
            case 'e':this.value = 1; break; 
            case 'i':this.value = 1; break; 
            case 'o':this.value = 1; break; 
            case 'n':this.value = 1; break; 
            case 'r':this.value = 1; break; 
            case 't':this.value = 1; break; 
            case 'l':this.value = 1; break; 
            case 's':this.value = 1;  break; 
            case 'u':this.value = 1; break; 
                       
            case 'd': this.value = 2; break;
            case 'g': this.value = 2; break;
            
            case 'b': this.value = 3; break;
            case 'c': this.value = 3; break;
            case 'm': this.value = 3; break;
            case 'p': this.value = 3; break;
            
            case 'f': this.value = 4; break;
            case 'h': this.value = 4; break;
            case 'v': this.value = 4; break;
            case 'w': this.value = 4; break;
            case 'y': this.value = 4; break;
            
            case 'k': this.value = 5; break;
            
            case 'j': this.value = 8; break;
            case 'x': this.value = 8; break;
            
            case 'q': this.value = 10; break;
            case 'z': this.value = 10; break;
        }
    }
    
    public void setXPos(int xPos){ //if the Tile is placed on the board
        this.xPos = xPos;
    }
    
    public void setYPos(int yPos){ //if the Tile is placed on the board
        this.yPos = yPos;
    }
    
    public int getValue(){
        return this.value;
    }
    
    public String toString(){
        return this.letter + "";
        
    }
    
}
