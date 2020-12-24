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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Update_User_Password extends AppCompatActivity {
    TextInputLayout pass, conf_pass;
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Button update;
    SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__user__password);

        pass = findViewById(R.id.new_password);
        conf_pass = findViewById(R.id.update_password);
        update = findViewById(R.id.update);

        Bundle intent = getIntent().getExtras();
        String Phone = intent.getString("phone");

        fAuth = FirebaseAuth.getInstance();


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pDialog = new SweetAlertDialog(Update_User_Password.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(true);
                pDialog.show();

                String one = pass.getEditText().getText().toString();
                String two = conf_pass.getEditText().getText().toString();
                if(TextUtils.equals(one,two) && one.length()>=6){

                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("Users");

                    Query checkUser = reference.orderByChild("phone").equalTo(Phone);
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String email = snapshot.child(Phone).child("email").getValue(String.class);
                                String old_pass  = snapshot.child(Phone).child("password").getValue(String.class);
                                String phone = snapshot.child(Phone).child("phone").getValue(String.class);
                                String image = snapshot.child(Phone).child("image").getValue(String.class);
                                String name = snapshot.child(Phone).child("name").getValue(String.class);

                                fAuth.signInWithEmailAndPassword(email,old_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            fAuth.getCurrentUser().updatePassword(one);
                                            Users user = new Users(name,email,one,phone,image,"abc");
                                            reference.child(phone).setValue(user);
                                            pDialog = new SweetAlertDialog(Update_User_Password.this, SweetAlertDialog.SUCCESS_TYPE);
                                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                            pDialog.setTitleText("Congratulations");
                                            pDialog.setCancelable(true);
                                            pDialog.setContentText("Your Password has updated Successfully Click OK to Sign In");
                                            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    fAuth.signOut();
                                                    startActivity(new Intent(getApplicationContext(),Admin_Login.class));
                                                    finish();
                                                }
                                            });
                                            pDialog.show();
                                        }
                                    }
                                });
                            }
                            else{
                                pDialog = new SweetAlertDialog(Update_User_Password.this, SweetAlertDialog.ERROR_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("Oops");
                                pDialog.setCancelable(true);
                                pDialog.setContentText("No Data Exist");
                                pDialog.show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }
                else{
                    pDialog = new SweetAlertDialog(Update_User_Password.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Oops!");
                    pDialog.setCancelable(true);
                    pDialog.setContentText("Password is not correct or length <6");
                    pDialog.show();
                }
            }
        });
    }
    public void onBackPressed(){
        if(fAuth.getCurrentUser() != null)
            fAuth.signOut();
        Intent a = new Intent(getApplicationContext(),Login.class);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}