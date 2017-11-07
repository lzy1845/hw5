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
    private boolean selected;

    private void init() {
        redOutline = new Paint();
        redOutline.setColor(Color.rgb(140,21,21));
        redOutline.setStyle(Paint.Style.STROKE);
        redOutline.setStrokeWidth(5.0f);

        blueOutline = new Paint();
        blueOutline.setColor(Color.BLUE);
        blueOutline.setStyle(Paint.Style.STROKE);
        blueOutline.setStrokeWidth(15.0f);

        whiteFill = new Paint();
        whiteFill.setColor(Color.WHITE);

    }

    public GObject(float x, float y, float width, float height, boolean selected) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.selected = selected;
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


    public boolean overlap(double clickX, double clickY) {
        return clickX >= x && clickX <= x+width && clickY >= y && clickY <= y+height;
    }

    public void draw(Canvas canvas) {
    }

    public void setSelected(boolean b) {
        this.selected = b;
    }

    public boolean getSelected() {
        return this.selected;
    }

    public void update(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean equals(GObject gObjectTwo) {
        return (x == gObjectTwo.x) && (y == gObjectTwo.y) && (width == gObjectTwo.width) && (height == gObjectTwo.height);
    }
}
