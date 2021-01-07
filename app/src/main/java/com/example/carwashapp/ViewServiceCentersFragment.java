package com.example.carwashapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewServiceCentersFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    listAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_service_centers, container, false);
        recyclerView = view.findViewById(R.id.reyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference().child("Service_centers");

        FirebaseRecyclerOptions<gettingListFromFirebase> options =
                new FirebaseRecyclerOptions.Builder<gettingListFromFirebase>()
                        .setQuery(reference, gettingListFromFirebase.class)
                        .build();

        adapter = new listAdapter(options);
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