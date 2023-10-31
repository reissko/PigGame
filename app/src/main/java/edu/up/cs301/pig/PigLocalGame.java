package edu.up.cs301.pig;

import android.util.Log;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;

// dummy comment, to see if commit and push work from srvegdahl account

/**
 * class PigLocalGame controls the play of the game
 *
 * @author Andrew M. Nuxoll, modified by Steven R. Vegdahl
 * @version February 2016
 */
public class PigLocalGame extends LocalGame {

    PigGameState gameState;

    /**
     * This ctor creates a new game state
     */
    public PigLocalGame() {
        gameState = new PigGameState();
    }

    /**
     * can the player with the given id take an action right now?
     */
    @Override
    protected boolean canMove(int playerIdx) {
        return (playerIdx == gameState.getPlayerTurn());
    }

    /**
     * This method is called when a new action arrives from a player
     *
     * @return true if the action was taken or false if the action was invalid/illegal.
     */
    @Override
    protected boolean makeMove(GameAction action) {
        int player = gameState.getPlayerTurn();
        if(action instanceof PigHoldAction) {
            if(player == 0) {
                gameState.setPlayer0Score(gameState.getPlayer0Score() + gameState.getHoldAmt());
                gameState.setHoldAmt(0);
                if(playerNames.length > 1) {
                    gameState.setPlayerTurn(1);
                    //Log.i("Player turns switched", Integer.toString(gameState.getPlayerTurn()));
                }
            } else if(player == 1){
                gameState.setPlayer1Score(gameState.getPlayer1Score() + gameState.getHoldAmt());
                gameState.setHoldAmt(0);
                if(playerNames.length > 1) {
                    gameState.setPlayerTurn(0);
                    //Log.i("Player turns switched", Integer.toString(gameState.getPlayerTurn()));
                }
            }
            return true;
        } else if(action instanceof PigRollAction) {
            int rand = (int)((Math.random()*6)+1);

            //Log.i("Player who rolled", Integer.toString(gameState.getPlayerTurn()));
            //Log.i("Dice Roll Value", Integer.toString(rand));

            gameState.setDiceVal(rand);
            if(rand != 1) { //if the player rolled anything but a 1 increase the running hold amount
                gameState.setHoldAmt(gameState.getHoldAmt()+rand);
            } else { //if the player did roll a 1
                gameState.setHoldAmt(0);
                if(playerNames.length > 1) {
                    if (player == 0) {
                        gameState.setPlayerTurn(1);
                    } else if (player == 1) {
                        gameState.setPlayerTurn(0);
                    }
                    Log.i("Player turns switched", Integer.toString(gameState.getPlayerTurn()));
                }
            }
                return true;
        }
        return false;
    }//makeMove

    /**
     * send the updated state to a given player
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        PigGameState copyState = new PigGameState(gameState);
        p.sendInfo(copyState);
    }//sendUpdatedSate

    /**
     * Check if the game is over
     *
     * @return
     * 		a message that tells who has won the game, or null if the
     * 		game is not over
     */
    @Override
    protected String checkIfGameOver() {
        if(gameState.getPlayer0Score() >= 50) {
            return playerNames[0] + " Wins!";
        } else if(gameState.getPlayer1Score() >= 50) {
            return playerNames[1] + " Wins!";
        }
        return null;
    }

}// class PigLocalGame
