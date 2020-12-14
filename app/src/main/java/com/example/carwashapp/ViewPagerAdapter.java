package com.example.carwashapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new AppointmentFragment();
            case 1 :
                return new CreateUserFragment();
            case 2 :
                return new AddServiceCenterFragment();
            case 3 :
                return new ViewServiceCentersFragment();
            case 4 :
                return new profileFragment();
            default :
                return new AddServiceCenterFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
