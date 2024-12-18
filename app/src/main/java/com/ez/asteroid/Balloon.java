package com.ez.asteroid;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Balloon {

    private float x, y;
    private float speedX, speedY;
    private Bitmap balloonImage; // Bitmap for balloon image
    private float width, height;

    public Balloon(float x, float y, float speedX, float speedY, Bitmap balloonImage) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.balloonImage = balloonImage;
        this.width = balloonImage.getWidth();
        this.height = balloonImage.getHeight();
    }

    public void updatePosition(int screenWidth, int screenHeight) {
        // Update position
        x += speedX;
        y += speedY;

        // Bounce back when hitting edges
        if (x >= screenWidth - width || x <= 0) {
            speedX = -speedX;
        }
        if (y >= screenHeight - height || y <= 0) {
            speedY = -speedY;
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        // Draw the image on the canvas
        canvas.drawBitmap(balloonImage, x - width / 2, y - height / 2, paint);
    }
}
