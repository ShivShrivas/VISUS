package com.org.visus.holdgassessment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.org.visus.R;
import com.org.visus.activity.MyActionActivity;
import com.org.visus.models.MyAssignment;
import com.org.visus.holdgassessment.actvity.MyHoldAssessmentActivity;

import java.util.List;

public class HoldAssigmentAdapter extends RecyclerView.Adapter<HoldAssigmentAdapter.MyViewHolder> {

    MyHoldAssessmentActivity myPandingAssessmentActivity;
    List<MyAssignment.MyAssignmentData> data;
    Context context;
    String visusService;
    String visusServiceID;

    public HoldAssigmentAdapter(MyHoldAssessmentActivity myPandingAssessmentActivity, List<MyAssignment.MyAssignmentData> data, Context context, String visusService, String visusServiceID) {
        this.myPandingAssessmentActivity = myPandingAssessmentActivity;
        this.data = data;
        this.context = context;
        this.visusService = visusService;
        this.visusServiceID = visusServiceID;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (data != null) {
            if (data.get(position).getClaimNumber() != null) {
                holder.textViewClaimNumber.setText(data.get(position).getClaimNumber());
                holder.textViewPolicyNumber.setText(data.get(position).getPolicyNumber());
                holder.textViewInsuranceCompany.setText(data.get(position).getInsuranceCompanyName());
                holder.textViewAssignedDate.setText(data.get(position).getInsuranceAssignedOnDate());
                holder.textViewProductSubCategory.setText(data.get(position).getProductSubCategory());
                holder.textViewTATForInvestigation.setText(data.get(position).gettATForInvestigator().toString());
                holder.textViewInsuredOrClaimentName.setText(data.get(position).getInsuredOrClaimentName() != null ? data.get(position).getInsuredOrClaimentName() : "N/A");

                holder.take_action.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, MyActionActivity.class);
                        intent.putExtra("Data", data.get(position));
                        intent.putExtra("VisusService", visusService);
                        intent.putExtra("VisusServiceID", visusServiceID);
                        intent.putExtra("AssessmentType", "Hold");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
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
        TextView textViewClaimNumber, textViewPolicyNumber, textViewInsuranceCompany, textViewAssignedDate, textViewTATForInvestigation, textViewProductSubCategory, textViewInsuredOrClaimentName;
        Button take_action;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewClaimNumber = itemView.findViewById(R.id.textViewClaimNumber);
            textViewPolicyNumber = itemView.findViewById(R.id.textViewPolicyNumber);
            textViewInsuranceCompany = itemView.findViewById(R.id.textViewInsuranceCompany);
            textViewAssignedDate = itemView.findViewById(R.id.textViewAssignedDate);
            textViewTATForInvestigation = itemView.findViewById(R.id.textViewTATForInvestigation);
            textViewProductSubCategory = itemView.findViewById(R.id.textViewProductSubCategory);
            take_action = itemView.findViewById(R.id.take_action);
            textViewInsuredOrClaimentName = itemView.findViewById(R.id.textViewInsuredOrClaimentName);

        }
    }
}
