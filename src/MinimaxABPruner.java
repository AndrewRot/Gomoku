import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bwolfson on 9/22/2017.
 */
public class MinimaxABPruner {

    boolean testing = false;

    double evaluate(Board brd){
        int oneAway = brd.evalStatus(brd.prevPlayer, 1);
        int twoAway = brd.evalStatus(brd.prevPlayer, 2);
        int threeAway = brd.evalStatus(brd.prevPlayer, 3);
        double score = oneAway * 100.0 + twoAway * 5.0 + threeAway * 1.0;
        //System.out.println("Score: "+score);
        return score;
    }

    Object[] getBestMove(Board brd, int depth, double alpha, double beta){
        ArrayList<Move> moveList;
        Set<Move> moves = new HashSet<Move>();
        LinkedList<Move> occupied = brd.getPlayerPlaces(brd.nextPlayer);
        for(int i = 0; i < occupied.size(); i++) {
            moves.addAll(brd.lookAround(occupied.get(i)));
        }
        moves.retainAll(brd.findEmpty());
        if(moves.isEmpty()){
            moveList = new ArrayList<Move>(brd.findEmpty());
        }
        else{
            moveList = new ArrayList<Move>(moves);
        }

        Double bestScore;
        Object[] temp;
        Double tempScore;
        Move bestMove = new Move(-1, -1);

        //eval leaf node
        if(depth == 0){
            Move curr = moveList.get(0);
            Move test = new Move(2,6);
            Move test2 = new Move(9,6);
            if(curr.equals(test) || curr.equals(test2)){
                test = new Move(-1,-1);
                testing = true;
            }
            Object[] o = { evaluate(brd), curr};
            return o;
        }
        bestScore = alpha;
        while(moveList.size() > 0){
            Board newBrd = new Board(brd.board, brd.nextPlayer, brd.prevPlayer);
            Move newMove = moveList.get(0);
            newBrd.executeMove(newBrd.nextPlayer, newMove);
            temp = getBestMove(newBrd, depth - 1, -beta, -bestScore);
            tempScore = -(Double) temp[0];
            if(tempScore > bestScore){
                bestScore = tempScore;
                bestMove = newMove;
            }
            if(bestScore > beta){
                Object[] o = { bestScore, bestMove};
                return o;
            }
            moveList.remove(0);
        }
        Object[] o = {bestScore, bestMove};
        return o;
    }
}
