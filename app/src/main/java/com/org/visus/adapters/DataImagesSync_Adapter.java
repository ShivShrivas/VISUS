package com.org.visus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.org.visus.R;
import com.org.visus.activity.DataSyncActivity;
import com.org.visus.activity.datasource.VISUS_DataSource;
import com.org.visus.activity.interfaces.ImageItemsUploadListner;
import com.org.visus.models.SaveInvestigatorActionOnlyData;

import java.util.List;

public class DataImagesSync_Adapter extends RecyclerView.Adapter<DataImagesSync_Adapter.DataImagesSync_ViewHolder> {

    List<SaveInvestigatorActionOnlyData.InvestigatorActionData> investigatorActionDataList;
    Context context;

    ImageItemsUploadListner imageItemsUploadListner;
    public DataImagesSync_Adapter(Context context, List<SaveInvestigatorActionOnlyData.InvestigatorActionData> savedResponseList, ImageItemsUploadListner imageItemsUploadListner) {
        this.investigatorActionDataList=savedResponseList;
        this.context=context;
        this.imageItemsUploadListner=imageItemsUploadListner;
    }


    @NonNull
    @Override
    public DataImagesSync_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_data_images_sync,parent,false);
        return new DataImagesSync_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataImagesSync_ViewHolder holder, int position) {
        holder.tvItemName.setText("Data Record "+(position+1));
        holder.tvAddress.setText(investigatorActionDataList.get(position).getCellAddress());
        VISUS_DataSource visus_dataSource = new VISUS_DataSource(context);
        visus_dataSource.open();

        holder.imageCount.setText(visus_dataSource.getPostInvestigatorImagesDataCountBuClientId(investigatorActionDataList.get(position).getClientID())+"");
        visus_dataSource.close();
        holder.buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageItemsUploadListner.uploadButtonOnClick(investigatorActionDataList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return investigatorActionDataList.size();
    }

    public class DataImagesSync_ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageButton imageButton;
        TextView imageCount;
        TextView tvItemName;
        TextView tvAddress;
        Button buttonUpload;

        public DataImagesSync_ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            imageButton = itemView.findViewById(R.id.imageButton);
            imageCount = itemView.findViewById(R.id.imageCount);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            buttonUpload = itemView.findViewById(R.id.button_upload);
        }
    }
}
