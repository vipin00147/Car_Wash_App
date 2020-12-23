package com.example.carwashapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;

public class RequestFragment extends Fragment {

    ImageView Image;
    TextView Center_name, Timing, Name, Amount, Status;
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        Image = view.findViewById(R.id.received_image);
        Center_name = view.findViewById(R.id.text);
        Timing = view.findViewById(R.id.appoint_time);
        Name = view.findViewById(R.id.appoint_name);
        Amount = view.findViewById(R.id.appoint_money);
        Status = view.findViewById(R.id.status);

        try {

            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("Appointment_Booked");

            Query checkUser = reference.orderByChild("mobile").equalTo(CurrentUser.phone);
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String Center_Name = snapshot.child(CurrentUser.phone).child("center_name").getValue(String.class);
                        String Center_Time = snapshot.child(CurrentUser.phone).child("appointment_date").getValue(String.class);
                        String Center_Photo = snapshot.child(CurrentUser.phone).child("center_image").getValue(String.class);
                        String Center_Amount = snapshot.child(CurrentUser.phone).child("amount").getValue(String.class);
                        String User_Name = snapshot.child(CurrentUser.phone).child("name").getValue(String.class);
                        String Status_Code = snapshot.child(CurrentUser.phone).child("status_code").getValue(String.class);

                        Center_name.setText(Center_Name);
                        Glide.with(Image.getContext()).load(Center_Photo).into(Image);
                        Timing.setText("Timing: "+Center_Time);
                        Name.setText("Customer Name: "+User_Name);
                        Amount.setText("Amount: Rs."+Center_Amount);

                        if (TextUtils.equals(Status_Code,"0")) {
                            Status.setText("Request Pending");
                            Status.setTextColor(getResources().getColor(R.color.red));
                        }
                        if (TextUtils.equals(Status_Code,"1")) {
                            Status.setText("Approved");
                            Status.setTextColor(getResources().getColor(R.color.green));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }
}