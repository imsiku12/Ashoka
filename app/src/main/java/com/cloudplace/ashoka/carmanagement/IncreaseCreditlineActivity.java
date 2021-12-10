package com.cloudplace.ashoka.carmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudplace.ashoka.R;
import com.cloudplace.ashoka.carmanagement.congratulations.CongratulationsActivity;

public class IncreaseCreditlineActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView modifyTv,backTv;
    private LinearLayout increaseCreditLine,decreaseCreditLine,userDeteailsToModifyCreditline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_increase_creditline);

        init_view();
    }

    private void init_view() {

        increaseCreditLine = findViewById(R.id.increase_credit_line_LL);
        decreaseCreditLine = findViewById(R.id.decrease_credit_line_LL);
        userDeteailsToModifyCreditline = findViewById(R.id.user_deteails_to_modify_creditline_LL);
        modifyTv = findViewById(R.id.modify_Tv);
        backTv = findViewById(R.id.back_Tv);

        increaseCreditLine.setOnClickListener(this);
        decreaseCreditLine.setOnClickListener(this);
        modifyTv.setOnClickListener(this);
        backTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == modifyTv){
            Intent intent = new Intent(IncreaseCreditlineActivity.this, CongratulationsActivity.class);
            startActivity(intent);
            finish();
        }
        else if (view == backTv){
            finish();
        }
        else if (view == increaseCreditLine){
            userDeteailsToModifyCreditline.setVisibility(View.VISIBLE);;
            decreaseCreditLine.setVisibility(View.GONE);
        }
        else if (view == decreaseCreditLine){
            userDeteailsToModifyCreditline.setVisibility(View.VISIBLE);
            increaseCreditLine.setVisibility(View.GONE);
        }

    }
}