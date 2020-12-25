package com.example.carwashapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AppointmentFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference reference;
    Search_Appointment_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
        reference = FirebaseDatabase.getInstance().getReference().child("Appointment_Booked");
        recyclerView = view.findViewById(R.id.appointment_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<requestListfromFirebase> options =
                new FirebaseRecyclerOptions.Builder<requestListfromFirebase>()
                        .setQuery(reference, requestListfromFirebase.class)
                        .build();

        adapter = new Search_Appointment_Adapter(options);
        recyclerView.setAdapter(adapter);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}