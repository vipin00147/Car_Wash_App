package com.example.carwashapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class profileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        LinearLayout profile = view.findViewById(R.id.linear_layout1);
        LinearLayout appointment = view.findViewById(R.id.linear_layout2);
        LinearLayout create_user = view.findViewById(R.id.linear_layout3);
        LinearLayout add_service_center = view.findViewById(R.id.linear_layout4);
        LinearLayout service_center_list = view.findViewById(R.id.linear_layout5);
        LinearLayout logout = view.findViewById(R.id.linear_layout6);
        return  view;
    }
}