package edu.stanford.cs108.mobiledraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ziyili on 11/5/17.
 */

public class RectView extends View {
    float x1, y1, x2, y2, x, y, width, height;
    private Paint redOutline, whiteFill;
    List<GObject> shapes = new ArrayList<GObject>();
    private String EVENT;


    public RectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        redOutline = new Paint();
        redOutline.setColor(Color.RED);
        redOutline.setStyle(Paint.Style.STROKE);
        redOutline.setStrokeWidth(5.0f);
        whiteFill = new Paint();
        whiteFill.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (GObject g: shapes) {
            g.draw(canvas);
        }

    }

    public void getEvent() {
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        int index = rg.getCheckedRadioButtonId();
        if (index == R.id.selectButton) {
            EVENT = "Select";
        } else if (index == R.id.rectButton) {
            EVENT = "Rect";
        } else if (index == R.id.ovalButton) {
            EVENT = "Oval";
        } else {
            EVENT = "Erase";
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getEvent();
        if (EVENT.equals("Rect") || EVENT.equals("Oval")) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x1 = event.getX();
                    y1 = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    x2 = event.getX();
                    y2 = event.getY();

                    x = Math.min(x1, x2);
                    y = Math.min(y1, y2);
                    width = Math.max(x1, x2) - x;
                    height = Math.max(y1, y2) - y;

                    if (EVENT.equals("Rect")) {
                        GRect newRect = new GRect(x, y, width, height, false);
                        shapes.add(newRect);
                        System.out.println("Rect - x: " + x + " y: " + y);
                    } else {
                        GOval newOval = new GOval(x, y, width, height, false);
                        shapes.add(newOval);
                        System.out.println("Oval - x: " + x + " y: " + y);
                    }
                    invalidate();
            }

        }
        return true;
    }
}
