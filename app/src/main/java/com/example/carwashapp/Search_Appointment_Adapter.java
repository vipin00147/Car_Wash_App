package com.example.carwashapp;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import de.hdodenhof.circleimageview.CircleImageView;

public class Search_Appointment_Adapter extends FirebaseRecyclerAdapter<requestListfromFirebase,Search_Appointment_Adapter.viewHolder> {

    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    public Search_Appointment_Adapter(@NonNull FirebaseRecyclerOptions<requestListfromFirebase> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull requestListfromFirebase model) {

        holder.center_name.setText(model.getCenter_name());
        holder.name.setText(model.getName());
        holder.appointment_date.setText(model.getAppointment_date());
        holder.amount.setText(model.getAmount());
        holder.phone.setText(model.getMobile());
        Glide.with(holder.imageView.getContext()).load(model.getCenter_image()).into(holder.imageView);
        fAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Appointment_Booked");

        Query checkUser = reference.orderByChild("mobile").equalTo(getRef(position).getKey());
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Status_Code = snapshot.child(getRef(position).getKey()).child("status_code").getValue(String.class);
                if(TextUtils.equals(Status_Code,"0")){
                    holder.approve.setText("Approve");
                    holder.decline.setText("Declined🔒");
                }
                else if(TextUtils.equals(Status_Code,"1")){
                    holder.approve.setText("Approved🔒");
                    holder.decline.setText("Decline");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String MOBILE_NUMBER = getRef(position).getKey();
                //Set Appointment Status Flag of a User...
                fAuth = FirebaseAuth.getInstance();
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Appointment_Booked");
                reference.child(MOBILE_NUMBER).child("status_code").setValue("1");
                holder.approve.setText("Approved🔒");
                holder.decline.setText("Decline");
            }
        });
        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String MOBILE_NUMBER = getRef(position).getKey();
                //Set Appointment Status Flag of a User...
                fAuth = FirebaseAuth.getInstance();
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Appointment_Booked");
                reference.child(MOBILE_NUMBER).child("status_code").setValue("0");
                holder.approve.setText("Approve");
                holder.decline.setText("Declined🔒");
            }
        });

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_list_row,parent,false);
        return new viewHolder(view);
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        View rootView;
        TextView center_name, name, phone, amount, appointment_date;
        Button decline, approve;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.circleImageView);
            center_name = itemView.findViewById(R.id.textView9);
            name = itemView.findViewById(R.id.textView12);
            phone = itemView.findViewById(R.id.textView15);
            amount = itemView.findViewById(R.id.textView17);
            appointment_date = itemView.findViewById(R.id.textView13);
            decline = itemView.findViewById(R.id.decline);
            approve = itemView.findViewById(R.id.approve);
            rootView = itemView;
        }
    }
}
