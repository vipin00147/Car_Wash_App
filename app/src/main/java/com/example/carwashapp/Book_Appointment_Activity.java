package com.example.carwashapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

import static android.app.ProgressDialog.show;

public class Book_Appointment_Activity extends AppCompatActivity {

    TextInputLayout dateText;
    CheckBox c1,c2,c3,c4,c5;
    private int total =0 ;
    Boolean flag;
    int new_hour,new_min;
    ImageView calender;
    Button continue_Page;
    private int year,day,month,hour, minute;
    String h1,h2,h3,h4,h5;
    int duration = 0;
    String p1,p2,p3,p4,p5;
    String d1,d2,d3,d4,d5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__appointment_);

        dateText = findViewById(R.id.time);
        calender = findViewById(R.id.calender);
        c1 = findViewById(R.id.checkbox1);
        c2 = findViewById(R.id.checkbox2);
        c3 = findViewById(R.id.checkbox3);
        c4 = findViewById(R.id.checkbox4);
        c5 = findViewById(R.id.checkbox5);
        continue_Page = findViewById(R.id.Continue);

        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                day = cal.get(Calendar.DAY_OF_MONTH);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);

                DatePickerDialog dialog = new DatePickerDialog(Book_Appointment_Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        dateText.getEditText().setText(date+"/"+(month+1)+"/"+year);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(Book_Appointment_Activity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                hour = i;
                                minute = i1;

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0,0,0,hour,minute);

                                String date = dateText.getEditText().getText().toString();
                                dateText.getEditText().setText(date+"  "+ DateFormat.format("hh:mm aa", calendar));
                                new_hour = i;
                                new_min = i1;
                            }
                        },12,0,false);
                        timePickerDialog.updateTime(hour,minute);
                        timePickerDialog.show();
                    }

                }, year,month,day);
                dialog.show();
            }
        });

        //button listener to check if the service is selected or not..
        continue_Page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total = 0;
                duration = 0;
                flag = false;
                String txt = dateText.getEditText().getText().toString();
                if(c1.isChecked()){
                    total += 100;
                    h1 = "Vacuuming";
                    d1 = getResources().getString( R.string.vacuuming);
                    p1 = "Rs.100";
                    flag = true;
                }
                if(c2.isChecked()){
                    total += 100;
                    h2 = "Brushing - Steam Clean";
                    d2 = getResources().getString(R.string.brushing);
                    p2 = "Rs.100";
                    flag = true;
                }
                if(c3.isChecked()){
                    total += 200;
                    h3 = "Glass Cleaning";
                    d3 = getResources().getString(R.string.polishing);
                    p3 = "Rs.200";
                    flag = true;
                }
                if(c4.isChecked()){
                    total +=60;
                    h4 = "Leather Cleaning";
                    d4 = getResources().getString(R.string.trimming);
                    p4 = "Rs.60";
                    flag = true;
                }
                if(c5.isChecked()){
                    total += 60;
                    h5 = "Perfuming";
                    d5 = getResources().getString(R.string.perfuming);
                    p5 = "Rs.60";
                    flag = true;
                }
                if(flag == true && !TextUtils.isEmpty(txt)){

                    //Go to Appointment Review Fragment...
                    Intent intent = new Intent(getApplicationContext(),PaymentActivity.class);
                    intent.putExtra("total",total+"");
                    intent.putExtra("timing",dateText.getEditText().getText().toString());
                    startActivity(intent);

                }
                else{
                    if(flag == false)
                        Toast.makeText(Book_Appointment_Activity.this, "Select a Service", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(Book_Appointment_Activity.this, "Set Appointment date & Time", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}