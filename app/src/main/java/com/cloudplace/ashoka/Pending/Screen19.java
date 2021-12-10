package com.cloudplace.ashoka.Pending;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cloudplace.ashoka.R;

public class Screen19 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_19);

        findViewById(R.id.nextBtnTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Screen19.this, Screen20.class));
            }
        });
    }
}