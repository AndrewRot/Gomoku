/**
 * Board
 * Created by andrewrot on 9/20/2017.
 *
 */

import java.util.Random;
import java.io.*;
import java.util.*;


public class Move {

    int row;
    int col;

    public Move(int r, int c){
        this.row = r;
        this.col = c;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Move)) {
            return false;
        }

        Move move = (Move) o;

        return move.row == row && move.col == col;
    }

    //Idea from effective Java : Item 9
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + row;
        result = 31 * result + col;
        return result;
    }

}