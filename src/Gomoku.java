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
    

    public Gomoku(){
        initializeBoard(); 
        this.yourTurn = true;
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
                System.out.println("Your move: ");
                String line = scanner.nextLine();   

                //manipulate gameboard with your inserted move
                //to implement


                yourTurn = false;
                getComputerMove();
            }
        } catch (Exception exc){
            System.out.println("exception caught in Gomoku:" + exc.getMessage());
        }
        
    }

    public void getComputerMove(){
        //generate AI for computers next move.
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
}