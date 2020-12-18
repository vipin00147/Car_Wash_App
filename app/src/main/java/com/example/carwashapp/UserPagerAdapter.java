package com.example.carwashapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class UserPagerAdapter extends FragmentStatePagerAdapter {
    public UserPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new SearchFragment();
            case 1 :
                return new RequestFragment();
            case 2 :
                return new User_ProfileFragment();
            default :
                return new SearchFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
