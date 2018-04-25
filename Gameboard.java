import java.util.*;
import java.io.*;
/**
 * Write a description of class Gameboard here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Gameboard
{
    // instance variables - replace the example below with your own
  
    private ArrayList<String> word_list = new ArrayList<String>();
    private int w = 15;
    private int h = 15;
    private Square[][] grid = new Square[w+2][h+2]; //15x15 grid with one-square border around it
    
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
        
        /* importWordList();
        String test = "zygote";
        
        if(word_list.contains(test)){
            System.out.println(test);
        }
        */
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
             System.out.println(grid[i][j]);
            }
        }
        
    }
    
    public void runScrabble(){
        importWordList();
        boolean isRunning = true;
        do{
            output_grid();
            
            
            
            
            
            
        }while(isRunning);
        
    }
    
 //   public void graphics
    
    
    public void printFileData(){ //print segment of word list
        for (int i = 0; i < 3; i++){
            System.out.println(word_list.get(i));   
        }
    }
}
