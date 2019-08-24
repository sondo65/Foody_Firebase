package com.example.foodyfirebase.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodyfirebase.controller.PlacesController;
import com.example.foodyfirebase.controller.interfaces.PlacesCallBackInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    String restaurantCode;
    String restaurantName;
    String closeTime;
    String introduceVideo;
    boolean shipper;
    String openTime;
    List<String> utilitis;
    long voteTurn;
    List<String> restaurentImages;

    private DatabaseReference rootNode;

    public Restaurant() {
        rootNode = FirebaseDatabase.getInstance().getReference();
    }

    public String getRestaurantCode() {
        return restaurantCode;
    }

    public void setRestaurantCode(String restaurantCode) {
        this.restaurantCode = restaurantCode;
    }

    public List<String> getRestaurentImages() {
        return restaurentImages;
    }

    public void setRestaurentImages(List<String> restaurentImages) {
        this.restaurentImages = restaurentImages;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getIntroduceVideo() {
        return introduceVideo;
    }

    public void setIntroduceVideo(String introduceVideo) {
        this.introduceVideo = introduceVideo;
    }

    public boolean isShipper() {
        return shipper;
    }

    public void setShipper(boolean shipper) {
        this.shipper = shipper;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public List<String> getUtilitis() {
        return utilitis;
    }

    public void setUtilitis(List<String> utilitis) {
        this.utilitis = utilitis;
    }

    public long getVoteTurn() {
        return voteTurn;
    }

    public void setVoteTurn(long voteTurn) {
        this.voteTurn = voteTurn;
    }

    public void getListRestaurant(final PlacesCallBackInterface placesInterface) {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PlacesController.lsRes.clear();
                DataSnapshot dataRes = dataSnapshot.child("restaurants");
                Log.d("data","onDatachange is running");
                for (DataSnapshot valueRes : dataRes.getChildren()) {
                    Restaurant restaurant = valueRes.getValue(Restaurant.class);
                    restaurant.setRestaurantCode(valueRes.getKey());
                    DataSnapshot dataListImage = (DataSnapshot) dataSnapshot.child("restaurantImages").child(valueRes.getKey());
                    List<String> listImages = new ArrayList<>();
                    for(DataSnapshot valueImages  : dataListImage.getChildren())
                    {
                        String image = valueImages.getValue(String.class);
                        listImages.add(image);
                    }
                    restaurant.setRestaurentImages(listImages);
                    placesInterface.getListRestaurant(restaurant);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        rootNode.addValueEventListener(eventListener);
    }
}
