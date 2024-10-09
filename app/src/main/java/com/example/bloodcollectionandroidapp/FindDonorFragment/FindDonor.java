package com.example.bloodcollectionandroidapp.FindDonorFragment;


import android.content.Intent;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bloodcollectionandroidapp.MainPage;
import com.example.bloodcollectionandroidapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FindDonor extends Fragment {

    private EditText editLocation;
    private String selectedBloodGroup = "";
    private String message = "", address = "", blGroup = "";
    // private GoogleMap mMap;
    Button btnSearch;
    private FirebaseUser fUser;
    //String location="";
    private RecyclerView recyclerView;
    private DonorAdapter donorAdapter;
    private List<Donor> donorList = new ArrayList<>();


    public FindDonor() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the main fragment layout
        View view = inflater.inflate(R.layout.fragment_find_donor, container, false);

        // Initialize UI elements in the main fragment layout
        editLocation = view.findViewById(R.id.edit_location);
        btnSearch = view.findViewById(R.id.btn_search);

        Button btnAp = view.findViewById(R.id.btn_ap);
        Button btnOp = view.findViewById(R.id.btn_op);
        Button btnBp = view.findViewById(R.id.btn_bp);
        Button btnAbp = view.findViewById(R.id.btn_abp);
        Button btnB = view.findViewById(R.id.btn_b);
        Button btnAbm = view.findViewById(R.id.btn_abm);
        Button btnOpm = view.findViewById(R.id.btn_opm);
        Button btnAm = view.findViewById(R.id.btn_am);

        // Retrieve the arguments passed to this fragment
        Bundle bundle = getArguments();

        if (bundle != null) {
            String location = bundle.getString("location");

            if (location != null) {
                editLocation.setText(location);
            }

            // Check if address and blood group are passed
             address = bundle.getString("address");
             blGroup = bundle.getString("blGroup");

            if (address != null && blGroup != null && container != null) {
                // Inflate a different layout and return it
                View setView = inflater.inflate(R.layout.activity_donorlist, container, false);
                recyclerView = setView.findViewById(R.id.recycler_donor_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                readUsers(address, blGroup);
                return setView;
            }
        }

        // Set up button click listeners for blood group selection
        btnAp.setOnClickListener(v -> selectBloodGroup("A+"));
        btnOp.setOnClickListener(v -> selectBloodGroup("O+"));
        btnBp.setOnClickListener(v -> selectBloodGroup("B+"));
        btnAbp.setOnClickListener(v -> selectBloodGroup("AB+"));
        btnB.setOnClickListener(v -> selectBloodGroup("B-"));
        btnAbm.setOnClickListener(v -> selectBloodGroup("AB-"));
        btnOpm.setOnClickListener(v -> selectBloodGroup("O-"));
        btnAm.setOnClickListener(v -> selectBloodGroup("A-"));

        // Set up click listeners for other UI elements
        editLocation.setOnClickListener(v -> search_click());
        btnSearch.setOnClickListener(view1 -> searchDonor());

        // Return the default view
        return view;
    }


    private void selectBloodGroup(String bloodGroup) {
        selectedBloodGroup = bloodGroup;
        Toast.makeText(getActivity(), "Selected Blood Group: " + selectedBloodGroup, Toast.LENGTH_SHORT).show();
    }

    private void search_click() {
        Intent intent = new Intent(getActivity(), map_page.class);
        startActivity(intent);
    }

    private void searchDonor() {
        String location = editLocation.getText().toString();
        if (selectedBloodGroup.isEmpty() || location.isEmpty()) {
            Toast.makeText(getActivity(), "Please select a blood group and enter a location", Toast.LENGTH_SHORT).show();
        } else {
           // List<Address> addressList = null;
            // Toast.makeText(getActivity(), "Searching for " + selectedBloodGroup + " donors in " + location, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("address", location);
            intent.putExtra("blGroup", selectedBloodGroup);
            intent.setClass(getActivity(), MainPage.class);
            getActivity().startActivity(intent);
        }
        }

    private void readUsers(String address, String blGroup) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser == null) {
            // Handle the case where the user is not logged in
            Log.e("FirebaseUser", "User is not logged in");
            return;
        }

        final String current_user = firebaseUser.getUid();  // Avoid calling getUid() before the null check
        Log.d("Current user ID", ":" + current_user);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UsersDetails");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                donorList.clear();
                Log.d("DataChange", "Data changed, snapshot count: " + snapshot.getChildrenCount());

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Donor donor_list = new Donor();

                    if (donor_list != null) {
                        donor_list.setUser_id(dataSnapshot.getKey());

                        // Retrieving data with null checks and logging to debug potential issues
                       // String fromTakerName = dataSnapshot.child("username").getValue(String.class);
                        String toDonorName = dataSnapshot.child("username").getValue(String.class);
                        String location = dataSnapshot.child("location").getValue(String.class);
                        String bloodGroup = dataSnapshot.child("blood_group").getValue(String.class);
                        String userMode = dataSnapshot.child("user_mode").getValue(String.class);

                        // Logging to ensure the values are being retrieved correctly
                        Log.d("DonorData", "toDonorName: " + toDonorName + " Location: " + location + ", BloodGroup: " + bloodGroup + ", UserMode: " + userMode);


                        donor_list.setToDonorName(toDonorName);
                        donor_list.setDate("Today");
                        donor_list.setLocation(location);
                        donor_list.setBloodType(bloodGroup);
                        // Null check for necessary fields
                        if (toDonorName != null && location != null && bloodGroup != null && userMode != null) {

                            String INVALID_PATTERN = "(?i)[а-яёa-z0-9\\s,!_{}\\[\\];+-]+";
                            Matcher m1 = Pattern.compile(INVALID_PATTERN).matcher(address);
                             Matcher m2 = Pattern.compile(INVALID_PATTERN).matcher(donor_list.getLocation());
                            // ".*\\b"+address+"\\b.*"
                            if(m1.matches()&&m2.matches()) {

                                // Check if the user is not the current user and if the user is a donor
                                if (!donor_list.getUser_id().equals(current_user)) {
                                    // Check if the donor location contains the address (case-insensitive comparison)
                                    if ( "Donor".equalsIgnoreCase(userMode) &&
                                            blGroup.equalsIgnoreCase(bloodGroup)) {

                                        // Add to donor list if the location matches and blood group matches
                                        donorList.add(new Donor(
                                                donor_list.getToDonorName(),
                                                donor_list.getDate(),  // Placeholder for date
                                                donor_list.getLocation(),
                                                donor_list.getBloodType()
                                        ));

                                    } else {
                                        Log.d("DonorMatch", "User did not match donor criteria: " + donor_list.getUser_id());
                                    }
                                } else {
                                    Log.e("DataChange", "It is self account " + donor_list.getUser_id());
                                }
                            }
                            else {
                                Log.e("DataChange", "location not matched" + donor_list.getUser_id());
                            }

                        } else {
                            Log.e("DataChange", "One or more fields are null for donor: " + donor_list.getUser_id());
                        }
                    } else {
                        Log.e("DataChange", "Donor object is null");
                    }
                }
                // Set or update the adapter
                if (donorAdapter == null) {
                    donorAdapter = new DonorAdapter(donorList, getContext());
                    recyclerView.setAdapter(donorAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                } else {
                    donorAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", error.getMessage());
            }
        });
    }

    // Method to get the current date
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }





}



