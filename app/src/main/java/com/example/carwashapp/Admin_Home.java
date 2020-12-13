package com.example.carwashapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.WindowManager;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class Admin_Home extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__home);

        bottomNavigation = (MeowBottomNavigation)findViewById(R.id.bottom_nav);

        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.order));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.add_user));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.car_wash));
        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.view_list));
        bottomNavigation.add(new MeowBottomNavigation.Model(5,R.drawable.account_circle));

        bottomNavigation.show(3,true);
    }
}