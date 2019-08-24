package com.example.foodyfirebase.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodyfirebase.R;
import com.example.foodyfirebase.controller.PlacesController;


public class PlacesFragment extends Fragment {

    private PlacesController placesController;
    private RecyclerView recyclerViewPlaces;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.places_fragment_layout,container,false);
        recyclerViewPlaces = view.findViewById(R.id.recyclerViewPlaces);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        placesController = new PlacesController(getContext());
        placesController.getListRestaurantController(recyclerViewPlaces);
    }
}
