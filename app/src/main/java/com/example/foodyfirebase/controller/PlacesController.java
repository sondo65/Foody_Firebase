package com.example.foodyfirebase.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodyfirebase.R;
import com.example.foodyfirebase.adapters.AdapterRecyclePlaces;
import com.example.foodyfirebase.model.Restaurant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlacesController implements AdapterRecyclePlaces.OnRestaurantListener,ValueEventListener {

    Context context;
    Restaurant restaurant;
    public List<Restaurant> lsRes;
    private DatabaseReference rootNode;
    private ProgressDialog progressDialog;
    AdapterRecyclePlaces adapterRecyclePlaces;
    public PlacesController(Context context){
        this.context = context;
        rootNode = FirebaseDatabase.getInstance().getReference();
        restaurant = new Restaurant();
        rootNode.addValueEventListener(this);
        progressDialog = new ProgressDialog(context);
    }

    public void getListRestaurantController(RecyclerView recyclerViewPlaces){
        progressDialog.setMessage(context.getString(R.string.loading));
        progressDialog.show();
        lsRes = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewPlaces.setLayoutManager(layoutManager);
        adapterRecyclePlaces = new AdapterRecyclePlaces(lsRes, R.layout.custom_recycleview_places,this);
        recyclerViewPlaces.setAdapter(adapterRecyclePlaces);
    }

    @Override
    public void onRestaurantClick(int position) {
        Log.d("Click","Click + " + lsRes.get(position).getRestaurantName());
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        lsRes.clear();
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
            lsRes.add(restaurant);
        }
        adapterRecyclePlaces.notifyDataSetChanged();
        progressDialog.dismiss();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
