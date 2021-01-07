package com.example.carwashapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Register extends AppCompatActivity {
    TextInputLayout Email, Password, Phone, Name;
    Button Register;
    TextView loginButton;
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    SweetAlertDialog pDialog;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        Email = findViewById(R.id.email);
        Name = findViewById(R.id.full_name);
        Password = findViewById(R.id.password);
        Phone = findViewById(R.id.phone);
        Register = findViewById(R.id.register);
        loginButton = findViewById(R.id.signin_button);
        fAuth = FirebaseAuth.getInstance();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pDialog = new SweetAlertDialog(Register.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(true);
                pDialog.show();

                //Getting Values..

                String email = Email.getEditText().getText().toString().trim();
                String password = Password.getEditText().getText().toString().trim();
                String name = Name.getEditText().getText().toString().trim();
                String phone = Phone.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(name)) {
                    Name.setError("Name Required");
                    pDialog.dismiss();
                    return;
                }
                if(TextUtils.isEmpty(email)) {
                    Email.setError("Email is required");
                    pDialog.dismiss();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Password.setError("Password is Required");
                    pDialog.dismiss();
                    return;
                }

                if(password.length()<6) {
                    Password.setError("Password Must be >= 6");
                    pDialog.dismiss();
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    Phone.setError("Phone No. required");
                    pDialog.dismiss();
                    return;
                }
                if(phone.length()<10){
                    Phone.setError("Invalid Number");
                    pDialog.dismiss();
                    return;
                }


                // Register user On fireBase..
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            pDialog.setTitleText("Congratulations");
                            pDialog.setContentText("You have registered successfully.");
                            pDialog.show();

                            rootNode = FirebaseDatabase.getInstance();
                            reference = rootNode.getReference("Users");

                            //setting user to database...
                            Users users = new Users(name,email,password,phone,"","");

                            reference.child(phone).setValue(users);

                            //startActivity(new Intent(getApplicationContext(),Login.class));
                        }
                        else {
                            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            pDialog.setTitleText("Error");
                            pDialog.setContentText(task.getException().getMessage());
                            pDialog.show();
                        }
                    }
                });
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fAuth.getCurrentUser() !=null){
                    if(fAuth.getCurrentUser().getEmail() == "vipin00147@gmail.com" || fAuth.getCurrentUser().getEmail() == "srj2264@gmail.com"){
                        startActivity(new Intent(getApplicationContext(),Admin_Login.class));
                        finish();
                    }
                    else{
                        startActivity(new Intent(getApplicationContext(),Login.class));
                        finish();
                    }
                }
                else{
                    startActivity(new Intent(getApplicationContext(),Login.class));
                    finish();
                }
            }
        });
    }
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}