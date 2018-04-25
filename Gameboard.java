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
    private ArrayList<String> bag = new ArrayList<String>();
    private int w = 15;
    private int h = 15;
    private Square[][] grid = new Square[w+2][h+2]; //15x15 grid with one-square border around it
    private Scanner in = new Scanner(System.in);
    
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
        
        importWordList();
        
        
        
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
    
    public void output_grid(){
        for (int i = 1; i < w+1; i++){ //excludes printing of the "out of bounds" border
            for (int j = 0; j < h+1; j++){ //excludes printing of the "out of bounds" border
             System.out.print(grid[i][j]);
            }
            System.out.println();
        }
        
    }
    
    public void runScrabble(){
        boolean isRunning = true;
        do{
            //output_grid();
            System.out.print("What word would you like to include on the board?");
            String wordTest = in.next();
            String word = validWordTest(wordTest);
            //passed valid word test
            //System.out.println(word);
            
            
            
            
        }while(isRunning);
        
    }
    
    public String validWordTest(String word){
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
    
    //public void graphics
    
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
