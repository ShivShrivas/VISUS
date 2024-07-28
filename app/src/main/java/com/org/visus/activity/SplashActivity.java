package com.org.visus.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.org.visus.R;
import com.org.visus.apis.ApiClient;
import com.org.visus.apis.ApiService;
import com.org.visus.models.DeviceInfo;
import com.org.visus.models.DeviceRegistrationResponse;
import com.org.visus.models.TokenResponse;
import com.org.visus.utility.ConnectionUtility;
import com.org.visus.utility.PrefUtils;

import java.util.Timer;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private Timer timer;
    private ProgressBar progressBar;
    private int i = 0;
    ApiService apiService;
    String Token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Token = PrefUtils.getFromPrefs(SplashActivity.this, PrefUtils.Token);
        if (Token.equalsIgnoreCase("")) {
            if (ConnectionUtility.isConnected(SplashActivity.this)) {
                getToken();
            } else {
                findViewById(R.id.img).setVisibility(View.GONE);
                findViewById(R.id.img_no_internet).setVisibility(View.VISIBLE);
                ConnectionUtility.AlertDialogForNoConnectionAvaialble(SplashActivity.this);
            }
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    updateUI();
                }
            }, 5000);   //5 seconds

        }

        /*try {
            String DeviceID = PrefUtils.getFromPrefs(SplashActivity.this, PrefUtils.DeviceID);
            if (DeviceID.equalsIgnoreCase("")) {
                if (ConnectionUtility.isConnected(SplashActivity.this)) {
                    postDeviceRegistration();
                } else {
                    ConnectionUtility.AlertDialogForNoConnectionAvaialble(SplashActivity.this);
                }
            }
            progressBar = findViewById(R.id.progressBar);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progressBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            }
            progressBar.setProgress(0);
            final long period = 40;
            timer = new Timer();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (i < 100) {
                        progressBar.setProgress(i);
                        i++;
                    } else {
                        timer.cancel();
                        updateUI();
                    }
                }
            }, 0, period);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
        }*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
                        // Toast.makeText(SplashActivity.this, "Token " + tokenReponse.getAccessToken().toString(), Toast.LENGTH_LONG).show();
                        PrefUtils.saveToPrefs(SplashActivity.this, PrefUtils.Token, tokenReponse.getAccessToken() != null ? tokenReponse.getAccessToken() : "");
                        postDeviceRegistration();
                    }
                }

            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(SplashActivity.this, "fail " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUI() {
        Intent intent = null;
        String PINCODE = PrefUtils.getFromPrefs(SplashActivity.this, PrefUtils.PINCODE);
        String INV_code = PrefUtils.getFromPrefs(SplashActivity.this, PrefUtils.INV_code);
        if (INV_code.isEmpty() || PINCODE.isEmpty()) {
            intent = new Intent(SplashActivity.this, InvestigatorActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
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
        /*if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            deviceInfo.setDEVICE_ID(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        }*/
        deviceInfo.setDEVICE_ServerID("-1");
        return deviceInfo;
    }

    private void postDeviceRegistration() {
        /////Insert Device Info Into Room
        DeviceInfo getHardwareAndSoftwareInfoList = getHardwareAndSoftwareInfo();
        apiService = ApiClient.getClient(this).create(ApiService.class);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String hardwareAndSoftwareInfoJSONObject = gson.toJson(getHardwareAndSoftwareInfoList);
        //Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        //String prettyJsonForLogin = prettyGson.toJson(JSONObject);
        if (ConnectionUtility.isConnected(SplashActivity.this)) {
            Token = PrefUtils.getFromPrefs(SplashActivity.this, PrefUtils.Token);
            Call<DeviceRegistrationResponse> deviceRegistration = apiService.deviceRegistration("Bearer " + Token, getHardwareAndSoftwareInfoList);
            deviceRegistration.enqueue(new Callback<DeviceRegistrationResponse>() {
                @Override
                public void onResponse(Call<DeviceRegistrationResponse> call, Response<DeviceRegistrationResponse> response) {
                    Log.d("TAG", "onResponse: "+new Gson().toJson(response.body()));
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            DeviceRegistrationResponse deviceInfo = response.body();
                            //Toast.makeText(getApplicationContext(), "" + deviceInfo.getData().get(0).getMODEL(), Toast.LENGTH_LONG).show();
                            PrefUtils.saveToPrefs(SplashActivity.this, PrefUtils.IsDeviceVerified, deviceInfo.getData().get(0).getDeviceVerified() != null ? String.valueOf(deviceInfo.getData().get(0).getDeviceVerified()) : "");
                            PrefUtils.saveToPrefs(SplashActivity.this, PrefUtils.DEVICEServerID, deviceInfo.getData().get(0).getDEVICE_ServerID() != null ? deviceInfo.getData().get(0).getDEVICE_ServerID() : "");
                            if (deviceInfo.getData().get(0).getDeviceVerified() == true) {
                                updateUI();
                            } else {
                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(SplashActivity.this, SweetAlertDialog.WARNING_TYPE);
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
                            /*DeviceRegistrationResponse deviceInfo = response.body();
                            SharedPreferences sharedPreferences = getSharedPreferences("SocietyDeviceInfo", MODE_PRIVATE);
                            SharedPreferences.Editor deviceInfoEditor = sharedPreferences.edit();
                            deviceInfoEditor.putString("DeviceID", deviceInfo.getDEVICE_ServerID());
                            deviceInfoEditor.commit();
                            ///GO to Login Page Accroding to Type
                            final Intent intentLoginActivity = new Intent(SplashActivity.this, MobileRegistrationActivity.class);
                            intentLoginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intentLoginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intentLoginActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Thread timer = new Thread() {
                                public void run() {
                                    try {
                                        sleep(5000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } finally {
                                        getApplicationContext().startActivity(intentLoginActivity);
                                        finish();
                                    }
                                }
                            };
                            timer.start();*/

                        } else {

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "" + response.toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<DeviceRegistrationResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "" + t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            ConnectionUtility.AlertDialogForNoConnectionAvaialble(SplashActivity.this);
        }
    }
}