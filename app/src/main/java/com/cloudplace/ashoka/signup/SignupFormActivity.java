package com.cloudplace.ashoka.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudplace.ashoka.R;
import com.cloudplace.ashoka.kycform.FillKycFormActivity;
import com.cloudplace.ashoka.login.LoginActivity;
import com.cloudplace.ashoka.retrofitapicall.AshokaRetrofit;
import com.cloudplace.ashoka.retrofitapicall.GetdataService;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupFormActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout parentLayout;
    private TextInputEditText firstNameEt, surNameEt, emailEt, phoneNumberEt, passwordEt, reEnterPasswordEt;
    private TextView haveAnAccountTv, signUpTv;
    private ProgressDialog progressDialog;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup_form_new);

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("UserMobileNumber");

        init_view();
    }

    private void init_view() {
        parentLayout = findViewById(R.id.signUpFormParentLayout);

        firstNameEt = findViewById(R.id.firstNameEt);
        surNameEt = findViewById(R.id.surNameEt);
        emailEt = findViewById(R.id.emailEt);
        phoneNumberEt = findViewById(R.id.phoneNumberEt);
        passwordEt = findViewById(R.id.passwordEt);
        reEnterPasswordEt = findViewById(R.id.reEnterPasswordEt);

        haveAnAccountTv = findViewById(R.id.haveAnAccountTvForm);
        signUpTv = findViewById(R.id.signUpTvForm);

        haveAnAccountTv.setOnClickListener(this);
        signUpTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == haveAnAccountTv) {
            Intent intent = new Intent(SignupFormActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if (view == signUpTv) {
            String userFirstName = firstNameEt.getText().toString();
            String userSurName = surNameEt.getText().toString();
            String email = emailEt.getText().toString();
            String password = passwordEt.getText().toString();
            String reEnterPassword = reEnterPasswordEt.getText().toString();

            if (userFirstName.isEmpty()) {
                showSnakbar("Firstname is empty");
                firstNameEt.setError("Enter first name");
            } else if (userSurName.isEmpty()) {
                showSnakbar("Surname is empty");
                surNameEt.setError("Enter sur name");
            } else if (email.isEmpty()) {
                showSnakbar("Email is empty");
                emailEt.setError("Enter email");
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                showSnakbar("Please enter valid email");
                emailEt.setError("Enter valid email id");
            } else if (password.isEmpty()) {
                showSnakbar("Password is empty");
                passwordEt.setError("Enter password");
            } else if (reEnterPassword.isEmpty()) {
                showSnakbar("Re-enter password field is empty");
                reEnterPasswordEt.setError("Re-enter password");
            } else if (!password.equalsIgnoreCase(reEnterPassword)) {
                showSnakbar("Password & confirm password mismatch");
            } else {
                showLoaderForUserSignUp();
                user_onboarding_api_call(userFirstName, userSurName, email, phoneNumber, password);
            }
        }
    }

    private void user_onboarding_api_call(String userFirstName, String userSurName, String email, String phoneNumber, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile_number", phoneNumber);
            jsonObject.put("email_id", email);
            jsonObject.put("first_name", userFirstName);
            jsonObject.put("last_name", userSurName);
            jsonObject.put("password", password);
            jsonObject.put("onboard_mode", "Android");

            GetdataService getdataService = AshokaRetrofit.getRetrofitInstance().create(GetdataService.class);
            getdataService.user_onboard(jsonObject).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(response.body().string());
                            String status = jsonObject1.getString("status");
                            String message = jsonObject1.getString("message");
                            String errorMessage = jsonObject1.getString("errorMessage");

                            if (status.equalsIgnoreCase("0")) {
                                progressDialog.dismiss();
                                Toast.makeText(SignupFormActivity.this, message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignupFormActivity.this, FillKycFormActivity.class);
                                intent.putExtra("UserFirstName", userFirstName);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(SignupFormActivity.this, FillKycFormActivity.class);
                                intent.putExtra("UserFirstName", userFirstName);
                                startActivity(intent);
                                finish();
                                progressDialog.dismiss();
                                Toast.makeText(SignupFormActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Intent intent = new Intent(SignupFormActivity.this, FillKycFormActivity.class);
                            intent.putExtra("UserFirstName", userFirstName);
                            startActivity(intent);
                            finish();
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(SignupFormActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Intent intent = new Intent(SignupFormActivity.this, FillKycFormActivity.class);
                            intent.putExtra("UserFirstName", userFirstName);
                            startActivity(intent);
                            finish();
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(SignupFormActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        progressDialog.dismiss();
//                        Toast.makeText(SignupFormActivity.this, "Profile creation failed", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(SignupFormActivity.this, "Please try again 1", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupFormActivity.this, FillKycFormActivity.class);
                        intent.putExtra("UserFirstName", userFirstName);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDialog.dismiss();
//                    Toast.makeText(SignupFormActivity.this, "Profile creation failed", Toast.LENGTH_SHORT).show();
                    Toast.makeText(SignupFormActivity.this, "Please try again 2", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void showSnakbar(String message) {
        Snackbar snackbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snackbar.show();
    }

    private void showLoaderForUserSignUp() {
        progressDialog = new ProgressDialog(SignupFormActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Signing up...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
}