package com.org.visus.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.org.visus.databinding.ActivityGiPaReportingFormatBinding;
import com.org.visus.models.GiPAInsuCheckList;
import com.org.visus.models.MyAssignment;

import java.util.ArrayList;

public class GI_PA_ReportingFormatActivity extends AppCompatActivity {
    ActivityGiPaReportingFormatBinding activityGiPaReportingFormatBinding;
    Bundle bundle;
    MyAssignment.MyAssignmentData data;
    String VisusService, VisusServiceID;
    ArrayList<GiPAInsuCheckList.GiPAInsuCheckListData> giPAInsuCheckListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGiPaReportingFormatBinding = ActivityGiPaReportingFormatBinding.inflate(getLayoutInflater());
        setContentView(activityGiPaReportingFormatBinding.getRoot());
        bundle = getIntent().getExtras();
        if (bundle != null) {
            giPAInsuCheckListData = (ArrayList<GiPAInsuCheckList.GiPAInsuCheckListData>) getIntent().getSerializableExtra("GiPACheckListData");
            data = (MyAssignment.MyAssignmentData) getIntent().getSerializableExtra("Data");
            VisusService = bundle.getString("VisusService", "");
            VisusServiceID = bundle.getString("VisusServiceID", "");
        }
    }

    public void goBack(View view) {
        finish();
    }
}