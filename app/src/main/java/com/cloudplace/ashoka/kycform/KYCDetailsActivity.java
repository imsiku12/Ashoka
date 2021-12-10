package com.cloudplace.ashoka.kycform;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudplace.ashoka.R;
import com.cloudplace.ashoka.menu.MenuActivity;
import com.cloudplace.ashoka.retrofitapicall.AshokaRetrofit;
import com.cloudplace.ashoka.retrofitapicall.GetdataService;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.service.controls.ControlsProviderService.TAG;

public class KYCDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText businessRegidEt,balanceSheetEt,addressEt;
    private TextView uploadedImgTv,backBtnTv,nextBtnTv;
    private ProgressDialog progressDialog;

    private ImageView uploadImageIv;
    private Uri contentUri;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    UploadTask uploadTask;

    private String imageURIForAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_k_y_c_details);

        init_view();
    }

    private void init_view() {
        uploadImageIv = findViewById(R.id.img_upload_IV);
        uploadedImgTv = findViewById(R.id.imageUploadedURLTv);
        businessRegidEt = findViewById(R.id.businessRegId);
        balanceSheetEt = findViewById(R.id.balanceSheet);
        addressEt = findViewById(R.id.address);
        backBtnTv = findViewById(R.id.backBtnTvKyc);
        nextBtnTv = findViewById(R.id.nextBtnTvKyc);

        backBtnTv.setOnClickListener(this);
        nextBtnTv.setOnClickListener(this);
        uploadImageIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == uploadImageIv){
            CropImage.startPickImageActivity(this);
        }
        if (view == backBtnTv){
            Intent intent = new Intent(KYCDetailsActivity.this, FillKycFormActivity.class);
            startActivity(intent);
            finish();
        }
        if (view == nextBtnTv){
            if (businessRegidEt.getText().toString().isEmpty()){
                Toast.makeText(this, "Business reg id shouldn't empty", Toast.LENGTH_SHORT).show();
                businessRegidEt.setError("enter business regid");
            }
            else if (balanceSheetEt.getText().toString().isEmpty()){
                Toast.makeText(this, "Balance sheet field shouldn't empty", Toast.LENGTH_SHORT).show();
                balanceSheetEt.setError("enter balance sheet details");
            }
            else if (addressEt.getText().toString().isEmpty()){
                Toast.makeText(this, "Address field shouldn't empty", Toast.LENGTH_SHORT).show();
                addressEt.setError("enter address");
            }
            else {
                showLoaderForUserKYC();
                String businessRegId = businessRegidEt.getText().toString();
                String balanceSheet = balanceSheetEt.getText().toString();
                String address = addressEt.getText().toString();

                user_kyc_api_call(businessRegId,balanceSheet,address);
            }
        }
    }

    private void user_kyc_api_call(String businessRegId, String balanceSheet, String address) {
        GetdataService getdataService = AshokaRetrofit.getRetrofitInstance().create(GetdataService.class);
        getdataService.user_kyc("","",imageURIForAPI,businessRegId,balanceSheet,address).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                Intent intent = new Intent(KYCDetailsActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Intent intent = new Intent(KYCDetailsActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void showLoaderForUserKYC() {
        progressDialog = new ProgressDialog(KYCDetailsActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Saving data...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(getApplicationContext(), data);
            if (CropImage.isReadExternalStoragePermissionsRequired(getApplicationContext(), imageUri)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                }
            } else {
                startCropImageActivity(imageUri);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                contentUri = result.getUri();

                final StorageReference ref = storageRef.child("CarDocs/" + new Date().toString());
                uploadTask = ref.putFile(contentUri);
                uploadDocumentLoader();
                final Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return ref.getDownloadUrl();
                    }
                });
                urlTask.addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Uri downloadUri = task.getResult();
                            imageURIForAPI = downloadUri.toString();

                            Toast.makeText(KYCDetailsActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                            uploadedImgTv.setVisibility(View.GONE);
                            uploadedImgTv.setVisibility(View.VISIBLE);


                            Log.d(TAG, "Download URI - "+imageURIForAPI.toString());
                        }
                    }
                });
            }
        }
    }

    private void uploadDocumentLoader() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Uploading image...");
        progressDialog.show();;
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }
}