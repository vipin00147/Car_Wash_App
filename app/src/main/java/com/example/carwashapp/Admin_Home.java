package com.example.carwashapp;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Admin_Home extends AppCompatActivity {

    BottomNavigationView navigation;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__home);

        navigation = findViewById(R.id.navigation);
        viewPager = findViewById(R.id.view_pager);

        setUpViewPager();

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item1 :
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.item2 :
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.item3 :
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.item4 :
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.item5 :
                        viewPager.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });

       // navigation.setOnNavigationItemSelectedListener(navListener);
    }

    private  void setUpViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}