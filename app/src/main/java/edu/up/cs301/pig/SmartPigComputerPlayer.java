package edu.up.cs301.pig;

import android.graphics.Color;
import android.widget.ImageButton;

import edu.up.cs301.game.infoMsg.GameInfo;

public class SmartPigComputerPlayer extends PigComputerPlayer {

    private PigGameState gameState;
    private int scoreDifference;
    private int myScore;
    private int opponentScore;
    private int pointsToWin;

    public SmartPigComputerPlayer(String name) {super(name);}

    protected void receiveInfo(GameInfo info) {
        if(info instanceof PigGameState) {
            PigGameState gameInfo = (PigGameState)info;
            if (this.playerNum != gameInfo.getPlayerTurn()) { //if it's not the computer player's turn, return
                return;
            }
            int player = gameInfo.getPlayerTurn();
            if(player == 0) {
                myScore = gameInfo.getPlayer0Score();
                opponentScore = gameInfo.getPlayer1Score();
            } else if(player == 1) {
                myScore = gameInfo.getPlayer1Score();
                opponentScore = gameInfo.getPlayer0Score();
            }
            sleep(2000);
            if(myScore + gameInfo.getHoldAmt() >= opponentScore) { //if the smart ai is ahead or equal to human player then roll and hold
                PigRollAction roll = new PigRollAction(this);
                game.sendAction(roll);
                PigHoldAction hold = new PigHoldAction(this);
                game.sendAction(hold);
                return;
            }
            /*if the smart ai player is behind on points the ai will roll until the point difference is made up and
            will then hold the amount to make up the difference*/

            if(opponentScore > myScore + gameInfo.getHoldAmt()) {
                PigRollAction catchUpRoll = new PigRollAction(this);
                game.sendAction(catchUpRoll);
            }
        }
    }

}
