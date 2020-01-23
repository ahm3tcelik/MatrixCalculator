package com.ahmetc.matriscalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(2500);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
        timerThread.start();
    }
}
