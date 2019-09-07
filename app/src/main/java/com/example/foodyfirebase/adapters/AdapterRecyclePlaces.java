package com.example.foodyfirebase.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodyfirebase.R;
import com.example.foodyfirebase.model.Restaurant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AdapterRecyclePlaces extends RecyclerView.Adapter<AdapterRecyclePlaces.ViewHolder> {

    List<Restaurant> lsRestaurant;
    int resource;
    OnRestaurantListener mOnRestaurantListener;
    public AdapterRecyclePlaces(List<Restaurant> lsRestaurant, int resource,OnRestaurantListener onRestaurantListener){
        this.lsRestaurant = lsRestaurant;
        this.resource = resource;
        this.mOnRestaurantListener = onRestaurantListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvRestaurantName;
        Button btnOderPlaces;
        ImageView imgRestaurant;
        OnRestaurantListener onRestaurantListener;
        public ViewHolder(@NonNull View itemView, OnRestaurantListener onRestaurantListener) {
            super(itemView);
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantNamePlaces);
            btnOderPlaces = itemView.findViewById(R.id.btnOderPlaces);
            imgRestaurant = itemView.findViewById(R.id.imgRestaurant);
            this.onRestaurantListener = onRestaurantListener;
            imgRestaurant.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onRestaurantListener.onRestaurantClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public AdapterRecyclePlaces.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,mOnRestaurantListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRecyclePlaces.ViewHolder holder, int position) {
        Restaurant restaurant = lsRestaurant.get(position);
        holder.tvRestaurantName.setText(restaurant.getRestaurantName());
        if(restaurant.isShipper()){
            holder.btnOderPlaces.setVisibility(View.VISIBLE);
        }
        if(restaurant.getRestaurentImages().size()>0){
            StorageReference storageImages = FirebaseStorage.getInstance()
                    .getReference().child("images")
                    .child(restaurant.getRestaurantCode())
                    .child(restaurant.getRestaurentImages().get(0));
            final long ONE_MEGABYTE = 1024 * 1024;
            storageImages.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    holder.imgRestaurant.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lsRestaurant.size();
    }

    public interface OnRestaurantListener{
        void onRestaurantClick(int position);
    }

}
