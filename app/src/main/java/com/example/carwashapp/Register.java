package com.example.carwashapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

public class Register extends AppCompatActivity {
    TextInputLayout Email, Password, Phone, Name;
    Button Register;
    TextView loginButton;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Email = findViewById(R.id.email);
        Name = findViewById(R.id.full_name);
        Password = findViewById(R.id.password);
        Phone = findViewById(R.id.phone);
        Register = findViewById(R.id.register);
        loginButton = findViewById(R.id.signin_button);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting Values..

                String email = Email.getEditText().getText().toString().trim();
                String password = Password.getEditText().getText().toString().trim();
                String name = Name.getEditText().getText().toString().trim();
                String phone = Phone.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(name)) {
                    Name.setError("Name Required");
                    return;
                }
                if(TextUtils.isEmpty(email)) {
                    Email.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Password.setError("Password is Required");
                    return;
                }

                if(password.length()<6) {
                    Password.setError("Password Must be >= 6");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    Phone.setError("Phone No. required");
                    return;
                }
                if(phone.length()<10){
                    Phone.setError("Invalid Number");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Register user On fireBase..
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Register.this, "You have registered successfully.", Toast.LENGTH_SHORT).show();

                            rootNode = FirebaseDatabase.getInstance();
                            reference = rootNode.getReference("Users");

                            //setting user to database...
                            Users users = new Users(name,email,password,phone);

                            reference.child(phone).setValue(users);

                            //startActivity(new Intent(getApplicationContext(),Login.class));
                            progressBar.setVisibility(View.GONE);
                        }
                        else {
                            Toast.makeText(Register.this, "Error "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}