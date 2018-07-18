package com.example.user.quancafe.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.user.quancafe.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1000);
                    Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                    startActivity(intent);
                    finish();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
