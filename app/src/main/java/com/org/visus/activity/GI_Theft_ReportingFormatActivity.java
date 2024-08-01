package com.org.visus.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.org.visus.apis.ApiClient;
import com.org.visus.apis.ApiService;
import com.org.visus.databinding.ActivityGiTheftReportingFormatBinding;
import com.org.visus.holdgassessment.actvity.FinalSubmissionAssignmentHoldActivity;
import com.org.visus.models.GI_TheftResponse;
import com.org.visus.models.GiTheftInsuCheckList;
import com.org.visus.models.MyAssignment;
import com.org.visus.models.requset.GI_Theft;
import com.org.visus.utility.ConnectionUtility;
import com.org.visus.utility.PrefUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GI_Theft_ReportingFormatActivity extends AppCompatActivity {
    ActivityGiTheftReportingFormatBinding activityGiTheftReportingFormatBinding;
    Bundle bundle;
    ArrayList<GiTheftInsuCheckList.GiTheftInsuCheckListData> giTheftInsuCheckListData;
    ArrayList<MyAssignment.MyAssignmentData> data;
    String VisusService, VisusServiceID, AssessmentType;
    List<GI_Theft.GI_TheftData> lstGi_theftDataList = new ArrayList<>();
    String Token;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGiTheftReportingFormatBinding = ActivityGiTheftReportingFormatBinding.inflate(getLayoutInflater());
        setContentView(activityGiTheftReportingFormatBinding.getRoot());
        bundle = getIntent().getExtras();
        if (bundle != null) {
            giTheftInsuCheckListData = (ArrayList<GiTheftInsuCheckList.GiTheftInsuCheckListData>) getIntent().getSerializableExtra("GiTheftCheckListData");
            data = (ArrayList<MyAssignment.MyAssignmentData>) getIntent().getSerializableExtra("Data");
            VisusService = bundle.getString("VisusService", "");
            VisusServiceID = bundle.getString("VisusServiceID", "");
            AssessmentType = bundle.getString("AssessmentType", "");
        }
        CallListener();
    }

    private void CallListener() {
        activityGiTheftReportingFormatBinding.buttonSubmitGITheftDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GI_Theft_ReportingFormatActivity.this);
                builder.setTitle("Confirmation!");
                builder.setMessage("After submitting your checklist, Claim will be removed from the dashboard.Are you sure want to submit?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GI_Theft gi_theft = new GI_Theft();
                        gi_theft.setGiTheft_InvInsuranceRelID(Integer.parseInt(data.get(0).getInvInsuranceRelID()));
                        gi_theft.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                        gi_theft.setTat(data.get(0).gettATForInvestigator());
                        gi_theft.setInvestigatorID(data.get(0).getInvestigatorObj().getInvId());
                        gi_theft.setGeneralInsuranceID(data.get(0).getInsuranceDataID());
                        gi_theft.setGiTheftCheckListDataSaved(false);
                        gi_theft.setExceptionIfAny("");

                        GI_Theft.GI_TheftData gi_theftData = null;
                        lstGi_theftDataList.clear();
                        for (int i = 0; i < giTheftInsuCheckListData.size(); i++) {
                            gi_theftData = new GI_Theft.GI_TheftData();
                            gi_theftData.setInvGiTheftCheckListID(-1);
                            gi_theftData.setInvGiTheftCheckListHeadID(giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID());
                            gi_theftData.setInvGiTheftCheckListInvID(data.get(0).getInvestigatorObj().getInvId());
                            gi_theftData.setInvGiTheftCheckListGenInsuID(data.get(0).getInsuranceDataID());
                            gi_theftData.setInvGiTheftCheckListSubmittedOnDate(getCurrentDateTime());
                            gi_theftData.setEntryByUserID(-1);
                            gi_theftData.setEntryOnDate(getCurrentDateTime());

                            if (AssessmentType != null && !AssessmentType.equalsIgnoreCase("")
                                    && AssessmentType.equalsIgnoreCase("Hold")) {
                                gi_theftData.setHoldCase(true);
                            } else {
                                gi_theftData.setHoldCase(false);
                            }

                            if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 1) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextEClaimFormFilled.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextEClaimFormFilled.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 2) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextILTakeCareAppDownloadedAndScreenshotShared.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextILTakeCareAppDownloadedAndScreenshotShared.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 3) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextPreviousYearPolicyProvided.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextPreviousYearPolicyProvided.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 4) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextCurrentYearPolicy.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextCurrentYearPolicy.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 5) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextDelayInFIR.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextDelayInFIR.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 6) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextKeysImpressionStatement.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextKeysImpressionStatement.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 7) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextinsuredForgotKey.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextinsuredForgotKey.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 8) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextGoogleTimelineCollected.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextGoogleTimelineCollected.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 9) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextIONumberName.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextIONumberName.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 10) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextDelayIntimationAndReason.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextDelayIntimationAndReason.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 11) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextNeighbourStatement.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextNeighbourStatement.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 12) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextSpotWitnessStatement.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextSpotWitnessStatement.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 13) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextPurchaseBill.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextPurchaseBill.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 14) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextFasTagDetailCollected.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextFasTagDetailCollected.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 15) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextGPSDetail.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextGPSDetail.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 16) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextServiceHistoryRTOIntimation.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextServiceHistoryRTOIntimation.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 17) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextLoanStatement.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextLoanStatement.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 18) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextNONRepossessionLetterFinancierMail.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextNONRepossessionLetterFinancierMail.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 19) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextInsuredName.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextInsuredName.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 20) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextInsuredPhoto.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextInsuredPhoto.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 21) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextBankPassbook.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextBankPassbook.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 22) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextRTOFormsDulySigned.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextRTOFormsDulySigned.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 23) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextIfOriginalRCStolenThenRC.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextIfOriginalRCStolenThenRC.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 24) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextIfIVOnLoanThenForm35.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextIfIVOnLoanThenForm35.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 25) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextFIRIfFIRNotFiledThenPoliceApplication.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextFIRIfFIRNotFiledThenPoliceApplication.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 26) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextIndemnityBond200SatisfactionLetter.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextIndemnityBond200SatisfactionLetter.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 27) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextUP112CallRecordCallDone.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextUP112CallRecordCallDone.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 28) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextMotoVehicleTaxReceipt.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextMotoVehicleTaxReceipt.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 29) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextPanCard.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextPanCard.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 30) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextKeyStatus.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextKeyStatus.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 31) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextInCommercialVehiclePermit.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextInCommercialVehiclePermit.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 32) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextLastUserStatementAndKYC.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextLastUserStatementAndKYC.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 33) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextDriverStatementContactDetail.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextDriverStatementContactDetail.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 34) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextCaseStatus.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextCaseStatus.getText().toString().trim() : "");
                            } else if (giTheftInsuCheckListData.get(i).getInvGiTheftCheckListHeadID() == 35) {
                                gi_theftData.setInvestigatorRemark(activityGiTheftReportingFormatBinding.editTextInvestigatorsFindings.getText().toString().trim() != null ? activityGiTheftReportingFormatBinding.editTextInvestigatorsFindings.getText().toString().trim() : "");
                            }


                            lstGi_theftDataList.add(gi_theftData);
                        }
                        gi_theft.setLstInvGiTheftCheckList(lstGi_theftDataList);
                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        String JSONObject = gson.toJson(gi_theft);
                        postGI_TheftData(gi_theft);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    private void postGI_TheftData(GI_Theft gi_theft) {
        ProgressDialog dialog = ProgressDialog.show(GI_Theft_ReportingFormatActivity.this, "Loading", "Please wait...", true);
        apiService = ApiClient.getClient(this).create(ApiService.class);
        if (ConnectionUtility.isConnected(GI_Theft_ReportingFormatActivity.this)) {
            Token = PrefUtils.getFromPrefs(GI_Theft_ReportingFormatActivity.this, PrefUtils.Token);
            Call<GI_TheftResponse> lifeInsuranceCheckListCall = apiService.postGI_Theft("Bearer " + Token, gi_theft);
            lifeInsuranceCheckListCall.enqueue(new Callback<GI_TheftResponse>() {
                @Override
                public void onResponse(Call<GI_TheftResponse> call, Response<GI_TheftResponse> response) {
                    if (response.isSuccessful()) {
                        dialog.dismiss();

                        if (response.body() != null) {
                            GI_TheftResponse githeftCheckList = response.body();
                            if (githeftCheckList.getStatusCode() == 200 && githeftCheckList.getStatus().equalsIgnoreCase("success")) {
                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(GI_Theft_ReportingFormatActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                sweetAlertDialog.setTitleText("GREAT!");
                                sweetAlertDialog.setCancelable(false);
                                sweetAlertDialog.setContentText("Your GI-Theft Check list is submitted successfully");
                                sweetAlertDialog.show();
                                sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        sweetAlertDialog.dismiss();

                                        Intent intent = null;
                                        if (AssessmentType != null && !AssessmentType.equalsIgnoreCase("") && AssessmentType.equalsIgnoreCase("Hold")) {
                                            intent = new Intent(GI_Theft_ReportingFormatActivity.this, FinalSubmissionAssignmentHoldActivity.class);
                                        } else {
                                            intent = new Intent(GI_Theft_ReportingFormatActivity.this, FinalSubmissionAssignment_Activity.class);
                                        }
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra("VisusService", VisusService);
                                        intent.putExtra("VisusServiceID", VisusServiceID);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), githeftCheckList.getMsg(), Toast.LENGTH_LONG).show();
                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(GI_Theft_ReportingFormatActivity.this, SweetAlertDialog.ERROR_TYPE);
                                sweetAlertDialog.setTitleText("SORRY!!!");
                                sweetAlertDialog.setCancelable(false);
                                sweetAlertDialog.setContentText("Your GI-PA Check list is not submitted");
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
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<GI_TheftResponse> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "" + t, Toast.LENGTH_LONG).show();
                }
            });
        } else {
            ConnectionUtility.AlertDialogForNoConnectionAvaialble(GI_Theft_ReportingFormatActivity.this);
        }
    }

    public String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("en"));
        return sdf.format(new Date());
    }
}