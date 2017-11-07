package edu.stanford.cs108.mobiledraw;

import android.app.Activity;
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
    public EVENT currentEve ;
    private GObject shapeSelected;
    private int selectedIndex = -1;


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

    public enum EVENT {
        SELECT,
        RECT,
        OVAL,
        ERASE;
    }

    private void getEvent() {
        RadioGroup rg = (RadioGroup) ((Activity) getContext()).findViewById(R.id.radioGroup);
        int index = rg.getCheckedRadioButtonId();
        if (index == R.id.selectButton) {
            currentEve = EVENT.SELECT;
        } else if (index == R.id.rectButton) {
            currentEve = EVENT.RECT;
        } else if (index == R.id.ovalButton) {
            currentEve = EVENT.OVAL;
        } else {
            currentEve = EVENT.ERASE;
        }
    }

    private void selectShape(float selectX, float selectY) {
        for (int i = shapes.size()-1; i >=0; i--) {
            GObject g = shapes.get(i);
            if (g.overlap(selectX, selectY)) {
                selectedIndex = i;
                if (g instanceof GRect) {
                    shapeSelected = new GRect(g);
                } else {
                    shapeSelected = new GOval(g);
                }
                shapeSelected.setSelected(true);
                shapes.add(shapeSelected);
                updateEditView(shapeSelected);
                return;
            }
        }
        clearEdit();
    }

    private void eraseShape(float selectX, float selectY) {
        for (int i = shapes.size()-1; i >=0; i--) {
            GObject g = shapes.get(i);
            if (g.overlap(selectX, selectY)) {
                shapes.remove(g);
                break;
            }
        }
    }

    private void clearSelection(){
        if (shapes.size() != 0) {
            if(shapes.get(shapes.size()-1).getSelected()) {
                shapes.remove(shapes.size()-1);
            }
        }
    }

    private void updateEditView(GObject g) {
        EditText editX = (EditText) ((Activity)getContext()).findViewById(R.id.xField);
        EditText editY = (EditText) ((Activity)getContext()).findViewById(R.id.yField);
        EditText editWidth = (EditText) ((Activity)getContext()).findViewById(R.id.widthField);
        EditText editHeight = (EditText) ((Activity)getContext()).findViewById(R.id.heightField);
        editX.setText(String.format("%.05f", g.getX()));
        editY.setText(String.format("%.05f", g.getY()));
        editWidth.setText(String.format("%.05f", g.getWidth()));
        editHeight.setText(String.format("%.05f", g.getHeight()));
    }

    private void clearEdit(){
        EditText editX = (EditText) ((Activity)getContext()).findViewById(R.id.xField);
        EditText editY = (EditText) ((Activity)getContext()).findViewById(R.id.yField);
        EditText editWidth = (EditText) ((Activity)getContext()).findViewById(R.id.widthField);
        EditText editHeight = (EditText) ((Activity)getContext()).findViewById(R.id.heightField);
        editX.setText("");
        editY.setText("");
        editWidth.setText("");
        editHeight.setText("");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getEvent();
        clearSelection();
        if (currentEve == EVENT.RECT || currentEve == EVENT.OVAL) {
            clearEdit();
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

                    if (currentEve == EVENT.RECT) {
                        GRect newRect = new GRect(x, y, width, height, false);
                        GRect newRect2 = new GRect(x, y, width, height, true);
                        shapes.add(newRect);
                        shapes.add(newRect2);
                    } else {
                        GOval newOval = new GOval(x, y, width, height, false);
                        GOval newOval2 = new GOval(x, y, width, height, true);
                        shapes.add(newOval);
                        shapes.add(newOval2);
                    }
                    selectedIndex = shapes.size()-2;
                    updateEditView(shapes.get(selectedIndex));
                    invalidate();
            }

        } else if (currentEve == EVENT.SELECT) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = event.getX();
                    y = event.getY();
                    selectShape(x, y);
                    invalidate();
            }

        } else {
            clearEdit();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = event.getX();
                    y = event.getY();
                    eraseShape(x, y);
                    invalidate();
            }
        }
        return true;
    }

    public void onClickButton() {
        clearSelection();
        EditText editX = (EditText) ((Activity)getContext()).findViewById(R.id.xField);
        EditText editY = (EditText) ((Activity)getContext()).findViewById(R.id.yField);
        EditText editWidth = (EditText) ((Activity)getContext()).findViewById(R.id.widthField);
        EditText editHeight = (EditText) ((Activity)getContext()).findViewById(R.id.heightField);

        if (selectedIndex!= -1 && shapes.size() != 0) {
            GObject g = shapes.get(selectedIndex);
            g.update(Float.valueOf(editX.getText().toString()),
                    Float.valueOf(editY.getText().toString()),
                    Float.valueOf(editWidth.getText().toString()),
                    Float.valueOf(editHeight.getText().toString()));
            if (g instanceof GRect) {
                shapeSelected = new GRect(g);
            } else {
                shapeSelected = new GOval(g);
            }
            shapeSelected.setSelected(true);
            shapes.add(shapeSelected);
        }
        invalidate();
    }
}
