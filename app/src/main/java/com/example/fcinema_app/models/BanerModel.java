package com.example.fcinema_app.models;

import com.google.gson.annotations.SerializedName;

public class BanerModel {
    @SerializedName("anh")

    private String imgUrl;

    public BanerModel(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
