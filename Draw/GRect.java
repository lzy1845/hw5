package edu.stanford.cs108.mobiledraw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by ziyili on 11/5/17.
 */

public class GRect extends GObject {
    private boolean selected;

    public GRect(float x, float y, float width, float height, boolean selected) {
        super(x, y, width, height);
        this.selected = selected;
    }

    public void draw(Canvas canvas) {
        if (selected) {
            canvas.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), blueOutline);
        } else {
            canvas.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), redOutline);
        }
        canvas.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), whiteFill);

    }

    public void setSelected(boolean setSelected) {
        this.selected = setSelected;
    }

}
