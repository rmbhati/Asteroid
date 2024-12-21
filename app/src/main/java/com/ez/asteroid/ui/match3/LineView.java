package com.ez.asteroid.ui.match3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class LineView extends View {

    private Paint linePaint;
    private Paint pointPaint;
    private Paint correctLinePaint;
    private ArrayList<Line> drawnLines;  // Stores the drawn lines
    private PointF[] leftPoints;  // Left side points (A, B, C, D)
    private PointF[] rightPoints;  // Right side points (D, C, B, A)
    private boolean[] lineMatched;  // Tracks which lines are correctly drawn
    private Line currentLine;  // Tracks the currently drawn line

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);

        linePaint = new Paint();
        linePaint.setColor(0xFF0000FF); // Blue color for lines
        linePaint.setStrokeWidth(5);

        pointPaint = new Paint();
        pointPaint.setColor(0xFFFF0000); // Red color for points
        pointPaint.setStrokeWidth(10);

        correctLinePaint = new Paint();
        correctLinePaint.setColor(0xFF00FF00); // Green color for correct lines
        correctLinePaint.setStrokeWidth(5);

        drawnLines = new ArrayList<>();
        lineMatched = new boolean[4];

        // Define the points (arbitrary positions for this example)
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

        // Draw the points (left side: red, right side: green)
        for (PointF point : leftPoints) {
            canvas.drawPoint(point.x, point.y, pointPaint);
        }

        for (PointF point : rightPoints) {
            canvas.drawPoint(point.x, point.y, pointPaint);
        }

        // Draw the currently drawn line (blue)
        if (currentLine != null && currentLine.end != null) {
            canvas.drawLine(currentLine.start.x, currentLine.start.y, currentLine.end.x, currentLine.end.y, linePaint);
        }

        // Draw the drawn lines only if they are correct (use correct color)
        for (int i = 0; i < drawnLines.size(); i++) {
            Line line = drawnLines.get(i);
            if (lineMatched[i]) {
                canvas.drawLine(line.start.x, line.start.y, line.end.x, line.end.y, correctLinePaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Start drawing a line when the user touches down
                // Find the closest point on the left to start the line
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
        for (int i = 0; i < drawnLines.size(); i++) {
            Line line = drawnLines.get(i);
            int leftIndex = getLeftIndex(line.start);
            int rightIndex = getRightIndex(line.end);
            if (leftIndex != -1 && rightIndex != -1 && leftIndex == (3 - rightIndex)) {
                lineMatched[i] = true;  // Correct line
            } else {
                drawnLines.remove(i);  // Remove incorrect line
                lineMatched[i] = false;  // Incorrect line, don't show it
                i--;  // Adjust index after removal
            }
        }
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


