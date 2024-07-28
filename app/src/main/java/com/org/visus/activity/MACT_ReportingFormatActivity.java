package com.org.visus.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.org.visus.R;
import com.org.visus.apis.ApiClient;
import com.org.visus.apis.ApiService;
import com.org.visus.apis.ErrorLogAPICall;
import com.org.visus.databinding.ActivityMactReportingFormatBinding;
import com.org.visus.models.MACTInsuCheckList;
import com.org.visus.models.MACTResponse;
import com.org.visus.models.MyAssignment;
import com.org.visus.models.requset.MACT;
import com.org.visus.utility.ConnectionUtility;
import com.org.visus.utility.PrefUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MACT_ReportingFormatActivity extends AppCompatActivity {
    ActivityMactReportingFormatBinding activityMactReportingFormatBinding;
    Bundle bundle;
    ArrayList<MACTInsuCheckList.MACTInsuCheckListData> mactInsuCheckListData;
    String VisusService, VisusServiceID;
    Boolean IsWhetherDeceasedAdmittedHospital = false, IsDisabilityCertificate = false, IsMeetingWithClaimant = false, IsMeetingWithInsured = false, IsDriverDL_of_TP_Vehicle = false, IsRC = false, IsDLWithParticulars = false, IsInsurancePolicy = false, IsFIR = false, IsPolicePanchnama = false, IsPMReport = false, IsChargeSheetReport = false, IsCaseDiary = false, IsChargeSheet_filed_against_TP_Vehicle = false;
    ApiService apiService;
    String Token;
    MyAssignment.MyAssignmentData data;
    List<MACT.MACTData> mactDataList = new ArrayList<>();
    MACT.MACTData mactData1, mactData2, mactData3, mactData4, mactData5, mactData6, mactData7, mactData8, mactData9, mactData10, mactData11, mactData12, mactData13, mactData14, mactData15, mactData16, mactData17, mactData18, mactData19, mactData20, mactData21, mactData22, mactData23, mactData24, mactData25, mactData26, mactData27, mactData28, mactData29, mactData30, mactData31, mactData32, mactData33, mactData34, mactData35, mactData36, mactData37, mactData38, mactData39, mactData40, mactData41, mactData42;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMactReportingFormatBinding = ActivityMactReportingFormatBinding.inflate(getLayoutInflater());
        setContentView(activityMactReportingFormatBinding.getRoot());
        bundle = getIntent().getExtras();
        if (bundle != null) {
            mactInsuCheckListData = (ArrayList<MACTInsuCheckList.MACTInsuCheckListData>) getIntent().getSerializableExtra("MACTClaimData");
            data = (MyAssignment.MyAssignmentData) getIntent().getSerializableExtra("Data");
            VisusService = bundle.getString("VisusService", "");
            VisusServiceID = bundle.getString("VisusServiceID", "");
        }

        if (mactInsuCheckListData != null) {
            String INV_code = PrefUtils.getFromPrefs(MACT_ReportingFormatActivity.this, PrefUtils.INV_code);
            String INV_name = PrefUtils.getFromPrefs(MACT_ReportingFormatActivity.this, PrefUtils.INV_name);
            String ContactNumber = PrefUtils.getFromPrefs(MACT_ReportingFormatActivity.this, PrefUtils.ContactNumber);
            activityMactReportingFormatBinding.editTextInvestigatorCode.setText(INV_code);
            activityMactReportingFormatBinding.editTextInvestigatorName.setText(INV_name);
            activityMactReportingFormatBinding.editTextClaimNumber.setText(data.getClaimNumber());
            activityMactReportingFormatBinding.editTextClaimTitle.setText(data.getCaseTitle());
            if (data.getDateOfAccident() == null || data.getDateOfAccident().equalsIgnoreCase("") || data.getDateOfAccident().equalsIgnoreCase("N/A")) {
                activityMactReportingFormatBinding.editTextDateofAccident.setText("");
                activityMactReportingFormatBinding.editTextDateofAccident.setFocusable(true);
                activityMactReportingFormatBinding.editTextDateofAccident.setFocusableInTouchMode(true);
                activityMactReportingFormatBinding.editTextDateofAccident.setClickable(true);
                activityMactReportingFormatBinding.editTextDateofAccident.setEnabled(true);
            } else {
                activityMactReportingFormatBinding.editTextDateofAccident.setText(data.getDateOfAccident());
                activityMactReportingFormatBinding.editTextDateofAccident.setFocusable(false);
                activityMactReportingFormatBinding.editTextDateofAccident.setFocusableInTouchMode(false);
                activityMactReportingFormatBinding.editTextDateofAccident.setClickable(false);
                activityMactReportingFormatBinding.editTextDateofAccident.setEnabled(false);
            }
            //activityMactReportingFormatBinding.editTextClaimNumber.setText(ContactNumber);
            //////////////////Start Radio Button//////////////////////////

            mactData1 = new MACT.MACTData();
            activityMactReportingFormatBinding.rbWhetherDeceasedAdmittedHospitalNo.setChecked(true);

            mactData5 = new MACT.MACTData();
            activityMactReportingFormatBinding.rbDisabilityCertificateNo.setChecked(true);


            mactData7 = new MACT.MACTData();
            activityMactReportingFormatBinding.rbMeetingWithClaimantNo.setChecked(true);

            mactData16 = new MACT.MACTData();
            activityMactReportingFormatBinding.rgMeetingWithInsuredNo.setChecked(true);

            mactData20 = new MACT.MACTData();
            activityMactReportingFormatBinding.rbDriverDLOfTPVehicleNo.setChecked(true);

            mactData22 = new MACT.MACTData();
            activityMactReportingFormatBinding.rbRCNo.setChecked(true);

            mactData23 = new MACT.MACTData();
            activityMactReportingFormatBinding.rbDLwithParticularsNo.setChecked(true);

            mactData25 = new MACT.MACTData();
            activityMactReportingFormatBinding.rbInsurancePolicyNo.setChecked(true);


            mactData27 = new MACT.MACTData();
            activityMactReportingFormatBinding.rbFIRNo.setChecked(true);

            mactData28 = new MACT.MACTData();
            activityMactReportingFormatBinding.rbPolicePanchnamaNo.setChecked(true);

            mactData29 = new MACT.MACTData();
            activityMactReportingFormatBinding.rbPMReportNo.setChecked(true);

            mactData30 = new MACT.MACTData();
            activityMactReportingFormatBinding.rbChargeSheetReportNo.setChecked(true);

            mactData31 = new MACT.MACTData();
            activityMactReportingFormatBinding.rbCaseDiaryNo.setChecked(true);

            mactData32 = new MACT.MACTData();
            activityMactReportingFormatBinding.rbChargeSheetAgainstNo.setChecked(true);


            //////////////////End Radio Button//////////////////////////
            //////////////////Start Label Button//////////////////////////
            mactData3 = new MACT.MACTData();
            mactData6 = new MACT.MACTData();
            mactData10 = new MACT.MACTData();
            mactData15 = new MACT.MACTData();
            mactData17 = new MACT.MACTData();
            mactData21 = new MACT.MACTData();
            mactData26 = new MACT.MACTData();
            mactData33 = new MACT.MACTData();
            //////////////////End Label Button//////////////////////////


            //////////////////Start Edit Text//////////////////////////
            mactData2 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextIfAdmitted.setText(mactInsuCheckListData.get(1).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextIfAdmitted.setText("");

            mactData4 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextFirstHospitalAdmissionDetails.setText(mactInsuCheckListData.get(3).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextFirstHospitalAdmissionDetails.setText("");

            mactData8 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextDeceasedInjuredOccupation.setText(mactInsuCheckListData.get(7).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextDeceasedInjuredOccupation.setText("");

            mactData9 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextDeceasedInjuredSalariedfilingITRs.setText(mactInsuCheckListData.get(8).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextDeceasedInjuredSalariedfilingITRs.setText("");

            mactData10 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextHighSchoolMarksheet.setText(mactInsuCheckListData.get(9).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextHighSchoolMarksheet.setText("");

            mactData11 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextRationCard.setText(mactInsuCheckListData.get(10).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextRationCard.setText("");

            mactData12 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextVoterIDCard.setText(mactInsuCheckListData.get(11).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextVoterIDCard.setText("");

            mactData13 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextPariwarRegister.setText(mactInsuCheckListData.get(12).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextPariwarRegister.setText("");

            mactData14 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextAadharCard.setText(mactInsuCheckListData.get(13).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextAadharCard.setText("");


            mactData17 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextInsuredOccupation.setText(mactInsuCheckListData.get(16).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextInsuredOccupation.setText("");

            mactData18 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextDriverNameContact.setText(mactInsuCheckListData.get(17).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextDriverNameContact.setText("");

            mactData19 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextISTPVehicleInvolved.setText(mactInsuCheckListData.get(18).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextISTPVehicleInvolved.setText("");

            mactData24 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextPermit.setText(mactInsuCheckListData.get(23).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextPermit.setText("");


            mactData34 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextClaimantAdvocateNameAndNumber.setText(mactInsuCheckListData.get(33).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextClaimantAdvocateNameAndNumber.setText("");

            mactData35 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextClaimantContactNumber.setText(mactInsuCheckListData.get(34).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextClaimantContactNumber.setText("");

            mactData36 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextInsuredAdvocateNameAndNumber.setText(mactInsuCheckListData.get(35).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextInsuredAdvocateNameAndNumber.setText("");

            mactData37 = new MACT.MACTData();
            // activityMactReportingFormatBinding.editTextInsuredContactNumber.setText(mactInsuCheckListData.get(36).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextInsuredContactNumber.setText("");

            mactData38 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextWriteAboutClaimantVisit.setText(mactInsuCheckListData.get(37).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextWriteAboutClaimantVisit.setText("");

            mactData39 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextWriteAboutInsuredVisit.setText(mactInsuCheckListData.get(38).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextWriteAboutInsuredVisit.setText("");

            mactData40 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextWriteAboutDriverVisit.setText(mactInsuCheckListData.get(39).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextWriteAboutDriverVisit.setText("");

            mactData41 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextWriteAboutAccidentalSpotVisit.setText(mactInsuCheckListData.get(40).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextWriteAboutAccidentalSpotVisit.setText("");

            mactData42 = new MACT.MACTData();
            //activityMactReportingFormatBinding.editTextWriteAboutHospitalVisit.setText(mactInsuCheckListData.get(41).getpInvMACTCheckListHeadText());
            activityMactReportingFormatBinding.editTextWriteAboutHospitalVisit.setText("");
            //////////////////End Edit Text//////////////////////////
        }

        activityMactReportingFormatBinding.editTextDateofAccident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MACT_ReportingFormatActivity.this, R.style.DatePicker, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String month = null, date_1 = null;

                        if ((monthOfYear + 1) < 10) {

                            month = "0" + (monthOfYear + 1);
                        } else {
                            month = String.valueOf(monthOfYear + 1);
                        }
                        if (dayOfMonth < 10) {

                            date_1 = "0" + dayOfMonth;
                        } else {
                            date_1 = String.valueOf(dayOfMonth);
                        }
                        // String date = year + "-" + month + "-" + date_1;
                        String date = date_1 + "/" + month + "/" + year;
                                /*try {
                                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                                    Log.i("TAG", "" + date1);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }*/


                        activityMactReportingFormatBinding.editTextDateofAccident.setText(date);

//                                date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                datePickerDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.purple_500));
                datePickerDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.purple_500));
                // datePickerDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.purple_500));
            }
        });

        activityMactReportingFormatBinding.buttonSubmitMACTDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String DateofAccident = activityMactReportingFormatBinding.editTextDateofAccident.getText().toString().trim();
                if (DateofAccident.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter Date Of Accident", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MACT_ReportingFormatActivity.this);
                    builder.setTitle("Confirmation!");
                    builder.setMessage("After submitting your checklist, Claim will be removed from the dashboard.Are you sure want to submit?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MACT mact = new MACT();
                            mact.setMACT_InvInsuranceRelID(Integer.valueOf(data.getInvInsuranceRelID()));
                            mact.setInvestigatorSubmittionDate(getCurrentDateTime());
                            mact.setTat(data.gettATForInvestigator());
                            mact.setInvestigatorID(data.getInvestigatorObj().getInvId());
                            mact.setMactid(data.getInsuranceDataID());
                            mact.setMACTCheckListDataSaved(false);
                            mact.setExceptionIfAny("");

                            //////////////////////////////////RadioButton/////////////////////////////////////////////
                            mactData1.setInvMACTCheckListId(-1);
                            mactData1.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData1.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData1.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData1.setInvMACTCheckListHeadID(mactInsuCheckListData.get(0).getInvMACTCheckListHeadID());
                            mactData1.setInvMACTCheckListTextData("");
                            mactData1.setEntryOnDate(getCurrentDateTime());
                            mactData1.setEntryByUserID(-1);
                            mactData1.setEntryByUserName("");
                            mactData1.setHoldCase(false);
                            mactData1.setInvMACTCheckListStatusData(IsWhetherDeceasedAdmittedHospital);


                            mactData5.setInvMACTCheckListId(-1);
                            mactData5.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData5.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData5.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData5.setInvMACTCheckListHeadID(mactInsuCheckListData.get(4).getInvMACTCheckListHeadID());
                            mactData5.setInvMACTCheckListTextData("");
                            mactData5.setEntryOnDate(getCurrentDateTime());
                            mactData5.setEntryByUserID(-1);
                            mactData5.setEntryByUserName("");
                            mactData5.setHoldCase(false);
                            mactData5.setInvMACTCheckListStatusData(IsDisabilityCertificate);

                            mactData7.setInvMACTCheckListId(-1);
                            mactData7.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData7.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData7.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData7.setInvMACTCheckListHeadID(mactInsuCheckListData.get(6).getInvMACTCheckListHeadID());
                            mactData7.setInvMACTCheckListTextData("");
                            mactData7.setEntryOnDate(getCurrentDateTime());
                            mactData7.setEntryByUserID(-1);
                            mactData7.setEntryByUserName("");
                            mactData7.setHoldCase(false);
                            mactData7.setInvMACTCheckListStatusData(IsMeetingWithClaimant);


                            mactData16.setInvMACTCheckListId(-1);
                            mactData16.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData16.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData16.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData16.setInvMACTCheckListHeadID(mactInsuCheckListData.get(15).getInvMACTCheckListHeadID());
                            mactData16.setInvMACTCheckListTextData("");
                            mactData16.setEntryOnDate(getCurrentDateTime());
                            mactData16.setEntryByUserID(-1);
                            mactData16.setEntryByUserName("");
                            mactData16.setHoldCase(false);
                            mactData16.setInvMACTCheckListStatusData(IsMeetingWithInsured);

                            mactData20.setInvMACTCheckListId(-1);
                            mactData20.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData20.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData20.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData20.setInvMACTCheckListHeadID(mactInsuCheckListData.get(19).getInvMACTCheckListHeadID());
                            mactData20.setInvMACTCheckListTextData("");
                            mactData20.setEntryOnDate(getCurrentDateTime());
                            mactData20.setEntryByUserID(-1);
                            mactData20.setEntryByUserName("");
                            mactData20.setHoldCase(false);
                            mactData20.setInvMACTCheckListStatusData(IsDriverDL_of_TP_Vehicle);

                            mactData22.setInvMACTCheckListId(-1);
                            mactData22.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData22.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData22.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData22.setInvMACTCheckListHeadID(mactInsuCheckListData.get(21).getInvMACTCheckListHeadID());
                            mactData22.setInvMACTCheckListTextData("");
                            mactData22.setEntryOnDate(getCurrentDateTime());
                            mactData22.setEntryByUserID(-1);
                            mactData22.setEntryByUserName("");
                            mactData22.setHoldCase(false);
                            mactData22.setInvMACTCheckListStatusData(IsRC);

                            mactData23.setInvMACTCheckListId(-1);
                            mactData23.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData23.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData23.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData23.setInvMACTCheckListHeadID(mactInsuCheckListData.get(22).getInvMACTCheckListHeadID());
                            mactData23.setInvMACTCheckListTextData("");
                            mactData23.setEntryOnDate(getCurrentDateTime());
                            mactData23.setEntryByUserID(-1);
                            mactData23.setEntryByUserName("");
                            mactData23.setHoldCase(false);
                            mactData23.setInvMACTCheckListStatusData(IsDLWithParticulars);

                            mactData25.setInvMACTCheckListId(-1);
                            mactData25.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData25.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData25.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData25.setInvMACTCheckListHeadID(mactInsuCheckListData.get(24).getInvMACTCheckListHeadID());
                            mactData25.setInvMACTCheckListTextData("");
                            mactData25.setEntryOnDate(getCurrentDateTime());
                            mactData25.setEntryByUserID(-1);
                            mactData25.setEntryByUserName("");
                            mactData25.setHoldCase(false);
                            mactData25.setInvMACTCheckListStatusData(IsInsurancePolicy);

                            mactData27.setInvMACTCheckListId(-1);
                            mactData27.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData27.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData27.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData27.setInvMACTCheckListHeadID(mactInsuCheckListData.get(26).getInvMACTCheckListHeadID());
                            mactData27.setInvMACTCheckListTextData("");
                            mactData27.setEntryOnDate(getCurrentDateTime());
                            mactData27.setEntryByUserID(-1);
                            mactData27.setEntryByUserName("");
                            mactData27.setHoldCase(false);
                            mactData27.setInvMACTCheckListStatusData(IsFIR);

                            mactData28.setInvMACTCheckListId(-1);
                            mactData28.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData28.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData28.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData28.setInvMACTCheckListHeadID(mactInsuCheckListData.get(27).getInvMACTCheckListHeadID());
                            mactData28.setInvMACTCheckListTextData("");
                            mactData28.setEntryOnDate(getCurrentDateTime());
                            mactData28.setEntryByUserID(-1);
                            mactData28.setEntryByUserName("");
                            mactData28.setHoldCase(false);
                            mactData28.setInvMACTCheckListStatusData(IsPolicePanchnama);

                            mactData29.setInvMACTCheckListId(-1);
                            mactData29.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData29.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData29.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData29.setInvMACTCheckListHeadID(mactInsuCheckListData.get(28).getInvMACTCheckListHeadID());
                            mactData29.setInvMACTCheckListTextData("");
                            mactData29.setEntryOnDate(getCurrentDateTime());
                            mactData29.setEntryByUserID(-1);
                            mactData29.setEntryByUserName("");
                            mactData29.setHoldCase(false);
                            mactData29.setInvMACTCheckListStatusData(IsPMReport);

                            mactData30.setInvMACTCheckListId(-1);
                            mactData30.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData30.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData30.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData30.setInvMACTCheckListHeadID(mactInsuCheckListData.get(29).getInvMACTCheckListHeadID());
                            mactData30.setInvMACTCheckListTextData("");
                            mactData30.setEntryOnDate(getCurrentDateTime());
                            mactData30.setEntryByUserID(-1);
                            mactData30.setEntryByUserName("");
                            mactData30.setHoldCase(false);
                            mactData30.setInvMACTCheckListStatusData(IsChargeSheetReport);

                            mactData31.setInvMACTCheckListId(-1);
                            mactData31.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData31.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData31.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData31.setInvMACTCheckListHeadID(mactInsuCheckListData.get(30).getInvMACTCheckListHeadID());
                            mactData31.setInvMACTCheckListTextData("");
                            mactData31.setEntryOnDate(getCurrentDateTime());
                            mactData31.setEntryByUserID(-1);
                            mactData31.setEntryByUserName("");
                            mactData31.setHoldCase(false);
                            mactData31.setInvMACTCheckListStatusData(IsCaseDiary);

                            mactData32.setInvMACTCheckListId(-1);
                            mactData32.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData32.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData32.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData32.setInvMACTCheckListHeadID(mactInsuCheckListData.get(31).getInvMACTCheckListHeadID());
                            mactData32.setInvMACTCheckListTextData("");
                            mactData32.setEntryOnDate(getCurrentDateTime());
                            mactData32.setEntryByUserID(-1);
                            mactData32.setEntryByUserName("");
                            mactData32.setHoldCase(false);
                            mactData32.setInvMACTCheckListStatusData(IsChargeSheet_filed_against_TP_Vehicle);


                            //////////////////Start Label Button//////////////////////////
                            mactData3.setInvMACTCheckListId(-1);
                            mactData3.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData3.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData3.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData3.setInvMACTCheckListHeadID(mactInsuCheckListData.get(2).getInvMACTCheckListHeadID());
                            mactData3.setInvMACTCheckListTextData("");
                            mactData3.setEntryOnDate(getCurrentDateTime());
                            mactData3.setEntryByUserID(-1);
                            mactData3.setEntryByUserName("");
                            mactData3.setHoldCase(false);

                            mactData6.setInvMACTCheckListId(-1);
                            mactData6.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData6.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData6.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData6.setInvMACTCheckListHeadID(mactInsuCheckListData.get(5).getInvMACTCheckListHeadID());
                            mactData6.setInvMACTCheckListTextData("");
                            mactData6.setEntryOnDate(getCurrentDateTime());
                            mactData6.setEntryByUserID(-1);
                            mactData6.setEntryByUserName("");
                            mactData6.setHoldCase(false);

                            mactData10.setInvMACTCheckListId(-1);
                            mactData10.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData10.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData10.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData10.setInvMACTCheckListHeadID(mactInsuCheckListData.get(9).getInvMACTCheckListHeadID());
                            mactData10.setInvMACTCheckListTextData("");
                            mactData10.setEntryOnDate(getCurrentDateTime());
                            mactData10.setEntryByUserID(-1);
                            mactData10.setEntryByUserName("");
                            mactData10.setHoldCase(false);


                            mactData15.setInvMACTCheckListId(-1);
                            mactData15.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData15.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData15.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData15.setInvMACTCheckListHeadID(mactInsuCheckListData.get(14).getInvMACTCheckListHeadID());
                            mactData15.setInvMACTCheckListTextData("");
                            mactData15.setEntryOnDate(getCurrentDateTime());
                            mactData15.setEntryByUserID(-1);
                            mactData15.setEntryByUserName("");
                            mactData15.setHoldCase(false);

                            mactData17.setInvMACTCheckListId(-1);
                            mactData17.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData17.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData17.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData17.setInvMACTCheckListHeadID(mactInsuCheckListData.get(16).getInvMACTCheckListHeadID());
                            mactData17.setInvMACTCheckListTextData("");
                            mactData17.setEntryOnDate(getCurrentDateTime());
                            mactData17.setEntryByUserID(-1);
                            mactData17.setEntryByUserName("");
                            mactData17.setHoldCase(false);

                            mactData21.setInvMACTCheckListId(-1);
                            mactData21.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData21.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData21.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData21.setInvMACTCheckListHeadID(mactInsuCheckListData.get(20).getInvMACTCheckListHeadID());
                            mactData21.setInvMACTCheckListTextData("");
                            mactData21.setEntryOnDate(getCurrentDateTime());
                            mactData21.setEntryByUserID(-1);
                            mactData21.setEntryByUserName("");
                            mactData21.setHoldCase(false);

                            mactData26.setInvMACTCheckListId(-1);
                            mactData26.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData26.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData26.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData26.setInvMACTCheckListHeadID(mactInsuCheckListData.get(25).getInvMACTCheckListHeadID());
                            mactData26.setInvMACTCheckListTextData("");
                            mactData26.setEntryOnDate(getCurrentDateTime());
                            mactData26.setEntryByUserID(-1);
                            mactData26.setEntryByUserName("");
                            mactData26.setHoldCase(false);

                            mactData33.setInvMACTCheckListId(-1);
                            mactData33.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData33.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData33.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData33.setInvMACTCheckListHeadID(mactInsuCheckListData.get(32).getInvMACTCheckListHeadID());
                            mactData33.setInvMACTCheckListTextData("");
                            mactData33.setEntryOnDate(getCurrentDateTime());
                            mactData33.setEntryByUserID(-1);
                            mactData33.setEntryByUserName("");
                            mactData33.setHoldCase(false);
                            //////////////////End Label Button//////////////////////////


                            //////////////////Start Edit Text//////////////////////////
                            mactData2.setInvMACTCheckListId(-1);
                            mactData2.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData2.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData2.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData2.setInvMACTCheckListHeadID(mactInsuCheckListData.get(1).getInvMACTCheckListHeadID());
                            mactData2.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextIfAdmitted.getText().toString().trim());
                            mactData2.setEntryOnDate(getCurrentDateTime());
                            mactData2.setEntryByUserID(-1);
                            mactData2.setEntryByUserName("");
                            mactData2.setHoldCase(false);

                            mactData4.setInvMACTCheckListId(-1);
                            mactData4.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData4.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData4.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData4.setInvMACTCheckListHeadID(mactInsuCheckListData.get(3).getInvMACTCheckListHeadID());
                            mactData4.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextFirstHospitalAdmissionDetails.getText().toString().trim());
                            mactData4.setEntryOnDate(getCurrentDateTime());
                            mactData4.setEntryByUserID(-1);
                            mactData4.setEntryByUserName("");
                            mactData4.setHoldCase(false);

                            mactData8.setInvMACTCheckListId(-1);
                            mactData8.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData8.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData8.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData8.setInvMACTCheckListHeadID(mactInsuCheckListData.get(7).getInvMACTCheckListHeadID());
                            mactData8.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextDeceasedInjuredOccupation.getText().toString().trim());
                            mactData8.setEntryOnDate(getCurrentDateTime());
                            mactData8.setEntryByUserID(-1);
                            mactData8.setEntryByUserName("");
                            mactData8.setHoldCase(false);

                            mactData9.setInvMACTCheckListId(-1);
                            mactData9.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData9.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData9.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData9.setInvMACTCheckListHeadID(mactInsuCheckListData.get(8).getInvMACTCheckListHeadID());
                            mactData9.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextDeceasedInjuredSalariedfilingITRs.getText().toString().trim());
                            mactData9.setEntryOnDate(getCurrentDateTime());
                            mactData9.setEntryByUserID(-1);
                            mactData9.setEntryByUserName("");
                            mactData9.setHoldCase(false);


                            mactData11.setInvMACTCheckListId(-1);
                            mactData11.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData11.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData11.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData11.setInvMACTCheckListHeadID(mactInsuCheckListData.get(10).getInvMACTCheckListHeadID());
                            mactData11.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextRationCard.getText().toString().trim());
                            mactData11.setEntryOnDate(getCurrentDateTime());
                            mactData11.setEntryByUserID(-1);
                            mactData11.setEntryByUserName("");
                            mactData11.setHoldCase(false);

                            mactData12.setInvMACTCheckListId(-1);
                            mactData12.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData12.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData12.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData12.setInvMACTCheckListHeadID(mactInsuCheckListData.get(11).getInvMACTCheckListHeadID());
                            mactData12.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextVoterIDCard.getText().toString().trim());
                            mactData12.setEntryOnDate(getCurrentDateTime());
                            mactData12.setEntryByUserID(-1);
                            mactData12.setEntryByUserName("");
                            mactData12.setHoldCase(false);

                            mactData13.setInvMACTCheckListId(-1);
                            mactData13.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData13.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData13.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData13.setInvMACTCheckListHeadID(mactInsuCheckListData.get(12).getInvMACTCheckListHeadID());
                            mactData13.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextPariwarRegister.getText().toString().trim());
                            mactData13.setEntryOnDate(getCurrentDateTime());
                            mactData13.setEntryByUserID(-1);
                            mactData13.setEntryByUserName("");
                            mactData13.setHoldCase(false);

                            mactData14.setInvMACTCheckListId(-1);
                            mactData14.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData14.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData14.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData14.setInvMACTCheckListHeadID(mactInsuCheckListData.get(13).getInvMACTCheckListHeadID());
                            mactData14.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextAadharCard.getText().toString().trim());
                            mactData14.setEntryOnDate(getCurrentDateTime());
                            mactData14.setEntryByUserID(-1);
                            mactData14.setEntryByUserName("");
                            mactData14.setHoldCase(false);

                            mactData17.setInvMACTCheckListId(-1);
                            mactData17.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData17.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData17.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData17.setInvMACTCheckListHeadID(mactInsuCheckListData.get(16).getInvMACTCheckListHeadID());
                            mactData17.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextInsuredOccupation.getText().toString().trim());
                            mactData17.setEntryOnDate(getCurrentDateTime());
                            mactData17.setEntryByUserID(-1);
                            mactData17.setEntryByUserName("");
                            mactData17.setHoldCase(false);


                            mactData18.setInvMACTCheckListId(-1);
                            mactData18.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData18.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData18.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData18.setInvMACTCheckListHeadID(mactInsuCheckListData.get(17).getInvMACTCheckListHeadID());
                            mactData18.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextDriverNameContact.getText().toString().trim());
                            mactData18.setEntryOnDate(getCurrentDateTime());
                            mactData18.setEntryByUserID(-1);
                            mactData18.setEntryByUserName("");
                            mactData18.setHoldCase(false);

                            mactData19.setInvMACTCheckListId(-1);
                            mactData19.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData19.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData19.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData19.setInvMACTCheckListHeadID(mactInsuCheckListData.get(18).getInvMACTCheckListHeadID());
                            mactData19.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextISTPVehicleInvolved.getText().toString().trim());
                            mactData19.setEntryOnDate(getCurrentDateTime());
                            mactData19.setEntryByUserID(-1);
                            mactData19.setEntryByUserName("");
                            mactData19.setHoldCase(false);

                            mactData24.setInvMACTCheckListId(-1);
                            mactData24.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData24.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData24.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData24.setInvMACTCheckListHeadID(mactInsuCheckListData.get(18).getInvMACTCheckListHeadID());
                            mactData24.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextPermit.getText().toString().trim());
                            mactData24.setEntryOnDate(getCurrentDateTime());
                            mactData24.setEntryByUserID(-1);
                            mactData24.setEntryByUserName("");
                            mactData24.setHoldCase(false);

                            mactData34.setInvMACTCheckListId(-1);
                            mactData34.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData34.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData34.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData34.setInvMACTCheckListHeadID(mactInsuCheckListData.get(33).getInvMACTCheckListHeadID());
                            mactData34.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextClaimantAdvocateNameAndNumber.getText().toString().trim());
                            mactData34.setEntryOnDate(getCurrentDateTime());
                            mactData34.setEntryByUserID(-1);
                            mactData34.setEntryByUserName("");
                            mactData34.setHoldCase(false);

                            mactData35.setInvMACTCheckListId(-1);
                            mactData35.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData35.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData35.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData35.setInvMACTCheckListHeadID(mactInsuCheckListData.get(34).getInvMACTCheckListHeadID());
                            mactData35.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextClaimantContactNumber.getText().toString().trim());
                            mactData35.setEntryOnDate(getCurrentDateTime());
                            mactData35.setEntryByUserID(-1);
                            mactData35.setEntryByUserName("");
                            mactData35.setHoldCase(false);

                            mactData36.setInvMACTCheckListId(-1);
                            mactData36.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData36.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData36.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData36.setInvMACTCheckListHeadID(mactInsuCheckListData.get(35).getInvMACTCheckListHeadID());
                            mactData36.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextInsuredAdvocateNameAndNumber.getText().toString().trim());
                            mactData36.setEntryOnDate(getCurrentDateTime());
                            mactData36.setEntryByUserID(-1);
                            mactData36.setEntryByUserName("");
                            mactData36.setHoldCase(false);

                            mactData37.setInvMACTCheckListId(-1);
                            mactData37.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData37.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData37.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData37.setInvMACTCheckListHeadID(mactInsuCheckListData.get(36).getInvMACTCheckListHeadID());
                            mactData37.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextInsuredContactNumber.getText().toString().trim());
                            mactData37.setEntryOnDate(getCurrentDateTime());
                            mactData37.setEntryByUserID(-1);
                            mactData37.setEntryByUserName("");
                            mactData37.setHoldCase(false);


                            mactData38.setInvMACTCheckListId(-1);
                            mactData38.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData38.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData38.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData38.setInvMACTCheckListHeadID(mactInsuCheckListData.get(37).getInvMACTCheckListHeadID());
                            mactData38.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextWriteAboutClaimantVisit.getText().toString().trim());
                            mactData38.setEntryOnDate(getCurrentDateTime());
                            mactData38.setEntryByUserID(-1);
                            mactData38.setEntryByUserName("");
                            mactData38.setHoldCase(false);

                            mactData39.setInvMACTCheckListId(-1);
                            mactData39.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData39.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData39.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData39.setInvMACTCheckListHeadID(mactInsuCheckListData.get(38).getInvMACTCheckListHeadID());
                            mactData39.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextWriteAboutInsuredVisit.getText().toString().trim());
                            mactData39.setEntryOnDate(getCurrentDateTime());
                            mactData39.setEntryByUserID(-1);
                            mactData39.setEntryByUserName("");
                            mactData39.setHoldCase(false);

                            mactData40.setInvMACTCheckListId(-1);
                            mactData40.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData40.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData40.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData40.setInvMACTCheckListHeadID(mactInsuCheckListData.get(38).getInvMACTCheckListHeadID());
                            mactData40.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextWriteAboutDriverVisit.getText().toString().trim());
                            mactData40.setEntryOnDate(getCurrentDateTime());
                            mactData40.setEntryByUserID(-1);
                            mactData40.setEntryByUserName("");
                            mactData40.setHoldCase(false);

                            mactData41.setInvMACTCheckListId(-1);
                            mactData41.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData41.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData41.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData41.setInvMACTCheckListHeadID(mactInsuCheckListData.get(39).getInvMACTCheckListHeadID());
                            mactData41.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextWriteAboutAccidentalSpotVisit.getText().toString().trim());
                            mactData41.setEntryOnDate(getCurrentDateTime());
                            mactData41.setEntryByUserID(-1);
                            mactData41.setEntryByUserName("");
                            mactData41.setHoldCase(false);

                            mactData42.setInvMACTCheckListId(-1);
                            mactData42.setInvMACTCheckListMactID(data.getInsuranceDataID());
                            mactData42.setInvMACTCheckListInvID(data.getInvestigatorObj().getInvId());
                            mactData42.setInvestigatorSubmittionDateTime(getCurrentDateTime());
                            mactData42.setInvMACTCheckListHeadID(mactInsuCheckListData.get(40).getInvMACTCheckListHeadID());
                            mactData42.setInvMACTCheckListTextData(activityMactReportingFormatBinding.editTextWriteAboutHospitalVisit.getText().toString().trim());
                            mactData42.setEntryOnDate(getCurrentDateTime());
                            mactData42.setEntryByUserID(-1);
                            mactData42.setEntryByUserName("");
                            mactData42.setHoldCase(false);

                            //////////////////End Edit Text//////////////////////////


                            mactDataList.add(mactData1);
                            mactDataList.add(mactData2);
                            mactDataList.add(mactData3);
                            mactDataList.add(mactData4);
                            mactDataList.add(mactData5);
                            mactDataList.add(mactData6);
                            mactDataList.add(mactData7);
                            mactDataList.add(mactData8);
                            mactDataList.add(mactData9);
                            mactDataList.add(mactData10);
                            mactDataList.add(mactData11);
                            mactDataList.add(mactData12);
                            mactDataList.add(mactData13);
                            mactDataList.add(mactData14);
                            mactDataList.add(mactData15);
                            mactDataList.add(mactData16);
                            mactDataList.add(mactData17);
                            mactDataList.add(mactData18);
                            mactDataList.add(mactData19);
                            mactDataList.add(mactData20);
                            mactDataList.add(mactData21);
                            mactDataList.add(mactData22);
                            mactDataList.add(mactData23);
                            mactDataList.add(mactData24);
                            mactDataList.add(mactData25);
                            mactDataList.add(mactData26);
                            mactDataList.add(mactData27);
                            mactDataList.add(mactData28);
                            mactDataList.add(mactData29);
                            mactDataList.add(mactData30);
                            mactDataList.add(mactData31);
                            mactDataList.add(mactData32);
                            mactDataList.add(mactData33);
                            mactDataList.add(mactData34);
                            mactDataList.add(mactData35);
                            mactDataList.add(mactData36);
                            mactDataList.add(mactData37);
                            mactDataList.add(mactData38);
                            mactDataList.add(mactData39);
                            mactDataList.add(mactData40);
                            mactDataList.add(mactData41);
                            mactDataList.add(mactData42);
                            mact.setLstInvMACTCheckList(mactDataList);
                            postMactData(mact);
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
            }
        });


        activityMactReportingFormatBinding.rgWhetherDeceasedAdmittedHospital.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_WhetherDeceasedAdmittedHospital_Yes:
                        IsWhetherDeceasedAdmittedHospital = true;
                        break;
                    case R.id.rb_WhetherDeceasedAdmittedHospital_No:
                        IsWhetherDeceasedAdmittedHospital = false;
                        break;
                }
            }
        });

        activityMactReportingFormatBinding.rgDisabilityCertificate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_DisabilityCertificateYes:
                        IsDisabilityCertificate = true;
                        break;
                    case R.id.rb_DisabilityCertificateNo:
                        IsDisabilityCertificate = false;
                        break;
                }
            }
        });
    }

    public void goBack(View view) {
        finish();
    }

    private void postMactData(MACT mactData) {
        ProgressDialog dialog = ProgressDialog.show(MACT_ReportingFormatActivity.this, "Loading", "Please wait...", true);
        apiService = ApiClient.getClient(this).create(ApiService.class);
        if (ConnectionUtility.isConnected(MACT_ReportingFormatActivity.this)) {
            Token = PrefUtils.getFromPrefs(MACT_ReportingFormatActivity.this, PrefUtils.Token);
            Call<MACTResponse> lifeInsuranceCheckListCall = apiService.postMACT("Bearer " + Token, mactData);
            lifeInsuranceCheckListCall.enqueue(new Callback<MACTResponse>() {
                @Override
                public void onResponse(Call<MACTResponse> call, Response<MACTResponse> response) {
                    if (response.isSuccessful()) {
                        dialog.dismiss();
                        if (response.body() != null) {
                            MACTResponse lifeInsuranceCheckList = response.body();
                            if (lifeInsuranceCheckList.getStatusCode() == 200) {
                                if (lifeInsuranceCheckList.getData().get(0).getMACTCheckListDataSaved()) {
                                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MACT_ReportingFormatActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                    sweetAlertDialog.setTitleText("GREAT!");
                                    sweetAlertDialog.setCancelable(false);
                                    sweetAlertDialog.setContentText("Your MACT Check list is submitted successfully");
                                    sweetAlertDialog.show();
                                    sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            sweetAlertDialog.dismiss();
                                            Intent intent = new Intent(MACT_ReportingFormatActivity.this, FinalSubmissionAssignment_Activity.class);
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
                                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MACT_ReportingFormatActivity.this, SweetAlertDialog.ERROR_TYPE);
                                    sweetAlertDialog.setTitleText("SORRY!");
                                    sweetAlertDialog.setCancelable(false);
                                    sweetAlertDialog.setContentText("Your MACT Check list is not submitted");
                                    sweetAlertDialog.show();
                                    sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            sweetAlertDialog.dismiss();
                                        }
                                    });
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), lifeInsuranceCheckList.getMsg(), Toast.LENGTH_LONG).show();
                            }
                        } else {

                        }
                    } else {
                        ErrorLogAPICall apiCall= new ErrorLogAPICall(MACT_ReportingFormatActivity.this,"MACT_ReportingFormatActivity","InvMACTCheckList/saveMACTCheckListData", response.message()+" "+response.code(),"API Exception");
                        apiCall.saveErrorLog();
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<MACTResponse> call, Throwable t) {
                    ErrorLogAPICall apiCall= new ErrorLogAPICall(MACT_ReportingFormatActivity.this,"MACT_ReportingFormatActivity","InvMACTCheckList/saveMACTCheckListData", t.getMessage(),"API Exception");
                    apiCall.saveErrorLog();
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "" + t, Toast.LENGTH_LONG).show();
                }
            });
        } else {
            ConnectionUtility.AlertDialogForNoConnectionAvaialble(MACT_ReportingFormatActivity.this);
        }
    }

    public String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("en"));
        return sdf.format(new Date());
    }
}