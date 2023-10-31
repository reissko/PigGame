package edu.up.cs301.pig;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageButton;

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

                if(choice < .5) { //will perform a roll action
                    PigRollAction roll = new PigRollAction(this);
                    sleep(2000);
                    game.sendAction(roll);
                    Log.i("Computer player", "roll action");
                    return;
                }
                sleep(1000);
                PigHoldAction hold = new PigHoldAction(this);
                Log.i("Computer player", "hold action");
                game.sendAction(hold);
            }
        }
    }//receiveInfo

}
