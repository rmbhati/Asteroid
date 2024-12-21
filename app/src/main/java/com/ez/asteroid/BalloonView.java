package com.ez.asteroid;


import android.content.Context;    // Importing Context class
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;
import android.content.Context;

public class BalloonView extends View {

    private ArrayList<Balloon> balloons;
    private Handler handler;
    private Bitmap balloonImage;
    private boolean isAnimating;

    // Constructor that accepts Context and AttributeSet
    public BalloonView(Context context, AttributeSet attrs) {
        super(context, attrs);  // Call the superclass constructor
        balloons = new ArrayList<>();
        handler = new Handler(Looper.getMainLooper());

        // Load the balloon image
        balloonImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.approval); // Your balloon image


        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        Log.e("Screen Resolution", "Width: " + screenWidth + ", Height: " + screenHeight);

        // Initialize random balloons
        Random random = new Random();
        for (int i = 0; i < 10; i++) {  // Create 5 balloons
            float x = random.nextInt(screenWidth); //resolution wise device ka
            float y = random.nextInt(screenHeight) ;
            float speedX = random.nextFloat() * 3 + 2;
            float speedY = random.nextFloat() * 2 + 2;
            balloons.add(new Balloon(x, y, speedX, speedY, balloonImage));
        }
    }

    // Method to start animation
    public void startAnimation() {
        isAnimating = true;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (isAnimating) {
                    // Update each balloon's position
                    for (Balloon balloon : balloons) {
                        balloon.updatePosition(getWidth(), getHeight());
                    }
                    invalidate();  // Redraw the view
                    handler.postDelayed(this, 16);  // ~60 FPS (16ms per frame)
                }
            }
        };
        handler.post(runnable);  // Start animation
    }

    // Method to stop animation
    public void stopAnimation() {
        isAnimating = false;
    }

    // Toggle start/stop of animation
    public void toggleAnimation() {
        if (isAnimating) {
            stopAnimation();  // Stop animation
        } else {
            startAnimation();  // Start animation
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0x00FFFFFF);  // White background

        // Draw each balloon
        Paint paint = new Paint();
        for (Balloon balloon : balloons) {
            balloon.draw(canvas, paint);
        }
    }
}
