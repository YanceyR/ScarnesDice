package com.sonavah.yanceyreid.scarnesdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // if user or computer rolls, their turn score will be set to 0
    private static final int UNLUCKY_DIE = 1;

    //if com turn score is under this value, then com will roll again
    private static final int COM_RISK_LEVEL = 10;

    private static int userOverallScore = 0;
    private static int userTurnScore = 0;
    private static int comOverallScore = 0;
    private static int comTurnScore = 0;

    private static ImageView dieImage;
    private static TextView userScoreView;
    private static String userScoreText;
    private static TextView comScoreView;
    private static String comScoreText;

    private static Button rollButton;
    private static Button holdButton;

    private Random random = new Random();
    private int currentDie;
    private int[] dieImages = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3,
                    R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userScoreView = findViewById(R.id.userScoreView);
        userScoreText = (String)((TextView)findViewById(R.id.userScoreView)).getText();

        comScoreView = findViewById(R.id.comScoreView);
        comScoreText = (String)((TextView)findViewById(R.id.comScoreView)).getText();

        rollButton = findViewById(R.id.rollButton);
        holdButton = findViewById(R.id.holdButton);

        dieImage = findViewById(R.id.dieImageView);
        updateScoreViews();
    }

    private void comTurn() {
        int rolledDie;

        rollButton.setEnabled(false);
        holdButton.setEnabled(false);

        do {
            rolledDie = rollDie();
            comTurnScore += rolledDie;
        } while (comTurnScore < COM_RISK_LEVEL && rolledDie != 1);

        if (rolledDie != 1) {
            comOverallScore += comTurnScore;
        }

        comTurnScore = 0;

        rollButton.setEnabled(true);
        holdButton.setEnabled(true);
        updateScoreViews();
    }

    private int rollDie() {
        currentDie = random.nextInt(6 - 1+ 1) + 1;
        dieImage.setImageResource(dieImages[currentDie - 1]);
        return currentDie;
    }

    public void rollButtonClick(View view) {
        int rolledDie = rollDie();

        if (rolledDie == UNLUCKY_DIE) {
            userTurnScore = 0;
            comTurn();
        } else {
            userTurnScore += rolledDie;
        }
    }

    private void updateScoreViews() {
        userScoreView.setText(userScoreText + userOverallScore);
        comScoreView.setText(comScoreText + comOverallScore);
    }

    public void holdButtonClick(View view) {
        userOverallScore += userTurnScore;
        userTurnScore = 0;
        updateScoreViews();
        comTurn();
    }

    public void resetButtonClick(View view) {
        userOverallScore = 0;
        userTurnScore = 0;

        comOverallScore = 0;
        comTurnScore = 0;

        updateScoreViews();
    }
}
