package com.org.visus.holdgassessment.actvity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.org.visus.R;
import com.org.visus.activity.DashboardActivity;
import com.org.visus.activity.datasource.VISUS_DataSource;
import com.org.visus.apis.ApiClient;
import com.org.visus.apis.ApiService;
import com.org.visus.databinding.ActivityFinalSubmissionAssignmentHoldBinding;
import com.org.visus.models.GetServices;
import com.org.visus.models.MyAssignment;
import com.org.visus.models.TotalCases;
import com.org.visus.holdgassessment.adapter.FinalSubmissionAssignmentHoldAdapter;
import com.org.visus.utility.ConnectionUtility;
import com.org.visus.utility.PrefUtils;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalSubmissionAssignmentHoldActivity extends AppCompatActivity {
    TotalCases totalCases;
    List<TotalCases.TotalCasesData> totalCase;
    ActivityFinalSubmissionAssignmentHoldBinding assignmentHoldBinding;
    List<String> visusServiceList = new ArrayList<>();
    List<String> IDsVisusServiceList = new ArrayList<>();
    String visusService = "Select Service";
    String visusServiceID = "";
    String Token, InvestigatorID;

    Bundle bundle;
    String VisusService = "", VisusServiceID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignmentHoldBinding = ActivityFinalSubmissionAssignmentHoldBinding.inflate(getLayoutInflater());
        setContentView(assignmentHoldBinding.getRoot());
        resetService();
        getServices();
        if (ConnectionUtility.isConnected(FinalSubmissionAssignmentHoldActivity.this)) {
            getTotalCases_Hold();
        } else {
            ConnectionUtility.AlertDialogForNoConnectionAvaialble(FinalSubmissionAssignmentHoldActivity.this);
        }
        addSpinnerAdapter(assignmentHoldBinding.spinnerSelectVisusService, visusServiceList);
        callListener();
        bundle = getIntent().getExtras();

        if (bundle != null) {
            VisusService = bundle.getString("VisusService", "");
            VisusServiceID = bundle.getString("VisusServiceID", "");
            if (!VisusServiceID.isEmpty()) {
                if (ConnectionUtility.isConnected(FinalSubmissionAssignmentHoldActivity.this)) {
                    getTotalCases_Hold();
                    getMyAssignment_Hold(visusServiceID);

                } else {
                    ConnectionUtility.AlertDialogForNoConnectionAvaialble(FinalSubmissionAssignmentHoldActivity.this);
                }

                //getMyAssignmentAccordingTOServiceID(visusServiceID);
            }
        }
    }

    private void getServices() {
        VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
        visus_dataSource.open();
        List<GetServices.GetServicesData> getServices = visus_dataSource.getMyServises();
        for (GetServices.GetServicesData servicesData : getServices) {
            visusServiceList.add(servicesData.getVisusServicesText());
            IDsVisusServiceList.add(servicesData.getVisusServicesID().toString());
        }
        visus_dataSource.close();
    }

    private void resetService() {
        visusServiceList.clear();
        String list = "Select Service";
        visusServiceList.add(list);
        addSpinnerAdapter(assignmentHoldBinding.spinnerSelectVisusService, visusServiceList);
    }

    private void getTotalCases_Hold() {
        ApiService apiService;
        apiService = ApiClient.getClient(this).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(FinalSubmissionAssignmentHoldActivity.this, PrefUtils.Token);
        InvestigatorID = PrefUtils.getFromPrefs(FinalSubmissionAssignmentHoldActivity.this, PrefUtils.InvestigatorID);
        Call<TotalCases> call2 = apiService.getTotalCases_Hold("Bearer " + Token, InvestigatorID);
        call2.enqueue(new Callback<TotalCases>() {
            @Override
            public void onResponse(Call<TotalCases> call, Response<TotalCases> response) {
                if (response.body() != null) {
                    totalCases = response.body();
                    Log.e("totalCases", response.body().toString());
                    if (totalCases != null) {
                        int TotalPendingCases = 0, TotalSubmittedCases = 0;
                        if (totalCases.getStatus() != null && totalCases.getStatus().equalsIgnoreCase("success")) {
                            for (TotalCases.TotalCasesData totalCasesData : totalCases.getData()) {
                                TotalPendingCases = TotalPendingCases + totalCasesData.getTotalPendingCases();
                                TotalSubmittedCases = TotalSubmittedCases + totalCasesData.getTotalCases();
                            }
                            assignmentHoldBinding.textViewTotalSubmittedCases.setText(TotalSubmittedCases + "");
                            assignmentHoldBinding.textViewTotalPendingCases.setText(TotalPendingCases + "");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TotalCases> call, Throwable t) {
                call.cancel();
                Toast.makeText(FinalSubmissionAssignmentHoldActivity.this, "fail " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getMyAssignment_Hold(String visusServiceID) {
        ApiService apiService;
        apiService = ApiClient.getClient(this).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(FinalSubmissionAssignmentHoldActivity.this, PrefUtils.Token);
        InvestigatorID = PrefUtils.getFromPrefs(FinalSubmissionAssignmentHoldActivity.this, PrefUtils.InvestigatorID);
        Call<MyAssignment> call2 = apiService.getMyPendingAssignment_Hold("Bearer " + Token, visusServiceID, InvestigatorID);
        call2.enqueue(new Callback<MyAssignment>() {
            @Override
            public void onResponse(Call<MyAssignment> call, Response<MyAssignment> response) {
                if (response.body() != null) {
                    MyAssignment myAssignment = response.body();
                    if (myAssignment != null) {
                        if (myAssignment.getStatus() != null && myAssignment.getStatus().equalsIgnoreCase("success")) {
                            if (myAssignment.getData().size() == 0) {
                                assignmentHoldBinding.recylerViewMyAssignment.setVisibility(View.GONE);
                                assignmentHoldBinding.textViewNoRecordMyAssignment.setVisibility(View.VISIBLE);
                                // assignmentHoldBinding.textViewTotalSubmittedCases.setText("0");
                                assignmentHoldBinding.textViewTotalPendingCases.setText("0");
                            } else {
                                assignmentHoldBinding.recylerViewMyAssignment.setVisibility(View.VISIBLE);
                                assignmentHoldBinding.textViewNoRecordMyAssignment.setVisibility(View.GONE);
                                assignmentHoldBinding.recylerViewMyAssignment.setHasFixedSize(true);
                                assignmentHoldBinding.recylerViewMyAssignment.setAdapter(new FinalSubmissionAssignmentHoldAdapter(FinalSubmissionAssignmentHoldActivity.this, myAssignment.getData(), FinalSubmissionAssignmentHoldActivity.this, visusService, visusServiceID));
//                            assignmentHoldBinding.recylerViewMyAssignment.setAdapter(new Assigment_Adapter(FinalSubmissionAssignment_Activity.this, myAssignment.getData(), getApplicationContext(), visusService, visusServiceID));
                                for (String iDsVisusServiceList : IDsVisusServiceList) {
                                    if (visusServiceID.equals(iDsVisusServiceList)) {
                                        //assignmentHoldBinding.textViewTotalSubmittedCases.setText(totalCases.getData().get((Integer.parseInt(visusServiceID) - 1)).getTotalSubmittedCases() + "");
                                        assignmentHoldBinding.textViewTotalPendingCases.setText(totalCases.getData().get(Integer.parseInt(visusServiceID) - 1).getTotalPendingCases() + "");
                                        break;
                                    }
                                }
                            }
                        } else {
                        }
                    }
                }
                else {
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(FinalSubmissionAssignmentHoldActivity.this, SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setTitleText("SORRY!!!!");
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.setContentText(response.message());
                    sweetAlertDialog.show();
                    sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sweetAlertDialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<MyAssignment> call, Throwable t) {
                call.cancel();
                Toast.makeText(FinalSubmissionAssignmentHoldActivity.this, "fail " + t, Toast.LENGTH_LONG).show();
            }
        });
    }


    private void getToalCasesAccordingToServiceID(String visusServiceID) {
        int TotalPendingCases = 0, TotalSubmittedCases = 0;
        VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
        visus_dataSource.open();
        totalCase = visus_dataSource.getToalCasesAccordingToServiceID(visusServiceID);
        for (TotalCases.TotalCasesData totalCasesData : totalCase) {
            TotalPendingCases = TotalPendingCases + totalCasesData.getTotalPendingCases();
            TotalSubmittedCases = TotalSubmittedCases + totalCasesData.getTotalSubmittedCases();
        }
        assignmentHoldBinding.textViewTotalSubmittedCases.setText(String.valueOf(TotalSubmittedCases));
        assignmentHoldBinding.textViewTotalPendingCases.setText(String.valueOf(TotalPendingCases));
        visus_dataSource.close();
    }

    private void callListener() {
        assignmentHoldBinding.spinnerSelectVisusService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {
                    visusService = String.valueOf(adapterView.getItemAtPosition(position));
                    visusServiceID = IDsVisusServiceList.get(position - 1);
                    if (ConnectionUtility.isConnected(FinalSubmissionAssignmentHoldActivity.this)) {
                        if (visusServiceID.equalsIgnoreCase("4")) {
                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(FinalSubmissionAssignmentHoldActivity.this, SweetAlertDialog.WARNING_TYPE);
                            sweetAlertDialog.setTitleText("Information!!!!");
                            sweetAlertDialog.setCancelable(false);
                            sweetAlertDialog.setContentText("Work in progress...");
                            sweetAlertDialog.show();
                            sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    sweetAlertDialog.dismiss();
                                }
                            });
                            Toast.makeText(FinalSubmissionAssignmentHoldActivity.this, "Work in progress...", Toast.LENGTH_SHORT).show();
                        } else {
                            getMyAssignment_Hold(visusServiceID);
                        }
                    } else {
                        ConnectionUtility.AlertDialogForNoConnectionAvaialble(FinalSubmissionAssignmentHoldActivity.this);
                    }
                    //getMyAssignmentAccordingTOServiceID(visusServiceID);
                } else {
                    visusService = "Select Service";
                    visusServiceID = "";
                    if (ConnectionUtility.isConnected(FinalSubmissionAssignmentHoldActivity.this)) {
                        getTotalCases_Hold();
                    } else {
                        ConnectionUtility.AlertDialogForNoConnectionAvaialble(FinalSubmissionAssignmentHoldActivity.this);
                    }
                    assignmentHoldBinding.recylerViewMyAssignment.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void addSpinnerAdapter(SearchableSpinner spinner, List<String> listModels) {
        FinalSubmissionAssignmentHoldActivity.MySpinnerAdapter dataAdapter = new FinalSubmissionAssignmentHoldActivity.MySpinnerAdapter(FinalSubmissionAssignmentHoldActivity.this, android.R.layout.simple_spinner_item, listModels);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(dataAdapter);
    }

    private static class MySpinnerAdapter extends ArrayAdapter<String> {
        //Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/muli_semibold.ttf");
        private MySpinnerAdapter(Context context, int resource, List<String> items) {
            super(context, resource, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 0);
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setLayoutParams(params);
            view.setTextColor(Color.BLACK);
            //view.setTextSize(15);
            //view.setTypeface(font);
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            return view;
        }
    }

    public void goBack(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FinalSubmissionAssignmentHoldActivity.this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}