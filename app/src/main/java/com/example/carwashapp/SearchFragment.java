package com.example.carwashapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference reference;
    SearchView searchView;
    Search_List_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        reference = FirebaseDatabase.getInstance().getReference().child("Service_centers");
        recyclerView = view.findViewById(R.id.reyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchView = view.findViewById(R.id.searchView);

        FirebaseRecyclerOptions<gettingListFromFirebase> options =
                new FirebaseRecyclerOptions.Builder<gettingListFromFirebase>()
                        .setQuery(reference, gettingListFromFirebase.class)
                        .build();

        adapter = new Search_List_Adapter(options);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processsearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                processsearch(query);
                return false;
            }
        });


        return view;
    }

    private void processsearch(String query) {
        FirebaseRecyclerOptions<gettingListFromFirebase> options =
                new FirebaseRecyclerOptions.Builder<gettingListFromFirebase>()
                        .setQuery(reference.orderByChild("addess").startAt(query).endAt(query+"\uf8ff"), gettingListFromFirebase.class)
                        .build();

        adapter = new Search_List_Adapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
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