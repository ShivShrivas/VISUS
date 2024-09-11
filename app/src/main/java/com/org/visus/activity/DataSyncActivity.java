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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.org.visus.R;
import com.org.visus.activity.datasource.VISUS_DataSource;
import com.org.visus.activity.interfaces.ImageItemsUploadListner;
import com.org.visus.adapters.DataImagesSync_Adapter;
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

public class DataSyncActivity extends AppCompatActivity implements ImageItemsUploadListner {
    ActivityDataSyncBinding activityDataSyncBinding;
    DataImagesSync_Adapter dataImagesSyncAdapter;
    ImageItemsUploadListner imageItemsUploadListner;

    SaveInvestigatorActionOnlyData.InvestigatorActionData investigatorActionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDataSyncBinding = ActivityDataSyncBinding.inflate(getLayoutInflater());
        setContentView(activityDataSyncBinding.getRoot());
        imageItemsUploadListner=DataSyncActivity.this;
        activityDataSyncBinding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

                        List<SaveInvestigatorActionOnlyData.InvestigatorActionData> savedResponseList=visus_dataSource1.getPostInvestigatorSavedResponseData();

                        if (savedResponseList!=null  && savedResponseList.size() > 0) {

                            activityDataSyncBinding.recyclerViewData.setLayoutManager(new LinearLayoutManager(DataSyncActivity.this));
                            dataImagesSyncAdapter=new DataImagesSync_Adapter(DataSyncActivity.this,savedResponseList,imageItemsUploadListner);
                            activityDataSyncBinding.recyclerViewData.setAdapter(dataImagesSyncAdapter);
                            dataImagesSyncAdapter.notifyDataSetChanged();
                            dialog.dismiss();
//                     dialogImageUpload.dismiss();
                        }else{
                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(DataSyncActivity.this, SweetAlertDialog.WARNING_TYPE);
                            sweetAlertDialog.setTitleText("Synchronization");
                            sweetAlertDialog.setCancelable(false);
                            sweetAlertDialog.setContentText("No Need to Sync");
                            sweetAlertDialog.show();
                            sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    sweetAlertDialog.dismiss();
                                }
                            });
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
            List<SaveInvestigatorActionOnlyData.InvestigatorActionData> savedResponseList=visus_dataSource.getPostInvestigatorSavedResponseData();

            if (savedResponseList!=null  && savedResponseList.size() > 0) {

                activityDataSyncBinding.recyclerViewData.setLayoutManager(new LinearLayoutManager(this));
                dataImagesSyncAdapter=new DataImagesSync_Adapter(DataSyncActivity.this,savedResponseList,imageItemsUploadListner);
                activityDataSyncBinding.recyclerViewData.setAdapter(dataImagesSyncAdapter);
                dataImagesSyncAdapter.notifyDataSetChanged();
                dialog.dismiss();
//                     dialogImageUpload.dismiss();
            }else{
                dialog.dismiss();
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(DataSyncActivity.this, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.setTitleText("Synchronization");
                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.setContentText("No Need to Sync");
                sweetAlertDialog.show();
                sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sweetAlertDialog.dismiss();
                        finish();
                    }
                });
            }



        }
        }

    @Override
    public void uploadButtonOnClick(SaveInvestigatorActionOnlyData.InvestigatorActionData investigatorActionData) {
        uploadAllImagesData(investigatorActionData);
        this.investigatorActionData=investigatorActionData;
    }

    private void uploadAllImagesData(SaveInvestigatorActionOnlyData.InvestigatorActionData investigatorActionData) {
        ProgressDialog dialog = ProgressDialog.show(DataSyncActivity.this, "Synchronization", "Please wait...", true);

        VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
        visus_dataSource.open();
        List<SaveInvestigatorAction.SaveInvestigatorActionData> arrayListSaveInvestigatorActionDataPhoto = new ArrayList<>();
        arrayListSaveInvestigatorActionDataPhoto = visus_dataSource.getPostInvestigatorActionDataPhotosByClintId(investigatorActionData.getClientID());

        if (arrayListSaveInvestigatorActionDataPhoto.size() > 0) {
            ApiService apiService = ApiClient.getClient(DataSyncActivity.this).create(ApiService.class);
            String token = PrefUtils.getFromPrefs(DataSyncActivity.this, PrefUtils.Token);
            List<MultipartBody.Part> imageParts = new ArrayList<>();
            int count = 0;
            String VisusServicesID = "";
            String InvestigatorCaseActivityCaseInsuranceID = "";
            String InvestigatorCaseActivityInvID = "";
            String InvestigatorRequiredActivityID = "";
            String InvestigatorCaseActivity_ClientD = "";
            String InvInsuranceRelID = "";
            String InvestigatorCaseActivityPhotoServerID = "";
            for (SaveInvestigatorAction.SaveInvestigatorActionData imagedata : arrayListSaveInvestigatorActionDataPhoto) {

                    count++;
                    VisusServicesID = imagedata.getVisusServicesID();
                    InvestigatorCaseActivityCaseInsuranceID = imagedata.getInvestigatorCaseActivityCaseInsuranceID();
                    InvestigatorCaseActivityInvID = imagedata.getInvestigatorCaseActivityInvID();
                    InvestigatorRequiredActivityID = imagedata.getInvestigatorRequiredActivityID();
                    InvestigatorCaseActivity_ClientD = imagedata.getInvestigatorCaseActivity_ClientD();
                    InvInsuranceRelID = imagedata.getInvInsuranceRelID();
                    InvestigatorCaseActivityPhotoServerID =  investigatorActionData.getInvestigatorActionDataServerID();
                    MultipartBody.Part body_action_image1 = null;
                    if (imagedata.getOriginalFileName() != null) {
                        if (imagedata.getOriginalFileName().contains("Pdf_")) {
                            String uri = imagedata.getOriginalFileName().split("Pdf_")[1];
                            File file_action_image = new File(uri);
                            long fileSizeInBytes = file_action_image.length(); // Size in bytes


                            Log.d("TAG", "saveData: file size "+(fileSizeInBytes / 1024));
                            RequestBody request_file_action_photo = RequestBody.create(MediaType.parse("multipart/form-data"), file_action_image);
                            body_action_image1 = MultipartBody.Part.createFormData(count + "", !file_action_image.getName().equalsIgnoreCase("") ? file_action_image.getName() : "N?A", request_file_action_photo);
                            imageParts.add(body_action_image1);
                        } else {
                            File file_action_image = new File(imagedata.getOriginalFileName());
                            long fileSizeInBytes = file_action_image.length(); // Size in bytes


                            Log.d("TAG", "saveData: file size "+(fileSizeInBytes / 1024));
                            RequestBody request_file_action_photo = RequestBody.create(MediaType.parse("multipart/form-data"), file_action_image);
                            body_action_image1 = MultipartBody.Part.createFormData(count + "", !file_action_image.getName().equalsIgnoreCase("") ? file_action_image.getName() : "N?A", request_file_action_photo);
                            imageParts.add(body_action_image1);

                        }
                    }




            }
            Log.d("TAG", "onClick: count="+count);
            if (count > 0) {

                Call<SaveInvestigatorAction> call2 = apiService.saveInvestigatorActionlstPhotoData("Bearer " + token, imageParts, VisusServicesID, InvestigatorCaseActivityCaseInsuranceID, InvestigatorCaseActivityInvID, InvestigatorRequiredActivityID, InvestigatorCaseActivity_ClientD, InvInsuranceRelID, InvestigatorCaseActivityPhotoServerID);

                call2.enqueue(new Callback<SaveInvestigatorAction>() {
                    @Override
                    public void onResponse(Call<SaveInvestigatorAction> call, Response<SaveInvestigatorAction> response) {
                        Log.d("TAG", "onResponse: "+new Gson().toJson(response.body()));
                        dialog.dismiss();
                        if (response.isSuccessful() && response.code()==200 && response.body().getStatusCode()==200) {
                            SaveInvestigatorAction saveInvestigatorAction = response.body();
                            VISUS_DataSource visus_dataSource1 = new VISUS_DataSource(getApplicationContext());
                            visus_dataSource1.open();
                            Log.d("MAINID", " ID onResponse 1: "+saveInvestigatorAction.getData().get(0).getInvInsuranceRelID()+"////"+saveInvestigatorAction.getData().get(0).getInvestigatorCaseActivityCaseInsuranceID());

//                                                int abc = visus_dataSource1.updatePostInvestigatorActionDataPhoto(saveInvestigatorAction.getData().get(0).getInvestigatorCaseActivityPhotoServerID());
                            int abc = visus_dataSource1.updatePostInvestigatorActionDataPhotoNEW(investigatorActionData.getClientID());

                            long valMainData =
                                    visus_dataSource1.delete_tblPostInvestigatorResponsedata(investigatorActionData.getClientID());
                            Log.d("TAG", "MAINID...onResponse: "+abc);


                            //  progressDialog.setProgress((arrayListSaveInvestigatorActionDataPhoto.size()-visus_dataSource1.getPostInvestigatorActionDataPhoto().size()));
//                                             SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(DashboardActivity.this, SweetAlertDialog.SUCCESS_TYPE);
//                                             sweetAlertDialog.setTitleText("Great!!");
//                                             sweetAlertDialog.setCancelable(false);
//                                             sweetAlertDialog.setContentText("All data have been sync successfully!!");
//                                             sweetAlertDialog.show();
//                                             sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
//                                                 @Override
//                                                 public void onClick(View v) {
//                                                     txtUploadData.setText("Upload Data ("+visus_dataSource1.getPostInvestigatorActionData().size()+")");
//                                                     txtUploadImage.setText("Upload Image ("+visus_dataSource1.getPostInvestigatorSavedResponseData().size()+")");
//                                                     visus_dataSource1.close();
//                                                     sweetAlertDialog.dismiss();
//
//                                                 }
//                                             });


                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(DataSyncActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                sweetAlertDialog.setTitleText("Great!!");
                                sweetAlertDialog.setCancelable(false);
                                sweetAlertDialog.setContentText("data have been sync successfully!!");
                                sweetAlertDialog.show();
                                sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        List<SaveInvestigatorActionOnlyData.InvestigatorActionData> savedResponseList=visus_dataSource1.getPostInvestigatorSavedResponseData();

                                        if (savedResponseList!=null  && savedResponseList.size() > 0) {

                                            activityDataSyncBinding.recyclerViewData.setLayoutManager(new LinearLayoutManager(DataSyncActivity.this));
                                            dataImagesSyncAdapter=new DataImagesSync_Adapter(DataSyncActivity.this,savedResponseList,imageItemsUploadListner);
                                            activityDataSyncBinding.recyclerViewData.setAdapter(dataImagesSyncAdapter);
                                            dataImagesSyncAdapter.notifyDataSetChanged();

//                     dialogImageUpload.dismiss();
                                        }else{
                                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(DataSyncActivity.this, SweetAlertDialog.WARNING_TYPE);
                                            sweetAlertDialog.setTitleText("Synchronization");
                                            sweetAlertDialog.setCancelable(false);
                                            sweetAlertDialog.setContentText("No Need to Sync");
                                            sweetAlertDialog.show();
                                            sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    sweetAlertDialog.dismiss();
                                                    finish();
                                                }
                                            });
                                        }
                                        visus_dataSource1.close();
                                        sweetAlertDialog.dismiss();


                                    }
                                });

                        }else{

                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(DataSyncActivity.this, SweetAlertDialog.ERROR_TYPE);
                            sweetAlertDialog.setTitleText("Image Data Sync Error!!");
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
                    public void onFailure(Call<SaveInvestigatorAction> call, Throwable t) {
                        dialog.dismiss();
                        ErrorLogAPICall apiCall=new ErrorLogAPICall(DataSyncActivity.this,DataSyncActivity.this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[2].getMethodName(),t.getMessage(),"API ERROR");
                        apiCall.saveErrorLog();
                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(DataSyncActivity.this, SweetAlertDialog.ERROR_TYPE);
                        sweetAlertDialog.setTitleText("Image Data Sync Error!!");
                        sweetAlertDialog.setCancelable(false);
                        sweetAlertDialog.setContentText(t.getMessage());
                        sweetAlertDialog.show();
                        sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sweetAlertDialog.dismiss();
                            }
                        });

                    }
                });
            }


        }
    }
}