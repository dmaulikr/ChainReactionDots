package com.droid.joshxjin.chainreactiondots;

import android.app.Activity;
import android.app.usage.ConfigurationStats;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    SharedPreferences sharedPreferences;

    Button normalGameBtn, survivalGameBtn;
    ImageButton settingsBtn;
    String currentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Full screen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //store screen dimensions
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (currentLayout == null || currentLayout.equals("splashScreen")) {
            launchSplashScreen();
        }
    }

    public void launchSplashScreen() {
        currentLayout = "splashScreen";
        setContentView(R.layout.activity_main);

        normalGameBtn = (Button)findViewById(R.id.normalGameBtn);
        normalGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchNormalGame();
            }
        });

        survivalGameBtn = (Button)findViewById(R.id.survivalGameBtn);
        survivalGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //launchSurvialGame();
            }
        });

        settingsBtn = (ImageButton)findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSettings();
            }
        });

    }

    public void launchNormalGame() {
        currentLayout = "normalGame";
        setContentView(R.layout.normal_game);
    }

    public void launchSurvivalGame() {
        currentLayout = "survivalGame";
    }

    public void launchSettings() {
        Intent i = new Intent(this, PreferencesActivity.class);
        startActivity(i);
    }

    public void loadPreferences() {
        Constants.dotNumber = Integer.parseInt(sharedPreferences.getString("normalDotNum", "" + 20));

        String normalDifficulty = sharedPreferences.getString("normalDifficulty", "40%");
        String survivalDifficulty = sharedPreferences.getString("survivalDifficulty", "40%");

        switch(normalDifficulty) {
            case "40%":
                Constants.normalTargetNumber = (int)(Constants.dotNumber * 0.4);
                break;
            case "50%":
                Constants.normalTargetNumber = (int)(Constants.dotNumber * 0.5);
                break;
            case "60%":
                Constants.normalTargetNumber = (int)(Constants.dotNumber * 0.6);
                break;
            case "70%":
                Constants.normalTargetNumber = (int)(Constants.dotNumber * 0.7);
                break;
            case "80%":
                Constants.normalTargetNumber = (int)(Constants.dotNumber * 0.8);
                break;
        }

        switch(survivalDifficulty) {
            case "40%":
                Constants.survivalTarget = 0.4f;
                break;
            case "50%":
                Constants.survivalTarget = 0.5f;
                break;
            case "60%":
                Constants.survivalTarget = 0.6f;
                break;
            case "70%":
                Constants.survivalTarget = 0.7f;
                break;
            case "80%":
                Constants.survivalTarget = 0.8f;
                break;
        }

        Log.d("dotNumber", "" + Constants.dotNumber);
        Log.d("normalTargetNumber", "" + Constants.normalTargetNumber);
        Log.d("survivalTarget", "" + Constants.survivalTarget);

    }

    @Override
    public void onResume() {
        // Always call the superclass so it can restore the view hierarchy
        super.onResume();

        loadPreferences();

    }

    @Override
    public void onBackPressed() {
        if (!currentLayout.equals("splashScreen")) {
            launchSplashScreen();
        } else {
            super.onBackPressed();
        }
    }

}
