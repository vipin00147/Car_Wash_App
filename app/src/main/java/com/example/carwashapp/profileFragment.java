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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class profileFragment extends Fragment {

    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference referance;
    TextInputLayout edit_password, edit_email, edit_phone;
    TextView name, email;
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
        save = view.findViewById(R.id.save_edit);
        logout = view.findViewById(R.id.admin_logout);
        fAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        referance = rootNode.getReference("Admin");

        //getting current user from FireBase To set name and other credentials..
        String Email = fAuth.getCurrentUser().getEmail();
        email.setText(Email);


        // set their credentials...
        if (TextUtils.equals(Email, "vipin00147@gmail.com")){
            name.setText("Vipin");
            edit_email.getEditText().setText(Email);
        }

        else if (TextUtils.equals(Email, "srj2264@gmail.com")){
            name.setText("Shreya");
            edit_email.getEditText().setText(Email);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edit_email.getEditText().getText().toString().trim();
                String password = edit_password.getEditText().getText().toString().trim();
                String phone = edit_phone.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    edit_email.setError("Field Required");
                }else {
                    edit_email.setError(null);
                    edit_email.setErrorEnabled(false);
                }
                if(!TextUtils.isEmpty(password) && password.length()<6){
                    edit_password.setError("Password Should be greater then or equal 6");
                }
                else{
                    edit_password.setError(null);
                    edit_password.setErrorEnabled(false);
                }
                if(!TextUtils.isEmpty(phone) && phone.length()<10){
                    edit_phone.setError("Phone number should be = 10 and not be empty");
                }
                else{
                    edit_phone.setError(null);
                    edit_phone.setErrorEnabled(false);
                }

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) &&
                        !(password.length()<6)){
                    String username = name.getText().toString();

                    String new_email = edit_email.getEditText().getText().toString().trim();
                    Admin_Data admin = new Admin_Data(username,email,phone,password);


                    referance.child(username).setValue(admin);

                    fAuth.getCurrentUser().updatePassword(password);

                    new SweetAlertDialog(getContext(),SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Done")
                            .setContentText("Email and Password Has changed Successfully")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(null)
                            .show();

                   /*fAuth.sendPasswordResetEmail(new_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                new SweetAlertDialog(getContext(),SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Done")
                                        .setContentText("Password reset link is sent on your registered email")
                                        .setConfirmText("Ok")
                                        .setConfirmClickListener(null)
                                        .show();
                            }
                            else{
                                new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops!")
                                        .setContentText(task.getException().toString())
                                        .setConfirmText("Ok")
                                        .setConfirmClickListener(null)
                                        .show();
                            }
                        }
                    });*/
                }
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
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}