package com.ez.asteroid.ui.match;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ez.asteroid.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_march);
        DrawLineView drawLineView = findViewById(R.id.drawLineView);
        drawLineView.invalidate();


    }


}
