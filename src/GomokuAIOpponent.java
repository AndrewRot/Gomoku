/**
 * Gomoku
 * Created by andrewrot on 9/18/2017.
 *
 * The flow of this program is as follows:
 *
 * checkingForGo -> readMove -> calculateMove
 *
 * checkingForGo: We wait in the checkingForGo file function
 * readMove: Read the move_file and add a move to the gameboard.board
 * calculateMove: determine best place to move and write the move to move_file
 * 
 * ... loop back to top
 */

import java.io.File;
import java.util.Random;
import java.io.*;
import java.util.*;


public class GomokuAIOpponent {

    Board gameBoard;
    //char[][] gameBoard.board = new char[15][15]; //2d array to keep track of the game board

    File teamFile;
    File moveFile;

    HashMap<String, Integer> letterTable; //get int from letter [fast]
    HashMap<Integer, String> integerTable; //get letter from int [fast]

    String teamName = "groupX"; //change once here
    

    public GomokuAIOpponent(){
        gameBoard = new Board();
        initializeBoard(); //fill it as we go
        this.teamFile = new File(teamName+".go");
        this.moveFile = new File("move_file"); //maybe add .txt
        letterTable = new HashMap<String, Integer>();
        integerTable = new HashMap<Integer, String>();
        populateLetterHashMap();
        populateIntegerHashMap();
    }


     //might re-use some of this code to read from the groupname.go file
    private void checkingForGo()  {
        //wait until the file is created
        System.out.println("Waiting for " +teamName+".go file");
        while(!teamFile.exists()) {  }

        try { readMove(); }
        catch(IOException exc){ System.out.println("exception caught reading moves:" + exc.getMessage()); }
    }
       

    private void readMove()  throws IOException {
        //**THIS stuff might better be initiallized outside/before one time. To save time
        FileInputStream fis = new FileInputStream(moveFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        
        String line = null;
        while ((line = br.readLine()) != null) {    
            System.out.println("readMove:" +line);
            String[] move = line.split(" ");        //collect individual bits of info
            String groupName = move[0];             //argument 0 is the group name
            int col = letterTable.get(move[1]);     //convert letter to int
            int row = Integer.parseInt(move[2])-1;  //covert string to int
            
            //determine whos move it was for the move, mark gameboard.board accordingly
            if(!groupName.equals(teamName))
                gameBoard.board[row] [col] = 'O'; //oppoenent mark
            else
                gameBoard.board[row] [col] = 'X'; //our mark
        }
        br.close(); //Move this with above stuff (if we ever move it)
        gameBoard.printBoard();  //for testing
        calculateMove(); 
    }

        

    //this function calculates the best coordinate for a move, then writes it to the move_file
    public void calculateMove(){
        //generate AI for computers next move.
        outerloop: 
        for (int i = 0; i < gameBoard.board[0].length; i++) {
            for (int j = 0; j < gameBoard.board.length; j++) {
                if (gameBoard.board[i][j] == '_') {

                    gameBoard.board[i][j] = 'X'; 

                    //generate move and create line to write
                    int row = i+1;
                    String col = integerTable.get(j);
                    String line = teamName + " " + col + " " + row;
                    System.out.println("calculateMove: "+line);

                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(moveFile))) {
                        bw.write(line);
                    } catch(IOException exc) { 
                        System.out.println("exception caught write move:" + exc.getMessage()); 
                    }

                    break outerloop;

                }
            }
        }

        //*** Delete out groupX.go file (for now) -ref will control this later
        teamFile.delete();

        //check for next move
        checkingForGo();

    }



 


    public static void main(String[] args){
        GomokuAIOpponent g = new GomokuAIOpponent();;
        g.checkingForGo();
    }



    //iterate first col, every row in it, then move to next col across.
    private void initializeBoard(){
        for(int i = 0; i < gameBoard.board[0].length; i++){
            for(int j = 0; j < gameBoard.board.length; j++){
                gameBoard.board[i][j] = '_';
            }
        }
    }

/*
    private void printBoard(){
        System.out.println("     A B C D E F G H I J K L M N O ");
        for(int i = 0; i < gameBoard.board[0].length; i++){
            int row = i+1;
            //spaceing
            if(row < 10)
                System.out.print(row +"  [ ");
            else
                System.out.print(row +" [ ");

            for(int j = 0; j < gameBoard.board.length; j++){
                System.out.print(gameBoard.board[i][j] + " ");
            }
            System.out.println("]");
        }
        System.out.println(); //throw some pace between prints
    }
*/

   

    private void populateLetterHashMap(){
        letterTable.put("A", 0);
        letterTable.put("B", 1);
        letterTable.put("C", 2);
        letterTable.put("D", 3);
        letterTable.put("E", 4);
        letterTable.put("F", 5);
        letterTable.put("G", 6);
        letterTable.put("H", 7);
        letterTable.put("I", 8);
        letterTable.put("J", 9);
        letterTable.put("K", 10);
        letterTable.put("L", 11);
        letterTable.put("M", 12);
        letterTable.put("N", 13);
        letterTable.put("O", 14);
    }
    private void populateIntegerHashMap(){
        integerTable.put(0,"A");
        integerTable.put(1,"B");
        integerTable.put(2,"C");
        integerTable.put(3,"D");
        integerTable.put(4,"E");
        integerTable.put(5,"F");
        integerTable.put(6,"G");
        integerTable.put(7,"H");
        integerTable.put(8,"I");
        integerTable.put(9,"J");
        integerTable.put(10,"K");
        integerTable.put(11,"L");
        integerTable.put(12,"M");
        integerTable.put(13,"N");
        integerTable.put(14,"O");
    }



}