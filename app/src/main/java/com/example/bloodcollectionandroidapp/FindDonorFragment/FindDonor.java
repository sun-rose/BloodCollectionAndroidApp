package com.example.bloodcollectionandroidapp.FindDonorFragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bloodcollectionandroidapp.MainPage;
import com.example.bloodcollectionandroidapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.auth.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FindDonor extends Fragment {

    private EditText editLocation;
    private String selectedBloodGroup = "";
    private String message="";
   // private GoogleMap mMap;
    Button btnSearch;
    //String location="";
    public FindDonor() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_donor, container, false);

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

        if (getArguments() != null) {
            //editLocation.setText(getArguments().getString("location"));

            Bundle bundle = getArguments();
            message = bundle.getString("location");
            editLocation.setText(message);
        }



        btnAp.setOnClickListener(v -> selectBloodGroup("A+"));
        btnOp.setOnClickListener(v -> selectBloodGroup("O+"));
        btnBp.setOnClickListener(v -> selectBloodGroup("B+"));
        btnAbp.setOnClickListener(v -> selectBloodGroup("AB+"));
        btnB.setOnClickListener(v -> selectBloodGroup("B-"));
        btnAbm.setOnClickListener(v -> selectBloodGroup("AB-"));
        btnOpm.setOnClickListener(v -> selectBloodGroup("O-"));
        btnAm.setOnClickListener(v -> selectBloodGroup("A-"));

        editLocation.setOnClickListener(v -> search_click());
        btnSearch.setOnClickListener(view1 -> searchDonor());


//        Bundle extras = getIntent().getExtras();
//        if(extras!=null && extras.containsKey("flag"))

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


    }



