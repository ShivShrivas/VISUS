package com.org.visus.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import com.org.visus.R;
import com.org.visus.activity.datasource.VISUS_DataSource;
import com.org.visus.apis.ApiClient;
import com.org.visus.apis.ApiService;
import com.org.visus.apis.ErrorLogAPICall;
import com.org.visus.databinding.ActivityMyActionBinding;
import com.org.visus.models.MyAssignment;
import com.org.visus.models.RequreActions;
import com.org.visus.models.SaveInvestigatorAction;
import com.org.visus.models.TakeActionPhoto;
import com.org.visus.utility.ConnectionUtility;
import com.org.visus.utility.PrefUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyActionActivity extends AppCompatActivity {
    String currentPhotoPath = "";
    private static int addImagesCount = 0;
    ApiService apiService;
    ActivityMyActionBinding actionBinding;
    BottomSheetDialog sheetDialog;
    LinearLayout camera_linear, photo_linear;
    private int PHOTO_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int MY_Photo_PERMISSION_CODE = 200;
    List<String> actionTypeList = new ArrayList<>();
    List<String> IDActionTypeList = new ArrayList<>();
    String actionType = "Select Action Type";
    String actionTypeID = "";
    Geocoder geocoder;
    ImageView img_add, img_delete, img_action;
    TextInputEditText textInputEditTextDescriptionActionPhoto;
    TextView textViewSerialNumberActionPhoto;
    LayoutInflater layoutInflater;
    LinearLayout img_container;
    Location currentLocation = null;
    FusedLocationProviderClient fusedLocationProviderClient;
    String Token, InvestigatorID;
    View view1;
    Bundle bundle;
    MyAssignment.MyAssignmentData myAssignmentData;
    String VisusService, VisusServiceID;
    List<String> stringList = new ArrayList<>();
    Map<Integer, TakeActionPhoto> hashMapTakeActionPhoto = new HashMap<Integer, TakeActionPhoto>();
    String itemaction = "";
    /////////////////////////////////////////////
    Bitmap action_image_bitmap;
    File action_image_file;
    MultipartBody.Part body_action_image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBinding = ActivityMyActionBinding.inflate(getLayoutInflater());
        setContentView(actionBinding.getRoot());
        bundle = getIntent().getExtras();
        if (bundle != null) {
            myAssignmentData = (MyAssignment.MyAssignmentData) getIntent().getSerializableExtra("Data");
            VisusService = bundle.getString("VisusService", "");
            VisusServiceID = bundle.getString("VisusServiceID", "");
        }
        actionBinding.textViewInsuranceType.setText(VisusService);
        actionBinding.textViewClaimNumber.setText(myAssignmentData.getClaimNumber());
        actionBinding.textViewAssignedDate.setText(myAssignmentData.getInsuranceAssignedOnDate());
        actionBinding.textViewTATForInvestigation.setText(myAssignmentData.gettATForInvestigator().toString());
        actionBinding.textViewLocation.setText("Location Not Available");
        resetActionType();
        getActionType();
        callListener();
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MyActionActivity.this);

        Dexter.withContext(getApplicationContext()).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    getCurrentLocation();
                } else {
                    Toast.makeText(MyActionActivity.this, "Allow Permission First", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();

        actionBinding.buttonActionSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionType.equalsIgnoreCase("Select Action Type")) {
                    Toast.makeText(MyActionActivity.this, "Select Action Type", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    if (body_action_image == null) {
                        Toast.makeText(MyActionActivity.this, "Take Photo", Toast.LENGTH_LONG).show();
                        openBottomSheetDialog();
                        return;
                    } else {
                        if (ConnectionUtility.isConnected(MyActionActivity.this)) {
                            openEsignPopup();
                          //  postActionTypeData();
                        } else {
                            VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
                            visus_dataSource.open();
                            InvestigatorID = PrefUtils.getFromPrefs(MyActionActivity.this, PrefUtils.InvestigatorID);
                            long insertPostInvestigatorActionData = 0;
                            if (currentLocation == null) {
                                insertPostInvestigatorActionData = visus_dataSource.insertPostInvestigatorActionData(action_image_file.getAbsolutePath(), VisusServiceID, myAssignmentData.getInsuranceDataID().toString(), InvestigatorID, actionBinding.actionComments.getText().toString().trim(), actionTypeID, "0.0", "0.0", actionBinding.textViewLocation.getText().toString().trim(), myAssignmentData.getInvInsuranceRelID(),action_image_file);
                            } else {
                                insertPostInvestigatorActionData = visus_dataSource.insertPostInvestigatorActionData(action_image_file.getAbsolutePath(), VisusServiceID, myAssignmentData.getInsuranceDataID().toString(), InvestigatorID, actionBinding.actionComments.getText().toString().trim(), actionTypeID, String.valueOf(currentLocation.getLatitude()), String.valueOf(currentLocation.getLongitude()), actionBinding.textViewLocation.getText().toString().trim(), myAssignmentData.getInvInsuranceRelID(),action_image_file);
                            }
                            visus_dataSource.close();
                            if (insertPostInvestigatorActionData > 0) {
                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyActionActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                sweetAlertDialog.setTitleText("Great!");
                                sweetAlertDialog.setCancelable(false);
                                sweetAlertDialog.setContentText("Take Action Successfully");
                                sweetAlertDialog.show();
                                sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        sweetAlertDialog.dismiss();
                                        finish();
                                    }
                                });
                            } else {
                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyActionActivity.this, SweetAlertDialog.ERROR_TYPE);
                                sweetAlertDialog.setTitleText("Sorry!!!");
                                sweetAlertDialog.setCancelable(false);
                                sweetAlertDialog.setContentText("Take Action ERROR");
                                sweetAlertDialog.show();
                                sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        sweetAlertDialog.dismiss();
                                    }
                                });
                            }
                        }                        //postActionTypeData();
                    }
                }
            }
        });
    }

    private void openEsignPopup() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.option_popup);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        CardView btnSaveOnline = dialog.findViewById(R.id.btnSaveOnline);
        CardView saveDataOffline = dialog.findViewById(R.id.saveDataOffline);



        btnSaveOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                postActionTypeData();
            }
        });

        saveDataOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.cancel();
                VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
                visus_dataSource.open();
                InvestigatorID = PrefUtils.getFromPrefs(MyActionActivity.this, PrefUtils.InvestigatorID);
                long insertPostInvestigatorActionData = 0;
                if (currentLocation == null) {
                    insertPostInvestigatorActionData = visus_dataSource.insertPostInvestigatorActionData(action_image_file.getAbsolutePath(), VisusServiceID, myAssignmentData.getInsuranceDataID().toString(), InvestigatorID, actionBinding.actionComments.getText().toString().trim(), actionTypeID, "0.0", "0.0", actionBinding.textViewLocation.getText().toString().trim(), myAssignmentData.getInvInsuranceRelID(),action_image_file);
                } else {
                    insertPostInvestigatorActionData = visus_dataSource.insertPostInvestigatorActionData(action_image_file.getAbsolutePath(), VisusServiceID, myAssignmentData.getInsuranceDataID().toString(), InvestigatorID, actionBinding.actionComments.getText().toString().trim(), actionTypeID, String.valueOf(currentLocation.getLatitude()), String.valueOf(currentLocation.getLongitude()), actionBinding.textViewLocation.getText().toString().trim(), myAssignmentData.getInvInsuranceRelID(),action_image_file);
                }
                visus_dataSource.close();
                if (insertPostInvestigatorActionData > 0) {
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyActionActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                    sweetAlertDialog.setTitleText("Great!");
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.setContentText("Take Action Successfully");
                    sweetAlertDialog.show();
                    sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sweetAlertDialog.dismiss();
                            finish();
                        }
                    });
                } else {
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyActionActivity.this, SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setTitleText("Sorry!!!");
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.setContentText("Take Action ERROR");
                    sweetAlertDialog.show();
                    sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sweetAlertDialog.dismiss();
                        }
                    });
                }
            }
        });

        dialog.show();
    }

    private void postActionTypeData() {
        ProgressDialog dialog = ProgressDialog.show(MyActionActivity.this, "Loading", "Please wait...", true);
        apiService = ApiClient.getClient(this).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(MyActionActivity.this, PrefUtils.Token);
        InvestigatorID = PrefUtils.getFromPrefs(MyActionActivity.this, PrefUtils.InvestigatorID);
        if (currentLocation == null) {
            currentLocation.setLatitude(0.0);
            currentLocation.setLongitude(0.0);
        }

        Call<SaveInvestigatorAction> call2 = apiService.postInvestigatorActionData("Bearer " + Token, body_action_image, VisusServiceID, myAssignmentData.getInsuranceDataID().toString(), InvestigatorID, actionBinding.actionComments.getText().toString().trim(), actionTypeID, String.valueOf(currentLocation.getLatitude()), String.valueOf(currentLocation.getLongitude()), actionBinding.textViewLocation.getText().toString().trim(), "-1", myAssignmentData.getInvInsuranceRelID());
        call2.enqueue(new Callback<SaveInvestigatorAction>() {
            @Override
            public void onResponse(Call<SaveInvestigatorAction> call, Response<SaveInvestigatorAction> response) {

                dialog.dismiss();
                if (response.body() != null) {
                    final SaveInvestigatorAction saveInvestigatorAction = response.body();
                    if (saveInvestigatorAction != null) {
                        ///if (saveInvestigatorAction.getStatus() != null && saveInvestigatorAction.getStatus().equalsIgnoreCase("success")) {
                        if (saveInvestigatorAction.getStatusCode() == 200) {
                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyActionActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                            sweetAlertDialog.setTitleText("Great!");
                            sweetAlertDialog.setCancelable(false);
                            sweetAlertDialog.setContentText(saveInvestigatorAction.getMsg());
                            sweetAlertDialog.show();
                            sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    sweetAlertDialog.dismiss();
                                    finish();
                                }
                            });
                        } else {
                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyActionActivity.this, SweetAlertDialog.ERROR_TYPE);
                            sweetAlertDialog.setTitleText("Sorry!!!");
                            sweetAlertDialog.setCancelable(false);
                            sweetAlertDialog.setContentText(saveInvestigatorAction.getMsg());
                            sweetAlertDialog.show();
                            sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    sweetAlertDialog.dismiss();
                                }
                            });
                        }
                    }
                } else {
                    ErrorLogAPICall apiCall= new ErrorLogAPICall(MyActionActivity.this,"MyActionActivity","postActionTypeData", response.message(),"API Exception");
                    apiCall.saveErrorLog();
                    if (response.code() == 401) {
                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyActionActivity.this, SweetAlertDialog.ERROR_TYPE);
                        sweetAlertDialog.setTitleText("Sorry!!!");
                        sweetAlertDialog.setCancelable(false);
                        sweetAlertDialog.setContentText(response.message());
                        sweetAlertDialog.show();
                        sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PrefUtils.removeFromSharedPreferences(MyActionActivity.this, PrefUtils.Token);
                                Intent intent = new Intent(MyActionActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                sweetAlertDialog.dismiss();
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SaveInvestigatorAction> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());
                dialog.dismiss();
                ErrorLogAPICall apiCall= new ErrorLogAPICall(MyActionActivity.this,"MyActionActivity","MyAssignmentList/SaveInvestigatorActionData", t.toString(),"API Exception");
                apiCall.saveErrorLog();
                Toast.makeText(MyActionActivity.this, "fail " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callListener() {
        actionBinding.spinnerSelectActionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {
                    actionType = String.valueOf(adapterView.getItemAtPosition(position));
                    actionTypeID = IDActionTypeList.get(position - 1);
                    /*actionBinding.photoImg.setVisibility(View.GONE);*/
                    openBottomSheetDialog();
                } else {
                    actionType = "Select Action Type";
                    actionTypeID = "";
                    actionBinding.photoImg.setImageBitmap(null);
                    actionBinding.photoImg.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        actionBinding.takeactionBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingInflatedId")
            @Override
            public void onClick(View view) {
                addImagesCount = addImagesCount + 1;
                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layoutInflater = LayoutInflater.from(MyActionActivity.this);
                img_container = (LinearLayout) findViewById(R.id.img_container);
                final View view_action = layoutInflater.inflate(R.layout.camera_layout, null);
                img_add = view_action.findViewById(R.id.img_add);
                img_delete = view_action.findViewById(R.id.img_delete);
                img_action = view_action.findViewById(R.id.img_action);
                textViewSerialNumberActionPhoto = view_action.findViewById(R.id.textViewSerialNumberActionPhoto);
                textInputEditTextDescriptionActionPhoto = view_action.findViewById(R.id.textInputEditTextDescriptionActionPhoto);

                img_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openBottomSheetDialog();
                        img_container.getTag();
                        // hashMapTakeActionPhoto.get(0)
                        //  stringList.add(itemaction);
                    }
                });

                img_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      /* img_action.setImageDrawable(null);
                       Picasso.get().load(R.drawable.imgplaceholder).into(img_action);*/
                        //img_delete.setVisibility(View.VISIBLE);
                        //img_add.setVisibility(View.GONE);
                        //  stringList.remove(itemaction);
                        img_container.removeView(view_action);
                    }
                });
                // stringList.add(itemaction);
                img_container.addView(view_action);
                img_container.setTag(addImagesCount);
                textViewSerialNumberActionPhoto.setText(String.valueOf(addImagesCount));
                hashMapTakeActionPhoto.put(addImagesCount, null);
            }
        });
    }

    private void openBottomSheetDialog() {
        sheetDialog = new BottomSheetDialog(MyActionActivity.this, R.style.BottomSheetStyle);
        view1 = LayoutInflater.from(MyActionActivity.this).inflate(R.layout.bottomsheetdialog, findViewById(R.id.sheet_relative));
        camera_linear = view1.findViewById(R.id.camera_linear);
        photo_linear = view1.findViewById(R.id.photo_linear);
        photo_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(MyActionActivity.this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, PHOTO_CODE);
                        } else {
                            Toast.makeText(MyActionActivity.this, "Please Allow All the Permission", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                        Toast.makeText(MyActionActivity.this, "continue permission", Toast.LENGTH_SHORT).show();
                    }
                }).check();
            }
        });
        actionBinding.photoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(MyActionActivity.this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            String fileName = "photo";
                            File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                            try {
                                File imageFile = File.createTempFile(fileName, ".jpg", storageDirectory);
                                currentPhotoPath = imageFile.getAbsolutePath();
                                Uri imageUri = FileProvider.getUriForFile(MyActionActivity.this, "com.org.visus.fileprovider", imageFile);
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                startActivityForResult(intent, CAMERA_REQUEST);
                            } catch (Exception ex) {
                            }
                            /*Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, CAMERA_REQUEST);*/
                        } else {
                            Toast.makeText(MyActionActivity.this, "Please Allow All the Permission", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                        Toast.makeText(MyActionActivity.this, "continue permission", Toast.LENGTH_SHORT).show();
                    }
                }).check();
            }
        });
        camera_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(MyActionActivity.this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            String fileName = "photo";
                            File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                            try {
                                File imageFile = File.createTempFile(fileName, ".jpg", storageDirectory);
                                currentPhotoPath = imageFile.getAbsolutePath();
                                Uri imageUri = FileProvider.getUriForFile(MyActionActivity.this, "com.org.visus.fileprovider", imageFile);
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                startActivityForResult(intent, CAMERA_REQUEST);
                            } catch (Exception ex) {
                            }
                            /*Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, CAMERA_REQUEST);*/
                        } else {
                            Toast.makeText(MyActionActivity.this, "Please Allow All the Permission", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                        Toast.makeText(MyActionActivity.this, "continue permission", Toast.LENGTH_SHORT).show();
                    }
                }).check();
            }
        });
        sheetDialog.setContentView(view1);
        sheetDialog.show();
    }

    private void getActionType() {
        VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
        visus_dataSource.open();
        List<RequreActions.RequreActionsData> getRequreActionsData = visus_dataSource.getRequreActionsData();
        for (RequreActions.RequreActionsData requreActionsData : getRequreActionsData) {
            actionTypeList.add(requreActionsData.getInvestigatorReqActivityText());
            IDActionTypeList.add(requreActionsData.getInvestigatorReqActivityID().toString());
        }
        visus_dataSource.close();
        /*apiService = ApiClient.getClient(this).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(MyActionActivity.this, PrefUtils.Token);
        Call<RequreActions> call2 = apiService.getMyReqActionList("Bearer " + Token);
        call2.enqueue(new Callback<RequreActions>() {
            @Override
            public void onResponse(Call<RequreActions> call, Response<RequreActions> response) {
                if (response.body() != null) {
                    final RequreActions requreActions = response.body();
                    if (requreActions != null) {
                        if (requreActions.getStatus() != null && requreActions.getStatus().equalsIgnoreCase("success")) {
                            actionTypeList.clear();
                            resetActionType();
                            for (RequreActions.RequreActionsData requreActionsData : requreActions.getData()) {
                                actionTypeList.add(requreActionsData.getInvestigatorReqActivityText());
                                IDActionTypeList.add(requreActionsData.getInvestigatorReqActivityID().toString());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RequreActions> call, Throwable t) {
                call.cancel();
                Toast.makeText(MyActionActivity.this, "fail " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });*/
    }


    private void getCurrentLocation() {
        @SuppressLint("MissingPermission") Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    method_geocoder();
                } else {
                    actionBinding.textViewLocation.setText("Location Not Available");
                    currentLocation = null;
                }
            }
        });
    }

    private void method_geocoder() {
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            if (currentLocation != null) {
                addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String add = addresses.get(0).getAddressLine(0);
                String feature = addresses.get(0).getFeatureName();
                Log.i("geo", "method_geocoder: " + add);
//            Toast.makeText(DashboardActivity.this, "addresss    "+add, Toast.LENGTH_SHORT).show();
                actionBinding.textViewLocation.setText(add.trim());
            } else {
                actionBinding.textViewLocation.setText("Location Not Available");
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void goBack(View view) {
        finish();
    }

    /*private Bitmap writeTextBitmap(Bitmap bitmap, StringBuilder text) {
        Typeface tf = Typeface.create("Times New Roman", Typeface.NORMAL);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTypeface(tf);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(10);
        Rect textRect = new Rect();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            paint.getTextBounds(text, 0, text.length(), textRect);
        }
        Canvas canvas = new Canvas(bitmap);
        //If the text is bigger than the canvas , reduce the font size
        // if (textRect.width() >= (canvas.getWidth() - 4))     //the padding on either sides is considered as 4, so as to appropriately fit in the text
        //    paint.setTextSize(convertToPixels(MyActionActivity.this, 7));        //Scaling needs to be used for different dpi's

        //Calculate the positions
        int xPos = (canvas.getWidth() / 2) - 2;     //-2 is for regulating the x position offset

        //"- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));

        //canvas.drawText(String.valueOf(text), xPos, yPos, paint);

        int x = 5, y = 140;
        for (String line : text.toString().split("\n")) {
            canvas.drawText(line, x, y, paint);
            canvas.drawText(line, 30f, bitmap.getHeight(), paint);
            y += paint.descent() - paint.ascent();
        }
        return bitmap;
    }*/

//    private void addStampToImage(Bitmap originalBitmap, String s) {
//        Log.d("TAG", "addStampToImage: "+s);
//
//        int extraHeight = (int) (originalBitmap.getHeight() * 0.30);
//
//        Bitmap newBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight() + extraHeight, Bitmap.Config.ARGB_8888);
//
//        Canvas canvas = new Canvas(newBitmap);
//        canvas.drawColor(Color.BLUE);
//        canvas.drawBitmap(originalBitmap, 0, 0, null);
//
//        Resources resources = getResources();
//        float scale = resources.getDisplayMetrics().density;
//
//        int y = 4000;
//        String[] lines = s.split("[!]");
//        for (String text : lines) {
//            Paint pText = new Paint();
//            pText.setColor(Color.WHITE);
//            setTextSizeForWidth(pText, (int) (originalBitmap.getHeight() * 0.10), text);
//            Rect bounds = new Rect();
//            pText.getTextBounds(text, 0, text.length(), bounds);
//            Rect textHeightWidth = new Rect();
//            pText.getTextBounds(text, 0, text.length(), textHeightWidth);
//            canvas.drawText(text, 100, y, pText);
//            //canvas.drawText(text, (canvas.getWidth() / 4) - (textHeightWidth.width() / 4), originalBitmap.getHeight() + (extraHeight / 4) + (textHeightWidth.height() / 4), pText);
//        }
//        actionBinding.photoImg.setImageBitmap(newBitmap);
//        actionBinding.photoImg.setVisibility(View.VISIBLE);
//        action_image_bitmap = newBitmap;
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
//        String formattedDate = df.format(c.getTime());
//        persistImage(newBitmap, formattedDate, "action_photo");
//    }

    private void addStampToImage(Bitmap originalBitmap, String s) {
        Log.d("TAG", "addStampToImage: " + s);

        int extraHeight = (int) (originalBitmap.getHeight() * 0.22);

        Bitmap newBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight() + extraHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(newBitmap);

        // Draw the original bitmap on the canvas
        canvas.drawBitmap(originalBitmap, 0, 0, null);

        // Draw the blue background for the additional area
        Paint bluePaint = new Paint();
        bluePaint.setColor(Color.BLUE);
        canvas.drawRect(0, originalBitmap.getHeight(), originalBitmap.getWidth(), originalBitmap.getHeight() + extraHeight, bluePaint);

        Resources resources = getResources();
        float scale = resources.getDisplayMetrics().density;

        // Starting Y position for the text
        int y = originalBitmap.getHeight() + (int) (extraHeight * 0.1);

        String[] lines = s.split("[!]");
        for (String text : lines) {
            Paint pText = new Paint();
            pText.setColor(Color.WHITE);
            setTextSizeForWidth(pText, (int) (originalBitmap.getHeight() * 0.10), text);
            Rect bounds = new Rect();
            pText.getTextBounds(text, 0, text.length(), bounds);

            // Draw the text
            canvas.drawText(text, 40, y, pText);

            // Increment Y position for next line of text
            y += bounds.height() + 20; // Add some padding between lines
        }

        actionBinding.photoImg.setImageBitmap(newBitmap);
        actionBinding.photoImg.setVisibility(View.VISIBLE);
        action_image_bitmap = newBitmap;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String formattedDate = df.format(c.getTime());
        persistImage(newBitmap, formattedDate, "action_photo");
    }


    private void setTextSizeForWidth(Paint paint, float desiredHeight, String text) {
        final float testTextSize = 12f;
        float textSize = paint.getTextSize();
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        float desiredTextSize = testTextSize * desiredHeight / bounds.height();
        paint.setTextSize(textSize * (9f));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK /*&& data != null*/) {
                action_image_bitmap = BitmapFactory.decodeFile(currentPhotoPath);
                String INV_code = PrefUtils.getFromPrefs(MyActionActivity.this, PrefUtils.INV_code);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" ! !Investigator Code: " + INV_code + "!");
                if (myAssignmentData != null) {
                    stringBuilder.append("Claim Number: " + myAssignmentData.getClaimNumber() + "!");
                }
                if (currentLocation != null) {
                    stringBuilder.append("Latitude: " + currentLocation.getLatitude() + "!");
                    stringBuilder.append("Longitude: " + currentLocation.getLongitude() + "!");
                }
                stringBuilder.append("Submitted Date: " + getCurrentDateTime());
                //Bitmap writeTextBitmap = writeTextBitmap(action_image_bitmap, stringBuilder);
                if (action_image_bitmap != null) {
                    //addStampToImage(action_image_bitmap, stringBuilder);
                    addStampToImage(action_image_bitmap, String.valueOf(stringBuilder));
                } else {
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyActionActivity.this, SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setTitleText("ERROR!!!");
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.setContentText("Action Image Bitmap Is NULL");
                    sweetAlertDialog.show();
                    sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sweetAlertDialog.dismiss();
                        }
                    });
                }
                if (sheetDialog != null) {
                    sheetDialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Sheet Dialog Is NULL-1", Toast.LENGTH_LONG).show();
                }
                // img_add.setVisibility(View.GONE);
                // img_delete.setVisibility(View.VISIBLE);
            } else if (requestCode == PHOTO_CODE && resultCode == Activity.RESULT_OK && data != null) {
//            img_action.setVisibility(View.VISIBLE);
//            Uri uri = data.getData();
//            img_action.setImageURI(uri);
                try {
//                InputStream inputStream = getContentResolver().openInputStream(uri);
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                img_action.setImageBitmap(bitmap);
//                sheetDialog.dismiss();
//                img_add.setVisibility(View.GONE);
//                img_delete.setVisibility(View.VISIBLE);
//                multipart_uploadfile(uri,PHOTO_CODE);
                } catch (Exception exception) {
                }
            } else {
                if (sheetDialog != null) {
                    sheetDialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Sheet Dialog Is NULL-2", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(MyActionActivity.this, "File is not uploaded yet!...Try Again", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception exception) {
            throw new RuntimeException("After onActivityResult ERROR : " + exception.toString());
            /*SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyActionActivity.this, SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setTitleText("ERROR!!!");
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setContentText("ON ACTIVITY RESULT ERROR");
            sweetAlertDialog.show();
            sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sweetAlertDialog.dismiss();
                }
            });*/
        }
    }

    public String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("en"));
        return sdf.format(new Date());
    }

    private void persistImage(Bitmap photo, String formattedDate, String action_photo_type) {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/Visus");
        File filesDir = this.getFilesDir();
        try {
            path.mkdirs();
            if (photo == action_image_bitmap) {
                if (action_photo_type.equalsIgnoreCase("action_photo")) {
                    action_image_file = new File(path, formattedDate + ".jpg");
                }
                OutputStream os;
                try {
                    os = new FileOutputStream(path + "/" + formattedDate + ".jpg");
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                }

                File file_action_image = new File(action_image_file.toString());
                RequestBody request_file_action_photo = RequestBody.create(MediaType.parse("multipart/form-data"), file_action_image);
                body_action_image = MultipartBody.Part.createFormData("OriginalFIleName", file_action_image.getName(), request_file_action_photo);
            }
        } catch (Exception e) {
            Log.i("ERROR ", e.toString());
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyActionActivity.this, SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setTitleText("ERROR!!!");
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setContentText("ERROR " + e.toString());
            sweetAlertDialog.show();
            sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sweetAlertDialog.dismiss();
                }
            });
        }
    }


    private void resetActionType() {
        actionTypeList.clear();
        String list = "Select Action Type";
        actionTypeList.add(list);
        addSpinnerAdapter(actionBinding.spinnerSelectActionType, actionTypeList);
    }

    public void addSpinnerAdapter(Spinner spinner, List<String> listModels) {
        MySpinnerAdapter dataAdapter = new MySpinnerAdapter(MyActionActivity.this, android.R.layout.simple_spinner_item, listModels);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(dataAdapter);
    }


    private static class MySpinnerAdapter extends ArrayAdapter<String> {
        //Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/muli_semibold.ttf");
        private MySpinnerAdapter(Context context, int resource, List<String> items) {
            super(context, resource, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 0);
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setLayoutParams(params);
            view.setTextColor(Color.BLACK);
            //view.setTextSize(15);
            //view.setTypeface(font);
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            return view;
        }
    }
}