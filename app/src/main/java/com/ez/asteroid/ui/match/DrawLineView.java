package com.ez.asteroid.ui.match;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DrawLineView extends View {

    private Paint paint;
    private List<Line> lines = new ArrayList<>();
    private Bitmap backgroundImage;
    private Bitmap scaledBackgroundImage;
    private static final int MAX_LINES = 4;  // Maximum number of lines to draw

    public DrawLineView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize paint for the line
        paint = new Paint();
        paint.setColor(0xFF000000); // Black color
        paint.setStrokeWidth(5); // Line width
        paint.setAntiAlias(true); // Smooth lines
    }

    // Set the image that will be used as the background
    public void setBackgroundImage(Bitmap bitmap) {
        this.backgroundImage = bitmap;
        this.scaledBackgroundImage = Bitmap.createScaledBitmap(bitmap, getWidth(), getHeight(), true);
        invalidate(); // Redraw to display the image
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the background image if it is set
        if (scaledBackgroundImage != null) {
            canvas.drawBitmap(scaledBackgroundImage, 0, 0, null);
        }

        // Draw all the lines in the list (up to MAX_LINES)
        for (Line line : lines) {
            canvas.drawLine(line.startPoint.x, line.startPoint.y, line.endPoint.x, line.endPoint.y, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Start a new line if the number of lines is less than MAX_LINES
                if (lines.size() < MAX_LINES) {
                    // Add a new line with start and end at the same point (user taps to set both points)
                    lines.add(new Line(new PointF(x, y), new PointF(x, y)));
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                // Update the last line's end point as the user moves their finger
                if (!lines.isEmpty()) {
                    Line currentLine = lines.get(lines.size() - 1);
                    currentLine.endPoint = new PointF(x, y);
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
                // Finalize the last line's end point
                if (!lines.isEmpty()) {
                    Line currentLine = lines.get(lines.size() - 1);
                    currentLine.endPoint = new PointF(x, y);
                    invalidate();
                }
                break;
        }

        return true;
    }

    // Clear all drawn lines
    public void clear() {
        lines.clear();
        invalidate(); // Redraw to remove all lines
    }

    // Clear the background image
    public void clearBackgroundImage() {
        backgroundImage = null;
        scaledBackgroundImage = null;
        invalidate(); // Redraw to remove the background image
    }

    // Allow customization of paint settings
    public void setPaintColor(int color) {
        paint.setColor(color);
    }

    public void setStrokeWidth(float width) {
        paint.setStrokeWidth(width);
    }

    private static class Line {
        PointF startPoint;
        PointF endPoint;

        Line(PointF start, PointF end) {
            startPoint = start;
            endPoint = end;
        }
    }
}

