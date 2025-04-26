package com.example.advancedevapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.advancedevapp.ActivityPanel;
import com.example.advancedevapp.ActivityRegister;
import com.example.common.AppPrefsHelper;
import com.example.common.PrefsConstants;

public class MainActivity extends AppCompatActivity {

    private AppPrefsHelper prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = new AppPrefsHelper(this, PrefsConstants.PREFS_WATER);

        String username = prefs.getString("username", null);

        if (username == null || username.isEmpty()) {
            Log.d("MainActivityLog", "No user found. Moving to Register screen.");
            startActivity(new Intent(this, ActivityRegister.class));
        } else {
            int startWeight = prefs.getInt("startWeight", -1);
            int stepsAvg = prefs.getInt("stepsAvg", -1);
            Log.d("MainActivityLog", "User: " + username + ", Weight: " + startWeight + ", StepsAvg: " + stepsAvg);
            startActivity(new Intent(this, ActivityPanel.class));
        }
        finish();
    }
}
