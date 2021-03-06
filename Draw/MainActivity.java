package edu.stanford.cs108.mobiledraw;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    RectView rectView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RadioGroup rd = (RadioGroup) findViewById(R.id.radioGroup);
        rd.check(R.id.rectButton);
        rectView = (RectView) findViewById(R.id.rectView);
    }

    public void onClick(View view) {
        rectView.onClickButton();
    }


}
