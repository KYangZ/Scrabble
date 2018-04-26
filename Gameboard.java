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
    
    /**
     * Constructor for objects of class Gameboard
     */
    public Gameboard()
    {
       for (int i = 0; i < w+2; i++){
         for (int j = 0; j < h+2; j++){
             grid[i][j] = new Square(i, j);
            }
        }
           
       players[0] = new Player(); players[1] = new Player();
            
       importWordList();
       distributeLetterBag(); 
    }

    public void makeLetterBag(){ //populates bag with all the letters found in Scrabble
        for (int i = 0; i < 9; i++){
            bag.add("a"); bag.add("i");
        }
        for (int i = 0; i < 12; i++) bag.add("e"); 
        for (int i = 0; i < 8; i++) bag.add("o");
        for (int i = 0; i < 6; i++){
            bag.add("n"); bag.add("r"); bag.add("t");
        }
        for (int i = 0; i < 4; i++){
            bag.add("l"); bag.add("s"); bag.add("u"); bag.add("d");
        }
        for (int i = 0; i < 3; i++) bag.add("g");
        for (int i = 0; i < 2; i++){
            bag.add("b"); bag.add("c"); bag.add("m"); bag.add("p"); bag.add("f"); 
            bag.add("h"); bag.add("v"); bag.add("v"); bag.add("w"); bag.add("y");
        }
        bag.add("k"); bag.add("j"); bag.add("x"); bag.add("q"); bag.add("z");   
    }
    
       public void importWordList(){ //import list of valid words
        try{
             Scanner sc = new Scanner(new File ("words_alpha.txt"));
             String word = "";
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
            System.out.print("    " + i + " ");
            for (int j = 0; j < h+1; j++){ //excludes printing of the "out of bounds" border
                System.out.print(grid[i][j]); 
            }
            System.out.println();
        }
    }
    
    public void runScrabble(){
        boolean isRunning = true;
        do{
            printPlayerLettersAndPoints(); //prints player's available tiles and the points he/she has
            output_grid();
            System.out.print("What word would you like to include on the board?");
            String wordTest = in.next();
            String word = validWordTest(wordTest);
            //passed valid word test at this point
            
            //System.out.println(word);
            
            
            
            
        }while(isRunning);
        
    }
    
    public String validWordTest(String word){ //tested and verified
        boolean invalidWord = true;
        do{
            //if(Collections.binarySearch(word_list, word) == -1){
            if (!(word_list.contains(word))){ //invalid word
                System.out.print("Invalid word. Re-enter a correct word.");
                invalidWord = true;
                word = in.next();
            }
            else{ //valid word
                invalidWord = false;
            }
        }while(invalidWord);
        
        return word;
    }
    
    public boolean validMove(){ 
        
        
        return false;
    }
    
    //public void graphics
    
    
    
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
}
