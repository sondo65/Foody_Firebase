package com.example.foodyfirebase.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.foodyfirebase.R;
import com.example.foodyfirebase.adapters.AdapterRecyclePlaces;
import com.example.foodyfirebase.adapters.AdapterViewPagerHome;
import com.example.foodyfirebase.model.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener{

    private ViewPager viewPagerHome;
    private AdapterViewPagerHome adapterViewPagerHome;
    private RadioButton radPlaces,radFood;
    private RadioGroup radioGroup;
    private ImageView imgAddRestaurant;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        mDatabase = FirebaseDatabase.getInstance();

        viewPagerHome = findViewById(R.id.viewPagerHome);
        radPlaces = findViewById(R.id.radPlaces);
        radFood = findViewById(R.id.radFood);
        radioGroup = findViewById(R.id.radioGroup);
        imgAddRestaurant = findViewById(R.id.imgAddRestaurant);

        adapterViewPagerHome = new AdapterViewPagerHome(getSupportFragmentManager());
        viewPagerHome.setAdapter(adapterViewPagerHome);
        viewPagerHome.setOnPageChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        imgAddRestaurant.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgAddRestaurant:
                Restaurant res = new Restaurant();
                res.setRestaurantName("Demo Firebase");
                res.setShipper(true);
                res.setVoteTurn(100);
                mDatabase.getReference().child("restaurants").push().setValue(res).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                      if(task.isSuccessful())
                      {
                          Toast.makeText(HomeActivity.this,"Add restaurant success! ",Toast.LENGTH_LONG).show();
                      }else{
                          Toast.makeText(HomeActivity.this,"Add restaurant failed! ",Toast.LENGTH_LONG).show();
                      }
                    }
                });
                break;
        }
    }
}
