package com.org.visus.activity;

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

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.org.visus.R;
import com.org.visus.activity.datasource.VISUS_DataSource;
import com.org.visus.adapters.FinalSubmissionAssignment_Adapter;
import com.org.visus.apis.ApiClient;
import com.org.visus.apis.ApiService;
import com.org.visus.databinding.ActivityFinalSubmissionAssignmentBinding;
import com.org.visus.models.GetServices;
import com.org.visus.models.MyAssignment;
import com.org.visus.models.TotalCases;
import com.org.visus.utility.ConnectionUtility;
import com.org.visus.utility.PrefUtils;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalSubmissionAssignment_Activity extends AppCompatActivity {
    TotalCases totalCases;
    List<TotalCases.TotalCasesData> totalCase;
    ActivityFinalSubmissionAssignmentBinding assignmentBinding;
    List<String> visusServiceList = new ArrayList<>();
    String visusServiceID = "";
    List<String> IDsVisusServiceList = new ArrayList<>();
    String visusService = "Select Service";
    String Token, InvestigatorID;

    Bundle bundle;
    String VisusService = "", VisusServiceID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignmentBinding = ActivityFinalSubmissionAssignmentBinding.inflate(getLayoutInflater());
        setContentView(assignmentBinding.getRoot());
        resetService();
        getServices();
        if (ConnectionUtility.isConnected(FinalSubmissionAssignment_Activity.this)) {
            getTotalCases();
        } else {
            ConnectionUtility.AlertDialogForNoConnectionAvaialble(FinalSubmissionAssignment_Activity.this);
        }
        addSpinnerAdapter(assignmentBinding.spinnerSelectVisusService, visusServiceList);
        callListener();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            VisusService = bundle.getString("VisusService", "");
            VisusServiceID = bundle.getString("VisusServiceID", "");
            if (!VisusServiceID.isEmpty()) {
                if (ConnectionUtility.isConnected(FinalSubmissionAssignment_Activity.this)) {
                    getTotalCases();
                    getMyAssignment(visusServiceID);
                } else {
                    ConnectionUtility.AlertDialogForNoConnectionAvaialble(FinalSubmissionAssignment_Activity.this);
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
        addSpinnerAdapter(assignmentBinding.spinnerSelectVisusService, visusServiceList);
    }

    private void getTotalCases() {
        ApiService apiService;
        apiService = ApiClient.getClient(this).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(FinalSubmissionAssignment_Activity.this, PrefUtils.Token);
        InvestigatorID = PrefUtils.getFromPrefs(FinalSubmissionAssignment_Activity.this, PrefUtils.InvestigatorID);
        Call<TotalCases> call2 = apiService.getTotalCases("Bearer " + Token, InvestigatorID);
        call2.enqueue(new Callback<TotalCases>() {
            @Override
            public void onResponse(Call<TotalCases> call, Response<TotalCases> response) {
                if (response.body() != null) {

                    int TotalPendingCases = 0, TotalSubmittedCases = 0;
                    totalCases = response.body();

                    if (totalCases != null) {
                        if (totalCases.getStatus() != null && totalCases.getStatus().equalsIgnoreCase("success")) {
                            int i = 0;
                            for (TotalCases.TotalCasesData totalCasesData : totalCases.getData()) {
                                TotalPendingCases = TotalPendingCases + totalCasesData.getTotalPendingCases();
                                TotalSubmittedCases = TotalSubmittedCases + totalCasesData.getTotalCases();
                            }
                            assignmentBinding.textViewTotalSubmittedCases.setText(TotalSubmittedCases + "");
                            assignmentBinding.textViewTotalPendingCases.setText(TotalPendingCases + "");

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TotalCases> call, Throwable t) {
                call.cancel();
                Toast.makeText(FinalSubmissionAssignment_Activity.this, "fail " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getMyAssignment(String visusServiceID) {
        ApiService apiService;
        apiService = ApiClient.getClient(this).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(FinalSubmissionAssignment_Activity.this, PrefUtils.Token);
        InvestigatorID = PrefUtils.getFromPrefs(FinalSubmissionAssignment_Activity.this, PrefUtils.InvestigatorID);
        Call<MyAssignment> call2 = apiService.getMyPendingAssignment("Bearer " + Token, visusServiceID, InvestigatorID);
        call2.enqueue(new Callback<MyAssignment>() {
            @Override
            public void onResponse(Call<MyAssignment> call, Response<MyAssignment> response) {
                if(response.isSuccessful() && response.code()==200){
                    if (response.body() != null) {
                        MyAssignment myAssignment = response.body();
                        if (myAssignment != null) {
                            if (myAssignment.getStatus() != null && myAssignment.getStatus().equalsIgnoreCase("success")) {
                                if (myAssignment.getData().size() == 0) {
                                    assignmentBinding.recylerViewMyAssignment.setVisibility(View.GONE);
                                    assignmentBinding.textViewNoRecordMyAssignment.setVisibility(View.VISIBLE);
                                    assignmentBinding.textViewTotalSubmittedCases.setText("0");
                                    assignmentBinding.textViewTotalPendingCases.setText("0");

                                } else {
                                    assignmentBinding.recylerViewMyAssignment.setVisibility(View.VISIBLE);
                                    assignmentBinding.textViewNoRecordMyAssignment.setVisibility(View.GONE);
                                    assignmentBinding.recylerViewMyAssignment.setHasFixedSize(true);
                                    assignmentBinding.recylerViewMyAssignment.setAdapter(new FinalSubmissionAssignment_Adapter(FinalSubmissionAssignment_Activity.this, myAssignment.getData(), FinalSubmissionAssignment_Activity.this, visusService, visusServiceID));
//                            assignmentBinding.recylerViewMyAssignment.setAdapter(new Assigment_Adapter(FinalSubmissionAssignment_Activity.this, myAssignment.getData(), getApplicationContext(), visusService, visusServiceID));
                                    for (String iDsVisusServiceList : IDsVisusServiceList) {
                                        if (visusServiceID.equals(iDsVisusServiceList)) {
                                            assignmentBinding.textViewTotalSubmittedCases.setText(totalCases.getData().get((Integer.parseInt(visusServiceID) - 1)).getTotalCases() + "");
                                            assignmentBinding.textViewTotalPendingCases.setText(totalCases.getData().get(Integer.parseInt(visusServiceID) - 1).getTotalPendingCases() + "");
                                            break;
                                        }
                                    }
                                }

                            } else {
                            }
                        }
                    }
                    else {
                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(FinalSubmissionAssignment_Activity.this, SweetAlertDialog.ERROR_TYPE);
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
            }
            @Override
            public void onFailure(Call<MyAssignment> call, Throwable t) {
                call.cancel();
                Toast.makeText(FinalSubmissionAssignment_Activity.this, "fail " + t, Toast.LENGTH_LONG).show();
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
            TotalSubmittedCases = TotalSubmittedCases + totalCasesData.getTotalCases();
        }
        assignmentBinding.textViewTotalSubmittedCases.setText(String.valueOf(TotalSubmittedCases));
        assignmentBinding.textViewTotalPendingCases.setText(String.valueOf(TotalPendingCases));
        visus_dataSource.close();
    }

    private void callListener() {
        assignmentBinding.spinnerSelectVisusService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {
                    visusService = String.valueOf(adapterView.getItemAtPosition(position));
                    visusServiceID = IDsVisusServiceList.get(position - 1);
                    if (ConnectionUtility.isConnected(FinalSubmissionAssignment_Activity.this)) {
                        if (visusServiceID.equalsIgnoreCase("4")) {
                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(FinalSubmissionAssignment_Activity.this, SweetAlertDialog.WARNING_TYPE);
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
                            Toast.makeText(FinalSubmissionAssignment_Activity.this, "Work in progress...", Toast.LENGTH_SHORT).show();
                        } else {

                            getMyAssignment(visusServiceID);
                        }
                    } else {
                        ConnectionUtility.AlertDialogForNoConnectionAvaialble(FinalSubmissionAssignment_Activity.this);
                    }
                    //getMyAssignmentAccordingTOServiceID(visusServiceID);
                } else {
                    visusService = "Select Service";
                    visusServiceID = "";
                    if (ConnectionUtility.isConnected(FinalSubmissionAssignment_Activity.this)) {
                        getTotalCases();
                    } else {
                        ConnectionUtility.AlertDialogForNoConnectionAvaialble(FinalSubmissionAssignment_Activity.this);
                    }
                    assignmentBinding.recylerViewMyAssignment.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void addSpinnerAdapter(SearchableSpinner spinner, List<String> listModels) {
        MySpinnerAdapter dataAdapter = new MySpinnerAdapter(FinalSubmissionAssignment_Activity.this, android.R.layout.simple_spinner_item, listModels);
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
        Intent intent = new Intent(FinalSubmissionAssignment_Activity.this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}

