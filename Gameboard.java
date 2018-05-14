import java.util.*;
import java.io.*;
import java.util.Collections;

/**
 * Scrabble Gameboard
 *
 * @author Kristy Lee, Kory Yang
 * @version (a version number or a date)
 */

public class Gameboard{
    
    private ArrayList<String> word_list = new ArrayList<String>();
    private ArrayList<String> bag = new ArrayList<String>(); //this will be split among the players
    private int w = 15;
    private int h = 15;
    private Square[][] grid = new Square[w+2][h+2]; //15x15 grid with one-square border around it
    private Scanner in = new Scanner(System.in);
    private Player[] players = new Player[2];
    private Random rand = new Random();
   
    public Gameboard()
    {
       for (int i = 0; i < w+2; i++){
         for (int j = 0; j < h+2; j++){
             grid[i][j] = new Square(i, j);
            }
        }
           
       players[0] = new Player(); players[1] = new Player(); //create two players
            
       importWordList();
       distributeLetterBag(); 
    }

    public void makeLetterBag(){ //Executed one time only. Populates Scrabble bag with all the letters found in Scrabble
        for (int i = 0; i < 9; i++){
            bag.add("A"); bag.add("I");
        }
        for (int i = 0; i < 12; i++) bag.add("E"); 
        for (int i = 0; i < 8; i++) bag.add("O");
        for (int i = 0; i < 6; i++){
            bag.add("N"); bag.add("R"); bag.add("T");
        }
        for (int i = 0; i < 4; i++){
            bag.add("L"); bag.add("S"); bag.add("U"); bag.add("D");
        }
        for (int i = 0; i < 3; i++) bag.add("G");
        for (int i = 0; i < 2; i++){
            bag.add("B"); bag.add("C"); bag.add("M"); bag.add("P"); bag.add("F"); 
            bag.add("H"); bag.add("V"); bag.add("W"); bag.add("Y");
        }
        bag.add("K"); bag.add("J"); bag.add("X"); bag.add("Q"); bag.add("Z");   
    }
    
    public void importWordList(){ //import list of valid words. tested and verified to work
        try{
             Scanner sc = new Scanner(new File ("scrabble_words.txt"));
             String word = "";
             String temp = sc.nextLine(); //Scrabble word list copyright information
             temp = sc.nextLine(); //empty line. 
             
             //now start adding actual words to the word_list
             while (sc.hasNextLine()){
                 word = sc.next();
                 word_list.add(word);
             }
             sc.close();
        }catch (IOException io){
         System.out.println("Error: " + io.getMessage());   
        }
    }
    
    public void distributeLetterBag(){ //only run once in entire game. each player draws 7 tiles from the bag to begin game 
        makeLetterBag();
        for (int i = 0; i < 7; i++){ //distribute 7 letters randomly to each player
           players[0].buildPlayerBag(bag.remove(rand.nextInt(bag.size())));
           players[1].buildPlayerBag(bag.remove(rand.nextInt(bag.size())));
        }
    }
    
    public void refillPlayerBag(int p){
        ArrayList<Tile> playerBag = players[p].getPlayerBagArrayList();
        int numberToRefill = 7 - playerBag.size();
        for (int i = 0; i < numberToRefill; i++){ //distribute new letters randomly to each player. The player now should have 7 letters in total
           players[p].buildPlayerBag(bag.remove(rand.nextInt(bag.size())));
        }
    }
    
    public void printPlayerLettersAndPoints(){
         //display on console
        System.out.println("Player 1's letters: " + players[0].printPlayerBag());
        System.out.println("Player 1's points: " + players[0].getPoints());
        System.out.println();
        System.out.println("Player 2's letters: " + players[1].printPlayerBag());
        System.out.println("Player 2's points: " + players[1].getPoints()); 
        System.out.println();
    }
    
    public void output_grid(){
        System.out.println("      1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 ");
        for (int i = 1; i < w+1; i++){ //excludes printing of the "out of bounds" border
            if (i < 10){
                System.out.print("    " + i + " ");
            }
            else{
                System.out.print("   " + i + " ");
            }
           
            for (int j = 1; j < h+1; j++){ //excludes printing of the "out of bounds" border
                System.out.print(grid[i][j]); 
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }
    
    public void runScrabble(){
        boolean isRunning = true;
        boolean valid = true;
        boolean passTurn = false;
        int player = 0;
        String wordTest = "";
        int counter = 0; //counts number of consecutive passes
        String appendTo = "";
        boolean firstTurn = true;
        boolean front = false;
        boolean back = false;
        String appendToDirection = " ";
       
        game_loops: //placed here because if there are consecutive passes, break out of all loops and terminate game
        do{
            printPlayerLettersAndPoints(); //prints player's available tiles and the points he/she has
            output_grid();
            do{
                do{
                    front = false;
                    back = false;
                    appendTo = "";
                    
                    System.out.println();
                    System.out.println("It's your turn, Player " + (player+1) + "! What letters would you like to place on the board?"); 
                    System.out.print("Make sure you type 7 letters at most (in order). If you want to pass your turn to the next player, write 'pass turn' ");
                    wordTest = in.nextLine();
                    wordTest = wordTest.toUpperCase();
                    
                    if (wordTest.equalsIgnoreCase("pass turn")){
                    }
                    else if(firstTurn){
                        wordTest = validWordTest(wordTest, appendTo.toUpperCase(), appendToDirection);
                    }
                    else if (!firstTurn){
                        System.out.print("What letter are you appending the letter to? ");
                        appendTo = in.nextLine(); 
                        do{
                            System.out.print("Is this letter at the beginning of the word or at the end? Type 'f' for front or 'b' for back ");
                            appendToDirection = in.nextLine();
                        }while (! (appendToDirection.substring(0,1).equalsIgnoreCase("b")) && ! (appendToDirection.substring(0,1).equalsIgnoreCase("f")));   
                        
                        wordTest = validWordTest(wordTest, appendTo.toUpperCase(), appendToDirection.substring(0,1).toLowerCase());
                    }
                   
                    
                    if(wordTest.equalsIgnoreCase(appendTo + "pass turn") || wordTest.equalsIgnoreCase("pass turn" + appendTo) ){ //verified to work
                        counter++;
                        if (counter == 1){
                            //switch player
                            if (player == 0) player = 1;   
                            else player = 0;
                        }
                        else {
                            System.out.println();
                            System.out.println("Both players have ended the game. ");
                            System.out.println();
                            printPlayerLettersAndPoints();
                            output_grid();
                            System.out.println("Game over! ");
                            determineWinner();
                            break game_loops; //permanently terminates game
                        }
                    }
                    else{
                        if(wordTest.length() >= 9 || wordTest.length() == 0) //verified to work
                        System.out.println("Your sequence of words is too long! Try again. ");
                    }
                    
                }while (wordTest.length() >= 8 || wordTest.length() <= 0);
                
                if (appendToDirection.equalsIgnoreCase("f")){
                    System.out.println(appendTo.toUpperCase() + wordTest + " is a word!");//passed valid word test at this point (the word is found in the Scrabble Dictionary)
                }
                else
                    System.out.println(wordTest + appendTo.toUpperCase() + " is a word!");
                
            } while(!checkPlayerLetters(wordTest, player));  //need method to check if the person has the available letters to make the word
            System.out.println("You can play this word! ");
            counter = 0; //the player hasn't passed his turn by this point of the game
            ArrayList<Tile> playerBag = players[player].getPlayerBagArrayList();
            
            if (firstTurn){
                firstTurn = false;
                boolean isValid = true; String rTemp = ""; int r = 0; String cTemp = ""; int c = 0;
                //ask for positioning of word
                do{
                    System.out.print("Which row would you like the first letter of the word to begin at? ");
                    rTemp = in.nextLine();
                    r = Integer.parseInt(rTemp);
                    if (r < 1 && r > 15) isValid = false;  
                    else isValid = true;
                    
                 } while(!isValid);
                
                do{
                    System.out.print("Which column? ");
                    cTemp = in.nextLine();
                    c = Integer.parseInt(cTemp);
                    if (c < 1 && c > 15) isValid = false; 
                    else isValid = true;
                    
                } while(!isValid);
                
                String orientation = "lol";
                while (orientation.charAt(0) != 'v' && orientation.charAt(0) != 'h'){
                    System.out.print("Place " + wordTest + " vertically or horizontally? Type 'v' for vertical and 'h' for horizontal. ");
                    orientation = in.nextLine();
                    orientation = orientation.toLowerCase();
                }
                
                boolean hasBeenUsed = false;
   
                for (int i = 0; i < wordTest.length(); i++){
                    hasBeenUsed = false;
                    for (int j = playerBag.size() - 1; j > -1; j--){
                        if (wordTest.substring(i,i+1).equals(playerBag.get(j).toString()) && hasBeenUsed == false){
                            if (orientation.charAt(0) == 'v'){
                                players[player].playerBag.get(j).setStatusVertical();//these tiles are vertically oriented
                                players[player].addPoints(players[player].playerBag.get(j).getValue());
                                grid[r][c].placeTile(players[player].playerBag.remove(j));
                                r++;
                            }
                            else {
                                players[player].playerBag.get(j).setStatusHorizontal();//these tiles are horizontally oriented
                                players[player].addPoints(players[player].playerBag.get(j).getValue());
                                grid[r][c].placeTile(players[player].playerBag.remove(j));
                                c++;
                            }
                            hasBeenUsed = true;
                        }
                    }
                }
            }
            else{//not the first turn
                boolean isValid = true; String rTemp = ""; int r = 0; String cTemp = ""; int c = 0;
                //ask for positioning of word
                do{
                    System.out.print("Which row is the letter you'd like your word appended to located at? ");
                    rTemp = in.nextLine();
                    r = Integer.parseInt(rTemp);
                    if (r < 1 && r > 15) isValid = false;  
                    else isValid = true;
                    
                 } while(!isValid);
                
                 do{
                    System.out.print("Which column? ");
                    cTemp = in.nextLine();
                    c = Integer.parseInt(cTemp);
                    if (c < 1 && c > 15) isValid = false; 
                    else isValid = true;
                } while(!isValid);
                
                boolean horizontal = grid[r][c].getTile().getHorizontalStatus();
                boolean vertical = grid[r][c].getTile().getVerticalStatus();
                
                if (horizontal && vertical){
                   System.out.println("Sorry, you can't make this move."); 
                }
                else if (horizontal){
                    boolean hasBeenUsed = false;
                    if(appendToDirection.equalsIgnoreCase("f"))
                        r++;
                    else
                        r-= wordTest.length();
                        
                    for (int i = 0; i < wordTest.length(); i++){
                        hasBeenUsed = false;
                        for (int j = playerBag.size() - 1; j > -1; j--){
                            if (wordTest.substring(i,i+1).equals(playerBag.get(j).toString()) && hasBeenUsed == false){
                                players[player].playerBag.get(j).setStatusVertical();//these tiles are vertically oriented
                                players[player].addPoints(players[player].playerBag.get(j).getValue());
                                grid[r][c].placeTile(players[player].playerBag.remove(j));
                                r++;
                              
                                hasBeenUsed = true;
                            }
                        }
                    }
                }
                else if (vertical){
                    boolean hasBeenUsed = false;
                    if (appendToDirection.equalsIgnoreCase("f"))
                        c++;
                    else
                        c-=wordTest.length();
                        
                    for (int i = 0; i < wordTest.length(); i++){
                        hasBeenUsed = false;
                        for (int j = playerBag.size() - 1; j > -1; j--){
                            if (wordTest.substring(i,i+1).equals(playerBag.get(j).toString()) && hasBeenUsed == false){
                                players[player].playerBag.get(j).setStatusHorizontal();//these tiles are horizontally oriented
                                players[player].addPoints(players[player].playerBag.get(j).getValue());
                                grid[r][c].placeTile(players[player].playerBag.remove(j));
                                c++;
                                
                                hasBeenUsed = true;
                            }
                        }
                    }
                }
                 
            }
            
            //refill player's bag after subtracting letters
            refillPlayerBag(player);
            
            //System.out.println(word + " has been added to the board. ");
            System.out.println();
            
            if (player == 0) {player = 1;}//switch players now   
            else {player = 0;}
            
            wordTest = ""; //reset
        }while(isRunning);
        
    }
    
    public String validWordTest(String word, String appendTo, String appendToDirection){ //tested and verified to work
        boolean invalidWord = true;
       
          do{
                if  (word.equalsIgnoreCase(appendTo + "pass turn") || word.equalsIgnoreCase("pass turn" + appendTo) ){
                    invalidWord = false;
                } 
                else if(!(word_list.contains(appendTo + word)) && appendToDirection.equalsIgnoreCase("f")  || (!(word_list.contains(word + appendTo)) && appendToDirection.equalsIgnoreCase("b"))){ //invalid word
                    System.out.print("Invalid word. Re-enter the letters that can be appended to the letter inputed above. ");
                    invalidWord = true;
                    word = in.nextLine();
                    if (appendTo.isEmpty()){
                        word = word.toUpperCase();   
                    }
                    else{
                        if (appendToDirection.equalsIgnoreCase("b"))
                             word = word.toUpperCase() + appendTo.substring(0,1).toUpperCase();
                        else
                            word = appendTo.substring(0,1).toUpperCase() + word.toUpperCase();
                    }
                }
                else{ //valid word
                    invalidWord = false;
                }
            }while(invalidWord);
            
        return word;
        
    }
    
    public boolean checkPlayerLetters(String word, int p){
        
        ArrayList<Tile> playerBag = players[p].getPlayerBagArrayList();
        int count = 0;
        
        for (int i = 0; i < word.length(); i++){
            
            for (int j = 0; j < playerBag.size(); j++){
                if (word.substring(i,i+1).equals(playerBag.get(j).toString())){
                    count = count + 1;
                }
            }            
            if (count == 0){
                System.out.println("Sorry! You can't play this word with the letters you have. Try again.");
                return false;
            }
            
            count = 0; //reset for next letter in word
            
        }
        
        return true; //all letters in the word are contained inside the playerBag
        
    }
    
    public boolean validMove(int r, int c, String orientation, String word){ 
       
        
        return false;
    }
    
    public void determineWinner(){
        if (players[0].getPoints() > players[1].getPoints())
            System.out.println("Player 1 wins!");
        else if (players[1].getPoints() > players[0].getPoints())
            System.out.println("Player 2 wins!");
        else
            System.out.println("It's a tie!");
    }
    
    //public void graphics
    
    
}
    //STUFF BELOW IS NOT NEEDED, they were only test runs
    /* tests speed of importing data and speed of fetching data from word list
    public void printFileData(){ //print segment of word list
        for (int i = 0; i < 3; i++){
            System.out.println(word_list.get(i));   
        }
        Scanner in = new Scanner(System.in);
        System.out.print("Continue?");
        String statement = in.next();
        if (statement.equals("yes")){
            String test = "zygote";
        
            if(word_list.contains(test)){
            System.out.println(test);
        }
        }
    } */

