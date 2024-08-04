package com.org.visus.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.org.visus.R;
import com.org.visus.activity.datasource.VISUS_DataSource;
import com.org.visus.apis.ApiClient;
import com.org.visus.apis.ApiService;
import com.org.visus.databinding.ActivityDashboardBinding;
import com.org.visus.databinding.ActivityDataSyncBinding;
import com.org.visus.models.DeviceInvLocation;
import com.org.visus.models.SaveInvestigatorAction;
import com.org.visus.models.SaveInvestigatorActionOnlyData;
import com.org.visus.models.TokenResponse;
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
    private List<SaveInvestigatorAction.SaveInvestigatorActionData> arrayListSaveInvestigatorActionDataPhoto = new ArrayList<>();
    int count = 0;
    int  uploadedDataDetailsCountForPrint=0;
    int  uploadedImageDetailsCountForPrint=0;

    int  uploadedDataDetailsCount=1;
    int  uploadedImageDetailsCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDataSyncBinding = ActivityDataSyncBinding.inflate(getLayoutInflater());
        setContentView(activityDataSyncBinding.getRoot());
     //  setContentView(R.layout.activity_data_sync);
        VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
        visus_dataSource.open();

        activityDataSyncBinding.pendingCountValue.setText(visus_dataSource.getPostInvestigatorActionData().size() + "");
        activityDataSyncBinding.pendingImageCountValue.setText(visus_dataSource.getPostInvestigatorActionDataPhoto().size()+"");
        activityDataSyncBinding.uploadedCountValue.setText(uploadedDataDetailsCountForPrint+"");
        activityDataSyncBinding.uploadedImageCountValue.setText(uploadedImageDetailsCountForPrint+"");
        activityDataSyncBinding.syncDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getToken();
            }
        });
      //  getToken();

    }
    public void goBack(View view) {
        finish();
    }
    private void getToken() {
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
                        ProgressDialog dialog = ProgressDialog.show(DataSyncActivity.this, "Synchronization", "Please wait...", true);
                        List<DeviceInvLocation> deviceInvLocationList = visus_dataSource.getDeviceInvLocation();
                        if (deviceInvLocationList != null && deviceInvLocationList.size() > 0) {
                            for (DeviceInvLocation deviceInvLocation : deviceInvLocationList) {

                                    postDeviceInvLocation(deviceInvLocation, dialog);


                            }
                        } else {
                            saveData(Token, dialog);
                        }
                        visus_dataSource.close();
                    }
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
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
                        saveData(Token, dialog);
                    } else {
                    }
                    visus_dataSource.close();
                } else {
                }
            }

            @Override
            public void onFailure(Call<DeviceInvLocation> call, Throwable t) {
                call.cancel();
                Toast.makeText(DataSyncActivity.this, "fail " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void saveData(String Token, ProgressDialog dialog) {
        VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
        visus_dataSource.open();
        int updatetblPostInvestigatorActionDataToken = visus_dataSource.updatetblPostInvestigatorActionDataToken(Token);
        int updatetblPostInvestigatorActionDataPhotoToken = visus_dataSource.updatetblPostInvestigatorActionDataPhotoToken(Token);
        if (updatetblPostInvestigatorActionDataToken > 0) {
            List<SaveInvestigatorActionOnlyData.InvestigatorActionData> getPostInvestigatorActionData = visus_dataSource.getPostInvestigatorActionData();
            visus_dataSource.close();
            if (getPostInvestigatorActionData != null && getPostInvestigatorActionData.size() > 0) {
                for (SaveInvestigatorActionOnlyData.InvestigatorActionData postInvestigatorActionData : getPostInvestigatorActionData) {
                    if (uploadedDataDetailsCount<=3){
                        postActionTypeDataNew(postInvestigatorActionData, dialog);
                        uploadedDataDetailsCount++;
                        visus_dataSource.open();
                        activityDataSyncBinding.pendingCountValue.setText((visus_dataSource.getPostInvestigatorActionData()==null?0:visus_dataSource.getPostInvestigatorActionData().size()) + "");
                        activityDataSyncBinding.pendingImageCountValue.setText((visus_dataSource.getPostInvestigatorActionDataPhoto()==null?0:visus_dataSource.getPostInvestigatorActionDataPhoto().size())+"");
                        activityDataSyncBinding.uploadedCountValue.setText((uploadedDataDetailsCountForPrint+uploadedDataDetailsCount)+"");
                        activityDataSyncBinding.uploadedImageCountValue.setText((uploadedImageDetailsCountForPrint+uploadedImageDetailsCount)+"");
                        visus_dataSource.open();
                    }else {
                        visus_dataSource.open();
                        activityDataSyncBinding.pendingCountValue.setText((visus_dataSource.getPostInvestigatorActionData()==null?0:visus_dataSource.getPostInvestigatorActionData().size() ) + "");
                        activityDataSyncBinding.pendingImageCountValue.setText((visus_dataSource.getPostInvestigatorActionDataPhoto()==null?0:visus_dataSource.getPostInvestigatorActionDataPhoto().size())+"");
                        activityDataSyncBinding.uploadedCountValue.setText((uploadedDataDetailsCountForPrint+uploadedDataDetailsCount)+"");
                        activityDataSyncBinding.uploadedImageCountValue.setText((uploadedImageDetailsCountForPrint+uploadedImageDetailsCount)+"");
                        visus_dataSource.open();
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        uploadedDataDetailsCount=0;
                    }

                }
            } else {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

        } else {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            if (updatetblPostInvestigatorActionDataPhotoToken > 0) {
                VISUS_DataSource visus_dataSourceNew = new VISUS_DataSource(getApplicationContext());
                visus_dataSourceNew.open();
                arrayListSaveInvestigatorActionDataPhoto.clear();
                arrayListSaveInvestigatorActionDataPhoto = visus_dataSource.getPostInvestigatorActionDataPhoto();
                visus_dataSourceNew.close();
                if (arrayListSaveInvestigatorActionDataPhoto != null && arrayListSaveInvestigatorActionDataPhoto.size() > 0) {
                    postActionTypeData();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            } else {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
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
        }
    }

    private void postActionTypeDataNew(SaveInvestigatorActionOnlyData.InvestigatorActionData investigatorActionData, ProgressDialog dialog) {
        String Token = PrefUtils.getFromPrefs(DataSyncActivity.this, PrefUtils.Token);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        Call<SaveInvestigatorActionOnlyData> call2 = apiService.postInvestigatorActionDataNew("Bearer " + Token, investigatorActionData);
        call2.enqueue(new Callback<SaveInvestigatorActionOnlyData>() {
            @Override
            public void onResponse(Call<SaveInvestigatorActionOnlyData> call, Response<SaveInvestigatorActionOnlyData> response) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (response.body() != null) {
                    final SaveInvestigatorActionOnlyData saveInvestigatorActionData = response.body();
                    if (investigatorActionData != null) {
                        Log.d("error1", saveInvestigatorActionData.getStatusCode() + "");
                        if (saveInvestigatorActionData.getStatusCode() == 200) {
                            VISUS_DataSource visus_dataSource1 = new VISUS_DataSource(getApplicationContext());
                            visus_dataSource1.open();
                            long valMainData =
                                    visus_dataSource1.delete_tblPostInvestigatorActionData(saveInvestigatorActionData.getData().get(0).getClientID());
                            long val =
                                    visus_dataSource1.update_tblPostInvestigatorActionDataPhoto(saveInvestigatorActionData.getData().get(0)
                                            .getInvestigatorActionDataServerID(), saveInvestigatorActionData.getData().get(0).getClientID());
                            if (val > 0) {
                                arrayListSaveInvestigatorActionDataPhoto.clear();
                                arrayListSaveInvestigatorActionDataPhoto = visus_dataSource1.getPostInvestigatorActionDataPhoto();
                                visus_dataSource1.close();
                                if (arrayListSaveInvestigatorActionDataPhoto != null && arrayListSaveInvestigatorActionDataPhoto.size() > 0) {
                                    postActionTypeData();
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SaveInvestigatorActionOnlyData> call, Throwable t) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                call.cancel();
            }
        });
    }

    private void postActionTypeData() {
        if (arrayListSaveInvestigatorActionDataPhoto!= null && arrayListSaveInvestigatorActionDataPhoto.size()>0) {
            if(arrayListSaveInvestigatorActionDataPhoto.get(count)!=null){
                SaveInvestigatorAction.SaveInvestigatorActionData saveData = arrayListSaveInvestigatorActionDataPhoto.get(count);
                ProgressDialog dialog = ProgressDialog.show(DataSyncActivity.this, "Loading", "Please wait...", true);
                ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
                String Token = PrefUtils.getFromPrefs(DataSyncActivity.this, PrefUtils.Token);
                MultipartBody.Part body_action_image1 =null;
                if(saveData.getOriginalFileName()!=null){
                    if(saveData.getOriginalFileName().contains("Pdf_")){
                        String uri= saveData.getOriginalFileName().split("Pdf_")[1];
                        File file_action_image = new File(uri);
                        RequestBody request_file_action_photo = RequestBody.create(MediaType.parse("multipart/form-data"), uri.toString());
                        body_action_image1 = MultipartBody.Part.createFormData("OriginalFIleName", !file_action_image.getName().equalsIgnoreCase("") ? file_action_image.getName() : "N?A", request_file_action_photo);
                    }else {
                        File file_action_image = new File(saveData.getOriginalFileName());
                        RequestBody request_file_action_photo = RequestBody.create(MediaType.parse("multipart/form-data"), file_action_image);
                        body_action_image1 = MultipartBody.Part.createFormData("OriginalFIleName", !file_action_image.getName().equalsIgnoreCase("") ? file_action_image.getName() : "N?A", request_file_action_photo);
                    }
                }
                if(body_action_image1!=null){
                    Call<SaveInvestigatorAction> call2 = apiService.postInvestigatorActionDataPhoto("Bearer " + Token, body_action_image1, saveData.getVisusServicesID(), saveData.getInvestigatorCaseActivityCaseInsuranceID(), saveData.getInvestigatorCaseActivityInvID(), saveData.getInvestigatorRequiredActivityID(), saveData.getInvestigatorCaseActivity_ClientD(), saveData.getInvInsuranceRelID(), saveData.getInvestigatorCaseActivityPhotoServerID());
                    call2.enqueue(new Callback<SaveInvestigatorAction>() {
                        @Override
                        public void onResponse(Call<SaveInvestigatorAction> call, Response<SaveInvestigatorAction> response) {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            if (response.body() != null) {
                                final SaveInvestigatorAction saveInvestigatorAction = response.body();
                                if (saveInvestigatorAction != null) {
                                    Log.d("error1", saveInvestigatorAction.getStatusCode() + "");
                                    if (saveInvestigatorAction.getStatusCode() == 200) {
                                        VISUS_DataSource visus_dataSource1 = new VISUS_DataSource(getApplicationContext());
                                        visus_dataSource1.open();
                                        int abc = visus_dataSource1.updatePostInvestigatorActionDataPhoto(saveInvestigatorAction.getData().get(0).getInvestigatorCaseActivityID().toString());
                                        visus_dataSource1.close();
                                        count++;
                                        if (count == arrayListSaveInvestigatorActionDataPhoto.size() || count==2) {

                                            Log.d("countGreat",count+"");
                                            setSweetDailog(saveInvestigatorAction.getMsg(), "Great!");
                                        } else {
                                            Log.d("count",count+"");
                                            postActionTypeData();
                                        }
                                        VISUS_DataSource visus_dataSource2 = new VISUS_DataSource(getApplicationContext());
                                        activityDataSyncBinding.pendingCountValue.setText((visus_dataSource2.getPostInvestigatorActionData()==null?0:visus_dataSource2.getPostInvestigatorActionData().size() )+ "");
                                        activityDataSyncBinding.pendingImageCountValue.setText((visus_dataSource2.getPostInvestigatorActionDataPhoto()==null?0:visus_dataSource2.getPostInvestigatorActionDataPhoto().size())+"");
                                        activityDataSyncBinding.uploadedCountValue.setText((uploadedDataDetailsCountForPrint+uploadedDataDetailsCount)+"");
                                        activityDataSyncBinding.uploadedImageCountValue.setText((uploadedImageDetailsCountForPrint+(count+1))+"");
                                        visus_dataSource2.close();
                                    } else {
                                        setSweetDailog(saveInvestigatorAction.getMsg(), "Sorry!!!");
                                    }
                                } else {
                                    setSweetDailog(saveInvestigatorAction.getMsg(), "Sorry!!!");
                                }
                            } else {
                                setSweetDailog(response.message(), "Sorry!!!");
                            }
                        }

                        @Override
                        public void onFailure(Call<SaveInvestigatorAction> call, Throwable t) {
                            setSweetDailog(t.getMessage(), "Sorry!!!");
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            call.cancel();
                        }
                    });
                }

            }

        }

    }


    private void setSweetDailog(String Content, String titleText) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(DataSyncActivity.this, SweetAlertDialog.SUCCESS_TYPE);
        sweetAlertDialog.setTitleText(titleText);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setContentText(Content);
        sweetAlertDialog.show();
        sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sweetAlertDialog.dismiss();
            }
        });
    }

}