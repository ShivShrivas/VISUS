package com.org.visus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.org.visus.R;
import com.org.visus.utility.PrefUtils;
import com.skydoves.elasticviews.ElasticButton;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText investigatorCode, PINCode;
    ElasticButton login_visus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById();
        callListener();
    }

    private void findViewById() {
        investigatorCode = findViewById(R.id.investigatorCode);
        PINCode = findViewById(R.id.PINCode);
        login_visus = findViewById(R.id.login_visus);
    }

    private void callListener() {
        login_visus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String investigator_code = investigatorCode.getText().toString();
                String pin = PINCode.getText().toString();
                if (investigator_code.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fill Investigator Code", Toast.LENGTH_LONG).show();
                    return;
                } else if (pin.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fill PIN Code", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    String INV_code = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.INV_code);
                    String PINCODE = PrefUtils.getFromPrefs(LoginActivity.this, PrefUtils.PINCODE);
                    if (INV_code.equals(investigator_code) && PINCODE.equals(pin)) {
                        Intent intentPINActivity = new Intent(LoginActivity.this, DashboardActivity.class);
                        intentPINActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intentPINActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intentPINActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentPINActivity);
                    } else {
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
                }
            }
        });
    }
}