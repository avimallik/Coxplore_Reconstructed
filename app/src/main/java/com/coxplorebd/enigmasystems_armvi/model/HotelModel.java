package com.coxplorebd.enigmasystems_armvi.model;

/**
 * Created by Arm_AVI on 1/27/2019.
 */

public class HotelModel {
    String name = null, phone = null, description = null, web = null,  latitude=null, longitude=null, image = "" ;

    public HotelModel(String name, String phone, String description, String web, String latitude, String longitude, String image) {
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.web = web;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
