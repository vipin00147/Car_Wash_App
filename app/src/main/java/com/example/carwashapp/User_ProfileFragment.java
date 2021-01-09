package com.example.carwashapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class User_ProfileFragment extends Fragment {

    TextView Logout, User_Name, User_Email,User_Profile,User_Support,about_us, user_booking;
    CircleImageView Profile_image;
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    BottomNavigationView navigation;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null){

        }


        View view = inflater.inflate(R.layout.fragment_user__profile, container, false);
        Logout = view.findViewById(R.id.user_logout);
        User_Name = view.findViewById(R.id.user_name);
        User_Email = view.findViewById(R.id.user_email);
        User_Profile = view.findViewById(R.id.user_profile);
        User_Support = view.findViewById(R.id.user_support);
        about_us = view.findViewById(R.id.about_us);
        Profile_image = view.findViewById(R.id.user_image_one);
        user_booking = view.findViewById(R.id.user_booking);
        navigation = view.findViewById(R.id.user_navigation);
        viewPager = view.findViewById(R.id.user_view_pager);

        SharedPreferences getshrd = getContext().getSharedPreferences("demo",MODE_PRIVATE);
        if(Uri.parse(getshrd.getString("image", "0")).toString().isEmpty())
            Profile_image.setImageResource(R.drawable.ic_user_profile);
        else
            Profile_image.setImageURI(Uri.parse(getshrd.getString("image", "0")));

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");
        Query checkUser = reference.orderByChild("phone").equalTo(CurrentUser.phone);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User_Name.setText(snapshot.child(CurrentUser.phone).child("name").getValue(String.class));
                User_Email.setText(snapshot.child(CurrentUser.phone).child("email").getValue(String.class));
                if(snapshot.child(CurrentUser.phone).child("image").getValue(String.class).isEmpty())
                    Profile_image.setImageResource(R.drawable.ic_user_profile);
                else
                    Glide.with(Profile_image.getContext()).load(snapshot.child(CurrentUser.phone).child("image").getValue(String.class)).into(Profile_image);

                SharedPreferences shrd = getContext().getSharedPreferences("demo",MODE_PRIVATE);
                SharedPreferences.Editor editor = shrd.edit();
                editor.putString("image",snapshot.child(CurrentUser.phone).child("image").getValue(String.class));
                editor.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        User_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),User_Profile_Setting_Activity.class);
                startActivity(intent);
            }
        });

        User_Support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Intent intent = new Intent(getContext(),Help_And_Support.class);
                //startActivity(intent);
            }
        });

        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),About_Us.class));
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                startActivity(new Intent(getContext(),Login.class));
            }
        });
        return view;
    }
}