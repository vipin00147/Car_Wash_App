package com.example.carwashapp;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddServiceCenterFragment extends Fragment {
    TextInputLayout Service_center_name;
    TextInputLayout Phone_no, Address, Opening_hours, Pin_code;
    FloatingActionButton Done;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_service_center, container, false);

        //Initializing text view Items..
        Service_center_name = view.findViewById(R.id.service_center_name);
        Phone_no = view.findViewById(R.id.phone_no);
        Address = view.findViewById(R.id.address);
        Opening_hours = view.findViewById(R.id.opening_hours);
        Pin_code = view.findViewById(R.id.Pin_code);
        Done = view.findViewById(R.id.fab);

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CENTER_NAME = Service_center_name.getEditText().getText().toString();
                String PHONE_NO = Phone_no.getEditText().getText().toString().trim();
                String ADDRESS = Address.getEditText().getText().toString().trim();
                String OPENING_HOURS = Opening_hours.getEditText().getText().toString().trim();
                String PIN_CODE = Pin_code.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(CENTER_NAME))              //Apply Empty validation
                    Service_center_name.setError("Input Field required");
                else{
                    Service_center_name.setError(null);
                    Service_center_name.setErrorEnabled(false);
                }
                if(TextUtils.isEmpty(PHONE_NO))
                    Phone_no.setError("Input Field required");
                else{
                    Phone_no.setError(null);
                    Phone_no.setErrorEnabled(false);
                }
                if(TextUtils.isEmpty(ADDRESS))
                    Address.setError("Input Field required");
                else{
                    Address.setError(null);
                    Address.setErrorEnabled(false);
                }
                if(TextUtils.isEmpty(OPENING_HOURS))
                    Opening_hours.setError("Input Field required");
                else{
                    Opening_hours.setError(null);
                    Opening_hours.setErrorEnabled(false);
                }
                if(TextUtils.isEmpty(PIN_CODE))
                    Pin_code.setError("Input Field required");
                else{
                    Pin_code.setError(null);
                    Pin_code.setErrorEnabled(false);
                }

                if(!TextUtils.isEmpty(CENTER_NAME) && !TextUtils.isEmpty(PHONE_NO) && !TextUtils.isEmpty(ADDRESS)
                && !TextUtils.isEmpty(OPENING_HOURS) && !TextUtils.isEmpty(PIN_CODE)){
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("Service_centers");

                    //Adding Service Center to database...
                    Users users = new Users(CENTER_NAME, PHONE_NO, ADDRESS, OPENING_HOURS, PIN_CODE);

                    reference.child(CENTER_NAME).setValue(users);

                    SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading ...");
                    pDialog.setCancelable(true);
                    pDialog.show();

                    final Handler handler  = new Handler();
                    final Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (pDialog.isShowing()) {
                                pDialog.dismiss();
                            }
                        }
                    };

                    pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            handler.removeCallbacks(runnable);
                            new SweetAlertDialog(getContext(),SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Done")
                                    .setContentText("Service Center Successfully")
                                    .show();
                        }
                    });

                    handler.postDelayed(runnable, 3000);

                    Service_center_name.getEditText().setText("");
                    Phone_no.getEditText().setText("");
                    Address.getEditText().setText("");
                    Opening_hours.getEditText().setText("");
                    Pin_code.getEditText().setText("");

                }
                else{
                    new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText("All Fields Are Required")
                            .show();
                }
            }
        });

        return view;
    }
}