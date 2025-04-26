package com.example.smokeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;

import com.example.common.ActivityPanelBase;
import com.example.common.AppPrefsHelper;
import com.example.common.PrefsConstants;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class ActivityPanel extends ActivityPanelBase {
    
    private MaterialTextView week_LBL ,month_LBL , counter_LBL,welcomeLBL  ;
    private MaterialButton save_BTN;
    private AppCompatImageButton avg_BTN , report_BTN;
    private AppPrefsHelper prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initData();
        initListeners();
    }

    private void initViews() {
        welcomeLBL = findViewById(com.example.common.R.id.welcome_LBL);
        counter_LBL = findViewById(com.example.common.R.id.counter_LBL);
        week_LBL = findViewById(com.example.common.R.id.week_LBL);
        month_LBL = findViewById(com.example.common.R.id.month_LBL);
        save_BTN = findViewById(com.example.common.R.id.save_BTN);
        avg_BTN = findViewById(com.example.common.R.id.avg_BTN);
        report_BTN = findViewById(com.example.common.R.id.report_BTN);
    }

    private void initData() {
        prefs = new AppPrefsHelper(this, PrefsConstants.PREFS_SMOKE);
        String name = prefs.getString("username", "NoNameUser");
        int target = prefs.getInt("maxPerDay", -1);
        int started = prefs.getInt("startValue", -1);
        welcomeLBL.setText("Welcome " + name + "!\nYour target is: " + target + "\nYou started with: " + started);
    }

    private void initListeners() {
        save_BTN.setOnClickListener(v -> handleSave());
        avg_BTN.setOnClickListener(v -> updateAverages());
        report_BTN.setOnClickListener(v -> checkViolations());
    }

    private void handleSave() {
        int count = Integer.parseInt(counter_LBL.getText().toString());
        String history = prefs.getString("smokeHistory", "");
        history = history + (history.isEmpty() ? "" : ",") + count;
        prefs.putString("smokeHistory", history);
        prefs.putInt("smokedToday", count);
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        resetCounter();
    }

    private void updateAverages() {
        int avgWeek = calculateAverage(7);
        if (avgWeek != -1) {
            week_LBL.setVisibility(View.VISIBLE);
            week_LBL.setText("Average smoked last week: " + avgWeek);
        } else {
            week_LBL.setVisibility(View.GONE);
        }
        int avgMonth = calculateAverage(30);
        if (avgMonth != -1) {
            month_LBL.setVisibility(View.VISIBLE);
            month_LBL.setText("Average smoked last month: " + avgMonth);
        } else {
            month_LBL.setVisibility(View.GONE);
        }
    }

    private int calculateAverage(int numItems) {
        String history = prefs.getString("smokeHistory", "");
        String[] values = history.split(",");
        if (values.length < numItems) return -1;
        int sum = 0, count = 0;
        for (int i = values.length - numItems; i < values.length; i++) {
            try {
                sum += Integer.parseInt(values[i]);
                count++;
            } catch (NumberFormatException ignored) {}
        }
        return count > 0 ? sum / count : -1;
    }

    private void checkViolations() {
        String history = prefs.getString("smokeHistory", "");
        int maxPerDay = prefs.getInt("maxPerDay", -1);

        if (maxPerDay == -1 || history.isEmpty()) {
            week_LBL.setVisibility(View.GONE);
            month_LBL.setVisibility(View.GONE);
            return;
        }
        String[] values = history.split(",");
        if (values.length >= 7) {
            int weeklyViolations = 0;
            for (int i = values.length - 7; i < values.length; i++) {
                try {
                    int daily = Integer.parseInt(values[i]);
                    if (daily > maxPerDay) {
                        weeklyViolations++;
                    }
                } catch (NumberFormatException ignored) {}
            }
            week_LBL.setVisibility(View.VISIBLE);
            week_LBL.setText("Weekly violations: " + weeklyViolations);
        } else {
            week_LBL.setVisibility(View.GONE);
        }

        if (values.length >= 30) {
            int monthlyViolations = 0;
            for (int i = values.length - 30; i < values.length; i++) {
                try {
                    int daily = Integer.parseInt(values[i]);
                    if (daily > maxPerDay) {
                        monthlyViolations++;
                    }
                } catch (NumberFormatException ignored) {}
            }
            month_LBL.setVisibility(View.VISIBLE);
            month_LBL.setText("Monthly violations: " + monthlyViolations);
        } else {
            month_LBL.setVisibility(View.GONE);
        }
    }


}
