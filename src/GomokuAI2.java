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


public class GomokuAI2 {

    Board2 gameBoard;
    //char[][] gameBoard.board = new char[15][15]; //2d array to keep track of the game board

    File teamFile;
    File moveFile;


    boolean yourTurn; //initialize randomly later on. true if your turn
    boolean firstTurn; //if it's the computer's first turn -> use for default middle of board move


    HashMap<String, Integer> letterTable; //get int from letter [fast]
    HashMap<Integer, String> integerTable; //get letter from int [fast]

    String teamName = "groupX"; //change once here

    Timer timer;

    MinimaxABPruner2 minimax = new MinimaxABPruner2();

    String validLetters = "ABCDEFGHIJKLMNO";

    Character ourSymbol = 'O';//start O as default
    Character theirSymbol = 'X';

    Move prevMove = new Move(7, 7); //only really used on first turn if they take our move
    int moves = 0;

    public GomokuAI2(){
        gameBoard = new Board2();
        initializeBoard(); //fill it as we go
        this.teamFile = new File(teamName+".go");
        this.moveFile = new File("move_file"); //maybe add .txt
        this.yourTurn = true;
        this.firstTurn = true;
        letterTable = new HashMap<String, Integer>();
        integerTable = new HashMap<Integer, String>();
        populateLetterHashMap();
        populateIntegerHashMap();
        timer = new Timer();

    }

  
    class timerRunOut extends TimerTask {
        public void run() {
            System.out.println("Time's up!");

            //insert code here to write next move to file
            //xxxx

            checkingForGo();
        }
    }

     /* Checking For Go: This is where we wait for groupname.go file to be created.
      * Once the file is found, we will begin our timer.
      * We then call read move to find out where the opponented moved.
      * IF - the timer reaches it's limit, we jump to timerRunOut and submit the best move found so far
      */
    private void checkingForGo()  {
        
        timer.cancel(); //stop the timer, no longer our move

        //wait until the file is created
        System.out.println("Waiting for " +teamName+".go file");
        while(!teamFile.exists()) {  }

        //file has been found! Begin timer 
        timer = new Timer();
        timer.schedule(new timerRunOut(), 9 * 1000); //give us 1 to write a move to the file (reduce this later)

        //boolean x = true;
        //while(x){x = true;} ----Used for testing the timer

        try { readMove(); }
        catch(IOException exc){ System.out.println("exception caught reading moves:" + exc.getMessage()); }
    }
       
    /* Read Move: This reads the newest move from the move_file
     * It then breaks apart the move and determines how it fits onto our gameBoard array
     * After we understand the move, we then want to calculate the next move
    */
    private void readMove()  throws IOException {
        //**THIS stuff might better be initiallized outside/before one time. To save time
        FileInputStream fis = new FileInputStream(moveFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        
        String line = null;
        
        while ((line = br.readLine()) != null) {    


            moves = gameBoard.countMoves();

            //if moves = 0 -> we go first .. we are O
            if(moves == 0 && firstTurn){
                ourSymbol = 'O';
                theirSymbol = 'X';
            }
            //if moves = 1 -> they went first .. we are X
            else if(moves == 1 && firstTurn){
                ourSymbol = 'X';
                theirSymbol = 'O';
            }

            //they took our move - update the board accordingly
            else if(moves == 1){
                gameBoard.board[prevMove.row] [prevMove.col] = theirSymbol;
            }


            System.out.println("readMove:" +line);
            String[] move = line.split(" ");        //collect individual bits of info
            String groupName = move[0];             //argument 0 is the group name
            
            //check to see if valid move was written to the file
            //if(!validLetters.contains(move[1]) && (Integer.parseInt(move[2])-1) != -1){

                int col = letterTable.get(move[1]);     //convert letter to int
                int row = Integer.parseInt(move[2])-1;  //covert string to int
                
                //****determine whos move it was for the move, mark gameboard.board accordingly
                if(!groupName.equals(teamName))
                    gameBoard.board[row] [col] = theirSymbol; //oppoenent mark
                else
                    gameBoard.board[row] [col] = ourSymbol; //our mark

                gameBoard.checkWin(row, col);
            //}
            //otherwise we ignore the move
        }



        br.close(); //Move this with above stuff (if we ever move it)
        gameBoard.printBoard();  //for testing


        if(moves == 0 && firstTurn){
            ourSymbol = 'O';
            theirSymbol = 'X';
        }
        moves++;
        

        //calculate how many moves
        /*moves = gameBoard.countMoves();

        //if moves = 0 -> we go first .. we are O
            if(moves == 0 && firstTurn){
                ourSymbol = 'O';
                theirSymbol = 'X';
            }
            //if moves = 1 -> they went first .. we are X
            else if(moves == 1 && firstTurn){
                ourSymbol = 'X';
                theirSymbol = 'O';
            }

            //they took our move - update the board accordingly
            else if(moves == 1){
                gameBoard.board[prevMove.row] [prevMove.col] = theirSymbol;
            }
*/
        

        calculateMove(); 
    }

        

    //this function calculates the best coordinate for a move, then writes it to the move_file
    public void calculateMove(){

        /* ******OLD LOGIC*******
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
        }*/

        //******UPDATED LOGIC with MINIMAX and AB PRUNES***********
        
        //*****
        char nextPlayer = yourTurn ? 'O' : 'X';
        char p = yourTurn ? 'X' : 'O';


        Board2 brd = new Board2(gameBoard.board, theirSymbol, ourSymbol);
        if(firstTurn){
            Move m = new Move(1,1);
            if(brd.board[7][7] == theirSymbol){
                brd.executeMove(ourSymbol, new Move(7,8));
                m.row = 7;
                m.col = 8;
            }
            else{
                brd.executeMove(ourSymbol, new Move(7,7));
                m.row = 7;
                m.col = 7;
            }
            this.gameBoard = brd;//.board;
            gameBoard.printBoard();
            firstTurn = false;
            yourTurn = true;
            //teamFile.delete(); 
            //getUserMove();


            //Print the move to the move_file
            //generate move and create line to write
            int row = m.row+1;
            String col = integerTable.get(m.col);
            String line = teamName + " " + col + " " + row;
            System.out.println("calculateMove: "+line);

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(moveFile))) {
                bw.write(line);
            } catch(IOException exc) { 
                System.out.println("exception caught write move:" + exc.getMessage()); 
            }

        }
        else {
            Object[] x = minimax.getBestMove(brd, 3, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            Move m = (Move) x[1];
            brd.executeMove(ourSymbol, m);
            //gameBoard.print();
            this.gameBoard = brd;//.board;
            gameBoard.printBoard();
            if(brd.checkWin(m.row, m.col)){
                endOfGame(yourTurn);
            }
            else{
                yourTurn = true;
                //teamFile.delete(); //send move to the ref
                //getUserMove();
            }

            //update our prevmove
            prevMove = m;

            //Print the move to the move_file
            //generate move and create line to write
            int row = m.row+1;
            String col = integerTable.get(m.col);
            String line = teamName + " " + col + " " + row;
            System.out.println("calculateMove: "+line);

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(moveFile))) {
                bw.write(line);
            } catch(IOException exc) { 
                System.out.println("exception caught write move:" + exc.getMessage()); 
            }

        }


        //*** Delete the groupX.go file (for now) -ref will control this later
        //
        //teamFile.delete();

        //check for next move
        //checkingForGo();

    }



    private void endOfGame(boolean playerTurn){
        if(playerTurn)
            System.out.println("You have won the game!");
        else 
            System.out.println("You lost to AI!");
    }
 


    public static void main(String[] args){
        GomokuAI2 g = new GomokuAI2();;
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


        letterTable.put("a", 0);
        letterTable.put("b", 1);
        letterTable.put("c", 2);
        letterTable.put("d", 3);
        letterTable.put("e", 4);
        letterTable.put("f", 5);
        letterTable.put("g", 6);
        letterTable.put("h", 7);
        letterTable.put("i", 8);
        letterTable.put("j", 9);
        letterTable.put("k", 10);
        letterTable.put("l", 11);
        letterTable.put("m", 12);
        letterTable.put("n", 13);
        letterTable.put("o", 14);
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