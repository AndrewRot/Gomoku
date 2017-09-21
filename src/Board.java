/**
 * Board
 * Created by andrewrot on 9/20/2017.
 *
 */

import java.util.Random;
import java.io.*;
import java.util.*;


public class Board {

    //2d array to keep track of the game board
    char[][] board = new char[15][15];

    //boolean yourTurn; //initialize randomly later on. true if your turn
    

    public Board(){
        initializeBoard(); 
        //this.yourTurn = true;
    }

    //iterate first col, every row in it, then move to next col across.
    private void initializeBoard(){
        for(int i = 0; i < board[0].length; i++){
            for(int j = 0; j < board.length; j++){
                board[i][j] = '_';
            }
        }
    }




    //Check to see if the previous move won the game for either player
    //check the four directions
    public boolean checkWin(int r, int c){

        String symbol = "X";
        //check win for what letter?
        if(true) //fix
            symbol = "X";
        else 
            symbol = "O";

        if (vertical(r, c, symbol)|| horizontal(r, c, symbol) || forwardSlash(r, c, symbol)  || backwardSlash(r, c, symbol)) {
            System.out.println("WININNNER");
            return true;
        }  

        return false;
    }

    //**** need to implement diagnols. find simple way to check
    // check for the / direction
    private boolean forwardSlash(int row, int col, String s){
        int streak = 0;
        //System.out.println("row: "+row + " col: "+col);

        int tempRow = row; // move this down left until it is 0, then start from there.
        int tempCol = col; //same here

        while(tempRow < 14 && tempCol > 0){
            tempRow++;
            tempCol--;
        }

        System.out.println("Start looking from ["+tempRow+"] ["+tempCol+"]");
        //now we have our starting point on the edge of the board, check the diagnol starting here
        while(tempRow >= 0 && tempCol <= 14){ 
            System.out.println("Examine ["+tempRow+"] ["+tempCol+"]  " + "comparing: "+s.charAt(0)+ " and "+ board[tempRow][tempCol]);
            if (s.charAt(0) == board[tempRow][tempCol]) {
                //System.out.println("streak++");
                streak++;
            }
            else 
                streak = 0;
            if(streak == 3)
                return true;
            tempRow--;
            tempCol++;
        }
        return false; 
    }
    // check for the \ direction
    private boolean backwardSlash(int row, int col, String s){
        int streak = 0;

        int tempRow = row; // move this down left until it is 0, then start from there.
        int tempCol = col; //same here

        while(tempCol < 14 && tempRow > 0){
            tempRow++;
            tempCol++;
        }
        //now we have our starting point on the edge of the board, check the diagnol starting here
        while(tempCol >= 0 && tempRow <= 14){ 
            if (s.charAt(0) == board[tempRow][tempCol]) 
                streak++;
            else 
                streak = 0;
            if(streak == 3)
                return true;
            tempRow--;
            tempCol--;
        }
        return false; 
    }
    private boolean vertical(int r, int c, String s){
        int streak = 0;
        for (int i = 0; i < board[0].length; i++) {
            if (s.charAt(0) == board[i][c]) 
                streak++;
            else 
                streak = 0;
            if(streak == 3)
                return true;
        }
        return false; 
    }
     private boolean horizontal(int r, int c, String s){
        int streak = 0;
        for (int i = 0; i < board.length; i++) {
            if (s.charAt(0) == board[r][i]) 
                streak++;
            else 
                streak = 0;
            if(streak == 3)
                return true;
        }
        return false; 
    }

    //iterate first col, every row in it, then move to next col across.
    public LinkedList<Move> findEmpty(){
        LinkedList<Move> empties = new LinkedList<Move>();
        for(int i = 0; i < board[0].length; i++){
            for(int j = 0; j < board.length; j++){
                if(board[i][j] == '_'){
                    Move newSpot = new Move(i, j);
                    empties.add(newSpot);
                }
            }
        }
        return empties;
    }






    //print board
    public void printBoard(){

        System.out.println("     A B C D E F G H I J K L M N O ");
        for(int i = 0; i < board[0].length; i++){
            int row = i+1;
            //spaceing
            if(row < 10)
                System.out.print(row +"  [ ");
            else
                System.out.print(row +" [ ");

            for(int j = 0; j < board.length; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println("]");
        }
        System.out.println(); //throw some pace between prints
    }



}