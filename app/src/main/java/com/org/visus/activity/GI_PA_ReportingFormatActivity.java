package com.org.visus.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.org.visus.R;
import com.org.visus.apis.ApiClient;
import com.org.visus.apis.ApiService;
import com.org.visus.apis.ErrorLogAPICall;
import com.org.visus.databinding.ActivityGiPaReportingFormatBinding;
import com.org.visus.holdgassessment.actvity.FinalSubmissionAssignmentHoldActivity;
import com.org.visus.models.GI_PAResponse;
import com.org.visus.models.GiPAInsuCheckList;
import com.org.visus.models.MyAssignment;
import com.org.visus.models.requset.GI_PA;
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

public class GI_PA_ReportingFormatActivity extends AppCompatActivity {

    List<GI_PA.GI_PAData> lstGi_paDataList = new ArrayList<>();
    String Token;
    ApiService apiService;
    ActivityGiPaReportingFormatBinding activityGiPaReportingFormatBinding;
    Bundle bundle;
    ArrayList<MyAssignment.MyAssignmentData> data;
    String VisusService, VisusServiceID, AssessmentType;
    ArrayList<GiPAInsuCheckList.GiPAInsuCheckListData> giPAInsuCheckListData;
    Boolean IsDulyFilledClaimForm = false, AttestedDeathCertificate = false, AttestedPostMortem = false, CancelledChequeWithClaimant = false, RegistrationCopyOfVehicle = false, /*DrivingLicenseOfDriver = false,*/
            IndemnityCumDeclarationBond = false, AMLDocuments = false, NomineeDeclarationDocuments = false, CaseSpecificDocuments = false, PoliceFinalChargeSheetCourtFinalOrder = false, MedicalCauseDeathCertificateIssued = false, HospitalizationPaperTreatmentPaper = false, PastConsultationPaper = false, NeighbourStatementAlongWithKYC = false, ContactNumberOfPersonWhoMeetDuringSpotVisit = false, IVPhotosAlongWithEngineChassisNumber = false, SpotVerificationDetail = false, IONumberAndName = false, ReasonForNoFIRN0PMRPanchnama = false, DulyFilledClaimFormInjured = false, DisabilityCertificateStatingPercentageDisablement = false, FIRMLC = false, IndemnityCumDeclarationBondRs100StampPaper = false, RegistrationCopyVehicle = false, DrivingLicenseDriverInjured = false, HospitalTreatmentPapersEssentialConfirmation = false, ColorPhotographInjuredReflectingDisability = false, PhotoIDProofInjured = false, CancelledChequePayeeName = false, AMLDocumentsInjured = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGiPaReportingFormatBinding = ActivityGiPaReportingFormatBinding.inflate(getLayoutInflater());
        setContentView(activityGiPaReportingFormatBinding.getRoot());
        bundle = getIntent().getExtras();
        if (bundle != null) {
            giPAInsuCheckListData = (ArrayList<GiPAInsuCheckList.GiPAInsuCheckListData>) getIntent().getSerializableExtra("GiPACheckListData");
            data = (ArrayList<MyAssignment.MyAssignmentData>) getIntent().getSerializableExtra("Data");
            VisusService = bundle.getString("VisusService", "");
            VisusServiceID = bundle.getString("VisusServiceID", "");
            AssessmentType = bundle.getString("AssessmentType", "");
        }

        callListener();
    }

    private void callListener() {
        activityGiPaReportingFormatBinding.buttonSubmitGIPADetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GI_PA_ReportingFormatActivity.this);
                builder.setTitle("Confirmation!");
                builder.setMessage("After submitting your checklist, Claim will be removed from the dashboard.Are you sure want to submit?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        GI_PA gi_pa = new GI_PA();
                        gi_pa.setGiPA_InvInsuranceRelID(Integer.valueOf(data.get(0).getInvInsuranceRelID()));
                        gi_pa.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                        gi_pa.setTat(data.get(0).gettATForInvestigator());
                        gi_pa.setInvestigatorID(data.get(0).getInvestigatorObj().getInvId());
                        gi_pa.setGeneralInsuranceID(data.get(0).getInsuranceDataID());
                        gi_pa.setGiPACheckListDataSaved(false);
                        gi_pa.setExceptionIfAny("");

                        GI_PA.GI_PAData gi_paData = null;
                        lstGi_paDataList.clear();
                        for (int i = 0; i < giPAInsuCheckListData.size(); i++) {
                            gi_paData = new GI_PA.GI_PAData();
                            gi_paData.setInvGiPACheckListID(-1);
                            gi_paData.setInvGiPACheckListHeadID(giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID());
                            gi_paData.setInvGiPACheckListInvID(data.get(0).getInvestigatorObj().getInvId());
                            gi_paData.setInvGiPACheckListGeneralInsuID(data.get(0).getInsuranceDataID());
                            gi_paData.setInvGiPACheckListSubmittedOnDateTime(getCurrentDateTime());
                            gi_paData.setEntryByUserID(-1);
                            gi_paData.setEntryOnDate(getCurrentDateTime());
                            if (AssessmentType != null && !AssessmentType.equalsIgnoreCase("") && AssessmentType.equalsIgnoreCase("Hold")) {
                                gi_paData.setHoldCase(true);
                            } else {
                                gi_paData.setHoldCase(false);
                            }
                            if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 1) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextDulyFilledClaimForm.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextDulyFilledClaimForm.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(IsDulyFilledClaimForm);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextDulyFilledClaimForm.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextDulyFilledClaimForm.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextDulyFilledClaimForm.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextDulyFilledClaimForm.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 2) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextAttestedDeathCertificate.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextAttestedDeathCertificate.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(AttestedDeathCertificate);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextAttestedDeathCertificate.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextAttestedDeathCertificate.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextAttestedDeathCertificate.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextAttestedDeathCertificate.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 3) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextAttestedPostMortem.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextAttestedPostMortem.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(AttestedPostMortem);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextAttestedPostMortem.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextAttestedPostMortem.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextAttestedPostMortem.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextAttestedPostMortem.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 4) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextCancelledChequeWithClaimant.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextCancelledChequeWithClaimant.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(CancelledChequeWithClaimant);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextCancelledChequeWithClaimant.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextCancelledChequeWithClaimant.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextCancelledChequeWithClaimant.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextCancelledChequeWithClaimant.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 5) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextRegistrationCopyOfVehicle.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextRegistrationCopyOfVehicle.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(RegistrationCopyOfVehicle);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextRegistrationCopyOfVehicle.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextRegistrationCopyOfVehicle.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextRegistrationCopyOfVehicle.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextRegistrationCopyOfVehicle.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 6) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextIndemnityCumDeclarationBond.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextIndemnityCumDeclarationBond.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(IndemnityCumDeclarationBond);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextIndemnityCumDeclarationBond.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextIndemnityCumDeclarationBond.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextIndemnityCumDeclarationBond.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextIndemnityCumDeclarationBond.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 7) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextAMLDocuments.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextAMLDocuments.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(AMLDocuments);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextAMLDocuments.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextAMLDocuments.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextAMLDocuments.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextAMLDocuments.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 8) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextNomineeDeclarationDocuments.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextNomineeDeclarationDocuments.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(NomineeDeclarationDocuments);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextNomineeDeclarationDocuments.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextNomineeDeclarationDocuments.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextNomineeDeclarationDocuments.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextNomineeDeclarationDocuments.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 9) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextCaseSpecificDocuments.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextCaseSpecificDocuments.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(CaseSpecificDocuments);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextCaseSpecificDocuments.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextCaseSpecificDocuments.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextCaseSpecificDocuments.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextCaseSpecificDocuments.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 10) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextPoliceFinalChargeSheetCourtFinalOrder.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextPoliceFinalChargeSheetCourtFinalOrder.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(PoliceFinalChargeSheetCourtFinalOrder);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextPoliceFinalChargeSheetCourtFinalOrder.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextPoliceFinalChargeSheetCourtFinalOrder.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextPoliceFinalChargeSheetCourtFinalOrder.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextPoliceFinalChargeSheetCourtFinalOrder.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 11) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextMedicalCauseDeathCertificateIssued.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextMedicalCauseDeathCertificateIssued.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(MedicalCauseDeathCertificateIssued);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextMedicalCauseDeathCertificateIssued.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextMedicalCauseDeathCertificateIssued.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextMedicalCauseDeathCertificateIssued.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextMedicalCauseDeathCertificateIssued.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 12) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextHospitalizationPaperTreatmentPaper.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextHospitalizationPaperTreatmentPaper.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(HospitalizationPaperTreatmentPaper);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextHospitalizationPaperTreatmentPaper.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextHospitalizationPaperTreatmentPaper.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextHospitalizationPaperTreatmentPaper.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextHospitalizationPaperTreatmentPaper.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 13) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextPastConsultationPaper.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextPastConsultationPaper.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(PastConsultationPaper);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextPastConsultationPaper.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextPastConsultationPaper.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextPastConsultationPaper.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextPastConsultationPaper.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 14) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextNeighbourStatementAlongWithKYC.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextNeighbourStatementAlongWithKYC.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(NeighbourStatementAlongWithKYC);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextNeighbourStatementAlongWithKYC.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextNeighbourStatementAlongWithKYC.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextNeighbourStatementAlongWithKYC.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextNeighbourStatementAlongWithKYC.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 15) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextContactNumberOfPersonWhoMeetDuringSpotVisit.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextContactNumberOfPersonWhoMeetDuringSpotVisit.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(ContactNumberOfPersonWhoMeetDuringSpotVisit);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextContactNumberOfPersonWhoMeetDuringSpotVisit.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextContactNumberOfPersonWhoMeetDuringSpotVisit.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextContactNumberOfPersonWhoMeetDuringSpotVisit.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextContactNumberOfPersonWhoMeetDuringSpotVisit.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 16) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextIVPhotosAlongWithEngineChassisNumber.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextIVPhotosAlongWithEngineChassisNumber.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(IVPhotosAlongWithEngineChassisNumber);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextIVPhotosAlongWithEngineChassisNumber.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextIVPhotosAlongWithEngineChassisNumber.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextIVPhotosAlongWithEngineChassisNumber.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextIVPhotosAlongWithEngineChassisNumber.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 17) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextSpotVerificationDetail.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextDulyFilledClaimForm.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(SpotVerificationDetail);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextSpotVerificationDetail.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextDulyFilledClaimForm.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextSpotVerificationDetail.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextDulyFilledClaimForm.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 18) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextIONumberAndName.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextIONumberAndName.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(IONumberAndName);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextIONumberAndName.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextIONumberAndName.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextIONumberAndName.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextIONumberAndName.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 19) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextReasonForNoFIRN0PMRPanchnama.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextReasonForNoFIRN0PMRPanchnama.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(ReasonForNoFIRN0PMRPanchnama);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextReasonForNoFIRN0PMRPanchnama.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextReasonForNoFIRN0PMRPanchnama.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextReasonForNoFIRN0PMRPanchnama.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextReasonForNoFIRN0PMRPanchnama.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 20) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextDulyFilledClaimFormInjured.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextDulyFilledClaimFormInjured.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(DulyFilledClaimFormInjured);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextDulyFilledClaimFormInjured.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextDulyFilledClaimFormInjured.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextDulyFilledClaimFormInjured.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextDulyFilledClaimFormInjured.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 21) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextDisabilityCertificateStatingPercentageDisablement.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextDisabilityCertificateStatingPercentageDisablement.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(DisabilityCertificateStatingPercentageDisablement);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextDisabilityCertificateStatingPercentageDisablement.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextDisabilityCertificateStatingPercentageDisablement.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextDisabilityCertificateStatingPercentageDisablement.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextDisabilityCertificateStatingPercentageDisablement.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 22) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextFIRMLC.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextFIRMLC.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(FIRMLC);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextFIRMLC.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextFIRMLC.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextFIRMLC.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextFIRMLC.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 23) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextIndemnityCumDeclarationBond.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextIndemnityCumDeclarationBond.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(IndemnityCumDeclarationBondRs100StampPaper);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextIndemnityCumDeclarationBond.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextIndemnityCumDeclarationBond.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextIndemnityCumDeclarationBond.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextIndemnityCumDeclarationBond.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 24) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextRegistrationCopyVehicle.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextRegistrationCopyVehicle.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(RegistrationCopyVehicle);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextRegistrationCopyVehicle.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextRegistrationCopyVehicle.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextRegistrationCopyVehicle.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextRegistrationCopyVehicle.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 25) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextDrivingLicenseDriverInjured.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextDrivingLicenseDriverInjured.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(DrivingLicenseDriverInjured);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextDrivingLicenseDriverInjured.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextDrivingLicenseDriverInjured.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextDrivingLicenseDriverInjured.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextDrivingLicenseDriverInjured.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 26) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextHospitalTreatmentPapersEssentialConfirmation.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextHospitalTreatmentPapersEssentialConfirmation.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(HospitalTreatmentPapersEssentialConfirmation);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextHospitalTreatmentPapersEssentialConfirmation.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextHospitalTreatmentPapersEssentialConfirmation.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextHospitalTreatmentPapersEssentialConfirmation.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextHospitalTreatmentPapersEssentialConfirmation.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 27) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextColorPhotographInjuredReflectingDisability.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextColorPhotographInjuredReflectingDisability.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(ColorPhotographInjuredReflectingDisability);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextColorPhotographInjuredReflectingDisability.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextColorPhotographInjuredReflectingDisability.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextColorPhotographInjuredReflectingDisability.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextColorPhotographInjuredReflectingDisability.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 28) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextPhotoIDProofInjured.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextPhotoIDProofInjured.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(PhotoIDProofInjured);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextPhotoIDProofInjured.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextPhotoIDProofInjured.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextPhotoIDProofInjured.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextPhotoIDProofInjured.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 29) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextCancelledChequePayeeName.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextCancelledChequePayeeName.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(CancelledChequePayeeName);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextCancelledChequePayeeName.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextCancelledChequePayeeName.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextCancelledChequePayeeName.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextCancelledChequePayeeName.getText().toString().trim() : "");
                            } else if (giPAInsuCheckListData.get(i).getInvGiPACheckListHeadID() == 30) {
                                gi_paData.setInvGiPACheckListRemark(activityGiPaReportingFormatBinding.editTextAMLDocumentsInjured.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextAMLDocumentsInjured.getText().toString().trim() : "");
                                gi_paData.setCheckListHeadDataAvailable(AMLDocumentsInjured);
                                gi_paData.setInvGiPACheckListYes(activityGiPaReportingFormatBinding.editTextAMLDocumentsInjured.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextAMLDocumentsInjured.getText().toString().trim() : "");
                                gi_paData.setInvGiPACheckListNo(activityGiPaReportingFormatBinding.editTextAMLDocumentsInjured.getText().toString().trim() != null ? activityGiPaReportingFormatBinding.editTextAMLDocumentsInjured.getText().toString().trim() : "");
                            }
                            lstGi_paDataList.add(gi_paData);
                        }
                        gi_pa.setLstInvGiPACheckList(lstGi_paDataList);

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        String JSONObject = gson.toJson(gi_pa);
                        postGI_PAData(gi_pa);
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


        activityGiPaReportingFormatBinding.rgAttestedDeathCertificate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_AttestedDeathCertificateYes:
                        AttestedDeathCertificate = true;
                        break;
                    case R.id.rb_AttestedDeathCertificateNo:
                        AttestedDeathCertificate = false;
                        break;
                }
            }
        });

        activityGiPaReportingFormatBinding.rgAttestedPostMortem.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_AttestedPostMortemYes:
                        AttestedPostMortem = true;
                        break;
                    case R.id.rb_AttestedPostMortemNo:
                        AttestedPostMortem = false;
                        break;
                }
            }
        });

        activityGiPaReportingFormatBinding.rgCancelledChequeWithClaimant.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_CancelledChequeWithClaimantYes:
                        CancelledChequeWithClaimant = true;
                        break;
                    case R.id.rb_CancelledChequeWithClaimantNo:
                        CancelledChequeWithClaimant = false;
                        break;
                }
            }
        });

        activityGiPaReportingFormatBinding.rgRegistrationCopyOfVehicle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_RegistrationCopyOfVehicleYes:
                        RegistrationCopyOfVehicle = true;
                        break;
                    case R.id.rb_RegistrationCopyOfVehicleNo:
                        RegistrationCopyOfVehicle = false;
                        break;
                }
            }
        });

        /*activityGiPaReportingFormatBinding.rgDrivingLicenseOfDriver.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_DrivingLicenseOfDriverYes:
                        DrivingLicenseOfDriver = true;
                        break;
                    case R.id.rb_DrivingLicenseOfDriverNo:
                        DrivingLicenseOfDriver = false;
                        break;
                }
            }
        });*/

        activityGiPaReportingFormatBinding.rgIndemnityCumDeclarationBond.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_IndemnityCumDeclarationBondYes:
                        IndemnityCumDeclarationBond = true;
                        break;
                    case R.id.rb_IndemnityCumDeclarationBondNo:
                        IndemnityCumDeclarationBond = false;
                        break;
                }
            }
        });

        activityGiPaReportingFormatBinding.rgAMLDocuments.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_AMLDocumentsYes:
                        AMLDocuments = true;
                        break;
                    case R.id.rb_AMLDocumentsNo:
                        AMLDocuments = false;
                        break;
                }
            }
        });

        activityGiPaReportingFormatBinding.rgNomineeDeclarationDocuments.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_NomineeDeclarationDocumentsYes:
                        NomineeDeclarationDocuments = true;
                        break;
                    case R.id.rb_NomineeDeclarationDocumentsNo:
                        NomineeDeclarationDocuments = false;
                        break;
                }
            }
        });

        activityGiPaReportingFormatBinding.rgCaseSpecificDocuments.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_CaseSpecificDocumentsYes:
                        CaseSpecificDocuments = true;
                        break;
                    case R.id.rb_CaseSpecificDocumentsNo:
                        CaseSpecificDocuments = false;
                        break;
                }
            }
        });

        activityGiPaReportingFormatBinding.rgPoliceFinalChargeSheetCourtFinalOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_PoliceFinalChargeSheetCourtFinalOrderYes:
                        PoliceFinalChargeSheetCourtFinalOrder = true;
                        break;
                    case R.id.rb_PoliceFinalChargeSheetCourtFinalOrderNo:
                        PoliceFinalChargeSheetCourtFinalOrder = false;
                        break;
                }
            }
        });

        activityGiPaReportingFormatBinding.rgMedicalCauseDeathCertificateIssued.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_MedicalCauseDeathCertificateIssuedYes:
                        MedicalCauseDeathCertificateIssued = true;
                        break;
                    case R.id.rb_MedicalCauseDeathCertificateIssuedNo:
                        MedicalCauseDeathCertificateIssued = false;
                        break;
                }
            }
        });

        activityGiPaReportingFormatBinding.rgHospitalizationPaperTreatmentPaper.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_HospitalizationPaperTreatmentPaperYes:
                        HospitalizationPaperTreatmentPaper = true;
                        break;
                    case R.id.rb_HospitalizationPaperTreatmentPaperNo:
                        HospitalizationPaperTreatmentPaper = false;
                        break;
                }
            }
        });
        activityGiPaReportingFormatBinding.rgPastConsultationPaper.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_PastConsultationPaperYes:
                        PastConsultationPaper = true;
                        break;
                    case R.id.rb_PastConsultationPaperNo:
                        PastConsultationPaper = false;
                        break;
                }
            }
        });
        activityGiPaReportingFormatBinding.rgNeighbourStatementAlongWithKYC.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_NeighbourStatementAlongWithKYCYes:
                        NeighbourStatementAlongWithKYC = true;
                        break;
                    case R.id.rb_NeighbourStatementAlongWithKYCNo:
                        NeighbourStatementAlongWithKYC = false;
                        break;
                }
            }
        });
        activityGiPaReportingFormatBinding.rgContactNumberOfPersonWhoMeetDuringSpotVisit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_ContactNumberOfPersonWhoMeetDuringSpotVisitYes:
                        ContactNumberOfPersonWhoMeetDuringSpotVisit = true;
                        break;
                    case R.id.rb_ContactNumberOfPersonWhoMeetDuringSpotVisitNo:
                        ContactNumberOfPersonWhoMeetDuringSpotVisit = false;
                        break;
                }
            }
        });
        activityGiPaReportingFormatBinding.rgIVPhotosAlongWithEngineChassisNumber.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_IVPhotosAlongWithEngineChassisNumberYes:
                        IVPhotosAlongWithEngineChassisNumber = true;
                        break;
                    case R.id.rb_IVPhotosAlongWithEngineChassisNumberNo:
                        IVPhotosAlongWithEngineChassisNumber = false;
                        break;
                }
            }
        });
        activityGiPaReportingFormatBinding.rgSpotVerificationDetail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_SpotVerificationDetailYes:
                        SpotVerificationDetail = true;
                        break;
                    case R.id.rb_SpotVerificationDetailNo:
                        SpotVerificationDetail = false;
                        break;
                }
            }
        });
        activityGiPaReportingFormatBinding.rgIONumberAndName.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_IONumberAndNameYes:
                        IONumberAndName = true;
                        break;
                    case R.id.rb_IONumberAndNameNo:
                        IONumberAndName = false;
                        break;
                }
            }
        });
        activityGiPaReportingFormatBinding.rgReasonForNoFIRN0PMRPanchnama.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_ReasonForNoFIR_N0PMR_PanchnamaYes:
                        ReasonForNoFIRN0PMRPanchnama = true;
                        break;
                    case R.id.rb_ReasonForNoFIR_N0PMR_PanchnamaNo:
                        ReasonForNoFIRN0PMRPanchnama = false;
                        break;
                }
            }
        });
        activityGiPaReportingFormatBinding.rgDulyFilledClaimFormInjured.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_DulyFilledClaimForm_InjuredYes:
                        DulyFilledClaimFormInjured = true;
                        break;
                    case R.id.rb_DulyFilledClaimForm_InjuredNo:
                        DulyFilledClaimFormInjured = false;
                        break;
                }
            }
        });
        activityGiPaReportingFormatBinding.rgDisabilityCertificateStatingPercentageDisablement.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_DisabilityCertificateStatingPercentageDisablementYes:
                        DisabilityCertificateStatingPercentageDisablement = true;
                        break;
                    case R.id.rb_DisabilityCertificateStatingPercentageDisablementNo:
                        DisabilityCertificateStatingPercentageDisablement = false;
                        break;
                }
            }
        });
        activityGiPaReportingFormatBinding.rgFIRMLC.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_FIR_MLCYes:
                        FIRMLC = true;
                        break;
                    case R.id.rb_FIR_MLCNo:
                        FIRMLC = false;
                        break;
                }
            }
        });
        activityGiPaReportingFormatBinding.rgIndemnityCumDeclarationBondRs100StampPaper.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_IndemnityCumDeclarationBondRs_100_StampPaperYes:
                        IndemnityCumDeclarationBondRs100StampPaper = true;
                        break;
                    case R.id.rb_IndemnityCumDeclarationBondRs_100_StampPaperNo:
                        IndemnityCumDeclarationBondRs100StampPaper = false;
                        break;
                }
            }
        });
        activityGiPaReportingFormatBinding.rgRegistrationCopyVehicle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_RegistrationCopyVehicleYes:
                        RegistrationCopyVehicle = true;
                        break;
                    case R.id.rb_RegistrationCopyVehicleNo:
                        RegistrationCopyVehicle = false;
                        break;
                }
            }
        });
        activityGiPaReportingFormatBinding.rgDrivingLicenseDriverInjured.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_DrivingLicenseDriverInjuredYes:
                        DrivingLicenseDriverInjured = true;
                        break;
                    case R.id.rb_DrivingLicenseDriverInjuredNo:
                        DrivingLicenseDriverInjured = false;
                        break;
                }
            }
        });
        activityGiPaReportingFormatBinding.rgHospitalTreatmentPapersEssentialConfirmation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_HospitalTreatmentPapersEssentialConfirmationYes:
                        HospitalTreatmentPapersEssentialConfirmation = true;
                        break;
                    case R.id.rb_HospitalTreatmentPapersEssentialConfirmationNo:
                        HospitalTreatmentPapersEssentialConfirmation = false;
                        break;
                }
            }
        });
        activityGiPaReportingFormatBinding.rgColorPhotographInjuredReflectingDisability.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_ColorPhotographInjuredReflectingDisabilityYes:
                        ColorPhotographInjuredReflectingDisability = true;
                        break;
                    case R.id.rb_ColorPhotographInjuredReflectingDisabilityNo:
                        ColorPhotographInjuredReflectingDisability = false;
                        break;
                }
            }
        });
        activityGiPaReportingFormatBinding.rgPhotoIDProofInjured.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_PhotoIDProofInjuredYes:
                        PhotoIDProofInjured = true;
                        break;
                    case R.id.rb_PhotoIDProofInjuredNo:
                        PhotoIDProofInjured = false;
                        break;
                }
            }
        });
        activityGiPaReportingFormatBinding.rgCancelledChequePayeeName.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_CancelledChequePayeeNameYes:
                        CancelledChequePayeeName = true;
                        break;
                    case R.id.rb_CancelledChequePayeeNameNo:
                        CancelledChequePayeeName = false;
                        break;
                }
            }
        });
        activityGiPaReportingFormatBinding.rgAMLDocumentsInjured.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_AMLDocuments_InjuredYes:
                        AMLDocumentsInjured = true;
                        break;
                    case R.id.rb_AMLDocuments_InjuredNo:
                        AMLDocumentsInjured = false;
                        break;
                }
            }
        });
    }

    public void goBack(View view) {
        finish();
    }

    private void postGI_PAData(GI_PA gi_pa) {
        ProgressDialog dialog = ProgressDialog.show(GI_PA_ReportingFormatActivity.this, "Loading", "Please wait...", true);
        apiService = ApiClient.getClient(this).create(ApiService.class);
        if (ConnectionUtility.isConnected(GI_PA_ReportingFormatActivity.this)) {
            Token = PrefUtils.getFromPrefs(GI_PA_ReportingFormatActivity.this, PrefUtils.Token);
            Call<GI_PAResponse> lifeInsuranceCheckListCall = apiService.postGI_PA("Bearer " + Token, gi_pa);
            lifeInsuranceCheckListCall.enqueue(new Callback<GI_PAResponse>() {
                @Override
                public void onResponse(Call<GI_PAResponse> call, Response<GI_PAResponse> response) {
                    if (response.isSuccessful()) {
                        dialog.dismiss();

                        if (response.body() != null) {
                            GI_PAResponse giOdCheckList = response.body();
                            if (giOdCheckList.getStatusCode() == 200 && giOdCheckList.getStatus().equalsIgnoreCase("success")) {
                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(GI_PA_ReportingFormatActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                sweetAlertDialog.setTitleText("GREAT!");
                                sweetAlertDialog.setCancelable(false);
                                sweetAlertDialog.setContentText("Your GI-PA Check list is submitted successfully");
                                sweetAlertDialog.show();
                                sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        sweetAlertDialog.dismiss();
                                        Intent intent = null;
                                        if (AssessmentType != null && !AssessmentType.equalsIgnoreCase("") && AssessmentType.equalsIgnoreCase("Hold")) {
                                            intent = new Intent(GI_PA_ReportingFormatActivity.this, FinalSubmissionAssignmentHoldActivity.class);

                                        } else {
                                            intent = new Intent(GI_PA_ReportingFormatActivity.this, FinalSubmissionAssignment_Activity.class);
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
                                Toast.makeText(getApplicationContext(), giOdCheckList.getMsg(), Toast.LENGTH_LONG).show();
                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(GI_PA_ReportingFormatActivity.this, SweetAlertDialog.ERROR_TYPE);
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
                public void onFailure(Call<GI_PAResponse> call, Throwable t) {
                    ErrorLogAPICall apiCall=new ErrorLogAPICall(GI_PA_ReportingFormatActivity.this,GI_PA_ReportingFormatActivity.this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[2].getMethodName(),t.getMessage(),"API ERROR");
                    apiCall.saveErrorLog();
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "" + t, Toast.LENGTH_LONG).show();
                }
            });
        } else {
            ConnectionUtility.AlertDialogForNoConnectionAvaialble(GI_PA_ReportingFormatActivity.this);
        }
    }

    public String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("en"));
        return sdf.format(new Date());
    }
}