package com.example.bloodcollectionandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.bloodcollectionandroidapp.FindDonorFragment.Donor;
import com.example.bloodcollectionandroidapp.FindDonorFragment.DonorAdapter;
import com.example.bloodcollectionandroidapp.FindDonorFragment.FindDonor;
import com.example.bloodcollectionandroidapp.FindDonorFragment.request_2_donor;
import com.example.bloodcollectionandroidapp.HomePageFragment.HomepageView;
import com.example.bloodcollectionandroidapp.ProfileFragment.Profile;
import com.example.bloodcollectionandroidapp.RequestFragment.Request;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;

import java.util.Objects;


public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        FirebaseApp.initializeApp(this);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    selectedFragment = new HomepageView();
                } else if (itemId == R.id.navigation_find_donor) {


                    selectedFragment = new FindDonor();
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    Fragment fragment = new FindDonor();
//                    fragmentTransaction.replace(R.id.fragment_container, fragment);
//                    fragmentTransaction.commit();
                    Intent intent = getIntent();
                    if (intent != null) {


                        if (intent.hasExtra("location")) {

                            String location = getIntent().getStringExtra("location");

                            // Pass the location data to the fragment
                            selectedFragment = new FindDonor();
                            Bundle args = new Bundle();
                            args.putString("location", location);
                            selectedFragment.setArguments(args);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, selectedFragment)
                                    .commit();
                            // bottomNavigationView.setSelectedItemId(R.id.navigation_find_donor);
                        } else if (intent.hasExtra("address") && intent.hasExtra("blGroup")) {
                            selectedFragment = new FindDonor();
                            String location = getIntent().getStringExtra("address");
                            String blGroup = getIntent().getStringExtra("blGroup");
                            Bundle args = new Bundle();
                            args.putString("address", location);
                            args.putString("blGroup", blGroup);
                            selectedFragment.setArguments(args);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, selectedFragment)
                                    .commit();

                        }
                        else {
                            String source = getIntent().getStringExtra("back");
                            if (source != null &&source.equals("back")) {
                                selectedFragment = new FindDonor();
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, selectedFragment)
                                        .commit();

                            }
                        }


                    }

                } else if (itemId == R.id.navigation_request) {
                    selectedFragment = new Request();
                } else if (itemId == R.id.navigation_profile) {
                    selectedFragment = new Profile();
                } else {
                    return false;
                }

                // Replace the existing fragment with the selected one
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();

                return true;
            }
        });
        Intent intent = getIntent();
        String source = getIntent().getStringExtra("back");
        if(intent.hasExtra("location")) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_find_donor);
        }
        else if(intent.hasExtra("address") && intent.hasExtra("blGroup")) {

            bottomNavigationView.setSelectedItemId(R.id.navigation_find_donor);
        }
        else if(source != null && source.equals("back")) {

            bottomNavigationView.setSelectedItemId(R.id.navigation_find_donor);
        }
        else {
            // Set default selection
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }
    }
}