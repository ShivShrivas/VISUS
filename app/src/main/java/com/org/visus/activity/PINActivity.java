package com.org.visus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.org.visus.R;
import com.org.visus.utility.PrefUtils;
import com.skydoves.elasticviews.ElasticButton;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PINActivity extends AppCompatActivity {

    PinView otp_view_visus, confirm_otp_view_visus;
    ElasticButton otp_view_visus_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinactivity);
        findViewById();
        callListener();
    }

    private void callListener() {
        otp_view_visus_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp_view = otp_view_visus.getText().toString();
                String confirm_otp_view = confirm_otp_view_visus.getText().toString();
                if (otp_view.isEmpty() && confirm_otp_view.isEmpty()) {
                    Toast.makeText(PINActivity.this, "Fill PIN", Toast.LENGTH_SHORT).show();
                } else {
                    if (otp_view.equals(confirm_otp_view)) {
                        PrefUtils.saveToPrefs(PINActivity.this, PrefUtils.PINCODE, confirm_otp_view);
                        Intent intentPINActivity = new Intent(PINActivity.this, LoginActivity.class);
                        intentPINActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentPINActivity);
                    } else {
                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(PINActivity.this, SweetAlertDialog.ERROR_TYPE);
                        sweetAlertDialog.setTitleText("SORRY!");
                        sweetAlertDialog.setCancelable(false);
                        sweetAlertDialog.setContentText("SORRY! Invalid PIN");
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
        });
    }

    private void findViewById() {
        otp_view_visus = findViewById(R.id.otp_view_visus);
        confirm_otp_view_visus = findViewById(R.id.confirm_otp_view_visus);
        otp_view_visus_submit = findViewById(R.id.otp_view_visus_submit);
    }
}