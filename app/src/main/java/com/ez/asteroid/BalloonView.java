package com.ez.asteroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

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

        // Initialize random balloons
        Random random = new Random();
        for (int i = 0; i < 5; i++) {  // Create 5 balloons
            float x = random.nextInt(100) + 50;
            float y = random.nextInt(100) + 50;
            float speedX = random.nextFloat() * 5 + 2;
            float speedY = random.nextFloat() * 5 + 2;
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
        canvas.drawColor(0xFFFFFFFF);  // White background

        // Draw each balloon
        Paint paint = new Paint();
        for (Balloon balloon : balloons) {
            balloon.draw(canvas, paint);
        }
    }
}
