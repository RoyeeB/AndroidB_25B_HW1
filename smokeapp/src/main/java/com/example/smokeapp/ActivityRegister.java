package com.example.smokeapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.common.ActivityPanelBase;
import com.example.common.AppPrefsHelper;
import com.example.common.PrefsConstants;
import com.google.android.material.button.MaterialButton;

public class ActivityRegister extends ActivityPanelBase {

    private AppCompatEditText fullname_EDT, option1_EDT, option2_EDT;
    private MaterialButton register_BTN;
    private AppPrefsHelper prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = new AppPrefsHelper(this, PrefsConstants.PREFS_SMOKE);
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
        option1_EDT = findViewById(com.example.common.R.id.option1_EDT);
        option2_EDT = findViewById(com.example.common.R.id.option2_EDT);
        register_BTN = findViewById(com.example.common.R.id.register_BTN);
    }

    private void initListeners() {
        register_BTN.setOnClickListener(v -> handleRegister());
    }

    private void handleRegister() {
        String fullName = fullname_EDT.getText().toString().trim();
        String option1Str = option1_EDT.getText().toString().trim();
        String option2Str = option2_EDT.getText().toString().trim();
        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(option1Str) || TextUtils.isEmpty(option2Str)) {
            Toast.makeText(this, "Something empty! check again", Toast.LENGTH_SHORT).show();
            return;
        }
        int option1 = Integer.parseInt(option1Str);
        int option2 = Integer.parseInt(option2Str);
        prefs.saveSmokingData(fullName, option1, option2);
        prefs.putInt("startValue", option1);
        Log.d("RegisterLog", "SaveName: " + fullName + " TodaySmoke: " + option1 + ", YourTarget " + option2);
        Toast.makeText(this,
                "Saved " + fullName + " | TodaySmoke: " + option1 + " | YourTarget " + option2,
                Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, ActivityPanel.class));
        finish();
    }
}
