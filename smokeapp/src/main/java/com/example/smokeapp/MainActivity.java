package com.example.smokeapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.common.ActivityPanelBase;
import com.example.common.AppPrefsHelper;
import com.example.common.PrefsConstants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppPrefsHelper prefs = new AppPrefsHelper(this, PrefsConstants.PREFS_SMOKE);

        String name = prefs.getString("username", "");
        int maxPerDay = prefs.getInt("maxPerDay", 0);

        if (name.isEmpty() || maxPerDay == 0) {
            Intent intent = new Intent(this, ActivityRegister.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ActivityPanel.class);
            intent.putExtra("username", name);
            startActivity(intent);
        }

        finish();
    }
}
