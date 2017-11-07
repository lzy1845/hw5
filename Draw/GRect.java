package edu.stanford.cs108.mobiledraw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by ziyili on 11/5/17.
 */

public class GRect extends GObject {

    public GRect(float x, float y, float width, float height, boolean selected) {
        super(x, y, width, height, selected);
    }

    public GRect(GObject g) {
        super(g.getX(), g.getY(), g.getWidth(), g.getHeight(), g.getSelected());
    }

    public void draw(Canvas canvas) {
        if (this.getSelected()) {
            canvas.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), blueOutline);

        } else {
            canvas.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), redOutline);
        }
        canvas.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), whiteFill);

    }
}
