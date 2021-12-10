package com.cloudplace.ashoka.retrofitapicall;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetdataService {
    @FormUrlEncoded
    @POST("send_otp")
    Call<ResponseBody> send_otp(@Field("mobile_number") String user_mobile_number);

    @FormUrlEncoded
    @POST("verify_otp")
    Call<ResponseBody> verify_otp(@Field("mobile_number") String userMobileNumber,
                                  @Field("otp") String otp);

    @POST("user_onboard")
    Call<ResponseBody> user_onboard(@Body JSONObject user_onboard_obj);

    @POST("user_login")
    Call<ResponseBody> user_login(@Field("mobile_number") String mobile_number,
                                  @Field("password") String password);

    @POST("user_logout")
    Call<ResponseBody> user_logout(@Body JSONObject user_logout_obj);

    @FormUrlEncoded
    @POST("user_kyc")
    Call<ResponseBody> user_kyc(@Field("mobile_number") String user_mobile_number,
                                @Field("business_user_id") String business_user_id,
                                @Field("passphoto_url") String passphoto_url,
                                @Field("business_reg_id") String business_reg_id,
                                @Field("last_balance_sheet_url") String last_balance_sheet_url,
                                @Field("address") String address);
}