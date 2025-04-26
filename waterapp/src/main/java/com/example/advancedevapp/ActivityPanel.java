package com.example.advancedevapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatImageButton;
import com.example.common.ActivityPanelBase;
import com.example.common.AppPrefsHelper;
import com.example.common.PrefsConstants;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class ActivityPanel extends ActivityPanelBase {

    private MaterialTextView welcomeLBL, counter_LBL, week_LBL, month_LBL;
    private MaterialButton save_BTN;
    private AppCompatImageButton avg_BTN, report_BTN;
    private AppPrefsHelper prefs;
    private int dailyCupsGoal = 0;

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
        prefs = new AppPrefsHelper(this, PrefsConstants.PREFS_WATER);
        String name = prefs.getString("username", "User");
        int weight = prefs.getInt("startWeight", -1);
        int stepsAvg = prefs.getInt("stepsAvg", -1);
        dailyCupsGoal = calculateDailyCups(weight, stepsAvg);
        welcomeLBL.setText("Welcome " + name + "!\nTarget: " + dailyCupsGoal + " cups per day");
    }

    private void initListeners() {
        save_BTN.setOnClickListener(v -> handleSave());
        avg_BTN.setOnClickListener(v -> updateAverages());
        report_BTN.setOnClickListener(v -> checkViolations());
    }

    private void handleSave() {
        int count = Integer.parseInt(counter_LBL.getText().toString());
        String history = prefs.getString("waterHistory", "");
        history = history + (history.isEmpty() ? "" : ",") + count;
        prefs.putString("waterHistory", history);
        Log.d("WaterLog", "Updated Water History: " + history);

        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        resetCounter();
    }

    private void updateAverages() {
        int avgWeek = calculateAverage(7);
        if (avgWeek != -1) {
            week_LBL.setVisibility(View.VISIBLE);
            week_LBL.setText("Weekly average: " + avgWeek + " cups");
        } else {
            week_LBL.setVisibility(View.GONE);
        }
        int avgMonth = calculateAverage(30);
        if (avgMonth != -1) {
            month_LBL.setVisibility(View.VISIBLE);
            month_LBL.setText("Monthly average: " + avgMonth + " cups");
        } else {
            month_LBL.setVisibility(View.GONE);
        }
    }

    private void checkViolations() {
        String history = prefs.getString("waterHistory", "");
        if (dailyCupsGoal == 0 || history.isEmpty()) {
            week_LBL.setVisibility(View.GONE);
            month_LBL.setVisibility(View.GONE);
            return;
        }
        String[] values = history.split(",");
        if (values.length >= 7) {
            int weeklyViolations = 0;
            for (int i = values.length - 7; i < values.length; i++) {
                try {
                    int cups = Integer.parseInt(values[i]);
                    if (cups < dailyCupsGoal) {
                        weeklyViolations++;
                    }
                } catch (NumberFormatException ignored) {}
            }
            week_LBL.setVisibility(View.VISIBLE);
            week_LBL.setText("Weekly under-target days: " + weeklyViolations);
        } else {
            week_LBL.setVisibility(View.GONE);
        }
        if (values.length >= 30) {
            int monthlyViolations = 0;
            for (int i = values.length - 30; i < values.length; i++) {
                try {
                    int cups = Integer.parseInt(values[i]);
                    if (cups < dailyCupsGoal) {
                        monthlyViolations++;
                    }
                } catch (NumberFormatException ignored) {}
            }
            month_LBL.setVisibility(View.VISIBLE);
            month_LBL.setText("Monthly under-target days: " + monthlyViolations);
        } else {
            month_LBL.setVisibility(View.GONE);
        }
    }

    private int calculateAverage(int numItems) {
        String history = prefs.getString("waterHistory", "");
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

    private int calculateDailyCups(int weightKg, int stepsPerDay) {
        int baseWaterMl = weightKg * 35;
        int stepsWaterMl = (stepsPerDay / 1000) * 100;
        int totalMl = baseWaterMl + stepsWaterMl;
        return totalMl / 180;
    }
}
