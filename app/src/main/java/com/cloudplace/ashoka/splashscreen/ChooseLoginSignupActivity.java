package com.cloudplace.ashoka.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudplace.ashoka.R;
import com.cloudplace.ashoka.login.LoginActivity;
import com.cloudplace.ashoka.signup.SignUpActivity;

public class ChooseLoginSignupActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView signUpCardLayout;
    private TextView loginCardLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_login_signup);

        init_view();
    }

    private void init_view() {
        loginCardLayout = findViewById(R.id.tv_login);
        signUpCardLayout = findViewById(R.id.tv_signup);

        loginCardLayout.setOnClickListener(this);
        signUpCardLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == loginCardLayout) {
            Intent intent = new Intent(ChooseLoginSignupActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        if (view == signUpCardLayout) {
            Intent intent = new Intent(ChooseLoginSignupActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    }
}