package edu.stanford.cs108.mobiledraw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by ziyili on 11/5/17.
 */

public class GObject {
    private float x, y, width, height;
    public Paint redOutline, whiteFill, blueOutline;

    private void init() {
        redOutline = new Paint();
        redOutline.setColor(Color.RED);
        redOutline.setStyle(Paint.Style.STROKE);
        redOutline.setStrokeWidth(5.0f);

        blueOutline = new Paint();
        blueOutline.setColor(Color.BLUE);
        blueOutline.setStyle(Paint.Style.STROKE);
        blueOutline.setStrokeWidth(15.0f);

        whiteFill = new Paint();
        whiteFill.setColor(Color.WHITE);

    }

    public GObject(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        init();
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public boolean isSelected(double clickX, double clickY) {
        return (clickX >= x && clickX <= x+width && clickY >= y && clickY <= y+height);
    }

    public void draw(Canvas canvas) {

    }
}
