package edu.up.cs301.pig;

import android.widget.TextView;

import edu.up.cs301.game.infoMsg.GameState;


public class PigGameState extends GameState {
    private int playerTurn;
    private int player0Score;
    private int player1Score;
    private int holdAmt;
    private int diceVal;

    public PigGameState() {
        playerTurn = 0;
        player0Score = 0;
        player1Score = 0;
        holdAmt = 0;
        diceVal = 1;
    }
    public PigGameState(PigGameState copy) {
        this.playerTurn = copy.playerTurn;
        this.player0Score = copy.player0Score;
        this.player1Score = copy.player1Score;
        this.holdAmt = copy.holdAmt;
        this.diceVal = copy.diceVal;
    }


    public int getPlayerTurn() {
        return playerTurn;
    }

    public int getPlayer0Score() {
        return player0Score;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getHoldAmt() {
        return holdAmt;
    }

    public int getDiceVal() {
        return diceVal;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setPlayer0Score(int player0Score) {
        this.player0Score = player0Score;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public void setHoldAmt(int holdAmt) {
        this.holdAmt = holdAmt;
    }

    public void setDiceVal(int diceVal) {
        this.diceVal = diceVal;
    }
}
