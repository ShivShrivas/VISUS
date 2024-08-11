package com.org.visus.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
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
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;


import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Gravity;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.org.visus.databinding.ActivityMyActionBinding;
import com.org.visus.models.MyAssignment;
import com.org.visus.models.RequreActions;
import com.org.visus.models.SaveInvestigatorAction;

import com.org.visus.models.SaveInvestigatorActionOnlyData;
import com.org.visus.models.TakeActionPhoto;
import com.org.visus.utility.ConnectionUtility;
import com.org.visus.utility.PrefUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

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
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_Photo_PERMISSION_CODE = 200;
    private static final int ACTION_GET_CONTENT_PDF_WORD = 101;
    private static final int ACTION_GET_CONTENT_PDF_WORD_REQUEST = 1;
    String uriString = "";
    File myFile = null;
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
    String Token, InvestigatorID, DEVICEServerID;
    View view1;
    Bundle bundle;
    MyAssignment.MyAssignmentData myAssignmentData;
    String VisusService, VisusServiceID, AssessmentType = "";
    List<String> stringList = new ArrayList<>();
    Map<Integer, TakeActionPhoto> hashMapTakeActionPhoto = new HashMap<Integer, TakeActionPhoto>();
    String itemaction = "";
    /////////////////////////////////////////////
    Bitmap action_image_bitmap;
    File action_image_file;
    MultipartBody.Part body_action_image = null;
    private ArrayList<SaveInvestigatorAction.SaveInvestigatorActionData> arrayListSaveInvestigatorActionDataPhoto = new ArrayList();
    int count = 0;

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
            AssessmentType = bundle.getString("AssessmentType", "");
            actionBinding.textViewInsuranceType.setText(VisusService);
            actionBinding.textViewClaimNumber.setText(myAssignmentData.getClaimNumber());
            actionBinding.textViewAssignedDate.setText(myAssignmentData.getInsuranceAssignedOnDate());
            actionBinding.textViewTATForInvestigation.setText(myAssignmentData.gettATForInvestigator().toString());
            actionBinding.textViewLocation.setText("Location Not Available");
        }

        resetActionType();
        getActionType();
        callListener();
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MyActionActivity.this);

//       Dexter.withContext(getApplicationContext()).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION).withListener(new MultiplePermissionsListener() {
        Dexter.withContext(getApplicationContext()).withPermissions( Manifest.permission.ACCESS_FINE_LOCATION).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    getCurrentLocation();
                } else {
                    Toast.makeText(MyActionActivity.this, "Allow Location Permission First", Toast.LENGTH_SHORT).show();
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
                    if (arrayListSaveInvestigatorActionDataPhoto == null || arrayListSaveInvestigatorActionDataPhoto.size() == 0) {
                        Toast.makeText(MyActionActivity.this, "Take Photo", Toast.LENGTH_LONG).show();
                        openBottomSheetDialog();
                        return;
                    } else {
                        InvestigatorID = PrefUtils.getFromPrefs(MyActionActivity.this, PrefUtils.InvestigatorID);
                        DEVICEServerID = PrefUtils.getFromPrefs(MyActionActivity.this, PrefUtils.DEVICEServerID);

                        long insertPostInvestigatorActionData = 0;
                        SaveInvestigatorActionOnlyData saveInvestigatorActionData = new SaveInvestigatorActionOnlyData();
                        SaveInvestigatorActionOnlyData.InvestigatorActionData investigatorActionData = (saveInvestigatorActionData).new InvestigatorActionData();
                        investigatorActionData.setServiceTypeID(VisusServiceID != null ? VisusServiceID + "" : "0");
                        investigatorActionData.setInvID(InvestigatorID != null ? InvestigatorID : "0");
                        investigatorActionData.setSavedInvActionData("false");
                        investigatorActionData.setInvestigatorActionDataServerID("-1");
                        investigatorActionData.setClientID("-1");
                        investigatorActionData.setServiceID(myAssignmentData.getInsuranceDataID() != null ? myAssignmentData.getInsuranceDataID().toString() : "0");
                        investigatorActionData.setComments(actionBinding.actionComments.getText().toString().trim() != null ? actionBinding.actionComments.getText().toString().trim() : "0");
                        investigatorActionData.setActionID(actionTypeID != null ? actionTypeID : "0");
                        investigatorActionData.setLatitude(currentLocation != null ? currentLocation.getLatitude() + "" : "0.0");
                        investigatorActionData.setLongitude(currentLocation != null ? currentLocation.getLongitude() + "" : "0.0");
                        actionBinding.textViewLocation.getText().toString().trim();
                        investigatorActionData.setCellAddress(actionBinding.textViewLocation.getText().toString().trim());
                        investigatorActionData.setInvInsuranceRelID(myAssignmentData.getInvInsuranceRelID() != null ? myAssignmentData.getInvInsuranceRelID() : "0");
                        if (investigatorActionData != null) {
                            VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
                            visus_dataSource.open();
                            insertPostInvestigatorActionData = visus_dataSource.insertPostInvestigatorActionDataNew(investigatorActionData);
                            if (insertPostInvestigatorActionData > 0) {
                                if (arrayListSaveInvestigatorActionDataPhoto != null && arrayListSaveInvestigatorActionDataPhoto.size() > 0) {
                                    for (int i = 0; i < arrayListSaveInvestigatorActionDataPhoto.size(); i++) {
                                        SaveInvestigatorAction.SaveInvestigatorActionData arr = arrayListSaveInvestigatorActionDataPhoto.get(i);
                                        arr.setInvestigatorCaseActivity_ClientD(insertPostInvestigatorActionData + "");
                                        long val = visus_dataSource.insertPostInvestigatorActionDataPhoto(arr);
                                        arrayListSaveInvestigatorActionDataPhoto.get(i).setCommon_ID(String.valueOf(val));
                                    }
                                }
                            }
                            visus_dataSource.close();
                            if (insertPostInvestigatorActionData > 0) {
                                Toast.makeText(MyActionActivity.this, "Take Action Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MyActionActivity.this, "Something Went to Wrong", Toast.LENGTH_SHORT).show();
                            }
                            if (ConnectionUtility.isConnected(MyActionActivity.this)) {
                                openEsignPopup(investigatorActionData, insertPostInvestigatorActionData);

                            } else {
                                count = 0;
                                arrayListSaveInvestigatorActionDataPhoto.clear();
                                actionBinding.llPhotoImgShow.removeAllViews();
                                actionBinding.pdf.setVisibility(View.GONE);
                                actionBinding.actionComments.getText().clear();
                                actionBinding.spinnerSelectActionType.setSelection(0);
                            }
                        }
                    }
                }
            }
        });
        actionBinding.pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MyActionActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MyActionActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    openfile();
                }
            }
        });
    }
    private void openEsignPopup(SaveInvestigatorActionOnlyData.InvestigatorActionData investigatorActionData, long insertPostInvestigatorActionData) {
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
                try {
                         postActionTypeDataNew(investigatorActionData, insertPostInvestigatorActionData);
                    } catch (Exception e) {
                         e.getMessage();
                    }
            }
        });

        saveDataOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                count = 0;
                arrayListSaveInvestigatorActionDataPhoto.clear();
                actionBinding.llPhotoImgShow.removeAllViews();
                actionBinding.pdf.setVisibility(View.GONE);
                actionBinding.actionComments.getText().clear();
                actionBinding.spinnerSelectActionType.setSelection(0);
                dialog.cancel();
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyActionActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                sweetAlertDialog.setTitleText("Great!!");
                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.setContentText("Data have been saved successfully!!");
                sweetAlertDialog.show();
                sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sweetAlertDialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
    }
    private void postActionTypeDataNew(SaveInvestigatorActionOnlyData.InvestigatorActionData investigatorActionData, long insertPostInvestigatorActionData) {
        ProgressDialog dialog = ProgressDialog.show(MyActionActivity.this, "Loading", "Please wait...", true);
        Token = PrefUtils.getFromPrefs(MyActionActivity.this, PrefUtils.Token);
        apiService = ApiClient.getClient(this).create(ApiService.class);
        Call<SaveInvestigatorActionOnlyData> call2 = apiService.postInvestigatorActionDataNew("Bearer " + Token, investigatorActionData);
        call2.enqueue(new Callback<SaveInvestigatorActionOnlyData>() {
            @Override
            public void onResponse(Call<SaveInvestigatorActionOnlyData> call, Response<SaveInvestigatorActionOnlyData> response) {
                if (response.body() != null) {
                    final SaveInvestigatorActionOnlyData saveInvestigatorActionData = response.body();
                    if (investigatorActionData != null) {
                        Log.d("error1", saveInvestigatorActionData.getStatusCode() + "");
                        if (saveInvestigatorActionData.getStatusCode() == 200) {
                            if (arrayListSaveInvestigatorActionDataPhoto != null && arrayListSaveInvestigatorActionDataPhoto.size() > 0) {
                                if (saveInvestigatorActionData.getData().get(0).getInvestigatorActionDataServerID() != null) {
                                    VISUS_DataSource visus_dataSource1 = new VISUS_DataSource(getApplicationContext());
                                    visus_dataSource1.open();
                                    long valMainData = visus_dataSource1.delete_tblPostInvestigatorActionData(String.valueOf(insertPostInvestigatorActionData));
                                    visus_dataSource1.open();
                                    postActionTypeData(saveInvestigatorActionData.getData().get(0).getInvestigatorActionDataServerID(), dialog, insertPostInvestigatorActionData);
                                } else {
                                    setSweetDailog(saveInvestigatorActionData.getMsg(), "Sorry!!!", 0);
                                }
                            }
                        } else {
                            setSweetDailog(saveInvestigatorActionData.getMsg(), "Sorry!!!", 0);
                        }
                    }
                } else {
                    if (response.code() == 401) {
                        setSweetDailog(response.message(), "Sorry!!!", response.code());
                    } else {
                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<SaveInvestigatorActionOnlyData> call, Throwable t) {
                dialog.dismiss();
                call.cancel();
            }
        });
    }

    private void setSweetDailog(String Content, String titleText, int responcecode) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(MyActionActivity.this, SweetAlertDialog.SUCCESS_TYPE);
        sweetAlertDialog.setTitleText(titleText);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setContentText(Content);
        sweetAlertDialog.show();
        sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (responcecode == 401) {
                    PrefUtils.removeFromSharedPreferences(MyActionActivity.this, PrefUtils.Token);
                    Intent intent = new Intent(MyActionActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    sweetAlertDialog.dismiss();
                } else {
                    sweetAlertDialog.dismiss();
                    // finish();
                }
            }
        });
    }

    private void postActionTypeData(String ServerID, ProgressDialog dialog, long insertPostInvestigatorActionData) {
        if (arrayListSaveInvestigatorActionDataPhoto != null && arrayListSaveInvestigatorActionDataPhoto.size() > 0) {
            if (arrayListSaveInvestigatorActionDataPhoto.get(count) != null) {
                MultipartBody.Part body_action_image1 = null;
                SaveInvestigatorAction.SaveInvestigatorActionData saveData = arrayListSaveInvestigatorActionDataPhoto.get(count);
               /* ProgressDialog dialogNew = ProgressDialog.show(MyActionActivity.this, "Loading",
                        "Please wait...", true);*/
                apiService = ApiClient.getClient(this).create(ApiService.class);
                Token = PrefUtils.getFromPrefs(MyActionActivity.this, PrefUtils.Token);
                InvestigatorID = PrefUtils.getFromPrefs(MyActionActivity.this, PrefUtils.InvestigatorID);
                Log.d("ServerID", ServerID);
                if (saveData.getOriginalFileName() != null) {
                    if (saveData.getOriginalFileName().contains("Pdf_")) {
                        String uri = saveData.getOriginalFileName().split("Pdf_")[1];
                        File file_action_image = new File(uri);
                        if (file_action_image.exists() && file_action_image.canRead()) {
                            RequestBody request_file_action_photo = RequestBody.create(MediaType.parse("multipart/form-data"), file_action_image);
                            body_action_image1 = MultipartBody.Part.createFormData("OriginalFIleName", !file_action_image.getName().equalsIgnoreCase("") ? file_action_image.getName() : "N?A", request_file_action_photo);
                        }
                    } else {
                        File file_action_image = new File(saveData.getOriginalFileName());
                        RequestBody request_file_action_photo = RequestBody.create(MediaType.parse("multipart/form-data"), file_action_image);
                        body_action_image1 = MultipartBody.Part.createFormData("OriginalFIleName", !file_action_image.getName().equalsIgnoreCase("") ? file_action_image.getName() : "N?A", request_file_action_photo);
                    }
                }
                if (body_action_image1 != null) {
                    Call<SaveInvestigatorAction> call2 = apiService.postInvestigatorActionDataPhoto("Bearer " + Token, body_action_image1, VisusServiceID, myAssignmentData.getInsuranceDataID().toString(), InvestigatorID, actionTypeID, "-1", myAssignmentData.getInvInsuranceRelID(), ServerID);
                    call2.enqueue(new Callback<SaveInvestigatorAction>() {
                        @Override
                        public void onResponse(Call<SaveInvestigatorAction> call, Response<SaveInvestigatorAction> response) {
                            if (response.body() != null) {
                                final SaveInvestigatorAction saveInvestigatorAction = response.body();
                                if (saveInvestigatorAction != null) {
                                    VISUS_DataSource visus_dataSource1 = new VISUS_DataSource(getApplicationContext());
                                    visus_dataSource1.open();
                                    int abc = visus_dataSource1.updatePostInvestigatorActionDataPhotoTwoPra(String.valueOf(insertPostInvestigatorActionData), saveData.getCommon_ID());
                                    visus_dataSource1.close();
                                    Log.d("error1", saveInvestigatorAction.getStatusCode() + "");
                                    if (saveInvestigatorAction.getStatusCode() == 200) {
                                        count++;
                                        if (arrayListSaveInvestigatorActionDataPhoto.size() == count) {
                                            Log.d("countGreat", count + "");
                                            if (dialog != null && dialog.isShowing()) {
                                                dialog.dismiss();
                                            }
                                            count = 0;
                                            arrayListSaveInvestigatorActionDataPhoto.clear();
                                            actionBinding.llPhotoImgShow.removeAllViews();
                                            actionBinding.pdf.setVisibility(View.GONE);
                                            actionBinding.actionComments.getText().clear();
                                            actionBinding.spinnerSelectActionType.setSelection(0);
                                            setSweetDailog(saveInvestigatorAction.getMsg(), "Great!", 0);
                                        } else {
                                            Log.d("count", count + "");
                                            postActionTypeData(ServerID, dialog, insertPostInvestigatorActionData);
                                        }
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
                                        }
                                    });
                                } else {
                                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<SaveInvestigatorAction> call, Throwable t) {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            call.cancel();
                        }
                    });
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }


            }
        }
    }

    private void callListener() {
        actionBinding.spinnerSelectActionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("TAG", "onItemSelected: "+position);
                if (position != 0) {
                    actionType = String.valueOf(adapterView.getItemAtPosition(position));
                    actionTypeID = IDActionTypeList.get(position - 1);
                    if (position == 19) {
                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S) {
                            if (ContextCompat.checkSelfPermission(MyActionActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(MyActionActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, ACTION_GET_CONTENT_PDF_WORD_REQUEST);
                            } else {
                                openPdfAndWordFile();
                            }
                        }else{
                            openPdfAndWordFile();
                        }


                    } else {
                        openBottomSheetDialog();
                    }
                } else {
                    actionType = "Select Action Type";
                    actionTypeID = "";
                    // actionBinding.photoImg.setImageBitmap(null);
                    actionBinding.llPhotoImg.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        actionBinding.clickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

//                Dexter.withContext(MyActionActivity.this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA).withListener(new MultiplePermissionsListener() {
////                Dexter.withContext(MyActionActivity.this).withPermissions( Manifest.permission.CAMERA).withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
//                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
//
//
//                        } else {
//                            Toast.makeText(MyActionActivity.this, "Please Allow All the Permission", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
//                        permissionToken.continuePermissionRequest();
//                        Toast.makeText(MyActionActivity.this, "continue permission", Toast.LENGTH_SHORT).show();
//                    }
//                }).check();
            }
        });

      /*  actionBinding.claerPhotoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionType = "Select Action Type";
                actionTypeID = "";
                actionBinding.photoImg.setImageBitmap(null);
                actionBinding.llPhotoImg.setVisibility(View.GONE);
                if (body_action_image != null) {
                    body_action_image = null;
                }
            }
        });*/

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

    private void openPdfAndWordFile() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        galleryIntent.setType("application/pdf");
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(galleryIntent, ACTION_GET_CONTENT_PDF_WORD);
    }

    private void openBottomSheetDialog() {
        sheetDialog = new BottomSheetDialog(MyActionActivity.this, R.style.BottomSheetStyle);
        view1 = LayoutInflater.from(MyActionActivity.this).inflate(R.layout.bottomsheetdialog, findViewById(R.id.sheet_relative));
        camera_linear = view1.findViewById(R.id.camera_linear);
        photo_linear = view1.findViewById(R.id.photo_linear);
        photo_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PHOTO_CODE);
//                Dexter.withContext(MyActionActivity.this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
//                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
//                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                            startActivityForResult(intent, PHOTO_CODE);
//                        } else {
//                            Toast.makeText(MyActionActivity.this, "Please Allow All the Permission", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
//                        permissionToken.continuePermissionRequest();
//                        Toast.makeText(MyActionActivity.this, "continue permission", Toast.LENGTH_SHORT).show();
//                    }
//                }).check();
            }
        });
        /*actionBinding.photoImg.setOnClickListener(new View.OnClickListener() {
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
        });*/
        camera_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Dexter.withContext(MyActionActivity.this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA).withListener(new MultiplePermissionsListener() {
                                    Dexter.withContext(MyActionActivity.this).withPermissions( Manifest.permission.CAMERA).withListener(new MultiplePermissionsListener() {
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

//                Dexter.withContext(MyActionActivity.this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA).withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
//                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
//                            String fileName = "photo";
//                            File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//                            try {
//                                File imageFile = File.createTempFile(fileName, ".jpg", storageDirectory);
//                                currentPhotoPath = imageFile.getAbsolutePath();
//                                Uri imageUri = FileProvider.getUriForFile(MyActionActivity.this, "com.org.visus.fileprovider", imageFile);
//                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                                startActivityForResult(intent, CAMERA_REQUEST);
//                            } catch (Exception ex) {
//                            }
//                        } else {
//                            Toast.makeText(MyActionActivity.this, "Please Allow All the Permission", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
//                        permissionToken.continuePermissionRequest();
//                        Toast.makeText(MyActionActivity.this, "continue permission", Toast.LENGTH_SHORT).show();
//                    }
//                }).check();
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

    private String method_geocoder() {
        List<Address> addresses;
        String addresslco = "";
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            if (currentLocation != null) {
                addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                addresslco = addresses.get(0).getAddressLine(0);
                String feature = addresses.get(0).getFeatureName();
                Log.i("geo", "method_geocoder: " + addresslco);
//            Toast.makeText(DashboardActivity.this, "addresss    "+add, Toast.LENGTH_SHORT).show();
                actionBinding.textViewLocation.setText(addresslco.trim());
            } else {
                actionBinding.textViewLocation.setText("Location Not Available");
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return addresslco;

    }


    public void goBack(View view) {
        finish();
    }


    private void addStampToImage(Bitmap originalBitmap, String s, SaveInvestigatorAction.SaveInvestigatorActionData saveInvestigatorActionData) {
        int extraHeight = (int) (originalBitmap.getHeight() * 0.30);
        Bitmap newBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight() + extraHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.BLUE);
        canvas.drawBitmap(originalBitmap, 0, 0, null);
        Resources resources = getResources();
        float scale = resources.getDisplayMetrics().density;
        int y = originalBitmap.getHeight();
        int size = extraHeight / 8;
        String[] lines = s.split("[!]");
        for (String text : lines) {
            Paint pText = new Paint();
            pText.setColor(Color.WHITE);
            pText.setTextSize((float) (newBitmap.getHeight() * 0.001));
            setTextSizeForWidth(pText, (int) (originalBitmap.getHeight() * 0.20), text);
            Rect bounds = new Rect();
            pText.getTextBounds(text, 0, text.length(), bounds);
            Rect textHeightWidth = new Rect();
            pText.getTextBounds(text, 0, text.length(), textHeightWidth);
            y = y + size;
            canvas.drawText(text, 50, y, pText);
        }
        //actionBinding.photoImg.setImageBitmap(newBitmap);

        actionBinding.llPhotoImg.setVisibility(View.VISIBLE);
        action_image_bitmap = newBitmap;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String formattedDate = df.format(c.getTime());
        persistImage(newBitmap, newBitmap, formattedDate, "action_photo", saveInvestigatorActionData);
        // todo on photo work add arraryList
    }

    private void setImageBitmapDynamic(Bitmap newBitmap, int tag) {
        LinearLayout relativeLayout = new LinearLayout(this);
        LinearLayout.LayoutParams paramsrelativeLayout = new LinearLayout.LayoutParams(400, ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(paramsrelativeLayout);
        relativeLayout.getTag(tag);
        paramsrelativeLayout.setMargins(10, 5, 10, 5);
        relativeLayout.setOrientation(LinearLayout.VERTICAL);
        actionBinding.llPhotoImgShow.addView(relativeLayout);

        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams paramsClearImageView = new LinearLayout.LayoutParams(60, 60);
        imageView.setLayoutParams(paramsClearImageView);
        imageView.getTag(tag);
        paramsClearImageView.gravity = Gravity.END;
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.baseline_clear_24));
        relativeLayout.addView(imageView);

        View v = new ImageView(getBaseContext());
        ImageView imageViewImg;
        imageViewImg = new ImageView(v.getContext());
        LinearLayout.LayoutParams paramsClearIvageView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageViewImg.setLayoutParams(paramsClearIvageView);
        paramsClearIvageView.gravity = Gravity.START;
        imageViewImg.setImageBitmap(newBitmap);
        imageViewImg.getTag(tag);
        relativeLayout.addView(imageViewImg);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrayListSaveInvestigatorActionDataPhoto != null && arrayListSaveInvestigatorActionDataPhoto.size() > 0) {
                    arrayListSaveInvestigatorActionDataPhoto.remove(tag);
                    actionBinding.llPhotoImgShow.removeViewAt(tag);
                }
            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrayListSaveInvestigatorActionDataPhoto != null && arrayListSaveInvestigatorActionDataPhoto.size() > 0) {
                    showPhotoAlterDialog(arrayListSaveInvestigatorActionDataPhoto.get(tag).getActivityFilePath());

                }
            }
        });
    }

    private void showPhotoAlterDialog(String path) {
        Bitmap action_image_bitmap = BitmapFactory.decodeFile(path);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Photo");
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout_photo, null);
        builder.setView(customLayout);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        ImageView imageView = customLayout.findViewById(R.id.clearImage);
        ImageView imageViewForShow = customLayout.findViewById(R.id.imageView);
        if (action_image_bitmap != null) {
            imageViewForShow.setImageBitmap(action_image_bitmap);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    private void setTextSizeForWidth(Paint paint, float desiredHeight, String text) {
        final float testTextSize = 10f;
        float textSize = paint.getTextSize();
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        float desiredTextSize = testTextSize * desiredHeight / bounds.height();
        paint.setTextSize(textSize * 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK /*&& data != null*/) {
                action_image_bitmap = BitmapFactory.decodeFile(currentPhotoPath);
                int quality = 10; // You can adjust the quality (0-100)
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                action_image_bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
                String INV_code = PrefUtils.getFromPrefs(MyActionActivity.this, PrefUtils.INV_code);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Investigator Code: " + INV_code + "!");
                stringBuilder.append("Action Type: " + actionType + "!");
                if (myAssignmentData != null) {
                    stringBuilder.append("Claim Number: " + myAssignmentData.getClaimNumber() + "!");
                }
                if (currentLocation != null) {
                    stringBuilder.append("Latitude: " + currentLocation.getLatitude() + " , Longitude: " + currentLocation.getLongitude() + "!");
                    stringBuilder.append("Longitude: " + currentLocation.getLongitude() + "!");
                    stringBuilder.append("Address: " + method_geocoder() + "!");
                }
                stringBuilder.append("Submitted Date: " + getCurrentDateTime());
                Log.d("TAG", "onActivityResult: "+currentLocation.getLatitude());
                if (action_image_bitmap != null) {
                    SaveInvestigatorAction saveInvestigatorAction = new SaveInvestigatorAction();
                    SaveInvestigatorAction.SaveInvestigatorActionData saveInvestigatorActionData = (saveInvestigatorAction).new SaveInvestigatorActionData();
                    saveInvestigatorActionData.setVisusServicesID(VisusServiceID);
                    saveInvestigatorActionData.setInvestigatorRequiredActivityID(actionTypeID);
                    saveInvestigatorActionData.setLatitudeAtClickingPhoto(currentLocation != null ? currentLocation.getLatitude() + "" : "0.0");
                    saveInvestigatorActionData.setLongitudeAtClickingPhoto(currentLocation != null ? currentLocation.getLongitude() + "" : "0.0");
                    saveInvestigatorActionData.setInvestigatorCaseActivityCaseInsuranceID(myAssignmentData.getInsuranceDataID() != null ? myAssignmentData.getInsuranceDataID() + "" : "0.0");
                    InvestigatorID = PrefUtils.getFromPrefs(MyActionActivity.this, PrefUtils.InvestigatorID);
                    saveInvestigatorActionData.setInvestigatorCaseActivityInvID(InvestigatorID != null ? InvestigatorID + "" : "0");
                    saveInvestigatorActionData.setInvInsuranceRelID(myAssignmentData.getInvInsuranceRelID() != null ? myAssignmentData.getInvInsuranceRelID() + "" : "0");
                    saveInvestigatorActionData.setInvestigatorComments(actionBinding.actionComments.getText().toString().trim() != null ? actionBinding.actionComments.getText().toString().trim() : "0");
                    saveInvestigatorActionData.setInsuranceAssignedOnDate(myAssignmentData.getInsuranceAssignedOnDate() != null ? myAssignmentData.getInsuranceAssignedOnDate() : "1900/10/01");
                    addStampToImage(action_image_bitmap, String.valueOf(stringBuilder), saveInvestigatorActionData);
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
            } else if (requestCode == PHOTO_CODE && resultCode == Activity.RESULT_OK && data != null) {

                try {

                } catch (Exception exception) {
                }
            } else if (requestCode == ACTION_GET_CONTENT_PDF_WORD && resultCode == RESULT_OK) {
                Uri uri = data.getData();
                uriString = getFilePathFromContentUriNew(Uri.parse(data.getDataString()));
                File myFile = new File(uriString);
                //    myFile = tranferOnOntherFolder(myFile1);
                if (myFile != null) {
                    SaveInvestigatorAction saveInvestigatorAction = new SaveInvestigatorAction();
                    SaveInvestigatorAction.SaveInvestigatorActionData saveInvestigatorActionData = (saveInvestigatorAction).new SaveInvestigatorActionData();
                    saveInvestigatorActionData.setVisusServicesID(VisusServiceID);
                    saveInvestigatorActionData.setInvestigatorRequiredActivityID(actionTypeID);
                    saveInvestigatorActionData.setLatitudeAtClickingPhoto(currentLocation != null ? currentLocation.getLatitude() + "" : "0.0");
                    saveInvestigatorActionData.setLongitudeAtClickingPhoto(currentLocation != null ? currentLocation.getLongitude() + "" : "0.0");
                    saveInvestigatorActionData.setInvestigatorCaseActivityCaseInsuranceID(myAssignmentData.getInsuranceDataID() != null ? myAssignmentData.getInsuranceDataID() + "" : "0.0");
                    InvestigatorID = PrefUtils.getFromPrefs(MyActionActivity.this, PrefUtils.InvestigatorID);
                    saveInvestigatorActionData.setInvestigatorCaseActivityInvID(InvestigatorID != null ? InvestigatorID + "" : "0");
                    saveInvestigatorActionData.setInvInsuranceRelID(myAssignmentData.getInvInsuranceRelID() != null ? myAssignmentData.getInvInsuranceRelID() + "" : "0");
                    saveInvestigatorActionData.setInvestigatorComments(actionBinding.actionComments.getText().toString().trim() != null ? actionBinding.actionComments.getText().toString().trim() : "0");
                    saveInvestigatorActionData.setInsuranceAssignedOnDate(myAssignmentData.getInsuranceAssignedOnDate() != null ? myAssignmentData.getInsuranceAssignedOnDate() : "1900/10/01");
                    saveInvestigatorActionData.setActivityFilePath(myFile.getPath());
                    saveInvestigatorActionData.setOriginalFileName("Pdf_" + myFile.getPath());
                    saveInvestigatorActionData.setFileSubmittionOnDate(getCurrentDateTime());
                    arrayListSaveInvestigatorActionDataPhoto.clear();
                    arrayListSaveInvestigatorActionDataPhoto.add(saveInvestigatorActionData);
                    actionBinding.pdf.setVisibility(View.VISIBLE);
                    actionBinding.pdf.setText(myFile.getName() != null && !myFile.getName().equalsIgnoreCase("") ? myFile.getName() : "N?A");
                } else {
                    Toast.makeText(MyActionActivity.this, "File is not uploaded yet!...Try Again", Toast.LENGTH_SHORT).show();
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
        }
    }

    private void compressBitmap(Bitmap action_image_bitmap) {
        int quality = 50; // You can adjust the quality (0-100)
        // Convert Bitmap to InputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        action_image_bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);

    }

    private File tranferOnOntherFolder(File myFile) {
        File destinationFile = null;
        try {
            File sourceFile = new File(myFile.getPath());
            File destinationFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/Visus");
            if (!sourceFile.exists()) {
                System.out.println("Source file does not exist.");
                return null;
            }
            if (!destinationFolder.exists()) {
                System.out.println("Destination folder does not exist.");
                return null;
            }

            String destinationFilePath = destinationFolder + "/" + sourceFile.getName();
            destinationFile = new File(destinationFilePath);
            // Use file input stream and file output stream for copying the file
            FileInputStream inStream = new FileInputStream(sourceFile);
            FileOutputStream outStream = new FileOutputStream(destinationFile);
            // Use file channels for efficient file copy
            FileChannel inChannel = inStream.getChannel();
            FileChannel outChannel = outStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inStream.close();
            outStream.close();
            Toast.makeText(this, "File copied to destination folder successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destinationFile;
    }
    public File compressImageToFile(File file, int targetSizeKB) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int quality = 100; // Start with high quality

        // Compress the image and check the file size
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
        while (outputStream.size() / 1024 > targetSizeKB && quality > 0) {
            // Reduce quality and compress again
            outputStream.reset(); // Clear the previous data
            quality -= 10; // Decrease quality
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
        }

        // Optionally resize the image if needed
        if (outputStream.size() / 1024 > targetSizeKB) {
            bitmap = resizeBitmap(bitmap, 1024); // Resize to a width of 1024px
            outputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
        }

        // Save the compressed image to a new file
        File compressedFile = new File(file.getParent(), "compressed_image.jpg");
        try (FileOutputStream fos = new FileOutputStream(compressedFile)) {
            fos.write(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return compressedFile;
    }

    private Bitmap resizeBitmap(Bitmap bitmap, int newWidth) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float aspectRatio = (float) height / width;
        int newHeight = Math.round(newWidth * aspectRatio);
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
    }

    public String getFilePathFromContentUriNew(Uri contentUri) {
        String filePath = "", fileName = "";
        try {
            InputStream inputStream = getContentResolver().openInputStream(contentUri);
            Random random = new Random();
            Random random1 = new Random();
            int x = random1.nextInt(10000);
            Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
            cursor.moveToFirst();
            fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            File desinationFile = new File(getFilesDir(), fileName);
            if (desinationFile.exists()) {
                desinationFile.delete();
            }
            OutputStream outputStream = new FileOutputStream(desinationFile);
            byte[] buffer = new byte[1024];
            int readByte = 0;
            while ((readByte = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, readByte);

            }
            if (desinationFile.exists()) {
                filePath = desinationFile.getPath();
                fileName = desinationFile.getName();
            }
            inputStream.close();
            outputStream.close();


        } catch (Exception e) {
            Log.e("FilePicker", "Error getting file path from content URI: " + e.getMessage());
        }

        return filePath;
    }

    public String getFilePathFromContentUri(Uri contentUri) {
        ContentResolver contentResolver = getContentResolver();
        String filePath = null;

        String[] projection = {OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE};
        Cursor cursor = contentResolver.query(contentUri, projection, null, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                String fileName = cursor.getString(nameIndex);

                // Assuming you want to save the file in the external storage directory
                File externalDir = Environment.getExternalStorageDirectory();
                File file = new File(externalDir, fileName);
                filePath = file.getAbsolutePath();
            }
        } catch (Exception e) {
            Log.e("FilePicker", "Error getting file path from content URI: " + e.getMessage());
        }

        return filePath;
    }

    public String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("en"));
        return sdf.format(new Date());
    }

    public void openfile() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri contentUri = Uri.parse("content://" + uriString);
        intent.setDataAndType(contentUri, "application/pdf");
        try {
            if (intent.resolveActivity(getPackageManager()) != null) {
                // Start the intent to open the PDF file
                startActivity(intent);
            } else {
                // Handle the case where there's no PDF viewer app installed
                // You can prompt the user to install a PDF viewer app in this case
            }
        } catch (Exception e) {
            Toast.makeText(MyActionActivity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void persistImage(Bitmap newPhoto, Bitmap photo, String formattedDate, String action_photo_type, SaveInvestigatorAction.SaveInvestigatorActionData saveInvestigatorActionData) {

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
                file_action_image= compressImageToFile(file_action_image, 400);
                saveInvestigatorActionData.setActivityFilePath(file_action_image.getPath());
                saveInvestigatorActionData.setOriginalFileName(action_image_file.toString());
                saveInvestigatorActionData.setFileSubmittionOnDate(formattedDate);


                RequestBody request_file_action_photo = RequestBody.create(MediaType.parse("multipart/form-data"), file_action_image);
                body_action_image = MultipartBody.Part.createFormData("OriginalFIleName", file_action_image.getName(), request_file_action_photo);
                arrayListSaveInvestigatorActionDataPhoto.add(saveInvestigatorActionData);
                if (arrayListSaveInvestigatorActionDataPhoto.size() > 0) {
                    setImageBitmapDynamic(newPhoto, arrayListSaveInvestigatorActionDataPhoto.size() - 1);
                }

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