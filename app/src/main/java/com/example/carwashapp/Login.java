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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login extends AppCompatActivity {
    TextInputLayout Id, Password;
    Button Signin,Login_Admin;
    TextView Signup;
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Id = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Signin = findViewById(R.id.Signin);
        Login_Admin = findViewById(R.id.Admin_Login);
        Signup = findViewById(R.id.SignUp);
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),User_Home.class));
            finish();
        }

            Signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Getting Values..

                    String id = Id.getEditText().getText().toString().trim();
                    String password = Password.getEditText().getText().toString().trim();
                    if(TextUtils.isEmpty(id)) {
                        Id.setError("Email is required");
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

                    if(id.length()==10){

                        // Sign in using Phone No and Password...
                        rootNode = FirebaseDatabase.getInstance();
                        reference = rootNode.getReference("Users");

                        Query checkUser = reference.orderByChild("phone").equalTo(id);
                        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){

                                    String db_password = snapshot.child(id).child("password").getValue(String.class);
                                    if(db_password.equals(password)){
                                        String email = snapshot.child(id).child("email").getValue(String.class);

                                        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful()){
                                                    startActivity(new Intent(getApplicationContext(), User_Home.class));
                                                    finish();
                                                }
                                                else{
                                                    new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                                                            .setTitleText("Oops...")
                                                            .setContentText(task.getException().getMessage())
                                                            .show();
                                                }
                                            }
                                        });
                                    }
                                    else{
                                        new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Oops...")
                                                .setContentText("Wrong Password")
                                                .show();
                                    }
                                }
                                else{
                                    new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("No DATA Exist")
                                            .show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                    else {
                        // Sign in using Email Id and Password...
                        fAuth.signInWithEmailAndPassword(id,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    new SweetAlertDialog(Login.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("SUCCESS")
                                            .setContentText("Login Successfully")
                                            .show();

                                    startActivity(new Intent(getApplicationContext(), User_Home.class));
                                    finish();
                                }
                                else{
                                    new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Failed")
                                            .setContentText(task.getException().getMessage())
                                            .show();
                                }
                            }
                        });
                    }
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