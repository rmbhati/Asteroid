package com.ez.asteroid.ui.match3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.util.Log;

import com.ez.asteroid.R;

import java.util.ArrayList;

public class LineView extends View {

    private Paint linePaint;
    private Paint correctLinePaint;
    private ArrayList<Line> drawnLines;  // Stores the drawn lines
    private PointF[] leftPoints;  // Left side points (A, B, C, D)
    private PointF[] rightPoints;  // Right side points (D, C, B, A)
    private boolean[] lineMatched;  // Tracks which lines are correctly drawn
    private Line currentLine;  // Tracks the currently drawn line

    private Bitmap[] leftPointImages;  // Array for left point images
    private Bitmap[] rightPointImages; // Array for right point images

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);

        linePaint = new Paint();
        linePaint.setColor(0xFF0000FF); // Blue color for lines
        linePaint.setStrokeWidth(5);

        correctLinePaint = new Paint();
        correctLinePaint.setColor(0xFF00FF00); // Green color for correct lines
        correctLinePaint.setStrokeWidth(5);

        drawnLines = new ArrayList<>();
        lineMatched = new boolean[4];

        // Load different images for each point from drawable resources
        leftPointImages = new Bitmap[] {
                BitmapFactory.decodeResource(getResources(), R.drawable.image1),
                BitmapFactory.decodeResource(getResources(), R.drawable.image2),
                BitmapFactory.decodeResource(getResources(), R.drawable.image3),
                BitmapFactory.decodeResource(getResources(), R.drawable.image4)
        };

        rightPointImages = new Bitmap[] {
                BitmapFactory.decodeResource(getResources(), R.drawable.image1),
                BitmapFactory.decodeResource(getResources(), R.drawable.image2),
                BitmapFactory.decodeResource(getResources(), R.drawable.image3),
                BitmapFactory.decodeResource(getResources(), R.drawable.image4)
        };

        // Ensure images are loaded properly
        for (int i = 0; i < leftPointImages.length; i++) {
            if (leftPointImages[i] == null) {
                Log.e("LineView", "Left point image " + i + " not loaded!");
            }
        }
        for (int i = 0; i < rightPointImages.length; i++) {
            if (rightPointImages[i] == null) {
                Log.e("LineView", "Right point image " + i + " not loaded!");
            }
        }

        // Define the positions for the points (arbitrary positions)
        leftPoints = new PointF[]{
                new PointF(100, 100),  // A
                new PointF(100, 300),  // B
                new PointF(100, 500),  // C
                new PointF(100, 700)   // D
        };

        rightPoints = new PointF[]{
                new PointF(600, 100),  // D
                new PointF(600, 500),  // C
                new PointF(600, 300),  // B
                new PointF(600, 700)   // A
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("LineView", "onDraw called");

        // Draw the images (left side and right side) with their respective images
        for (int i = 0; i < leftPoints.length; i++) {
            drawImageAtPosition(canvas, leftPointImages[i], leftPoints[i]);
        }

        for (int i = 0; i < rightPoints.length; i++) {
            drawImageAtPosition(canvas, rightPointImages[i], rightPoints[i]);
        }

        // Draw the currently drawn line (blue)
        if (currentLine != null && currentLine.end != null) {
            canvas.drawLine(currentLine.start.x, currentLine.start.y, currentLine.end.x, currentLine.end.y, linePaint);
        }

        // Draw the drawn lines only if they are correct (green)
        for (int i = 0; i < drawnLines.size(); i++) {
            Line line = drawnLines.get(i);
            if (lineMatched[i]) {
                canvas.drawLine(line.start.x, line.start.y, line.end.x, line.end.y, correctLinePaint);
            }
        }
    }

    private void drawImageAtPosition(Canvas canvas, Bitmap image, PointF position) {
        if (image != null) {
            // Define the destination rectangle (where the image will be drawn)
            Rect destRect = new Rect(
                    (int) position.x - image.getWidth() / 2,
                    (int) position.y - image.getHeight() / 2,
                    (int) position.x + image.getWidth() / 2,
                    (int) position.y + image.getHeight() / 2
            );
            canvas.drawBitmap(image, null, destRect, null);
        } else {
            Log.e("LineView", "Image is null at position: " + position);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        Log.d("LineView", "Touch event at: (" + x + ", " + y + ")");

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Start drawing a line when the user touches down
                // Find the closest image on the left to start the line
                for (int i = 0; i < leftPoints.length; i++) {
                    if (distance(x, y, leftPoints[i].x, leftPoints[i].y) < 50) {
                        currentLine = new Line(leftPoints[i], null); // Start line from this point
                        invalidate();
                        return true;
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE:
                // Update the end of the line while the user moves their finger
                if (currentLine != null && currentLine.start != null) {
                    currentLine.end = new PointF(x, y);  // Continuously update the end point
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
                // Check if the end of the line was drawn near a right point
                if (currentLine != null && currentLine.end != null) {
                    boolean matched = false;
                    for (int i = 0; i < rightPoints.length; i++) {
                        if (distance(currentLine.end.x, currentLine.end.y, rightPoints[i].x, rightPoints[i].y) < 50) {
                            currentLine.end = rightPoints[i];  // Snap the end point to the right point
                            drawnLines.add(currentLine);  // Add the line to drawn lines
                            checkMatching();
                            matched = true;
                            break;
                        }
                    }

                    // If the line was not matched correctly, remove it
                    if (!matched) {
                        currentLine = null;
                    }

                    currentLine = null;  // Reset the current line
                }
                invalidate();
                break;
        }
        return true;
    }

    // Helper method to calculate distance between two points
    private float distance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    // Check if the drawn lines match the correct connections
    private void checkMatching() {
        ArrayList<Line> correctLines = new ArrayList<>();
        for (int i = 0; i < drawnLines.size(); i++) {
            Line line = drawnLines.get(i);
            int leftIndex = getLeftIndex(line.start);
            int rightIndex = getRightIndex(line.end);
            if (leftIndex != -1 && rightIndex != -1 && leftIndex == (3 - rightIndex)) {
                lineMatched[i] = true;  // Correct line
                correctLines.add(line);
            } else {
                lineMatched[i] = false;  // Incorrect line, don't show it
            }
        }
        drawnLines = correctLines;  // Update drawnLines to only contain correct ones
        invalidate();
    }

    // Get the index of a left point
    private int getLeftIndex(PointF point) {
        for (int i = 0; i < leftPoints.length; i++) {
            if (point.x == leftPoints[i].x && point.y == leftPoints[i].y) {
                return i;
            }
        }
        return -1;
    }

    // Get the index of a right point
    private int getRightIndex(PointF point) {
        for (int i = 0; i < rightPoints.length; i++) {
            if (point.x == rightPoints[i].x && point.y == rightPoints[i].y) {
                return i;
            }
        }
        return -1;
    }

    // Inner class to store each line
    private static class Line {
        PointF start;
        PointF end;

        Line(PointF start, PointF end) {
            this.start = start;
            this.end = end;
        }
    }
}
