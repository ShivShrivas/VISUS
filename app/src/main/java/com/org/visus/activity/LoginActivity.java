package com.org.visus.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.org.visus.R;
import com.org.visus.activity.datasource.VISUS_DataSource;
import com.org.visus.apis.ApiClient;
import com.org.visus.apis.ApiService;
import com.org.visus.apis.ErrorLogAPICall;
import com.org.visus.models.DeviceInfo;
import com.org.visus.models.DeviceInvLocation;
import com.org.visus.models.DeviceOfInvStatus;
import com.org.visus.models.DeviceRegistrationResponse;
import com.org.visus.models.DeviceStatusResponse;
import com.org.visus.models.GetServices;
import com.org.visus.models.MyAssignment;
import com.org.visus.models.RequreActions;
import com.org.visus.models.TotalCases;
import com.org.visus.utility.ConnectionUtility;
import com.org.visus.utility.FileUtils;
import com.org.visus.utility.PrefUtils;
import com.skydoves.elasticviews.ElasticButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private final int LOCTION_CODE = 10101;

    TextInputEditText investigatorCode, PINCode;
    ElasticButton login_visus;
    String Token, InvestigatorID, DEVICEServerID;
    TotalCases totalCases;
    ProgressDialog progressDialog;
    Geocoder geocoder;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(LoginActivity.this);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S) { // Check if the Android version is 12 or lower
            Dexter.withContext(getApplicationContext())
                    .withPermissions(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                            if (multiplePermissionsReport.areAllPermissionsGranted()) {
                                getCurrentLocation();

                            } else if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                                PrefUtils.showSettingsDialog(LoginActivity.this);
                            } else {
                                Toast.makeText(LoginActivity.this, "Allow Permission First", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    })
                    .check();
        } else {
            // For Android versions 13 and higher, handle permissions differently if needed
            Dexter.withContext(getApplicationContext())
                    .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION) // Storage permissions are not needed for Android 13+
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                            if (multiplePermissionsReport.areAllPermissionsGranted()) {
                                getCurrentLocation();
                            } else if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                                PrefUtils.showSettingsDialog(LoginActivity.this);
                            } else {
                                Toast.makeText(LoginActivity.this, "Allow Permission First", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    })
                    .check();
        }
        if (ConnectionUtility.isConnected(LoginActivity.this)) {
            String getToken = PrefUtils.getToken(LoginActivity.this);
            getRequreActionsData();
            getServices();
            getTotalCases();
            getMyAssignmentAll();
            getHoldMyAssignmentAll();
        }
        progressDialog = new ProgressDialog(LoginActivity.this);
        findViewById();
        String INV_codeForUi = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.INV_code);
        if (!INV_codeForUi.equalsIgnoreCase("")) {
            investigatorCode.setText(INV_codeForUi);
            investigatorCode.setEnabled(false);
        }
        callListener();
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

    private String method_geocoder() {
        String add = "";
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            add = addresses.get(0).getAddressLine(0);
            String feature = addresses.get(0).getFeatureName();
            Log.i("geo", "method_geocoder: " + add);
            // Toast.makeText(LoginActivity.this, "addresss    " + add, Toast.LENGTH_SHORT).show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return add;
    }

    private void findViewById() {
        investigatorCode = findViewById(R.id.investigatorCode);
        PINCode = findViewById(R.id.PINCode);
        login_visus = findViewById(R.id.login_visus);
    }

    private void getServices() {
        ApiService apiService;
        apiService = ApiClient.getClient(this).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.Token);
        Call<GetServices> call2 = apiService.getServices("Bearer " + Token);
        call2.enqueue(new Callback<GetServices>() {
            @Override
            public void onResponse(Call<GetServices> call, Response<GetServices> response) {
                if (response.body() != null) {
//                    Log.i("response", "onResponse: " + response.body());
                    final GetServices getServices = response.body();
                    if (getServices != null) {
                        if (getServices.getStatus() != null && getServices.getStatus().equalsIgnoreCase("success")) {
                            VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
                            visus_dataSource.open();
                            visus_dataSource.delete_tblMyServices();
                            for (GetServices.GetServicesData servicesData : getServices.getData()) {
                                visus_dataSource.insertMyServices(servicesData.getVisusServicesID().toString(), servicesData.getVisusServicesText(), servicesData.getVisusServicesDesc(), servicesData.getVisusActiveServices().toString());
                            }
                            visus_dataSource.close();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetServices> call, Throwable t) {
                ErrorLogAPICall apiCall=new ErrorLogAPICall(LoginActivity.this,LoginActivity.this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[2].getMethodName(),t.getMessage(),"API ERROR");
                apiCall.saveErrorLog();
                call.cancel();
                Toast.makeText(LoginActivity.this, "fail " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getRequreActionsData() {
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.Token);
        Call<RequreActions> call2 = apiService.getMyReqActionList("Bearer " + Token);
        call2.enqueue(new Callback<RequreActions>() {
            @Override
            public void onResponse(Call<RequreActions> call, Response<RequreActions> response) {
                if (response.body() != null) {
                    final RequreActions requreActions = response.body();
                    if (requreActions != null) {
                        if (requreActions.getStatus() != null && requreActions.getStatus().equalsIgnoreCase("success")) {
                            VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
                            visus_dataSource.open();
                            visus_dataSource.delete_tblRequreActionsData();
                            for (RequreActions.RequreActionsData requreActionsData : requreActions.getData()) {
                                visus_dataSource.insertRequreActionsData(requreActionsData);
                            }
                            visus_dataSource.close();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RequreActions> call, Throwable t) {
                ErrorLogAPICall apiCall=new ErrorLogAPICall(LoginActivity.this,LoginActivity.this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[2].getMethodName(),t.getMessage(),"API ERROR");
                apiCall.saveErrorLog();
                call.cancel();
                Toast.makeText(LoginActivity.this, "fail " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getTotalCases() {
        ApiService apiService;
        apiService = ApiClient.getClient(this).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.Token);
        InvestigatorID = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.InvestigatorID);
        Call<TotalCases> call2 = apiService.getTotalCases("Bearer " + Token, InvestigatorID);
        call2.enqueue(new Callback<TotalCases>() {
            @Override
            public void onResponse(Call<TotalCases> call, Response<TotalCases> response) {
                if (response.body() != null) {
                    totalCases = response.body();
                    if (totalCases != null) {
                        if (totalCases.getStatus() != null && totalCases.getStatus().equalsIgnoreCase("success")) {
                            VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
                            visus_dataSource.open();
                            visus_dataSource.delete_tblToalCases();
                            for (TotalCases.TotalCasesData totalCasesData : totalCases.getData()) {
                                visus_dataSource.insertToalCases(totalCasesData);
                            }
                            visus_dataSource.close();


                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TotalCases> call, Throwable t) {
                ErrorLogAPICall apiCall=new ErrorLogAPICall(LoginActivity.this,LoginActivity.this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[2].getMethodName(),t.getMessage(),"API ERROR");
                apiCall.saveErrorLog();
                call.cancel();
                Toast.makeText(LoginActivity.this, "fail " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getMyAssignmentAll() {
        ApiService apiService;
        apiService = ApiClient.getClient(this).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.Token);
        InvestigatorID = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.InvestigatorID);
        Call<MyAssignment> call2 = apiService.getMyPendingAssignmentAll("Bearer " + Token, InvestigatorID);
        call2.enqueue(new Callback<MyAssignment>() {
            @Override
            public void onResponse(Call<MyAssignment> call, Response<MyAssignment> response) {
                if (response.body() != null) {
                    MyAssignment myAssignment = response.body();
                    Log.d("error11", new Gson().toJson(myAssignment.getData()));
                    if (myAssignment != null) {
                        if (myAssignment.getStatus() != null && myAssignment.getStatus().equalsIgnoreCase("success")) {
                            VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
                            visus_dataSource.open();
                            visus_dataSource.delete_tblMyAssignmentData();
                            visus_dataSource.delete_tblInvestigatorObj();
                            for (MyAssignment.MyAssignmentData myAssignmentData : myAssignment.getData()) {
                                visus_dataSource.inserttblMyAssignmentData(myAssignmentData);
                            }
                            visus_dataSource.close();
                        } else {
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<MyAssignment> call, Throwable t) {
                ErrorLogAPICall apiCall=new ErrorLogAPICall(LoginActivity.this,LoginActivity.this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[2].getMethodName(),t.getMessage(),"API ERROR");
                apiCall.saveErrorLog();
                call.cancel();
                Toast.makeText(LoginActivity.this, "fail " + t, Toast.LENGTH_LONG).show();
            }
        });
    }


    private void getHoldMyAssignmentAll() {
        ApiService apiService;
        apiService = ApiClient.getClient(this).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.Token);
        InvestigatorID = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.InvestigatorID);
        Call<MyAssignment> call2 = apiService.getMyHoldAssignmentAll("Bearer " + Token, InvestigatorID);
        call2.enqueue(new Callback<MyAssignment>() {
            @Override
            public void onResponse(Call<MyAssignment> call, Response<MyAssignment> response) {
                if (response.body() != null) {
                    MyAssignment myAssignment = response.body();
                    Log.d("error11", new Gson().toJson(myAssignment.getData()));
                    if (myAssignment != null) {
                        if (myAssignment.getStatus() != null && myAssignment.getStatus().equalsIgnoreCase("success")) {
                            VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
                            visus_dataSource.open();
                            visus_dataSource.delete_tblMyAssignmentData_Hold();
                            visus_dataSource.delete_tblInvestigatorObj();
                            for (MyAssignment.MyAssignmentData myAssignmentData : myAssignment.getData()) {
                                visus_dataSource.inserttblMyAssignmentHoldData(myAssignmentData);
                            }
                            visus_dataSource.close();
                        } else {
                        }
                    }
                    getTotalCases_Hold();


                }
            }

            @Override
            public void onFailure(Call<MyAssignment> call, Throwable t) {
                ErrorLogAPICall apiCall=new ErrorLogAPICall(LoginActivity.this,LoginActivity.this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[2].getMethodName(),t.getMessage(),"API ERROR");
                apiCall.saveErrorLog();
                call.cancel();
                Toast.makeText(LoginActivity.this, "fail " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getTotalCases_Hold() {
        ApiService apiService;
        apiService = ApiClient.getClient(this).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.Token);
        InvestigatorID = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.InvestigatorID);
        Call<TotalCases> call2 = apiService.getTotalCases_Hold("Bearer " + Token, InvestigatorID);
        call2.enqueue(new Callback<TotalCases>() {
            @Override
            public void onResponse(Call<TotalCases> call, Response<TotalCases> response) {
                if (response.body() != null) {
                    totalCases = response.body();
                    if (totalCases != null) {
                        if (totalCases.getStatus() != null && totalCases.getStatus().equalsIgnoreCase("success")) {
                            VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
                            visus_dataSource.open();
                            visus_dataSource.delete_tblToalCases_Hold();
                            for (TotalCases.TotalCasesData totalCasesData : totalCases.getData()) {
                                visus_dataSource.insertToalCases_Hold(totalCasesData);
                            }
                            visus_dataSource.close();


                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TotalCases> call, Throwable t) {
                ErrorLogAPICall apiCall=new ErrorLogAPICall(LoginActivity.this,LoginActivity.this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[2].getMethodName(),t.getMessage(),"API ERROR");
                apiCall.saveErrorLog();
                call.cancel();
                Toast.makeText(LoginActivity.this, "fail " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void Alert(String msg) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setTitleText("Information");
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setContentText(msg);
        sweetAlertDialog.show();
        sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sweetAlertDialog.dismiss();
            }
        });
    }

    private void callListener() {
        login_visus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String investigator_code = investigatorCode.getText().toString();
                String pin = PINCode.getText().toString();
                if (investigator_code.isEmpty()) {
                    Alert("Fill Investigator Code");
                    return;
                } else if (pin.isEmpty()) {
                    Alert("Fill PIN Code");
                    return;
                } else {
                    String INV_code = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.INV_code);
                    String PINCODE = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.PINCODE);
                    if (INV_code.equals(investigator_code) && PINCODE.equals(pin)) {
                        if (ConnectionUtility.isConnected(LoginActivity.this)) {
                            try {
                                syncDeviceInvLocation();
                            } catch (Exception e) {
                                e.getLocalizedMessage();
                            }
                        } else {
                            boolean loginStatus = PrefUtils.getDeviceOfInvStatus(LoginActivity.this, PrefUtils.LoginFristOrSecondTime);
                            if (loginStatus) {
                                DeviceInvLocation deviceInvLocation = new DeviceInvLocation();
                                deviceInvLocation.setDeviceLocation_ID(-1);
                                InvestigatorID = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.InvestigatorID);
                                DEVICEServerID = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.DEVICEServerID);
                                deviceInvLocation.setDeviceLocation_DeviceID(Integer.parseInt(DEVICEServerID != null ? DEVICEServerID : "2"));
                                deviceInvLocation.setDeviceLocation_InvID(Integer.parseInt(InvestigatorID != null ? InvestigatorID : "11"));
                                deviceInvLocation.setDeviceLocation_Latitude(currentLocation != null ? currentLocation.getLatitude() + "" : "0.0");
                                deviceInvLocation.setDeviceLocation_Longitude(currentLocation != null ? currentLocation.getLongitude() + "" : "0.0");
                                deviceInvLocation.setDeviceLocation_GeoAddress(currentLocation != null ? method_geocoder() : "");
                                deviceInvLocation.setDeviceLocation_SaveOnDate(FileUtils.getDateTimeCurrent() != null ? FileUtils.getDateTimeCurrent() : "27052023");
                                deviceInvLocation.setIsLatestDeviceLocation(true);
                                deviceInvLocation.setIsSavedDeviceLocation(false);
                                deviceInvLocation.setExceptionIfAny("");
                                if (deviceInvLocation != null) {
                                    VISUS_DataSource dataSource = new VISUS_DataSource(LoginActivity.this);
                                    dataSource.open();
                                    long val = dataSource.insertDeviceInvLocation(deviceInvLocation);
                                    if (val > 0) {
                                        dataSource.close();
                                        boolean deviceStatus = PrefUtils.getDeviceOfInvStatus(LoginActivity.this, PrefUtils.DeviceOfInvStatus);
                                        if (deviceStatus) {
                                            intentDashBoard();
                                        } else {
                                            sweetAlertBoxInvAndPinWrong();
                                        }
                                    } else dataSource.close();

                                }
                            } else {
                                ConnectionUtility.AlertDialogForNoConnectionAvaialble(LoginActivity.this);
                            }
                        }

                    } else {
                        sweetAlertBoxInvAndPinWrong();
                    }
                }
            }
        });
    }

    private void sweetAlertBoxInvAndPinWrong() {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.setTitleText("SORRY!!!");
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setContentText("Invalid investigator code or pin code");
        sweetAlertDialog.show();
        sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sweetAlertDialog.dismiss();
            }
        });

    }

    private void syncDeviceInvLocation() {
        showProgress();
        DeviceInvLocation deviceInvLocation = new DeviceInvLocation();
        deviceInvLocation.setDeviceLocation_ID(-1);
        InvestigatorID = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.InvestigatorID);
        DEVICEServerID = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.DEVICEServerID);
        deviceInvLocation.setDeviceLocation_DeviceID(Integer.parseInt(DEVICEServerID != null ? DEVICEServerID : "2"));
        deviceInvLocation.setDeviceLocation_InvID(Integer.parseInt(InvestigatorID != null ? InvestigatorID : "11"));
        deviceInvLocation.setDeviceLocation_Latitude(currentLocation != null ? currentLocation.getLatitude() + "" : "0.0");
        deviceInvLocation.setDeviceLocation_Longitude(currentLocation != null ? currentLocation.getLongitude() + "" : "0.0");
        deviceInvLocation.setDeviceLocation_GeoAddress(currentLocation != null ? method_geocoder() : "");
        deviceInvLocation.setDeviceLocation_SaveOnDate(FileUtils.getDateTimeCurrent() != null ? FileUtils.getDateTimeCurrent() : "27052023");
        deviceInvLocation.setIsLatestDeviceLocation(true);
        deviceInvLocation.setIsSavedDeviceLocation(false);
        deviceInvLocation.setExceptionIfAny("");
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.Token);
        Call<DeviceInvLocation> postDeviceInvLocation = apiService.postDeviceInvLocation("Bearer " + Token, deviceInvLocation);
        postDeviceInvLocation.enqueue(new Callback<DeviceInvLocation>() {
            @Override
            public void onResponse(Call<DeviceInvLocation> call, Response<DeviceInvLocation> response) {
                if  (response.isSuccessful()) {
                //    intentDashBoard();
                  getDeviceOfInvStatus();
                } else {

                }
                closeProgress();
            }

            @Override
            public void onFailure(Call<DeviceInvLocation> call, Throwable t) {
                ErrorLogAPICall apiCall=new ErrorLogAPICall(LoginActivity.this,LoginActivity.this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[2].getMethodName(),t.getMessage(),"API ERROR");
                apiCall.saveErrorLog();
                closeProgress();
            }
        });

    }

    private void showProgress() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

    private void closeProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void postDeviceRegistration() {
        /////Insert Device Info Into Room
        DeviceInfo getHardwareAndSoftwareInfoList = getHardwareAndSoftwareInfo();
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String hardwareAndSoftwareInfoJSONObject = gson.toJson(getHardwareAndSoftwareInfoList);
        if (ConnectionUtility.isConnected(LoginActivity.this)) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.setMessage("Please Wait");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }
            Token = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.Token);
            Call<DeviceRegistrationResponse> deviceRegistration = apiService.deviceRegistration("Bearer " + Token, getHardwareAndSoftwareInfoList);
            deviceRegistration.enqueue(new Callback<DeviceRegistrationResponse>() {
                @Override
                public void onResponse(Call<DeviceRegistrationResponse> call, Response<DeviceRegistrationResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            DeviceRegistrationResponse deviceInfo = response.body();
                            PrefUtils.saveToPrefs(LoginActivity.this, PrefUtils.IsDeviceVerified, deviceInfo.getData().get(0).getDeviceVerified() != null ? String.valueOf(deviceInfo.getData().get(0).getDeviceVerified()) : "");
                            PrefUtils.saveToPrefs(LoginActivity.this, PrefUtils.DEVICEServerID, deviceInfo.getData().get(0).getDEVICE_ServerID() != null ? deviceInfo.getData().get(0).getDEVICE_ServerID() : "");
                            if (deviceInfo.getData().get(0).getDeviceVerified() == true) {
                                Intent intentPINActivity = new Intent(LoginActivity.this, DashboardActivity.class);
                                intentPINActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intentPINActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intentPINActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intentPINActivity);
                            } else {
                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE);
                                sweetAlertDialog.setTitleText("Sorry!");
                                sweetAlertDialog.setCancelable(false);
                                sweetAlertDialog.setContentText("Sorry! Your device is not verified, please contact your portal administrator.");
                                sweetAlertDialog.show();
                                sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        sweetAlertDialog.dismiss();
                                        finish();
                                    }
                                });
                            }
                        } else {

                        }
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "" + response.toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<DeviceRegistrationResponse> call, Throwable t) {
                    ErrorLogAPICall apiCall=new ErrorLogAPICall(LoginActivity.this,LoginActivity.this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[2].getMethodName(),t.getMessage(),"API ERROR");
                    apiCall.saveErrorLog();
                    Toast.makeText(getApplicationContext(), "" + t.toString(), Toast.LENGTH_LONG).show();
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            });
        } else {
            ConnectionUtility.AlertDialogForNoConnectionAvaialble(LoginActivity.this);
        }
    }

    private void getDeviceOfInvStatus() {
        showProgress();
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.Token);
        InvestigatorID = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.InvestigatorID);
        DEVICEServerID = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.DEVICEServerID);
        //  Log.d("TAG", "getDeviceOfInvStatus: "+InvestigatorID+"//"+DEVICEServerID);
        Call<DeviceStatusResponse> deviceOfInvStatus = apiService.getDeviceOfInvStatus("Bearer " + Token, InvestigatorID != null ? InvestigatorID : "11", DEVICEServerID != null ? DEVICEServerID : "2");
        deviceOfInvStatus.enqueue(new Callback<DeviceStatusResponse>() {
            @Override
            public void onResponse(Call<DeviceStatusResponse> call, Response<DeviceStatusResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData().get(0).getDeviceStatus() != null && response.body().getData().get(0).getDeviceStatus() == true) {
                            String key = PrefUtils.savedDeviceOfInvStatus(LoginActivity.this, PrefUtils.DeviceOfInvStatus, response.body().getData().get(0).getDeviceStatus());
                            String keyLogin = PrefUtils.savedLoginFristTimeOrSecondTime(LoginActivity.this, PrefUtils.LoginFristOrSecondTime, true);
                            intentDashBoard();
                        } else {
                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE);
                            sweetAlertDialog.setTitleText("Sorry!");
                            sweetAlertDialog.setCancelable(false);
                            sweetAlertDialog.setContentText("Sorry! Your device Of INV Status is not verified, please contact your portal administrator.");
                            sweetAlertDialog.show();
                            sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    sweetAlertDialog.dismiss();
                                }
                            });
                        }
                    } else {
                    }

                } else {
                    // Toast.makeText(LoginActivity.this, response.message() + 500, Toast.LENGTH_SHORT).show();
                }
                closeProgress();
            }

            @Override
            public void onFailure(Call<DeviceStatusResponse> call, Throwable t) {
                ErrorLogAPICall apiCall=new ErrorLogAPICall(LoginActivity.this,LoginActivity.this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[2].getMethodName(),t.getMessage(),"API ERROR");
                apiCall.saveErrorLog();
                closeProgress();
            }
        });
    }

    private void intentDashBoard() {
        Intent intentPINActivity = new Intent(LoginActivity.this, DashboardActivity.class);
        intentPINActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intentPINActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentPINActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentPINActivity);
    }

    private DeviceInfo getHardwareAndSoftwareInfo() {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setRELEASE(Build.VERSION.RELEASE);
        deviceInfo.setDEVICE(Build.DEVICE);//Build.VERSION.SDK
        deviceInfo.setMODEL(Build.MODEL);
        deviceInfo.setPRODUCT(Build.PRODUCT);
        deviceInfo.setBRAND(Build.BRAND);
        deviceInfo.setDISPLAY(Build.DISPLAY);
        deviceInfo.setCPU_ABI(Build.CPU_ABI);
        deviceInfo.setCPU_ABI2(Build.CPU_ABI2);
        deviceInfo.setUNKNOWN(Build.UNKNOWN);
        deviceInfo.setHARDWARE(Build.HARDWARE);
        deviceInfo.setID(Build.ID);
        Log.d("ID", Build.ID);
        deviceInfo.setMANUFACTURER(Build.MANUFACTURER);
        deviceInfo.setSERIAL(Build.SERIAL);
        deviceInfo.setUSER(Build.USER);
        deviceInfo.setHOST(Build.HOST);
        deviceInfo.setDEVICE_ID(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        deviceInfo.setDEVICE_ServerID("-1");
        return deviceInfo;
    }

}