package com.cloudplace.ashoka.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cloudplace.ashoka.R;
import com.cloudplace.ashoka.carmanagement.CarManagementActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView creditLineAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        init_view();
    }

    private void init_view() {
        creditLineAvailable = findViewById(R.id.credit_line_available_tv);
        creditLineAvailable.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MenuActivity.this, CarManagementActivity.class);
        startActivity(intent);
    }
}