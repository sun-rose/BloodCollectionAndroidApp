package com.example.bloodcollectionandroidapp.FindDonorFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodcollectionandroidapp.R;

import java.util.List;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.DonorViewHolder> {

    private List<Donor> donorList;

    public DonorAdapter(List<Donor> donorList) {
        this.donorList = donorList;
    }

    @NonNull
    @Override
    public DonorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.relative_donor_list, parent, false);
        return new DonorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonorViewHolder holder, int position) {
        Donor donor = donorList.get(position);
        holder.tvName.setText(donor.getName());
        holder.tvLastDonate.setText(donor.getLastDonate());
        holder.tvLocation.setText(donor.getLocation());
        holder.btnRequestBlood.setOnClickListener(v -> {
            // Handle request blood button click
        });
    }

    @Override
    public int getItemCount() {
        return donorList.size();
    }

    static class DonorViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvLastDonate, tvLocation;
        Button btnRequestBlood;

        public DonorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvLastDonate = itemView.findViewById(R.id.tvLastDonate);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            btnRequestBlood = itemView.findViewById(R.id.btnRequestBlood);

        }
    }
}

