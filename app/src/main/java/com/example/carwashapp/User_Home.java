package com.example.carwashapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class User_Home extends AppCompatActivity {

    BottomNavigationView navigation;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__home);
        navigation = findViewById(R.id.user_navigation);
        viewPager = findViewById(R.id.user_view_pager);

        setUpViewPager();

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.user_item1 :
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.user_item2 :
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.user_item3 :
                        viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
    }

    private  void setUpViewPager() {
        UserPagerAdapter adapter = new UserPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navigation.getMenu().findItem(R.id.user_item1).setChecked(true);
                        break;
                    case 1:
                        navigation.getMenu().findItem(R.id.user_item2).setChecked(true);
                        break;
                    case 2:
                        navigation.getMenu().findItem(R.id.user_item3).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}