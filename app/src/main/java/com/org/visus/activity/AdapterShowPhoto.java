package com.org.visus.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.org.visus.R;
import com.org.visus.adapters.FinalSubmissionAction_Adapter;
import com.org.visus.models.ActionUploadedFile;

import java.util.List;

public class AdapterShowPhoto extends RecyclerView.Adapter<AdapterShowPhoto.ViewHolder> {
    List<ActionUploadedFile> lstActionUploadedFiles;
    Context context;

    public AdapterShowPhoto(List<ActionUploadedFile> lstActionUploadedFiles, Context context) {
        this.lstActionUploadedFiles = lstActionUploadedFiles;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_photo_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (lstActionUploadedFiles != null && lstActionUploadedFiles.size() > 0) {
            if (lstActionUploadedFiles.get(position).getActivityFilePath() != null && lstActionUploadedFiles.get(position).getActivityFilePath().contains(".jpg") || lstActionUploadedFiles.get(position).getActivityFilePath().contains(".jpeg") || lstActionUploadedFiles.get(position).getActivityFilePath().contains(".png") || lstActionUploadedFiles.get(position).getActivityFilePath().contains(".gif")) {
                Glide.with(context).load(lstActionUploadedFiles.get(position).getActivityFilePath()).into(holder.imageView);
            }

        }
    }

    @Override
    public int getItemCount() {
        if (lstActionUploadedFiles != null && lstActionUploadedFiles.size() > 0) {
            return lstActionUploadedFiles.size();
        } else {
            return 0;

        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
