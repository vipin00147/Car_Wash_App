package com.example.carwashapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    TabLayout tabIndicator;
    IntroView_Page_Adapter introViewPageAdapter;
    TextView btnNext;
    Button btnGetStarted;
    int position = 0;
    Animation btnAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        if(restorePrefData()){
            Intent intent = new Intent(getApplicationContext(),Register.class);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        screenPager = findViewById(R.id.viewPager2);
        tabIndicator = findViewById(R.id.tabLayout);
        btnNext = findViewById(R.id.btn_Next);
        btnGetStarted = findViewById(R.id.btnGetStarted);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);

        //fill List Screen..
        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Quick Wash","Cleaning the interior of a car includes vacuuming, headliners.",R.drawable.first));
        mList.add(new ScreenItem("Easy Payment","Easy Payment at low price with full interior and exterior wash",R.drawable.second));
        mList.add(new ScreenItem("Quick Return","Car Returning within 5 Hours of wash. To reduce the scope of time loss.",R.drawable.third));

        introViewPageAdapter = new IntroView_Page_Adapter(this,mList);
        screenPager.setAdapter(introViewPageAdapter);

        //setup tabLayout with viewpager..
        tabIndicator.setupWithViewPager(screenPager);

        // next button click listener...
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = screenPager.getCurrentItem();
                if(position<mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if(position  == mList.size()-1){
                    loadLastScreen();
                }
            }
        });
        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == mList.size()-1){
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //get started btn listener...
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
                //save a boolean value to shows intro only once..
                savePrefData();
                finish();
            }
        });


    }
    //Show the get Started Button after reaching at last screen..
    private void loadLastScreen() {
        btnGetStarted.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.GONE);
        tabIndicator.setVisibility(View.GONE);

        //set btn animation ..
        btnGetStarted.setAnimation(btnAnim);
    }
    private void savePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("mypref",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isOpened",true);
        editor.commit();
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("mypref",MODE_PRIVATE);
        Boolean isOpened = pref.getBoolean("isOpened",false);
        return isOpened;
    }
}