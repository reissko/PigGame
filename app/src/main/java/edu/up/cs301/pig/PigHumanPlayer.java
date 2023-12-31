package edu.up.cs301.pig;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;

import org.w3c.dom.Text;

/**
 * A GUI for a human to play Pig. This default version displays the GUI but is incomplete
 *
 *
 * @author Andrew M. Nuxoll, modified by Steven R. Vegdahl
 * @version February 2016
 */
public class PigHumanPlayer extends GameHumanPlayer implements OnClickListener {

	/* instance variables */

    // These variables will reference widgets that will be modified during play
    private TextView    playerScoreTextView = null;
    private TextView    oppScoreTextView    = null;
    private TextView    yourScoreText = null;
    private TextView    oppScoreText = null;
    private TextView    turnTotalTextView   = null;
    private static TextView    messageTextView     = null;
    private ImageButton dieImageButton      = null;
    private Button      holdButton          = null;
    private PigGameState gameState;

    // the android activity that we are running
    private GameMainActivity myActivity;

    /**
     * constructor does nothing extra
     */
    public PigHumanPlayer(String name) {
        super(name);
    }

    /**
     * Returns the GUI's top view object
     *
     * @return
     * 		the top object in the GUI's view hierarchy
     */
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /**
     * callback method when we get a message (e.g., from the game)
     *
     * @param info
     * 		the message
     */
    @Override
    public void receiveInfo(GameInfo info) {
        if(!(info instanceof PigGameState)) {
            flash(Color.RED,500);
            return;
        } else {
            gameState = (PigGameState) info;
            if(gameState.getPlayerTurn() == 0) {
                dieImageButton.setBackgroundColor(Color.GREEN);
            } else if(gameState.getPlayerTurn() == 1) {
                dieImageButton.setBackgroundColor(Color.RED);
            }
            if(playerNum == 0) { // human player is player 0
                playerScoreTextView.setText(String.valueOf(gameState.getPlayer0Score()));
                oppScoreTextView.setText(String.valueOf(gameState.getPlayer1Score()));
                turnTotalTextView.setText(String.valueOf(gameState.getHoldAmt()));
            } else if(playerNum == 1) { // human player is player 1
                playerScoreTextView.setText(String.valueOf(gameState.getPlayer1Score()));
                oppScoreTextView.setText(String.valueOf(gameState.getPlayer0Score()));
            }
            int dieRollNum = gameState.getDiceVal();
            switch(dieRollNum) {
                case 1:
                    dieImageButton.setImageResource(R.drawable.face1); break;
                case 2:
                    dieImageButton.setImageResource(R.drawable.face2); break;
                case 3:
                    dieImageButton.setImageResource(R.drawable.face3); break;
                case 4:
                    dieImageButton.setImageResource(R.drawable.face4); break;
                case 5:
                    dieImageButton.setImageResource(R.drawable.face5); break;
                case 6:
                    dieImageButton.setImageResource(R.drawable.face6); break;
            }
        }
    }//receiveInfo

    /**
     * this method gets called when the user clicks the die or hold button. It
     * creates a new PigRollAction or PigHoldAction and sends it to the game.
     *
     * @param button
     * 		the button that was clicked
     */
    public void onClick(View button) {
        if(button instanceof Button) {
            if(playerNum == 0) {
                messageTextView.setText(name + " added " + gameState.getHoldAmt() + " to their score");
            } else if(playerNum == 1) {
                messageTextView.setText(name + " added " + gameState.getHoldAmt() + " to their score");
            }
            messageTextView.setText(name + " added " + gameState.getHoldAmt() + " to their score");
            PigHoldAction hold = new PigHoldAction(this);
            game.sendAction(hold);
        } else if(button instanceof ImageButton) {
            PigRollAction roll = new PigRollAction(this);
            game.sendAction(roll);
            if(playerNum == 0 && gameState.getPlayer0Score() != 0 && gameState.getPlayer1Score() != 0) {
                if (gameState.getDiceVal() == 1) {
                    messageTextView.setText("Oh no! " + name + " rolled a 1 and lost everything!");
                }
            } else if(playerNum == 1) {
                if(gameState.getDiceVal() == 1) {
                    messageTextView.setText("Oh no! " + name + " rolled a 1 and lost everything!");
                }
            }
        }
    }// onClick

    public static void changeMessageTextView(String text) {
        if(messageTextView != null) {
            messageTextView.setText(text);
        }
    }

    /**
     * callback method--our game has been chosen/rechosen to be the GUI,
     * called from the GUI thread
     *
     * @param activity
     * 		the activity under which we are running
     */
    public void setAsGui(GameMainActivity activity) {

        // remember the activity
        myActivity = activity;

        // Load the layout resource for our GUI
        activity.setContentView(R.layout.pig_layout);

        //Initialize the widget reference member variables
        this.playerScoreTextView = (TextView)activity.findViewById(R.id.yourScoreValue);
        this.oppScoreTextView    = (TextView)activity.findViewById(R.id.oppScoreValue);
        this.yourScoreText       = (TextView)activity.findViewById(R.id.yourScoreText);
        this.oppScoreText        = (TextView)activity.findViewById(R.id.oppScoreText);
        this.turnTotalTextView   = (TextView)activity.findViewById(R.id.turnTotalValue);
        this.messageTextView     = (TextView)activity.findViewById(R.id.messageTextView);
        this.dieImageButton      = (ImageButton)activity.findViewById(R.id.dieButton);
        this.holdButton          = (Button)activity.findViewById(R.id.holdButton);

        //Listen for button presses
        dieImageButton.setOnClickListener(this);
        holdButton.setOnClickListener(this);

    }//setAsGui

    @Override
    protected void initAfterReady() {
        yourScoreText.setText(allPlayerNames[0] + " Score: ");
        oppScoreText.setText(allPlayerNames[1] + " Score: ");
    }
}// class PigHumanPlayer
