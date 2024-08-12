package com.org.visus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.org.visus.R;
import com.org.visus.apis.ApiClient;
import com.org.visus.apis.ApiService;
import com.org.visus.apis.ErrorLogAPICall;
import com.org.visus.models.SMSAsOTP;
import com.org.visus.utility.PrefUtils;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends AppCompatActivity {
    Bundle bundle;
    String insCode;
    String Token, DEVICEServerID;
    TextView setmobile, verify_otp, counterTimer;
    PinView otp_view;
    String API_OTP;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            insCode = bundle.getString("InvestigatorID", "");
        }
        findViewById();
        callListener();
        getSmsAsOtp(insCode);
        Counter_method();
    }

    private void Counter_method() {

        long log_timing = Long.parseLong(String.valueOf(5));
        long timeLeftInMillis = log_timing * 60 * 1000;
//         long duration= TimeUnit.MINUTES.toMillis(log_timing);
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                String sDuration = String.format(Locale.ENGLISH, "%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(l),
                        TimeUnit.MILLISECONDS.toSeconds(l) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));

                counterTimer.setText(sDuration);
            }

            @Override
            public void onFinish() {
                countDownTimer.cancel();
            }
        }.start();
    }

    private void callListener() {
        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp_view.getText().toString().equals(API_OTP)) {
                    countDownTimer.cancel();
                    Intent intentPINActivity = new Intent(OTPActivity.this, PINActivity.class);
                    intentPINActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intentPINActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intentPINActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentPINActivity);
                } else {
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(OTPActivity.this, SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setTitleText("SORRY!");
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.setContentText("SORRY! Invalid OTP");
                    sweetAlertDialog.show();
                    sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sweetAlertDialog.dismiss();
                        }
                    });
                }

            }
        });
    }

    private void findViewById() {
        setmobile = findViewById(R.id.setmobile);
        verify_otp = findViewById(R.id.verify_otp);
        otp_view = findViewById(R.id.otp_view);
        counterTimer = findViewById(R.id.counterTimer);
    }

    private void getSmsAsOtp(String insCode) {
        ApiService apiService;
        apiService = ApiClient.getClient(this).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(OTPActivity.this, PrefUtils.Token);
        DEVICEServerID = PrefUtils.getFromPrefs(OTPActivity.this, PrefUtils.DEVICEServerID);
        Call<SMSAsOTP> call2 = apiService.getSmsAsOtp("Bearer " + Token, insCode, DEVICEServerID);
        call2.enqueue(new Callback<SMSAsOTP>() {
            @Override
            public void onResponse(Call<SMSAsOTP> call, Response<SMSAsOTP> response) {

                if (response.body() != null) {
                    Log.i("response", "onResponse: " + response.body());
                    final SMSAsOTP smsAsOTP = response.body();
                    if (smsAsOTP != null) {
                        if (smsAsOTP.getStatus() != null && smsAsOTP.getStatus().equalsIgnoreCase("success")) {
                            setmobile.setText("+91" + smsAsOTP.getData().get(0).getDisplayMobNumber());
                            otp_view.setText(smsAsOTP.getData().get(0).getOtp());
                            API_OTP = smsAsOTP.getData().get(0).getOtp();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<SMSAsOTP> call, Throwable t) {
                ErrorLogAPICall apiCall=new ErrorLogAPICall(OTPActivity.this,OTPActivity.this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[2].getMethodName(),t.getMessage(),"API ERROR");
                apiCall.saveErrorLog();
                call.cancel();
                Toast.makeText(OTPActivity.this, "fail " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}