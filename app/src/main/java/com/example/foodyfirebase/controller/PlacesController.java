package com.example.foodyfirebase.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodyfirebase.R;
import com.example.foodyfirebase.adapters.AdapterRecyclePlaces;
import com.example.foodyfirebase.controller.interfaces.PlacesCallBackInterface;
import com.example.foodyfirebase.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class PlacesController {

    Context context;
    Restaurant restaurant;
    public static List<Restaurant> lsRes;
    private ProgressDialog progressDialog;
    AdapterRecyclePlaces adapterRecyclePlaces;
    public PlacesController(Context context){
        this.context = context;
        restaurant = new Restaurant();
        progressDialog = new ProgressDialog(context);
    }

    public void getListRestaurantController(RecyclerView recyclerViewPlaces){
        progressDialog.setMessage(context.getString(R.string.loading));
        progressDialog.show();
        lsRes = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewPlaces.setLayoutManager(layoutManager);
        adapterRecyclePlaces = new AdapterRecyclePlaces(lsRes, R.layout.custom_recycleview_places);
        recyclerViewPlaces.setAdapter(adapterRecyclePlaces);
        PlacesCallBackInterface placesInterface = new PlacesCallBackInterface() {
            @Override
            public void getListRestaurant(Restaurant Res) {
                progressDialog.dismiss();
                lsRes.add(Res);
                adapterRecyclePlaces.notifyDataSetChanged();
            }
        };
        restaurant.getListRestaurant(placesInterface);

    }
}
