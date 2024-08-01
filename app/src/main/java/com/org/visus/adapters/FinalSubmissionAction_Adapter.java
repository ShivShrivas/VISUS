package com.org.visus.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.org.visus.R;
import com.org.visus.activity.Action_FinalSubmit_Activity;
import com.org.visus.activity.ShowPhotoActivity;
import com.org.visus.databinding.ActivityActionFinalSubmitBinding;
import com.org.visus.models.ActionUploadedFile;
import com.org.visus.models.InvReqActivityFile;
import com.org.visus.models.MyAssignment;

import java.util.List;

public class FinalSubmissionAction_Adapter extends RecyclerView.Adapter<FinalSubmissionAction_Adapter.MyViewHolder> {

    Action_FinalSubmit_Activity action_finalSubmit_activity;
    List<InvReqActivityFile.InvReqActivityFileData> data;
    MyAssignment.MyAssignmentData myPendingAssignmentData;
    DownloadManager manager;
    Context context;

    public FinalSubmissionAction_Adapter(Action_FinalSubmit_Activity action_finalSubmit_activity, List<InvReqActivityFile.InvReqActivityFileData> data, MyAssignment.MyAssignmentData myPendingAssignmentData, Context context) {
        this.action_finalSubmit_activity = action_finalSubmit_activity;
        this.data = data;
        this.myPendingAssignmentData = myPendingAssignmentData;
        this.context = context;
    }

    @NonNull
    @Override
    public FinalSubmissionAction_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.finalsubmitaction_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FinalSubmissionAction_Adapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (data != null) {
            if (data.get(position) != null) {
                holder.textViewClaimNumber.setText(myPendingAssignmentData.getClaimNumber() != null ? myPendingAssignmentData.getClaimNumber() : "N/A");
                holder.textViewProductSubCategory.setText(myPendingAssignmentData.getProductSubCategory() != null ? myPendingAssignmentData.getProductSubCategory() : "N/A");
                holder.textViewPolicyNumber.setText(myPendingAssignmentData.getPolicyNumber() != null ? myPendingAssignmentData.getPolicyNumber() : "N/A");
                holder.textViewInsuranceCompany.setText(myPendingAssignmentData.getInsuranceCompanyName() != null ? myPendingAssignmentData.getInsuranceCompanyName() : "N/A");
                holder.textViewAssignedDate.setText(myPendingAssignmentData.getInsuranceAssignedOnDate() != null ? myPendingAssignmentData.getInsuranceAssignedOnDate() : "N/A");
                holder.textViewTATForInvestigation.setText(myPendingAssignmentData.gettATForInvestigator() != null ? myPendingAssignmentData.gettATForInvestigator().toString() : "N/A");
                holder.textViewActionType.setText(data.get(position).getObjInvReqActivity().getInvestigatorReqActivityText() != null ? data.get(position).getObjInvReqActivity().getInvestigatorReqActivityText() : "N/A");
                holder.textViewLattitude.setText(data.get(position).getLatitudeAtClickingPhoto() != null ? data.get(position).getLatitudeAtClickingPhoto() : "N/A");
                holder.textViewLongitude.setText(data.get(position).getLongitudeAtClickingPhoto() != null ? data.get(position).getLongitudeAtClickingPhoto() : "N/A");
                holder.textViewAddress.setText(data.get(position).getAddressAtClickingPhoto() != null ? data.get(position).getAddressAtClickingPhoto() : "N/A");
                holder.textViewComments.setText(data.get(position).getInvestigatorComments() != null ? data.get(position).getInvestigatorComments() : "N/A");

                if (data.get(position).getLstActionUploadedFiles() != null && data.get(position).getLstActionUploadedFiles().size() > 0) {
                    int count = 0;
                    holder.ll_Pdf.setVisibility(View.GONE);
                    for (ActionUploadedFile invRqAtivityFileData : data.get(position).getLstActionUploadedFiles()) {
                        count++;
                        if (invRqAtivityFileData != null && invRqAtivityFileData.getActivityFilePath().contains(".jpg")
                                || invRqAtivityFileData.getActivityFilePath().contains(".jpeg") || invRqAtivityFileData.getActivityFilePath().contains(".png") || invRqAtivityFileData.getActivityFilePath().contains(".gif")) {
                            setImageMainLayout(invRqAtivityFileData.getActivityFilePath(), holder.ll_PhotoShow, count, position);
                            holder.horizontalScrollView.setVisibility(View.VISIBLE);
                            holder.ll_photo.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    if (data.get(position).getOriginalFileName() != null && !data.get(position).getOriginalFileName().equalsIgnoreCase("")) {
                        holder.ll_photo.setVisibility(View.GONE);
                        holder.ll_Pdf.setVisibility(View.VISIBLE);
                        holder.tv_pdf.setText(data.get(position).getOriginalFileName());
                    } else {
                        holder.ll_Pdf.setVisibility(View.GONE);
                        holder.ll_photo.setVisibility(View.GONE);
                    }
                }
            }
            holder.ll_PhotoShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(action_finalSubmit_activity, ShowPhotoActivity.class);
                    intent.putExtra("data", new Gson().toJson(data.get(position)));
                    action_finalSubmit_activity.startActivity(intent);
                }
            });

            holder.tv_pdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (data.get(position).getActivityFilePath() != null && data.get(position).getActivityFilePath().contains(".pdf")) {
                       /* manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                        Uri uri = Uri.parse(data.get(position).getActivityFilePath());
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                        long reference = manager.enqueue(request);*/
                    }
                }
            });
        }

    }

    private void setImageMainLayout(String activityFilePath, LinearLayout ll_photo, int tag, int pos) {
        LinearLayout relativeLayout = new LinearLayout(context);
        LinearLayout.LayoutParams paramsrelativeLayout = new
                LinearLayout.LayoutParams(350, 350);
        relativeLayout.setLayoutParams(paramsrelativeLayout);
        relativeLayout.getTag(tag);
        paramsrelativeLayout.setMargins(10, 5, 10, 5);
        relativeLayout.setOrientation(LinearLayout.VERTICAL);
        ll_photo.addView(relativeLayout);

        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams paramsClearImageView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  350);
        imageView.setLayoutParams(paramsClearImageView);
        imageView.getTag(tag);
        paramsClearImageView.gravity = Gravity.CENTER;
        relativeLayout.addView(imageView);
        Glide.with(action_finalSubmit_activity).load(activityFilePath).into(imageView);

    }




    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewClaimNumber, textViewProductSubCategory, textViewPolicyNumber, textViewInsuranceCompany, textViewAssignedDate, textViewTATForInvestigation, textViewActionType, textViewComments, textViewLattitude, textViewLongitude, textViewAddress, tv_pdf;
        ImageView imageViewPhoto;
        LinearLayout ll_photo, ll_Pdf, ll_PhotoShow;

        HorizontalScrollView horizontalScrollView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewClaimNumber = itemView.findViewById(R.id.textViewClaimNumber);
            textViewProductSubCategory = itemView.findViewById(R.id.textViewProductSubCategory);
            textViewPolicyNumber = itemView.findViewById(R.id.textViewPolicyNumber);
            textViewInsuranceCompany = itemView.findViewById(R.id.textViewInsuranceCompany);
            textViewAssignedDate = itemView.findViewById(R.id.textViewAssignedDate);
            textViewTATForInvestigation = itemView.findViewById(R.id.textViewTATForInvestigation);
            textViewActionType = itemView.findViewById(R.id.textViewActionType);
            textViewComments = itemView.findViewById(R.id.textViewComments);
            textViewLattitude = itemView.findViewById(R.id.textViewLattitude);
            textViewLongitude = itemView.findViewById(R.id.textViewLongitude);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            ll_photo = itemView.findViewById(R.id.ll_photo);
            ll_Pdf = itemView.findViewById(R.id.ll_Pdf);
            tv_pdf = itemView.findViewById(R.id.tv_pdf);
            ll_PhotoShow = itemView.findViewById(R.id.ll_PhotoShow);
            horizontalScrollView = itemView.findViewById(R.id.horizontalScrollView);
        }
    }
}
