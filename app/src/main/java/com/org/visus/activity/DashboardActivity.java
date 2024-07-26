package com.org.visus.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.CursorWindow;
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
import com.org.visus.models.PostInvestigatorActionData;
import com.org.visus.models.SaveInvestigatorAction;
import com.org.visus.models.TokenResponse;
import com.org.visus.utility.ConnectionUtility;
import com.org.visus.utility.PrefUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
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
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    String Token;

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
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); // Set the size to 100MB
        } catch (Exception e) {
            Log.e("TAG", "Failed to set CursorWindow size", e);
        }
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
                    getToken();
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

    private void postActionTypeData(PostInvestigatorActionData postInvestigatorActionData, ProgressDialog dialog) {
        File file_action_image = postInvestigatorActionData.getOriginalFile();
        Log.d("TAG", "postActionTypeData: "+file_action_image.getAbsolutePath());
        RequestBody request_file_action_photo = RequestBody.create(MediaType.parse("multipart/form-data"), file_action_image);
        MultipartBody.Part body_action_image = MultipartBody.Part.createFormData("OriginalFIleName", file_action_image.getName(), request_file_action_photo);
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        Call<SaveInvestigatorAction> call2 = apiService.postInvestigatorActionData("Bearer "+Token , body_action_image, postInvestigatorActionData.getServiceTypeID(), postInvestigatorActionData.getServiceID(), postInvestigatorActionData.getInvID(), postInvestigatorActionData.getComments(), postInvestigatorActionData.getActionID(), postInvestigatorActionData.getLatitude(), postInvestigatorActionData.getLongitude(), postInvestigatorActionData.getCellAddress(), postInvestigatorActionData.getClientID(), postInvestigatorActionData.getInvInsuranceRelID_SAVING());
        call2.enqueue(new Callback<SaveInvestigatorAction>() {
            @Override
            public void onResponse(Call<SaveInvestigatorAction> call, Response<SaveInvestigatorAction> response) {
                if (response.body() != null) {
                    final SaveInvestigatorAction saveInvestigatorAction = response.body();
                    if (saveInvestigatorAction != null) {
                        if (saveInvestigatorAction.getStatusCode() == 200) {
                            /////Delete Local Table Row and Photo Both According To ID
                            VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
                            visus_dataSource.open();
                            if (saveInvestigatorAction.getData().get(0).getInvestigatorCaseActivityID() > 0) {
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                                visus_dataSource.delete_tblPostInvestigatorActionData(saveInvestigatorAction.getData().get(0).getInvestigatorCaseActivity_ClientD());
                            }
                            visus_dataSource.close();
                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(DashboardActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                            sweetAlertDialog.setTitleText("Great!");
                            sweetAlertDialog.setCancelable(false);
                            sweetAlertDialog.setContentText("Syncing Successfully");
                            sweetAlertDialog.show();
                            sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    sweetAlertDialog.dismiss();
//                                    finish();
                                }
                            });
                          //  Toast.makeText(DashboardActivity.this, "Syncing Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DashboardActivity.this, "Fail!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<SaveInvestigatorAction> call, Throwable t) {
                dialog.dismiss();
                call.cancel();
                Toast.makeText(DashboardActivity.this, "fail " + t.toString(), Toast.LENGTH_LONG).show();
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
                        Token = PrefUtils.getFromPrefs(DashboardActivity.this, PrefUtils.Token);
                        int updatetblPostInvestigatorActionDataToken = visus_dataSource.updatetblPostInvestigatorActionDataToken(Token);
                        if (updatetblPostInvestigatorActionDataToken > 0) {
                            ProgressDialog dialog = ProgressDialog.show(DashboardActivity.this, "Synchronization", "Please wait...", true);
                            List<PostInvestigatorActionData> getPostInvestigatorActionData = visus_dataSource.getPostInvestigatorActionData();
                            for (PostInvestigatorActionData postInvestigatorActionData : getPostInvestigatorActionData) {
                                postActionTypeData(postInvestigatorActionData, dialog);
                            }
                        } else {
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
}