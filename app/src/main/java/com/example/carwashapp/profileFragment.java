package com.example.carwashapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class profileFragment extends Fragment {

    FirebaseAuth fAuth;
    TextInputLayout edit_password, edit_email, edit_phone;
    TextView name, email,forgot;
    MaterialButton save, logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        name = view.findViewById(R.id.title);
        email = view.findViewById(R.id.email_id);
        edit_email = view.findViewById(R.id.edit_email);
        edit_password = view.findViewById(R.id.edit_password);
        edit_phone = view.findViewById(R.id.edit_phone);
        forgot = view.findViewById(R.id.forgot);
        save = view.findViewById(R.id.save_edit);
        logout = view.findViewById(R.id.admin_logout);

        fAuth = FirebaseAuth.getInstance();

        //getting current user from FireBase To set name and other credentials..
        String Email = fAuth.getCurrentUser().getEmail();
        email.setText(Email);


        // set their credentials...
        if (TextUtils.equals(Email, "vipin00147@gmail.com")){
            name.setText("Vipin Kumar");
            edit_email.getEditText().setText(Email);
        }

        else if (TextUtils.equals(Email, "srj2264@gmail.com")){
            name.setText("Shreya Jain");
            edit_email.getEditText().setText(Email);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edit_email.getEditText().getText().toString().trim();
                String phone = edit_phone.getEditText().getText().toString().trim();
                String name = edit_password.getEditText().getText().toString().trim();
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edit_email.getEditText().getText().toString().trim();
                fAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "Mail Sent", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                startActivity(new Intent(getContext(), Admin_Login.class));
            }
        });
            return view;
    }
}