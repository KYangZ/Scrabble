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
    public ArrayList<Tile> playerBag = new ArrayList<Tile>();

    public Player(){
       this.points = 0;
    }
    
    public void addPoints(int points){
        this.points += points;
    }   
    
    public int getPoints(){
        return this.points;
    }
    
    public ArrayList<Tile> getPlayerBagArrayList(){
        return playerBag;
    }
    
    public String printPlayerBag(){
        String lettersInBag = "";
        for (int i = 0; i < playerBag.size(); i++){
            lettersInBag += playerBag.get(i) + " "; //uses toString method
        }
        return lettersInBag;
    }
    
    public void buildPlayerBag(String letter){
        playerBag.add(new Tile(letter.charAt(0))); //add letter to Tile object
    }
    
    
    
}
