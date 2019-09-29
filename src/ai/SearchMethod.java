package ai;

import kalaha.GameState;

public class SearchMethod {
    /**
     * Using Player 1's scores to estimate scores of a GameState.
     */
    public static int playerEstimation=1;
    /**
     * maxDepth is set to ensure that the search returns in time.
     */
    public static int maxDepth=6;
    /**
     * Using Minimax search algorithm to find scores of the given currentBoard
     */
    static int searchScores(GameState currentBoard, int depth){
        //System.out.println("Search "+currentBoard.toString());
        if (currentBoard.gameEnded()){
            return currentBoard.getScore(playerEstimation);
        }
        if (depth>maxDepth){
            return evaluation(currentBoard);
        }
        int currentNextPlayer=currentBoard.getNextPlayer();
        int score=-1;
        //int bestMove=-1;
        for (int i=1;i<7;i++){//TODO improve to random sequence
            GameState newState=currentBoard.clone();
            if(newState.makeMove(i)){
                if (newState.gameEnded()){
                    int endScores=newState.getScore(playerEstimation);
                    //int player=newState.getNextPlayer();
                    if ((currentNextPlayer!=playerEstimation && endScores<score) ||
                            (currentNextPlayer==playerEstimation && endScores>score) || score<0){
                        //(currentNextPlayer!=playerEstimation && endScores<score)--find minimum
                        //(currentNextPlayer==playerEstimation && endScores>score)--find maximum
                        score=endScores;
                        //bestMove=i;
                    }
                    continue;
                }
                //possibly prune
                if (currentNextPlayer!=newState.getNextPlayer()){
                    boolean pruneFlag=false;
                    int newStateScores=0;
                    for (int j=1;j<7;j++){//TODO improve to random sequence
                        GameState newerState=newState.clone();
                        if (newerState.makeMove(j)){
                            int newerScores=searchScores(newerState,depth+2);
                            if (currentNextPlayer==playerEstimation){
                                if (newerScores<=score && score>=0){
                                    pruneFlag=true;
                                    break;
                                }else {
                                    newStateScores=newerScores;
                                }
                            }else {
                                if (newerScores>=score && score>=0){
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
                    //bestMove=i;
                }else
                    if (currentNextPlayer==playerEstimation && newScore>score){
                        //find maximum
                        score=newScore;
                        //bestMove=i;
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

    public static int searchMove(GameState currentState){
        int bestScores=-1;
        int bestMove=0;
        for (int i=1 ; i<7 ; i++){
            GameState nextState=currentState.clone();
            if (nextState.makeMove(i)){
                int nextScores=searchScores(nextState,1);
                if (currentState.getNextPlayer()==playerEstimation){
                    //expect max
                    if (nextScores>bestScores || bestScores<0){
                        bestScores=nextScores;
                        bestMove=i;
                    }
                }else {
                    //expect min
                    if (nextScores<bestScores || bestScores<0){
                        bestScores=nextScores;
                        bestMove=i;
                    }
                }

            }
        }
        return bestMove;
    }

    public static void main(String[] args){
        GameState start=new GameState();
        //int result = searchScores(start,1);
        start.makeMove(1);
        int result1 = searchMove(start);
        //System.out.println(result);
    }
}
