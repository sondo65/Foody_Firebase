package com.example.foodyfirebase.model;

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


    public Restaurant() {

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

}
