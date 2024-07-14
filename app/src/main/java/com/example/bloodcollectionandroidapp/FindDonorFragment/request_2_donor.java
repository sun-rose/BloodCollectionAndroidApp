package com.example.bloodcollectionandroidapp.FindDonorFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;

import com.example.bloodcollectionandroidapp.MainPage;
import com.example.bloodcollectionandroidapp.R;

import java.util.ArrayList;
import java.util.List;

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
    private List<Donor> donorList;



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



        recyclerView = view.findViewById(R.id.donorlistView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       // back_button = view.findViewById(R.id.back_button);

        donorList = new ArrayList<>();
        donorList.add(new Donor("Tarique Billah Maksud", "4 months ago", "Dhaka Medical College Hospital"));
        donorList.add(new Donor("Imraul Hoques Istiyak", "4 months ago", "Dhaka Medical College Hospital"));
        donorList.add(new Donor("Md. kamrujamal Imrul Kayes", "4 months ago", "Dhaka Medical College Hospital"));
        donorList.add(new Donor("Md. kamrujamal Mashrafee", "4 months ago", "Dhaka Medical College Hospital"));

        donorAdapter = new DonorAdapter(donorList);
        recyclerView.setAdapter(donorAdapter);


       return view;
    }
    private void on_back_pressed() {
        Intent intent = new Intent(getActivity(), MainPage.class);
        intent.putExtra("back", "request_2_donor");
        startActivity(intent);
    }



}