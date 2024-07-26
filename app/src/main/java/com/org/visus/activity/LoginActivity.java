package com.org.visus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.org.visus.R;
import com.org.visus.activity.datasource.VISUS_DataSource;
import com.org.visus.apis.ApiClient;
import com.org.visus.apis.ApiService;
import com.org.visus.models.GetServices;
import com.org.visus.models.MyAssignment;
import com.org.visus.models.RequreActions;
import com.org.visus.models.TotalCases;
import com.org.visus.utility.ConnectionUtility;
import com.org.visus.utility.PrefUtils;
import com.skydoves.elasticviews.ElasticButton;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText investigatorCode, PINCode;
    ElasticButton login_visus;
    String Token, InvestigatorID;
    TotalCases totalCases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (ConnectionUtility.isConnected(LoginActivity.this)) {
            String getToken = PrefUtils.getToken(LoginActivity.this);
            getRequreActionsData();
            getServices();
            getTotalCases();
            getMyAssignmentAll();
        }
        findViewById();
        callListener();
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
                call.cancel();
                Toast.makeText(LoginActivity.this, "fail " + t, Toast.LENGTH_LONG).show();
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