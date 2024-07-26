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
import com.org.visus.databinding.ActivityGiOdReportingFormatBinding;
import com.org.visus.models.GI_ODResponse;
import com.org.visus.models.GiODInsuCheckList;
import com.org.visus.models.MyAssignment;
import com.org.visus.models.requset.GI_OD;
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

public class GI_OD_ReportingFormatActivity extends AppCompatActivity {
    Boolean IsWrittenStatementWithKYC = false, IsSelfieWithInsured = false, IsGoogleTimeline = false, IsVehicleRelatedPapers = false, IsOccupationDetailsLivingStatus = false, IsWrittenStatementWithKYCDLCopy = false, IsGoogleTimelineRoamingMessagePhotoPhone = false, Is8_PhotosWithWideAngle = false, WitnessVideo_Statement = false, Name_ContactNo = false, AnyOtherDetail = false, ServiceHistory = false, IsGateEntry = false, IsEstimates_Quotation = false;
    Boolean IsJobCard = false, IsPhotosFromEverySide = false, IsInsidePhotos = false, IsMeterReading = false, IsRegistration_Chassis_EngineNumber = false, IsColourOfVehicle = false, IsWrittenStatementWithKYC_OccupantVisit = false, IsPhotographsWithStatement_OccupantVisit = false, IsWrittenStatementWithKYC_TPDetails = false, IsPhotographStatementVideoRecording_TPDetails = false, IsCollectNewspaperClip = false, IsRegisterEntryDateOfAdmission = false, IsTreatmentPapersDischargeSlip = false, IsPhotographHospital = false, IsContactNoIOContactPerson = false, IsAnyOtherRelevantDetails = false, IsPayableNotPayable = false;
    String Token;
    ApiService apiService;
    List<GI_OD.GI_ODData> lstInvGiODCheckList = new ArrayList<>();
    ActivityGiOdReportingFormatBinding activityGiOdReportingFormatBinding;
    Bundle bundle;
    ArrayList<MyAssignment.MyAssignmentData> data;
    String VisusService, VisusServiceID;
    ArrayList<GiODInsuCheckList.GiODInsuCheckListData> giODInsuCheckListData;

    GI_OD.GI_ODData gi_odData0, gi_odData1, gi_odData2, gi_odData3, gi_odData4, gi_odData5, gi_odData6, gi_odData7, gi_odData8, gi_odData9, gi_odData10;
    GI_OD.GI_ODData gi_odData11, gi_odData12, gi_odData13, gi_odData14, gi_odData15, gi_odData16, gi_odData17, gi_odData18, gi_odData19, gi_odData20;
    GI_OD.GI_ODData gi_odData21, gi_odData22, gi_odData23, gi_odData24, gi_odData25, gi_odData26, gi_odData27, gi_odData28, gi_odData29, gi_odData30;
    GI_OD.GI_ODData gi_odData31, gi_odData32;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_gi_od_reporting_format);
        activityGiOdReportingFormatBinding = ActivityGiOdReportingFormatBinding.inflate(getLayoutInflater());
        setContentView(activityGiOdReportingFormatBinding.getRoot());
        bundle = getIntent().getExtras();
        if (bundle != null) {
            giODInsuCheckListData = (ArrayList<GiODInsuCheckList.GiODInsuCheckListData>) getIntent().getSerializableExtra("GiODCheckListData");
            data = (ArrayList<MyAssignment.MyAssignmentData>) getIntent().getSerializableExtra("Data");
            VisusService = bundle.getString("VisusService", "");
            VisusServiceID = bundle.getString("VisusServiceID", "");
        }
        if (giODInsuCheckListData != null) {
            gi_odData0 = new GI_OD.GI_ODData();
            gi_odData1 = new GI_OD.GI_ODData();
            gi_odData2 = new GI_OD.GI_ODData();
            gi_odData3 = new GI_OD.GI_ODData();
            gi_odData4 = new GI_OD.GI_ODData();
            gi_odData5 = new GI_OD.GI_ODData();
            gi_odData6 = new GI_OD.GI_ODData();
            gi_odData7 = new GI_OD.GI_ODData();
            gi_odData8 = new GI_OD.GI_ODData();
            gi_odData9 = new GI_OD.GI_ODData();
            gi_odData10 = new GI_OD.GI_ODData();
            gi_odData11 = new GI_OD.GI_ODData();
            gi_odData12 = new GI_OD.GI_ODData();
            gi_odData13 = new GI_OD.GI_ODData();
            gi_odData14 = new GI_OD.GI_ODData();
            gi_odData15 = new GI_OD.GI_ODData();
            gi_odData16 = new GI_OD.GI_ODData();
            gi_odData17 = new GI_OD.GI_ODData();
            gi_odData18 = new GI_OD.GI_ODData();
            gi_odData19 = new GI_OD.GI_ODData();
        }

        callListener();
    }

    private void callListener() {
        activityGiOdReportingFormatBinding.rgPayableNotPayable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_PayableNotPayable_Yes:
                        IsPayableNotPayable = true;
                        break;
                    case R.id.rb_PayableNotPayable_No:
                        IsPayableNotPayable = false;
                        break;
                }
            }
        });

        activityGiOdReportingFormatBinding.rgAnyOtherRelevantDetails.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_AnyOtherRelevantDetails_Yes:
                        IsAnyOtherRelevantDetails = true;
                        break;
                    case R.id.rb_AnyOtherRelevantDetails_No:
                        IsAnyOtherRelevantDetails = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.rgContactNoIOContactPerson.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_ContactNoIOContactPerson_Yes:
                        IsContactNoIOContactPerson = true;
                        break;
                    case R.id.rb_ContactNoIOContactPerson_No:
                        IsContactNoIOContactPerson = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.rgPhotographHospital.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_PhotographHospital_Yes:
                        IsPhotographHospital = true;
                        break;
                    case R.id.rb_PhotographHospital_No:
                        IsPhotographHospital = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.rgTreatmentPapersDischargeSlip.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_TreatmentPapersDischargeSlip_Yes:
                        IsTreatmentPapersDischargeSlip = true;
                        break;
                    case R.id.rb_TreatmentPapersDischargeSlip_No:
                        IsTreatmentPapersDischargeSlip = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.rgRegisterEntryDateOfAdmission.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_RegisterEntryDateOfAdmission_Yes:
                        IsRegisterEntryDateOfAdmission = true;
                        break;
                    case R.id.rb_RegisterEntryDateOfAdmission_No:
                        IsRegisterEntryDateOfAdmission = false;
                        break;
                }
            }
        });

        activityGiOdReportingFormatBinding.rgCollectNewspaperClip.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_CollectNewspaperClip_Yes:
                        IsCollectNewspaperClip = true;
                        break;
                    case R.id.rb_CollectNewspaperClip_No:
                        IsCollectNewspaperClip = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.rgPhotographStatementVideoRecordingTPDetails.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_PhotographStatementVideoRecording_TPDetails_Yes:
                        IsPhotographStatementVideoRecording_TPDetails = true;
                        break;
                    case R.id.rb_PhotographStatementVideoRecording_TPDetails_No:
                        IsPhotographStatementVideoRecording_TPDetails = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.rgPhotographStatementVideoRecordingTPDetails.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_PhotographStatementVideoRecording_TPDetails_Yes:
                        IsPhotographStatementVideoRecording_TPDetails = true;
                        break;
                    case R.id.rb_PhotographStatementVideoRecording_TPDetails_No:
                        IsPhotographStatementVideoRecording_TPDetails = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.rgWrittenStatementWithKYCTPDetails.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_WrittenStatementWithKYC_TPDetails_Yes:
                        IsWrittenStatementWithKYC_TPDetails = true;
                        break;
                    case R.id.rb_WrittenStatementWithKYC_TPDetails_No:
                        IsWrittenStatementWithKYC_TPDetails = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.rgPhotographsWithStatementOccupantVisit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_PhotographsWithStatement_OccupantVisit_Yes:
                        IsPhotographsWithStatement_OccupantVisit = true;
                        break;
                    case R.id.rb_PhotographsWithStatement_OccupantVisit_No:
                        IsPhotographsWithStatement_OccupantVisit = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.rgWrittenStatementWithKYCOccupantVisit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_WrittenStatementWithKYC_OccupantVisit_Yes:
                        IsWrittenStatementWithKYC_OccupantVisit = true;
                        break;
                    case R.id.rb_WrittenStatementWithKYC_OccupantVisit_No:
                        IsWrittenStatementWithKYC_OccupantVisit = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.rgWrittenStatementWithKYCOccupantVisit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_WrittenStatementWithKYC_OccupantVisit_Yes:
                        IsWrittenStatementWithKYC_OccupantVisit = true;
                        break;
                    case R.id.rb_WrittenStatementWithKYC_OccupantVisit_No:
                        IsWrittenStatementWithKYC_OccupantVisit = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.rgColourOfVehicle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_ColourOfVehicle_Yes:
                        IsColourOfVehicle = true;
                        break;
                    case R.id.rb_ColourOfVehicle_No:
                        IsColourOfVehicle = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.rgRegistrationChassisEngineNumber.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_Registration_Chassis_EngineNumber_Yes:
                        IsRegistration_Chassis_EngineNumber = true;
                        break;
                    case R.id.rb_Registration_Chassis_EngineNumber_No:
                        IsRegistration_Chassis_EngineNumber = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.rgMeterReading.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_MeterReading_Yes:
                        IsMeterReading = true;
                        break;
                    case R.id.rb_MeterReading_No:
                        IsMeterReading = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.rgInsidePhotos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_InsidePhotos_Yes:
                        IsInsidePhotos = true;
                        break;
                    case R.id.rb_InsidePhotos_No:
                        IsInsidePhotos = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.rgPhotosFromEverySide.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_PhotosFromEverySide_Yes:
                        IsPhotosFromEverySide = true;
                        break;
                    case R.id.rb_PhotosFromEverySide_No:
                        IsPhotosFromEverySide = false;
                        break;
                }
            }
        });

        activityGiOdReportingFormatBinding.rgJobCard.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_JobCard_Yes:
                        IsJobCard = true;
                        break;
                    case R.id.rb_JobCard_No:
                        IsJobCard = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.rgEstimatesQuotation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_Estimates_Quotation_Yes:
                        IsEstimates_Quotation = true;
                        break;
                    case R.id.rb_Estimates_Quotation_No:
                        IsEstimates_Quotation = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.rgGateEntry.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_GateEntry_Yes:
                        IsGateEntry = true;
                        break;
                    case R.id.rb_GateEntry_No:
                        IsGateEntry = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.rgServiceHistory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_ServiceHistory_Yes:
                        ServiceHistory = true;
                        break;
                    case R.id.rb_ServiceHistory_No:
                        ServiceHistory = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.rgAnyOtherDetail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_AnyOtherDetail_Yes:
                        AnyOtherDetail = true;
                        break;
                    case R.id.rb_AnyOtherDetail_No:
                        AnyOtherDetail = false;
                        break;
                }
            }
        });

        activityGiOdReportingFormatBinding.rgAnyOtherDetail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_AnyOtherDetail_Yes:
                        AnyOtherDetail = true;
                        break;
                    case R.id.rb_AnyOtherDetail_No:
                        AnyOtherDetail = false;
                        break;
                }
            }
        });

        activityGiOdReportingFormatBinding.rgNameContactNo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_Name_ContactNo_Yes:
                        Name_ContactNo = true;
                        break;
                    case R.id.rb_Name_ContactNo_No:
                        Name_ContactNo = false;
                        break;
                }
            }
        });

        activityGiOdReportingFormatBinding.rgWitnessVideoStatement.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_WitnessVideo_Statement_Yes:
                        WitnessVideo_Statement = true;
                        break;
                    case R.id.rb_WitnessVideo_Statement_No:
                        WitnessVideo_Statement = false;
                        break;
                }
            }
        });

        activityGiOdReportingFormatBinding.rg8PhotosWithWideAngle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_8_PhotosWithWideAngle_Yes:
                        Is8_PhotosWithWideAngle = true;
                        break;
                    case R.id.rb_8_PhotosWithWideAngle_No:
                        Is8_PhotosWithWideAngle = false;
                        break;
                }
            }
        });

        activityGiOdReportingFormatBinding.rgGoogleTimelineRoamingMessagePhotoPhone.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_GoogleTimelineRoamingMessagePhotoPhone_Yes:
                        IsGoogleTimelineRoamingMessagePhotoPhone = true;
                        break;
                    case R.id.rb_GoogleTimelineRoamingMessagePhotoPhone_No:
                        IsGoogleTimelineRoamingMessagePhotoPhone = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.rgWrittenStatementWithKYCDLCopy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_WrittenStatementWithKYCDLCopy_Yes:
                        IsWrittenStatementWithKYCDLCopy = true;
                        break;
                    case R.id.rb_WrittenStatementWithKYCDLCopy_No:
                        IsWrittenStatementWithKYCDLCopy = false;
                        break;
                }
            }
        });

        activityGiOdReportingFormatBinding.rgOccupationDetailsLivingStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_OccupationDetailsLivingStatus_Yes:
                        IsOccupationDetailsLivingStatus = true;
                        break;
                    case R.id.rb_OccupationDetailsLivingStatus_No:
                        IsOccupationDetailsLivingStatus = false;
                        break;
                }
            }
        });

        activityGiOdReportingFormatBinding.rgVehicleRelatedPapers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_VehicleRelatedPapers_Yes:
                        IsVehicleRelatedPapers = true;
                        break;
                    case R.id.rb_VehicleRelatedPapers_No:
                        IsVehicleRelatedPapers = false;
                        break;
                }
            }
        });


        activityGiOdReportingFormatBinding.rgGoogleTimeline.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_GoogleTimeline_Yes:
                        IsGoogleTimeline = true;
                        break;
                    case R.id.rb_GoogleTimeline_No:
                        IsGoogleTimeline = false;
                        break;
                }
            }
        });

        activityGiOdReportingFormatBinding.rgSelfieWithInsured.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_SelfieWithInsured_Yes:
                        IsSelfieWithInsured = true;
                        break;
                    case R.id.rb_SelfieWithInsured_No:
                        IsSelfieWithInsured = false;
                        break;
                }
            }
        });

        activityGiOdReportingFormatBinding.rgWrittenStatementWithKYC.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_WrittenStatementWithKYC_Yes:
                        IsWrittenStatementWithKYC = true;
                        break;
                    case R.id.rb_WrittenStatementWithKYC_No:
                        IsWrittenStatementWithKYC = false;
                        break;
                }
            }
        });
        activityGiOdReportingFormatBinding.buttonSubmitGIODDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GI_OD_ReportingFormatActivity.this);
                builder.setTitle("Confirmation!");
                builder.setMessage("After submitting your checklist, Claim will be removed from the dashboard.Are you sure want to submit?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        GI_OD gi_od = new GI_OD();
                        gi_od.setGiOD_InvInsuranceRelID(data.get(0).getInvInsuranceRelID());
                        gi_od.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                        gi_od.setTat(data.get(0).gettATForInvestigator());
                        gi_od.setInvestigatorID(data.get(0).getInvestigatorObj().getInvId());
                        gi_od.setGeneralInsuranceID(data.get(0).getInsuranceDataID());
                        gi_od.setGiODCheckListDataSaved(false);
                        gi_od.setExceptionIfAny("");
                        GI_OD.GI_ODData gi_odData = null;
                        lstInvGiODCheckList.clear();
                        for (int i = 0; i < giODInsuCheckListData.size(); i++) {
                            gi_odData = new GI_OD.GI_ODData();
                            gi_odData.setInvestigatorGiODCheckListID(-1);
                            gi_odData.setInvestigatorGiODCheckListInvID(data.get(0).getInvestigatorObj().getInvId());
                            gi_odData.setInvestigatorGiODCheckListGenInsuID(data.get(0).getInsuranceDataID());
                            gi_odData.setInvestigatorODVisitTypeID(giODInsuCheckListData.get(i).getInvestigatorODVisitTypeID());
                            gi_odData.setInvestigatorODVisitDocID(giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID());
                            gi_odData.setInvestigatorGiODCheckListSubmittedOnDate(getCurrentDateTime());
                            gi_odData.setInvestigatorGiODCheckListSubmittedByUserID(-1);
                            gi_odData.setInvestigatorGiODCheckListEntryOnDate(getCurrentDateTime());
                            if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 1) {
                                gi_odData.setGIInvCheckListStatus(IsWrittenStatementWithKYC);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextWrittenStatementWithKYC.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextWrittenStatementWithKYC.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 2) {
                                gi_odData.setGIInvCheckListStatus(IsSelfieWithInsured);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextSelfieWithInsured.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextSelfieWithInsured.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 3) {
                                gi_odData.setGIInvCheckListStatus(IsGoogleTimeline);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextGoogleTimeline.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextGoogleTimeline.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 4) {
                                gi_odData.setGIInvCheckListStatus(IsVehicleRelatedPapers);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextVehicleRelatedPapers.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextVehicleRelatedPapers.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 5) {
                                gi_odData.setGIInvCheckListStatus(IsOccupationDetailsLivingStatus);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextOccupationDetailsLivingStatus.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextOccupationDetailsLivingStatus.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 6) {
                                gi_odData.setGIInvCheckListStatus(IsWrittenStatementWithKYCDLCopy);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextWrittenStatementWithKYCDLCopy.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextWrittenStatementWithKYCDLCopy.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 7) {
                                gi_odData.setGIInvCheckListStatus(IsGoogleTimelineRoamingMessagePhotoPhone);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextGoogleTimelineRoamingMessagePhotoPhone.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextGoogleTimelineRoamingMessagePhotoPhone.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 8) {
                                gi_odData.setGIInvCheckListStatus(Is8_PhotosWithWideAngle);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editText8PhotosWithWideAngle.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editText8PhotosWithWideAngle.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 9) {
                                gi_odData.setGIInvCheckListStatus(WitnessVideo_Statement);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextWitnessVideoStatement.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextWitnessVideoStatement.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 10) {
                                gi_odData.setGIInvCheckListStatus(Name_ContactNo);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextNameContactNo.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextNameContactNo.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 11) {
                                gi_odData.setGIInvCheckListStatus(AnyOtherDetail);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextAnyOtherDetail.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextAnyOtherDetail.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 12) {
                                gi_odData.setGIInvCheckListStatus(ServiceHistory);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextServiceHistory.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextServiceHistory.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 13) {
                                gi_odData.setGIInvCheckListStatus(IsGateEntry);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextGateEntry.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextGateEntry.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 14) {
                                gi_odData.setGIInvCheckListStatus(IsEstimates_Quotation);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextEstimatesQuotation.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextEstimatesQuotation.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 15) {
                                gi_odData.setGIInvCheckListStatus(IsJobCard);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextJobCard.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextJobCard.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 16) {
                                gi_odData.setGIInvCheckListStatus(false);////Label
                                gi_odData.setGiInvCheckListReason("");////Label
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 17) {
                                gi_odData.setGIInvCheckListStatus(IsPhotosFromEverySide);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextPhotosFromEverySide.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextPhotosFromEverySide.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 18) {
                                gi_odData.setGIInvCheckListStatus(IsInsidePhotos);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextInsidePhotos.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextInsidePhotos.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 19) {
                                gi_odData.setGIInvCheckListStatus(IsMeterReading);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextMeterReading.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextMeterReading.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 20) {
                                gi_odData.setGIInvCheckListStatus(IsRegistration_Chassis_EngineNumber);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextRegistrationChassisEngineNumber.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextRegistrationChassisEngineNumber.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 21) {
                                gi_odData.setGIInvCheckListStatus(IsColourOfVehicle);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextColourOfVehicle.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextColourOfVehicle.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 22) {
                                gi_odData.setGIInvCheckListStatus(IsWrittenStatementWithKYC_OccupantVisit);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextWrittenStatementWithKYCOccupantVisit.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextWrittenStatementWithKYCOccupantVisit.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 23) {
                                gi_odData.setGIInvCheckListStatus(IsPhotographsWithStatement_OccupantVisit);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextPhotographsWithStatement.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextPhotographsWithStatement.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 24) {
                                gi_odData.setGIInvCheckListStatus(IsWrittenStatementWithKYC_TPDetails);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextWrittenStatementWithKYCTPDetails.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextWrittenStatementWithKYCTPDetails.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 25) {
                                gi_odData.setGIInvCheckListStatus(IsPhotographStatementVideoRecording_TPDetails);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextPhotographStatementVideoRecordingTPDetails.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextPhotographStatementVideoRecordingTPDetails.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 26) {
                                gi_odData.setGIInvCheckListStatus(IsCollectNewspaperClip);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextCollectNewspaperClip.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextCollectNewspaperClip.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 28) {
                                gi_odData.setGIInvCheckListStatus(IsRegisterEntryDateOfAdmission);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextRegisterEntryDateOfAdmission.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextRegisterEntryDateOfAdmission.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 29) {
                                gi_odData.setGIInvCheckListStatus(IsTreatmentPapersDischargeSlip);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextTreatmentPapersDischargeSlip.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextTreatmentPapersDischargeSlip.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 30) {
                                gi_odData.setGIInvCheckListStatus(IsPhotographHospital);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextPhotographHospital.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextPhotographHospital.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 31) {
                                gi_odData.setGIInvCheckListStatus(IsContactNoIOContactPerson);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextContactNoIOContactPerson.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextContactNoIOContactPerson.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 34) {
                                gi_odData.setGIInvCheckListStatus(IsAnyOtherRelevantDetails);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextAnyOtherRelevantDetails.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextAnyOtherRelevantDetails.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 32) {
                                gi_odData.setGIInvCheckListStatus(IsPayableNotPayable);
                                gi_odData.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextPayableNotPayable.getText().toString().trim() != null ? activityGiOdReportingFormatBinding.editTextPayableNotPayable.getText().toString().trim() : "");
                            } else if (giODInsuCheckListData.get(i).getInvestigatorODVisitDocumentID() == 33) {
                                gi_odData.setGIInvCheckListStatus(false);////Label
                                gi_odData.setGiInvCheckListReason("");////Label
                            }
                            lstInvGiODCheckList.add(gi_odData);
                        }


                        gi_od.setLstInvGiODCheckList(lstInvGiODCheckList);

                        ////////////////////////////////////////////Start Region Insured Visit//////////////////////////////////////////////////////////////////////
                        /*gi_odData0.setInvestigatorGiODCheckListID(-1);
                        gi_odData0.setInvestigatorGiODCheckListInvID(data.get(0).getInsuranceDataID());
                        gi_odData0.setInvestigatorGiODCheckListGenInsuID(data.get(0).getInvestigatorObj().getInvId());
                        gi_odData0.setInvestigatorODVisitTypeID(giODInsuCheckListData.get(0).getInvestigatorODVisitTypeID());
                        gi_odData0.setInvestigatorODVisitDocID(giODInsuCheckListData.get(0).getInvestigatorODVisitDocumentID());
                        gi_odData0.setInvestigatorGiODCheckListSubmittedOnDate(getCurrentDateTime());
                        gi_odData0.setInvestigatorGiODCheckListSubmittedByUserID(-1);
                        gi_odData0.setInvestigatorGiODCheckListEntryOnDate(getCurrentDateTime());
                        gi_odData0.setGIInvCheckListStatus(IsWrittenStatementWithKYC);
                        gi_odData0.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextWrittenStatementWithKYC.getText().toString().trim());
                        gi_odData0.setHoldCase(false);

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                        gi_odData1.setInvestigatorGiODCheckListID(-1);
                        gi_odData1.setInvestigatorGiODCheckListInvID(data.get(0).getInsuranceDataID());
                        gi_odData1.setInvestigatorGiODCheckListGenInsuID(data.get(0).getInvestigatorObj().getInvId());
                        gi_odData1.setInvestigatorODVisitTypeID(giODInsuCheckListData.get(1).getInvestigatorODVisitTypeID());
                        gi_odData1.setInvestigatorODVisitDocID(giODInsuCheckListData.get(1).getInvestigatorODVisitDocumentID());
                        gi_odData1.setInvestigatorGiODCheckListSubmittedOnDate(getCurrentDateTime());
                        gi_odData1.setInvestigatorGiODCheckListSubmittedByUserID(-1);
                        gi_odData1.setInvestigatorGiODCheckListEntryOnDate(getCurrentDateTime());
                        gi_odData1.setGIInvCheckListStatus(IsSelfieWithInsured);
                        gi_odData1.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextSelfieWithInsured.getText().toString().trim());
                        gi_odData1.setHoldCase(false);

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        gi_odData2.setInvestigatorGiODCheckListID(-1);
                        gi_odData2.setInvestigatorGiODCheckListInvID(data.get(0).getInsuranceDataID());
                        gi_odData2.setInvestigatorGiODCheckListGenInsuID(data.get(0).getInvestigatorObj().getInvId());
                        gi_odData2.setInvestigatorODVisitTypeID(giODInsuCheckListData.get(2).getInvestigatorODVisitTypeID());
                        gi_odData2.setInvestigatorODVisitDocID(giODInsuCheckListData.get(2).getInvestigatorODVisitDocumentID());
                        gi_odData2.setInvestigatorGiODCheckListSubmittedOnDate(getCurrentDateTime());
                        gi_odData2.setInvestigatorGiODCheckListSubmittedByUserID(-1);
                        gi_odData2.setInvestigatorGiODCheckListEntryOnDate(getCurrentDateTime());
                        gi_odData2.setGIInvCheckListStatus(IsGoogleTimeline);
                        gi_odData2.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextGoogleTimeline.getText().toString().trim());
                        gi_odData2.setHoldCase(false);

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        gi_odData3.setInvestigatorGiODCheckListID(-1);
                        gi_odData3.setInvestigatorGiODCheckListInvID(data.get(0).getInsuranceDataID());
                        gi_odData3.setInvestigatorGiODCheckListGenInsuID(data.get(0).getInvestigatorObj().getInvId());
                        gi_odData3.setInvestigatorODVisitTypeID(giODInsuCheckListData.get(3).getInvestigatorODVisitTypeID());
                        gi_odData3.setInvestigatorODVisitDocID(giODInsuCheckListData.get(3).getInvestigatorODVisitDocumentID());
                        gi_odData3.setInvestigatorGiODCheckListSubmittedOnDate(getCurrentDateTime());
                        gi_odData3.setInvestigatorGiODCheckListSubmittedByUserID(-1);
                        gi_odData3.setInvestigatorGiODCheckListEntryOnDate(getCurrentDateTime());
                        gi_odData3.setGIInvCheckListStatus(IsVehicleRelatedPapers);
                        gi_odData3.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextVehicleRelatedPapers.getText().toString().trim());
                        gi_odData3.setHoldCase(false);

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        gi_odData4.setInvestigatorGiODCheckListID(-1);
                        gi_odData4.setInvestigatorGiODCheckListInvID(data.get(0).getInsuranceDataID());
                        gi_odData4.setInvestigatorGiODCheckListGenInsuID(data.get(0).getInvestigatorObj().getInvId());
                        gi_odData4.setInvestigatorODVisitTypeID(giODInsuCheckListData.get(4).getInvestigatorODVisitTypeID());
                        gi_odData4.setInvestigatorODVisitDocID(giODInsuCheckListData.get(4).getInvestigatorODVisitDocumentID());
                        gi_odData4.setInvestigatorGiODCheckListSubmittedOnDate(getCurrentDateTime());
                        gi_odData4.setInvestigatorGiODCheckListSubmittedByUserID(-1);
                        gi_odData4.setInvestigatorGiODCheckListEntryOnDate(getCurrentDateTime());
                        gi_odData4.setGIInvCheckListStatus(IsOccupationDetailsLivingStatus);
                        gi_odData4.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextOccupationDetailsLivingStatus.getText().toString().trim());
                        gi_odData4.setHoldCase(false);

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        gi_odData5.setInvestigatorGiODCheckListID(-1);
                        gi_odData5.setInvestigatorGiODCheckListInvID(data.get(0).getInsuranceDataID());
                        gi_odData5.setInvestigatorGiODCheckListGenInsuID(data.get(0).getInvestigatorObj().getInvId());
                        gi_odData5.setInvestigatorODVisitTypeID(giODInsuCheckListData.get(5).getInvestigatorODVisitTypeID());
                        gi_odData5.setInvestigatorODVisitDocID(giODInsuCheckListData.get(5).getInvestigatorODVisitDocumentID());
                        gi_odData5.setInvestigatorGiODCheckListSubmittedOnDate(getCurrentDateTime());
                        gi_odData5.setInvestigatorGiODCheckListSubmittedByUserID(-1);
                        gi_odData5.setInvestigatorGiODCheckListEntryOnDate(getCurrentDateTime());
                        gi_odData5.setGIInvCheckListStatus(IsWrittenStatementWithKYCDLCopy);
                        gi_odData5.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextOccupationDetailsLivingStatus.getText().toString().trim());
                        gi_odData5.setHoldCase(false);

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        gi_odData6.setInvestigatorGiODCheckListID(-1);
                        gi_odData6.setInvestigatorGiODCheckListInvID(data.get(0).getInsuranceDataID());
                        gi_odData6.setInvestigatorGiODCheckListGenInsuID(data.get(0).getInvestigatorObj().getInvId());
                        gi_odData6.setInvestigatorODVisitTypeID(giODInsuCheckListData.get(6).getInvestigatorODVisitTypeID());
                        gi_odData6.setInvestigatorODVisitDocID(giODInsuCheckListData.get(6).getInvestigatorODVisitDocumentID());
                        gi_odData6.setInvestigatorGiODCheckListSubmittedOnDate(getCurrentDateTime());
                        gi_odData6.setInvestigatorGiODCheckListSubmittedByUserID(-1);
                        gi_odData6.setInvestigatorGiODCheckListEntryOnDate(getCurrentDateTime());
                        gi_odData6.setGIInvCheckListStatus(IsGoogleTimelineRoamingMessagePhotoPhone);
                        gi_odData6.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextOccupationDetailsLivingStatus.getText().toString().trim());
                        gi_odData6.setHoldCase(false);

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        gi_odData7.setInvestigatorGiODCheckListID(-1);
                        gi_odData7.setInvestigatorGiODCheckListInvID(data.get(0).getInsuranceDataID());
                        gi_odData7.setInvestigatorGiODCheckListGenInsuID(data.get(0).getInvestigatorObj().getInvId());
                        gi_odData7.setInvestigatorODVisitTypeID(giODInsuCheckListData.get(7).getInvestigatorODVisitTypeID());
                        gi_odData7.setInvestigatorODVisitDocID(giODInsuCheckListData.get(7).getInvestigatorODVisitDocumentID());
                        gi_odData7.setInvestigatorGiODCheckListSubmittedOnDate(getCurrentDateTime());
                        gi_odData7.setInvestigatorGiODCheckListSubmittedByUserID(-1);
                        gi_odData7.setInvestigatorGiODCheckListEntryOnDate(getCurrentDateTime());
                        gi_odData7.setGIInvCheckListStatus(Is8_PhotosWithWideAngle);
                        gi_odData7.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextOccupationDetailsLivingStatus.getText().toString().trim());
                        gi_odData7.setHoldCase(false);

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        gi_odData8.setInvestigatorGiODCheckListID(-1);
                        gi_odData8.setInvestigatorGiODCheckListInvID(data.get(0).getInsuranceDataID());
                        gi_odData8.setInvestigatorGiODCheckListGenInsuID(data.get(0).getInvestigatorObj().getInvId());
                        gi_odData8.setInvestigatorODVisitTypeID(giODInsuCheckListData.get(8).getInvestigatorODVisitTypeID());
                        gi_odData8.setInvestigatorODVisitDocID(giODInsuCheckListData.get(8).getInvestigatorODVisitDocumentID());
                        gi_odData8.setInvestigatorGiODCheckListSubmittedOnDate(getCurrentDateTime());
                        gi_odData8.setInvestigatorGiODCheckListSubmittedByUserID(-1);
                        gi_odData8.setInvestigatorGiODCheckListEntryOnDate(getCurrentDateTime());
                        gi_odData8.setGIInvCheckListStatus(WitnessVideo_Statement);
                        gi_odData8.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextOccupationDetailsLivingStatus.getText().toString().trim());
                        gi_odData8.setHoldCase(false);

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        gi_odData9.setInvestigatorGiODCheckListID(-1);
                        gi_odData9.setInvestigatorGiODCheckListInvID(data.get(0).getInsuranceDataID());
                        gi_odData9.setInvestigatorGiODCheckListGenInsuID(data.get(0).getInvestigatorObj().getInvId());
                        gi_odData9.setInvestigatorODVisitTypeID(giODInsuCheckListData.get(9).getInvestigatorODVisitTypeID());
                        gi_odData9.setInvestigatorODVisitDocID(giODInsuCheckListData.get(9).getInvestigatorODVisitDocumentID());
                        gi_odData9.setInvestigatorGiODCheckListSubmittedOnDate(getCurrentDateTime());
                        gi_odData9.setInvestigatorGiODCheckListSubmittedByUserID(-1);
                        gi_odData9.setInvestigatorGiODCheckListEntryOnDate(getCurrentDateTime());
                        gi_odData9.setGIInvCheckListStatus(Name_ContactNo);
                        gi_odData9.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextOccupationDetailsLivingStatus.getText().toString().trim());
                        gi_odData9.setHoldCase(false);

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        gi_odData10.setInvestigatorGiODCheckListID(-1);
                        gi_odData9.setInvestigatorGiODCheckListInvID(data.get(0).getInsuranceDataID());
                        gi_odData9.setInvestigatorGiODCheckListGenInsuID(data.get(0).getInvestigatorObj().getInvId());
                        gi_odData9.setInvestigatorODVisitTypeID(giODInsuCheckListData.get(9).getInvestigatorODVisitTypeID());
                        gi_odData9.setInvestigatorODVisitDocID(giODInsuCheckListData.get(9).getInvestigatorODVisitDocumentID());
                        gi_odData9.setInvestigatorGiODCheckListSubmittedOnDate(getCurrentDateTime());
                        gi_odData9.setInvestigatorGiODCheckListSubmittedByUserID(-1);
                        gi_odData9.setInvestigatorGiODCheckListEntryOnDate(getCurrentDateTime());
                        gi_odData9.setGIInvCheckListStatus(Name_ContactNo);
                        gi_odData9.setGiInvCheckListReason(activityGiOdReportingFormatBinding.editTextOccupationDetailsLivingStatus.getText().toString().trim());
                        gi_odData9.setHoldCase(false);
                        ////////////////////////////////////////////////End Region Insured Visit//////////////////////////////////////////////////////////////////


                        lstInvGiODCheckList.add(gi_odData0);
                        lstInvGiODCheckList.add(gi_odData1);
                        lstInvGiODCheckList.add(gi_odData2);
                        lstInvGiODCheckList.add(gi_odData3);
                        lstInvGiODCheckList.add(gi_odData4);

                        lstInvGiODCheckList.add(gi_odData5);
                        lstInvGiODCheckList.add(gi_odData6);
                        lstInvGiODCheckList.add(gi_odData7);
                        lstInvGiODCheckList.add(gi_odData8);
                        lstInvGiODCheckList.add(gi_odData9);

                        gi_od.setLstInvGiODCheckList(lstInvGiODCheckList);*/

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        String JSONObject = gson.toJson(gi_od);
                        postMactData(gi_od);
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

    private void postMactData(GI_OD gi_od) {
        ProgressDialog dialog = ProgressDialog.show(GI_OD_ReportingFormatActivity.this, "Loading", "Please wait...", true);
        apiService = ApiClient.getClient(this).create(ApiService.class);
        if (ConnectionUtility.isConnected(GI_OD_ReportingFormatActivity.this)) {
            Token = PrefUtils.getFromPrefs(GI_OD_ReportingFormatActivity.this, PrefUtils.Token);
            Call<GI_ODResponse> lifeInsuranceCheckListCall = apiService.postGI_OD("Bearer " + Token, gi_od);
            lifeInsuranceCheckListCall.enqueue(new Callback<GI_ODResponse>() {
                @Override
                public void onResponse(Call<GI_ODResponse> call, Response<GI_ODResponse> response) {
                    if (response.isSuccessful()) {
                        dialog.dismiss();
                        if (response.body() != null) {
                            GI_ODResponse giOdCheckList = response.body();
                            if (giOdCheckList.getStatusCode() == 200 && giOdCheckList.getStatus().equalsIgnoreCase("success")) {
                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(GI_OD_ReportingFormatActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                sweetAlertDialog.setTitleText("GREAT!");
                                sweetAlertDialog.setCancelable(false);
                                sweetAlertDialog.setContentText("Your GI-OD Check list is submitted successfully");
                                sweetAlertDialog.show();
                                sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        sweetAlertDialog.dismiss();
                                        Intent intent = new Intent(GI_OD_ReportingFormatActivity.this, FinalSubmissionAssignment_Activity.class);
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
                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(GI_OD_ReportingFormatActivity.this, SweetAlertDialog.ERROR_TYPE);
                                sweetAlertDialog.setTitleText("SORRY!");
                                sweetAlertDialog.setCancelable(false);
                                sweetAlertDialog.setContentText("Your GI-OD Check list is not submitted");
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
                public void onFailure(Call<GI_ODResponse> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "" + t, Toast.LENGTH_LONG).show();
                }
            });
        } else {
            ConnectionUtility.AlertDialogForNoConnectionAvaialble(GI_OD_ReportingFormatActivity.this);
        }
    }

    public String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("en"));
        return sdf.format(new Date());
    }

    public void goBack(View view) {
        finish();
    }
}