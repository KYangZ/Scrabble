
/**
 * The Scrabble Class - contains instructions for running the game
 * (word checking, win conditions, etc.)
 *
 * @Kristy Lee, Kory Yang
 * @version 05152018
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
                System.out.println("When you are finished placing tiles on the board, hit 'enter' on the keyboard.");
                System.out.println("Or enter anything else to pass your turn");
                wordTest = in.nextLine().toUpperCase().trim();
                
                if(!wordTest.equals("")){
                    counter++;
                    valid = true;
                } else {
                    
                    String testWord = "";
                    
                    boolean vertical = true;
                    boolean horizontal = true;
                    
                    //System.out.println(tempWord);
                    
                    if(tempWord.size() <= 1){
                        System.out.println("You have to place at least two letters!");
                        horizontal = true;
                        vertical = true;
                        eraseWord();
                    } else {
                        
                        int column = tempWord.get(0).location.xpos;
                        int row = tempWord.get(0).location.ypos;
                        
                        for(Tile t : tempWord){ 
                            if(row != t.location.ypos){
                                horizontal = false;
                            }
                            
                            if(column != t.location.xpos){
                                vertical = false;
                            }
                        }
                    
                        if(!vertical && !horizontal){
                            System.out.println("Your word is not in a line. Please try again.");
                            eraseWord();
                            
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
                                
                                boolean intersects = false;
                                
                                try{
                                    while(!squares[tempWord.get(0).location.xpos - 1][row].letter.equals("")){
                                        tempWord.add(0,squares[tempWord.get(0).location.xpos - 1][row].t);
                                        //System.out.println(tempWord);
                                        intersects = true;
                                    }
                                } catch (java.lang.NullPointerException | java.lang.IndexOutOfBoundsException e){
                                   
                                }
                                
                                try{
                                    while(!squares[tempWord.get(tempWord.size() - 1).location.xpos + 1][row].letter.equals("")){
                                        tempWord.add(squares[tempWord.get(tempWord.size() - 1).location.xpos + 1][row].t);
                                        //System.out.println(tempWord);
                                        intersects = true;
                                    }
                                } catch (java.lang.NullPointerException | java.lang.IndexOutOfBoundsException e){
                                   
                                }
                                
                                int start = tempWord.get(0).location.xpos;
                                int end = tempWord.get(tempWord.size() - 1).location.xpos;
                                
                                try{
                                    int index = 0;
                                    for(int i = start; i <= end; i++){                                        
                                        //System.out.println("Run " + index);
                                        if(i != tempWord.get(index).location.xpos){
                                            //System.out.println(i + "," + tempWord.get(index).location.xpos);
                                            for(Tile t : tilesPlayed){
                                                if(t.location.ypos == row){
                                                    intersects = true;
                                                    tempWord.add(index,t);
                                                }
                                            }
                                        }
                                       index++;
                                    }
                                
                                } catch (java.lang.IndexOutOfBoundsException e){
                                    System.out.println("failure");
                                }
                                
                                if(!intersects && tilesPlayed.size() > 0){
                                   System.out.println("Your word isn't connected!");
                                   eraseWord();
                                }

                                for(Tile t : tempWord){
                                    testWord += t.toString();
                                }
                            
                                
                                System.out.println("Player " + (getTurn() + 1) + " played: " + testWord);
                                
                                if(word_list.contains(testWord)){
                                    valid = true;
                                    System.out.println("Valid Word!");
                                    //increment points
                                    //adds to tiles played
                                    
                                    int pointValue = 0;
                                    
                                    for(Tile t : tempWord){
                                        t.location.t = t;
                                        players[getTurn()].addPoints(t.getValue());
                                        pointValue += t.getValue();
                                        tilesPlayed.add(t);
                                    }
                                    
                                    counter = 0;
                                    System.out.println("Player " + (getTurn() + 1) + " played " + testWord + " for " + pointValue + " points. ");
                                } else {
                                    System.out.println("Invalid word. Try Again.");
                                    eraseWord();
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
                                
                                //System.out.println(tempWord);
                                
                                boolean intersects = false;
                                
                                try{
                                    while(!squares[column][tempWord.get(0).location.ypos - 1].letter.equals("")){
                                        tempWord.add(0,squares[column][tempWord.get(0).location.ypos - 1].t);
                                        //System.out.println(tempWord);
                                        intersects = true;
                                    }
                                } catch (java.lang.NullPointerException | java.lang.IndexOutOfBoundsException e){
                                }
                                
                                try{
                                    while(!squares[column][tempWord.get(tempWord.size() - 1).location.ypos + 1].letter.equals("")){
                                        tempWord.add(squares[column][tempWord.get(tempWord.size() - 1).location.ypos + 1].t);
                                        System.out.println(tempWord);
                                        intersects = true;
                                    }
                                } catch (java.lang.NullPointerException | java.lang.IndexOutOfBoundsException e){
                                   
                                }

                                int start = tempWord.get(0).location.ypos;
                                int end = tempWord.get(tempWord.size() - 1).location.ypos;
                                
                                try{
                                    int index = 0;
                                    for(int i = start; i <= end; i++){                                        
                                        //System.out.println("Run " + index);
                                        if(i != tempWord.get(index).location.ypos){
                                            //System.out.println(i + "," + tempWord.get(index).location.xpos);
                                            for(Tile t : tilesPlayed){
                                                if(t.location.xpos == column){
                                                    intersects = true;
                                                    tempWord.add(index,t);
                                                }
                                            }
                                        }
                                       index++;
                                    }
                                
                                } catch (java.lang.IndexOutOfBoundsException e){
                                    System.out.println("failure");
                                }
                                
                                if(!intersects && tilesPlayed.size() > 0){
                                   System.out.println("Your word isn't connected!");
                                   eraseWord();
                                }
                                
                                
                                for(Tile t : tempWord){
                                    testWord += t.toString();
                                }
                                
                                System.out.println("Player " + (getTurn() + 1) + " played: " + testWord);
                                
                                if(word_list.contains(testWord)){
                                    valid = true;
                                    System.out.println("Valid Word!");
                                    //increment points
                                    //adds to tiles played
                                    
                                    
                                    
                                    int pointValue = 0;
                                    
                                    for(Tile t : tempWord){
                                        t.location.t = t;
                                        players[getTurn()].addPoints(t.getValue());
                                        pointValue += t.getValue();
                                        tilesPlayed.add(t);
                                    }
                                    
                                    counter = 0;
                                    System.out.println("Player " + (getTurn() + 1) + " played " + testWord + " for " + pointValue + " points. ");
                                } else {
                                    System.out.println("Inavlid word. Try Again.");
                                    eraseWord();
                                }
                                vertical = false;
                            }
                            tempWord.clear();
                            refillPlayerBag(playerTurn);
                        }
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
                System.out.println("Both players have passed. The game is now over.");
                isRunning = false;

                if(players[0].getPoints() > players[1].getPoints()){
                    System.out.println("Player 1 wins!");
                } else if (players[0].getPoints() < players[1].getPoints()){
                    System.out.println("Player 2 wins!");
                } else {
                    System.out.println("It's a draw!");
                }
            } 
            
        }
    }
    
    public int getTurn(){
        return playerTurn;
    }
    
    public void eraseWord(){
        for(int i = 0; i < tempWord.size(); i++){
            if(!tilesPlayed.contains(tempWord.get(i))){
                tempWord.get(i).location.reset(); 
                players[playerTurn].playerBag.add(tempWord.remove(i));
                i--;
            }
        }
    }
    
    
}
