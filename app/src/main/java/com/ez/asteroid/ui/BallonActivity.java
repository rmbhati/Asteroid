package com.ez.asteroid.ui;

import android.app.Activity;
import android.os.Bundle;

import com.ez.asteroid.BalloonView;
import com.ez.asteroid.R;

public class BallonActivity extends Activity {

    private BalloonView balloonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //balloonView = new BalloonView(this);  // Initialize the custom view
        //setContentView(balloonView);

        setContentView(R.layout.activity_ballon);


        balloonView = findViewById(R.id.balloonView);  // Get the BalloonView reference

        balloonView.toggleAnimation();


        balloonView.bringToFront();
    }

}
