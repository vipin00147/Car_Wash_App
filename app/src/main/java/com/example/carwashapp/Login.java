package com.example.carwashapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    TextInputLayout Email, Password;
    Button Signin,Login_Admin;
    TextView Signup;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Signin = findViewById(R.id.Signin);
        progressBar = findViewById(R.id.progressBar2);
        Login_Admin = findViewById(R.id.Admin_Login);
        Signup = findViewById(R.id.SignUp);
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting Values..

                String email = Email.getEditText().getText().toString().trim();
                String password = Password.getEditText().getText().toString().trim();
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

                progressBar.setVisibility(View.VISIBLE);

                // Sign in using FireBase..
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();

                            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(Login.this, "Error "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        Login_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin_Login.class));
                finish();
            }
        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
                finish();
            }
        });
    }
}