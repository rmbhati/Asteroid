package com.ez.asteroid.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class LineDrawerView extends View {

    private Paint paint;
    private float startX, startY, endX, endY;
    private boolean isDrawing = false;

    private int leftImagePositions[][] = {
            {50, 50},   // Apple
            {50, 150},  // Ball
            {50, 250},  // Cat
            {50, 350}   // Dog
    };

    private int rightImagePositions[][] = {
            {500, 50},   // Apple
            {500, 150},  // Ball
            {500, 250},  // Cat
            {500, 350}   // Dog
    };

    private String[] leftImages = {"Apple", "Ball", "Cat", "Dog"};
    private String[] rightImages = {"Apple", "Ball", "Cat", "Dog"};

    public LineDrawerView(Context context) {
        super(context);
        init();
    }

    public LineDrawerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED); // Default color for the line
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // If the user is drawing, draw the line
        if (isDrawing) {
            canvas.drawLine(startX, startY, endX, endY, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int startImageIndex = -1;
        int endImageIndex = -1;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Start drawing: Detect which left image is touched
                startX = event.getX();
                startY = event.getY();

                // Check if the touch is within any of the left image areas
                startImageIndex = getTouchedImageIndex(startX, startY, leftImagePositions);
                Log.d("LineDrawer", "ACTION_DOWN: startX=" + startX + ", startY=" + startY + ", index=" + startImageIndex);

                if (startImageIndex != -1) {
                    // Valid touch on the left image, start drawing the line
                    isDrawing = true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                // Update the line's endpoint as the user moves
                endX = event.getX();
                endY = event.getY();
                invalidate();  // Redraw the view with the updated line
                break;

            case MotionEvent.ACTION_UP:
                // End drawing: Detect which right image is touched
                endX = event.getX();
                endY = event.getY();
                endImageIndex = getTouchedImageIndex(endX, endY, rightImagePositions);
                Log.d("LineDrawer", "ACTION_UP: endX=" + endX + ", endY=" + endY + ", index=" + endImageIndex);

                if (endImageIndex != -1 && startImageIndex != -1 && isDrawing) {
                    // Both start and end positions are valid, check the matching line
                    checkMatchingLine(startX, startY, endX, endY, leftImagePositions, rightImagePositions, startImageIndex, endImageIndex);
                }
                isDrawing = false;
                break;
        }
        return true;
    }

    // Check which image is touched based on the coordinates
    private int getTouchedImageIndex(float x, float y, int[][] imagePositions) {
        for (int i = 0; i < imagePositions.length; i++) {
            int imageX = imagePositions[i][0];
            int imageY = imagePositions[i][1];

            // Define the area around each image where the user can touch to select it (tolerance area)
            if (Math.abs(x - imageX) < 50 && Math.abs(y - imageY) < 50) {
                return i;  // Return the index of the touched image
            }
        }
        return -1;  // Return -1 if no image is touched
    }

    // Check if the line drawn matches the corresponding left and right images
    private void checkMatchingLine(float startX, float startY, float endX, float endY,
                                   int[][] leftPositions, int[][] rightPositions,
                                   int leftImageIndex, int rightImageIndex) {
        // If the drawn line connects the same image from left and right, set the color to green
        if (leftImageIndex == rightImageIndex) {
            paint.setColor(Color.GREEN); // Correct match: green line
            Log.d("LineDrawer", "Match found! Line is green.");
        } else {
            paint.setColor(Color.RED);   // Incorrect match: red line
            Log.d("LineDrawer", "No match! Line is red.");
        }

        // Trigger a redraw to show the line with the correct color
        invalidate();
    }
}
