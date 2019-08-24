package com.example.foodyfirebase.view;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.foodyfirebase.R;
import com.example.foodyfirebase.adapters.AdapterViewPagerHome;

public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    private ViewPager viewPagerHome;
    private AdapterViewPagerHome adapterViewPagerHome;
    private RadioButton radPlaces,radFood;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        viewPagerHome = findViewById(R.id.viewPagerHome);
        radPlaces = findViewById(R.id.radPlaces);
        radFood = findViewById(R.id.radFood);
        radioGroup = findViewById(R.id.radioGroup);

        adapterViewPagerHome = new AdapterViewPagerHome(getSupportFragmentManager());
        viewPagerHome.setAdapter(adapterViewPagerHome);
        viewPagerHome.setOnPageChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                radPlaces.setChecked(true);
                break;
            case 1:
                radFood.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.radPlaces:
                viewPagerHome.setCurrentItem(0);
                break;
            case R.id.radFood:
                viewPagerHome.setCurrentItem(1);
                break;
        }
    }
}
