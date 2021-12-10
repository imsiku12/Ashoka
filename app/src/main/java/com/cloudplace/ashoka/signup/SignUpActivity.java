package com.cloudplace.ashoka.signup;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudplace.ashoka.R;
import com.cloudplace.ashoka.login.LoginActivity;
import com.cloudplace.ashoka.retrofitapicall.AshokaRetrofit;
import com.cloudplace.ashoka.retrofitapicall.Constants;
import com.cloudplace.ashoka.retrofitapicall.GetdataService;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import in.aabhasjindal.otptextview.OtpTextView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView sendOTPTv;
    private LinearLayout signUpTv, haveAnAccountTv;
    private EditText etMobileNumber;
    private RelativeLayout parentLayout;
    private String TAG = "SignUpActivity";
    private ProgressDialog progressDialog;
    private OtpTextView otpValue;
    private String userMobileNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_new);

        init_view();
    }

    private void init_view() {
        parentLayout = findViewById(R.id.parentLayout);
        etMobileNumber = findViewById(R.id.user_mobile_number_Et);
        sendOTPTv = findViewById(R.id.sendOtpTv);
        otpValue = findViewById(R.id.otp_view);
        haveAnAccountTv = findViewById(R.id.haveAnAccountTv);
        signUpTv = findViewById(R.id.signUpTv);

        sendOTPTv.setOnClickListener(this);
        haveAnAccountTv.setOnClickListener(this);
        signUpTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == sendOTPTv) {
            userMobileNo = etMobileNumber.getText().toString();
            if (etMobileNumber.getText().toString().isEmpty()) {
                showSnakbar(Constants.mobile_number_empty);
                etMobileNumber.setError(Constants.mobile_number_required);
            }
//            if (userMobileNumberInt < 10){
//                showSnakbar(Constants.mobile_number_less_than_ten);
//            }
            else {
                showLoaderForSendOTP();
                send_otp_api_call(etMobileNumber.getText().toString());
            }
        }
        if (view == haveAnAccountTv) {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if (view == signUpTv) {
            if (etMobileNumber.getText().toString().equals("")) {
                showSnakbar(Constants.mobile_number_empty);
                etMobileNumber.setError(Constants.mobile_number_required);
            } else {
                if (!otpValue.getOTP().isEmpty()) {
                    showLoaderForSignup();
                    Log.d(TAG, "OTP VAL - " + otpValue.getOTP().toString());
                    String otpValStr = otpValue.getOTP().toString();
                    if (otpValStr.equalsIgnoreCase("12345")) {
                        Toast.makeText(SignUpActivity.this, "OTP Verified", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, SignupFormActivity.class);
                        intent.putExtra("UserMobileNumber", userMobileNo);
                        progressDialog.dismiss();
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Invalid Otp", Toast.LENGTH_SHORT).show();
                    }


//                verify_otp(userMobileNo, otpValStr);
                } else {
                    Toast.makeText(SignUpActivity.this, "Please enter OTP.", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show();
        }
    }


    private void send_otp_api_call(String userMobileNumber) {

        GetdataService getdataService = AshokaRetrofit.getRetrofitInstance().create(GetdataService.class);
        getdataService.send_otp(userMobileNumber).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        JSONObject responseObj = new JSONObject(response.body().string());
                        String message = responseObj.getString("message");
                        String otp = responseObj.getString("otp");

                        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                        showSnakbar("Your Otp - " + otp);

                        etMobileNumber.clearFocus();
                        otpValue.requestFocusOTP();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        Toast.makeText(SignUpActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Api error, Please try again", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, "Response Data - " + response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    private void verify_otp(String userMbleNumberStr, String otpValStr) {

        GetdataService getdataService = AshokaRetrofit.getRetrofitInstance().create(GetdataService.class);
        getdataService.verify_otp(userMbleNumberStr, otpValStr).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {
                        JSONObject responseObject = new JSONObject(response.body().string());
                        String status = responseObject.getString("status");
                        String message = responseObject.getString("message");
                        String errorMessage = responseObject.getString("errorMessage");

                        Log.d(TAG, "onResponse: RESPONSE STATUS - " + status);
                        if (status.equalsIgnoreCase("0")) {
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "OTP Verified", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, SignupFormActivity.class);
                            intent.putExtra("UserMobileNumber", userMbleNumberStr);
                            progressDialog.dismiss();
                            startActivity(intent);
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "OTP Mismatched...", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(SignUpActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(SignUpActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SignUpActivity.this, "Verification failed...", Toast.LENGTH_SHORT).show();
                Toast.makeText(SignUpActivity.this, "Please try again...", Toast.LENGTH_SHORT).show();
            }
        });
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

    private void showLoaderForSendOTP() {
        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Sending otp");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void showLoaderForSignup() {
        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Veifying OTP...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
}