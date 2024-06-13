package com.example.bloodcollectionandroidapp.HomePagePortion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodcollectionandroidapp.R;

import java.util.List;

public class BloodRequestAdapter extends RecyclerView.Adapter<BloodRequestAdapter.ViewHolder> {

    private List<BloodRequest> bloodRequestList;
    private Context context;

    public BloodRequestAdapter(List<BloodRequest> bloodRequestList, Context context) {
        this.bloodRequestList = bloodRequestList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_page, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BloodRequest bloodRequest = bloodRequestList.get(position);
        holder.name.setText(bloodRequest.getName());
        holder.date.setText(bloodRequest.getDate());
        holder.location.setText(bloodRequest.getLocation());
        holder.bloodType.setText(bloodRequest.getBloodType());
    }

    @Override
    public int getItemCount() {
        return bloodRequestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date, location, bloodType;
        public Button donateButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            location = itemView.findViewById(R.id.location);
            bloodType = itemView.findViewById(R.id.blood_type);
            donateButton = itemView.findViewById(R.id.donate_button);
        }
    }
}
