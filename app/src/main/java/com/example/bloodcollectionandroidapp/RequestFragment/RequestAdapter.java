package com.example.bloodcollectionandroidapp.RequestFragment;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bloodcollectionandroidapp.HomePageFragment.BloodRequest;
import com.example.bloodcollectionandroidapp.R;


import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {


    private List<BloodRequest> requestList;
    private final Context cont;

    public RequestAdapter(List<BloodRequest> requests, Context con) {

        this.requestList = requests;
        this.cont = con;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BloodRequest request = requestList.get(position);

        holder.MacthDonorName.setText(request.getToDonorName());
        holder.tvBloodType.setText(request.getBloodType());
        holder.tvLastDonate.setText(request.getPost_date());
        holder.tvLocation.setText(request.getLocation());

        holder.btnRequestStatus.setText(request.getReqBloodStatus());
        holder.btnRequestStatus.setOnClickListener(v -> {
            // Update Firebase to accept or cancel the request
            // updateRequestStatus();
        });
if (request.getReqBloodStatus().equalsIgnoreCase("Accepted")) {
    holder.btnContact.setText("Contact");
    holder.btnContact.setOnClickListener(v -> {
        // Handle contact click (e.g., open phone dialer or send message)
    });
}
else{
    holder.btnContact.setText("Cancel");
    holder.btnContact.setOnClickListener(v -> {
        // Handle contact click (e.g., open phone dialer or send message)
    });
}

;
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBloodType, tvLastDonate, tvLocation, MacthDonorName ;
        Button btnRequestStatus, btnContact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            MacthDonorName = itemView.findViewById(R.id.MDonorName);
            tvBloodType = itemView.findViewById(R.id.tvBloodType);
            tvLastDonate = itemView.findViewById(R.id.tvLastDonate);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            btnRequestStatus = itemView.findViewById(R.id.btnRequestStatus);
            btnContact = itemView.findViewById(R.id.btnContact);
        }
    }

    private void updateRequestStatus(Request request) {
      //  DatabaseReference requestRef = FirebaseDatabase.getInstance().getReference("bl").child(request.);
      //  requestRef.child("Accepted").setValue(!request.isRequestAccepted());
    }
}