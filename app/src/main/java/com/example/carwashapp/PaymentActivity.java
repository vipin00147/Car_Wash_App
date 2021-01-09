package com.example.carwashapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import com.braintreepayments.cardform.view.CardForm;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class PaymentActivity extends AppCompatActivity {
    AlertDialog.Builder alertBuilder;
    TextView amount, Timing ;
    String total, timing, vehicle_number, vehicle_type;
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    CheckBox cod;
    DatabaseReference reference;
    String NAME, MOBILE, EMAIL, AMOUNT, TIMING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Bundle intent = getIntent().getExtras();
        total = intent.getString("total");
        timing = intent.getString("timing");
        vehicle_number = intent.getString("vehicle_number");
        vehicle_type = intent.getString("vehicle_type");

        CardForm cardForm = findViewById(R.id.card_form);
        Button buy = findViewById(R.id.button);
        amount = findViewById(R.id.amount_total);
        Timing = findViewById(R.id.textView7);
        cod = findViewById(R.id.cod);

        amount.setText("Rs."+total);
        Timing.setText("Appointment Timing : "+timing);

        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(PaymentActivity.this);
        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cod.isChecked()) {
                    new SweetAlertDialog(PaymentActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Good job!")
                            .setContentText("Your appointment is scheduled")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    uploadDetails();
                                    startActivity(new Intent(getApplicationContext(), User_Home.class));
                                }
                            })
                            .show();
                } else {
                if (cardForm.isValid()) {
                    alertBuilder = new AlertDialog.Builder(PaymentActivity.this);
                    alertBuilder.setTitle("Confirm before purchase");
                    alertBuilder.setMessage("Card number: " + cardForm.getCardNumber() + "\n" +
                            "Card expiry date: " + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                            "Card CVV: " + cardForm.getCvv() + "\n" +
                            "Postal code: " + cardForm.getPostalCode() + "\n" +
                            "Phone number: " + cardForm.getMobileNumber());
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            uploadDetails();

                            new SweetAlertDialog(PaymentActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Good job!")
                                    .setContentText("Your appointment is scheduled")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            startActivity(new Intent(getApplicationContext(), User_Home.class));
                                        }
                                    })
                                    .show();
                            //setting appointment detail of current user


                        }
                    });
                    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();

                } else {
                    new SweetAlertDialog(PaymentActivity.this)
                            .setTitleText("Oops!")
                            .setContentText("Please Complete the form")
                            .show();
                }
            }
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    //uploading appointment  details of current user...
    private void uploadDetails() {
        fAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference().child("Users");

        String email = fAuth.getCurrentUser().getEmail();

        Query checkUser = reference.orderByChild("phone").equalTo(CurrentUser.phone);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    MOBILE = snapshot.child(CurrentUser.phone).child("phone").getValue(String.class);
                    EMAIL = snapshot.child(CurrentUser.phone).child("email").getValue(String.class);
                    NAME = snapshot.child(CurrentUser.phone).child("name").getValue(String.class);
                    AMOUNT = total;
                    TIMING = timing;

                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference().child("Appointment_Booked");

                    UploadApointmentDetail detail = new UploadApointmentDetail(NAME, MOBILE, EMAIL, AMOUNT, TIMING, CurrentUser.center_name, CurrentUser.center_image,"0", vehicle_number, vehicle_type);
                    reference.child(MOBILE).setValue(detail);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}