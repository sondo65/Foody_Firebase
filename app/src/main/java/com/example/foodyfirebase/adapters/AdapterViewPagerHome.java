package com.example.foodyfirebase.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.foodyfirebase.view.fragments.FoodFragment;
import com.example.foodyfirebase.view.fragments.PlacesFragment;

public class AdapterViewPagerHome extends FragmentStatePagerAdapter {

    private Fragment placesFragment;
    private Fragment foodFragment;

    public AdapterViewPagerHome(FragmentManager fm) {
        super(fm);
        placesFragment = new PlacesFragment();
        foodFragment = new FoodFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return placesFragment;
            case 1:
                return foodFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
