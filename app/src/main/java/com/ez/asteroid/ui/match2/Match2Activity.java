package com.ez.asteroid.ui.match2;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ez.asteroid.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Match2Activity extends AppCompatActivity {

    private ImageView[] leftImages = new ImageView[4];
    private ImageView[] rightImages = new ImageView[4];
    private LineView lineView;

    private int leftIndex = -1;  // to track selected image on the left
    private int rightIndex = -1; // to track selected image on the right

    int selectedPos = 0;
    ArrayList<MatchModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match2);


        list = new ArrayList<>();
        MatchModel model = new MatchModel(
                R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4,
                R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image1,
                3, 0, 2, 1
        );
        list.add(model);

        // Initialize ImageViews
        leftImages[0] = findViewById(R.id.imageLeft1);
        leftImages[1] = findViewById(R.id.imageLeft2);
        leftImages[2] = findViewById(R.id.imageLeft3);
        leftImages[3] = findViewById(R.id.imageLeft4);

        rightImages[0] = findViewById(R.id.imageRight1);
        rightImages[1] = findViewById(R.id.imageRight2);
        rightImages[2] = findViewById(R.id.imageRight3);
        rightImages[3] = findViewById(R.id.imageRight4);

        lineView = findViewById(R.id.lineView);

        //set image to imageview
        leftImages[0].setImageResource(list.get(selectedPos).getL1());
        leftImages[1].setImageResource(list.get(selectedPos).getL2());
        leftImages[2].setImageResource(list.get(selectedPos).getL3());
        leftImages[3].setImageResource(list.get(selectedPos).getL4());

        rightImages[0].setImageResource(list.get(selectedPos).getR1());
        rightImages[1].setImageResource(list.get(selectedPos).getR2());
        rightImages[2].setImageResource(list.get(selectedPos).getR3());
        rightImages[3].setImageResource(list.get(selectedPos).getR4());


        for (int i = 0; i < 4; i++) {
            leftImages[i].setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    leftIndex = getLeftImageIndex(v);
                    if (leftIndex != -1) {
                        lineView.clearLine();
                        //lineView.startLine(event.getX(), event.getY());
                    }
                }
                return true;
            });

            rightImages[i].setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    rightIndex = getRightImageIndex(v);
                    if (rightIndex != -1 && leftIndex != -1) {
                        // Here, check if it matches or not
                        //lineView.endLine(event.getX(), event.getY());
                        checkMatch(leftIndex, rightIndex);
                    }
                }
                return true;
            });
        }
    }

    private int getLeftImageIndex(View v) {
        for (int i = 0; i < leftImages.length; i++) {
            if (v == leftImages[i]) {
                return i;
            }
        }
        return -1;
    }

    private int getRightImageIndex(View v) {
        for (int i = 0; i < rightImages.length; i++) {
            if (v == rightImages[i]) {
                return i;
            }
        }
        return -1;
    }

    private void checkMatch(int leftIndex, int rightIndex) {
        Log.e("Check", "Left: " + leftIndex + ", Right: " + rightIndex + " Opt: " + list.get(selectedPos).o1);
        if (leftIndex == 0) {
            if (rightIndex == list.get(selectedPos).o1) {
                Toast.makeText(this, "Match!", Toast.LENGTH_SHORT).show();
                this.leftIndex = -1;
            } else {
                Toast.makeText(this, "Not a match", Toast.LENGTH_SHORT).show();
            }
        } else if (leftIndex == 1) {
            if (rightIndex == list.get(selectedPos).o2) {
                Toast.makeText(this, "Match!", Toast.LENGTH_SHORT).show();
                this.leftIndex = -1;
            } else {
                Toast.makeText(this, "Not a match", Toast.LENGTH_SHORT).show();
            }
        } else if (leftIndex == 2) {
            if (rightIndex == list.get(selectedPos).o3) {
                this.leftIndex = -1;
                Toast.makeText(this, "Match!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Not a match", Toast.LENGTH_SHORT).show();
            }
        } else if (leftIndex == 3) {
            if (rightIndex == list.get(selectedPos).o4) {
                Toast.makeText(this, "Match!", Toast.LENGTH_SHORT).show();
                this.leftIndex = -1;
            } else {
                Toast.makeText(this, "Not a match", Toast.LENGTH_SHORT).show();
            }
        }

        /*if (leftIndex == rightIndex) {
            Toast.makeText(this, "Match!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not a match", Toast.LENGTH_SHORT).show();
        }*/
    }
}
