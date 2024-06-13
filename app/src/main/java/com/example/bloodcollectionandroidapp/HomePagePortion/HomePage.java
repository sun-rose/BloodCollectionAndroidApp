package com.example.bloodcollectionandroidapp.HomePagePortion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.bloodcollectionandroidapp.R;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BloodRequestAdapter adapter;
    private List<BloodRequest> bloodRequestList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

            recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            bloodRequestList = new ArrayList<>();
            bloodRequestList.add(new BloodRequest("Tarique Farhan Rushow", "08/12/2020", "Dhaka Medical College Hospital", "AB+"));
            bloodRequestList.add(new BloodRequest("Imraul Hoques Istiyak", "07/12/2020", "Delta Medical College Hospital", "O+"));
            bloodRequestList.add(new BloodRequest("Md. Kamrujamal Imrul Kayes", "07/12/2020", "The One Hope Medical Hospital", "AB-"));

            adapter = new BloodRequestAdapter(bloodRequestList, this);
            recyclerView.setAdapter(adapter);
        }

}