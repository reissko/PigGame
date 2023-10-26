package edu.up.cs301.pig;

import android.os.CountDownTimer;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.util.Tickable;

/**
 * An AI for Pig
 *
 * @author Andrew M. Nuxoll
 * @version August 2015
 */
public class PigComputerPlayer extends GameComputerPlayer {

    /**
     * ctor does nothing extra
     */
    public PigComputerPlayer(String name) {
        super(name);
    }

    /**
     * callback method--game's state has changed
     *
     * @param info
     * 		the information (presumably containing the game's state)
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if(info instanceof PigGameState){
            PigGameState gameInfo = (PigGameState) info;
            if (this.playerNum != gameInfo.getPlayerTurn()) { //if it's not the computer player's turn, return
                return;
            } else { //if it is the computer player's turn, choose a random action (roll or hold)
                double choice = Math.random();
                while(choice < .5) { //will perform a roll action
                    PigRollAction roll = new PigRollAction(this);
                    game.sendAction(roll);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    choice = Math.random();
                }
                PigHoldAction hold = new PigHoldAction(this);
                game.sendAction(hold);
                if (playerNum == 0) { // computer player is player 0
                    gameInfo.setPlayerTurn(1);
                } else if (playerNum == 1) {// computer player is player 1
                    gameInfo.setPlayerTurn(0);
                }
            }
        }
    }//receiveInfo

}
