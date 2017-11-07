package edu.stanford.cs108.mobiledraw;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by ziyili on 11/5/17.
 */

public class GOval extends GObject {
    private boolean selected;

    public GOval(float x, float y, float width, float height, boolean selected) {
        super(x, y, width, height);
        this.selected = selected;
    }

    public void draw(Canvas canvas) {
        RectF rectF = new RectF(getX(), getY(), getX() + getWidth(), getY() + getHeight());
        if (selected) {
            canvas.drawRect(rectF, blueOutline);
        } else {
            canvas.drawRect(rectF, redOutline);
        }
        canvas.drawRect(rectF, whiteFill);

    }

    public void setSelected(boolean setSelected) {
        this.selected = setSelected;
    }
}
