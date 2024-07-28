package com.org.visus.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.org.visus.R;
import com.org.visus.apis.ApiClient;
import com.org.visus.apis.ApiService;
import com.org.visus.apis.ErrorLogAPICall;
import com.org.visus.databinding.ActivityLifeInsuranceReportingFormatBinding;
import com.org.visus.models.LifeInsuranceCheckList;
import com.org.visus.utility.ConnectionUtility;
import com.org.visus.utility.PrefUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LifeInsuranceReportingFormatActivity extends AppCompatActivity {
    Boolean IsOccupationDetailsConfirmed = false, IsAgeConfirmed = false;
    ActivityLifeInsuranceReportingFormatBinding activityLifeInsuranceReportingFormatBinding;
    Bundle bundle;
    ApiService apiService;
    String Token;
    String VisusService, VisusServiceID;
    LifeInsuranceCheckList.LifeInsuranceCheckListData LifeInsuranceCheckListData;
    LayoutInflater layoutInflaterDetails;
    Map<String, String> mapNeighbourDetails = new HashMap<String, String>();
    Map<String, String> mapMedicalStoreDetails = new HashMap<String, String>();
    Map<String, String> mapHospitalDetails = new HashMap<String, String>();
    LifeInsuranceCheckList.InvLifeInsuCheckListNeighbours invLifeInsuCheckListNeighbours = new LifeInsuranceCheckList.InvLifeInsuCheckListNeighbours();
    LifeInsuranceCheckList.InvLifeInsuCheckListMedicalStore invLifeInsuCheckListMedicalStore = new LifeInsuranceCheckList.InvLifeInsuCheckListMedicalStore();
    LifeInsuranceCheckList.InvLifeInsuCheckListHospitalCheck lifeInsuCheckListHospitalCheck = new LifeInsuranceCheckList.InvLifeInsuCheckListHospitalCheck();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLifeInsuranceReportingFormatBinding = ActivityLifeInsuranceReportingFormatBinding.inflate(getLayoutInflater());
        setContentView(activityLifeInsuranceReportingFormatBinding.getRoot());
        bundle = getIntent().getExtras();
        if (bundle != null) {
            LifeInsuranceCheckListData = (LifeInsuranceCheckList.LifeInsuranceCheckListData) getIntent().getSerializableExtra("LifeInsuranceCheckListData");
            VisusService = bundle.getString("VisusService", "");
            VisusServiceID = bundle.getString("VisusServiceID", "");
        }

        if (LifeInsuranceCheckListData != null) {
            if (LifeInsuranceCheckListData.getInvestigatorCode() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextInvestigatorCode.setText(LifeInsuranceCheckListData.getInvestigatorCode());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextInvestigatorCode.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getInvestigatorName() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextInvestigatorName.setText(LifeInsuranceCheckListData.getInvestigatorName());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextInvestigatorName.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getInvestigatorContactNo() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextInvestigatorContactNumber.setText(LifeInsuranceCheckListData.getInvestigatorContactNo());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextInvestigatorContactNumber.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getInsuredName() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextInsuredName.setText(LifeInsuranceCheckListData.getInsuredName());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextInsuredName.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getInsuredFatherOrHusbandName() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextInsuredFatherOrHusbandName.setText(LifeInsuranceCheckListData.getInsuredFatherOrHusbandName());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextInsuredFatherOrHusbandName.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getInsuredAge() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextInsuredAge.setText(LifeInsuranceCheckListData.getInsuredAge().toString());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextInsuredAge.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getInsuredCompany() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextInsuredCompany.setText(LifeInsuranceCheckListData.getInsuredCompany());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextInsuredCompany.setText("N/A");
            }

            /*if (LifeInsuranceCheckListData.getClaimantName() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextClaimantName.setText(LifeInsuranceCheckListData.getClaimantName());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextClaimantName.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getClaimantAge() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextClaimantAge.setText(LifeInsuranceCheckListData.getClaimantAge().toString());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextClaimantAge.setText("N/A");
            }*/

            if (LifeInsuranceCheckListData.getAnganbadiName() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextAnganwadiName.setText(LifeInsuranceCheckListData.getAnganbadiName());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextAnganwadiName.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getAnganbadiContactNumber() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextAnganwadiContactNumber.setText(LifeInsuranceCheckListData.getAnganbadiContactNumber().toString());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextAnganwadiContactNumber.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getAshaBahuName() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextAshaBahuName.setText(LifeInsuranceCheckListData.getAshaBahuName());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextAshaBahuName.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getAshaBahuContactNo() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextAshaBahuContactNumber.setText(LifeInsuranceCheckListData.getAshaBahuContactNo().toString());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextAshaBahuContactNumber.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getGramPradhanName() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextGramPradhanName.setText(LifeInsuranceCheckListData.getGramPradhanName());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextGramPradhanName.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getGramPradhanContactNo() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextGramPradhanContactNumber.setText(LifeInsuranceCheckListData.getGramPradhanContactNo().toString());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextGramPradhanContactNumber.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getSabhasadWardName() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextSabhasadWardName.setText(LifeInsuranceCheckListData.getSabhasadWardName());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextSabhasadWardName.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getSabhasadWardContactNo() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextSabhasadWardContactNumber.setText(LifeInsuranceCheckListData.getSabhasadWardContactNo().toString());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextSabhasadWardContactNumber.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getVillageSecretaryName() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextVillageSecretaryName.setText(LifeInsuranceCheckListData.getVillageSecretaryName());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextVillageSecretaryName.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getVillageSecretaryContactNo() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextVillageSecretaryContactNumber.setText(LifeInsuranceCheckListData.getVillageSecretaryContactNo().toString());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextVillageSecretaryContactNumber.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getNagarNigamAuthorityName() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextNagarNigamAuthorityName.setText(LifeInsuranceCheckListData.getNagarNigamAuthorityName());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextNagarNigamAuthorityName.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getNagarNigamAuthorityContactNo() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextNagarNigamAuthorityContactNumber.setText(LifeInsuranceCheckListData.getNagarNigamAuthorityContactNo().toString());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextNagarNigamAuthorityContactNumber.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getpHCCHCDistrictHospitalAddress() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextPHCCHCDistrictHospitalAddress.setText(LifeInsuranceCheckListData.getpHCCHCDistrictHospitalAddress());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextPHCCHCDistrictHospitalAddress.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getpHCCHCDistrictHospitalAddressContactNo() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextPHCCHCDistrictHospitalContactnumber.setText(LifeInsuranceCheckListData.getpHCCHCDistrictHospitalAddressContactNo().toString());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextPHCCHCDistrictHospitalContactnumber.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getPoliceStationCheck() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextPoliceStationCheck.setText(LifeInsuranceCheckListData.getPoliceStationCheck());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextPoliceStationCheck.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getCremationPlaceDetails() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextCremationPlaceDetails.setText(LifeInsuranceCheckListData.getCremationPlaceDetails().toString());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextCremationPlaceDetails.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getFamilyMemberDetails() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextFamilyMemberDetails.setText(LifeInsuranceCheckListData.getFamilyMemberDetails().toString());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextFamilyMemberDetails.setText("N/A");
            }

            if (LifeInsuranceCheckListData.getNarrationOfTheCase() != null) {
                activityLifeInsuranceReportingFormatBinding.editTextNarrationOfCase.setText(LifeInsuranceCheckListData.getNarrationOfTheCase().toString());
            } else {
                activityLifeInsuranceReportingFormatBinding.editTextNarrationOfCase.setText("N/A");
            }
        }


        activityLifeInsuranceReportingFormatBinding.buttonSubmitNeighbourDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NarrationOfCase = activityLifeInsuranceReportingFormatBinding.editTextNarrationOfCase.getText().toString().trim();
                if (NarrationOfCase.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill atleast Narration Of Case", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LifeInsuranceReportingFormatActivity.this);
                    builder.setTitle("Confirmation!");
                    builder.setMessage("After submitting your data will be removed from the list.Are you sure you want to submit?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LifeInsuranceCheckListData.setLi_InvInsuranceRelID(LifeInsuranceCheckListData.getLi_InvInsuranceRelID());
                            LifeInsuranceCheckListData.setInvestigatorName(activityLifeInsuranceReportingFormatBinding.editTextInvestigatorName.getText().toString().trim());
                            LifeInsuranceCheckListData.setInvestigatorContactNo(activityLifeInsuranceReportingFormatBinding.editTextInvestigatorContactNumber.getText().toString().trim());
                            LifeInsuranceCheckListData.setInsuredFatherOrHusbandName(activityLifeInsuranceReportingFormatBinding.editTextInsuredFatherOrHusbandName.getText().toString().trim());
                            if (activityLifeInsuranceReportingFormatBinding.editTextInsuredAge.getText().toString().trim().isEmpty()) {
                                LifeInsuranceCheckListData.setInsuredAge(Integer.parseInt("0"));
                            } else {
                                LifeInsuranceCheckListData.setInsuredAge(Integer.parseInt(activityLifeInsuranceReportingFormatBinding.editTextInsuredAge.getText().toString().trim()));
                            }

                            LifeInsuranceCheckListData.setInsuredCompany(activityLifeInsuranceReportingFormatBinding.editTextInsuredCompany.getText().toString().trim());
                            LifeInsuranceCheckListData.setClaimantName(activityLifeInsuranceReportingFormatBinding.editTextClaimantName.getText().toString().trim());
                            if (activityLifeInsuranceReportingFormatBinding.editTextClaimantAge.getText().toString().trim().isEmpty()) {
                                LifeInsuranceCheckListData.setClaimantAge(Integer.parseInt("0"));
                            } else {
                                LifeInsuranceCheckListData.setClaimantAge(Integer.parseInt(activityLifeInsuranceReportingFormatBinding.editTextClaimantAge.getText().toString().trim()));
                            }
                            LifeInsuranceCheckListData.setAnganbadiName(activityLifeInsuranceReportingFormatBinding.editTextAnganwadiName.getText().toString().trim());
                            LifeInsuranceCheckListData.setAnganbadiContactNumber(activityLifeInsuranceReportingFormatBinding.editTextAnganwadiContactNumber.getText().toString().trim());
                            LifeInsuranceCheckListData.setAshaBahuName(activityLifeInsuranceReportingFormatBinding.editTextAshaBahuName.getText().toString().trim());
                            LifeInsuranceCheckListData.setAshaBahuContactNo(activityLifeInsuranceReportingFormatBinding.editTextAshaBahuContactNumber.getText().toString().trim());
                            LifeInsuranceCheckListData.setGramPradhanName(activityLifeInsuranceReportingFormatBinding.editTextGramPradhanName.getText().toString().trim());
                            LifeInsuranceCheckListData.setGramPradhanContactNo(activityLifeInsuranceReportingFormatBinding.editTextGramPradhanContactNumber.getText().toString().trim());
                            LifeInsuranceCheckListData.setSabhasadWardName(activityLifeInsuranceReportingFormatBinding.editTextSabhasadWardName.getText().toString().trim());
                            LifeInsuranceCheckListData.setSabhasadWardContactNo(activityLifeInsuranceReportingFormatBinding.editTextSabhasadWardContactNumber.getText().toString().trim());
                            LifeInsuranceCheckListData.setVillageSecretaryName(activityLifeInsuranceReportingFormatBinding.editTextVillageSecretaryName.getText().toString().trim());
                            LifeInsuranceCheckListData.setVillageSecretaryContactNo(activityLifeInsuranceReportingFormatBinding.editTextVillageSecretaryContactNumber.getText().toString().trim());
                            LifeInsuranceCheckListData.setNagarNigamAuthorityName(activityLifeInsuranceReportingFormatBinding.editTextNagarNigamAuthorityName.getText().toString().trim());
                            LifeInsuranceCheckListData.setNagarNigamAuthorityContactNo(activityLifeInsuranceReportingFormatBinding.editTextNagarNigamAuthorityContactNumber.getText().toString().trim());
                            LifeInsuranceCheckListData.setpHCCHCDistrictHospitalAddress(activityLifeInsuranceReportingFormatBinding.editTextPHCCHCDistrictHospitalAddress.getText().toString().trim());
                            LifeInsuranceCheckListData.setpHCCHCDistrictHospitalAddressContactNo(activityLifeInsuranceReportingFormatBinding.editTextPHCCHCDistrictHospitalContactnumber.getText().toString().trim());
                            LifeInsuranceCheckListData.setPoliceStationCheck(activityLifeInsuranceReportingFormatBinding.editTextPoliceStationCheck.getText().toString().trim());
                            LifeInsuranceCheckListData.setCremationPlaceDetails(activityLifeInsuranceReportingFormatBinding.editTextCremationPlaceDetails.getText().toString().trim());
                            LifeInsuranceCheckListData.setFamilyMemberDetails(activityLifeInsuranceReportingFormatBinding.editTextFamilyMemberDetails.getText().toString().trim());
                            LifeInsuranceCheckListData.setNarrationOfTheCase(activityLifeInsuranceReportingFormatBinding.editTextNarrationOfCase.getText().toString().trim());
                            LifeInsuranceCheckListData.setOccupationDetailsConfirmed(IsOccupationDetailsConfirmed);
                            LifeInsuranceCheckListData.setAgeConfirmed(IsAgeConfirmed);
                            LifeInsuranceCheckListData.setOccupationDetails(activityLifeInsuranceReportingFormatBinding.editTextOccupationDetails.getText().toString().trim());
                            LifeInsuranceCheckListData.setAboutAgeConfirmed(activityLifeInsuranceReportingFormatBinding.editTextAgeDetails.getText().toString().trim());
                            LifeInsuranceCheckListData.setInvestigatorLiCheckListID(-1);
                            LifeInsuranceCheckListData.setLiCheckListDataSaved(false);
                            LifeInsuranceCheckListData.setInvestigatorLiCheckListSubmittedDate(getCurrentDateTime());

                            List<LifeInsuranceCheckList.InvLifeInsuCheckListNeighbours> listNeighbours = new ArrayList<>();
                            for (Map.Entry<String, String> entry : mapNeighbourDetails.entrySet()) {
                                invLifeInsuCheckListNeighbours.setNeighbours_Name(entry.getValue());
                                invLifeInsuCheckListNeighbours.setNeighbours_ContactNumber(entry.getKey());
                                listNeighbours.add(invLifeInsuCheckListNeighbours);
                            }
                            LifeInsuranceCheckListData.setLstInvLifeInsuCheckListNeighbours(listNeighbours);

                            List<LifeInsuranceCheckList.InvLifeInsuCheckListMedicalStore> listMedicalStores = new ArrayList<>();
                            for (Map.Entry<String, String> entry : mapMedicalStoreDetails.entrySet()) {
                                invLifeInsuCheckListMedicalStore.setMedicalStoreCheckName(entry.getValue());
                                invLifeInsuCheckListMedicalStore.setMedicalStoreCheckAddress(entry.getKey());
                                listMedicalStores.add(invLifeInsuCheckListMedicalStore);
                            }
                            LifeInsuranceCheckListData.setLstInvLifeInsuCheckListMedicalStore(listMedicalStores);

                            List<LifeInsuranceCheckList.InvLifeInsuCheckListHospitalCheck> listHospitalChecks = new ArrayList<>();
                            for (Map.Entry<String, String> entry : mapHospitalDetails.entrySet()) {
                                lifeInsuCheckListHospitalCheck.setHospitalCheckNameAddress(entry.getValue());
                                lifeInsuCheckListHospitalCheck.setHospitalCheckNameContactNo(entry.getKey());
                                listHospitalChecks.add(lifeInsuCheckListHospitalCheck);
                            }
                            LifeInsuranceCheckListData.setLstInvLifeInsuCheckListHospitalCheck(listHospitalChecks);
                            postLifeInsurance(LifeInsuranceCheckListData);
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

        activityLifeInsuranceReportingFormatBinding.buttonAddHospitalCheck.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingInflatedId")
            @Override
            public void onClick(View v) {
                String hospitalCheckDetailsName = activityLifeInsuranceReportingFormatBinding.hospitalCheckDetailsName.getText().toString().trim();
                String hospitalCheckContactNumber = activityLifeInsuranceReportingFormatBinding.hospitalCheckContactNumber.getText().toString().trim();
                if (hospitalCheckDetailsName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill Hospital Name", Toast.LENGTH_LONG).show();
                    return;
                } else if (hospitalCheckContactNumber.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill Hospital Contact Number", Toast.LENGTH_LONG).show();
                    return;
                } else if (mapHospitalDetails.containsKey(hospitalCheckContactNumber)) {
                    Toast.makeText(getApplicationContext(), "Hospital Details already exists", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    layoutInflaterDetails = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    layoutInflaterDetails = LayoutInflater.from(LifeInsuranceReportingFormatActivity.this);
                    final View view = layoutInflaterDetails.inflate(R.layout.li_reporting_neighbour_detail_layout, null);
                    TextView Name = view.findViewById(R.id.Name);
                    Name.setText(hospitalCheckDetailsName);
                    TextView Mobile = view.findViewById(R.id.Mobile);
                    Mobile.setText(hospitalCheckContactNumber);
                    ImageView deleteDetails = view.findViewById(R.id.deleteDetails);
                    mapHospitalDetails.put(hospitalCheckContactNumber, hospitalCheckDetailsName);
                    activityLifeInsuranceReportingFormatBinding.linearLayoutHospitalCheckDetailLayout.addView(view);
                    deleteDetails.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mapHospitalDetails.remove(hospitalCheckContactNumber);
                            activityLifeInsuranceReportingFormatBinding.linearLayoutHospitalCheckDetailLayout.removeView(view);
                            // Toast.makeText(getApplicationContext(), mapNeighbourDetails.size() + "", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                activityLifeInsuranceReportingFormatBinding.hospitalCheckContactNumber.setText("");
                activityLifeInsuranceReportingFormatBinding.hospitalCheckDetailsName.setText("");
                activityLifeInsuranceReportingFormatBinding.hospitalCheckDetailsName.requestFocus();
            }
        });

        activityLifeInsuranceReportingFormatBinding.buttonAddMedicalStoreCheck.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingInflatedId")
            @Override
            public void onClick(View v) {
                String medicalStoreCheckContactNumber = activityLifeInsuranceReportingFormatBinding.medicalStoreCheckContactNumber.getText().toString().trim();
                String medicalStoreCheckName = activityLifeInsuranceReportingFormatBinding.medicalStoreCheckName.getText().toString().trim();
                if (medicalStoreCheckName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill Medical Store Name", Toast.LENGTH_LONG).show();
                    return;
                } else if (medicalStoreCheckContactNumber.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill Medical Store Contact Number", Toast.LENGTH_LONG).show();
                    return;
                } else if (mapMedicalStoreDetails.containsKey(medicalStoreCheckContactNumber)) {
                    Toast.makeText(getApplicationContext(), "Medical Store Details already exists", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    layoutInflaterDetails = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    layoutInflaterDetails = LayoutInflater.from(LifeInsuranceReportingFormatActivity.this);
                    final View view = layoutInflaterDetails.inflate(R.layout.li_reporting_neighbour_detail_layout, null);
                    TextView Name = view.findViewById(R.id.Name);
                    Name.setText(medicalStoreCheckName);
                    TextView Mobile = view.findViewById(R.id.Mobile);
                    Mobile.setText(medicalStoreCheckContactNumber);
                    ImageView deleteDetails = view.findViewById(R.id.deleteDetails);
                    mapMedicalStoreDetails.put(medicalStoreCheckContactNumber, medicalStoreCheckName);
                    activityLifeInsuranceReportingFormatBinding.linearLayoutMedicalStoreCheckDetailLayout.addView(view);
                    deleteDetails.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mapMedicalStoreDetails.remove(medicalStoreCheckContactNumber);
                            activityLifeInsuranceReportingFormatBinding.linearLayoutMedicalStoreCheckDetailLayout.removeView(view);
                            // Toast.makeText(getApplicationContext(), mapNeighbourDetails.size() + "", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                activityLifeInsuranceReportingFormatBinding.medicalStoreCheckContactNumber.setText("");
                activityLifeInsuranceReportingFormatBinding.medicalStoreCheckName.setText("");
                activityLifeInsuranceReportingFormatBinding.medicalStoreCheckName.requestFocus();
            }
        });


        activityLifeInsuranceReportingFormatBinding.buttonAddNameContactNumber.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingInflatedId")
            @Override
            public void onClick(View v) {
                String neighbourDetailsContactNumber = activityLifeInsuranceReportingFormatBinding.neighbourDetailsContactNumber.getText().toString().trim();
                String neighbourDetailsName = activityLifeInsuranceReportingFormatBinding.neighbourDetailsName.getText().toString().trim();
                if (neighbourDetailsName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill Neighbour Name", Toast.LENGTH_LONG).show();
                    return;
                } else if (neighbourDetailsContactNumber.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill Neighbour Contact Number", Toast.LENGTH_LONG).show();
                    return;
                } else if (mapNeighbourDetails.containsKey(neighbourDetailsContactNumber)) {
                    Toast.makeText(getApplicationContext(), "Neighbour Details already exists", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    layoutInflaterDetails = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    layoutInflaterDetails = LayoutInflater.from(LifeInsuranceReportingFormatActivity.this);
                    final View view = layoutInflaterDetails.inflate(R.layout.li_reporting_neighbour_detail_layout, null);
                    TextView Name = view.findViewById(R.id.Name);
                    Name.setText(neighbourDetailsName);
                    TextView Mobile = view.findViewById(R.id.Mobile);
                    Mobile.setText(neighbourDetailsContactNumber);
                    ImageView deleteDetails = view.findViewById(R.id.deleteDetails);
                    mapNeighbourDetails.put(neighbourDetailsContactNumber, neighbourDetailsName);
                    activityLifeInsuranceReportingFormatBinding.linearLayoutNeighbourDetailLayout.addView(view);
                    deleteDetails.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mapNeighbourDetails.remove(neighbourDetailsContactNumber);
                            activityLifeInsuranceReportingFormatBinding.linearLayoutNeighbourDetailLayout.removeView(view);
                            // Toast.makeText(getApplicationContext(), mapNeighbourDetails.size() + "", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                activityLifeInsuranceReportingFormatBinding.neighbourDetailsContactNumber.setText("");
                activityLifeInsuranceReportingFormatBinding.neighbourDetailsName.setText("");
                activityLifeInsuranceReportingFormatBinding.neighbourDetailsName.requestFocus();
            }
        });


        // activityLifeInsuranceReportingFormatBinding.neighbourRecycler.setHasFixedSize(true);
        // activityLifeInsuranceReportingFormatBinding.neighbourRecycler.setAdapter(new LI_Reporting_NeighbourDetails_Adapter());

        activityLifeInsuranceReportingFormatBinding.rgOccupationDetails.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_OccupationDetailsConfirmed:
                        IsOccupationDetailsConfirmed = true;
                        break;
                    case R.id.rb_OccupationDetailsNotConfirmed:
                        IsOccupationDetailsConfirmed = false;
                        break;
                }
            }
        });

        activityLifeInsuranceReportingFormatBinding.rgAgeConfirmed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_OccupationDetailsConfirmed:
                        IsAgeConfirmed = true;
                        break;
                    case R.id.rb_OccupationDetailsNotConfirmed:
                        IsAgeConfirmed = false;
                        break;
                }
            }
        });

    }


    private void callListener() {

    }

    public void goBack(View view) {
        finish();
    }

    private void postLifeInsurance(LifeInsuranceCheckList.LifeInsuranceCheckListData lifeInsuranceCheckListData) {
        apiService = ApiClient.getClient(this).create(ApiService.class);
        if (ConnectionUtility.isConnected(LifeInsuranceReportingFormatActivity.this)) {
            Token = PrefUtils.getFromPrefs(LifeInsuranceReportingFormatActivity.this, PrefUtils.Token);
            Call<LifeInsuranceCheckList> lifeInsuranceCheckListCall = apiService.postLifeInsurance("Bearer " + Token, lifeInsuranceCheckListData);
            lifeInsuranceCheckListCall.enqueue(new Callback<LifeInsuranceCheckList>() {
                @Override
                public void onResponse(Call<LifeInsuranceCheckList> call, Response<LifeInsuranceCheckList> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            LifeInsuranceCheckList lifeInsuranceCheckList = response.body();
                            if (lifeInsuranceCheckList.getStatusCode() == 200) {
                                if (lifeInsuranceCheckList.getData().get(0).getLiCheckListDataSaved()) {
                                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(LifeInsuranceReportingFormatActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                    sweetAlertDialog.setTitleText("GREAT!");
                                    sweetAlertDialog.setCancelable(false);
                                    sweetAlertDialog.setContentText("Your Life Insurance Check list is submitted successfully");
                                    sweetAlertDialog.show();
                                    sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            sweetAlertDialog.dismiss();
                                            Intent intent = new Intent(LifeInsuranceReportingFormatActivity.this, FinalSubmissionAssignment_Activity.class);
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
                                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(LifeInsuranceReportingFormatActivity.this, SweetAlertDialog.ERROR_TYPE);
                                    sweetAlertDialog.setTitleText("SORRY!");
                                    sweetAlertDialog.setCancelable(false);
                                    sweetAlertDialog.setContentText("Your Life Insurance Check list is not submitted");
                                    sweetAlertDialog.show();
                                    sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            sweetAlertDialog.dismiss();
                                        }
                                    });
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), lifeInsuranceCheckList.getMsg().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {

                        }
                    } else {
                        ErrorLogAPICall apiCall= new ErrorLogAPICall(LifeInsuranceReportingFormatActivity.this,"LifeInsuranceReportingFormatActivity","LiCheckList/saveLifeInsuCheckListData", response.message()+" "+response.code(),"API Exception");
                        apiCall.saveErrorLog();
                        Toast.makeText(getApplicationContext(), "" + response.toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<LifeInsuranceCheckList> call, Throwable t) {
                    ErrorLogAPICall apiCall= new ErrorLogAPICall(LifeInsuranceReportingFormatActivity.this,"LifeInsuranceReportingFormatActivity","LiCheckList/saveLifeInsuCheckListData", t.getMessage(),"API Exception");
                    apiCall.saveErrorLog();
                    Toast.makeText(getApplicationContext(), "" + t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            ConnectionUtility.AlertDialogForNoConnectionAvaialble(LifeInsuranceReportingFormatActivity.this);
        }
    }

    public String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("en"));
        return sdf.format(new Date());
    }
}