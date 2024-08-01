package com.org.visus.apis;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.org.visus.activity.DashboardActivity;
import com.org.visus.activity.datasource.VISUS_DataSource;
import com.org.visus.models.ExceptionInfoRequest;
import com.org.visus.models.PostInvestigatorActionData;
import com.org.visus.models.TokenResponse;
import com.org.visus.utility.PrefUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ErrorLogAPICall {

    Context context;
    String AppException_ActivityClassName;
    String AppException_MethodName;
    String AppException_ExceptionText;
    String setDeveloperComment;


    public ErrorLogAPICall(Context context, String AppException_ActivityClassName, String AppException_MethodName, String AppException_ExceptionText, String setDeveloperComment) {
        this.context = context;
        this.AppException_ActivityClassName = AppException_ActivityClassName;
        this.AppException_MethodName = AppException_MethodName;
        this.AppException_ExceptionText = AppException_ExceptionText;
        this.setDeveloperComment = setDeveloperComment;

    }

   public void saveErrorLog() {

        ApiService apiService;
        apiService = ApiClient.getClient(context).create(ApiService.class);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        //String JSONObject = gson.toJson(getHardwareAndSoftwareInfoList);
        //Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        //String prettyJsonForLogin = prettyGson.toJson(JSONObject);
        Call<TokenResponse> call2 = apiService.getToken("admin", "Visus#2022@Api2021", "password");
        call2.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                if (response.body() != null) {
                    final TokenResponse tokenReponse = response.body();
                    if (tokenReponse != null) {
                        PrefUtils.saveToPrefs(context, PrefUtils.Token, tokenReponse.getAccessToken() != null ? tokenReponse.getAccessToken() : "");
                            sendErrorDetailsToServer(tokenReponse.getAccessToken());
                    }
                }

            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(context, "fail " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendErrorDetailsToServer(String accessToken) {
//        ApiService apiService;
//        apiService = ApiClient.getClientUAT(context).create(ApiService.class);
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        Gson gson = gsonBuilder.create();
//
//
//        Date now = new Date();
//
//        // Define the date-time formatter
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//
//        // Format the current date and time
//        String formattedDateTime = formatter.format(now);
//
//
//
//        ExceptionInfoRequest request = new ExceptionInfoRequest();
//        request.setAppException_ID(-1);
//        request.setAppException_OnServerDate("");
//        request.setAppException_OnAppDate(formattedDateTime);
//        request.setAppException_DeviceID(1);
//        request.setAppException_InvID(2);
//        request.setAppException_ActivityClassName(AppException_ActivityClassName);
//        request.setAppException_MethodName(AppException_MethodName);
//        request.setAppException_ExceptionText(AppException_ExceptionText);
//        request.setDeveloperComment("");
//        request.setIfAnyApiException(null);
//        request.setExceptionDataSaved(false);
//
//        Call<JsonObject> call = apiService.sendExceptionInfo("Bearer " + accessToken, request);
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (response.isSuccessful()) {
//                    Log.d("API_CALL", "Success: " + new Gson().toJson(response.message()));
//                } else {
//                    Log.d("API_CALL", "Error: " + response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Log.d("API_CALL", "Failure: " + t.getMessage());
//            }
//        });

    }
}
