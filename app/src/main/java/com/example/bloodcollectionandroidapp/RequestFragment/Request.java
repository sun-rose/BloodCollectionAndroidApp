package com.example.bloodcollectionandroidapp.RequestFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.bloodcollectionandroidapp.FindDonorFragment.BloodPost;
import com.example.bloodcollectionandroidapp.HomePageFragment.BloodRequest;
import com.example.bloodcollectionandroidapp.HomePageFragment.BloodRequestAdapter;
import com.example.bloodcollectionandroidapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Request extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private RequestAdapter requestAdapter;
    private List<BloodRequest> bloodReq;

    public Request() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        //LinearLayout requestList = view.findViewById(R.id.request_list);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bloodReq = new ArrayList<>();
        requestAdapter = new RequestAdapter(bloodReq, getContext());
        recyclerView.setAdapter(requestAdapter);
        // Sample data
//        String[] names = {"Tarique Farhan Rushow", "Tarique Farhan Rushow", "Tarique Farhan Rushow"};
//        String[] statuses = {"Request Accepted", "Request Pending", "Request Pending"};
//        String[] buttonColors = {"@android:color/holo_green_light", "@android:color/holo_red_light", "@android:color/holo_red_light"};

        readTakerUsers();
        return view;
    }

    private void readTakerUsers() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser == null) {
            // Handle the case where the user is not logged in
            Log.e("FirebaseUser", "User is not logged in");
            return;
        }

        final String current_user = firebaseUser.getUid();  // Avoid calling getUid() before the null check
        Log.d("Current user ID", ":" + current_user);

        // Reference to the "UsersDetails" node in Firebase
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("UsersDetails");

        // Reference to the "blPost" node in Firebase
        DatabaseReference bloodPostReference = FirebaseDatabase.getInstance().getReference("blPost");

        userReference.child(current_user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                if (userSnapshot.exists()) {
                    String storedUsername = userSnapshot.child("username").getValue(String.class);
                    String userMode = userSnapshot.child("user_mode").getValue(String.class);

                    // Check if the current user is in "Donor" mode
                    if (!"Taker".equalsIgnoreCase(userMode)) {
                        Log.e("DataChange", "You are not a Taker.");
                        return;
                    }

                    // Query for blood requests in "blPost" that match the current user (Donor)
                    bloodPostReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshotSecond) {
                            bloodReq.clear();  // Clear the list before adding new data

                            // Iterate over each post in "blPost"
                            for (DataSnapshot dsnapshot : dataSnapshotSecond.getChildren()) {
                                for (DataSnapshot thstep : dsnapshot.getChildren()) {
                                    // Initialize BloodRequest and populate it with data from the snapshot
                                    BloodRequest level2 = thstep.getValue(BloodRequest.class);

                                    // Null check for level2 object
                                    if (level2 != null) {
                                        // Set BloodRequest properties from the snapshot
                                        level2.setId(thstep.getKey());
                                        level2.setToDonorName(thstep.child("ReqDonorName").getValue(String.class));
                                        level2.setFromTakerName(thstep.child("FromTakerName").getValue(String.class));
                                        level2.setPost_date(thstep.child("PostedOn").getValue(String.class));  // Correct field for post date
                                        level2.setLocation(thstep.child("location").getValue(String.class));
                                        level2.setBloodType(thstep.child("ReqBloodType").getValue(String.class));
                                        level2.setReqBloodStatus(thstep.child("ReqBloodStatus").getValue(String.class));
                                        // Check if the current user matches the donor in the request
                                        if (storedUsername != null && storedUsername.equalsIgnoreCase(level2.getFromTakerName())) {
                                            // Add the blood request to the list if the user is a donor
                                            bloodReq.add(new BloodRequest(
                                                    level2.getToDonorName(),
                                                    level2.getFromTakerName(),
                                                    level2.getPost_date(),  // Use the correct post date field
                                                    level2.getLocation(),
                                                    level2.getBloodType(),
                                                    level2.getReqBloodStatus()
                                            ));
                                        }
                                    } else {
                                        Log.e("DataChange", "BloodRequest object is null.");
                                    }
                                }

                                // Notify adapter about data changes
                                if (requestAdapter != null) {
                                    requestAdapter.notifyDataSetChanged();
                                } else {
                                    requestAdapter = new RequestAdapter(bloodReq, getContext());
                                    recyclerView.setAdapter(requestAdapter);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                }

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("DatabaseError", error.getMessage());
                        }

                    });

                } else {
                    Log.e("DataChange", "No user data available.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", error.getMessage());
            }
        });
    }







}