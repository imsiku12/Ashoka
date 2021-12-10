package com.cloudplace.ashoka.Pending;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cloudplace.ashoka.MainActivity;
import com.cloudplace.ashoka.R;
import com.cloudplace.ashoka.menu.MenuActivity;

public class Screen20 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_20);

        findViewById(R.id.nextBtnTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Screen20.this, MenuActivity.class));
            }
        });

    }
}