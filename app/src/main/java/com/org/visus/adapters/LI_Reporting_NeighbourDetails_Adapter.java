package com.org.visus.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.org.visus.R;

public class LI_Reporting_NeighbourDetails_Adapter extends RecyclerView.Adapter<LI_Reporting_NeighbourDetails_Adapter.MyViewHolder> {
    @NonNull
    @Override
    public LI_Reporting_NeighbourDetails_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.li_reporting_neighbour_detail_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LI_Reporting_NeighbourDetails_Adapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
