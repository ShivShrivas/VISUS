package com.org.visus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.org.visus.R;
import com.org.visus.apis.ApiClient;
import com.org.visus.apis.ApiService;
import com.org.visus.models.Investigator;
import com.org.visus.utility.PrefUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvestigatorActivity extends AppCompatActivity {

    Button InvestigatorInfo_btn;
    TextInputEditText investi_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigator);
        findViewById();
        callListener();
    }

    private void callListener() {
        InvestigatorInfo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = investi_edit.getText().toString();
                if (text.isEmpty()) {
                    Toast.makeText(InvestigatorActivity.this, "Enter Investigator Code", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    try {
                        getInvestigatorInfoAccordingToInvestigatorID(text);
                       // Toast.makeText(InvestigatorActivity.this, "Click Ok", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(InvestigatorActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    private void findViewById() {
        InvestigatorInfo_btn = findViewById(R.id.InvestigatorInfo_btn);
        investi_edit = findViewById(R.id.investi_edit);
    }

    private void getInvestigatorInfoAccordingToInvestigatorID(String insCode) {

       // Toast.makeText(InvestigatorActivity.this, "api call", Toast.LENGTH_SHORT).show();
        ApiService apiService;
        apiService = ApiClient.getClient(this).create(ApiService.class);
        String Token = PrefUtils.getFromPrefs(InvestigatorActivity.this, PrefUtils.Token);
        Call<Investigator> call2 = apiService.getInvestigatorInfoAccordingToInvestigatorID("Bearer " + Token, insCode);
        call2.enqueue(new Callback<Investigator>() {
            @Override
            public void onResponse(Call<Investigator> call, Response<Investigator> response) {

                if (response.body() != null) {
                  //  Toast.makeText(InvestigatorActivity.this, "response body ok", Toast.LENGTH_SHORT).show();
                    final Investigator investigator = response.body();
                    if (investigator != null) {
                        if (investigator.getStatus() != null && investigator.getStatus().equalsIgnoreCase("success")) {
                            if (investigator.getData().size() == 0) {
                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(InvestigatorActivity.this, SweetAlertDialog.ERROR_TYPE);
                                sweetAlertDialog.setTitleText("SORRY!!!!");
                                sweetAlertDialog.setCancelable(false);
                                sweetAlertDialog.setContentText("Your provided investigator code is invalid, contact portal administrator");
                                sweetAlertDialog.show();
                                sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        sweetAlertDialog.dismiss();
                                    }
                                });
                            } else {
                                if (investigator.getData().get(0).getiSActive() != null && investigator.getData().get(0).getiSActive().equalsIgnoreCase("yes")) {
                                    PrefUtils.saveToPrefs(InvestigatorActivity.this, PrefUtils.INV_code, investigator.getData().get(0).getiNVCode());
                                    PrefUtils.saveToPrefs(InvestigatorActivity.this, PrefUtils.INV_name, investigator.getData().get(0).getiNVName());
                                    PrefUtils.saveToPrefs(InvestigatorActivity.this, PrefUtils.INV_FatherName, investigator.getData().get(0).getiNVFatherName());
                                    PrefUtils.saveToPrefs(InvestigatorActivity.this, PrefUtils.ContactNumber, investigator.getData().get(0).getiNVContactNumber1());
                                    PrefUtils.saveToPrefs(InvestigatorActivity.this, PrefUtils.ContactNumber2, investigator.getData().get(0).getiNVContactNumber2());
                                    PrefUtils.saveToPrefs(InvestigatorActivity.this, PrefUtils.Email, investigator.getData().get(0).getEmail());
                                    PrefUtils.saveToPrefs(InvestigatorActivity.this, PrefUtils.PanNumber, investigator.getData().get(0).getpANNumber());
                                    PrefUtils.saveToPrefs(InvestigatorActivity.this, PrefUtils.JoiningDate, investigator.getData().get(0).getJoiningDate());
                                    PrefUtils.saveToPrefs(InvestigatorActivity.this, PrefUtils.Status, investigator.getData().get(0).getiSActive());
                                    PrefUtils.saveToPrefs(InvestigatorActivity.this, PrefUtils.InvestigatorID, investigator.getData().get(0).getInvId().toString());
                                    /////////////////////////////////////////////////////////////////
                                    Intent intentOTPActivity = new Intent(InvestigatorActivity.this, OTPActivity.class);
                                    intentOTPActivity.putExtra("InvestigatorID", investigator.getData().get(0).getInvId().toString());
                                    intentOTPActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intentOTPActivity);
                                } else {
                                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(InvestigatorActivity.this, SweetAlertDialog.WARNING_TYPE);
                                    sweetAlertDialog.setTitleText("SORRY!");
                                    sweetAlertDialog.setCancelable(false);
                                    sweetAlertDialog.setContentText("Sorry! Your provided investigator code is deactivated, contact portal administrator.");
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
                    }


                }
                //Toast.makeText(InvestigatorActivity.this, "response body null", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Investigator> call, Throwable t) {
                call.cancel();
                Toast.makeText(InvestigatorActivity.this, "fail " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}