package com.org.visus.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.org.visus.activity.datasource.VISUS_DataSource;
import com.org.visus.apis.ApiClient;
import com.org.visus.apis.ApiService;
import com.org.visus.databinding.ActivityDashboardBinding;
import com.org.visus.models.DeviceInvLocation;
import com.org.visus.models.SaveInvestigatorAction;
import com.org.visus.models.SaveInvestigatorActionOnlyData;
import com.org.visus.models.TokenResponse;
import com.org.visus.holdgassessment.actvity.MyHoldAssessmentActivity;
import com.org.visus.holdgassessment.actvity.FinalSubmissionAssignmentHoldActivity;
import com.org.visus.utility.ConnectionUtility;
import com.org.visus.utility.PrefUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding dashboardBinding;
    private static final int REQUEST_CODE = 101;
    Geocoder geocoder;
    int count = 0;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private List<SaveInvestigatorAction.SaveInvestigatorActionData> arrayListSaveInvestigatorActionDataPhoto = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(dashboardBinding.getRoot());
        String INV_code = PrefUtils.getFromPrefs(DashboardActivity.this, PrefUtils.INV_code);
        String INV_name = PrefUtils.getFromPrefs(DashboardActivity.this, PrefUtils.INV_name);
        dashboardBinding.title.setText("Welcome, " + INV_name);
        dashboardBinding.code.setText("(" + INV_code + ")");

        try {
            long date = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy");
            String dateString = sdf.format(date);
            dashboardBinding.currentdate.setText(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        dashboardBinding.linearLayoutDataSynchronization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConnectionUtility.isConnected(DashboardActivity.this)) {
                    Intent intent=new Intent(DashboardActivity.this,DataSyncActivity.class);
                    startActivity(intent);

                } else {
                    ConnectionUtility.AlertDialogForNoConnectionAvaialble(DashboardActivity.this);
                }
            }
        });

        dashboardBinding.myassigmentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, MyAssigmentWork_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        dashboardBinding.finalsubmitAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, FinalSubmissionAssignment_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        dashboardBinding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(DashboardActivity.this);

        Dexter.withContext(getApplicationContext()).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    getCurrentLocation();
                } else {
                    Toast.makeText(DashboardActivity.this, "Allow Permission First", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();

            }
        }).check();
        dashboardBinding.myassigmentHoldLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, MyHoldAssessmentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        dashboardBinding.myFinalSubmissionHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, FinalSubmissionAssignmentHoldActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


    }

    private void getCurrentLocation() {
        @SuppressLint("MissingPermission") Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    method_geocoder();
                }
            }
        });
    }

    private void method_geocoder() {
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String add = addresses.get(0).getAddressLine(0);
            String feature = addresses.get(0).getFeatureName();
            Log.i("geo", "method_geocoder: " + add);
//            Toast.makeText(DashboardActivity.this, "addresss    "+add, Toast.LENGTH_SHORT).show();
            dashboardBinding.location.setText(add);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    private void postActionTypeDataNew(SaveInvestigatorActionOnlyData.InvestigatorActionData investigatorActionData, ProgressDialog dialog) {
        String Token = PrefUtils.getFromPrefs(DashboardActivity.this, PrefUtils.Token);
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
                ProgressDialog dialog = ProgressDialog.show(DashboardActivity.this, "Loading", "Please wait...", true);
                ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
                String Token = PrefUtils.getFromPrefs(DashboardActivity.this, PrefUtils.Token);
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
                                        if (count == arrayListSaveInvestigatorActionDataPhoto.size()) {
                                            Log.d("countGreat",count+"");
                                            setSweetDailog(saveInvestigatorAction.getMsg(), "Great!");
                                        } else {
                                            Log.d("count",count+"");
                                            postActionTypeData();
                                        }
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
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(DashboardActivity.this, SweetAlertDialog.SUCCESS_TYPE);
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
                        PrefUtils.saveToPrefs(DashboardActivity.this, PrefUtils.Token, tokenReponse.getAccessToken() != null ? tokenReponse.getAccessToken() : "");
                        VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
                        visus_dataSource.open();
                        String Token = PrefUtils.getFromPrefs(DashboardActivity.this, PrefUtils.Token);
                        ProgressDialog dialog = ProgressDialog.show(DashboardActivity.this, "Synchronization", "Please wait...", true);
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
                Toast.makeText(DashboardActivity.this, "fail " + t.toString(), Toast.LENGTH_LONG).show();
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
                    postActionTypeDataNew(postInvestigatorActionData, dialog);
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
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(DashboardActivity.this, SweetAlertDialog.WARNING_TYPE);
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


    private void postDeviceInvLocation(DeviceInvLocation deviceInvLocation, ProgressDialog dialog) {
        ApiService apiService = ApiClient.getClient(DashboardActivity.this).create(ApiService.class);
        String Token = PrefUtils.getFromPrefs(DashboardActivity.this, PrefUtils.Token);
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
                Toast.makeText(DashboardActivity.this, "fail " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }
}