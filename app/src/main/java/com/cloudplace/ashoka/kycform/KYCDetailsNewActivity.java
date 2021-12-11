package com.cloudplace.ashoka.kycform;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.cloudplace.ashoka.Pending.Screen17;
import com.cloudplace.ashoka.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class KYCDetailsNewActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_reg_no_comp, et_comp_name, et_last_name, et_first_name, et_county, et_city, et_pin, et_street, et_number_of_bul;
    String address = "";
    TextView tv_save;
    CardView cv_bus_reg_doc, cv_co_mana_dir_id, cv_last_3_bal_sheet, cv_3_acc_rec;
    private String business_reg_doc_url = "", company_ma_direc_id_url = "", last_3_bal_sheet_url = "", last_3_bank_acc_rec_url = "";

    private String business_reg_doc = "1", company_ma_direc_id = "2", last_3_bal_sheet = "3", last_3_bank_acc_rec = "4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_kyc);

        init_view();
        onClickFun();
    }

    private void init_view() {
        et_reg_no_comp = findViewById(R.id.et_reg_no_comp);
        et_comp_name = findViewById(R.id.et_comp_name);
        et_last_name = findViewById(R.id.et_last_name);
        et_first_name = findViewById(R.id.et_first_name);
        et_county = findViewById(R.id.et_county);
        et_city = findViewById(R.id.et_city);
        et_pin = findViewById(R.id.et_pin);
        et_street = findViewById(R.id.et_street);
        et_number_of_bul = findViewById(R.id.et_number_of_bul);

        cv_bus_reg_doc = findViewById(R.id.cv_bus_reg_doc);
        cv_last_3_bal_sheet = findViewById(R.id.cv_last_3_bal_sheet);
        cv_3_acc_rec = findViewById(R.id.cv_3_acc_rec);
        cv_co_mana_dir_id = findViewById(R.id.cv_co_mana_dir_id);

        tv_save = findViewById(R.id.tv_save);
    }

    @Override
    public void onClick(View view) {
//        if (view == uploadImageIv) {
//            CropImage.startPickImageActivity(this);
//        }
//        if (view == backBtnTv) {
//            Intent intent = new Intent(KYCDetailsNewActivity.this, FillKycFormActivity.class);
//            startActivity(intent);
//            finish();
//        }
//        if (view == nextBtnTv) {
//            if (businessRegidEt.getText().toString().isEmpty()) {
//                Toast.makeText(this, "Business reg id shouldn't empty", Toast.LENGTH_SHORT).show();
//                businessRegidEt.setError("enter business regid");
//            } else if (balanceSheetEt.getText().toString().isEmpty()) {
//                Toast.makeText(this, "Balance sheet field shouldn't empty", Toast.LENGTH_SHORT).show();
//                balanceSheetEt.setError("enter balance sheet details");
//            } else if (addressEt.getText().toString().isEmpty()) {
//                Toast.makeText(this, "Address field shouldn't empty", Toast.LENGTH_SHORT).show();
//                addressEt.setError("enter address");
//            } else {
//                showLoaderForUserKYC();
//                String businessRegId = businessRegidEt.getText().toString();
//                String balanceSheet = balanceSheetEt.getText().toString();
//                String address = addressEt.getText().toString();
//
//                user_kyc_api_call(businessRegId, balanceSheet, address);
//            }
//        }
    }

//    private void user_kyc_api_call(String businessRegId, String balanceSheet, String address) {
//        GetdataService getdataService = AshokaRetrofit.getRetrofitInstance().create(GetdataService.class);
//        getdataService.user_kyc("", "", imageURIForAPI, businessRegId, balanceSheet, address).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                progressDialog.dismiss();
//                Intent intent = new Intent(KYCDetailsNewActivity.this, MenuActivity.class);
//                startActivity(intent);
//                finish();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                progressDialog.dismiss();
//                Intent intent = new Intent(KYCDetailsNewActivity.this, MenuActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//    }
//
//    private void showLoaderForUserKYC() {
//        progressDialog = new ProgressDialog(KYCDetailsNewActivity.this);
//        progressDialog.setTitle("Please wait");
//        progressDialog.setMessage("Saving data...");
//        progressDialog.setCancelable(false);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
//    }

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
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                contentUri = result.getUri();
//
//                final StorageReference ref = storageRef.child("CarDocs/" + new Date().toString());
//                uploadTask = ref.putFile(contentUri);
//                uploadDocumentLoader();
//                final Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                    @Override
//                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                        if (!task.isSuccessful()) {
//                            throw task.getException();
//                        }
//                        return ref.getDownloadUrl();
//                    }
//                });
//                urlTask.addOnCompleteListener(new OnCompleteListener<Uri>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Uri> task) {
//                        if (task.isSuccessful()) {
//                            progressDialog.dismiss();
//                            Uri downloadUri = task.getResult();
//                            imageURIForAPI = downloadUri.toString();
//
//                            Toast.makeText(KYCDetailsNewActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
//                            uploadedImgTv.setVisibility(View.GONE);
//                            uploadedImgTv.setVisibility(View.VISIBLE);
//
//
//                            Log.d(TAG, "Download URI - " + imageURIForAPI.toString());
//                        }
//                    }
//                });
//            }
//        }
    }

//    private void uploadDocumentLoader() {
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Please wait");
//        progressDialog.setMessage("Uploading image...");
//        progressDialog.show();
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setCancelable(false);
//    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    void onClickFun() {
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address = et_number_of_bul.getText().toString() + "," + et_street.getText().toString() + "," + et_city.getText().toString() + "," + et_pin.getText().toString() + "," + et_county.getText().toString();
                Intent intent = new Intent(KYCDetailsNewActivity.this, Screen17.class);
                startActivity(intent);

            }
        });

        cv_bus_reg_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.startPickImageActivity(KYCDetailsNewActivity.this);
            }
        });

        cv_co_mana_dir_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.startPickImageActivity(KYCDetailsNewActivity.this);
            }
        });

        cv_3_acc_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.startPickImageActivity(KYCDetailsNewActivity.this);
            }
        });

        cv_last_3_bal_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.startPickImageActivity(KYCDetailsNewActivity.this);
            }
        });
    }
}