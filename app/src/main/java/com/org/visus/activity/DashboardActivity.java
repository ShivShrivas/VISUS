package com.org.visus.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.org.visus.databinding.ActivityDashboardBinding;
import com.org.visus.utility.PrefUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding dashboardBinding;
    private static final int REQUEST_CODE = 101;
    Geocoder geocoder;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(dashboardBinding.getRoot());
        String INV_code = PrefUtils.getFromPrefs(DashboardActivity.this, PrefUtils.INV_code);
        String INV_name = PrefUtils.getFromPrefs(DashboardActivity.this, PrefUtils.INV_name);
        dashboardBinding.title.setText("Welcome, " + INV_name);
        dashboardBinding.code.setText("(" + INV_code + ")");

        try {
            long date = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy");
            String dateString = sdf.format(date);
            dashboardBinding.currentdate.setText(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        dashboardBinding.myassigmentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, MyAssigmentWork_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        dashboardBinding.finalsubmitAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, FinalSubmissionAssignment_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(DashboardActivity.this);

        Dexter.withContext(getApplicationContext()).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    getCurrentLocation();
                } else {
                    Toast.makeText(DashboardActivity.this, "Allow Permission First", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();

            }
        }).check();
    }

    private void getCurrentLocation() {
        @SuppressLint("MissingPermission") Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    method_geocoder();
                }
            }
        });
    }

    private void method_geocoder() {
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String add = addresses.get(0).getAddressLine(0);
            String feature = addresses.get(0).getFeatureName();
            Log.i("geo", "method_geocoder: " + add);
//            Toast.makeText(DashboardActivity.this, "addresss    "+add, Toast.LENGTH_SHORT).show();
            dashboardBinding.location.setText(add);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}