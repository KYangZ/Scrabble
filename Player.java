
/**
 * The Player Class - Describes the two players in the game.
 *
 * @Kristy Lee, Kory Yang
 * @version 05152018
 */

import java.util.ArrayList;

public class Player
{
    private int points;
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
