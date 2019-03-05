package example.supervoo.scorekeeper;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {


    // Private Variables holding Scores
    private int mScore1;
    private int mScore2;
    private TextView mScore_1;
    private TextView mScore_2;

    static final String STATE_SCORE_1 = "T1";
    static final String STATE_SCORE_2 = "T2";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the scores.
        outState.putInt(STATE_SCORE_1, mScore1);
        outState.putInt(STATE_SCORE_2, mScore2);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScore_1 = (TextView) findViewById(R.id.score_team_1);
        mScore_2 = (TextView) findViewById(R.id.score_team_2);

        final Switch retroMode = (Switch) findViewById(R.id.switch_retro);
        retroMode.setChecked(false);
//
        retroMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
//                    retroMode.setTextColor(getResources().getColor(android.R.color.white));
                    displayToaster("Retro Not Available Yet");


                } else {
                    // The toggle is disabled
                    displayToaster("Reverted");
                }
            }
        });

        if (savedInstanceState != null) {
            mScore1 = savedInstanceState.getInt(STATE_SCORE_1);
            mScore2 = savedInstanceState.getInt(STATE_SCORE_2);

            //Set the score text views
            mScore_1.setText(String.valueOf(mScore1));
            mScore_2.setText(String.valueOf(mScore2));
        }

    }

    // Increase Score
    public void increaseScoreTeam(View view) {
        // Get the ID of the button that was clicked
        int viewID = view.getId();

        switch (viewID) {
            //If id is equal to Team 1
            case R.id.increase_team_1:
                // Increment the score and update the TextView
                mScore1++;
                mScore_1.setText(String.valueOf(mScore1));
                break;
            //If id is equal to Team 2
            case R.id.increase_team_2:
                // Increment the score and update the TextView
                mScore2++;
                mScore_2.setText(String.valueOf(mScore2));
                break;
        }
    }

    // Decrease Score
    public void decreaseScoreTeam(View view) {
        // Get the ID of the button that was clicked
        int viewID = view.getId();

        switch (viewID) {
            //If id is equal to Team 1
            case R.id.decrease_team_1:
                //Decrement the score and update the TextView
                mScore1--;
                mScore_1.setText(String.valueOf(mScore1));
                break;
            //If id is equal to Team 2
            case R.id.decrease_team_2:
                //Decrement the score and update the TextView
                mScore2--;
                mScore_2.setText(String.valueOf(mScore2));
                break;
        }

    }

    public void destroyScore(View view) {
        mScore1 = 0;
        mScore2 = 0;
        mScore_1.setText(String.valueOf(mScore1));
        mScore_2.setText(String.valueOf(mScore2));
        displayToaster("Scores Destroyed");
    }

    public void displayToaster(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}
