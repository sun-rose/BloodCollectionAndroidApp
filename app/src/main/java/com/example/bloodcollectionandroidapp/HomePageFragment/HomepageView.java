package com.example.bloodcollectionandroidapp.HomePageFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bloodcollectionandroidapp.FindDonorFragment.Donor;
import com.example.bloodcollectionandroidapp.ProfileFragment.User;
import com.example.bloodcollectionandroidapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomepageView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomepageView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private BloodRequestAdapter bloodRequestAdapter;
    private List<BloodRequest> bloodRequestList;
    private List<Donor> donList;
    private String dateToday = "";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomepageView() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomepageView.
     */
    // TODO: Rename and change types and number of parameters
    public static HomepageView newInstance(String param1, String param2) {
        HomepageView fragment = new HomepageView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage_view, container, false);

        recyclerView = view.findViewById(R.id.recycler_home_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Date todaysdate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        dateToday = format.format(todaysdate);

        bloodRequestList = new ArrayList<>();

        readUsers();

        return view;

    }


    private void readUsers() {
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
                if (userSnapshot.exists()) {  // Corrected: checking if user data exists
                    String storedUsername = userSnapshot.child("username").getValue(String.class);

                    // Query for blood requests in "blPost" that match the current user (Donor)
                    bloodPostReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshotSecond) {
                            bloodRequestList.clear();  // Clear the list before adding new data

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

                                        // Check if the current user matches the donor in the request
                                        if (storedUsername != null && !storedUsername.equalsIgnoreCase(current_user)) {
                                            // Add the blood request to the list if the user is a donor
                                            bloodRequestList.add(new BloodRequest(
                                                    level2.getToDonorName(),
                                                    level2.getFromTakerName(),
                                                    level2.getPost_date(),  // Use the correct post date field
                                                    level2.getLocation(),
                                                    level2.getBloodType()
                                            ));
                                        }
                                    } else {
                                        Log.e("DataChange", "BloodRequest object is null.");
                                    }
                                }
                            }

                            // Notify adapter about data changes
                            if (bloodRequestAdapter == null) {
                                bloodRequestAdapter = new BloodRequestAdapter(bloodRequestList, getContext());
                                recyclerView.setAdapter(bloodRequestAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            } else {
                                bloodRequestAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("DatabaseError", error.getMessage());
                        }
                    });
                } else {
                    Log.e("DataChange", "User data does not exist.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", error.getMessage());
            }
        });
    }





    // Helper method to get the current date as a formatted string
    private String getCurrentDate() {
        // Example format: "dd/MM/yyyy"
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }





}


