package ai;

import kalaha.GameState;

public class MiniMaxSearch {
    /*
    *estimated by Player 1's scores
     */
    public static int playerEstimation=1;
    public static int searchScores(GameState currentBoard){
        if (currentBoard.gameEnded()){
            return currentBoard.getScore(playerEstimation);
        }
        int currentNextPlayer=currentBoard.getNextPlayer();
        int score=0;
        int bestMove=-1;
        for (int i=0;i<6;i++){//TODO improve to random
            GameState newState=currentBoard.clone();
            if(newState.makeMove(i)){
                int newScore=searchScores(newState);
                //int player=newState.getNextPlayer();
                if (currentNextPlayer!=playerEstimation && newScore<score){
                    //find minimum
                    score=newScore;
                    bestMove=i;
                }else
                    if (currentNextPlayer==playerEstimation && newScore>score){
                        //find maximum
                        score=newScore;
                        bestMove=i;
                    }
            }
        }
        return score;
    }

    public static void main(String[] args){

    }
}
