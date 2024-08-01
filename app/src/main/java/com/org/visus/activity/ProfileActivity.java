package com.org.visus.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.org.visus.R;
import com.org.visus.databinding.ActivityProfileBinding;
import com.org.visus.utility.PrefUtils;

public class ProfileActivity extends AppCompatActivity {


    ActivityProfileBinding activityProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_profile);
        activityProfileBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(activityProfileBinding.getRoot());
        String INV_code = PrefUtils.getFromPrefs(ProfileActivity.this, PrefUtils.INV_code);
        String INV_name = PrefUtils.getFromPrefs(ProfileActivity.this, PrefUtils.INV_name);
        String ContactNumber = PrefUtils.getFromPrefs(ProfileActivity.this, PrefUtils.ContactNumber);
        String ContactNumber2 = PrefUtils.getFromPrefs(ProfileActivity.this, PrefUtils.ContactNumber2);
        String Email = PrefUtils.getFromPrefs(ProfileActivity.this, PrefUtils.Email);
        String PanNumber = PrefUtils.getFromPrefs(ProfileActivity.this, PrefUtils.PanNumber);
        String JoiningDate = PrefUtils.getFromPrefs(ProfileActivity.this, PrefUtils.JoiningDate);
        String Status = PrefUtils.getFromPrefs(ProfileActivity.this, PrefUtils.Status);


        activityProfileBinding.textViewInvestigatorName.setText(INV_name);
        activityProfileBinding.textViewInvestigatorCode.setText(INV_code);
        activityProfileBinding.textViewContactNumber.setText(ContactNumber);
        activityProfileBinding.textViewOtherContactNumber.setText(ContactNumber2);
        activityProfileBinding.textViewEmailID.setText(Email);
        activityProfileBinding.textViewPANNumber.setText(PanNumber);
        activityProfileBinding.textViewJoiningDate.setText(JoiningDate);
        activityProfileBinding.textViewStatus.setText(Status);
    }
}