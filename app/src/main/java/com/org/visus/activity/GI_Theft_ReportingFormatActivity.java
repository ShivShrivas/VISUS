package com.org.visus.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.org.visus.databinding.ActivityGiTheftReportingFormatBinding;
import com.org.visus.models.GiTheftInsuCheckList;
import com.org.visus.models.MyAssignment;

import java.util.ArrayList;

public class GI_Theft_ReportingFormatActivity extends AppCompatActivity {
    ActivityGiTheftReportingFormatBinding activityGiTheftReportingFormatBinding;
    Bundle bundle;
    ArrayList<GiTheftInsuCheckList.GiTheftInsuCheckListData> giTheftInsuCheckListData;
    MyAssignment.MyAssignmentData data;
    String VisusService, VisusServiceID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGiTheftReportingFormatBinding = ActivityGiTheftReportingFormatBinding.inflate(getLayoutInflater());
        setContentView(activityGiTheftReportingFormatBinding.getRoot());
        bundle = getIntent().getExtras();
        if (bundle != null) {
            giTheftInsuCheckListData = (ArrayList<GiTheftInsuCheckList.GiTheftInsuCheckListData>) getIntent().getSerializableExtra("GiTheftCheckListData");
            data = (MyAssignment.MyAssignmentData) getIntent().getSerializableExtra("Data");
            VisusService = bundle.getString("VisusService", "");
            VisusServiceID = bundle.getString("VisusServiceID", "");
        }
    }

    public void goBack(View view) {
        finish();
    }
}