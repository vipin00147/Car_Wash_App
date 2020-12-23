package com.example.carwashapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_Profile_Setting_Activity extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    CircleImageView Profile_Image;
    DatabaseReference reference;
    TextInputLayout Name,Email,Phone;
    StorageReference Folder;
    FloatingActionButton fab;

    public static final int ImageBack = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile__setting_);

        Name = findViewById(R.id.Edit_Name);
        Email = findViewById(R.id.Edit_Email);
        Phone = findViewById(R.id.Edit_Phone);
        Profile_Image = findViewById(R.id.image);
        fab = findViewById(R.id.floatingActionButton);

        fAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");

        SharedPreferences getshrd = getSharedPreferences("demo",MODE_PRIVATE);
        Profile_Image.setImageURI(Uri.parse(getshrd.getString("image","0")));
        Name.getEditText().setText(getshrd.getString("name","0"));
        Email.getEditText().setText(getshrd.getString("email","0"));
        Phone.getEditText().setText(getshrd.getString("phone","0"));

        Query checkUser = reference.orderByChild("phone").equalTo(CurrentUser.phone);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Name.getEditText().setText(snapshot.child(CurrentUser.phone).child("name").getValue(String.class));
                Email.getEditText().setText(snapshot.child(CurrentUser.phone).child("email").getValue(String.class));
                Phone.getEditText().setText(snapshot.child(CurrentUser.phone).child("phone").getValue(String.class));
                Glide.with(Profile_Image.getContext()).load(snapshot.child(CurrentUser.phone).child("image").getValue(String.class)).into(Profile_Image);

                SharedPreferences shrd = getSharedPreferences("demo",MODE_PRIVATE);
                SharedPreferences.Editor editor = shrd.edit();
                editor.putString("name",Name.getEditText().getText().toString());
                editor.putString("email",Email.getEditText().getText().toString());
                editor.putString("phone",Phone.getEditText().getText().toString());
                editor.apply();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,ImageBack);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == ImageBack){
            if(resultCode == RESULT_OK){
                Uri image_data = data.getData();
                Folder = FirebaseStorage.getInstance().getReference().child("ImageFolder");
                StorageReference image_name = Folder.child("image"+image_data.getLastPathSegment());
                image_name.putFile(image_data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                         reference = FirebaseDatabase.getInstance().getReference("Users");

                         image_name.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                             @Override
                             public void onSuccess(Uri uri) {
                                 HashMap<String,String> hashMap = new HashMap<>();
                                 hashMap.put("image",uri.toString());
                                 reference.child(CurrentUser.phone).child("image").setValue(uri.toString());

                                 SharedPreferences shrd = getSharedPreferences("demo",MODE_PRIVATE);
                                 SharedPreferences.Editor editor = shrd.edit();
                                 editor.putString("image",uri.toString());
                                 editor.apply();

                                 return;
                             }
                         });
                    }
                });
            }
        }




        super.onActivityResult(requestCode, resultCode, data);
    }

}