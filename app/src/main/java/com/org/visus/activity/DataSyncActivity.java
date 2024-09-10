package com.org.visus.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.org.visus.R;
import com.org.visus.activity.datasource.VISUS_DataSource;
import com.org.visus.apis.ApiClient;
import com.org.visus.apis.ApiService;
import com.org.visus.apis.ErrorLogAPICall;
import com.org.visus.databinding.ActivityDashboardBinding;
import com.org.visus.databinding.ActivityDataSyncBinding;
import com.org.visus.models.DeviceInvLocation;
import com.org.visus.models.SaveInvestigatorAction;
import com.org.visus.models.SaveInvestigatorActionOnlyData;
import com.org.visus.models.TokenResponse;
import com.org.visus.utility.ConnectionUtility;
import com.org.visus.utility.PrefUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSyncActivity extends AppCompatActivity {
    ActivityDataSyncBinding activityDataSyncBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDataSyncBinding = ActivityDataSyncBinding.inflate(getLayoutInflater());
        setContentView(activityDataSyncBinding.getRoot());
        if (ConnectionUtility.isConnected(DataSyncActivity.this)) {
            ProgressDialog dialog = ProgressDialog.show(DataSyncActivity.this, "Synchronization", "Please wait...", true);

            getToken(dialog);
//                    Intent intent=new Intent(DashboardActivity.this,DataSyncActivity.class);
//                    startActivity(intent);

        } else {
            ConnectionUtility.AlertDialogForNoConnectionAvaialble(DataSyncActivity.this);
        }

    }

    private void getToken(ProgressDialog dialog) {
        ApiService apiService;
        apiService = ApiClient.getClient(this).create(ApiService.class);
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
                        PrefUtils.saveToPrefs(DataSyncActivity.this, PrefUtils.Token, tokenReponse.getAccessToken() != null ? tokenReponse.getAccessToken() : "");
                        VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
                        visus_dataSource.open();
                        String Token = PrefUtils.getFromPrefs(DataSyncActivity.this, PrefUtils.Token);

                        List<DeviceInvLocation> deviceInvLocationList = visus_dataSource.getDeviceInvLocation();
                        if (deviceInvLocationList != null && deviceInvLocationList.size() > 0) {
                            for (DeviceInvLocation deviceInvLocation : deviceInvLocationList) {
                                postDeviceInvLocation(deviceInvLocation, dialog);
                            }
                        } else {
                            Log.d("TAG", "onResponse: getToken");
//                            saveData(Token, dialog);
                            uploadAllData(dialog);
                        }
                        visus_dataSource.close();
                    }
                }else{
                    dialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                dialog.dismiss();

                ErrorLogAPICall apiCall=new ErrorLogAPICall(DataSyncActivity.this,DataSyncActivity.this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[2].getMethodName(),t.getMessage(),"API ERROR");
                apiCall.saveErrorLog();
                call.cancel();
                Toast.makeText(DataSyncActivity.this, "fail " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void postDeviceInvLocation(DeviceInvLocation deviceInvLocation, ProgressDialog dialog) {
        ApiService apiService = ApiClient.getClient(DataSyncActivity.this).create(ApiService.class);
        String Token = PrefUtils.getFromPrefs(DataSyncActivity.this, PrefUtils.Token);
        Call<DeviceInvLocation> postDeviceInvLocation = apiService.postDeviceInvLocation("Bearer " + Token, deviceInvLocation);
        postDeviceInvLocation.enqueue(new Callback<DeviceInvLocation>() {
            @Override
            public void onResponse(Call<DeviceInvLocation> call, Response<DeviceInvLocation> response) {
                if (response.isSuccessful()) {
                    VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
                    visus_dataSource.open();
                    visus_dataSource.delete_tblDeviceInvLocation(deviceInvLocation.getDeviceLocation_SaveOnDate());
                    int count = visus_dataSource.count_tblDeviceInvLocation();
                    if (count <= 0) {
                        //   saveData(Token, dialog);
                        uploadAllData(dialog);
                    } else {
                        dialog.dismiss();
                    }
                    visus_dataSource.close();
                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<DeviceInvLocation> call, Throwable t) {
                dialog.dismiss();
                ErrorLogAPICall apiCall=new ErrorLogAPICall(DataSyncActivity.this,DataSyncActivity.this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[2].getMethodName(),t.getMessage(),"API ERROR");
                apiCall.saveErrorLog();
                call.cancel();
                Toast.makeText(DataSyncActivity.this, "fail " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void uploadAllData(ProgressDialog dialog) {
        VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
        visus_dataSource.open();
        List<SaveInvestigatorActionOnlyData.InvestigatorActionData> getPostInvestigatorActionData = visus_dataSource.getPostInvestigatorActionData();

        if (getPostInvestigatorActionData.size()>0){
            dialog.show();

            String Token = PrefUtils.getFromPrefs(DataSyncActivity.this, PrefUtils.Token);
            ApiService apiService = ApiClient.getClient(DataSyncActivity.this).create(ApiService.class);
            Call<SaveInvestigatorActionOnlyData> call2 = apiService.SaveInvestigatorActionListDataOnly("Bearer " + Token, getPostInvestigatorActionData);
            call2.enqueue(new Callback<SaveInvestigatorActionOnlyData>() {
                @Override
                public void onResponse(Call<SaveInvestigatorActionOnlyData> call, Response<SaveInvestigatorActionOnlyData> response) {

                    Log.d("TAG", "onResponse: "+new Gson().toJson(response.body()));

                    if (response.isSuccessful() && response.code()==200 && response.body().getStatusCode()==200){
                        SaveInvestigatorActionOnlyData saveInvestigatorActionDataresponse = response.body();

                        VISUS_DataSource visus_dataSource1=new VISUS_DataSource(getApplicationContext());
                        visus_dataSource1.open();

                        for (SaveInvestigatorActionOnlyData.InvestigatorActionData saveInvestigatorActionData:saveInvestigatorActionDataresponse.getData() ) {
                            long saveResponseData =
                                    visus_dataSource1.insertPostInvestigatorImageResponseData(saveInvestigatorActionData);
                            long valMainData =
                                    visus_dataSource1.delete_tblPostInvestigatorActionData(saveInvestigatorActionData.getClientID());
                            Log.d("MAINID", " ID onResponse: "+saveInvestigatorActionData.getInvestigatorActionDataServerID());
                            long val =
                                    visus_dataSource1.update_tblPostInvestigatorActionDataPhoto(saveInvestigatorActionData.getInvestigatorActionDataServerID(), saveInvestigatorActionData.getClientID());
                        }


                        visus_dataSource1.close();

                    }else{
                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(DataSyncActivity.this, SweetAlertDialog.ERROR_TYPE);
                        sweetAlertDialog.setTitleText("Data Sync Error!!");
                        sweetAlertDialog.setCancelable(false);
                        sweetAlertDialog.setContentText(response.body().getMsg());
                        sweetAlertDialog.show();
                        sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                sweetAlertDialog.dismiss();
                            }
                        });
                    }


                }

                @Override
                public void onFailure(Call<SaveInvestigatorActionOnlyData> call, Throwable t) {
                    ErrorLogAPICall apiCall=new ErrorLogAPICall(DataSyncActivity.this,DataSyncActivity.this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[2].getMethodName(),t.getMessage(),"API ERROR");
                    apiCall.saveErrorLog();

                    call.cancel();
                }
            });
            dialog.dismiss();
        }else {


        }
        }

}