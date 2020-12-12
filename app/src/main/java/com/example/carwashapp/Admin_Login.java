package com.example.carwashapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Admin_Login extends AppCompatActivity {
    TextInputLayout Email,Password;
    Button Admin_Login, Customer_Login;
    TextView Create_Account;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__login);

        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Admin_Login = findViewById(R.id.Admin_Login);
        Customer_Login = findViewById(R.id.Customer_Login);
        Create_Account = findViewById(R.id.SignUp);
        progressBar = findViewById(R.id.progressBar2);

        //Login As Admin..
        Admin_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                String email = Email.getEditText().getText().toString().trim();
                String password = Password.getEditText().getText().toString().trim();

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Admin");

                //check query whether the entered username and password is present or not....
                Query checkUser = reference.orderByChild("username").equalTo(email);

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        if(datasnapshot.exists()) {

                            //fetching data from database...
                            String name = datasnapshot.child(email).child("username").getValue(String.class);
                            String pass = datasnapshot.child(email).child("password").getValue(String.class);

                            //matching entered username and password is correct or not....
                           if(TextUtils.equals(name,email) && TextUtils.equals(pass,password)){
                                Toast.makeText(Admin_Login.this, "Welcome "+name, Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                        else{
                            Toast.makeText(Admin_Login.this, "Username doesn't exist", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
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
}