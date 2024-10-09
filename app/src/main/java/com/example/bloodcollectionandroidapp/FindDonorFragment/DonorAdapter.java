package com.example.bloodcollectionandroidapp.FindDonorFragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodcollectionandroidapp.ProfileFragment.UserDetails;
import com.example.bloodcollectionandroidapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.DonorViewHolder> {

    private List<Donor> donorList;
    private List<UserDetails> userList;
    private FirebaseUser fUser;
    private final Context context;
    private String takerName;

    public DonorAdapter(List<Donor> donorList, Context context) {
        this.donorList = donorList;
        this.context = context;
    }
 //   public DonorAdapter(List<BloodPost> takerPost, Context context) {
 //       this.takerPost = takerPost;
  //      this.context = context;
 //   }


    @NonNull
    @Override
    public DonorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.relative_donor_list, parent, false);
        return new DonorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonorViewHolder holder, int position) {
        Donor donor = donorList.get(position);
        //UserDetails uDetails =
        holder.tvName.setText(donor.getFromTakerName());
        holder.tvLastDonate.setText(donor.getDate());
        holder.tvLocation.setText(donor.getLocation());
        holder.btnRequestBlood.setOnClickListener(v -> {

            // Get the currently authenticated user
            FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

            // Check if user is authenticated
            if (fUser == null) {
                Log.e(TAG, "User is not authenticated.");
                return;  // Exit the method if not authenticated
            }

            // Reference to the current user's details in Firebase
            DatabaseReference takerNameRef = FirebaseDatabase.getInstance().getReference("UsersDetails");

            // Retrieve the taker name using a single event listener
            takerNameRef.child(fUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot d_Snapshot) {

                    // Retrieve the taker name (username) and blood group
                    final String takerName = d_Snapshot.child("username").getValue(String.class);
                    final String takerBloodGroup = d_Snapshot.child("blood_group").getValue(String.class);

                    if (takerName == null || takerBloodGroup == null) {
                        Log.e(TAG, "Taker name or blood group is null.");
                        return;
                    }

                    // Reference to the blPost node
                    DatabaseReference postRef = FirebaseDatabase.getInstance().getReference("blPost").child(fUser.getUid());

                    // Check if the post already exists based on ReqDonorName
                    postRef.orderByChild("ReqDonorName").equalTo(holder.tvName.getText().toString())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (!snapshot.exists()) {
                                        // Prepare the data to add
                                        Map<String, Object> userDetails = new HashMap<>();
                                        userDetails.put("FromTakerName", takerName);
                                        userDetails.put("ReqDonorName", holder.tvName.getText().toString());
                                        userDetails.put("PostedOn", holder.tvLastDonate.getText().toString());
                                        userDetails.put("location", holder.tvLocation.getText().toString());
                                        userDetails.put("ReqBloodType", takerBloodGroup);
                                        userDetails.put("ReqBloodStatus", "Pending");

                                        // Query for donor details based on the donor's name
                                        DatabaseReference donorRef = FirebaseDatabase.getInstance().getReference("UsersDetails");
                                        donorRef.orderByChild("username").equalTo(holder.tvName.getText().toString())
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot donorSnapshot) {
                                                        if (donorSnapshot.exists()) {
                                                            for (DataSnapshot donorChild : donorSnapshot.getChildren()) {
                                                                String donorId = donorChild.child("user_id").getValue(String.class);

                                                                if (donorId != null) {
                                                                    // Add donor ID to user details
                                                                    userDetails.put("ReqDonorId", donorId);

                                                                    // Save the data to Firebase
                                                                    postRef.child(donorId).setValue(userDetails)
                                                                            .addOnSuccessListener(aVoid -> {
                                                                                Log.d(TAG, "Details added successfully.");
                                                                            })
                                                                            .addOnFailureListener(e -> {
                                                                                Log.e(TAG, "Failed to add user details: " + e.getMessage());
                                                                            });
                                                                }
                                                            }
                                                        } else {
                                                            Log.e(TAG, "Donor not found.");
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                        Log.e(TAG, "Error fetching donor details.", error.toException());
                                                    }
                                                });
                                    } else {
                                        Log.d(TAG, "Post with ReqDonorName already exists.");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.e(TAG, "Error fetching post details.", databaseError.toException());
                                }
                            });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e(TAG, "Error fetching user details.", databaseError.toException());
                }
            });
        });








    }



    @Override
    public int getItemCount() {
        return donorList.size();
    }

    public static class DonorViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvLastDonate, tvLocation;
        Button btnRequestBlood;

        public DonorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvLastDonate = itemView.findViewById(R.id.tvLastDonate);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            btnRequestBlood = itemView.findViewById(R.id.btnRequestBlood);
            if (tvName == null || tvLastDonate == null || tvLocation == null || btnRequestBlood == null) {
                Log.e("ViewHolder", "One of the views is null");
            }
        }
    }

    //code for creating a db table
//    private void updateBloodRequests() {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("BloodTakingPosts").child(fUser.getUid());
//
//        Map<String, Object> userDetails = new HashMap<>();
//        userDetails.put("username", profileName.getText().toString());
//        userDetails.put("mobile", profileMobile.getText().toString());
//        userDetails.put("location", profileLocation.getText().toString());
//        userDetails.put("blood_group", profileBloodGroup.getText().toString());
//        userDetails.put("user_mode", mode_switch_text);
//
//
//        reference.updateChildren(userDetails)
//                .addOnSuccessListener(aVoid -> {
//                    Log.d(TAG, "Your details updated successfully.");
//                    Toast.makeText(requireContext(), "User details updated.", Toast.LENGTH_SHORT).show();
//                })
//                .addOnFailureListener(e -> {
//                    Log.d(TAG, "onFailure: " + e);
//                    Toast.makeText(requireContext(), "Failed to update user details.", Toast.LENGTH_SHORT).show();
//                });
//
//    }


    private void addingBloodRequests(DonorViewHolder holder) {


}







}

