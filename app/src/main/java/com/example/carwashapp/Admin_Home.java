package com.example.carwashapp;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Admin_Home extends AppCompatActivity {

    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__home);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(navListener);
    }
     private BottomNavigationView.OnNavigationItemSelectedListener navListener =
             new BottomNavigationView.OnNavigationItemSelectedListener() {
                 @Override
                 public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                     Fragment fragment = null;
                     switch (item.getItemId()){
                         case R.id.item1 :
                             fragment = new AppointmentFragment();
                             break;
                         case R.id.item2 :
                             fragment = new CreateUserFragment();
                             break;
                         case R.id.item3 :
                             fragment = new AddServiceCenterFragment();
                             break;
                         case R.id.item4 :
                             fragment = new ViewServiceCentersFragment();
                             break;
                         case  R.id.item5 :
                             fragment = new profileFragment();
                             break;
                     }
                     getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                     return true;
                 }
             };
}