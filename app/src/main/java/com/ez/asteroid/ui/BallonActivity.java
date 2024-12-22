package com.ez.asteroid.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


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

        ImageView cc=findViewById(R.id.cc);


        cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BallonActivity.this, "DFFDDFGDF", Toast.LENGTH_SHORT).show();
            }
        });


        balloonView = findViewById(R.id.balloonView);  // Get the BalloonView reference

        balloonView.toggleAnimation();


        balloonView.bringToFront();
    }

}
