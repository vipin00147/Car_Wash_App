package com.example.carwashapp;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CreateUserFragment extends Fragment {

    TextInputLayout name, email, password, phone;
    Button create_user;
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_user, container, false);

        name = view.findViewById(R.id.enter_name);
        email = view.findViewById(R.id.enter_email);
        password = view.findViewById(R.id.enter_password);
        phone = view.findViewById(R.id.enter_phone);
        create_user = view.findViewById(R.id.create_user);

        fAuth = FirebaseAuth.getInstance();

        create_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting Credential of User...
                String NAME = name.getEditText().getText().toString().trim();
                String EMAIL = email.getEditText().getText().toString().trim();
                String PASSWORD = password.getEditText().getText().toString().trim();
                String PHONE = phone.getEditText().getText().toString().trim();
                String IMAGE = "";


                // Register user On fireBase..
                fAuth.createUserWithEmailAndPassword(EMAIL,PASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            rootNode = FirebaseDatabase.getInstance();
                            reference = rootNode.getReference("Users");

                            //setting user to database...
                            Users users = new Users(NAME, EMAIL, PASSWORD, PHONE,"","");
                            reference.child(PHONE).setValue(users);

                            SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText("Loading ...");
                            pDialog.setCancelable(true);
                            pDialog.show();

                            final Handler handler  = new Handler();
                            final Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    if (pDialog.isShowing()) {
                                        pDialog.dismiss();
                                    }
                                }
                            };

                            pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    handler.removeCallbacks(runnable);
                                    new SweetAlertDialog(getContext(),SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Done")
                                            .setContentText("User created Successfully")
                                            .show();
                                }
                            });

                            handler.postDelayed(runnable, 3000);
                        }
                        else {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(task.getException().getMessage())
                                    .show();
                        }
                    }
                });
            }
        });
        return view;
    }
}