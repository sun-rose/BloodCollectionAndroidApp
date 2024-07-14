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
    private String dateToday ="";

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
        View view= inflater.inflate(R.layout.fragment_homepage_view, container, false);

        recyclerView = view.findViewById(R.id.recycler_home_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Date todaysdate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        dateToday= format.format(todaysdate);

        bloodRequestList = new ArrayList<>();

        readUsers();
        return view;




    }

    private void readUsers() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String current_user= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        Log.d("Current user ID", ":" + current_user);


        if (firebaseUser == null) {
            // Handle the case where the user is not logged in
            Log.e("FirebaseUser", "User is not logged in");
            return;
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UsersDetails");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                bloodRequestList.clear();
                Log.d("DataChange", "Data changed, snapshot count: " + snapshot.getChildrenCount());

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BloodRequest taker_list = dataSnapshot.getValue(BloodRequest.class);
                    if (taker_list != null) {
                        //Log.d("DataChange", "User ID from snapshot: " + dataSnapshot.child("user_id").getValue(String.class));
                        taker_list.setUser_id(dataSnapshot.getKey());
                        //Log.d("snapshotID", "snapshotID: " +taker_list.getUser_id() );
                        taker_list.setName(dataSnapshot.child("username").getValue(String.class));
                        taker_list.setUser_mode(dataSnapshot.child("user_mode").getValue(String.class));
                        taker_list.setLocation(dataSnapshot.child("location").getValue(String.class));
                        taker_list.setBloodType(dataSnapshot.child("blood_group").getValue(String.class));

                        if (taker_list.getUser_id()!= null && !taker_list.getUser_id().equals(current_user)) {
                            if ("Taker".equalsIgnoreCase(taker_list.getUser_mode())) {
                                bloodRequestList.add(new BloodRequest(
                                        taker_list.getName(),
                                        dateToday, // Ensure dateToday is properly defined
                                        taker_list.getLocation(),
                                        taker_list.getBloodType()
                                ));
                            }
                        }
                    } else {
                        Log.e("DataChange", "BloodRequest object is null");
                    }
                }

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
    }
}