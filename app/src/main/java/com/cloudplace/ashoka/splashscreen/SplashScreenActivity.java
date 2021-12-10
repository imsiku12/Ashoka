package com.cloudplace.ashoka.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cloudplace.ashoka.R;

public class SplashScreenActivity extends AppCompatActivity {
    private RelativeLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen_new);

        init_view();

        show_splash_screen();
    }

    private void show_splash_screen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i= new Intent(SplashScreenActivity.this, ChooseLoginSignupActivity.class);
                startActivity(i);
                finish();
            }
        }, 1500);
    }

    private void init_view() {
        parentLayout = findViewById(R.id.parentLayout);
    }
}