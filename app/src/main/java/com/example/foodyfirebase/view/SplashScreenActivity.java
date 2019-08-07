package com.example.foodyfirebase.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.foodyfirebase.R;

public class SplashScreenActivity extends AppCompatActivity {

    static final int TIME_TO_LOADING = 2000;
    TextView txtVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);

        txtVersion = findViewById(R.id.txtVersion);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
            txtVersion.setText( getString(R.string.version) + " " + packageInfo.versionName);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent iSignIn = new Intent(SplashScreenActivity.this,SignInActivity.class);
                    startActivity(iSignIn);
                }
            },TIME_TO_LOADING);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
}
