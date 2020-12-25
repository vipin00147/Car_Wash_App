package com.example.carwashapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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


public class Admin_Login extends AppCompatActivity {
    TextInputLayout Username, Password;
    Button Admin_Login, Customer_Login;
    TextView Create_Account;
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__login);

        Username = findViewById(R.id.username);
        Password = findViewById(R.id.Password);
        Admin_Login = findViewById(R.id.Admin_Login);
        Customer_Login = findViewById(R.id.Customer_Login);
        Create_Account = findViewById(R.id.SignUp);
        fAuth = FirebaseAuth.getInstance();
        try{
            if(fAuth.getCurrentUser().getEmail() == "vipin00147@gmail.com" || fAuth.getCurrentUser().getEmail() == "srj2264@gmail.com"){
                startActivity(new Intent(getApplicationContext(),Admin_Home.class));
                finish();
            }
            else{
                fAuth.signOut();
            }
        }
       catch (Exception e){
            e.printStackTrace();
        }
            //Login As Admin..
            Admin_Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SweetAlertDialog pDialog = new SweetAlertDialog(Admin_Login.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading ...");
                    pDialog.setCancelable(true);
                    pDialog.show();

                    String username = Username.getEditText().getText().toString().trim();
                    String password = Password.getEditText().getText().toString().trim();
                    if(TextUtils.isEmpty(username)) {
                        pDialog.dismiss();
                        Username.setError("Username is required");
                        return;
                    }
                    else {
                        pDialog.dismiss();
                        Username.setError(null);
                        Username.setErrorEnabled(false);
                    }
                    if(TextUtils.isEmpty(password)) {
                        pDialog.dismiss();
                        Password.setError("Password is Required");
                        return;
                    }else {
                        pDialog.dismiss();
                        Password.setError(null);
                        Password.setErrorEnabled(false);
                    }

                    if(password.length()<6) {
                        pDialog.dismiss();
                        Password.setError("Password Must be >= 6");
                        return;
                    } else {
                        pDialog.dismiss();
                        Password.setError(null);
                        Password.setErrorEnabled(false);
                    }

                    // Sign in using FireBase..
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("Admin");

                    Query checkUser = reference.orderByChild("username").equalTo(username);
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                new SweetAlertDialog(Admin_Login.this, SweetAlertDialog.PROGRESS_TYPE)
                                        .setTitleText("Loading")
                                        .show();
                                String db_password = snapshot.child(username).child("password").getValue(String.class);
                                if(db_password.equals(password)){
                                    String email = snapshot.child(username).child("email").getValue(String.class);

                                    fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                                startActivity(new Intent(getApplicationContext(), Admin_Home.class));
                                                finish();
                                                pDialog.dismiss();
                                            }
                                            else{
                                                new SweetAlertDialog(Admin_Login.this, SweetAlertDialog.ERROR_TYPE)
                                                        .setTitleText("Oops...")
                                                        .setContentText(task.getException().getMessage())
                                                        .show();
                                                pDialog.dismiss();
                                            }
                                        }
                                    });
                                }
                                else{
                                    new SweetAlertDialog(Admin_Login.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("Wrong Password")
                                            .show();
                                    pDialog.dismiss();
                                }
                            }
                            else{
                                new SweetAlertDialog(Admin_Login.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("No DATA Exist")
                                        .show();
                                pDialog.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });

            //Go to customer Login Page
            Customer_Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(),Login.class));
                    finish();
                }
            });

            //Go To Create Account Page;
            Create_Account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(),Register.class));
                    finish();
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