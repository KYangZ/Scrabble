
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
    private boolean isHorizontal = false; //letter is part of a horizontal chain of letters on grid
    private boolean isVertical = false; //letter is part of a vertical chain of letters on grid

    /**
     * Constructor for objects of class Tile
     */
    
    public Tile(){
       
    }
    
    public Tile(char letter){
        this.letter = letter;
        
        switch(letter){ //assign point values
            case 'A': this.value = 1;  break; 
            case 'E':this.value = 1; break; 
            case 'I':this.value = 1; break; 
            case 'O':this.value = 1; break; 
            case 'N':this.value = 1; break; 
            case 'R':this.value = 1; break; 
            case 'T':this.value = 1; break; 
            case 'L':this.value = 1; break; 
            case 'S':this.value = 1;  break; 
            case 'U':this.value = 1; break; 
                       
            case 'D': this.value = 2; break;
            case 'G': this.value = 2; break;
            
            case 'B': this.value = 3; break;
            case 'C': this.value = 3; break;
            case 'M': this.value = 3; break;
            case 'P': this.value = 3; break;
            
            case 'F': this.value = 4; break;
            case 'H': this.value = 4; break;
            case 'V': this.value = 4; break;
            case 'W': this.value = 4; break;
            case 'Y': this.value = 4; break;
            
            case 'K': this.value = 5; break;
            
            case 'J': this.value = 8; break;
            case 'X': this.value = 8; break;
            
            case 'Q': this.value = 10; break;
            case 'Z': this.value = 10; break;
        }
    }
    
    public void setXPos(int xPos){ //if the Tile is placed on the board
        this.xPos = xPos;
    }
    
    public void setYPos(int yPos){ //if the Tile is placed on the board
        this.yPos = yPos;
    }
    
    public void setStatusHorizontal(){
     this.isHorizontal = true;   
    }
    
    public void setStatusVertical(){
        this.isVertical = true;
    }
    
    public boolean getHorizontalStatus(){
        return isHorizontal;
    }
    
    public boolean getVerticalStatus(){
        return isVertical;
    }   
    
    public int getValue(){
        return this.value;
    }
    
    public String toString(){
        return this.letter + "";
    }
    
    
    
}
