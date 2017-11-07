package edu.stanford.cs108.mobiledraw;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by ziyili on 11/5/17.
 */

public class GOval extends GObject {

    public GOval(float x, float y, float width, float height, boolean selected) {
        super(x, y, width, height, selected);
    }

    public GOval(GObject g) {
        super(g.getX(), g.getY(), g.getWidth(), g.getHeight(), g.getSelected());
    }

    public void draw(Canvas canvas) {
        RectF rectF = new RectF(getX(), getY(), getX() + getWidth(), getY() + getHeight());
        if (this.getSelected()) {
            canvas.drawOval(rectF, blueOutline);
        } else {
            canvas.drawOval(rectF, redOutline);
        }
        canvas.drawOval(rectF, whiteFill);
    }
}
