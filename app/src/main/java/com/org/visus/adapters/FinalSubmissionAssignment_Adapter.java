package com.org.visus.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.org.visus.R;
import com.org.visus.activity.Action_FinalSubmit_Activity;
import com.org.visus.activity.DashboardActivity;
import com.org.visus.activity.FinalSubmissionAssignment_Activity;
import com.org.visus.activity.GI_OD_ReportingFormatActivity;
import com.org.visus.activity.GI_PA_ReportingFormatActivity;
import com.org.visus.activity.GI_Theft_ReportingFormatActivity;
import com.org.visus.activity.LifeInsuranceReportingFormatActivity;
import com.org.visus.activity.MACT_ReportingFormatActivity;
import com.org.visus.apis.ApiClient;
import com.org.visus.apis.ApiService;
import com.org.visus.models.GiODInsuCheckList;
import com.org.visus.models.GiPAInsuCheckList;
import com.org.visus.models.GiTheftInsuCheckList;
import com.org.visus.models.LifeInsuranceCheckList;
import com.org.visus.models.MACTInsuCheckList;
import com.org.visus.models.MyAssignment;
import com.org.visus.utility.PrefUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalSubmissionAssignment_Adapter extends RecyclerView.Adapter<FinalSubmissionAssignment_Adapter.MyViewHolder> {
    FinalSubmissionAssignment_Activity finalSubmissionAssignment_activity;
    List<MyAssignment.MyAssignmentData> data;
    Context context;
    String visusService;
    String visusServiceID;
    ApiService apiService;
    String Token, InvestigatorID;


    public FinalSubmissionAssignment_Adapter(FinalSubmissionAssignment_Activity finalSubmissionAssignment_activity, List<MyAssignment.MyAssignmentData> data, Context context, String visusService, String visusServiceID) {
        this.finalSubmissionAssignment_activity = finalSubmissionAssignment_activity;
        this.data = data;
        this.context = context;
        this.visusService = visusService;
        this.visusServiceID = visusServiceID;
    }

    @NonNull
    @Override
    public FinalSubmissionAssignment_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.finalsubmit_myassignment_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FinalSubmissionAssignment_Adapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (data != null) {
            if (data.get(position).getClaimNumber() != null) {
                holder.textViewClaimNumber.setText(data.get(position).getClaimNumber());
                holder.textViewPolicyNumber.setText(data.get(position).getPolicyNumber());
                holder.textViewInsuranceCompany.setText(data.get(position).getInsuranceCompanyName());
                holder.textViewAssignedDate.setText(data.get(position).getInsuranceAssignedOnDate());
                holder.textViewTATForInvestigation.setText(data.get(position).gettATForInvestigator().toString());

                holder.view_action.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, Action_FinalSubmit_Activity.class);
                        intent.putExtra("Data", data.get(position));
                        intent.putExtra("VisusService", visusService);
                        intent.putExtra("VisusServiceID", visusServiceID);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });


                holder.finalsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (visusServiceID.equalsIgnoreCase("1")) {
                            getLifeInsuCheckList(data.get(position).getInsuranceDataID().toString());
                        } else if (visusServiceID.equalsIgnoreCase("2")) {
                            if (data.get(position).getProductID().equals(1)) {
                                //////1 is used for Theft//////
                                getGiTheftInsuCheckList(data.get(position).getInsuranceDataID().toString());
                            } else if (data.get(position).getProductID().equals(2)) {
                                //////2 is used for OD (Own Damage)//////
                                getGiODInsuCheckList(data.get(position).getInsuranceDataID().toString());
                            } else if (data.get(position).getProductID().equals(3)) {
                                //////3 is used of PA (Personal Accident)//////
                                getGiPAInsuCheckList(data.get(position).getInsuranceDataID().toString());
                            }
                        } else if (visusServiceID.equalsIgnoreCase("3")) {
                            getMACTInsuCheckList(data.get(position));
                        }
                    }
                });

                /*holder.finalsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(finalSubmissionAssignment_activity);
                        builder.setTitle("Final Submit Alert!");
                        builder.setMessage("Are you sure want to Final Submit ?");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String getCurrentDate = getCurrentDate();
                                String insuranceAssignedOnDate = data.get(position).getInsuranceAssignedOnDate();
                                Date currentDate = null;
                                Date insuranceAssignedDate = null;
                                try {
                                    currentDate = new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDate);
                                    insuranceAssignedDate = new SimpleDateFormat("dd/MM/yyyy").parse(insuranceAssignedOnDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Calendar c = Calendar.getInstance();
                                c.setTime(currentDate);
                                // int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

                                long diff = currentDate.getTime() - insuranceAssignedDate.getTime();
                                postFinalSubmit(data.get(position).getInsuranceDataID().toString(), TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
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
                });*/
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewClaimNumber, textViewPolicyNumber, textViewInsuranceCompany, textViewAssignedDate, textViewTATForInvestigation;
        Button view_action, finalsubmit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewClaimNumber = itemView.findViewById(R.id.textViewClaimNumber);
            textViewPolicyNumber = itemView.findViewById(R.id.textViewPolicyNumber);
            textViewInsuranceCompany = itemView.findViewById(R.id.textViewInsuranceCompany);
            textViewAssignedDate = itemView.findViewById(R.id.textViewAssignedDate);
            textViewTATForInvestigation = itemView.findViewById(R.id.textViewTATForInvestigation);
            view_action = itemView.findViewById(R.id.view_action);
            finalsubmit = itemView.findViewById(R.id.finalsubmit);
        }
    }

    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("en"));
        return sdf.format(new Date());
    }

    public String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("en"));
        return sdf.format(new Date());
    }

    private void getLifeInsuCheckList(String InsuranceDataID) {
        apiService = ApiClient.getClient(context).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(context, PrefUtils.Token);
        InvestigatorID = PrefUtils.getFromPrefs(context, PrefUtils.InvestigatorID);
        Call<LifeInsuranceCheckList> call2 = apiService.getLifeInsuCheckList("Bearer " + Token, InsuranceDataID, InvestigatorID);
        call2.enqueue(new Callback<LifeInsuranceCheckList>() {
            @Override
            public void onResponse(Call<LifeInsuranceCheckList> call, Response<LifeInsuranceCheckList> response) {
                if (response.body() != null) {
                    LifeInsuranceCheckList lifeInsuranceCheckList = response.body();
                    if (lifeInsuranceCheckList != null) {
                        if (lifeInsuranceCheckList.getStatus() != null && lifeInsuranceCheckList.getStatus().equalsIgnoreCase("success")) {
                            if (lifeInsuranceCheckList.getData().size() == 0) {

                            } else {
                                Intent intent = new Intent(context, LifeInsuranceReportingFormatActivity.class);
                                intent.putExtra("LifeInsuranceCheckListData", lifeInsuranceCheckList.getData().get(0));
                                intent.putExtra("VisusService", visusService);
                                intent.putExtra("VisusServiceID", visusServiceID);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        } else {
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LifeInsuranceCheckList> call, Throwable t) {
                call.cancel();
                Toast.makeText(context, "fail " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getGiODInsuCheckList(String InsuranceDataID) {
        apiService = ApiClient.getClient(context).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(context, PrefUtils.Token);
        InvestigatorID = PrefUtils.getFromPrefs(context, PrefUtils.InvestigatorID);
        Call<GiODInsuCheckList> call2 = apiService.getGiODInsuCheckList("Bearer " + Token);
        call2.enqueue(new Callback<GiODInsuCheckList>() {
            @Override
            public void onResponse(Call<GiODInsuCheckList> call, Response<GiODInsuCheckList> response) {
                if (response.body() != null) {
                    GiODInsuCheckList giODInsuCheckList = response.body();
                    if (giODInsuCheckList != null) {
                        if (giODInsuCheckList.getStatus() != null && giODInsuCheckList.getStatus().equalsIgnoreCase("success")) {
                            if (giODInsuCheckList.getData().size() == 0) {

                            } else {
                                Intent intent = new Intent(context, GI_OD_ReportingFormatActivity.class);
                                intent.putExtra("GiODCheckListData", (Serializable) giODInsuCheckList.getData());
                                intent.putExtra("Data", (Serializable) data);
                                intent.putExtra("VisusService", visusService);
                                intent.putExtra("VisusServiceID", visusServiceID);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        } else {
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GiODInsuCheckList> call, Throwable t) {
                call.cancel();
                Toast.makeText(context, "fail " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getGiTheftInsuCheckList(String InsuranceDataID) {
        apiService = ApiClient.getClient(context).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(context, PrefUtils.Token);
        InvestigatorID = PrefUtils.getFromPrefs(context, PrefUtils.InvestigatorID);
        Call<GiTheftInsuCheckList> call2 = apiService.getGiTheftInsuCheckList("Bearer " + Token);
        call2.enqueue(new Callback<GiTheftInsuCheckList>() {
            @Override
            public void onResponse(Call<GiTheftInsuCheckList> call, Response<GiTheftInsuCheckList> response) {
                if (response.body() != null) {
                    GiTheftInsuCheckList giTheftInsuCheckList = response.body();
                    if (giTheftInsuCheckList != null) {
                        if (giTheftInsuCheckList.getStatus() != null && giTheftInsuCheckList.getStatus().equalsIgnoreCase("success")) {
                            if (giTheftInsuCheckList.getData().size() == 0) {

                            } else {
                                Intent intent = new Intent(context, GI_Theft_ReportingFormatActivity.class);
                                intent.putExtra("GiTheftCheckListData", (Serializable) giTheftInsuCheckList.getData());
                                intent.putExtra("Data", (Serializable) data);
                                intent.putExtra("VisusService", visusService);
                                intent.putExtra("VisusServiceID", visusServiceID);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        } else {
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GiTheftInsuCheckList> call, Throwable t) {
                call.cancel();
                Toast.makeText(context, "fail " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getGiPAInsuCheckList(String InsuranceDataID) {
        apiService = ApiClient.getClient(context).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(context, PrefUtils.Token);
        InvestigatorID = PrefUtils.getFromPrefs(context, PrefUtils.InvestigatorID);
        Call<GiPAInsuCheckList> call2 = apiService.getGiPAInsuCheckList("Bearer " + Token);
        call2.enqueue(new Callback<GiPAInsuCheckList>() {
            @Override
            public void onResponse(Call<GiPAInsuCheckList> call, Response<GiPAInsuCheckList> response) {
                if (response.body() != null) {
                    GiPAInsuCheckList giPAInsuCheckList = response.body();
                    if (giPAInsuCheckList != null) {
                        if (giPAInsuCheckList.getStatus() != null && giPAInsuCheckList.getStatus().equalsIgnoreCase("success")) {
                            if (giPAInsuCheckList.getData().size() == 0) {

                            } else {
                                Intent intent = new Intent(context, GI_PA_ReportingFormatActivity.class);
                                intent.putExtra("GiPACheckListData", (Serializable) giPAInsuCheckList.getData());
                                intent.putExtra("Data", (Serializable) data);
                                intent.putExtra("VisusService", visusService);
                                intent.putExtra("VisusServiceID", visusServiceID);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        } else {
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GiPAInsuCheckList> call, Throwable t) {
                call.cancel();
                Toast.makeText(context, "fail " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getMACTInsuCheckList(MyAssignment.MyAssignmentData data) {
        apiService = ApiClient.getClient(context).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(context, PrefUtils.Token);
        InvestigatorID = PrefUtils.getFromPrefs(context, PrefUtils.InvestigatorID);
        Call<MACTInsuCheckList> call2 = apiService.getMACTInsuCheckList("Bearer " + Token);
        call2.enqueue(new Callback<MACTInsuCheckList>() {
            @Override
            public void onResponse(Call<MACTInsuCheckList> call, Response<MACTInsuCheckList> response) {
                if (response.body() != null) {
                    MACTInsuCheckList mactInsuCheckList = response.body();
                    if (mactInsuCheckList != null) {
                        if (mactInsuCheckList.getStatus() != null && mactInsuCheckList.getStatus().equalsIgnoreCase("success")) {
                            if (mactInsuCheckList.getData().size() == 0) {

                            } else {
                                //Toast.makeText(context, "List Size " + mactInsuCheckList.getData().size(), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(context, MACT_ReportingFormatActivity.class);
                                intent.putExtra("MACTClaimData", (Serializable) mactInsuCheckList.getData());
                                intent.putExtra("Data", data);
                                intent.putExtra("VisusService", visusService);
                                intent.putExtra("VisusServiceID", visusServiceID);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        } else {
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MACTInsuCheckList> call, Throwable t) {
                call.cancel();
                Toast.makeText(context, "fail " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void postFinalSubmit(String serviceID, long tat) {
        apiService = ApiClient.getClient(finalSubmissionAssignment_activity).create(ApiService.class);
        Token = PrefUtils.getFromPrefs(finalSubmissionAssignment_activity, PrefUtils.Token);
        InvestigatorID = PrefUtils.getFromPrefs(finalSubmissionAssignment_activity, PrefUtils.InvestigatorID);
        Call<Boolean> call2 = apiService.postMyAssignmentFinal("Bearer " + Token, visusServiceID, serviceID, InvestigatorID, getCurrentDateTime(), String.valueOf(tat));
        call2.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                if (response.body() != null) {
                    Log.i("response", "onResponse: " + response.body());
                    final Boolean finalSubmitAction = response.body();
                    if (finalSubmitAction != null) {
                        if (finalSubmitAction) {
                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(finalSubmissionAssignment_activity, SweetAlertDialog.SUCCESS_TYPE);
                            sweetAlertDialog.setTitleText("Great!");
                            sweetAlertDialog.setCancelable(false);
                            sweetAlertDialog.setContentText("Final submission has been done successfully");
                            sweetAlertDialog.show();
                            sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    sweetAlertDialog.dismiss();
                                    Intent intentPINActivity = new Intent(finalSubmissionAssignment_activity, DashboardActivity.class);
                                    intentPINActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intentPINActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intentPINActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    finalSubmissionAssignment_activity.getApplicationContext().startActivity(intentPINActivity);
                                }
                            });
                        } else {
                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(finalSubmissionAssignment_activity, SweetAlertDialog.ERROR_TYPE);
                            sweetAlertDialog.setTitleText("Sorry!!!");
                            sweetAlertDialog.setCancelable(false);
                            sweetAlertDialog.setContentText("Final submission fail");
                            sweetAlertDialog.show();
                            sweetAlertDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    sweetAlertDialog.dismiss();
                                }
                            });
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                call.cancel();
                Toast.makeText(finalSubmissionAssignment_activity, "fail " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
