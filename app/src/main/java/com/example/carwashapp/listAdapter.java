package com.example.carwashapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class listAdapter extends FirebaseRecyclerAdapter<gettingListFromFirebase,listAdapter.viewHolder> {

    public listAdapter(@NonNull FirebaseRecyclerOptions<gettingListFromFirebase> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull gettingListFromFirebase model) {

        holder.center_name.setText(model.getCenter_name());
        holder.address.setText(model.getAddess());
        holder.phone.setText(model.getPhone_no());
        holder.pin_code.setText(model.getPin_code());
        holder.open_hours.setText(model.getOpening_hours());
        Glide.with(holder.imageView.getContext()).load(model.getImage()).into(holder.imageView);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_center_row,parent,false);
        return new viewHolder(view);
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView center_name, address, phone, pin_code, open_hours;
        View view;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (CircleImageView) itemView.findViewById(R.id.profile_image);
            center_name = (TextView) itemView.findViewById(R.id.center_name);
            address = (TextView) itemView.findViewById(R.id.center_address);
            phone = (TextView) itemView.findViewById(R.id.center_phone);
            pin_code = (TextView) itemView.findViewById(R.id.center_pin);
            open_hours = (TextView) itemView.findViewById(R.id.center_time);
            view = itemView;
        }
    }
}
