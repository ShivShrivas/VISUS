package com.org.visus.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.org.visus.R;
import com.org.visus.databinding.ActivityGiOdReportingFormatBinding;
import com.org.visus.models.GiODInsuCheckList;
import com.org.visus.models.MyAssignment;

import java.util.ArrayList;

public class GI_OD_ReportingFormatActivity extends AppCompatActivity {
    ActivityGiOdReportingFormatBinding activityGiOdReportingFormatBinding;
    Bundle bundle;
    MyAssignment.MyAssignmentData data;
    String VisusService, VisusServiceID;
    ArrayList<GiODInsuCheckList.GiODInsuCheckListData> giODInsuCheckListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gi_od_reporting_format);
        activityGiOdReportingFormatBinding = ActivityGiOdReportingFormatBinding.inflate(getLayoutInflater());
        setContentView(activityGiOdReportingFormatBinding.getRoot());
        bundle = getIntent().getExtras();
        if (bundle != null) {
            giODInsuCheckListData = (ArrayList<GiODInsuCheckList.GiODInsuCheckListData>) getIntent().getSerializableExtra("GiODCheckListData");
            data = (MyAssignment.MyAssignmentData) getIntent().getSerializableExtra("Data");
            VisusService = bundle.getString("VisusService", "");
            VisusServiceID = bundle.getString("VisusServiceID", "");
        }
    }

    public void goBack(View view) {
        finish();
    }
}