package com.org.visus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.org.visus.R;
import com.org.visus.databinding.ActivityMyActionBinding;
import com.org.visus.databinding.ActivityShowPhotoBinding;
import com.org.visus.models.InvReqActivityFile;

public class ShowPhotoActivity extends AppCompatActivity {
    ActivityShowPhotoBinding activityShowPhotoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityShowPhotoBinding = ActivityShowPhotoBinding.inflate(getLayoutInflater());
        setContentView(activityShowPhotoBinding.getRoot());
        getLocalData();
    }

    private void getLocalData() {
        Bundle bundle = getIntent().getExtras();
        String data = bundle.getString("data");
        InvReqActivityFile.InvReqActivityFileData invReqActivityFileData = new Gson().fromJson(data, InvReqActivityFile.InvReqActivityFileData.class);
        if (invReqActivityFileData != null) {
            if (invReqActivityFileData.getLstActionUploadedFiles() != null && invReqActivityFileData.getLstActionUploadedFiles().size() > 0) {
                AdapterShowPhoto AdapterShowPhoto = new AdapterShowPhoto(invReqActivityFileData.getLstActionUploadedFiles(), ShowPhotoActivity.this);
                activityShowPhotoBinding.rcView.setAdapter(AdapterShowPhoto);
                activityShowPhotoBinding.rcView.setHasFixedSize(true);
            }
        }
    }
}