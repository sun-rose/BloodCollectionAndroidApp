package com.example.bloodcollectionandroidapp.HomePageFragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodcollectionandroidapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BloodRequestAdapter extends RecyclerView.Adapter<BloodRequestAdapter.ViewHolder> {

    private final List<BloodRequest> bloodRequestList;
    private final Context context;

    public BloodRequestAdapter(List<BloodRequest> bloodRequestList, Context context) {
        this.bloodRequestList = bloodRequestList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.relative_home_page, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BloodRequest bloodRequest = bloodRequestList.get(position);
        holder.name.setText(bloodRequest.getFromTakerName());
        holder.date.setText(bloodRequest.getPost_date());
        holder.location.setText(bloodRequest.getLocation());
        holder.bloodType.setText(bloodRequest.getBloodType());
        holder.donateButton.setOnClickListener(v -> {
            // Get the currently authenticated user
            FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
            final String current_user = fUser.getUid();
            // Check if the user is authenticated
            if (fUser == null) {
                Log.e(TAG, "User is not authenticated.");
                return;  // Exit the method if the user is not authenticated
            }

            // Reference to the current user's details in Firebase
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("blPost");

            // Retrieve the taker name using a single event listener
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    bloodRequestList.clear();  // Clear the list before adding new data

                    // Iterate over each post in "blPost"
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        String takerID = postSnapshot.getKey();  // Get the key for the taker

                        // Iterate over each request inside the post
                        for (DataSnapshot requestSnapshot : postSnapshot.getChildren()) {
                            // Retrieve BloodRequest object from the snapshot
                            BloodRequest bloodRequest = requestSnapshot.getValue(BloodRequest.class);

                            if (bloodRequest != null) {
                                // Get the ReqBloodStatus and ReqDonorId fields from the snapshot
                                String reqBloodStatus = requestSnapshot.child("ReqBloodStatus").getValue(String.class);
                                String donorId = requestSnapshot.child("ReqDonorId").getValue(String.class);

                                // Set the status in the BloodRequest object
                                bloodRequest.setReqBloodStatus(reqBloodStatus);

                                // Check if the blood request status is "Pending"
                                if ("Pending".equalsIgnoreCase(bloodRequest.getReqBloodStatus())) {
                                    // Update the request blood status to "Accepted"
                                    updateBloodRequestStatus(userRef, current_user, donorId, takerID, "Accepted");
                                    Log.d(TAG, "Request changed to Accepted: " + bloodRequest);
                                }
                                if ("Accepted".equalsIgnoreCase(bloodRequest.getReqBloodStatus())) {
                                    // Update the request blood status to "Accepted"
                                    updateBloodRequestStatus(userRef, current_user, donorId, takerID, "Accepted");
                                    Log.d(TAG, "Acceptation no longer need: " + bloodRequest);
                                }
                            } else {
                                Log.e(TAG, "BloodRequest object is null.");
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Log the error message
                    Log.e(TAG, "Database error: " + error.getMessage());
                }
            });
        });
    }


/**
 * Helper method to update the status of a blood request.
 */
        private void updateBloodRequestStatus(DatabaseReference userRef, String cUser, String donorId, String takerID, String newStatus) {
            if (donorId != null && donorId.equals(cUser)) {
                // Create a map to hold the updated data
                Map<String, Object> updateData = new HashMap<>();
                updateData.put("ReqBloodStatus", newStatus);

                // Update the request status at the correct location in the database
                userRef.child(takerID).child(donorId).updateChildren(updateData)
                        .addOnSuccessListener(aVoid -> {
                            Log.d(TAG, "Blood request status updated successfully.");
                            Toast.makeText(context.getApplicationContext(), "Blood request status updated.", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Log.e(TAG, "Failed to update blood request status: " + e.getMessage());
                            Toast.makeText(context.getApplicationContext(), "Failed to update blood request status.", Toast.LENGTH_SHORT).show();
                        });
            } else {
                Log.e(TAG, "Donor ID is null. Cannot update blood request status.");
            }
        }





        @Override
    public int getItemCount() {
        return bloodRequestList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date, location, bloodType;
        public Button donateButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            location = itemView.findViewById(R.id.location);
            bloodType = itemView.findViewById(R.id.blood_type);
            donateButton = itemView.findViewById(R.id.donate_button);
            if (name == null || date == null || location == null || bloodType == null || donateButton == null) {
                Log.e("ViewHolder", "One of the views is null");
            }

            Log.d("ViewHolder", "name: " + name);
            Log.d("ViewHolder", "date: " + date);
            Log.d("ViewHolder", "location: " + location);
            Log.d("ViewHolder", "bloodType: " + bloodType);
            Log.d("ViewHolder", "donateButton: " + donateButton);

        }
    }
}
