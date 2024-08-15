package com.coxplorebd.enigmasystems_armvi.model;

/**
 * Created by Arm_AVI on 1/30/2019.
 */

public class TouristspotModel {

    String name = null, description = null,  latitude=null, longitude=null, image = "", imageTwo = "", imageThree = "" ;

    public TouristspotModel(String name, String description, String latitude, String longitude, String image, String imageTwo, String imageThree) {
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.imageTwo = imageTwo;
        this.imageThree = imageThree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;

    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getImageTwo() {
        return imageTwo;
    }

    public void setImageTwo(String imageTwo) {
        this.imageTwo = imageTwo;
    }

    public String getImageThree() {
        return imageThree;
    }

    public void setImageThree(String imageThree) {
        this.imageThree = imageThree;
    }
}
