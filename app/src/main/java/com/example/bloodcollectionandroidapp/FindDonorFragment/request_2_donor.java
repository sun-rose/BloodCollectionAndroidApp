package com.example.bloodcollectionandroidapp.FindDonorFragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.bloodcollectionandroidapp.HomePageFragment.BloodRequest;
import com.example.bloodcollectionandroidapp.HomePageFragment.BloodRequestAdapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link request_2_donor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class request_2_donor extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private DonorAdapter donorAdapter;
    private FirebaseUser fUser;
    private String blGroup, location, dateToday="";
    private List<Donor> takerPost;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public request_2_donor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment request_2_donor.
     */
    // TODO: Rename and change types and number of parameters
    public static request_2_donor newInstance(String param1, String param2) {
        request_2_donor fragment = new request_2_donor();
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_request_2_donor, container, false);
        takerPost = new ArrayList<>();


        recyclerView = view.findViewById(R.id.donorlistView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       // back_button = view.findViewById(R.id.back_button);

        if (getArguments() != null) {
            //editLocation.setText(getArguments().getString("location"));

            Bundle bundle = getArguments();
            this.blGroup = bundle.getString("blGroup");
            this.location = bundle.getString("address");

        }


        Date todaysdate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        dateToday= format.format(todaysdate);

//if() {
//
//}

        //readUsers();
       return view;
    }
    private void on_back_pressed() {
        Intent intent = new Intent(getActivity(), MainPage.class);
        intent.putExtra("back", "request_2_donor");
        startActivity(intent);
    }

//    private void readUsers() {
//        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        final String current_user= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
//        Log.d("Current user ID", ":" + current_user);
//
//
//        if (firebaseUser == null) {
//            // Handle the case where the user is not logged in
//            Log.e("FirebaseUser", "User is not logged in");
//            return;
//        }
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("BloodTakingPosts");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                takerPost.clear();
//                Log.d("DataChange", "Data changed, snapshot count: " + snapshot.getChildrenCount());
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    BloodRequest taker_list = dataSnapshot.getValue(BloodRequest.class);
//                    if (taker_list != null) {
//                        //Log.d("DataChange", "User ID from snapshot: " + dataSnapshot.child("user_id").getValue(String.class));
//                        taker_list.setId(dataSnapshot.getKey());
//                        //Log.d("snapshotID", "snapshotID: " +taker_list.getUser_id() );
//                        taker_list.setUsername(dataSnapshot.child("username").getValue(String.class));
//                       // taker_list.setUser_mode(dataSnapshot.child("postdate").getValue(String.class));
//                        taker_list.setLocation(dataSnapshot.child("location").getValue(String.class));
//                        taker_list.setBloodType(dataSnapshot.child("blood_group").getValue(String.class));
//
//                        if (taker_list.getId()!= null && !taker_list.getId().equals(current_user)) {
//                            if ("Taker".equalsIgnoreCase(taker_list.getUser_mode())) {
//                                takerPost.add(new Donor(
//                                        taker_list.getName(),
//                                        dateToday, // Ensure dateToday is properly defined
//                                        taker_list.getLocation(),
//                                        taker_list.getBloodType()
//                                ));
//
//                                takerPost.add(new Donor(
//
//                                ));
//                            }
//                        }
//                    } else {
//                        Log.e("DataChange", "BloodRequest object is null");
//                    }
//                }
//
//                if (donorAdapter == null) {
//                    donorAdapter = new DonorAdapter(takerPost, getContext());
//                    recyclerView.setAdapter(donorAdapter);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                } else {
//                    donorAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("DatabaseError", error.getMessage());
//            }
//        });
//    }







}