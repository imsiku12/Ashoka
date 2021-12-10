package com.cloudplace.ashoka.carmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cloudplace.ashoka.R;

public class CarManagementActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView modifyTv,backTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_car_management);

        init_view();

    }

    private void init_view() {
        modifyTv = findViewById(R.id.modifyTv);
        backTv = findViewById(R.id.backTv);

        modifyTv.setOnClickListener(this);
        backTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == modifyTv){
            Intent intent = new Intent(CarManagementActivity.this,IncreaseCreditlineActivity.class);
            startActivity(intent);
            finish();
        }
        else if (view == backTv){
            finish();
        }
    }
}