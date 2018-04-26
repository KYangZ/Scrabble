import java.util.*;
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    private int points;
    //private ArrayList<String> collectionLetters = new ArrayList<String>();
    private ArrayList<Tile> playerBag = new ArrayList<Tile>();

    /**
     * Constructor for objects of class Player
     */
    
    public Player(){
       this.points = 0;
    }
    
    public void addPoints(int points){
        this.points += points;
    }   
    
    public int getPoints(){
        return this.points;
    }
    
    public ArrayList<Tile> getPlayerBag(){
        return playerBag;
    }
    
    //need a method to set player bag
    
    public void buildPlayerBag(ArrayList<String> collectionLetters){
     //put the letters in the Tile bag and assign the letters+point values to each Tile   
    }
    
    
    
}
