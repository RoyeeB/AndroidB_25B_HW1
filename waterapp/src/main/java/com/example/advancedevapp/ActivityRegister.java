package com.example.advancedevapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.advancedevapp.ActivityPanel;
import com.example.common.ActivityPanelBase;
import com.example.common.AppPrefsHelper;
import com.example.common.PrefsConstants;
import com.google.android.material.button.MaterialButton;

public class ActivityRegister extends ActivityPanelBase {

    private AppCompatEditText fullname_EDT, weight_EDT, stepsAvg_EDT;
    private MaterialButton register_BTN;
    private AppPrefsHelper prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = new AppPrefsHelper(this, PrefsConstants.PREFS_WATER);
        String username = prefs.getString("username", null);
        if (username != null && !username.isEmpty()) {
            startActivity(new Intent(this, ActivityPanel.class));
            finish();
            return;
        }
        setContentView(com.example.common.R.layout.activity_register);
        initViews();
        initListeners();
    }

    private void initViews() {
        fullname_EDT = findViewById(com.example.common.R.id.fullname_EDT);
        weight_EDT = findViewById(com.example.common.R.id.option1_EDT);
        stepsAvg_EDT = findViewById(com.example.common.R.id.option2_EDT);
        register_BTN = findViewById(com.example.common.R.id.register_BTN);
    }

    private void initListeners() {
        register_BTN.setOnClickListener(v -> handleRegister());
    }

    private void handleRegister() {
        String fullName = fullname_EDT.getText().toString().trim();
        String weightStr = weight_EDT.getText().toString().trim();
        String stepsAvgStr = stepsAvg_EDT.getText().toString().trim();
        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(weightStr) || TextUtils.isEmpty(stepsAvgStr)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        int weight = Integer.parseInt(weightStr);
        int stepsAvg = Integer.parseInt(stepsAvgStr);
        prefs.saveWaterData(fullName, stepsAvg, weight);
        prefs.putInt("startWeight", weight);
        String savedName = prefs.getString("username", "dontSave");
        int savedWeight = prefs.getInt("startWeight", -1);
        int savedStepsAvg = prefs.getInt("stepsAvg", -1);
        Log.d("RegisterLog", "Saved User: " + savedName + ", Weight: " + savedWeight + ", Steps Avg: " + savedStepsAvg);
        Toast.makeText(this,
                "Saved " + savedName + " | Weight: " + savedWeight + "kg | Steps Avg: " + savedStepsAvg,
                Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, ActivityPanel.class));
        finish();
    }
}
