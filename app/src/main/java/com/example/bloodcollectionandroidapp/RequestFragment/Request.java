package com.example.bloodcollectionandroidapp.RequestFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bloodcollectionandroidapp.R;


public class Request extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        LinearLayout requestList = view.findViewById(R.id.request_list);

        // Sample data
        String[] names = {"Tarique Farhan Rushow", "Tarique Farhan Rushow", "Tarique Farhan Rushow"};
        String[] statuses = {"Request Accepted", "Request Pending", "Request Pending"};
        String[] buttonColors = {"@android:color/holo_green_light", "@android:color/holo_red_light", "@android:color/holo_red_light"};

        for (int i = 0; i < names.length; i++) {
            View requestItem = inflater.inflate(R.layout.request_item, requestList, false);

            TextView donorName = requestItem.findViewById(R.id.donor_name);
            donorName.setText(names[i]);

            Button requestStatus = requestItem.findViewById(R.id.request_status);
            requestStatus.setText(statuses[i]);

            Button contactButton = requestItem.findViewById(R.id.contact_button);
            contactButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));

            requestList.addView(requestItem);
        }

        return view;
    }
}