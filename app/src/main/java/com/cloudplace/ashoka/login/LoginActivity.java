package com.cloudplace.ashoka.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudplace.ashoka.R;
import com.cloudplace.ashoka.menu.MenuActivity;
import com.cloudplace.ashoka.retrofitapicall.AshokaRetrofit;
import com.cloudplace.ashoka.retrofitapicall.GetdataService;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userNameEt, passwordEt;
    private LinearLayout dontHaveAccountTv, signInTv;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_new);

        init_view();
    }

    private void init_view() {
        userNameEt = findViewById(R.id.et_mobile_number);
        passwordEt = findViewById(R.id.et_password);
        dontHaveAccountTv = findViewById(R.id.dontHaveAnAccountTv);
        signInTv = findViewById(R.id.signInTv);

        dontHaveAccountTv.setOnClickListener(this);
        signInTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == dontHaveAccountTv) {
            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if (view == signInTv) {
            if (userNameEt.getText().toString().isEmpty()) {
                userNameEt.setError("user name is empty");
            } else if (passwordEt.getText().toString().isEmpty()) {
                passwordEt.setError("password is empty");
            } else {
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();

                //Login(userNameEt.getText().toString(), passwordEt.getText().toString());
            }
        }
    }

    private void Login(String sMobile, String sPassword) {
        showLoader();
        GetdataService getdataService = AshokaRetrofit.getRetrofitInstance().create(GetdataService.class);
        getdataService.user_login(sMobile, sPassword).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {
                        JSONObject responseObject = new JSONObject(response.body().string());
                        String status = responseObject.getString("status");
                        String message = responseObject.getString("message");

                        if (status.equals("0")) {
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Oops.. Something went wrong. Please try again...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoader() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Sending otp");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

}