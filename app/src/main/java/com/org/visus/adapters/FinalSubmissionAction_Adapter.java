package com.org.visus.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.org.visus.R;
import com.org.visus.activity.Action_FinalSubmit_Activity;
import com.org.visus.models.InvReqActivityFile;
import com.org.visus.models.MyAssignment;

import java.util.List;

public class FinalSubmissionAction_Adapter extends RecyclerView.Adapter<FinalSubmissionAction_Adapter.MyViewHolder> {

    Action_FinalSubmit_Activity action_finalSubmit_activity;
    List<InvReqActivityFile.InvReqActivityFileData> data;
    MyAssignment.MyAssignmentData myPendingAssignmentData;

    public FinalSubmissionAction_Adapter(Action_FinalSubmit_Activity action_finalSubmit_activity, List<InvReqActivityFile.InvReqActivityFileData> data, MyAssignment.MyAssignmentData myPendingAssignmentData) {
        this.action_finalSubmit_activity = action_finalSubmit_activity;
        this.data = data;
        this.myPendingAssignmentData = myPendingAssignmentData;
    }

    @NonNull
    @Override
    public FinalSubmissionAction_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.finalsubmitaction_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FinalSubmissionAction_Adapter.MyViewHolder holder, int position) {
        if (data != null) {
            if (data.get(position) != null) {
                holder.textViewClaimNumber.setText(myPendingAssignmentData.getClaimNumber() != null ? myPendingAssignmentData.getClaimNumber() : "N/A");
                holder.textViewPolicyNumber.setText(myPendingAssignmentData.getPolicyNumber() != null ? myPendingAssignmentData.getPolicyNumber() : "N/A");
                holder.textViewInsuranceCompany.setText(myPendingAssignmentData.getInsuranceCompanyName() != null ? myPendingAssignmentData.getInsuranceCompanyName() : "N/A");
                holder.textViewAssignedDate.setText(myPendingAssignmentData.getInsuranceAssignedOnDate() != null ? myPendingAssignmentData.getInsuranceAssignedOnDate() : "N/A");
                holder.textViewTATForInvestigation.setText(myPendingAssignmentData.gettATForInvestigator() != null ? myPendingAssignmentData.gettATForInvestigator().toString() : "N/A");
                holder.textViewActionType.setText(data.get(position).getObjInvReqActivity().getInvestigatorReqActivityText() != null ? data.get(position).getObjInvReqActivity().getInvestigatorReqActivityText() : "N/A");
                holder.textViewLattitude.setText(data.get(position).getLatitudeAtClickingPhoto() != null ? data.get(position).getLatitudeAtClickingPhoto() : "N/A");
                holder.textViewLongitude.setText(data.get(position).getLongitudeAtClickingPhoto() != null ? data.get(position).getLongitudeAtClickingPhoto() : "N/A");
                holder.textViewAddress.setText(data.get(position).getAddressAtClickingPhoto() != null ? data.get(position).getAddressAtClickingPhoto() : "N/A");
                holder.textViewComments.setText(data.get(position).getInvestigatorComments() != null ? data.get(position).getInvestigatorComments() : "N/A");
                Glide.with(action_finalSubmit_activity).load(data.get(position).getActivityFilePath()).into(holder.imageViewPhoto);
            }
        }

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

        TextView textViewClaimNumber, textViewPolicyNumber, textViewInsuranceCompany, textViewAssignedDate, textViewTATForInvestigation, textViewActionType, textViewComments, textViewLattitude, textViewLongitude, textViewAddress;
        ImageView imageViewPhoto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewClaimNumber = itemView.findViewById(R.id.textViewClaimNumber);
            textViewPolicyNumber = itemView.findViewById(R.id.textViewPolicyNumber);
            textViewInsuranceCompany = itemView.findViewById(R.id.textViewInsuranceCompany);
            textViewAssignedDate = itemView.findViewById(R.id.textViewAssignedDate);
            textViewTATForInvestigation = itemView.findViewById(R.id.textViewTATForInvestigation);
            textViewActionType = itemView.findViewById(R.id.textViewActionType);
            textViewComments = itemView.findViewById(R.id.textViewComments);
            textViewLattitude = itemView.findViewById(R.id.textViewLattitude);
            textViewLongitude = itemView.findViewById(R.id.textViewLongitude);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            imageViewPhoto = itemView.findViewById(R.id.imageViewPhoto);
        }
    }
}
