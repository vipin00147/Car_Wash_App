package com.example.carwashapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class Search_List_Adapter extends FirebaseRecyclerAdapter<gettingListFromFirebase,Search_List_Adapter.viewHolder> {

    public Search_List_Adapter(@NonNull FirebaseRecyclerOptions<gettingListFromFirebase> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Search_List_Adapter.viewHolder holder, int position, @NonNull gettingListFromFirebase model) {

        holder.center_name.setText(model.getCenter_name());
        holder.address.setText(model.getAddess());
        holder.phone.setText(model.getPhone_no());
        holder.pin_code.setText(model.getPin_code());
        holder.open_hours.setText(model.getOpening_hours());
        Glide.with(holder.imageView.getContext()).load(model.getImage()).into(holder.imageView);

        holder.book_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Book_Appointment_Activity.class);
                intent.putExtra("center_key",getRef(position).getKey());
                holder.rootView.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public Search_List_Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_center_row,parent,false);
        return new viewHolder(view);
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        View rootView;
        TextView center_name, address, phone, pin_code, open_hours;
        Button book_appointment;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (CircleImageView) itemView.findViewById(R.id.search_profile_image);
            center_name = (TextView) itemView.findViewById(R.id.search_center_name);
            address = (TextView) itemView.findViewById(R.id.search_center_address);
            phone = (TextView) itemView.findViewById(R.id.search_center_phone);
            pin_code = (TextView) itemView.findViewById(R.id.search_center_pin);
            open_hours = (TextView) itemView.findViewById(R.id.search_center_time);
            book_appointment = (Button) itemView.findViewById(R.id.book_appointment);
            rootView = itemView;
        }
    }
}