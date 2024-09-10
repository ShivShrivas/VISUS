package com.org.visus.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.org.visus.R;

public class DataImagesSync_Adapter extends RecyclerView.Adapter<DataImagesSync_Adapter.DataImagesSync_ViewHolder> {
    @NonNull
    @Override
    public DataImagesSync_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_data_images_sync,parent,false);
        return new DataImagesSync_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataImagesSync_ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DataImagesSync_ViewHolder extends RecyclerView.ViewHolder{
        public DataImagesSync_ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
