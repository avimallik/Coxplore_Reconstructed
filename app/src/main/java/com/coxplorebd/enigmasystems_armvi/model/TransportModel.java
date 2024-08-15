package com.coxplorebd.enigmasystems_armvi.model;

/**
 * Created by Arm_AVI on 1/21/2019.
 */

public class TransportModel {

    String name = null, phone = null, description = null, web = null, image = "" ;

    public TransportModel(String name, String phone, String description, String image, String web) {
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.web = web;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
