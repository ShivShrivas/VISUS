package com.org.visus.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.org.visus.adapters.FinalSubmissionAction_Adapter;
import com.org.visus.apis.ApiClient;
import com.org.visus.apis.ApiService;
import com.org.visus.apis.ErrorLogAPICall;
import com.org.visus.databinding.ActivityActionFinalSubmitBinding;
import com.org.visus.models.InvReqActivityFile;
import com.org.visus.models.MyAssignment;
import com.org.visus.utility.ConnectionUtility;
import com.org.visus.utility.PrefUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Action_FinalSubmit_Activity extends AppCompatActivity {
    ActivityActionFinalSubmitBinding submissionAssignmentBinding;
    ApiService apiService;
    String Token, InvestigatorID;
    Bundle bundle;
    String VisusService, VisusServiceID;
    MyAssignment.MyAssignmentData myPendingAssignmentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        submissionAssignmentBinding = ActivityActionFinalSubmitBinding.inflate(getLayoutInflater());
        setContentView(submissionAssignmentBinding.getRoot());
        bundle = getIntent().getExtras();
        if (bundle != null) {
            myPendingAssignmentData = (MyAssignment.MyAssignmentData) getIntent().getSerializableExtra("Data");
            VisusService = bundle.getString("VisusService", "");
            VisusServiceID = bundle.getString("VisusServiceID", "");
        }
        if (ConnectionUtility.isConnected(Action_FinalSubmit_Activity.this)) {
            getMyPendingAssignment(VisusServiceID);
        } else {
            ConnectionUtility.AlertDialogForNoConnectionAvaialble(Action_FinalSubmit_Activity.this);
        }
    }


    private void getMyPendingAssignment(String visusServiceID) {
        apiService = ApiClient.getClient(this).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(Action_FinalSubmit_Activity.this, PrefUtils.Token);
        InvestigatorID = PrefUtils.getFromPrefs(Action_FinalSubmit_Activity.this, PrefUtils.InvestigatorID);
        Call<InvReqActivityFile> call2 = apiService.getInvReqActivityFile("Bearer " + Token, visusServiceID, myPendingAssignmentData.getInsuranceDataID().toString(), InvestigatorID, myPendingAssignmentData.getInvInsuranceRelID());
        call2.enqueue(new Callback<InvReqActivityFile>() {
            @Override
            public void onResponse(Call<InvReqActivityFile> call, Response<InvReqActivityFile> response) {
                if (response.body() != null) {
                    InvReqActivityFile invReqActivityFile = response.body();
                    if (invReqActivityFile != null) {
                        if (invReqActivityFile.getStatus() != null && invReqActivityFile.getStatus().equalsIgnoreCase("success")) {
                            if (invReqActivityFile.getData().size() == 0) {
                                submissionAssignmentBinding.actionRecycler.setVisibility(View.GONE);
                                submissionAssignmentBinding.textViewNoRecord.setVisibility(View.VISIBLE);
                            } else {
                                submissionAssignmentBinding.actionRecycler.setVisibility(View.VISIBLE);
                                submissionAssignmentBinding.textViewNoRecord.setVisibility(View.GONE);
                                submissionAssignmentBinding.actionRecycler.setHasFixedSize(true);
                                submissionAssignmentBinding.actionRecycler.setAdapter(new FinalSubmissionAction_Adapter(Action_FinalSubmit_Activity.this, invReqActivityFile.getData(), myPendingAssignmentData));
                            }
                        } else {
                        }
                    }
                }else{
                    ErrorLogAPICall apiCall= new ErrorLogAPICall(Action_FinalSubmit_Activity.this,"FinalSubmissionAssignment_Adapter","myUploadedFile/getInvReqActivityFile", response.message()+" "+response.code(),"API Exception");
                    apiCall.saveErrorLog();
                }
            }

            @Override
            public void onFailure(Call<InvReqActivityFile> call, Throwable t) {
                call.cancel();
                Toast.makeText(Action_FinalSubmit_Activity.this, "fail " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void goBack(View view) {
        finish();
    }
}