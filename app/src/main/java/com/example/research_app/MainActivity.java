package com.example.research_app;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

public class MainActivity extends Activity {
    Intent intent;
    //Called when activity if first called
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, LoginActivity.class);
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {

                startActivity(intent);
                finish();
            }
        }, 3000);

    }
}