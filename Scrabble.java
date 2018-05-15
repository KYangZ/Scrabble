
/**
 * Write a description of class Scrabble here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;


public class Scrabble extends JFrame {
    
    private JPanel p = new JPanel();
    public Square[][] squares = new Square[15][15];
    private int playerTurn = 0;
    
    private ArrayList<String> word_list = new ArrayList<String>();
    private ArrayList<String> bag = new ArrayList<String>();
    
    public ArrayList<Tile> tilesPlayed = new ArrayList<Tile>();
    public ArrayList<Tile> tempWord = new ArrayList<Tile>();
    
    private Scanner in = new Scanner(System.in);
    public Player[] players = new Player[2];
    private Random rand = new Random();

    public Scrabble(){
        super("Scrabble");
        setSize(800,800);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        p.setLayout(new GridLayout(15,15));
        
        for(int y = 0; y < 15; y++){
            for(int x = 0; x < 15; x++){
                squares[x][y] = new Square(x,y);
                p.add(squares[x][y]);
            }
        }

        add(p);
        setVisible(true);
        
        players[0] = new Player(); 
        players[1] = new Player();
        
        importWordList();
        distributeLetterBag();
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
        } catch (IOException io){
             System.out.println("Error: " + io.getMessage());   
        }
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
    
    public void runScrabble(){
        boolean isRunning = true;
        boolean valid = false;
        boolean passTurn = false;
        String wordTest = "";
        int counter = 0; //counts number of consecutive passes
        String appendTo = "";
        boolean firstTurn = true;
        
        System.out.println("GAME START!");
        
        while(isRunning){
            
            printPlayerLettersAndPoints();
            
            
  
            valid = false;
            
            while(!valid){
                System.out.println("Player " + (playerTurn+1) + "'s turn!");
                System.out.println("Click a tile on the board to play a letter!");
                System.out.println("Words must be formed vertically or horizontally, on existing words");
                System.out.println("When you are finished placing tiles on the board, hit 'enter' on the keyboard, or enter 'pass' to pass your turn");
                wordTest = in.nextLine().toUpperCase().trim();
                
                if(wordTest.equals("PASS")){
                    counter++;
                    valid = true;
                } else {
                    //NOTE TO SELF: PUT THIS LATER WHEN WORD CHECK IS COMPLETE
                    counter = 0;
                    
                    
                    String testWord = "";
                    
                    int column = tempWord.get(0).location.xpos;
                    int row = tempWord.get(0).location.ypos;
                    boolean vertical = true;
                    boolean horizontal = true;
                    
                    System.out.println(tempWord);
                    for(Tile t : tempWord){
                        
                        if(row != t.location.ypos){
                            horizontal = false;
                        }
                        
                        if(column != t.location.xpos){
                            vertical = false;
                        }

                        //testWord += t.toString().toUpperCase();
                    }
                    
                    if(!vertical && !horizontal){
                        System.out.println("Your word is not in a line. Please try again.");
                        
                        for(int i = 0; i < tempWord.size(); i++){
                                    if(!tilesPlayed.contains(tempWord.get(i))){
                                        tempWord.get(i).location.reset(); 
                                        players[playerTurn].playerBag.add(tempWord.remove(i));
                                        i--;
                                    }
                                }
                        
                    } else {
                        //other condition (is it a word??)
                        if(horizontal){
                            //find the leftmost (minimum xpos) square, then read to the right
 
                            for(int i = 0; i < tempWord.size(); i++){
                                for(int j = 0; j < tempWord.size() - i - 1; j++){
                                    if(tempWord.get(j).location.xpos > tempWord.get(j+1).location.xpos){
                                        Tile temp = tempWord.get(j);
                                        tempWord.set(j,tempWord.get(j+1)); 
                                        tempWord.set(j+1,temp);
                                    }
                                }
                            }
                            
                            
                            try{
                                while(!squares[tempWord.get(0).location.xpos - 1][row].letter.equals("")){
                                    tempWord.add(0,squares[tempWord.get(0).location.xpos - 1][row].t);
                                    System.out.println(tempWord);
                                }
                            } catch (java.lang.IndexOutOfBoundsException e){
                            }
                            
                            
                            
                            for(Tile t : tempWord){
                                testWord += t.toString();
                            }
                        
                            
                            System.out.println(testWord);
                            if(word_list.contains(testWord)){
                                valid = true;
                                System.out.println("Valid Word!");
                                //increment points
                                
                                //adds to tiles played
                                for(Tile t : tempWord){
                                    t.location.t = t;
                                    tilesPlayed.add(t);
                                }   
                        
                            } else {
                                System.out.println("Inavlid word. Try Again.");
                                for(int i = 0; i < tempWord.size(); i++){
                                    if(!tilesPlayed.contains(tempWord.get(i))){
                                        tempWord.get(i).location.reset(); 
                                        players[playerTurn].playerBag.add(tempWord.remove(i));
                                        i--;
                                    }
                                }
                            }
                            
                            horizontal = false;
                        } else if(vertical){
                            for(int i = 0; i < tempWord.size(); i++){
                                for(int j = 0; j < tempWord.size() - i - 1; j++){
                                    if(tempWord.get(j).location.ypos > tempWord.get(j+1).location.ypos){
                                        Tile temp = tempWord.get(j);
                                        tempWord.set(j,tempWord.get(j+1)); 
                                        tempWord.set(j+1,temp);
                                    }
                                }
                            }
                            
                            System.out.println(tempWord);
                            try{
                                while(!squares[column][tempWord.get(0).location.ypos - 1].letter.equals("")){
                                    tempWord.add(0,squares[column][tempWord.get(0).location.ypos - 1].t);
                                    System.out.println(tempWord);
                                }
                            } catch (java.lang.IndexOutOfBoundsException e){
                            }
                            
                            for(Tile t : tempWord){
                                testWord += t.toString();
                            }
                            
                            System.out.println(testWord);
                            if(word_list.contains(testWord)){
                                valid = true;
                                System.out.println("Valid Word!");
                                //increment points
                                
                                //adds to tiles played
                                for(Tile t : tempWord){
                                    t.location.t = t;
                                    tilesPlayed.add(t);
                                }
                                
                                 
                        
                            } else {
                                System.out.println("Inavlid word. Try Again.");
                                for(int i = 0; i < tempWord.size(); i++){
                                    if(!tilesPlayed.contains(tempWord.get(i))){
                                        tempWord.get(i).location.reset(); 
                                        players[playerTurn].playerBag.add(tempWord.remove(i));
                                        i--;
                                    }
                                }
                                
                            }
                            vertical = false;
                        }
                        
                        tempWord.clear();
                        refillPlayerBag(playerTurn);
                        //refill the player bag
                    }
                    
                    
                    
                    
                }
            }

            //switches off to the other player
            if(playerTurn == 0){
                playerTurn = 1;
            } else if(playerTurn == 1){ //it has to be one if not zero
                playerTurn = 0;
            }
            
            //ends the game when both players pass a turn
            if(counter >= 2){
                System.out.println("Both players have passed. The game will now end.");
                isRunning = false;
            } 
            
        }
    }
    
    public int getTurn(){
        return playerTurn;
    }
}
