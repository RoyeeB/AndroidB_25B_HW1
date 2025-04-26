package com.example.common;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class ActivityPanelBase extends AppCompatActivity {

    private MaterialTextView app_LBL_name, counter_LBL,week_LBL , month_LBL,welcome_LBL ;
    private ShapeableImageView main_IMG;
    private AppCompatImageButton add_BTN, reduce_BTN, report_BTN, avg_BTN;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_panel_base);
        initViews();
        initListeners();
        updateCounterUI();
    }

    private void initViews() {
        app_LBL_name = findViewById(R.id.app_LBL_name);
        counter_LBL = findViewById(R.id.counter_LBL);
        week_LBL = findViewById(R.id.week_LBL);
        month_LBL = findViewById(R.id.month_LBL);
        main_IMG = findViewById(R.id.main_IMG);
        add_BTN = findViewById(R.id.add_BTN);
        reduce_BTN = findViewById(R.id.reduce_BTN);
        report_BTN = findViewById(R.id.report_BTN);
        avg_BTN = findViewById(R.id.avg_BTN);
        welcome_LBL = findViewById(R.id.welcome_LBL);
    }

    private void initListeners() {
        add_BTN.setOnClickListener(v -> increaseCounter());
        reduce_BTN.setOnClickListener(v -> decreaseCounter());
    }

    private void increaseCounter() {
        counter++;
        updateCounterUI();
    }

    private void decreaseCounter() {
        if (counter > 0) {
            counter--;
        }
        updateCounterUI();
    }

    private void updateCounterUI() {
        counter_LBL.setText(String.valueOf(counter));
    }

    protected void resetCounter() {
        counter = 0;
        updateCounterUI();
        Log.d("CounterReset", "Counter Reset success");
    }


}