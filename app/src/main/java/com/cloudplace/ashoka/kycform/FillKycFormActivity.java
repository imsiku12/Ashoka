package com.cloudplace.ashoka.kycform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cloudplace.ashoka.R;
import com.cloudplace.ashoka.signup.SignUpActivity;

public class FillKycFormActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView backBtnTv,nextBtnTv;
    private TextView welcomeMsgUserTv;
    private String userFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fill_kyc_form);

        Intent intent = getIntent();
        userFullName = "Welcome " + intent.getStringExtra("UserFirstName") + " !";

        init_view();
    }

    private void init_view() {
        welcomeMsgUserTv = findViewById(R.id.userWelcomeMsgTv);
        backBtnTv = findViewById(R.id.backBtnTv);
        nextBtnTv = findViewById(R.id.nextBtnTv);

        backBtnTv.setOnClickListener(this);
        nextBtnTv.setOnClickListener(this);

        welcomeMsgUserTv.setText(userFullName);
    }

    @Override
    public void onClick(View view) {
        if (view ==  backBtnTv){
            Intent intent = new Intent(FillKycFormActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        }
        if (view == nextBtnTv){
            Intent intent = new Intent(FillKycFormActivity.this, KYCDetailsNewActivity.class);
            startActivity(intent);
            finish();
        }
    }
}