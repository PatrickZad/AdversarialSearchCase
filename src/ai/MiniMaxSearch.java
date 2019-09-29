package ai;

import kalaha.GameState;

public class MiniMaxSearch {
    /*
    *estimated by Player 1's scores
     */
    public static int playerEstimation=1;
    public static int depth=0;
    public static int threshould=6;
    public static int searchScores(GameState currentBoard, int depth){
        System.out.println("Search "+currentBoard.toString());
        if (currentBoard.gameEnded()){
            return currentBoard.getScore(playerEstimation);
        }
        if (depth>threshould){
            return evaluation(currentBoard);
        }
        int currentNextPlayer=currentBoard.getNextPlayer();
        int score=0;
        int bestMove=-1;
        for (int i=1;i<7;i++){//TODO improve to random
            GameState newState=currentBoard.clone();
            if(newState.makeMove(i)){
                if (newState.gameEnded()){
                    int endscore=newState.getScore(playerEstimation);
                    //int player=newState.getNextPlayer();
                    if (currentNextPlayer!=playerEstimation && endscore<score){
                        //find minimum
                        score=endscore;
                        bestMove=i;
                    }else
                    if (currentNextPlayer==playerEstimation && endscore>score){
                        //find maximum
                        score=endscore;
                        bestMove=i;
                    }
                    continue;
                }
                //possibly prune
                if (currentNextPlayer!=newState.getNextPlayer()){
                    boolean pruneFlag=false;
                    int newStateScores=0;
                    for (int j=1;j<7;j++){//TODO improve to random
                        GameState newerState=newState.clone();
                        if (newerState.makeMove(j)){
                            int newerScores=searchScores(newerState,depth+2);
                            if (currentNextPlayer==playerEstimation){
                                if (newerScores<=score){
                                    pruneFlag=true;
                                    break;
                                }else {
                                    newStateScores=newerScores;
                                }
                            }else {
                                if (newerScores>=score){
                                    pruneFlag=true;
                                    break;
                                }else {
                                    newStateScores=newerScores;
                                }
                            }
                        }
                    }
                    if (pruneFlag){
                        continue;
                    }else {
                        score=newStateScores;
                    }
                    continue;
                }
                int newScore=searchScores(newState,depth+1);
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

    static int evaluation(GameState currentState){
        int total=72;
        int estPlayerScores=currentState.getScore(playerEstimation);
        int anotherPlayerScores=currentState.getScore(3-playerEstimation);
        return estPlayerScores*total/(anotherPlayerScores+estPlayerScores);
    }

    public static void main(String[] args){
        GameState start=new GameState();
        int result = searchScores(start,1);
        start.makeMove(1);
        int result1 = searchScores(start,1);
        System.out.println(result);
    }
}
