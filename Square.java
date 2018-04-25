
/**
 * Square object that is a part of the Scrabble Gameboard
 *
 * @author Kristy Lee, Kory Yang
 * @version (a version number or a date)
 */
public class Square{
    private int xPos;
    private int yPos;
    boolean isFilled;
    private Tile letter;
    
    public Square(){
    }
    
    public Square(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
        this.isFilled = false;
    }
    
    public void setIsFilled(boolean isFilled, Tile letter){
        this.isFilled = true;
        this.letter = letter;
    }
    
    public int getXPos(){
        return this.xPos;
    }
    
    public int getYPos(){
        return this.yPos;
    }
    
    public boolean getIsFilled(){
        return this.isFilled;
    }
    
    public String toString(){
        if (isFilled){
           return this.letter.toString();
        }
        else{
         return " ";   
        }
    }

}
