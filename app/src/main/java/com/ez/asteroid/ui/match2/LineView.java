package com.ez.asteroid.ui.match2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LineView extends View {

    private Paint paint;
    private Path path;
    private float startX, startY, endX, endY;
    private boolean isDrawing = false;

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(0xFF0000FF);  // Blue color
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the path (line)
        if (isDrawing) {
            canvas.drawPath(path, paint);
        }
    }

    // Method to set the starting point of the line
    public void startLine(float x, float y) {
        startX = x;
        startY = y;
        isDrawing = true;
        path.moveTo(startX, startY);
        invalidate();
    }

    // Method to set the end point of the line
    public void endLine(float x, float y) {
        endX = x;
        endY = y;
        path.lineTo(endX, endY);
        invalidate();
    }

    // Clear the path
    public void clearLine() {
        path.reset();
        isDrawing = false;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Handle touch events if necessary
        return super.onTouchEvent(event);
    }
}

