/**
 * Gomoku
 * Created by andrewrot on 9/4/2017.
 *
 */

import java.util.Random;
import java.io.*;
import java.util.*;


public class Gomoku {

    //2d array to keep track of the game board
    char[][] gameBoard = new char[15][15];

    boolean yourTurn; //initialize randomly later on. true if your turn
    boolean firstTurn; //if it's the computer's first turn -> use for default middle of board move

    MinimaxABPruner minimax = new MinimaxABPruner();

    public Gomoku(){
        initializeBoard(); 
        this.yourTurn = true;
        this.firstTurn = true;
    }

    //iterate first col, every row in it, then move to next col across.
    private void initializeBoard(){

        System.out.println("     A B C D E F G H I J K L M N O ");
        for(int i = 0; i < gameBoard[0].length; i++){
            int row = i+1;
            //spaceing
            if(row < 10)
                System.out.print(row +"  [ ");
            else
                System.out.print(row +" [ ");

            for(int j = 0; j < gameBoard.length; j++){
                gameBoard[i][j] = '_';
                System.out.print(gameBoard[i][j] + " ");
            }
            System.out.println("]");
        }
    }


    public void getUserMove(){

        Scanner scanner = new Scanner(System.in);

        try{
            while(yourTurn){
                System.out.print("Your move [format 'col row']: ");
                String line = scanner.nextLine();   

                //manipulate gameboard with your inserted move
                //Maybe going to neeed to store this value or write it to a file later
                String[] move = line.split(" ");
                int col = convertToIndex(move[0]);
                int row = Integer.parseInt(move[1])-1;
                //convert first input from letter to index. Convert second from string to int, - 1 to account for real index of the board starting at 0, even tho labeled 1
                gameBoard[row] [col] = 'X';

                printBoard();

                char nextPlayer = yourTurn ? 'O' : 'X';
                char p = yourTurn ? 'X' : 'O';
                Board brd = new Board(gameBoard, nextPlayer, p);

                //check for win
                if(brd.checkWin(row, col))
                    endOfGame(yourTurn);
                else{ //keep playing, 
                    yourTurn = false;
                    getComputerMove();
                }
            }
        } catch (Exception exc){
            System.out.println("exception caught in Gomoku:" + exc.getMessage());
        } 
    }

    private void endOfGame(boolean playerTurn){
        if(playerTurn)
            System.out.println("You have won the game!");
        else 
            System.out.println("You lost to AI!");
    }

    public void getComputerMove(){
        //generate AI for computers next move.

        /*
        for (int i = 0; i < gameBoard[0].length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                if (gameBoard[i][j] == '_') {
                    //moves[0] = i;
                    //moves[1] = j;
                    gameBoard[i][j] = 'O';
                    printBoard();

                    //check for win
                    if(checkWin(i, j))
                        endOfGame(yourTurn);
                    else{ //keep playing, 
                        yourTurn = true;
                        
                        getUserMove();
                    }
                }
            }
        }
        //gets to here, board is full
        */
        char nextPlayer = yourTurn ? 'O' : 'X';
        char p = yourTurn ? 'X' : 'O';
        Board brd = new Board(gameBoard, nextPlayer, p);
        if(firstTurn){
            if(brd.board[7][7] == 'X'){
                brd.executeMove('O', new Move(7,8));
            }
            else{
                brd.executeMove('O', new Move(7,7));
            }
            this.gameBoard = brd.board;
            printBoard();
            firstTurn = false;
            yourTurn = true;
            getUserMove();
        }
        else {
            Object[] x = minimax.getBestMove(brd, 3, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            Move m = (Move) x[1];
            brd.executeMove(p, m);
            //gameBoard.print();
            this.gameBoard = brd.board;
            printBoard();
            if(brd.checkWin(m.row, m.col)){
                endOfGame(yourTurn);
            }
            else{
                yourTurn = true;
                getUserMove();
            }
        }
    }




    //convert character to an index
    private int convertToIndex(String c){
        String cols = "ABCDEFGHIJKLMNO";
        for(int i = 0; i < cols.length(); i++){
            if(cols.charAt(i) == c.charAt(0))
                return i;
        }
        return 0; //*update this to throw an error if letter not valid.
    }



     //might re-use some of this code to read from the groupname.go file
    private void readFile(File fin) throws IOException {
        //FileInputStream fis = new FileInputStream(fin);

        //Construct BufferedReader from InputStreamReader
        /*BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            String[] vals = line.split(" ");
            if(vals.length == 3){
                char fromVal = vals[0].charAt(0);
                char toVal = vals[1].charAt(0);
                float d = Float.parseFloat(vals[2]);
                graph.addEdge(fromVal, toVal, d);
                // add the reverse direction as well
                graph.addEdge(toVal, fromVal, d);
                if(fromVal == 'S' || toVal == 'S'){
                    graph.src = new Node(fromVal, 0);
                }
                if(toVal == 'G'){
                    graph.goal = new Node(toVal, d);
                }
                if(fromVal == 'G'){
                    graph.goal = new Node(fromVal);
                }
            }
            else if(vals.length == 2){
                char val = vals[0].charAt(0);
                float h = Float.parseFloat(vals[1]);
                graph.addHeuristic(val, h);
            }
        }

        br.close();*/
    }


    public static void main(String[] args){

        //System.out.println("Arg length: " + args.length);

        Gomoku g;

        if (args.length == 0) {
           g = new Gomoku();
        } else {
            g = new Gomoku();
            //g = new Gomoku(args[0]) );
        } 


        //start game with your move or the cpus
        if(g.yourTurn)
            g.getUserMove();
        else
            g.getComputerMove();


        /*SearchStrategies s = new SearchStrategies();
        // read file into adjacency list representation
        File dir = new File(".");
        try {
            File graph = new File(dir.getCanonicalPath() + File.separator + "graph.txt");
            s.readFile(graph);
        }
        catch(IOException exc){
            System.out.println("exception caught:" + exc.getMessage());
        }

        //Begin searches...

        //Depth 1st search
        try {
            DepthFirstSearch dfs = new DepthFirstSearch();
            s.General_Search(s.graph, dfs);
        }
        catch(Exception exc){
            System.out.println("exception caught in dfs:" + exc.getMessage());
        }*/


    }

    //print board
    private void printBoard(){

        System.out.println("     A B C D E F G H I J K L M N O ");
        for(int i = 0; i < gameBoard[0].length; i++){
            int row = i+1;
            //spaceing
            if(row < 10)
                System.out.print(row +"  [ ");
            else
                System.out.print(row +" [ ");

            for(int j = 0; j < gameBoard.length; j++){
                System.out.print(gameBoard[i][j] + " ");
            }
            System.out.println("]");
        }
        System.out.println(); //throw some pace between prints
    }



}